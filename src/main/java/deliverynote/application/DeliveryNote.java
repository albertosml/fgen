package deliverynote.application;

import container.application.Box;
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
     * The farmer customer.
     */
    private Customer farmer;

    /**
     * The trader customer.
     */
    private Customer trader;

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
     * @param farmer The delivery note farmer customer.
     * @param trader The delivery note farmer trader.
     * @param product The delivery note product.
     * @param template The delivery note template.
     * @param pallet The delivery note pallet.
     * @param numPallets The delivery note total pallets.
     * @param weighings The delivery note weighings.
     */
    private DeliveryNote(int code, Date date, Customer farmer, Customer trader, Product product, Template template, Pallet pallet, int numPallets, ArrayList<Weighing> weighings) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.farmer = farmer;
        this.trader = trader;
        this.product = product;
        this.template = template;
        this.pallet = pallet;
        this.numPallets = numPallets;
        this.weighings = weighings;
    }

    /**
     * Calculate the net weight for all weighings of a delivery note.
     *
     * @return The global net weight.
     */
    public int calculateNetWeight() {
        int totalNetWeight = 0;

        for (Weighing weighing : this.weighings) {
            Box box = weighing.getBox();
            int qty = weighing.getQty();
            int grossWeight = weighing.getWeight();

            int boxWeight = (int) Math.round(box.getWeight() * qty);
            int netWeight = (int) (grossWeight - boxWeight - pallet.getWeight());

            totalNetWeight += netWeight;
        }

        return totalNetWeight;
    }

    /**
     * Calculate the total number of boxes.
     *
     * @return The number of boxes included on the delivery note.
     */
    public int calculateTotalBoxes() {
        int numBoxes = 0;

        for (Weighing weighing : this.weighings) {
            numBoxes += weighing.getQty();
        }

        return numBoxes;
    }

    /**
     * Calculate the total number of pallets.
     *
     * @return The number of pallets included on the delivery note.
     */
    public int calculateTotalPallets() {
        return this.weighings.size();
    }

    /**
     * Calculate the total weight per box.
     *
     * @return The quotient between the total net weight and the total number of
     * boxes.
     */
    public double calculateTotalWeightPerBox() {
        double totalWeightPerBox = (double) this.calculateNetWeight() / this.calculateTotalBoxes();

        // Round to 2 decimals.
        return Math.round(totalWeightPerBox * 100.0) / 100.0;
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
     * Retrieve the delivery note farmer customer.
     *
     * @return The delivery note farmer customer.
     */
    public Customer getFarmer() {
        return this.farmer;
    }

    /**
     * Retrieve the delivery note trader customer.
     *
     * @return The delivery note trader customer.
     */
    public Customer getTrader() {
        return this.trader;
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
        Customer farmer = (Customer) attributes.get(DeliveryNoteAttribute.FARMER);
        Customer trader = (Customer) attributes.get(DeliveryNoteAttribute.TRADER);
        Product product = (Product) attributes.get(DeliveryNoteAttribute.PRODUCT);
        Template template = (Template) attributes.get(DeliveryNoteAttribute.TEMPLATE);
        Pallet pallet = (Pallet) attributes.get(DeliveryNoteAttribute.PALLET);
        int numPallets = (int) attributes.getOrDefault(DeliveryNoteAttribute.NUM_PALLETS, 0);
        ArrayList<Weighing> weighings = (ArrayList<Weighing>) attributes.get(DeliveryNoteAttribute.WEIGHINGS);

        return new DeliveryNote(code, date, farmer, trader, product, template, pallet, numPallets, weighings);
    }

}
