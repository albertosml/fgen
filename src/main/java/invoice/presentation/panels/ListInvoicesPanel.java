package invoice.presentation.panels;

import invoice.presentation.utils.ListInvoicesTableModel;
import customer.application.Customer;
import customer.application.usecases.ObtainCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import invoice.application.Invoice;
import invoice.persistence.mongo.MongoInvoiceRepository;
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
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which lists all the invoices.
 */
public class ListInvoicesPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListInvoicesPanel() {
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
        this.setLabelText(startDateLabel, LocalizationKey.START_DATE);
        this.setLabelText(endDateLabel, LocalizationKey.END_DATE);
        this.setButtonText(listDeliveryNotesButton, LocalizationKey.LIST);
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
            String className = ListInvoicesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customers cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Obtain all the invoices by executing the use case.
     *
     * @param farmer The farmer customer to get the invoices.
     * @param supplier The supplier customer to get the invoices.
     * @param start The start date to get the invoices.
     * @param end The end date to get the invoices.
     * @return A list with all available invoices on the system.
     */
    private ArrayList<Invoice> getInvoices(Customer farmer, Customer supplier, Date start, Date end) {
        try {
            MongoInvoiceRepository invoiceRepository = new MongoInvoiceRepository();
            ListInvoices listInvoices = new ListInvoices(invoiceRepository);
            return listInvoices.execute(farmer, supplier, start, end);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListInvoicesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Invoices cannot be shown because the database has not been found", ex);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.PERIOD));
        columnNames.add(Localization.getLocalization(LocalizationKey.TOTAL));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        ListInvoicesTableModel tableModel = new ListInvoicesTableModel(null, columnNames, table);
        table.setModel(tableModel);

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        farmerInput = new javax.swing.JComboBox<>();
        farmerLabel = new javax.swing.JLabel();
        endDateLabel = new javax.swing.JLabel();
        startDateLabel = new javax.swing.JLabel();
        listDeliveryNotesButton = new javax.swing.JButton();
        startDateInput = new com.toedter.calendar.JDateChooser();
        endDateInput = new com.toedter.calendar.JDateChooser();
        isSelectedFarmer = new javax.swing.JCheckBox();
        supplierLabel = new javax.swing.JLabel();
        isSelectedSupplier = new javax.swing.JCheckBox();
        supplierInput = new javax.swing.JComboBox<>();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${CODE}", "${DATE}", "${CUSTOMER}", "${PERIOD}", "${TOTAL}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Object.class
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
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(40);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
        }

        farmerInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        farmerLabel.setText("${FARMER}:");

        endDateLabel.setText("${END_DATE}:");

        startDateLabel.setText("${START_DATE}:");

        listDeliveryNotesButton.setText("${LIST}");
        listDeliveryNotesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listDeliveryNotesButtonActionPerformed(evt);
            }
        });

        isSelectedFarmer.setSelected(true);
        isSelectedFarmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedFarmerActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(isSelectedFarmer)
                            .addComponent(isSelectedSupplier))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supplierLabel)
                            .addComponent(farmerLabel))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(supplierInput, javax.swing.GroupLayout.Alignment.TRAILING, 0, 245, Short.MAX_VALUE)
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
                        .addComponent(listDeliveryNotesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(296, 296, 296))
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
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(supplierLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supplierInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(isSelectedSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(listDeliveryNotesButton))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
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

        Date startDate = startDateInput.getDate();
        Date endDate = endDateInput.getDate();

        ArrayList<Invoice> invoices = this.getInvoices(farmer, supplier, startDate, endDate);

        ListInvoicesTableModel tableModel = (ListInvoicesTableModel) table.getModel();
        tableModel.setInvoices(invoices);
    }//GEN-LAST:event_listDeliveryNotesButtonActionPerformed

    private void isSelectedFarmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedFarmerActionPerformed
        farmerInput.setEnabled(isSelectedFarmer.isSelected());
    }//GEN-LAST:event_isSelectedFarmerActionPerformed

    private void isSelectedSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedSupplierActionPerformed
        supplierInput.setEnabled(isSelectedSupplier.isSelected());
    }//GEN-LAST:event_isSelectedSupplierActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser endDateInput;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JComboBox<String> farmerInput;
    private javax.swing.JLabel farmerLabel;
    private javax.swing.JCheckBox isSelectedFarmer;
    private javax.swing.JCheckBox isSelectedSupplier;
    private javax.swing.JButton listDeliveryNotesButton;
    private javax.swing.JScrollPane scrollPane;
    private com.toedter.calendar.JDateChooser startDateInput;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JComboBox<String> supplierInput;
    private javax.swing.JLabel supplierLabel;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
