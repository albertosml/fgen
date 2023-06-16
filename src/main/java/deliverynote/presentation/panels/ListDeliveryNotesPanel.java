package deliverynote.presentation.panels;

import customer.application.Customer;
import customer.application.usecases.ListCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.ListDeliveryNotes;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import deliverynote.presentation.utils.ListDeliveryNotesMouseAdapter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which lists all the delivery notes.
 */
public class ListDeliveryNotesPanel extends javax.swing.JPanel {

    /**
     * Mouse listener.
     */
    private ListDeliveryNotesMouseAdapter mouseListener;

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
        this.setLabelText(monthLabel, LocalizationKey.MONTH);
        this.setLabelText(yearLabel, LocalizationKey.YEAR);
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
     * @param month The month to get the delivery notes.
     * @param year The year to get the delivery notes.
     * @return A list with all available delivery notes data on the system.
     */
    private ArrayList<DeliveryNoteData> getDeliveryNotesData(Customer customer, Product product, int month, int year) {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            ListDeliveryNotes listDeliveryNotes = new ListDeliveryNotes(deliveryNoteRepository);
            return listDeliveryNotes.execute(customer, product, month, year);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.CODE));
        columnNames.add(Localization.getLocalization(LocalizationKey.DATE));
        columnNames.add(Localization.getLocalization(LocalizationKey.CUSTOMER));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRODUCT));
        // Both columns does not contain a name, so they will contain the same
        // identifier. However, they need a different identification for the
        // retrieval. To solve that problem, a different empty string will be
        // set for them.
        columnNames.add("");
        columnNames.add(" ");

        return columnNames;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        TableModel dataModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(dataModel);

        this.mouseListener = new ListDeliveryNotesMouseAdapter(table);
        table.addMouseListener(this.mouseListener);

        // Set a button renderer for the action buttons.
        TableColumn downloadColumn = table.getColumn(columnNames.get(4));
        downloadColumn.setCellRenderer(new ButtonRenderer());

        TableColumn printColumn = table.getColumn(columnNames.get(5));
        printColumn.setCellRenderer(new ButtonRenderer());
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
        monthInput = new com.toedter.calendar.JMonthChooser();
        yearInput = new com.toedter.calendar.JYearChooser();
        yearLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        listDeliveryNotesButton = new javax.swing.JButton();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${CODE}", "${DATE}", "${CUSTOMER}", "${PRODUCT}", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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

        yearLabel.setText("${YEAR}:");

        monthLabel.setText("${MONTH}:");

        listDeliveryNotesButton.setText("${LIST}");
        listDeliveryNotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listDeliveryNotesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productLabel)
                            .addComponent(customerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(productInput, 0, 213, Short.MAX_VALUE)
                            .addComponent(customerInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(monthLabel)
                            .addComponent(yearLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearInput, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addComponent(listDeliveryNotesButton)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                                .addComponent(monthInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(yearInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(customerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(monthLabel)
                                    .addComponent(customerLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(productLabel)
                                    .addComponent(productInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(yearLabel)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(listDeliveryNotesButton)))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listDeliveryNotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDeliveryNotesButtonActionPerformed
        Customer customer = (Customer) customerInput.getSelectedItem();
        Product product = (Product) productInput.getSelectedItem();
        int month = monthInput.getMonth();
        int year = yearInput.getYear();

        mouseListener.addDeliveryNotesData(this.getDeliveryNotesData(customer, product, month, year));
    }//GEN-LAST:event_listDeliveryNotesButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> customerInput;
    private javax.swing.JLabel customerLabel;
    private javax.swing.JButton listDeliveryNotesButton;
    private com.toedter.calendar.JMonthChooser monthInput;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JComboBox<String> productInput;
    private javax.swing.JLabel productLabel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private com.toedter.calendar.JYearChooser yearInput;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
