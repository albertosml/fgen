package password.application.usecases;

import java.nio.charset.StandardCharsets;
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
 * Establish password use case.
 */
public class EstablishPassword {

    /**
     * @see PasswordRepository
     */
    private PasswordRepository passwordRepository;

    /**
     * Constructor.
     *
     * @param passwordRepository Password repository.
     */
    public EstablishPassword(PasswordRepository passwordRepository) {
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
     * Establish the password.
     *
     * @param password The password.
     * @return Whether the password has been established or not.
     */
    public boolean execute(char[] password) {
        String username = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.USERNAME);

        String encryptedPassword;
        try {
            encryptedPassword = this.encrypt(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            String className = EstablishPassword.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Password not established because of the next error:", ex);

            return false;
        }

        Password pass = Password.from(username, encryptedPassword);
        return passwordRepository.set(pass);
    }

}
