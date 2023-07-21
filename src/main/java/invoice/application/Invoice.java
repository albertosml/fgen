package invoice.application;

import container.application.Pallet;
import customer.application.Customer;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteAttribute;
import deliverynote.application.DeliveryNoteData;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import product.application.Product;
import template.application.Template;
import weighing.application.Weighing;

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
     * Constructor.
     *
     * @param code The invoice code.
     * @param date The invoice date.
     * @param deliveryNotes The invoice delivery notes.
     * @param startPeriod The invoice start period date.
     * @param endPeriod The invoice end period date.
     */
    private Invoice(int code, Date date, ArrayList<DeliveryNoteData> deliveryNotes, Date startPeriod, Date endPeriod) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.deliveryNotes = deliveryNotes;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
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

        return new Invoice(code, date, deliveryNotes, startPeriod, endPeriod);
    }

}
