package deliverynote.presentation.panels;

import customer.application.Customer;
import customer.application.usecases.ObtainCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.ListDeliveryNotes;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import deliverynote.presentation.utils.ListDeliveryNotesTableModel;
import invoice.application.Invoice;
import invoice.application.InvoiceAttribute;
import invoice.application.usecases.CreateInvoice;
import invoice.application.utils.InvoiceGenerator;
import invoice.persistence.mongo.MongoInvoiceRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import product.application.Product;
import product.application.usecases.ListProducts;
import product.persistence.mongo.MongoProductRepository;
import shared.application.Pair;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which lists all the delivery notes.
 */
public class ListDeliveryNotesPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListDeliveryNotesPanel() {
        initComponents();
        initializeFormLabels();
        initializeInputs();
        initializeTable();
    }

    /**
     * Set the text for a label component.
     *
     * @param label The label component.
     * @param key The localization key to use for getting the label text.
     */
    private void setLabelText(JLabel label, LocalizationKey key) {
        String localization = Localization.getLocalization(key);
        String labelText = String.format("%s:", localization);
        label.setText(labelText);
    }

    /**
     * Set the text for a button component.
     *
     * @param button The button component.
     * @param key The localization key to use for getting the button text.
     */
    private void setButtonText(JButton button, LocalizationKey key) {
        String localization = Localization.getLocalization(key);
        button.setText(localization);
    }

    /**
     * Initialize form labels.
     */
    private void initializeFormLabels() {
        this.setLabelText(farmerLabel, LocalizationKey.FARMER);
        this.setLabelText(supplierLabel, LocalizationKey.SUPPLIER);
        this.setLabelText(productLabel, LocalizationKey.PRODUCT);
        this.setLabelText(startDateLabel, LocalizationKey.START_DATE);
        this.setLabelText(endDateLabel, LocalizationKey.END_DATE);
        this.setLabelText(totalNetWeightLabel, LocalizationKey.TOTAL_NET_WEIGHT);
        this.setLabelText(totalNumBoxesLabel, LocalizationKey.TOTAL_NUM_BOXES);
        this.setButtonText(listDeliveryNotesButton, LocalizationKey.LIST);
        this.setButtonText(invoiceButton, LocalizationKey.BILL);
    }

    /**
     * Initialize inputs.
     */
    private void initializeInputs() {
        // Farmers input.
        Vector<Customer> farmers = new Vector<>(this.obtainCustomers(false));
        farmerInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Customer>(farmers));
        AutoCompleteDecorator.decorate(farmerInput);

        // Suppliers input.
        Vector<Customer> suppliers = new Vector<>(this.obtainCustomers(true));
        supplierInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Customer>(suppliers));
        AutoCompleteDecorator.decorate(supplierInput);

        // Product input.
        Vector<Product> products = new Vector<>(this.getProducts());
        productInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Product>(products));
        AutoCompleteDecorator.decorate(productInput);

        // End date, which will be now.
        Calendar currentDate = Calendar.getInstance();
        endDateInput.setDate(currentDate.getTime());

        // Start date, which will be set at the start moment.
        currentDate.set(Calendar.MILLISECOND, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);

        startDateInput.setDate(currentDate.getTime());
    }

    /**
     * Obtain all the customers data by executing the use case.
     *
     * @param getSuppliers Whether we must get the suppliers or not.
     * @return A list with all the customers on the system based on the given
     * filter.
     */
    private ArrayList<Customer> obtainCustomers(boolean getSuppliers) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ObtainCustomers obtainCustomers = new ObtainCustomers(customerRepository);
            return obtainCustomers.execute(getSuppliers);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customers cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Obtain all the non-removed products data by executing the use case.
     *
     * @return A list with all non-removed products on the system.
     */
    private ArrayList<Product> getProducts() {
        try {
            MongoProductRepository productRepository = new MongoProductRepository();
            ListProducts listProducts = new ListProducts(productRepository);
            return listProducts.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Products cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Obtain all the delivery notes data by executing the use case.
     *
     * @param farmer The farmer customer to get the delivery notes.
     * @param supplier The supplier customer to get the delivery notes.
     * @param product The product to get the delivery notes.
     * @param start The start date to get the delivery notes.
     * @param end The end date to get the delivery notes.
     * @return A list with all available delivery notes data on the system.
     */
    private ArrayList<DeliveryNoteData> getDeliveryNotesData(Customer farmer, Customer supplier, Product product, Date start, Date end) {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            ListDeliveryNotes listDeliveryNotes = new ListDeliveryNotes(deliveryNoteRepository);
            return listDeliveryNotes.execute(farmer, supplier, product, start, end);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListDeliveryNotesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Delivery notes cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Generate the column names for the table.
     *
     * @return A vector containing all the identifiers for each table column.
     */
    private Vector<String> generateColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add(Localization.getLocalization(LocalizationKey.DATE));
        columnNames.add(Localization.getLocalization(LocalizationKey.FARMER));
        columnNames.add(Localization.getLocalization(LocalizationKey.SUPPLIER));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRODUCT));
        columnNames.add(Localization.getLocalization(LocalizationKey.BOXES_QTY));
        columnNames.add(Localization.getLocalization(LocalizationKey.NET_WEIGHT));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRICE));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        ListDeliveryNotesTableModel tableModel = new ListDeliveryNotesTableModel(null, columnNames, table);
        table.setModel(tableModel);

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent event) {
                int eventType = event.getType();
                if (eventType == TableModelEvent.DELETE || eventType == TableModelEvent.UPDATE) {
                    setTotalValues(tableModel.getDeliveryNotesData());
                }
            }
        });

        // Actions.
        String downloadAction = Localization.getLocalization(LocalizationKey.DOWNLOAD);
        String printAction = Localization.getLocalization(LocalizationKey.PRINT);
        String removeAction = Localization.getLocalization(LocalizationKey.REMOVE);
        String[] actions = new String[]{downloadAction, printAction, removeAction};

        // Set a combobox cell editor for the actions column.
        TableColumn actionsColumn = table.getColumn(columnNames.get(7));
        JComboBox comboBox = new JComboBox(actions);
        actionsColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    /**
     * Based on the given data, calculate the total net weight and total boxes
     * quantity. Then, set the value on its corresponding panel field.
     *
     * @param deliveryNotesData The delivery notes data.
     */
    private void setTotalValues(ArrayList<DeliveryNoteData> deliveryNotesData) {
        int totalNumBoxes = 0;
        int totalNetWeight = 0;

        for (DeliveryNoteData deliveryNoteData : deliveryNotesData) {
            totalNumBoxes += deliveryNoteData.getNumBoxes();
            totalNetWeight += deliveryNoteData.getNetWeight();
        }

        this.totalNetWeightValue.setText(Integer.toString(totalNetWeight));
        this.totalNumBoxesValue.setText(Integer.toString(totalNumBoxes));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        farmerInput = new javax.swing.JComboBox<>();
        farmerLabel = new javax.swing.JLabel();
        productLabel = new javax.swing.JLabel();
        productInput = new javax.swing.JComboBox<>();
        endDateLabel = new javax.swing.JLabel();
        startDateLabel = new javax.swing.JLabel();
        listDeliveryNotesButton = new javax.swing.JButton();
        totalNetWeightLabel = new javax.swing.JLabel();
        totalNetWeightValue = new javax.swing.JLabel();
        totalNumBoxesLabel = new javax.swing.JLabel();
        totalNumBoxesValue = new javax.swing.JLabel();
        startDateInput = new com.toedter.calendar.JDateChooser();
        endDateInput = new com.toedter.calendar.JDateChooser();
        isSelectedFarmer = new javax.swing.JCheckBox();
        isSelectedProduct = new javax.swing.JCheckBox();
        supplierLabel = new javax.swing.JLabel();
        isSelectedSupplier = new javax.swing.JCheckBox();
        supplierInput = new javax.swing.JComboBox<>();
        invoiceButton = new javax.swing.JButton();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${DATE}", "${CUSTOMER}", "${PRODUCT}", "${BOXES_QTY}", "${NET_WEIGHT}", "${PRICE}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
        }

        farmerInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        farmerLabel.setText("${FARMER}:");

        productLabel.setText("${PRODUCT}:");

        productInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        endDateLabel.setText("${END_DATE}:");

        startDateLabel.setText("${START_DATE}:");

        listDeliveryNotesButton.setText("${LIST}");
        listDeliveryNotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listDeliveryNotesButtonActionPerformed(evt);
            }
        });

        totalNetWeightLabel.setText("${TOTAL_NET_WEIGHT} :");

        totalNetWeightValue.setText("0");

        totalNumBoxesLabel.setText("${TOTAL_NUM_BOXES} :");

        totalNumBoxesValue.setText("0");

        isSelectedFarmer.setSelected(true);
        isSelectedFarmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedFarmerActionPerformed(evt);
            }
        });

        isSelectedProduct.setSelected(true);
        isSelectedProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedProductActionPerformed(evt);
            }
        });

        supplierLabel.setText("${SUPPLIER}:");

        isSelectedSupplier.setSelected(true);
        isSelectedSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedSupplierActionPerformed(evt);
            }
        });

        supplierInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        invoiceButton.setText("${BILL}");
        invoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invoiceButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(isSelectedProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(isSelectedFarmer)
                                    .addComponent(isSelectedSupplier))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supplierLabel)
                            .addComponent(farmerLabel)
                            .addComponent(productLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(supplierInput, javax.swing.GroupLayout.Alignment.TRAILING, 0, 245, Short.MAX_VALUE)
                            .addComponent(productInput, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(farmerInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDateLabel)
                            .addComponent(endDateLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(endDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 39, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(listDeliveryNotesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(invoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(totalNumBoxesLabel)
                                .addGap(23, 23, 23)
                                .addComponent(totalNumBoxesValue))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(totalNetWeightLabel)
                                .addGap(18, 18, 18)
                                .addComponent(totalNetWeightValue)))
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(farmerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(startDateLabel)
                                .addComponent(farmerLabel))
                            .addComponent(startDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isSelectedFarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(endDateInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(endDateLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(supplierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(supplierInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(isSelectedSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(isSelectedProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(productLabel)
                                        .addComponent(productInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(listDeliveryNotesButton)
                            .addComponent(totalNetWeightLabel)
                            .addComponent(totalNetWeightValue))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalNumBoxesLabel)
                            .addComponent(totalNumBoxesValue)
                            .addComponent(invoiceButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listDeliveryNotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDeliveryNotesButtonActionPerformed
        Customer farmer = null;
        if (isSelectedFarmer.isSelected()) {
            farmer = (Customer) farmerInput.getSelectedItem();
        }

        Customer supplier = null;
        if (isSelectedSupplier.isSelected()) {
            supplier = (Customer) supplierInput.getSelectedItem();
        }

        Product product = null;
        if (isSelectedProduct.isSelected()) {
            product = (Product) productInput.getSelectedItem();
        }

        Date startDate = startDateInput.getDate();
        Date endDate = endDateInput.getDate();

        ArrayList<DeliveryNoteData> deliveryNotesData = this.getDeliveryNotesData(farmer, supplier, product, startDate, endDate);
        this.setTotalValues(deliveryNotesData);

        ListDeliveryNotesTableModel tableModel = (ListDeliveryNotesTableModel) table.getModel();
        tableModel.addDeliveryNotesData(deliveryNotesData);
    }//GEN-LAST:event_listDeliveryNotesButtonActionPerformed

    private void isSelectedFarmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedFarmerActionPerformed
        farmerInput.setEnabled(isSelectedFarmer.isSelected());
    }//GEN-LAST:event_isSelectedFarmerActionPerformed

    private void isSelectedProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedProductActionPerformed
        productInput.setEnabled(isSelectedProduct.isSelected());
    }//GEN-LAST:event_isSelectedProductActionPerformed

    private void isSelectedSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedSupplierActionPerformed
        supplierInput.setEnabled(isSelectedSupplier.isSelected());
    }//GEN-LAST:event_isSelectedSupplierActionPerformed

    private void invoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invoiceButtonActionPerformed
        ListDeliveryNotesTableModel tableModel = (ListDeliveryNotesTableModel) table.getModel();
        ArrayList<DeliveryNoteData> deliveryNotesData = tableModel.getDeliveryNotesData();

        // The invoice will not include delivery notes without a price.
        deliveryNotesData.removeIf(deliveryNoteData -> deliveryNoteData.getPrice() == 0);

        Date startPeriod = this.startDateInput.getDate();
        Date endPeriod = this.endDateInput.getDate();

        int templateCode = 0;
        Customer customer = null;
        if (this.isSelectedFarmer.isSelected()) {
            customer = (Customer) farmerInput.getSelectedItem();
            templateCode = 3; // Identifies farmer invoice.
        }

        if (this.isSelectedSupplier.isSelected()) {
            customer = (Customer) supplierInput.getSelectedItem();
            if (customer.getCode() == 5) {
                templateCode = 4; // Special supplier invoice.
            } else {
                templateCode = 2; // Identifies supplier invoice.
            }
        }

        Map<InvoiceAttribute, Object> invoiceAttributes = new HashMap<>();
        invoiceAttributes.put(InvoiceAttribute.DELIVERY_NOTES, deliveryNotesData);
        invoiceAttributes.put(InvoiceAttribute.START_PERIOD, startPeriod);
        invoiceAttributes.put(InvoiceAttribute.END_PERIOD, endPeriod);
        invoiceAttributes.put(InvoiceAttribute.CUSTOMER, customer);

        String message;
        try {
            MongoInvoiceRepository invoiceRepository = new MongoInvoiceRepository();
            CreateInvoice createInvoice = new CreateInvoice(invoiceRepository);
            Invoice invoice = createInvoice.execute(invoiceAttributes);

            InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
            boolean areInvoicesGenerated = invoiceGenerator.generate(invoice, templateCode);

            if (areInvoicesGenerated) {
                message = Localization.getLocalization(LocalizationKey.INVOICE_GENERATED_MESSAGE);
            } else {
                message = Localization.getLocalization(LocalizationKey.INVOICE_NOT_GENERATED_MESSAGE);
            }
        } catch (NotDefinedDatabaseContextException | IOException | InterruptedException ex) {
            String className = ListDeliveryNotesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Invoice not created because the database has not been found", ex);

            message = Localization.getLocalization(LocalizationKey.INVOICE_NOT_GENERATED_MESSAGE);
        }

        JOptionPane.showMessageDialog(this, message);
    }//GEN-LAST:event_invoiceButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser endDateInput;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JComboBox<String> farmerInput;
    private javax.swing.JLabel farmerLabel;
    private javax.swing.JButton invoiceButton;
    private javax.swing.JCheckBox isSelectedFarmer;
    private javax.swing.JCheckBox isSelectedProduct;
    private javax.swing.JCheckBox isSelectedSupplier;
    private javax.swing.JButton listDeliveryNotesButton;
    private javax.swing.JComboBox<String> productInput;
    private javax.swing.JLabel productLabel;
    private javax.swing.JScrollPane scrollPane;
    private com.toedter.calendar.JDateChooser startDateInput;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JComboBox<String> supplierInput;
    private javax.swing.JLabel supplierLabel;
    private javax.swing.JTable table;
    private javax.swing.JLabel totalNetWeightLabel;
    private javax.swing.JLabel totalNetWeightValue;
    private javax.swing.JLabel totalNumBoxesLabel;
    private javax.swing.JLabel totalNumBoxesValue;
    // End of variables declaration//GEN-END:variables
}
