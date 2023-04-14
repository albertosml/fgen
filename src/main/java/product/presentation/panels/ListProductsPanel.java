package product.presentation.panels;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import product.application.Product;
import product.application.usecases.ListProducts;
import product.persistence.mongo.MongoProductRepository;
import product.presentation.utils.ListProductsTableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which lists all the products.
 */
public class ListProductsPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListProductsPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the products data by executing the use case.
     *
     * @return A list with all products on the system.
     */
    private ArrayList<Product> getProducts() {
        try {
            MongoProductRepository productRepository = new MongoProductRepository();
            ListProducts listProducts = new ListProducts(productRepository);
            return listProducts.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListProductsPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Products cannot be shown because the database has not been found", ex);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.NAME));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRICE));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Set the table data based on the given products.
     *
     * @param products An array list containing all the products on the system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<Product> products) {
        Vector<Vector<Object>> data = new Vector<>();

        for (Product product : products) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Product code.
            String productCode = product.getCode();
            rowData.add(productCode);

            // Column 2: Product name.
            String productName = product.getName();
            rowData.add(productName);

            // Column 3: Product price.
            double productPrice = product.getPrice();
            rowData.add(productPrice);

            // Column 4: Empty name. It will show a button to remove or restore
            // a customer.
            String removeName = Localization.getLocalization(LocalizationKey.REMOVE);
            String restoreName = Localization.getLocalization(LocalizationKey.RESTORE);
            String removeRestoreButtonText = product.isDeleted() ? restoreName : removeName;
            rowData.add(removeRestoreButtonText);

            data.add(rowData);
        }

        return data;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<Product> products = this.getProducts();

        if (products != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(products);

            table.setModel(new ListProductsTableModel(data, columnNames));

            // Set a button renderer for the action button.
            TableColumn removeRestoreCustomerColumn = table.getColumn(columnNames.get(3));
            removeRestoreCustomerColumn.setCellRenderer(new ButtonRenderer());
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
                "${CODE}", "${NAME}", "${PERCENTAGE}", "${ISDISCOUNT}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            table.getColumnModel().getColumn(4).setResizable(false);
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
