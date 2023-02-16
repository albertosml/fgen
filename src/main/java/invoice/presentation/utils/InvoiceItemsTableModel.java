package invoice.presentation.utils;

import invoice.application.InvoiceItemAttribute;
import invoice.application.usecases.EditInvoiceItem;
import java.util.HashMap;
import java.util.Map;
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
     * Edit the invoice item.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return Whether the invoice item is allowed to be edited or not.
     */
    private boolean editInvoiceItem(Object newValue, int row, int column) {
        Object qty = column == 0 ? newValue : super.getValueAt(row, 0);
        Object description = column == 1 ? newValue : super.getValueAt(row, 1);
        Object price = column == 2 ? newValue : super.getValueAt(row, 2);

        Map<InvoiceItemAttribute, Object> attributes = new HashMap<>();
        attributes.put(InvoiceItemAttribute.QTY, (int) qty);
        attributes.put(InvoiceItemAttribute.DESCRIPTION, (String) description);
        attributes.put(InvoiceItemAttribute.PRICE, (double) price);

        EditInvoiceItem editInvoiceItem = new EditInvoiceItem();
        return editInvoiceItem.execute(attributes);
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
        // All the columns except the remove button column can be edited.
        return column < 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        if (this.isCellEditable(row, column)) {
            if (this.editInvoiceItem(newValue, row, column)) {
                super.setValueAt(newValue, row, column);
            } else if (column == 1) {
                // Invalid description, show message.
                String invalidDescriptionMessage = Localization.getLocalization(LocalizationKey.INVALID_POSITION_MESSAGE);
                JOptionPane.showMessageDialog(null, invalidDescriptionMessage);
            }
        }
    }

}
