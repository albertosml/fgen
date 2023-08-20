package password.application.usecases;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import password.application.Password;
import password.persistence.PasswordRepository;
import shared.application.configuration.ApplicationConfiguration;
import shared.application.configuration.ConfigurationVariable;

/**
 * Check password use case.
 */
public class CheckPassword {

    /**
     * @see PasswordRepository
     */
    private PasswordRepository passwordRepository;

    /**
     * Constructor.
     *
     * @param passwordRepository Password repository.
     */
    public CheckPassword(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    /**
     * Encrypt password with the configured salt.
     *
     * The algorithm used for the encryption is PBKDF2WithHmacSHA512.
     *
     * @param password The password.
     * @return The encrypted password.
     */
    private String encrypt(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String saltText = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.PASSWORD_SALT);
        byte[] salt = saltText.getBytes();
        int iterations = 10000;
        int keyLength = 128;

        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Check if the introduced password is the correct one.
     *
     * @param password The password.
     * @return Whether the password matches with the set one.
     */
    public boolean execute(char[] password) {
        String encryptedPassword;
        try {
            encryptedPassword = this.encrypt(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            String className = CheckPassword.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Password not established because of the next error:", ex);

            return false;
        }

        String username = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.USERNAME);
        Password pass = passwordRepository.get(username);

        return pass != null && pass.getPassword().equals(encryptedPassword);
    }

}
