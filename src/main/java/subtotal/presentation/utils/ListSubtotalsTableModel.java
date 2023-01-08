package subtotal.presentation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import subtotal.application.SubtotalAttribute;
import subtotal.application.usecases.EditSubtotal;
import subtotal.persistence.mongo.MongoSubtotalRepository;
import subtotal.presentation.panels.ListSubtotalsPanel;

/**
 * Table model for the list subtotals panel.
 */
public class ListSubtotalsTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public ListSubtotalsTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * Obtain all the subtotal attributes based on the new value and its
     * position defined by the row and the column.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return A map with all the attributes of the edited subtotal.
     */
    private Map<SubtotalAttribute, Object> getSubtotalAttributes(Object newValue, int row, int column) {
        Map<SubtotalAttribute, Object> subtotalAttributes = new HashMap<>();

        int subtotalCode = row + 1;
        subtotalAttributes.put(SubtotalAttribute.CODE, subtotalCode);

        Object subtotalName = column == 1 ? newValue : super.getValueAt(row, 1);
        subtotalAttributes.put(SubtotalAttribute.NAME, subtotalName);

        Object subtotalPercentage = column == 2 ? newValue : super.getValueAt(row, 2);
        subtotalAttributes.put(SubtotalAttribute.PERCENTAGE, subtotalPercentage);

        Object subtotalIsDiscount = column == 3 ? newValue : super.getValueAt(row, 3);
        subtotalAttributes.put(SubtotalAttribute.ISDISCOUNT, subtotalIsDiscount);

        // Subtotal can be only updated if it is not deleted.
        subtotalAttributes.put(SubtotalAttribute.ISDELETED, false);

        return subtotalAttributes;
    }

    /**
     * Execute the edit subtotal use case.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     */
    private void editSubtotal(Object newValue, int row, int column) {
        try {
            Map<SubtotalAttribute, Object> subtotalAttributes = this.getSubtotalAttributes(newValue, row, column);

            MongoSubtotalRepository subtotalRepository = new MongoSubtotalRepository();
            EditSubtotal editSubtotal = new EditSubtotal(subtotalRepository);
            editSubtotal.execute(subtotalAttributes);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListSubtotalsPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Subtotal not edited because the database has not been found", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        // Checkbox will be shown for the "discount/tax" column.
        if (columnIndex == 3) {
            return Boolean.class;

        }

        // Set number column class for the percentage.
        if (columnIndex == 2) {
            return Integer.class;

        }

        return Object.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        // Removed subtotals cannot be edited.
        String buttonValue = (String) super.getValueAt(row, 4);
        boolean isRemovedSubtotal = buttonValue.equals(Localization.getLocalization(LocalizationKey.RESTORE));

        if (isRemovedSubtotal) {
            return false;
        }

        // Only the restore/remove button and the code will not be
        // editable.
        int codeColumn = 0;
        int removeRestoreColumn = 4;
        return !(column == codeColumn || column == removeRestoreColumn);
    }

    /**
     * {@inheritDoc}
     *
     * Note that it also edits the subtotal on the system.
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        boolean isPercentageColumn = column == 2;
        if (isPercentageColumn) {
            int percentage = (int) newValue;
            if (percentage < 0) {
                percentage = 0;
            } else if (percentage > 100) {
                percentage = 100;
            }

            super.setValueAt(percentage, row, column);
        } else {
            super.setValueAt(newValue, row, column);
        }

        this.editSubtotal(newValue, row, column);
    }

}
