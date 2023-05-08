package deliverynote.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Mouse adapter for the delivery note items panel, which includes a button to
 * remove a delivery note item.
 */
public class DeliveryNoteItemsMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public DeliveryNoteItemsMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Remove the delivery note item.
     *
     * @param evt The mouse event.
     */
    private void removeDeliveryNoteItem(MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.removeRow(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int column = table.columnAtPoint(evt.getPoint());

        if (column == 2) {
            // In column 2, there is a button to remove the chosen delivery note
            // item defined by the clicked row.
            this.removeDeliveryNoteItem(evt);
        }
    }

}
