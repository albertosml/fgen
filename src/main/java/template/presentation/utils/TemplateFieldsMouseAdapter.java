package template.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import template.presentation.panels.TemplateFieldsPanel;

/**
 * Mouse adapter for the template fields panel, which includes a button to
 * remove a template field.
 */
public class TemplateFieldsMouseAdapter extends MouseAdapter {

    /**
     * Whether the template is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     * @param isDeleted Whether the template is deleted or not.
     */
    public TemplateFieldsMouseAdapter(JTable table, boolean isDeleted) {
        this.table = table;
        this.isDeleted = isDeleted;
    }

    /**
     * Remove the template field.
     *
     * @param evt The mouse event.
     */
    private void removeTemplateField(MouseEvent evt) {
        // A template field can be only removed if the template is not deleted.
        if (this.isDeleted) {
            return;
        }

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
            // In column 2, there is a button to remove the chosen template
            // field defined by the clicked row.
            this.removeTemplateField(evt);
        }
    }

}
