package variable.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import variable.application.usecases.RemoveVariable;
import variable.application.usecases.RestoreVariable;
import variable.persistence.mongo.MongoVariableRepository;

/**
 * Mouse adapter for the list variables panel, which includes a button to remove
 * or restore a variable.
 */
public class ListVariablesMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListVariablesMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Execute the remove variable use case.
     *
     * @param name The name of the variable to remove.
     * @return Whether the variable with the given code has been removed or not.
     */
    private boolean removeVariable(String name) {
        try {
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            RemoveVariable removeVariable = new RemoveVariable(variableRepository);
            return removeVariable.execute(name);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListVariablesMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Variable not removed because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Execute the restore variable use case.
     *
     * @param name The name of the variable to restore.
     * @return Whether the variable with the given code has been restored or
     * not.
     */
    private boolean restoreVariable(String name) {
        try {
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            RestoreVariable restoreVariable = new RestoreVariable(variableRepository);
            return restoreVariable.execute(name);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListVariablesMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Variable not restored because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Remove or restore the variable depending on the deletion state.
     *
     * @param evt The mouse event.
     */
    private void removeOrRestoreVariable(MouseEvent evt) {
        int removeRestoreColumn = 4;
        int row = table.rowAtPoint(evt.getPoint());

        TableModel tableModel = table.getModel();

        String cellText = (String) tableModel.getValueAt(row, removeRestoreColumn);
        String removeText = Localization.getLocalization(LocalizationKey.REMOVE);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);

        String variableName = (String) table.getValueAt(row, 0);
        String finalVariableName = variableName.substring(2, variableName.length() - 1);

        if (cellText.equals(removeText)) {
            boolean isVariableRemoved = this.removeVariable(finalVariableName);

            if (isVariableRemoved) {
                // Button state needs to be updated as the variable state has changed.
                tableModel.setValueAt(restoreText, row, removeRestoreColumn);
            }
        } else {
            boolean isVariableRestored = this.restoreVariable(finalVariableName);

            if (isVariableRestored) {
                // Button state needs to be updated as the variable state has changed.
                tableModel.setValueAt(removeText, row, removeRestoreColumn);
            }
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
            // the chosen variable defined by the clicked row.
            this.removeOrRestoreVariable(evt);
        }
    }

}
