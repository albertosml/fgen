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
    private int zipcode;

    /**
     * Customer IBAN.
     *
     * Only Spanish IBAN codes will be valid.
     */
    private String iban;

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
     * @param isDeleted Whether the customer is deleted or not.
     */
    private Customer(int code, String name, String tin, String address, String city, String province, int zipcode, String iban, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.tin = tin;
        this.address = address;
        this.city = city;
        this.province = province;
        this.zipcode = zipcode;
        this.iban = iban;
        this.isDeleted = isDeleted;
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
     * Retrieve the customer ZIP code.
     *
     * @return The customer ZIP code.
     */
    public int getZipCode() {
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
        int zipcode = (int) attributes.getOrDefault(CustomerAttribute.ZIPCODE, null);
        String iban = (String) attributes.getOrDefault(CustomerAttribute.IBAN, null);
        boolean isDeleted = (boolean) attributes.getOrDefault(CustomerAttribute.ISDELETED, false);

        return new Customer(code, name, tin, address, city, province, zipcode, iban, isDeleted);
    }
}
