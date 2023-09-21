package customer.application;

import java.util.Map;

/**
 * Customer entity class.
 */
public class Customer {

    /**
     * Customer code.
     */
    private int code;

    /**
     * Customer name.
     */
    private String name;

    /**
     * Customer tax identification number (TIN).
     *
     * Only Spanish NIF, NIE or CIF will be valid.
     */
    private String tin;

    /**
     * Customer address.
     */
    private String address;

    /**
     * Customer city.
     */
    private String city;

    /**
     * Customer province.
     */
    private String province;

    /**
     * Customer ZIP code.
     *
     * Only Spanish ZIP codes will be valid.
     */
    private String zipcode;

    /**
     * Customer IBAN.
     *
     * Only Spanish IBAN codes will be valid.
     */
    private String iban;

    /**
     * Whether the customer is farmer or not.
     */
    private boolean isFarmer;

    /**
     * Whether the customer is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code Customer code.
     * @param name Customer name.
     * @param tin Customer TIN.
     * @param address Customer address.
     * @param city Customer city.
     * @param province Customer province.
     * @param zipcode Customer ZIP code.
     * @param iban Customer IBAN.
     * @param isFarmer Whether the customer is farmer or not.
     * @param isDeleted Whether the customer is deleted or not.
     */
    private Customer(int code, String name, String tin, String address, String city, String province, String zipcode, String iban, boolean isFarmer, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.tin = tin;
        this.address = address;
        this.city = city;
        this.province = province;
        this.zipcode = zipcode;
        this.iban = iban;
        this.isFarmer = isFarmer;
        this.isDeleted = isDeleted;
    }

    /**
     * Retrieve the customer code.
     *
     * @return The customer code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the customer name.
     *
     * @return The customer name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the customer TIN.
     *
     * @return The customer TIN.
     */
    public String getTin() {
        return this.tin;
    }

    /**
     * Retrieve the customer address.
     *
     * @return The customer address.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Retrieve the customer city.
     *
     * @return The customer city.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Retrieve the customer province.
     *
     * @return The customer province.
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * Retrieve the customer ZIP code.
     *
     * @return The customer ZIP code.
     */
    public String getZipCode() {
        return this.zipcode;
    }

    /**
     * Retrieve the customer IBAN.
     *
     * @return The customer IBAN.
     */
    public String getIban() {
        return this.iban;
    }

    /**
     * Whether the customer is farmer or not.
     *
     * @return true if the customer is farmer, otherwise false.
     */
    public boolean isFarmer() {
        return this.isFarmer;
    }

    /**
     * Whether the customer is deleted from the system or not.
     *
     * @return true if the customer is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Update the deletion state for the customer.
     *
     * @param isDeleted Whether the customer is deleted from the system or not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s (%d)", this.name, this.code);
    }

    /**
     * Create a customer given its attribute values.
     *
     * @param attributes Map containing the value for each customer attribute.
     * @return The created customer.
     */
    public static Customer from(Map<CustomerAttribute, Object> attributes) {
        int unregisteredCustomerCode = 0;

        int code = (int) attributes.getOrDefault(CustomerAttribute.CODE, unregisteredCustomerCode);
        String name = (String) attributes.getOrDefault(CustomerAttribute.NAME, "");
        String tin = (String) attributes.getOrDefault(CustomerAttribute.TIN, "");
        String address = (String) attributes.getOrDefault(CustomerAttribute.ADDRESS, null);
        String city = (String) attributes.getOrDefault(CustomerAttribute.CITY, null);
        String province = (String) attributes.getOrDefault(CustomerAttribute.PROVINCE, null);
        String zipcode = (String) attributes.getOrDefault(CustomerAttribute.ZIPCODE, null);
        String iban = (String) attributes.getOrDefault(CustomerAttribute.IBAN, null);
        boolean isFarmer = (boolean) attributes.getOrDefault(CustomerAttribute.ISFARMER, false);
        boolean isDeleted = (boolean) attributes.getOrDefault(CustomerAttribute.ISDELETED, false);

        return new Customer(code, name, tin, address, city, province, zipcode, iban, isFarmer, isDeleted);
    }

}
