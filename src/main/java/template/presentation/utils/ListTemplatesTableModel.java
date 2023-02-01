package template.presentation.utils;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Table model for the list templates panel.
 */
public class ListTemplatesTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public ListTemplatesTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        // Template code column.
        if (columnIndex == 0) {
            return String.class;
        }

        // Template name column.
        if (columnIndex == 1) {
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
