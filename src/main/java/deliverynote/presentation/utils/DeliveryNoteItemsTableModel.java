package deliverynote.presentation.utils;

import deliverynote.application.DeliveryNoteItemAttribute;
import deliverynote.application.usecases.EditDeliveryNoteItem;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

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
     * Edit the delivery note item.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return Whether the delivery note item is allowed to be edited or not.
     */
    private boolean editDeliveryNoteItem(Object newValue, int row, int column) {
        Object container = column == 0 ? newValue : super.getValueAt(row, 0);
        Object qty = column == 1 ? newValue : super.getValueAt(row, 1);

        Map<DeliveryNoteItemAttribute, Object> attributes = new HashMap<>();
        attributes.put(DeliveryNoteItemAttribute.CONTAINER, container);
        attributes.put(DeliveryNoteItemAttribute.QTY, (int) qty);

        EditDeliveryNoteItem editDeliveryNoteItem = new EditDeliveryNoteItem();
        return editDeliveryNoteItem.execute(attributes);
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
        // All the columns except the remove button column can be edited.
        return column < 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        if (this.isCellEditable(row, column)) {
            if (this.editDeliveryNoteItem(newValue, row, column)) {
                super.setValueAt(newValue, row, column);
            } else if (column == 0) {
                // Invalid container, show message.
                String invalidContainerMessage = Localization.getLocalization(LocalizationKey.INVALID_CONTAINER_MESSAGE);
                JOptionPane.showMessageDialog(null, invalidContainerMessage);
            } else if (column == 1) {
                // Invalid quantity, show message.
                String invalidQtyMessage = Localization.getLocalization(LocalizationKey.INVALID_QTY_MESSAGE);
                JOptionPane.showMessageDialog(null, invalidQtyMessage);
            }
        }
    }

}
