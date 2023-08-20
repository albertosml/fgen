package password.persistence;

import password.application.Password;
import shared.persistence.Repository;

/**
 * Represents the repository model for the password entity.
 */
public interface PasswordRepository extends Repository {

    /**
     * Set the password.
     *
     * @param password The password entity.
     */
    public boolean set(Password password);

}
