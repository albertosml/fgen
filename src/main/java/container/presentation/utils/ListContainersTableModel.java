package container.presentation.utils;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Table model for the list containers panel.
 */
public class ListContainersTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public ListContainersTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            // Code column.
            case 0:
                return Integer.class;
            // Name column.
            case 1:
                return String.class;
            // Weight column.
            case 2:
                return Double.class;
            default:
                return Object.class;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
