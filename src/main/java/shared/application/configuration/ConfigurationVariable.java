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
    INVOICE_ITEMS_PER_PAGE,
    /**
     * Indicate the association between the customer identified by its TIN and
     * the template code for delivery notes.
     *
     * Note that "farmer" key will set the default delivery note template for
     * farmer customers and "trader" will do the same for trader customers.
     */
    DELIVERY_NOTE_TEMPLATE_BY_CUSTOMER,
    /**
     * Indicate the association between the customer identified by its TIN and
     * the template code for invoices.
     *
     * Note that "farmer" key will set the default invoice template for farmer
     * customers and "trader" will do the same for trader customers.
     */
    INVOICE_TEMPLATE_BY_CUSTOMER,
}
