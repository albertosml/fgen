package customer.presentation.utils;

import customer.application.usecases.RemoveCustomer;
import customer.persistence.mongo.MongoCustomerRepository;
import customer.presentation.panels.RegisterCustomerPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.MainFrame;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Mouse adapter for the list customers panel, which includes a button to
 * remove, remove or show a customer.
 */
public class ListCustomersMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListCustomersMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Execute the remove customer use case.
     *
     * @param code The code of the customer to remove.
     * @return Whether the customer with the given code has been removed or not.
     */
    private boolean removeCustomer(int code) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            RemoveCustomer removeCustomer = new RemoveCustomer(customerRepository);
            return removeCustomer.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterCustomerPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customer not removed because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Remove or restore the customer depending on the deletion customer state.
     *
     * @param evt The mouse event.
     */
    private void removeOrRestoreCustomer(MouseEvent evt) {
        int removeRestoreColumn = 2;
        int row = table.rowAtPoint(evt.getPoint());
        int chosenCustomerCode = row + 1;

        TableModel tableModel = table.getModel();

        String cellText = (String) tableModel.getValueAt(row, removeRestoreColumn);
        String removeText = Localization.getLocalization(LocalizationKey.REMOVE);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);

        if (cellText.equals(removeText)) {
            boolean isCustomerRemoved = this.removeCustomer(chosenCustomerCode);

            if (isCustomerRemoved) {
                // Button state needs to be updated as the customer state has changed.
                tableModel.setValueAt(restoreText, row, removeRestoreColumn);
            }
        }
    }

    /**
     * Show the selected customer by the mouse event.
     *
     * @param evt The mouse event.
     */
    private void showCustomer(MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());
        int chosenCustomerCode = row + 1;

        // Go to the show customer panel.
        MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(table.getParent());
        mainFrame.redirectToShowCustomer(chosenCustomerCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int column = table.columnAtPoint(evt.getPoint());

        if (column == 2) {
            // In column 2, there is a button to restore or remove
            // the chosen customer defined by the clicked row.
            this.removeOrRestoreCustomer(evt);
        } else if (column == 3) {
            // In column 3, there is a button to show the details of
            // the chosen customer defined by the clicked row.
            this.showCustomer(evt);
        }
    }

}
