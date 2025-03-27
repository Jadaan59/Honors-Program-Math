import java.math.BigInteger;
import java.security.SecureRandom;

public class SamplePrime {
    public static Object[] samplePrime() {
        int N = 512; // Bit size of the random number
        int k = 40;  // Number of trials for Miller-Rabin
        SecureRandom random = new SecureRandom();
        BigInteger randomNum;
        int trials = 0;

        while (true) {
            // Generate a random number of N bits (512 bits in this case)
            randomNum = new BigInteger(N, random);

            // Skip small numbers
            if (randomNum.compareTo(BigInteger.TWO) < 0) continue;

            // Increment trial count
            trials++;
            if (Miller_Rabin.millerRabin(randomNum, k)) {
                return new Object[]{randomNum, trials};
            }
        }
    }

    public static void main(String[] args) {
        // Repeat the sampling 10 times
        for (int i = 0; i < 1; i++) {
            Object[] result = samplePrime();
            BigInteger prime = (BigInteger) result[0];
            int trials = (int) result[1];

            System.out.println("Prime Number " + (i + 1) + ":");
            System.out.println("Generated prime: " + prime);
            System.out.println("Number of trials: " + trials);
            System.out.println();  // Adds a blank line between outputs for readability
        }
    }
}
