package subtotal.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Mouse adapter for the list subtotals panel, which includes a button to remove
 * or restore a subtotal.
 */
public class ListSubtotalsMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListSubtotalsMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Remove or restore the subtotal depending on the deletion state.
     *
     * @param evt The mouse event.
     */
    private void removeOrRestoreCustomer(MouseEvent evt) {
        int removeRestoreColumn = 4;
        int row = table.rowAtPoint(evt.getPoint());

        TableModel tableModel = table.getModel();

        String cellText = (String) tableModel.getValueAt(row, removeRestoreColumn);
        String removeText = Localization.getLocalization(LocalizationKey.REMOVE);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);

        // Button state needs to be updated as the customer state has changed.
        String buttonText = cellText.equals(removeText) ? restoreText : removeText;
        tableModel.setValueAt(buttonText, row, removeRestoreColumn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int column = table.columnAtPoint(evt.getPoint());

        if (column == 4) {
            // In column 4, there is a button to restore or remove
            // the chosen subtotal defined by the clicked row.
            this.removeOrRestoreCustomer(evt);
        }
    }

}
