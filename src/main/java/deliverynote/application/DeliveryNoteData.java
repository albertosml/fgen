package deliverynote.application;

import customer.application.Customer;
import customer.persistence.CustomerRepository;
import customer.persistence.mongo.MongoCustomerRepository;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import product.application.Product;
import product.persistence.ProductRepository;
import product.persistence.mongo.MongoProductRepository;
import shared.persistence.Base64Converter;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;

/**
 * Delivery note stored data.
 */
public class DeliveryNoteData {

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
     * The file.
     */
    private File file;

    /**
     * The total number of pallets.
     */
    private int numPallets;

    /**
     * The total number of boxes.
     */
    private int numBoxes;

    /**
     * The net weight.
     */
    private int netWeight;

    /**
     * Constructor.
     *
     * @param code The delivery note data code.
     * @param date The delivery note data date. If it is null, it will be set to
     * the current date time.
     * @param customer The delivery note data customer.
     * @param product The delivery note data product.
     * @param file The delivery note data file.
     * @param numPallets The delivery note data total pallets.
     * @param numBoxes The delivery note data total boxes.
     * @param netWeight The delivery note data net weight.
     */
    private DeliveryNoteData(int code, Date date, Customer customer, Product product, File file, int numPallets, int numBoxes, int netWeight) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.customer = customer;
        this.product = product;
        this.file = file;
        this.numPallets = numPallets;
        this.numBoxes = numBoxes;
        this.netWeight = netWeight;
    }

    /**
     * Retrieve the delivery note data code.
     *
     * @return The delivery note data code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the delivery note data generation datetime.
     *
     * @return The delivery note data date.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Retrieve the delivery note data customer.
     *
     * @return The delivery note data customer.
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Retrieve the delivery note data product.
     *
     * @return The delivery note data product.
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Retrieve the delivery note data file.
     *
     * @return The delivery note data file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Retrieve the delivery note data number of pallets.
     *
     * @return The delivery note data number of pallets.
     */
    public int getNumPallets() {
        return this.numPallets;
    }

    /**
     * Retrieve the delivery note data number of boxes.
     *
     * @return The delivery note data number of boxes.
     */
    public int getNumBoxes() {
        return this.numBoxes;
    }

    /**
     * Retrieve the delivery note data net weight.
     *
     * @return The delivery note data net weight.
     */
    public int getNetWeight() {
        return this.netWeight;
    }

    /**
     * Create a delivery note data given its attribute values.
     *
     * @param attributes Map containing the value for each delivery note data
     * attribute.
     * @return The created delivery note data.
     */
    public static DeliveryNoteData from(Map<DeliveryNoteDataAttribute, Object> attributes) {
        int code = (int) attributes.get(DeliveryNoteDataAttribute.CODE);

        Date date = (Date) attributes.get(DeliveryNoteDataAttribute.DATE);
        /*try {
            String dateString = (String) attributes.get(DeliveryNoteDataAttribute.DATE);
            DateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            date = iso8601DateFormat.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(DeliveryNoteData.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        Map<String, String> fileAttributes = (Map<String, String>) attributes.get(DeliveryNoteDataAttribute.FILE);
        File file = Base64Converter.decode(fileAttributes);

        Customer customer = null;
        try {
            CustomerRepository customerRepository = new MongoCustomerRepository();
            int customerCode = (int) attributes.get(DeliveryNoteDataAttribute.CUSTOMER);
            customer = customerRepository.find(customerCode);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = DeliveryNoteData.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customer cannot be obtained because the database has not been found", ex);
        }

        Product product = null;
        try {
            ProductRepository productRepository = new MongoProductRepository();
            String productCode = (String) attributes.get(DeliveryNoteDataAttribute.PRODUCT);
            product = productRepository.find(productCode);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = DeliveryNoteData.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customer cannot be obtained because the database has not been found", ex);
        }

        int numPallets = (int) attributes.getOrDefault(DeliveryNoteDataAttribute.NUM_PALLETS, 0);
        int numBoxes = (int) attributes.getOrDefault(DeliveryNoteDataAttribute.NUM_BOXES, 0);
        int netWeight = (int) attributes.getOrDefault(DeliveryNoteDataAttribute.NET_WEIGHT, 0);

        return new DeliveryNoteData(code, date, customer, product, file, numPallets, numBoxes, netWeight);
    }

}
