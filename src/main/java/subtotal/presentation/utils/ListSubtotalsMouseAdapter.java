package subtotal.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import subtotal.application.usecases.RemoveSubtotal;
import subtotal.application.utils.SubtotalRemovalState;
import subtotal.persistence.mongo.MongoSubtotalRepository;
import variable.persistence.mongo.MongoVariableRepository;

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
     * Execute the remove subtotal use case.
     *
     * @param code The code of the subtotal to remove.
     * @return Whether the subtotal with the given code has been removed or not.
     */
    private SubtotalRemovalState removeSubtotal(int code) {
        try {
            MongoSubtotalRepository subtotalRepository = new MongoSubtotalRepository();
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            RemoveSubtotal removeSubtotal = new RemoveSubtotal(subtotalRepository, variableRepository);
            return removeSubtotal.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListSubtotalsMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Subtotal not removed because the database has not been found", ex);
        }

        return SubtotalRemovalState.NOT_UPDATED;
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

        int subtotalCode = row + 1;

        if (cellText.equals(removeText)) {
            SubtotalRemovalState subtotalRemovalState = this.removeSubtotal(subtotalCode);

            if (subtotalRemovalState == SubtotalRemovalState.REMOVED) {
                // Button state needs to be updated as the subtotal state has changed.
                tableModel.setValueAt(restoreText, row, removeRestoreColumn);
            } else if (subtotalRemovalState == SubtotalRemovalState.ASSOCIATED_WITH_VARIABLE) {
                // Show an error message in this case.
                String infoMessage = Localization.getLocalization(LocalizationKey.SUBTOTAL_ASSOCIATED_WITH_VARIABLE_MESSAGE);
                JOptionPane.showMessageDialog(table.getParent(), infoMessage);
            }
        } else {
            // Button state needs to be updated as the subtotal state has changed.
            tableModel.setValueAt(removeText, row, removeRestoreColumn);
        }
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
