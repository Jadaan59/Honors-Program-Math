import java.math.BigInteger;

public class Div {

    public static BigInteger[] div(BigInteger x, BigInteger y) {
        // Ensure the divisor is positive
        if (y.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("Divisor y must be positive.");
        }

        // Base case: if x is 0, return (0, 0)
        if (x.equals(BigInteger.ZERO)) {
            return new BigInteger[]{BigInteger.ZERO, BigInteger.ZERO};
        }

        // Recursive step: divide x/2 by y
        BigInteger[] result = div(x.shiftRight(1), y); // x / 2
        BigInteger q = result[0];
        BigInteger r = result[1];

        // Update q and r
        q = q.shiftLeft(1); // q * 2
        r = r.shiftLeft(1); // r * 2

        // If x is odd, increment r
        if (x.and(BigInteger.ONE).equals(BigInteger.ONE)) {
            r = r.add(BigInteger.ONE);
        }

        // If r >= y, adjust q and r
        if (r.compareTo(y) >= 0) {
            r = r.subtract(y);
            q = q.add(BigInteger.ONE);
        }

        // Return the quotient and remainder
        return new BigInteger[]{q, r};
    }

    public static void main(String[] args) {
        BigInteger x = new BigInteger("99");
        BigInteger y = new BigInteger("12");
        BigInteger[] result = div(x, y);

        System.out.println(x + " = " + result[0] + "*" + y + " + " + result[1]);

        // Testing with random large numbers
        System.out.println("\nTesting with random numbers:");
        BigInteger randomX = new BigInteger("987654321987654321");
        BigInteger randomY = new BigInteger("123456789");
        BigInteger[] randomResult = div(randomX, randomY);

        System.out.println(randomX + " = " + randomResult[0] + "*" + randomY + " + " + randomResult[1]);
    }
}
