package template.presentation.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;
import template.application.TemplateField;
import template.application.TemplateFieldAttribute;
import template.application.usecases.CreateTemplateField;
import template.presentation.utils.TemplateFieldsMouseAdapter;
import template.presentation.utils.TemplateFieldsTableModel;

/**
 * Panel which shows all the template fields.
 */
public class TemplateFieldsPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public TemplateFieldsPanel() {
        initComponents();
        initializeTable(false);
        initializeLabels();
    }

    /**
     * Constructor.
     *
     * @param fields Map with all template fields data.
     * @param isDeleted Whether the template is deleted or not.
     */
    public TemplateFieldsPanel(Map<String, String> fields, boolean isDeleted) {
        initComponents();
        initializeTable(isDeleted);
        initializeLabels();
        initializeTemplateFields(fields);

        if (isDeleted) {
            disableInputs();
        }
    }

    /**
     * Initialize panel labels.
     */
    private void initializeLabels() {
        String addName = Localization.getLocalization(LocalizationKey.ADD);
        this.addTemplateFieldButton.setText(addName);

        String fieldsName = Localization.getLocalization(LocalizationKey.FIELDS);
        this.fields.setText(fieldsName);
    }

    /**
     * Generate the column names for the table.
     *
     * @return A vector containing all the identifiers for each table column.
     */
    private Vector<String> generateColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add(Localization.getLocalization(LocalizationKey.POSITION));
        columnNames.add(Localization.getLocalization(LocalizationKey.EXPRESSION));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Initialize the table.
     *
     * @param isDeleted Whether the template is deleted or not.
     */
    private void initializeTable(boolean isDeleted) {
        Vector<String> columnNames = this.generateColumnNames();

        table.setModel(new TemplateFieldsTableModel(new Vector<>(), columnNames, isDeleted));
        table.addMouseListener(new TemplateFieldsMouseAdapter(table, isDeleted));

        // Set a button renderer for the remove button.
        TableColumn removeColumn = table.getColumn(columnNames.get(2));
        removeColumn.setCellRenderer(new ButtonRenderer(!isDeleted));
    }

    /**
     * Execute the add template field use case.
     *
     * @param templateFieldAttributes A map with all attributes to add on the
     * template field.
     */
    private void addTemplateField(Map<TemplateFieldAttribute, String> templateFieldAttributes) {
        CreateTemplateField createTemplateField = new CreateTemplateField();
        TemplateField templateField = createTemplateField.execute(templateFieldAttributes);

        if (templateField == null) {
            String invalidTemplateFieldMessage = Localization.getLocalization(LocalizationKey.INVALID_TEMPLATE_FIELD_MESSAGE);
            JOptionPane.showMessageDialog(this, invalidTemplateFieldMessage);
        } else {
            String position = templateField.getPosition();
            String expression = templateField.getExpression();
            String removeLabel = Localization.getLocalization(LocalizationKey.REMOVE);

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.addRow(new Object[]{position, expression, removeLabel});
        }
    }

    /**
     * Initialize the template fields.
     *
     * @param fields Map with all template fields data.
     */
    private void initializeTemplateFields(Map<String, String> templateFields) {
        for (Map.Entry<String, String> field : templateFields.entrySet()) {
            Map<TemplateFieldAttribute, String> templateFieldAttributes = new HashMap<>();
            templateFieldAttributes.put(TemplateFieldAttribute.POSITION, field.getKey());
            templateFieldAttributes.put(TemplateFieldAttribute.EXPRESSION, field.getValue());

            this.addTemplateField(templateFieldAttributes);
        }
    }

    /**
     * Disable inputs.
     */
    private void disableInputs() {
        this.addTemplateFieldButton.setEnabled(false);
    }

    /**
     * Get the registered template fields.
     *
     * @return A list with all template fields set on the table.
     */
    public ArrayList<TemplateField> getFields() {
        ArrayList<TemplateField> templateFields = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            String position = (String) table.getValueAt(i, 0);
            String expression = (String) table.getValueAt(i, 1);

            Map<TemplateFieldAttribute, String> attributes = new HashMap<>();
            attributes.put(TemplateFieldAttribute.POSITION, position);
            attributes.put(TemplateFieldAttribute.EXPRESSION, expression);

            TemplateField field = TemplateField.from(attributes);
            templateFields.add(field);
        }

        return templateFields;
    }

    /**
     * Clean the table for the template fields.
     */
    public void cleanTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addTemplateFieldButton = new javax.swing.JButton();
        fields = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${POSITION}", "${EXPRESSION}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        addTemplateFieldButton.setText("${ADD}");
        addTemplateFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTemplateFieldButtonActionPerformed(evt);
            }
        });

        fields.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        fields.setText("${FIELDS}");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addTemplateFieldButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTemplateFieldButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fields))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addTemplateFieldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTemplateFieldButtonActionPerformed
        JTextField position = new JTextField(10);
        JTextField expression = new JTextField(30);

        String positionLabel = Localization.getLocalization(LocalizationKey.POSITION);
        String formattedPositionLabel = String.format("%s:", positionLabel);
        String expressionLabel = Localization.getLocalization(LocalizationKey.EXPRESSION);
        String formattedExpressionLabel = String.format("%s:", expressionLabel);

        JPanel addTemplateFieldsPanel = new JPanel();
        addTemplateFieldsPanel.add(new JLabel(formattedPositionLabel));
        addTemplateFieldsPanel.add(position);
        addTemplateFieldsPanel.add(Box.createHorizontalStrut(15)); // a spacer
        addTemplateFieldsPanel.add(new JLabel(formattedExpressionLabel));
        addTemplateFieldsPanel.add(expression);

        String addLabel = Localization.getLocalization(LocalizationKey.ADD);
        String fieldLabel = Localization.getLocalization(LocalizationKey.FIELD);
        String addTemplateFieldMessage = String.format("%s %s", addLabel, fieldLabel);

        int result = JOptionPane.showConfirmDialog(null, addTemplateFieldsPanel,
                addTemplateFieldMessage, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Map<TemplateFieldAttribute, String> templateFieldAttributes = new HashMap<>();
            templateFieldAttributes.put(TemplateFieldAttribute.POSITION, position.getText());
            templateFieldAttributes.put(TemplateFieldAttribute.EXPRESSION, expression.getText());

            this.addTemplateField(templateFieldAttributes);
        }
    }//GEN-LAST:event_addTemplateFieldButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTemplateFieldButton;
    private javax.swing.JLabel fields;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
