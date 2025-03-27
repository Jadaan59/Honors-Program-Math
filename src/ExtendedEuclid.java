import java.math.BigInteger;
import java.util.Random;

public class ExtendedEuclid {

    public static BigInteger[] extendedEuclid(BigInteger x, BigInteger y) {
        // Base case: if y == 0
        if (y.equals(BigInteger.ZERO)) {
            return new BigInteger[]{x, BigInteger.ONE, BigInteger.ZERO};
        }

        // Divide x by y to get quotient and remainder
        BigInteger[] div = Div.div(x, y);
        BigInteger q = div[0];
        BigInteger r = div[1];

        // Recursive call to extendedEuclid for gcd and coefficients
        BigInteger[] gcd = extendedEuclid(y, r);
        BigInteger d = gcd[0];
        BigInteger x1 = gcd[1];
        BigInteger y1 = gcd[2];

        // Update the coefficients
        BigInteger xyterm = Karatsuba.mult(q, y1);

        return new BigInteger[]{d, y1, x1.subtract(xyterm)};
    }

    /* Generate a random number between 1 and 999331 and calculate its inverse. */
    public static void main(String[] args) {
        BigInteger x = new BigInteger("999331");
        Random rand = new Random();
        BigInteger random = new BigInteger(x.bitLength(), rand).mod(x).add(BigInteger.ONE); // Random number in range (1, x)

        BigInteger[] result = extendedEuclid(random, x);

        if (result[0].compareTo(BigInteger.ONE) > 0) {
            System.out.println("No inverse exists for " + random + " modulo " + x);
        } else {
            BigInteger inverse = result[1];
            if (inverse.compareTo(BigInteger.ZERO) < 0) {
                inverse = inverse.add(x);
            }
            System.out.println("The inverse of " + random + " modulo " + x + " is " + inverse);
        }

        // Calculate the inverse of 296 mod 999331
        BigInteger num = new BigInteger("296");
        BigInteger[] test = extendedEuclid(num, x);
        BigInteger inverseOfGivenNumber = test[1];
        if (inverseOfGivenNumber.compareTo(BigInteger.ZERO) < 0) {
            inverseOfGivenNumber = inverseOfGivenNumber.add(x);
        }
        System.out.println("The inverse of " + num + " modulo " + x + " is " + inverseOfGivenNumber);
    }
}
