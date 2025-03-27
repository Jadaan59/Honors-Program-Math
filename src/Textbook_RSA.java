import java.math.BigInteger;

public class Textbook_RSA {

    public static BigInteger[][] Gen1() {
        // Sample 2 primes
        BigInteger p = (BigInteger) SamplePrime.samplePrime()[0];
        BigInteger q = (BigInteger) SamplePrime.samplePrime()[0];

        // Compute N = p * q
        BigInteger N = Karatsuba.mult(p, q);

        // Compute phi of N
        BigInteger phiN = Karatsuba.mult(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));

        // Choose e such that 1 < e < phi(N) and gcd(e, phi(N)) = 1
        BigInteger e = new BigInteger("3");

        // Ensure gcd(e, phi(N)) = 1
        while (phiN.gcd(e).compareTo(BigInteger.ONE) != 0) {
            e = e.add(BigInteger.TWO);  // Try the next odd number
        }

        // Find inverse of e
        BigInteger d = e.modInverse(phiN);

        // Secret key
        BigInteger[] sk = {N, d, p, q};
        // Public key
        BigInteger[] pk = {N, e};

        return new BigInteger[][]{sk, pk};
    }

    public static BigInteger Encryption(BigInteger[] pk, BigInteger m) {
        // Ensure message m is smaller than N
        return m.modPow(pk[1], pk[0]);
    }

    public static BigInteger Decryption(BigInteger c, BigInteger[] sk, BigInteger p, BigInteger q) {
        // Compute the modPow on p and q
        BigInteger cmodp = c.modPow(sk[1], p);
        BigInteger cmodq = c.modPow(sk[1], q);

        // Apply the Chinese Remainder Theorem (CRT)
        BigInteger[] GCD = ExtendedEuclid.extendedEuclid(p, q);

        // Compute x and y
        BigInteger x1 = Karatsuba.mult(cmodp, Karatsuba.mult(q, GCD[2])); // x1 = cmodp * q * inv(p) mod N
        BigInteger x2 = x1.mod(sk[0]); // Modulo N

        BigInteger y1 = Karatsuba.mult(cmodq, Karatsuba.mult(p, GCD[1])); // y1 = cmodq * p * inv(q) mod N
        BigInteger y2 = y1.mod(sk[0]); // Modulo N

        // Combine the results to get the decrypted message
        BigInteger message = x2.add(y2);
        return message.mod(sk[0]);
    }

    public static void main(String[] args) {
        // Run the Gen1 method to generate the public and private keys
        BigInteger[][] keys = Gen1();
        BigInteger[] publicKey = keys[1];
        BigInteger[] secretKey = keys[0];

        // Sample a random message m (smaller than N)
        BigInteger m = new BigInteger(publicKey[0].bitLength(), new java.security.SecureRandom());

        // Ensure that m is less than N
        BigInteger N = publicKey[0];
        m = m.mod(N);  // Ensure message is smaller than N

        // Encrypt the message
        BigInteger encryptedMessage = Encryption(publicKey, m);

        // Decrypt the message
        BigInteger decryptedMessage = Decryption(encryptedMessage, secretKey, secretKey[2], secretKey[3]);

        // Output the results
        System.out.println("Original message: " + m);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);

        // Verify that the decrypted message matches the original
        if (m.equals(decryptedMessage)) {
            System.out.println("Decryption was successful and correct!");
        } else {
            System.out.println("Decryption failed: messages do not match.");
        }
    }
}
