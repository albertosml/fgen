package invoice.presentation.panels;

import invoice.presentation.utils.ListInvoicesTableModel;
import customer.application.Customer;
import customer.application.usecases.ObtainCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import invoice.application.Invoice;
import invoice.application.usecases.ListInvoices;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import shared.application.configuration.ApplicationConfiguration;
import shared.application.configuration.ConfigurationVariable;
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

        this.traderInput.setEnabled(false);
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
        this.setLabelText(traderLabel, LocalizationKey.TRADER);
        this.setLabelText(startDateLabel, LocalizationKey.START_DATE);
        this.setLabelText(endDateLabel, LocalizationKey.END_DATE);
        this.setLabelText(invoicesTotalAmountLabel, LocalizationKey.TOTAL_AMOUNT);
        this.setLabelText(companyCommissionLabel, LocalizationKey.COMPANY_COMMISSION);
        this.setLabelText(individualCommissionLabel, LocalizationKey.INDIVIDUAL_COMMISSION);
        this.setLabelText(invoicesTotalWeightLabel, LocalizationKey.TOTAL_WEIGHT);
        this.setButtonText(listDeliveryNotesButton, LocalizationKey.LIST);
    }

    /**
     * Initialize inputs.
     */
    private void initializeInputs() {
        // Farmers input.
        Vector<Customer> farmers = new Vector<>(this.obtainCustomers(true));
        farmerInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Customer>(farmers));
        AutoCompleteDecorator.decorate(farmerInput);

        // Traders input.
        Vector<Customer> traders = new Vector<>(this.obtainCustomers(false));
        traderInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Customer>(traders));
        AutoCompleteDecorator.decorate(traderInput);

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
     * @param getFarmers Whether we must get the farmers or not.
     * @return A list with all the customers on the system based on the given
     * filter.
     */
    private ArrayList<Customer> obtainCustomers(boolean getFarmers) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ObtainCustomers obtainCustomers = new ObtainCustomers(customerRepository);
            return obtainCustomers.execute(getFarmers);
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
     * @param trader The trader customer to get the invoices.
     * @param start The start date to get the invoices.
     * @param end The end date to get the invoices.
     * @return A list with all available invoices on the system.
     */
    private ArrayList<Invoice> getInvoices(Customer farmer, Customer trader, Date start, Date end) {
        try {
            MongoInvoiceRepository invoiceRepository = new MongoInvoiceRepository();
            ListInvoices listInvoices = new ListInvoices(invoiceRepository);
            return listInvoices.execute(farmer, trader, start, end);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.TOTAL_WEIGHT));
        columnNames.add(Localization.getLocalization(LocalizationKey.TOTAL_AMOUNT));
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

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent event) {
                int eventType = event.getType();
                if (eventType == TableModelEvent.DELETE || eventType == TableModelEvent.UPDATE) {
                    setTotalsAndCommissionsFor(tableModel.getInvoices());
                }
            }
        });

        // Actions.
        String downloadAction = Localization.getLocalization(LocalizationKey.DOWNLOAD);
        String printAction = Localization.getLocalization(LocalizationKey.PRINT);
        String removeAction = Localization.getLocalization(LocalizationKey.REMOVE);
        String closeAction = Localization.getLocalization(LocalizationKey.CLOSE);
        String[] actions = new String[]{downloadAction, printAction, removeAction, closeAction};

        // Set a combobox cell editor for the actions column.
        TableColumn actionsColumn = table.getColumn(columnNames.get(6));
        JComboBox comboBox = new JComboBox(actions);
        actionsColumn.setCellEditor(new DefaultCellEditor(comboBox));

        // Resize first column.
        TableColumn codeColumn = table.getColumn(columnNames.get(0));
        codeColumn.setPreferredWidth(3);
    }

    /**
     * Based on the given data, calculate the total amount, total weight and
     * commissions for company and individual. Then, set the value on its
     * corresponding panel field.
     *
     * @param invoices The invoices.
     */
    private void setTotalsAndCommissionsFor(ArrayList<Invoice> invoices) {
        double totalAmount = 0;
        int totalWeight = 0;

        for (Invoice invoice : invoices) {
            totalAmount += invoice.getTotal();
            totalWeight += invoice.getTotalWeight();
        }

        // Invoices total amount.
        String formattedTotalAmount = String.format("%.2f €", totalAmount);
        this.invoicesTotalAmountValue.setText(formattedTotalAmount);

        // Company commission.
        double companyCommissionPercentage = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.COMPANY_COMMISSION_PERCENTAGE);
        double companyCommission = totalAmount * (companyCommissionPercentage / 100.0);
        String formattedCompanyCommissionValue = String.format("%.2f €", companyCommission);
        this.companyCommissionValue.setText(formattedCompanyCommissionValue);

        // Individual commission.
        double individualCommissionPercentage = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.INDIVIDUAL_COMMISSION_PERCENTAGE);
        double individualCommission = totalAmount * (individualCommissionPercentage / 100.0);
        String formattedIndividualCommissionValue = String.format("%.2f €", individualCommission);
        this.individualCommissionValue.setText(formattedIndividualCommissionValue);

        // Invoices total weight.
        String formattedTotalWeight = String.format("%d Kgs", totalWeight);
        this.invoicesTotalWeightValue.setText(formattedTotalWeight);
    }

    /**
     * Enable or disable the list invoices button depending on its state.
     *
     * List invoices button will be enabled if we have selected a farmer or a
     * trader to list.
     */
    private void updateListInvoicesButtonState() {
        boolean canListInvoices = isSelectedFarmer.isSelected() || isSelectedTrader.isSelected();
        this.listDeliveryNotesButton.setEnabled(canListInvoices);
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
        traderLabel = new javax.swing.JLabel();
        isSelectedTrader = new javax.swing.JCheckBox();
        traderInput = new javax.swing.JComboBox<>();
        invoicesTotalAmountLabel = new javax.swing.JLabel();
        invoicesTotalAmountValue = new javax.swing.JLabel();
        companyCommissionLabel = new javax.swing.JLabel();
        companyCommissionValue = new javax.swing.JLabel();
        individualCommissionValue = new javax.swing.JLabel();
        individualCommissionLabel = new javax.swing.JLabel();
        invoicesTotalWeightLabel = new javax.swing.JLabel();
        invoicesTotalWeightValue = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${CODE}", "${DATE}", "${CUSTOMER}", "${PERIOD}", "${TOTAL_AMOUNT}", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Object.class
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
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(40);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(6).setResizable(false);
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

        traderLabel.setText("${TRADER}:");

        isSelectedTrader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isSelectedTraderActionPerformed(evt);
            }
        });

        traderInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        invoicesTotalAmountLabel.setText("${TOTAL_AMOUNT}:");

        invoicesTotalAmountValue.setText("0€");

        companyCommissionLabel.setText("${COMPANY_COMMISSION}:");

        companyCommissionValue.setText("0€");

        individualCommissionValue.setText("0€");

        individualCommissionLabel.setText("${INDIVIDUAL_COMMISSION}:");

        invoicesTotalWeightLabel.setText("${TOTAL_WEIGHT}:");

        invoicesTotalWeightValue.setText("0€");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isSelectedFarmer)
                    .addComponent(isSelectedTrader))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(traderLabel)
                    .addComponent(farmerLabel))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(traderInput, javax.swing.GroupLayout.Alignment.TRAILING, 0, 245, Short.MAX_VALUE)
                    .addComponent(farmerInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startDateLabel)
                    .addComponent(endDateLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(endDateInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(listDeliveryNotesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(invoicesTotalAmountLabel)
                        .addGap(18, 18, 18)
                        .addComponent(invoicesTotalAmountValue))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(individualCommissionLabel)
                            .addComponent(companyCommissionLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(invoicesTotalWeightLabel)
                                .addGap(18, 18, 18)
                                .addComponent(invoicesTotalWeightValue)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(individualCommissionValue)
                            .addComponent(companyCommissionValue))))
                .addGap(145, 145, 145))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(farmerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(startDateLabel)
                        .addComponent(farmerLabel))
                    .addComponent(startDateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isSelectedFarmer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(listDeliveryNotesButton)
                        .addComponent(invoicesTotalAmountLabel)
                        .addComponent(invoicesTotalAmountValue)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(companyCommissionLabel)
                        .addComponent(companyCommissionValue))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(endDateInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(endDateLabel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(traderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(traderInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(isSelectedTrader, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(individualCommissionValue)
                    .addComponent(individualCommissionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invoicesTotalWeightLabel)
                    .addComponent(invoicesTotalWeightValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listDeliveryNotesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDeliveryNotesButtonActionPerformed
        Customer farmer = null;
        if (isSelectedFarmer.isSelected()) {
            farmer = (Customer) farmerInput.getSelectedItem();
        }

        Customer trader = null;
        if (isSelectedTrader.isSelected()) {
            trader = (Customer) traderInput.getSelectedItem();
        }

        Date startDate = startDateInput.getDate();
        Date endDate = endDateInput.getDate();

        ArrayList<Invoice> invoices = this.getInvoices(farmer, trader, startDate, endDate);
        this.setTotalsAndCommissionsFor(invoices);

        ListInvoicesTableModel tableModel = (ListInvoicesTableModel) table.getModel();
        tableModel.setInvoices(invoices);
    }//GEN-LAST:event_listDeliveryNotesButtonActionPerformed

    private void isSelectedFarmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedFarmerActionPerformed
        farmerInput.setEnabled(isSelectedFarmer.isSelected());

        if (this.isSelectedFarmer.isSelected()) {
            this.isSelectedTrader.setSelected(false);
            this.traderInput.setEnabled(false);
        }

        this.updateListInvoicesButtonState();
    }//GEN-LAST:event_isSelectedFarmerActionPerformed

    private void isSelectedTraderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isSelectedTraderActionPerformed
        traderInput.setEnabled(isSelectedTrader.isSelected());

        if (this.isSelectedTrader.isSelected()) {
            this.isSelectedFarmer.setSelected(false);
            this.farmerInput.setEnabled(false);
        }

        this.updateListInvoicesButtonState();
    }//GEN-LAST:event_isSelectedTraderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel companyCommissionLabel;
    private javax.swing.JLabel companyCommissionValue;
    private com.toedter.calendar.JDateChooser endDateInput;
    private javax.swing.JLabel endDateLabel;
    private javax.swing.JComboBox<String> farmerInput;
    private javax.swing.JLabel farmerLabel;
    private javax.swing.JLabel individualCommissionLabel;
    private javax.swing.JLabel individualCommissionValue;
    private javax.swing.JLabel invoicesTotalAmountLabel;
    private javax.swing.JLabel invoicesTotalAmountValue;
    private javax.swing.JLabel invoicesTotalWeightLabel;
    private javax.swing.JLabel invoicesTotalWeightValue;
    private javax.swing.JCheckBox isSelectedFarmer;
    private javax.swing.JCheckBox isSelectedTrader;
    private javax.swing.JButton listDeliveryNotesButton;
    private javax.swing.JScrollPane scrollPane;
    private com.toedter.calendar.JDateChooser startDateInput;
    private javax.swing.JLabel startDateLabel;
    private javax.swing.JTable table;
    private javax.swing.JComboBox<String> traderInput;
    private javax.swing.JLabel traderLabel;
    // End of variables declaration//GEN-END:variables
}
