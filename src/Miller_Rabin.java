import java.math.BigInteger;
import java.security.SecureRandom;

public class Miller_Rabin {

    public static boolean millerRabin(BigInteger N, int trials) {
        if (N.compareTo(BigInteger.ONE) <= 0) return false;
        if (N.equals(BigInteger.TWO) || N.equals(BigInteger.valueOf(3))) return true;
        if (N.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        // Decompose N-1 as (2^r) * d where d is odd
        BigInteger d = N.subtract(BigInteger.ONE);
        int r = 0;
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            r++;
        }

        SecureRandom random = new SecureRandom();

        // Test with multiple random bases to reduce error
        for (int i = 0; i < trials; i++) {
            BigInteger a;
            do {
                a = new BigInteger(N.bitLength(), random);
            } while (a.compareTo(BigInteger.TWO) < 0 || a.compareTo(N.subtract(BigInteger.TWO)) > 0);

            // Compute a^d % N using modular exponentiation
            BigInteger x = a.modPow(d, N);

            if (x.equals(BigInteger.ONE) || x.equals(N.subtract(BigInteger.ONE))) {
                continue;  // This base gives a good result, continue testing with another base
            }

            boolean isComposite = true;
            for (int j = 0; j < r - 1; j++) {
                x = x.modPow(BigInteger.TWO, N);
                if (x.equals(N.subtract(BigInteger.ONE))) {
                    isComposite = false;
                    break;
                }
            }

            if (isComposite) {
                return false;  // If any trial shows composite, return false
            }
        }

        // If all trials indicate probable prime
        return true;
    }


    public static void main(String args[]) {
        BigInteger[] testNumbers = {
                BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5),
                BigInteger.valueOf(9), BigInteger.valueOf(11), BigInteger.valueOf(15), BigInteger.valueOf(17),
                BigInteger.valueOf(19), BigInteger.valueOf(23), BigInteger.valueOf(29), BigInteger.valueOf(31),
                BigInteger.valueOf(37), BigInteger.valueOf(40), BigInteger.valueOf(97), BigInteger.valueOf(101),
                BigInteger.valueOf(131), BigInteger.valueOf(211), BigInteger.valueOf(1001), BigInteger.valueOf(1024),
                BigInteger.valueOf(2047), BigInteger.valueOf(7919), BigInteger.valueOf(10007)
        };

        // Iterate through each number and test for primality
        System.out.println("Miller-Rabin Primality Test Results:");
        for (BigInteger num : testNumbers) {
            System.out.println("Number: " + num + " -> " + Miller_Rabin.millerRabin(num,10));
        }
    }
}

