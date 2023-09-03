package shared.application.configuration;

/**
 * Represents all the available configuration variables.
 */
public enum ConfigurationVariable {
    /**
     * Application version.
     */
    VERSION,
    /**
     * Application name.
     */
    NAME,
    /**
     * Project URL.
     */
    PROJECT_URL,
    /**
     * Company commission percentage.
     */
    COMPANY_COMMISSION_PERCENTAGE,
    /**
     * Individual commission percentage.
     */
    INDIVIDUAL_COMMISSION_PERCENTAGE,
    /**
     * Password salt.
     */
    PASSWORD_SALT,
    /**
     * User name.
     */
    USERNAME,
    /**
     * Number of retries to introduce the password.
     */
    NUM_PASSWORD_RETRIES,
    /**
     * The maximum number of invoice items (delivery notes) that we can set on a
     * single invoice page.
     */
    INVOICE_ITEMS_PER_PAGE
}
