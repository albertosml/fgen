package weighing.presentation.utils;

import container.application.Box;
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
     * Calculate the net weight given the row data and the pallet weight. Then,
     * update that net weight for the given row.
     *
     * @param row Table row.
     * @param palletWeight The weight of the pallet.
     */
    public void updateNetWeight(int row, double palletWeight) {
        double boxWeight = 0;
        Box box = (Box) super.getValueAt(row, 0);
        if (box != null) {
            boxWeight = box.getWeight();
        }

        int qty = (int) super.getValueAt(row, 1);
        int grossWeight = (int) super.getValueAt(row, 2);

        int netWeight = (int) (grossWeight - (qty * boxWeight) - palletWeight);
        super.setValueAt(netWeight, row, 3);
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

        // Quantity, gross weight and net weight columns.
        return Integer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        // All the columns will be editable except the net weight.
        return column < 3;
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
                case 2:
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
