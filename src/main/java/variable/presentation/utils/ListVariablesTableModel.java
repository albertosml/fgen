package variable.presentation.utils;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

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
        return false;
    }
}
