package variable.presentation.panels;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.application.usecases.ListVariables;
import variable.persistence.mongo.MongoVariableRepository;
import variable.presentation.utils.ListVariablesTableModel;

/**
 * Panel which lists all the variables.
 */
public class ListVariablesPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListVariablesPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the variables data by executing the use case.
     *
     * @return A list with all variables on the system.
     */
    private ArrayList<Variable> getVariables() {
        try {
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            ListVariables listVariables = new ListVariables(variableRepository);
            return listVariables.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListVariablesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Variables cannot be shown because the database has not been found", ex);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.NAME));
        columnNames.add(Localization.getLocalization(LocalizationKey.DESCRIPTION));
        columnNames.add(Localization.getLocalization(LocalizationKey.ATTRIBUTE));
        columnNames.add(Localization.getLocalization(LocalizationKey.SUBTOTAL));
        // Column which shows the remove/restore button.
        columnNames.add("");

        return columnNames;
    }

    /**
     * Set the table data based on the given variables.
     *
     * @param variables An array list containing all the variables on the
     * system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<Variable> variables) {
        Vector<Vector<Object>> data = new Vector<>();

        for (Variable variable : variables) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Variable name.
            String variableName = variable.getName();
            String variableFormat = String.format("${%s}", variableName);
            rowData.add(variableFormat);

            // Column 2: Variable description.
            String variableDescription = variable.getDescription();
            rowData.add(variableDescription);

            // Column 3: Variable entity attribute.
            rowData.add(variable.getAttribute());

            // Column 4: Variable subtotal.
            if (variable instanceof SubtotalVariable) {
                SubtotalVariable subtotalVariable = (SubtotalVariable) variable;
                rowData.add(subtotalVariable.getSubtotal());
            } else {
                rowData.add(null);
            }

            // Column 5: Empty name. It will show a button to remove or restore
            // a customer.
            String removeName = Localization.getLocalization(LocalizationKey.REMOVE);
            String restoreName = Localization.getLocalization(LocalizationKey.RESTORE);
            String removeRestoreButtonText = variable.isDeleted() ? restoreName : removeName;
            rowData.add(removeRestoreButtonText);

            data.add(rowData);
        }

        return data;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<Variable> variables = this.getVariables();

        if (variables != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(variables);

            table.setModel(new ListVariablesTableModel(data, columnNames));

            // Set a button renderer for the action button.
            TableColumn removeRestoreVariableColumn = table.getColumn(columnNames.get(4));
            removeRestoreVariableColumn.setCellRenderer(new ButtonRenderer());
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
                "${NAME}", "${DESCRIPTION}", "${ATTRIBUTE}", "${SUBTOTAL}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
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
