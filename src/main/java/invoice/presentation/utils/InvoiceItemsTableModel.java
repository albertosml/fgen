package invoice.presentation.utils;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Table model for the invoice items panel.
 */
public class InvoiceItemsTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public InvoiceItemsTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            // Quantity column.
            case 0:
                return Integer.class;
            // Description column.
            case 1:
                return String.class;
            // Price column.
            case 2:
                return Double.class;
            // Remove button column.
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
