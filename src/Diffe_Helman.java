import java.math.BigInteger;
import java.security.SecureRandom;

 class DiffieHellman {

    public static void main(String[] args) {
        boolean isPrime = false;
        BigInteger primeGroup = null;
        BigInteger prime = null;

        // Find a prime p such that p = 2 * q + 1
        while (!isPrime) {
            Object[] sample = SamplePrime.samplePrime(); // Generate a random prime q
            prime = (BigInteger) sample[0];
            primeGroup = prime.multiply(BigInteger.TWO).add(BigInteger.ONE); // Calculate p = 2 * q + 1

            // Check if p is prime
            if (Miller_Rabin.millerRabin(primeGroup, 40)) { // 40 trials for accuracy
                System.out.println("Found a safe prime p! The number is: " + primeGroup);
                isPrime = true;
            }
        }

        // Find a generator for the group
        BigInteger generator = findGenerator(primeGroup, prime);
        System.out.println("Generator for the group: " + generator);

        SecureRandom random = new SecureRandom();

        // Generate Alice's private number (a)
        BigInteger alicePrivate = new BigInteger(primeGroup.bitLength(), random)
                .mod(primeGroup.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        // Compute Alice's public message: g^a mod p
        BigInteger aliceMessage = generator.modPow(alicePrivate, primeGroup);

        // Generate Bob's private number (b)
        BigInteger bobPrivate = new BigInteger(primeGroup.bitLength(), random)
                .mod(primeGroup.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        // Compute Bob's public message: g^b mod p
        BigInteger bobMessage = generator.modPow(bobPrivate, primeGroup);

        // Compute the shared secret
        BigInteger sharedKeyByAlice = bobMessage.modPow(alicePrivate, primeGroup); // (g^b)^a mod p
        BigInteger sharedKeyByBob = aliceMessage.modPow(bobPrivate, primeGroup); // (g^a)^b mod p

        // Print the shared keys
        System.out.println("The shared key according to Alice is: " + sharedKeyByAlice);
        System.out.println("The shared key according to Bob is: " + sharedKeyByBob);

        // Validate the shared keys
        if (sharedKeyByAlice.equals(sharedKeyByBob)) {
            System.out.println("The shared keys match!");
        } else {
            System.out.println("The shared keys do not match. Check the implementation.");
        }
    }

    /**
     * Finds a generator for the cyclic group of order p.
     *
     * @param p Safe prime (p = 2 * q + 1)
     * @param q Prime factor of p - 1
     * @return A generator of the group
     */
    private static BigInteger findGenerator(BigInteger p, BigInteger q) {
        SecureRandom random = new SecureRandom();
        BigInteger generator;

        while (true) {
            // Generate a random number between 1 and p-1
            generator = new BigInteger(p.bitLength(), random).mod(p.subtract(BigInteger.ONE)).add(BigInteger.ONE);

            // Check if g^q mod p == p-1
            BigInteger gPowQModP = generator.modPow(q, p);
            if (gPowQModP.equals(p.subtract(BigInteger.ONE))) {
                return generator; // Valid generator found
            }
        }
    }
}
