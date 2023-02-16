package invoice.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Mouse adapter for the invoice items panel, which includes a button to remove
 * an invoice item.
 */
public class InvoiceItemsMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public InvoiceItemsMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Remove the invoice item.
     *
     * @param evt The mouse event.
     */
    private void removeInvoiceItem(MouseEvent evt) {
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

        if (column == 3) {
            // In column 3, there is a button to remove the chosen invoice item
            // defined by the clicked row.
            this.removeInvoiceItem(evt);
        }
    }

}
