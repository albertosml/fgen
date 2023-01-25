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
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public TemplateFieldsMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Remove the template field.
     *
     * @param evt The mouse event.
     */
    private void removeTemplateField(MouseEvent evt) {
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
