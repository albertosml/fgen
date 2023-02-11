package template.presentation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import template.application.TemplateFieldAttribute;
import template.application.usecases.EditTemplateField;

/**
 * Table model for the template fields panel.
 */
public class TemplateFieldsTableModel extends DefaultTableModel {

    /**
     * Whether the template is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     * @param isDeleted Whether the template is deleted or not.
     */
    public TemplateFieldsTableModel(Vector<? extends Vector> data, Vector<?> columnNames, boolean isDeleted) {
        super(data, columnNames);
        this.isDeleted = isDeleted;
    }

    /**
     * Edit the template field.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return Whether the template field is allowed to be edited or not.
     */
    private boolean editTemplateField(Object newValue, int row, int column) {
        Object position = column == 0 ? newValue : super.getValueAt(row, 0);
        Object expression = column == 1 ? newValue : super.getValueAt(row, 1);

        Map<TemplateFieldAttribute, String> attributes = new HashMap<>();
        attributes.put(TemplateFieldAttribute.POSITION, (String) position);
        attributes.put(TemplateFieldAttribute.EXPRESSION, (String) expression);

        EditTemplateField editTemplateField = new EditTemplateField();
        return editTemplateField.execute(attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        // Set string column class for the position and the expression.
        if (columnIndex < 2) {
            return String.class;

        }

        return Object.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        // All the columns except the remove button column can be edited.
        // Cells can be only edited if the template is not deleted.
        return column < 2 && !this.isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        if (this.isCellEditable(row, column)) {
            if (this.editTemplateField(newValue, row, column)) {
                super.setValueAt(newValue, row, column);
            } else if (column == 0) {
                // Invalid position, show message.
                String invalidPositionMessage = Localization.getLocalization(LocalizationKey.INVALID_POSITION_MESSAGE);
                JOptionPane.showMessageDialog(null, invalidPositionMessage);
            }
        }
    }

}
