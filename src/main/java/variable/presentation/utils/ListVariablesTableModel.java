package variable.presentation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import subtotal.application.Subtotal;
import subtotal.application.usecases.ListSubtotals;
import subtotal.persistence.mongo.MongoSubtotalRepository;
import variable.application.EntityAttribute;
import variable.application.VariableAttribute;
import variable.application.usecases.EditVariable;
import variable.persistence.mongo.MongoVariableRepository;

/**
 * Table model for the list variables panel.
 */
public class ListVariablesTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public ListVariablesTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
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
            String className = ListVariablesTableModel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Subtotals cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Obtain all the variable attributes based on the new value and its
     * position defined by the row and the column.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return A map with all the attributes of the edited variable.
     */
    private Map<VariableAttribute, Object> getVariableAttributes(Object newValue, int row, int column) {
        Map<VariableAttribute, Object> variableAttributes = new HashMap<>();

        // Variable can be only updated if it is not deleted.
        variableAttributes.put(VariableAttribute.ISDELETED, false);

        Object variableName = column == 0 ? newValue : super.getValueAt(row, 0);
        // The value for the variable name will be "${NAME}", so we will remove
        // the two first characters and the last one from the name to retrieve
        // the original name.
        String stringVariableName = (String) variableName;
        String variableWithoutSymbol = stringVariableName.substring(2, stringVariableName.length() - 1);
        variableAttributes.put(VariableAttribute.NAME, variableWithoutSymbol);

        Object variableDescription = column == 1 ? newValue : super.getValueAt(row, 1);
        variableAttributes.put(VariableAttribute.DESCRIPTION, variableDescription);

        Object variableEntityAttribute = column == 2 ? newValue : super.getValueAt(row, 2);
        variableAttributes.put(VariableAttribute.ATTRIBUTE, variableEntityAttribute);

        Object variableSubtotal = column == 3 ? newValue : super.getValueAt(row, 3);
        if (variableSubtotal != null) {
            variableAttributes.put(VariableAttribute.SUBTOTAL, variableSubtotal);
        }

        return variableAttributes;
    }

    /**
     * Execute the edit variable use case.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     */
    private void editVariable(Object newValue, int row, int column) {
        try {
            Map<VariableAttribute, Object> variableAttributes = this.getVariableAttributes(newValue, row, column);

            MongoVariableRepository variableRepository = new MongoVariableRepository();
            EditVariable editVariable = new EditVariable(variableRepository);
            editVariable.execute(variableAttributes);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListVariablesTableModel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Variable not edited because the database has not been found", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        // The first 3 columns (name, description and attributes) will be string.
        if (columnIndex < 3) {
            return String.class;
        }

        return Object.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        // Rows associated to removed variables will not be editable.
        String removeRestoreButtonText = (String) super.getValueAt(row, 4);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);
        // Restore text is shown when the variable has been removed.
        boolean isVariableRemoved = removeRestoreButtonText.equals(restoreText);
        if (isVariableRemoved) {
            return false;
        }

        // Subtotal column will not be edited if the entity attribute is not
        // invoice subtotal.
        if (column == 3) {
            EntityAttribute entityAttribute = (EntityAttribute) super.getValueAt(row, 2);
            return entityAttribute == EntityAttribute.SUBTOTAL;
        }

        // All columns can be edited except the first one (which contains the
        // name) and the last one (that includes the remove/restore button).
        return column > 0 && column < 4;

    }

    /**
     * {@inheritDoc}
     *
     * Note that it also edits the variable on the system.
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        if (column == 2) {
            // If we switch from invoice subtotal attribute to another, we must
            // remove the subtotal.
            EntityAttribute newEntityAttribute = (EntityAttribute) newValue;
            EntityAttribute oldEntityAttribute = (EntityAttribute) super.getValueAt(row, column);
            if (oldEntityAttribute == EntityAttribute.SUBTOTAL && newEntityAttribute != EntityAttribute.SUBTOTAL) {
                super.setValueAt(null, row, 3);
            } else if (newEntityAttribute == EntityAttribute.SUBTOTAL && oldEntityAttribute != EntityAttribute.SUBTOTAL) {
                // Set the first subtotal by default when choosing the invoice
                // subtotal entity attribute.
                Vector<Subtotal> nonRemovedSubtotals = new Vector<>();

                for (Subtotal subtotal : this.getSubtotals()) {
                    if (!subtotal.isDeleted()) {
                        nonRemovedSubtotals.add(subtotal);
                    }
                }

                if (nonRemovedSubtotals.isEmpty()) {
                    // Abort the process if there is no subtotal to associate.
                    return;
                } else {
                    Subtotal firstSubtotal = nonRemovedSubtotals.get(0);
                    super.setValueAt(firstSubtotal, row, 3);
                }
            }
        }

        super.setValueAt(newValue, row, column);

        // Only update the variable if the cell is editable.
        if (this.isCellEditable(row, column)) {
            this.editVariable(newValue, row, column);
        }
    }
}
