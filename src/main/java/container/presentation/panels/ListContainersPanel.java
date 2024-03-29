package container.presentation.panels;

import container.application.Container;
import container.persistence.mongo.MongoContainerRepository;
import container.application.usecases.ListContainers;
import container.presentation.utils.ListContainersMouseAdapter;
import container.presentation.utils.ListContainersTableModel;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which lists all the containers.
 */
public class ListContainersPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListContainersPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the containers data by executing the use case.
     *
     * @return A list with all containers on the system.
     */
    private ArrayList<Container> getContainers() {
        try {
            MongoContainerRepository containerRepository = new MongoContainerRepository();
            ListContainers listContainers = new ListContainers(containerRepository);
            return listContainers.execute(true);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListContainersPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Containers cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Generate the column names for the table.
     *
     * @return A vector containing all the identifiers for each table column.
     */
    private Vector<String> generateColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add(Localization.getLocalization(LocalizationKey.CODE));
        columnNames.add(Localization.getLocalization(LocalizationKey.NAME));
        columnNames.add(Localization.getLocalization(LocalizationKey.WEIGHT));
        columnNames.add(Localization.getLocalization(LocalizationKey.IS_BOX));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Set the table data based on the given containers.
     *
     * @param containers An array list containing all the containers on the
     * system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<Container> containers) {
        Vector<Vector<Object>> data = new Vector<>();

        for (Container container : containers) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Container code.
            int containerCode = container.getCode();
            rowData.add(containerCode);

            // Column 2: Container name.
            String containerName = container.getName();
            rowData.add(containerName);

            // Column 3: Container weight.
            double containerWeight = container.getWeight();
            rowData.add(containerWeight);

            // Column 4: Is it a box or a pallet?
            boolean containerIsBox = container.isBox();
            rowData.add(containerIsBox);

            // Column 5: Empty name. It will show a button to remove or restore
            // a customer.
            String removeName = Localization.getLocalization(LocalizationKey.REMOVE);
            String restoreName = Localization.getLocalization(LocalizationKey.RESTORE);
            String removeRestoreButtonText = container.isDeleted() ? restoreName : removeName;
            rowData.add(removeRestoreButtonText);

            data.add(rowData);
        }

        return data;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<Container> containers = this.getContainers();

        if (containers != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(containers);

            table.setModel(new ListContainersTableModel(data, columnNames));

            table.addMouseListener(new ListContainersMouseAdapter(table));

            // Set a button renderer for the action button.
            TableColumn removeRestoreContainerColumn = table.getColumn(columnNames.get(4));
            removeRestoreContainerColumn.setCellRenderer(new ButtonRenderer());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${CODE}", "${NAME}", "${WEIGHT}", "${IS_BOX}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
