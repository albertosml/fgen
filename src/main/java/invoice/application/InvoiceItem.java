package invoice.application;

import java.util.Map;

/**
 * Invoice item element.
 */
public class InvoiceItem {

    /**
     * The quantity.
     */
    private int qty;

    /**
     * The description.
     */
    private String description;

    /**
     * The price.
     */
    private double price;

    /**
     * Constructor.
     *
     * @param qty The invoice item quantity.
     * @param description The invoice item description.
     * @param price The invoice item price.
     */
    private InvoiceItem(int qty, String description, double price) {
        this.qty = qty;
        this.description = description;
        this.price = price;
    }

    /**
     * Retrieve the invoice item quantity.
     *
     * @return The invoice item quantity.
     */
    public int getQty() {
        return this.qty;
    }

    /**
     * Retrieve the invoice item description.
     *
     * @return The invoice item description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieve the invoice item price.
     *
     * @return The invoice item price.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Create an invoice item given its attribute values.
     *
     * @param attributes Map containing the value for each invoice item
     * attribute.
     * @return The created invoice item.
     */
    public static InvoiceItem from(Map<InvoiceItemAttribute, Object> attributes) {
        int qty = (int) attributes.getOrDefault(InvoiceItemAttribute.QTY, 0);
        String description = (String) attributes.get(InvoiceItemAttribute.DESCRIPTION);
        double price = (double) attributes.getOrDefault(InvoiceItemAttribute.PRICE, 0);

        return new InvoiceItem(qty, description, price);
    }
}
