package template.presentation.utils;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Table model for the template fields panel.
 */
public class TemplateFieldsTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public TemplateFieldsTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
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
        return false;
    }

}
