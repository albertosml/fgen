package deliverynote.presentation.panels;

import container.application.Container;
import container.application.usecases.ListContainers;
import container.persistence.mongo.MongoContainerRepository;
import deliverynote.application.DeliveryNoteItem;
import deliverynote.application.DeliveryNoteItemAttribute;
import deliverynote.application.usecases.CreateDeliveryNoteItem;
import deliverynote.presentation.utils.DeliveryNoteItemsMouseAdapter;
import deliverynote.presentation.utils.DeliveryNoteItemsTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which shows all the delivery note items.
 */
public class DeliveryNoteItemsPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public DeliveryNoteItemsPanel() {
        initComponents();
        initializeTable();
        initializeLabels();
    }

    /**
     * Initialize panel labels.
     */
    private void initializeLabels() {
        String addName = Localization.getLocalization(LocalizationKey.ADD);
        this.addDeliveryNoteItemButton.setText(addName);

        String itemsName = Localization.getLocalization(LocalizationKey.ITEMS);
        this.items.setText(itemsName);
    }

    /**
     * Generate the column names for the table.
     *
     * @return A vector containing all the identifiers for each table column.
     */
    private Vector<String> generateColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add(Localization.getLocalization(LocalizationKey.CONTAINER));
        columnNames.add(Localization.getLocalization(LocalizationKey.QTY));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Obtain all the containers data by executing the use case.
     *
     * @return A list with all non-removed containers on the system.
     */
    private ArrayList<Container> getContainers() {
        try {
            MongoContainerRepository containerRepository = new MongoContainerRepository();
            ListContainers listContainers = new ListContainers(containerRepository);
            return listContainers.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = DeliveryNoteItemsPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Containers cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        table.setModel(new DeliveryNoteItemsTableModel(new Vector<>(), columnNames));
        table.addMouseListener(new DeliveryNoteItemsMouseAdapter(table));

        // Set a combobox cell editor for the container column.
        TableColumn containerColumn = table.getColumn(columnNames.get(0));
        JComboBox comboBox = new JComboBox(new Vector<Container>(this.getContainers()));
        containerColumn.setCellEditor(new DefaultCellEditor(comboBox));

        // Set a button renderer for the remove button.
        TableColumn removeColumn = table.getColumn(columnNames.get(2));
        removeColumn.setCellRenderer(new ButtonRenderer());
    }

    /**
     * Execute the add delivery note item use case.
     *
     * @param deliveryNoteItemAttributes A map with all attributes to add on the
     * delivery note item.
     */
    private void addDeliveryNoteItem(Map<DeliveryNoteItemAttribute, Object> deliveryNoteItemAttributes) {
        CreateDeliveryNoteItem createDeliveryNoteItem = new CreateDeliveryNoteItem();
        DeliveryNoteItem deliveryNoteItem = createDeliveryNoteItem.execute(deliveryNoteItemAttributes);

        if (deliveryNoteItem == null) {
            String invalidDeliveryNoteItemMessage = Localization.getLocalization(LocalizationKey.INVALID_DELIVERY_NOTE_ITEM_MESSAGE);
            JOptionPane.showMessageDialog(this, invalidDeliveryNoteItemMessage);
        } else {
            Container container = deliveryNoteItem.getContainer();
            int qty = deliveryNoteItem.getQty();
            String removeLabel = Localization.getLocalization(LocalizationKey.REMOVE);

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.addRow(new Object[]{container, qty, removeLabel});
        }
    }

    /**
     * Clear the content from the table.
     */
    public void clear() {
        table.removeAll();
    }

    /**
     * Get the registered delivery note items.
     *
     * @return A list with all the delivery note items set on the table.
     */
    public ArrayList<DeliveryNoteItem> getItems() {
        ArrayList<DeliveryNoteItem> deliveryNoteItems = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            Container container = (Container) table.getValueAt(i, 0);
            int qty = (int) table.getValueAt(i, 1);

            Map<DeliveryNoteItemAttribute, Object> attributes = new HashMap<>();
            attributes.put(DeliveryNoteItemAttribute.CONTAINER, container);
            attributes.put(DeliveryNoteItemAttribute.QTY, qty);

            DeliveryNoteItem item = DeliveryNoteItem.from(attributes);
            deliveryNoteItems.add(item);
        }

        return deliveryNoteItems;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addDeliveryNoteItemButton = new javax.swing.JButton();
        items = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${CONTAINER}", "${QTY}", ""
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

        addDeliveryNoteItemButton.setText("${ADD}");
        addDeliveryNoteItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDeliveryNoteItemButtonActionPerformed(evt);
            }
        });

        items.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        items.setText("${ITEMS}");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(items, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addDeliveryNoteItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addDeliveryNoteItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(items))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addDeliveryNoteItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDeliveryNoteItemButtonActionPerformed
        JPanel addDeliveryNoteItemPanel = new JPanel();

        // Container text.
        String containerLabel = Localization.getLocalization(LocalizationKey.CONTAINER);
        String formattedContainerLabel = String.format("%s:", containerLabel);
        addDeliveryNoteItemPanel.add(new JLabel(formattedContainerLabel));

        // Container combo box.
        JComboBox containersComboBox = new JComboBox(new Vector<Container>(this.getContainers()));
        addDeliveryNoteItemPanel.add(containersComboBox);

        // Spacer.
        addDeliveryNoteItemPanel.add(Box.createHorizontalStrut(15));

        // Quantity text.
        String qtyLabel = Localization.getLocalization(LocalizationKey.QTY);
        String formattedQtyLabel = String.format("%s:", qtyLabel);
        addDeliveryNoteItemPanel.add(new JLabel(formattedQtyLabel));

        // Quantity spinner.
        SpinnerNumberModel qtySpinnerNumberModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner qty = new JSpinner(qtySpinnerNumberModel);
        addDeliveryNoteItemPanel.add(qty);

        String addLabel = Localization.getLocalization(LocalizationKey.ADD);
        String itemLabel = Localization.getLocalization(LocalizationKey.ITEM);
        String addDeliveryNoteItemMessage = String.format("%s %s", addLabel, itemLabel);

        int result = JOptionPane.showConfirmDialog(null, addDeliveryNoteItemPanel,
                addDeliveryNoteItemMessage, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Map<DeliveryNoteItemAttribute, Object> deliveryNoteItemAttributes = new HashMap<>();
            deliveryNoteItemAttributes.put(DeliveryNoteItemAttribute.CONTAINER, containersComboBox.getSelectedItem());
            deliveryNoteItemAttributes.put(DeliveryNoteItemAttribute.QTY, qty.getValue());

            this.addDeliveryNoteItem(deliveryNoteItemAttributes);
        }
    }//GEN-LAST:event_addDeliveryNoteItemButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDeliveryNoteItemButton;
    private javax.swing.JLabel items;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
