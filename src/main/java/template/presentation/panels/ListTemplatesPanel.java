package template.presentation.panels;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;
import template.application.Template;
import template.application.usecases.ListTemplates;
import template.persistence.mongo.MongoTemplateRepository;
import template.presentation.utils.ListTemplatesMouseAdapter;
import template.presentation.utils.ListTemplatesTableModel;

/**
 * Panel which lists all the templates.
 */
public class ListTemplatesPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListTemplatesPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the templates data by executing the use case.
     *
     * @return A list with all templates on the system.
     */
    private ArrayList<Template> getTemplates() {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            ListTemplates listTemplates = new ListTemplates(templateRepository);
            return listTemplates.execute(true);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListTemplatesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Templates cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Set the table data based on the given templates.
     *
     * @param templates An array list containing all the templates on the
     * system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<Template> templates) {
        Vector<Vector<Object>> data = new Vector<>();

        for (Template template : templates) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Template code.
            int templateCode = template.getCode();
            rowData.add(templateCode);

            // Column 2: Template name.
            String templateName = template.getName();
            rowData.add(templateName);

            // Column 3: Empty name. It will be composed by a button to show a
            // template.
            String showName = Localization.getLocalization(LocalizationKey.SHOW);
            rowData.add(showName);

            // Column 4: Empty name. It will show a button to remove or restore
            // a template.
            String removeName = Localization.getLocalization(LocalizationKey.REMOVE);
            String restoreName = Localization.getLocalization(LocalizationKey.RESTORE);
            String removeRestoreButtonText = template.isDeleted() ? restoreName : removeName;
            rowData.add(removeRestoreButtonText);

            data.add(rowData);
        }

        return data;
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
        // Both columns does not contain a name, so they will contain the same
        // identifier. However, they need a different identification for the
        // retrieval. To solve that problem, a different empty string will be
        // set for them.
        columnNames.add("");
        columnNames.add(" ");

        return columnNames;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<Template> templates = this.getTemplates();

        if (templates != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(templates);

            table.setModel(new ListTemplatesTableModel(data, columnNames));
            table.addMouseListener(new ListTemplatesMouseAdapter(table));

            // Set a button renderer for the show button.
            TableColumn showColumn = table.getColumn(columnNames.get(2));
            showColumn.setCellRenderer(new ButtonRenderer());

            // Set a button renderer for the remove/restore button.
            TableColumn removeRestoreColumn = table.getColumn(columnNames.get(3));
            removeRestoreColumn.setCellRenderer(new ButtonRenderer());
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
                "${CODE}", "${NAME}", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
