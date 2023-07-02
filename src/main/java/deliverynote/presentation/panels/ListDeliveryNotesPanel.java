package deliverynote.presentation.panels;

import customer.application.Customer;
import customer.application.usecases.ListCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.ListDeliveryNotes;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import deliverynote.presentation.utils.ListDeliveryNotesTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import product.application.Product;
import product.application.usecases.ListProducts;
import product.persistence.mongo.MongoProductRepository;
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
        this.setLabelText(customerLabel, LocalizationKey.CUSTOMER);
        this.setLabelText(productLabel, LocalizationKey.PRODUCT);
        this.setLabelText(startDateLabel, LocalizationKey.START_DATE);
        this.setLabelText(endDateLabel, LocalizationKey.END_DATE);
        this.setLabelText(totalNetWeightLabel, LocalizationKey.TOTAL_NET_WEIGHT);
        this.setLabelText(totalNumBoxesLabel, LocalizationKey.TOTAL_NUM_BOXES);
        this.setButtonText(listDeliveryNotesButton, LocalizationKey.LIST);
    }

    /**
     * Initialize inputs.
     */
    private void initializeInputs() {
        // Customers input.
        Vector<Customer> customers = new Vector<>(this.getCustomers());
        customerInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Customer>(customers));
        AutoCompleteDecorator.decorate(customerInput);

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
     * Obtain all the non-removed customers data by executing the use case.
     *
     * @return A list with all non-removed customers on the system.
     */
    private ArrayList<Customer> getCustomers() {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ListCustomers listCustomers = new ListCustomers(customerRepository);
            return listCustomers.execute(false);
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
     * @param customer The customer to get the delivery notes.
     * @param product The product to get the delivery notes.
     * @param start The start date to get the delivery notes.
     * @param end The end date to get the delivery notes.
     * @return A list with all available delivery notes data on the system.
     */
    private ArrayList<DeliveryNoteData> getDeliveryNotesData(Customer customer, Product product, Date start, Date end) {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            ListDeliveryNotes listDeliveryNotes = new ListDeliveryNotes(deliveryNoteRepository);
            return listDeliveryNotes.execute(customer, product, start, end);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.CUSTOMER));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRODUCT));
        columnNames.add(Localization.getLocalization(LocalizationKey.BOXES_QTY));
        columnNames.add(Localization.getLocalization(LocalizationKey.NET_WEIGHT));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        table.setModel(new ListDeliveryNotesTableModel(null, columnNames, table));

        // Actions.
        String downloadAction = Localization.getLocalization(LocalizationKey.DOWNLOAD);
        String printAction = Localization.getLocalization(LocalizationKey.PRINT);
        String removeAction = Localization.getLocalization(LocalizationKey.REMOVE);
        String[] actions = new String[]{downloadAction, printAction, removeAction};

        // Set a combobox cell editor for the actions column.
        TableColumn actionsColumn = table.getColumn(columnNames.get(5));
        JComboBox comboBox = new JComboBox(actions);
        actionsColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        customerInput = new javax.swing.JComboBox<>();
        customerLabel = new javax.swing.JLabel();
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
        isSelectedCustomer = new javax.swing.JCheckBox();
        isSelectedProduct = new javax.swing.JCheckBox();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${DATE}", "${CUSTOMER}", "${PRODUCT}", "${BOXES_QTY}", "${NET_WEIGHT}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        }

        customerInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        customerLabel.setText("${CUSTOMER}:");

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

        isSelectedCustomer.setSelected(true);
        isSelectedCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedCustomerActionPerformed(evt);
            }
        });

        isSelectedProduct.setSelected(true);
        isSelectedProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(isSelectedProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(productLabel)
                                .addGap(18, 18, 18)
                                .addComponent(productInput, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(isSelectedCustomer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customerInput, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDateLabel)
                            .addComponent(endDateLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(endDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addComponent(listDeliveryNotesButton)
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(totalNetWeightLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalNetWeightValue))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(totalNumBoxesLabel)
                                .addGap(18, 18, 18)
                                .addComponent(totalNumBoxesValue)))
                        .addGap(0, 82, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPane)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(isSelectedCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(customerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(startDateLabel)
                                            .addComponent(customerLabel)))
                                    .addComponent(startDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(endDateInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(endDateLabel))
                                    .addComponent(productInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(productLabel)
                                        .addComponent(isSelectedProduct))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalNetWeightLabel)
                                    .addComponent(totalNetWeightValue))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(totalNumBoxesLabel)
                                    .addComponent(totalNumBoxesValue)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(listDeliveryNotesButton)))
                .addGap(24, 24, 24)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listDeliveryNotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDeliveryNotesButtonActionPerformed
        Customer customer = null;
        if (isSelectedCustomer.isSelected()) {
            customer = (Customer) customerInput.getSelectedItem();
        }

        Product product = null;
        if (isSelectedProduct.isSelected()) {
            product = (Product) productInput.getSelectedItem();
        }

        Date startDate = startDateInput.getDate();
        Date endDate = endDateInput.getDate();

        ArrayList<DeliveryNoteData> deliveryNotesData = this.getDeliveryNotesData(customer, product, startDate, endDate);

        int totalNumBoxes = 0;
        int totalNetWeight = 0;

        for (DeliveryNoteData deliveryNoteData : deliveryNotesData) {
            totalNumBoxes += deliveryNoteData.getNumBoxes();
            totalNetWeight += deliveryNoteData.getNetWeight();
        }

        this.totalNetWeightValue.setText(Integer.toString(totalNetWeight));
        this.totalNumBoxesValue.setText(Integer.toString(totalNumBoxes));

        ListDeliveryNotesTableModel tableModel = (ListDeliveryNotesTableModel) table.getModel();
        tableModel.addDeliveryNotesData(deliveryNotesData);
    }//GEN-LAST:event_listDeliveryNotesButtonActionPerformed

    private void isSelectedCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedCustomerActionPerformed
        customerInput.setEnabled(isSelectedCustomer.isSelected());
    }//GEN-LAST:event_isSelectedCustomerActionPerformed

    private void isSelectedProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedProductActionPerformed
        productInput.setEnabled(isSelectedProduct.isSelected());
    }//GEN-LAST:event_isSelectedProductActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> customerInput;
    private javax.swing.JLabel customerLabel;
    private com.toedter.calendar.JDateChooser endDateInput;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JCheckBox isSelectedCustomer;
    private javax.swing.JCheckBox isSelectedProduct;
    private javax.swing.JButton listDeliveryNotesButton;
    private javax.swing.JComboBox<String> productInput;
    private javax.swing.JLabel productLabel;
    private javax.swing.JScrollPane scrollPane;
    private com.toedter.calendar.JDateChooser startDateInput;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JTable table;
    private javax.swing.JLabel totalNetWeightLabel;
    private javax.swing.JLabel totalNetWeightValue;
    private javax.swing.JLabel totalNumBoxesLabel;
    private javax.swing.JLabel totalNumBoxesValue;
    // End of variables declaration//GEN-END:variables
}
