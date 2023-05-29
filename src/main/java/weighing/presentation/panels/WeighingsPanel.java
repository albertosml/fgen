package weighing.presentation.panels;

import container.application.Box;
import container.application.Container;
import container.application.usecases.ListBoxes;
import container.application.usecases.ListContainers;
import container.persistence.mongo.MongoContainerRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;
import weighing.application.Weighing;
import weighing.application.WeighingAttribute;
import weighing.presentation.utils.WeighingsTableModel;

/**
 * Panel which shows all the delivery note items.
 */
public class WeighingsPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public WeighingsPanel() {
        initComponents();
        initializeTable();
        initializeLabels();
    }

    /**
     * Initialize panel labels.
     */
    private void initializeLabels() {
        String weighingsName = Localization.getLocalization(LocalizationKey.WEIGHINGS);
        this.weighings.setText(weighingsName);
    }

    /**
     * Generate the column names for the table.
     *
     * @return A vector containing all the identifiers for each table column.
     */
    private Vector<String> generateColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add(Localization.getLocalization(LocalizationKey.BOX));
        columnNames.add(Localization.getLocalization(LocalizationKey.BOXES_QTY));
        columnNames.add(Localization.getLocalization(LocalizationKey.GROSS_WEIGHT));

        return columnNames;
    }

    /**
     * Obtain all the boxes data by executing the use case.
     *
     * @return A list with all non-removed boxes on the system.
     */
    private ArrayList<Box> getBoxes() {
        try {
            MongoContainerRepository containerRepository = new MongoContainerRepository();
            ListBoxes listBoxes = new ListBoxes(containerRepository);
            return listBoxes.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = WeighingsPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Boxes cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        table.setModel(new WeighingsTableModel(new Vector<>(), columnNames));

        // Set a combobox cell editor for the box column.
        TableColumn boxColumn = table.getColumn(columnNames.get(0));
        JComboBox comboBox = new JComboBox(new Vector<Box>(this.getBoxes()));
        boxColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    /**
     * Set the total number of weighings on the table.
     *
     * @param newValue The new total value.
     */
    public void setTotalWeighings(int newValue) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int diff = newValue - tableModel.getRowCount();

        if (diff > 0) {
            // If we require more rows, we add them.
            for (int i = 0; i < diff; i++) {
                tableModel.addRow(new Object[]{null, 0, 0});
            }
        } else if (diff < 0) {
            // If we require less rows, we remove the latest.
            for (int i = 0; i < Math.abs(diff); i++) {
                tableModel.removeRow(table.getRowCount() - 1);
            }
        }
    }

    /**
     * Clear weighings data.
     */
    public void clear() {
        table.removeAll();
    }

    /**
     * Get the weighings from the table.
     *
     * @return A list with all weighings from the table.
     */
    public ArrayList<Weighing> getWeighings() {
        ArrayList<Weighing> weighings = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            Box box = (Box) table.getValueAt(i, 0);
            int qty = (int) table.getValueAt(i, 1);
            Object weightValue = table.getValueAt(i, 2);
            double weight = Double.parseDouble(weightValue.toString());

            Map<WeighingAttribute, Object> attributes = new HashMap<>();
            attributes.put(WeighingAttribute.BOX, box);
            attributes.put(WeighingAttribute.QTY, qty);
            attributes.put(WeighingAttribute.WEIGHT, weight);

            Weighing weighing = Weighing.from(attributes);
            weighings.add(weighing);
        }

        return weighings;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        weighings = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${BOX}", "${BOXES_QTY}", "${GROSS_WEIGHT}"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        weighings.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        weighings.setText("${WEIGHINGS}");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addComponent(weighings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(weighings)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    private javax.swing.JLabel weighings;
    // End of variables declaration//GEN-END:variables

}
