package password.persistence;

import password.application.Password;
import shared.persistence.Repository;

/**
 * Represents the repository model for the password entity.
 */
public interface PasswordRepository extends Repository {

    /**
     * Obtain the password for the given username.
     *
     * @param username The user name.
     * @return The password entity for the given user name.
     */
    public Password get(String username);

    /**
     * Set the password.
     *
     * @param password The password entity.
     * @return Whether the password has been established or not.
     */
    public boolean set(Password password);

}
