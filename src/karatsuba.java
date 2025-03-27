import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;

class Karatsuba {

    public static BigInteger mult(BigInteger x, BigInteger y) {
        // Base case for numbers that fit in a single "bit" (small numbers)
        if (x.compareTo(BigInteger.TWO) < 0 || y.compareTo(BigInteger.TWO) < 0) {
            return x.multiply(y);
        }

        // Determine the maximum number of bits in x and y
        int n = Math.max(x.bitLength(), y.bitLength());
        int m = n / 2;

        // Split x and y into high (a, c) and low (b, d) halves
        BigInteger a = x.shiftRight(m); // Top half of x
        BigInteger b = x.subtract(a.shiftLeft(m)); // Bottom half of x
        BigInteger c = y.shiftRight(m); // Top half of y
        BigInteger d = y.subtract(c.shiftLeft(m)); // Bottom half of y

        // Karatsuba multiplication
        BigInteger A1 = mult(a, c);
        BigInteger A2 = mult(b, d);
        BigInteger A3 = mult(a.add(b), c.add(d));

        // Combine results
        return A1.shiftLeft(2 * m).add(A3.subtract(A1).subtract(A2).shiftLeft(m)).add(A2);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the first integer (x): ");
        BigInteger x = scan.nextBigInteger();
        System.out.print("Enter the second integer (y): ");
        BigInteger y = scan.nextBigInteger();
        scan.close();

        // Compute and display the result
        BigInteger result = mult(x, y);
        System.out.println("The result is " + result);

        // Testing with random large numbers
        System.out.println("\nTesting with random numbers:");
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            BigInteger randomX = new BigInteger(512, random); // 512-bit random number
            BigInteger randomY = new BigInteger(512, random); // 512-bit random number
            BigInteger testResult = mult(randomX, randomY);
            System.out.println("Test " + (i + 1) + ":");
            System.out.println("x = " + randomX);
            System.out.println("y = " + randomY);
            System.out.println("x * y = " + testResult);
        }
    }
}
