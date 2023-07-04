package deliverynote.application;

import customer.application.Customer;
import customer.persistence.CustomerRepository;
import customer.persistence.mongo.MongoCustomerRepository;
import java.io.File;
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
     * The farmer customer.
     */
    private Customer farmer;

    /**
     * The supplier customer.
     */
    private Customer supplier;

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
     * Whether the delivery note is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code The delivery note data code.
     * @param date The delivery note data date. If it is null, it will be set to
     * the current date time.
     * @param farmer The delivery note data farmer customer.
     * @param supplier The delivery note data farmer supplier.
     * @param product The delivery note data product.
     * @param file The delivery note data file.
     * @param numPallets The delivery note data total pallets.
     * @param numBoxes The delivery note data total boxes.
     * @param netWeight The delivery note data net weight.
     * @param isDeleted Whether the delivery note is deleted or not.
     */
    private DeliveryNoteData(int code, Date date, Customer farmer, Customer supplier, Product product, File file, int numPallets, int numBoxes, int netWeight, boolean isDeleted) {
        this.code = code;
        this.date = date == null ? Date.from(Instant.now()) : date;
        this.farmer = farmer;
        this.supplier = supplier;
        this.product = product;
        this.file = file;
        this.numPallets = numPallets;
        this.numBoxes = numBoxes;
        this.netWeight = netWeight;
        this.isDeleted = isDeleted;
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
     * Retrieve the delivery note data farmer customer.
     *
     * @return The delivery note data farmer customer.
     */
    public Customer getFarmer() {
        return this.farmer;
    }

    /**
     * Retrieve the delivery note data supplier customer.
     *
     * @return The delivery note data supplier customer.
     */
    public Customer getSupplier() {
        return this.supplier;
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
     * Whether the delivery note is deleted from the system or not.
     *
     * @return true if the delivery note is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Update the deletion state for the delivery note.
     *
     * @param isDeleted Whether the delivery note is deleted from the system or
     * not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

        Map<String, String> fileAttributes = (Map<String, String>) attributes.get(DeliveryNoteDataAttribute.FILE);
        File file = Base64Converter.decode(fileAttributes);
        file.deleteOnExit();

        Customer farmer = null, supplier = null;
        try {
            CustomerRepository customerRepository = new MongoCustomerRepository();

            int farmerCode = (int) attributes.get(DeliveryNoteDataAttribute.FARMER);
            farmer = customerRepository.find(farmerCode);

            int supplierCode = (int) attributes.get(DeliveryNoteDataAttribute.SUPPLIER);
            supplier = customerRepository.find(supplierCode);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = DeliveryNoteData.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customers cannot be obtained because the database has not been found", ex);
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

        boolean isDeleted = (boolean) attributes.getOrDefault(DeliveryNoteDataAttribute.IS_DELETED, false);

        return new DeliveryNoteData(code, date, farmer, supplier, product, file, numPallets, numBoxes, netWeight, isDeleted);
    }

}
