package subtotal.presentation.panels;

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
import subtotal.application.Subtotal;
import subtotal.application.usecases.ListSubtotals;
import subtotal.persistence.mongo.MongoSubtotalRepository;
import subtotal.presentation.utils.ListSubtotalsMouseAdapter;

/**
 * Panel which lists all the subtotals.
 */
public class ListSubtotalsPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListSubtotalsPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the subtotals data by executing the use case.
     *
     * @return A list with all subtotals on the system.
     */
    private ArrayList<Subtotal> getSubtotals() {
        try {
            MongoSubtotalRepository subtotalRepository = new MongoSubtotalRepository();
            ListSubtotals listSubtotals = new ListSubtotals(subtotalRepository);
            return listSubtotals.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListSubtotalsPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Subtotals cannot be shown because the database has not been found", ex);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.PERCENTAGE));
        columnNames.add(Localization.getLocalization(LocalizationKey.IS_DISCOUNT));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Set the table data based on the given subtotals.
     *
     * @param subtotals An array list containing all the subtotals on the
     * system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<Subtotal> subtotals) {
        Vector<Vector<Object>> data = new Vector<>();

        for (Subtotal subtotal : subtotals) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Subtotal code.
            int subtotalCode = subtotal.getCode();
            rowData.add(subtotalCode);

            // Column 2: Subtotal name.
            String subtotalName = subtotal.getName();
            rowData.add(subtotalName);

            // Column 3: Subtotal percentage.
            int subtotalPercentage = subtotal.getPercentage();
            rowData.add(subtotalPercentage);

            // Column 4: Subtotal discount/tax.
            rowData.add(subtotal.isDiscount());

            // Column 5: Empty name. It will show a button to remove or restore
            // a customer.
            String removeName = Localization.getLocalization(LocalizationKey.REMOVE);
            String restoreName = Localization.getLocalization(LocalizationKey.RESTORE);
            String removeRestoreButtonText = subtotal.isDeleted() ? restoreName : removeName;
            rowData.add(removeRestoreButtonText);

            data.add(rowData);
        }

        return data;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<Subtotal> subtotals = this.getSubtotals();

        if (subtotals != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(subtotals);

            TableModel dataModel = new DefaultTableModel(data, columnNames) {
                @Override
                public Class getColumnClass(int columnIndex) {
                    // Checkbox will be shown for the "discount/tax" column.
                    if (columnIndex == 3) {
                        return Boolean.class;
                    }

                    return Object.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table.setModel(dataModel);

            table.addMouseListener(new ListSubtotalsMouseAdapter(table));

            // Set a button renderer for the action button.
            TableColumn removeRestoreCustomerColumn = table.getColumn(columnNames.get(4));
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