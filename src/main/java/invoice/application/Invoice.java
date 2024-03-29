package invoice.application;

import customer.application.Customer;
import deliverynote.application.DeliveryNoteData;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Represents the data of the invoice entity.
 */
public class Invoice {

    /**
     * The invoice code.
     */
    private int code;

    /**
     * The invoice date.
     */
    private Date date;

    /**
     * The invoice delivery notes data.
     */
    private ArrayList<DeliveryNoteData> deliveryNotes;

    /**
     * The invoice start period date.
     */
    private Date startPeriod;

    /**
     * The invoice end period date.
     */
    private Date endPeriod;

    /**
     * The customer, which can be either a farmer or a trader.
     */
    private Customer customer;

    /**
     * A PDF file containing the invoice file.
     */
    private File file;

    /**
     * The invoice total amount.
     */
    private double totalAmount;

    /**
     * The invoice total weight.
     */
    private int totalWeight;

    /**
     * Whether the invoice is closed or not.
     */
    private boolean isClosed;

    /**
     * Whether the invoice is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code The invoice code.
     * @param date The invoice date.
     * @param deliveryNotes The invoice delivery notes.
     * @param startPeriod The invoice start period date.
     * @param endPeriod The invoice end period date.
     * @param customer The invoice customer.
     * @param file The invoice file.
     * @param total The invoice total.
     * @param isClosed Whether the invoice is closed or not.
     * @param isDeleted Whether the invoice is deleted or not.
     */
    private Invoice(int code, Date date, ArrayList<DeliveryNoteData> deliveryNotes, Date startPeriod, Date endPeriod, Customer customer, File file, double totalAmount, int totalWeight, boolean isClosed, boolean isDeleted) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.deliveryNotes = deliveryNotes;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.customer = customer;
        this.file = file;
        this.totalAmount = totalAmount;
        this.totalWeight = totalWeight;
        this.isClosed = isClosed;
        this.isDeleted = isDeleted;
    }

    /**
     * Calculate the invoice total amount.
     *
     * The total will be equal to the sum of the product of the price and net
     * weight for each delivery note.
     *
     * @return The invoice total amount.
     */
    public float calculateTotal() {
        float total = 0;

        for (DeliveryNoteData deliveryNote : this.deliveryNotes) {
            float deliveryNoteTotal = deliveryNote.getNetWeight() * deliveryNote.getPrice();
            total += deliveryNoteTotal;
        }

        return total;
    }

    /**
     * Calculate the invoice total weight.
     *
     * The total will be equal to the sum of the net weight of each delivery
     * note.
     *
     * @return The invoice total weight.
     */
    public int calculateTotalWeight() {
        int totalWeight = 0;

        for (DeliveryNoteData deliveryNote : this.deliveryNotes) {
            totalWeight += deliveryNote.getNetWeight();
        }

        return totalWeight;
    }

    /**
     * Retrieve the invoice code.
     *
     * @return The invoice code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the invoice date.
     *
     * @return The invoice date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Retrieve the invoice delivery notes.
     *
     * @return The invoice delivery notes.
     */
    public ArrayList<DeliveryNoteData> getDeliveryNotes() {
        return this.deliveryNotes;
    }

    /**
     * Retrieve the invoice start period date.
     *
     * @return The invoice start period date.
     */
    public Date getStartPeriod() {
        return this.startPeriod;
    }

    /**
     * Retrieve the invoice end period date.
     *
     * @return The invoice end period date.
     */
    public Date getEndPeriod() {
        return this.endPeriod;
    }

    /**
     * Retrieve the invoice customer.
     *
     * @return The invoice customer.
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Retrieve the invoice file.
     *
     * @return A PDF file containing the invoice file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Retrieve the invoice total amount.
     *
     * @return The invoice total amount.
     */
    public double getTotal() {
        return this.totalAmount;
    }

    /**
     * Retrieve the invoice total weight.
     *
     * @return The invoice total weight.
     */
    public int getTotalWeight() {
        return this.totalWeight;
    }

    /**
     * Whether the invoice is closed from the system or not.
     *
     * @return true if the invoice is closed, otherwise false.
     */
    public boolean isClosed() {
        return this.isClosed;
    }

    /**
     * Whether the invoice is deleted from the system or not.
     *
     * @return true if the invoice is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Update the invoice file.
     *
     * @param file A PDF containing the invoice file.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Update the invoice total amount.
     *
     * @param totalAmount The invoice total amount.
     */
    public void setTotal(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Update the invoice total weight.
     *
     * @param totalWeight The invoice total weight.
     */
    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * Update the closed state for the invoice.
     *
     * @param isClosed Whether the invoice is closed from the system or not.
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    /**
     * Update the deletion state for the invoice.
     *
     * @param isDeleted Whether the invoice is deleted from the system or not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Create an invoice given its attribute values.
     *
     * @param attributes Map containing the value for each invoice attribute.
     * @return The created invoice.
     */
    public static Invoice from(Map<InvoiceAttribute, Object> attributes) {
        int code = (int) attributes.get(InvoiceAttribute.CODE);
        Date date = (Date) attributes.getOrDefault(InvoiceAttribute.DATE, null);
        ArrayList<DeliveryNoteData> deliveryNotes = (ArrayList<DeliveryNoteData>) attributes.get(InvoiceAttribute.DELIVERY_NOTES);
        Date startPeriod = (Date) attributes.get(InvoiceAttribute.START_PERIOD);
        Date endPeriod = (Date) attributes.get(InvoiceAttribute.END_PERIOD);
        Customer customer = (Customer) attributes.get(InvoiceAttribute.CUSTOMER);
        File file = (File) attributes.get(InvoiceAttribute.FILE);
        double totalAmount = (double) attributes.getOrDefault(InvoiceAttribute.TOTAL_AMOUNT, 0.0);
        int totalWeight = (int) attributes.getOrDefault(InvoiceAttribute.TOTAL_WEIGHT, 0);
        boolean isClosed = (boolean) attributes.getOrDefault(InvoiceAttribute.IS_CLOSED, false);
        boolean isDeleted = (boolean) attributes.getOrDefault(InvoiceAttribute.IS_DELETED, false);

        return new Invoice(code, date, deliveryNotes, startPeriod, endPeriod, customer, file, totalAmount, totalWeight, isClosed, isDeleted);
    }

}
