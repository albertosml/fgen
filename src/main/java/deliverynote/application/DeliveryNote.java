package deliverynote.application;

import customer.application.Customer;
import java.util.ArrayList;
import java.util.Map;
import product.application.Product;
import template.application.Template;

/**
 * Delivery note element.
 */
public class DeliveryNote {

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
     * @param customer The delivery note customer.
     * @param product The delivery note product.
     * @param template The delivery note template.
     * @param weight The delivery note weight.
     * @param items The delivery note items.
     */
    private DeliveryNote(Customer customer, Product product, Template template, double weight, ArrayList<DeliveryNoteItem> items) {
        this.customer = customer;
        this.product = product;
        this.template = template;
        this.weight = weight;
        this.items = items;
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
        Customer customer = (Customer) attributes.get(DeliveryNoteAttribute.CUSTOMER);
        Product product = (Product) attributes.get(DeliveryNoteAttribute.PRODUCT);
        Template template = (Template) attributes.get(DeliveryNoteAttribute.TEMPLATE);
        double weight = (double) attributes.getOrDefault(DeliveryNoteAttribute.WEIGHT, 0);
        ArrayList<DeliveryNoteItem> items = (ArrayList<DeliveryNoteItem>) attributes.get(DeliveryNoteAttribute.ITEMS);

        return new DeliveryNote(customer, product, template, weight, items);
    }

}
