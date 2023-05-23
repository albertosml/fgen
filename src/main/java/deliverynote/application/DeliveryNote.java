package deliverynote.application;

import container.application.Box;
import container.application.Container;
import container.application.Pallet;
import customer.application.Customer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import product.application.Product;
import template.application.Template;
import weighing.application.Weighing;

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
     * The pallet.
     */
    private Pallet pallet;

    /**
     * The total number of pallets.
     */
    private int numPallets;

    /**
     * The weighings.
     */
    private ArrayList<Weighing> weighings;

    /**
     * Constructor.
     *
     * @param code The delivery note code.
     * @param date The delivery note date. If it is null, it will be set to the
     * current date time.
     * @param customer The delivery note customer.
     * @param product The delivery note product.
     * @param template The delivery note template.
     * @param pallet The delivery note pallet.
     * @param numPallets The delivery note total pallets.
     * @param weighings The delivery note weighings.
     */
    private DeliveryNote(int code, Date date, Customer customer, Product product, Template template, Pallet pallet, int numPallets, ArrayList<Weighing> weighings) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.customer = customer;
        this.product = product;
        this.template = template;
        this.pallet = pallet;
        this.numPallets = numPallets;
        this.weighings = weighings;
    }

    public double calculateNetWeight() {
        double totalNetWeight = 0;

        for (Weighing weighing : this.weighings) {
            Box box = weighing.getBox();
            int qty = weighing.getQty();
            double grossWeight = weighing.getWeight();

            double boxWeight = box.getWeight() * qty;
            double netWeight = grossWeight - boxWeight;

            totalNetWeight += netWeight;
        }

        return totalNetWeight;
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
     * Retrieve the delivery note pallet.
     *
     * @return The delivery note pallet.
     */
    public Pallet getPallet() {
        return this.pallet;
    }

    /**
     * Retrieve the delivery note number of pallets.
     *
     * @return The delivery note number of pallets..
     */
    public int getNumPallets() {
        return this.numPallets;
    }

    /**
     * Retrieve the delivery note weighings.
     *
     * @return The delivery note weighings.
     */
    public ArrayList<Weighing> getWeighings() {
        return this.weighings;
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
        Pallet pallet = (Pallet) attributes.get(DeliveryNoteAttribute.PALLET);
        int numPallets = (int) attributes.getOrDefault(DeliveryNoteAttribute.NUM_PALLETS, 0);
        ArrayList<Weighing> weighings = (ArrayList<Weighing>) attributes.get(DeliveryNoteAttribute.WEIGHINGS);

        return new DeliveryNote(code, date, customer, product, template, pallet, numPallets, weighings);
    }

}
