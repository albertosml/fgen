package template.presentation.utils;

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
import template.application.usecases.RemoveTemplate;
import template.application.usecases.RestoreTemplate;
import template.persistence.mongo.MongoTemplateRepository;

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
     * Execute the remove template use case.
     *
     * @param code The code of the template to remove.
     * @return Whether the template has been removed or not.
     */
    private boolean removeTemplate(int code) {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            RemoveTemplate removeTemplate = new RemoveTemplate(templateRepository);
            return removeTemplate.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListTemplatesMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Template not removed because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Execute the restore template use case.
     *
     * @param code The code of the template to restore.
     * @return Whether the template has been restored or not.
     */
    private boolean restoreTemplate(int code) {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            RestoreTemplate restoreTemplate = new RestoreTemplate(templateRepository);
            return restoreTemplate.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListTemplatesMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Template not restored because the database has not been found", ex);
        }

        return false;
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
        
        int templateCode = row + 1;

        if (cellText.equals(removeText)) {
            boolean isTemplateRemoved = this.removeTemplate(templateCode);
            
            if (isTemplateRemoved) {
                // Button state needs to be updated as the template deletion state has changed.
                tableModel.setValueAt(restoreText, row, removeRestoreColumn);
            }
        } else {
            boolean isTemplateRestored = this.restoreTemplate(templateCode);
            
            if (isTemplateRestored) {
                // Button state needs to be updated as the template deletion state has changed.
                tableModel.setValueAt(removeText, row, removeRestoreColumn);
            }
        }
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
