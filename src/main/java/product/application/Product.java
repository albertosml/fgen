package product.application;

import java.util.Map;

/**
 * Product entity class.
 */
public class Product {

    /**
     * Product code.
     */
    private String code;

    /**
     * Product name.
     */
    private String name;

    /**
     * Product price.
     *
     * It defines the price per single quantity unit.
     */
    private double price;

    /**
     * Whether the product is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code Product code.
     * @param name Product name.
     * @param price Product price.
     * @param isDeleted Whether the customer is deleted or not.
     */
    private Product(String code, String name, double price, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.isDeleted = isDeleted;
    }

    /**
     * Retrieve the product code.
     *
     * @return The product code.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Retrieve the product name.
     *
     * @return The product name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the product price.
     *
     * @return The product price.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Whether the product is deleted from the system or not.
     *
     * @return true if the product is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Update the deletion state for the product.
     *
     * @param isDeleted Whether the product is deleted from the system or not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Create a product given its attribute values.
     *
     * @param attributes Map containing the value for each product attribute.
     * @return The created product.
     */
    public static Product from(Map<ProductAttribute, Object> attributes) {
        int unregisteredProductCode = 0;

        String code = (String) attributes.getOrDefault(ProductAttribute.CODE, unregisteredProductCode);
        String name = (String) attributes.getOrDefault(ProductAttribute.NAME, "");
        Object priceValue = attributes.getOrDefault(ProductAttribute.PRICE, "0");
        double price = Double.parseDouble(priceValue.toString());
        boolean isDeleted = (boolean) attributes.getOrDefault(ProductAttribute.ISDELETED, false);

        return new Product(code, name, price, isDeleted);
    }

}
