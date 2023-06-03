package weighing.presentation.utils;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Table model for the weighings panel.
 */
public class WeighingsTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public WeighingsTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        // Box column.
        if (columnIndex == 0) {
            return Object.class;
        }

        // Quantity and gross weight columns.
        return Integer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        if (this.isCellEditable(row, column)) {
            switch (column) {
                case 0:
                    if (newValue == null) {
                        // Invalid box, show message.
                        String invalidBoxMessage = Localization.getLocalization(LocalizationKey.INVALID_BOX_MESSAGE);
                        JOptionPane.showMessageDialog(null, invalidBoxMessage);
                        return;
                    }
                    break;
                case 1:
                    if ((int) newValue == 0) {
                        // Invalid quantity, show message.
                        String invalidQtyMessage = Localization.getLocalization(LocalizationKey.INVALID_QTY_MESSAGE);
                        JOptionPane.showMessageDialog(null, invalidQtyMessage);
                        return;
                    }
                    break;
                default:
                    if ((int) newValue == 0) {
                        // Invalid gross weight, show message.
                        String invalidWeightMessage = Localization.getLocalization(LocalizationKey.INVALID_WEIGHT_MESSAGE);
                        JOptionPane.showMessageDialog(null, invalidWeightMessage);
                        return;
                    }
                    break;
            }

            super.setValueAt(newValue, row, column);
        }
    }

}
