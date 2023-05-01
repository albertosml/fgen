package deliverynote.presentation.utils;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Table model for the delivery note items panel.
 */
public class DeliveryNoteItemsTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public DeliveryNoteItemsTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        // Quantity column.
        if (columnIndex == 1) {
            return Integer.class;
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
