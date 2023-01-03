package customer.presentation.panels;

import customer.application.Customer;
import customer.application.usecases.ListCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import customer.presentation.utils.ListCustomersMouseAdapter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which lists all the customers.
 */
public class ListCustomersPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListCustomersPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the customers data by executing the use case.
     *
     * @return A list with all available customers on the system.
     */
    private ArrayList<Customer> getCustomers() {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ListCustomers listCustomers = new ListCustomers(customerRepository);
            return listCustomers.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListCustomersPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customers cannot be shown because the database has not been found", ex);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.DATA));
        // Both columns does not contain a name, so they will contain the same
        // identifier. However, they need a different identification for the
        // retrieval. To solve that problem, a different empty string will be
        // set for them.
        columnNames.add("");
        columnNames.add(" ");

        return columnNames;
    }

    /**
     * Set the table data based on the given customers.
     *
     * @param customers An array list containing all the available customers on
     * the system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<Customer> customers) {
        Vector<Vector<Object>> data = new Vector<>();

        for (Customer customer : customers) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Customer code.
            int customerCode = customer.getCode();
            rowData.add(customerCode);

            // Column 2: Customer mandatory data (Customer name - Customer TIN).
            String customerData = String.format("%s - %s", customer.getName(), customer.getTin());
            rowData.add(customerData);

            // Column 3: Empty name. It will show a button to remove or restore
            // a customer.
            String removeName = Localization.getLocalization(LocalizationKey.REMOVE);
            String restoreName = Localization.getLocalization(LocalizationKey.RESTORE);
            String removeRestoreButtonText = customer.isDeleted() ? restoreName : removeName;
            rowData.add(removeRestoreButtonText);

            // Column 4: Empty name with a whitespace. It will show a button to
            // see the customer details.
            String showName = Localization.getLocalization(LocalizationKey.SHOW);
            rowData.add(showName);

            data.add(rowData);
        }

        return data;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<Customer> customers = this.getCustomers();

        if (customers != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(customers);

            TableModel dataModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table.setModel(dataModel);

            table.addMouseListener(new ListCustomersMouseAdapter(table));

            // Set a button renderer for the action buttons.
            TableColumn removeRestoreCustomerColumn = table.getColumn(columnNames.get(2));
            removeRestoreCustomerColumn.setCellRenderer(new ButtonRenderer());

            TableColumn showCustomerColumn = table.getColumn(columnNames.get(3));
            showCustomerColumn.setCellRenderer(new ButtonRenderer());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${CODE}", "${DATA}", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
