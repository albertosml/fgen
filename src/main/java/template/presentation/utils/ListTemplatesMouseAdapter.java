package template.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import shared.presentation.MainFrame;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Mouse adapter for the list templates panel, which includes buttons to
 * remove, restore or show a customer.
 */
public class ListTemplatesMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListTemplatesMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Remove or restore the template depending on the deletion state.
     *
     * @param evt The mouse event.
     */
    private void removeOrRestoreTemplate(MouseEvent evt) {
        int removeRestoreColumn = 3;
        int row = table.rowAtPoint(evt.getPoint());

        TableModel tableModel = table.getModel();

        String cellText = (String) tableModel.getValueAt(row, removeRestoreColumn);
        String removeText = Localization.getLocalization(LocalizationKey.REMOVE);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);

        boolean isRemoved = cellText.equals(removeText);
        String newCellText = isRemoved ? restoreText : removeText;

        // Button state needs to be updated as the template deletion state has changed.
        tableModel.setValueAt(newCellText, row, removeRestoreColumn);
    }

    /**
     * Show the selected template by the mouse event.
     *
     * @param evt The mouse event.
     */
    private void showTemplate(MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());
        int chosenTemplateCode = row + 1;

        // Go to the show template panel.
        MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(table.getParent());
        mainFrame.redirectToShowTemplate(chosenTemplateCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int column = table.columnAtPoint(evt.getPoint());

        if (column == 2) {
            // In column 2, there is a button to show the details of
            // the chosen template defined by the clicked row.
            this.showTemplate(evt);
        } else if (column == 3) {
            // In column 3, there is a button to restore or remove
            // the chosen template defined by the clicked row.
            this.removeOrRestoreTemplate(evt);
        }
    }

}
