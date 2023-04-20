package container.presentation.utils;

import container.application.usecases.RemoveContainer;
import container.persistence.mongo.MongoContainerRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Mouse adapter for the list containers panel, which includes a button to
 * remove or restore a container.
 */
public class ListContainersMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListContainersMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Execute the remove container use case.
     *
     * @param code The code of the container to remove.
     * @return Whether the container with the given code has been removed or
     * not.
     */
    private boolean removeContainer(int code) {
        try {
            MongoContainerRepository containerRepository = new MongoContainerRepository();
            RemoveContainer removeContainer = new RemoveContainer(containerRepository);
            return removeContainer.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListContainersMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Container not removed because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Remove or restore the container depending on the deletion state.
     *
     * @param evt The mouse event.
     */
    private void removeOrRestoreContainer(MouseEvent evt) {
        int removeRestoreColumn = 3;
        int row = table.rowAtPoint(evt.getPoint());

        TableModel tableModel = table.getModel();

        String cellText = (String) tableModel.getValueAt(row, removeRestoreColumn);
        String removeText = Localization.getLocalization(LocalizationKey.REMOVE);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);

        int containerCode = (int) tableModel.getValueAt(row, 0);

        if (cellText.equals(removeText)) {
            boolean isContainerRemoved = this.removeContainer(containerCode);

            if (isContainerRemoved) {
                // Button state needs to be updated as the container state has changed.
                tableModel.setValueAt(restoreText, row, removeRestoreColumn);
            }
        } else {
            // Button state needs to be updated as the container state has changed.
            tableModel.setValueAt(removeText, row, removeRestoreColumn);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int column = table.columnAtPoint(evt.getPoint());

        if (column == 3) {
            // In column 3, there is a button to restore or remove
            // the chosen contianer defined by the clicked row.
            this.removeOrRestoreContainer(evt);
        }
    }

}
