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
     * The supplier customer.
     */
    private Customer supplier;

    /**
     * The farmer customer.
     */
    private Customer farmer;

    /**
     * A PDF file containing the farmer invoice file.
     */
    private File farmerInvoice;

    /**
     * A PDF file containing the supplier invoice file.
     */
    private File supplierInvoice;

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
     * @param farmer The invoice farmer customer.
     * @param supplier The invoice supplier customer.
     * @param isDeleted Whether the invoice is deleted or not.
     */
    private Invoice(int code, Date date, ArrayList<DeliveryNoteData> deliveryNotes, Date startPeriod, Date endPeriod, Customer farmer, Customer supplier, boolean isDeleted) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.deliveryNotes = deliveryNotes;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.farmer = farmer;
        this.supplier = supplier;
        this.isDeleted = isDeleted;
    }

    /**
     * Calculate the invoice total.
     *
     * The total will be equal to the sum of the product of the price and net
     * weight for each delivery note.
     *
     * @return The invoice total.
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
     * Retrieve the invoice farmer customer.
     *
     * @return The invoice farmer customer.
     */
    public Customer getFarmer() {
        return this.farmer;
    }

    /**
     * Retrieve the farmer invoice.
     *
     * @return A PDF file containing the farmer invoice.
     */
    public File getFarmerInvoice() {
        return this.farmerInvoice;
    }

    /**
     * Retrieve the invoice supplier customer.
     *
     * @return The invoice supplier customer.
     */
    public Customer getSupplier() {
        return this.supplier;
    }

    /**
     * Retrieve the supplier invoice.
     *
     * @return A PDF file containing the supplier invoice.
     */
    public File getSupplierInvoice() {
        return this.supplierInvoice;
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
     * Update the farmer invoice file.
     *
     * @param farmerInvoice A PDF containing the farmer invoice file.
     */
    public void setFarmerFile(File farmerInvoice) {
        this.farmerInvoice = farmerInvoice;
    }

    /**
     * Update the supplier invoice file.
     *
     * @param supplierInvoice A PDF containing the supplier invoice file.
     */
    public void setSupplierFile(File supplierInvoice) {
        this.supplierInvoice = supplierInvoice;
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
        Customer farmer = (Customer) attributes.get(InvoiceAttribute.FARMER);
        Customer supplier = (Customer) attributes.get(InvoiceAttribute.SUPPLIER);
        boolean isDeleted = (boolean) attributes.getOrDefault(InvoiceAttribute.IS_DELETED, false);

        return new Invoice(code, date, deliveryNotes, startPeriod, endPeriod, farmer, supplier, isDeleted);
    }

}
