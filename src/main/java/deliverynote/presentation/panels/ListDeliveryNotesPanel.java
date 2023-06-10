package deliverynote.presentation.panels;

import customer.application.Customer;
import customer.presentation.utils.ListCustomersMouseAdapter;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.ListDeliveryNotes;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import deliverynote.presentation.utils.ListDeliveryNotesMouseAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.ButtonRenderer;

/**
 * Panel which lists all the delivery notes.
 */
public class ListDeliveryNotesPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public ListDeliveryNotesPanel() {
        initComponents();
        initializeTable();
    }

    /**
     * Obtain all the delivery notes data by executing the use case.
     *
     * @return A list with all available delivery notes data on the system.
     */
    private ArrayList<DeliveryNoteData> getDeliveryNotesData() {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            ListDeliveryNotes listDeliveryNotes = new ListDeliveryNotes(deliveryNoteRepository);
            return listDeliveryNotes.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListDeliveryNotesPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Delivery notes cannot be shown because the database has not been found", ex);
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
        columnNames.add(Localization.getLocalization(LocalizationKey.DATE));
        columnNames.add(Localization.getLocalization(LocalizationKey.CUSTOMER));
        columnNames.add(Localization.getLocalization(LocalizationKey.PRODUCT));
        // Both columns does not contain a name, so they will contain the same
        // identifier. However, they need a different identification for the
        // retrieval. To solve that problem, a different empty string will be
        // set for them.
        columnNames.add("");
        columnNames.add(" ");

        return columnNames;
    }

    /**
     * Set the table data based on the given customers.
     *
     * @param deliveryNotesData An array list containing all the available
     * delivery notes data on the system.
     * @return A matrix containing all the table data.
     */
    private Vector<Vector<Object>> setTableData(ArrayList<DeliveryNoteData> deliveryNotesData) {
        Vector<Vector<Object>> data = new Vector<>();

        for (DeliveryNoteData deliveryNoteData : deliveryNotesData) {
            Vector<Object> rowData = new Vector<>();

            // Column 1: Delivery note data code.
            int code = deliveryNoteData.getCode();
            rowData.add(code);

            // Column 2: Delivery note data generation datetime.
            String pattern = "dd-MM-yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date date = deliveryNoteData.getDate();
            rowData.add(df.format(date));

            // Column 3: Delivery note data customer.
            rowData.add(deliveryNoteData.getCustomer().toString());

            // Column 4: Delivery note data product.
            rowData.add(deliveryNoteData.getProduct().toString());

            // Column 5: Empty name. It will show a button to download the delivery note.
            String downloadName = Localization.getLocalization(LocalizationKey.DOWNLOAD);
            rowData.add(downloadName);

            // Column 6: Empty name with a whitespace. It will show a button to
            // print the delivery note.
            String printName = Localization.getLocalization(LocalizationKey.PRINT);
            rowData.add(printName);

            data.add(rowData);
        }

        return data;
    }

    /**
     * Initialize the table.
     */
    private void initializeTable() {
        ArrayList<DeliveryNoteData> deliveryNotesData = this.getDeliveryNotesData();

        if (deliveryNotesData != null) {
            Vector<String> columnNames = this.generateColumnNames();
            Vector<Vector<Object>> data = this.setTableData(deliveryNotesData);

            TableModel dataModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table.setModel(dataModel);

            table.addMouseListener(new ListDeliveryNotesMouseAdapter(table, deliveryNotesData));

            // Set a button renderer for the action buttons.
            TableColumn downloadColumn = table.getColumn(columnNames.get(4));
            downloadColumn.setCellRenderer(new ButtonRenderer());

            TableColumn printColumn = table.getColumn(columnNames.get(5));
            printColumn.setCellRenderer(new ButtonRenderer());
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
                "${CODE}", "${DATE}", "${CUSTOMER}", "${PRODUCT}", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(5).setResizable(false);
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
