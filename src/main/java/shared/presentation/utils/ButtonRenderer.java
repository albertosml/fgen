package shared.presentation.utils;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * It is responsible of the button rendering.
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    /**
     * Constructor.
     */
    public ButtonRenderer() {
        this.setOpaque(true);
    }

    /**
     * Constructor.
     *
     * @param isEnabled Whether the button must be enabled or not.
     */
    public ButtonRenderer(boolean isEnabled) {
        this.setOpaque(true);
        this.setEnabled(isEnabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.setText((value == null) ? "" : value.toString());
        return this;
    }

}
