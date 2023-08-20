package password.application;

/**
 * Represents the data of the password entity.
 */
public class Password {

    /**
     * The user name.
     */
    private String username;

    /**
     * The password.
     */
    private String password;

    /**
     * Constructor.
     *
     * @param code The user name.
     * @param date The password.
     */
    private Password(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieve the user name.
     *
     * @return The user name.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieve the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Create a password given the user name and the password.
     *
     * @param username The user name.
     * @param password The password.
     * @return The created password object.
     */
    public static Password from(String username, String password) {
        return new Password(username, password);
    }

}
