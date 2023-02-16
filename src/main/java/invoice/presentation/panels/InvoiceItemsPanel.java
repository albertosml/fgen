package invoice.presentation.panels;

import invoice.application.InvoiceItem;
import invoice.application.InvoiceItemAttribute;
import invoice.application.usecases.CreateInvoiceItem;
import invoice.presentation.utils.InvoiceItemsTableModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which shows all the invoice items.
 */
public class InvoiceItemsPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public InvoiceItemsPanel() {
        initComponents();
        initializeTable();
        initializeLabels();
    }

    /**
     * Initialize panel labels.
     */
    private void initializeLabels() {
        String addName = Localization.getLocalization(LocalizationKey.ADD);
        this.addInvoiceItemButton.setText(addName);

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
        columnNames.add(Localization.getLocalization(LocalizationKey.QTY));
        columnNames.add(Localization.getLocalization(LocalizationKey.DESCRIPTION));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRICE));
        columnNames.add("");

        return columnNames;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        Vector<String> columnNames = this.generateColumnNames();

        table.setModel(new InvoiceItemsTableModel(new Vector<>(), columnNames));

        // Set a button renderer for the remove button.
        TableColumn removeColumn = table.getColumn(columnNames.get(3));
        removeColumn.setCellRenderer(new ButtonRenderer());
    }

    /**
     * Execute the add invoice item use case.
     *
     * @param invoiceItemAttributes A map with all attributes to add on the
     * invoice item.
     */
    private void addInvoiceItem(Map<InvoiceItemAttribute, Object> invoiceItemAttributes) {
        CreateInvoiceItem createInvoiceItem = new CreateInvoiceItem();
        InvoiceItem invoiceItem = createInvoiceItem.execute(invoiceItemAttributes);

        if (invoiceItem == null) {
            String invalidInvoiceItemMessage = Localization.getLocalization(LocalizationKey.INVALID_INVOICE_ITEM_MESSAGE);
            JOptionPane.showMessageDialog(this, invalidInvoiceItemMessage);
        } else {
            int qty = invoiceItem.getQty();
            String description = invoiceItem.getDescription();
            double price = invoiceItem.getPrice();
            String removeLabel = Localization.getLocalization(LocalizationKey.REMOVE);

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            tableModel.addRow(new Object[]{qty, description, price, removeLabel});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addInvoiceItemButton = new javax.swing.JButton();
        items = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "${QTY}", "${DESCRIPTION}", "${PRICE}", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.String.class, java.lang.Float.class, java.lang.Object.class
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

        addInvoiceItemButton.setText("${ADD}");
        addInvoiceItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addInvoiceItemButtonActionPerformed(evt);
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
                        .addComponent(addInvoiceItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addInvoiceItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(items))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addInvoiceItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addInvoiceItemButtonActionPerformed
        SpinnerNumberModel qtySpinnerNumberModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner qty = new JSpinner(qtySpinnerNumberModel);
        JTextField description = new JTextField(10);
        SpinnerNumberModel priceSpinnerNumberModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 0.1);
        JSpinner price = new JSpinner(priceSpinnerNumberModel);

        String qtyLabel = Localization.getLocalization(LocalizationKey.QTY);
        String formattedQtyLabel = String.format("%s:", qtyLabel);
        String descriptionLabel = Localization.getLocalization(LocalizationKey.DESCRIPTION);
        String formattedDescriptionLabel = String.format("%s:", descriptionLabel);
        String priceLabel = Localization.getLocalization(LocalizationKey.PRICE);
        String formattedPriceLabel = String.format("%s:", priceLabel);

        JPanel addInvoiceItemPanel = new JPanel();
        addInvoiceItemPanel.add(new JLabel(formattedQtyLabel));
        addInvoiceItemPanel.add(qty);
        addInvoiceItemPanel.add(Box.createHorizontalStrut(15)); // a spacer
        addInvoiceItemPanel.add(new JLabel(formattedDescriptionLabel));
        addInvoiceItemPanel.add(description);
        addInvoiceItemPanel.add(Box.createHorizontalStrut(15)); // a spacer
        addInvoiceItemPanel.add(new JLabel(formattedPriceLabel));
        addInvoiceItemPanel.add(price);

        String addLabel = Localization.getLocalization(LocalizationKey.ADD);
        String itemLabel = Localization.getLocalization(LocalizationKey.ITEM);
        String addInvoiceItemMessage = String.format("%s %s", addLabel, itemLabel);

        int result = JOptionPane.showConfirmDialog(null, addInvoiceItemPanel,
                addInvoiceItemMessage, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Map<InvoiceItemAttribute, Object> invoiceItemAttributes = new HashMap<>();
            invoiceItemAttributes.put(InvoiceItemAttribute.QTY, qty.getValue());
            invoiceItemAttributes.put(InvoiceItemAttribute.DESCRIPTION, description.getText());
            invoiceItemAttributes.put(InvoiceItemAttribute.PRICE, price.getValue());

            this.addInvoiceItem(invoiceItemAttributes);
        }
    }//GEN-LAST:event_addInvoiceItemButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addInvoiceItemButton;
    private javax.swing.JLabel items;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
