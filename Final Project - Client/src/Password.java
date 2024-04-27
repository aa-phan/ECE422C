import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Password{

    public static void main(String[] args) {
        String password = "your_password_here";

        // Generate a random salt
        byte[] salt = generateSalt();

        // Hash and salt the password
        String hashedPassword = hashAndSaltPassword(password, salt);

        // Output the salt and hashed password
        System.out.println("Salt: " + Base64.getEncoder().encodeToString(salt));
        System.out.println("Hashed Password: " + hashedPassword);
    }

    // Method to generate a random salt
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    // Method to hash and salt the password
    private static String hashAndSaltPassword(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(salt);
            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            // Combine salt and hashed password
            byte[] combined = new byte[hashedBytes.length + salt.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedBytes, 0, combined, salt.length, hashedBytes.length);

            // Convert to base64
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
