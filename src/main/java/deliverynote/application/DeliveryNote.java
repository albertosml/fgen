package deliverynote.application;

import customer.application.Customer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import product.application.Product;
import template.application.Template;

/**
 * Delivery note element.
 */
public class DeliveryNote {

    /**
     * The code.
     */
    private int code;

    /**
     * The date.
     */
    private Date date;

    /**
     * The customer.
     */
    private Customer customer;

    /**
     * The product.
     */
    private Product product;

    /**
     * The template.
     */
    private Template template;

    /**
     * The total weight.
     */
    private double weight;

    /**
     * The delivery note items.
     */
    private ArrayList<DeliveryNoteItem> items;

    /**
     * Constructor.
     *
     * @param code The delivery note code.
     * @param date The delivery note date. If it is null, it will be set to the
     * current date time.
     * @param customer The delivery note customer.
     * @param product The delivery note product.
     * @param template The delivery note template.
     * @param weight The delivery note weight.
     * @param items The delivery note items.
     */
    private DeliveryNote(int code, Date date, Customer customer, Product product, Template template, double weight, ArrayList<DeliveryNoteItem> items) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.customer = customer;
        this.product = product;
        this.template = template;
        this.weight = weight;
        this.items = items;
    }

    /**
     * Retrieve the delivery note code.
     *
     * @return The delivery note code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the delivery note date.
     *
     * @return The delivery note date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Retrieve the delivery note customer.
     *
     * @return The delivery note customer.
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Retrieve the delivery note product.
     *
     * @return The delivery note product.
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Retrieve the delivery note template.
     *
     * @return The delivery note template.
     */
    public Template getTemplate() {
        return this.template;
    }

    /**
     * Retrieve the delivery note total weight.
     *
     * @return The delivery note total weight.
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Retrieve the delivery note items.
     *
     * @return The delivery note items.
     */
    public ArrayList<DeliveryNoteItem> getItems() {
        return this.items;
    }

    /**
     * Create a delivery note given its attribute values.
     *
     * @param attributes Map containing the value for each delivery note
     * attribute.
     * @return The created delivery note.
     */
    public static DeliveryNote from(Map<DeliveryNoteAttribute, Object> attributes) {
        int code = (int) attributes.get(DeliveryNoteAttribute.CODE);
        Date date = (Date) attributes.getOrDefault(DeliveryNoteAttribute.DATE, null);
        Customer customer = (Customer) attributes.get(DeliveryNoteAttribute.CUSTOMER);
        Product product = (Product) attributes.get(DeliveryNoteAttribute.PRODUCT);
        Template template = (Template) attributes.get(DeliveryNoteAttribute.TEMPLATE);
        double weight = (double) attributes.getOrDefault(DeliveryNoteAttribute.WEIGHT, 0);
        ArrayList<DeliveryNoteItem> items = (ArrayList<DeliveryNoteItem>) attributes.get(DeliveryNoteAttribute.ITEMS);

        return new DeliveryNote(code, date, customer, product, template, weight, items);
    }

}
