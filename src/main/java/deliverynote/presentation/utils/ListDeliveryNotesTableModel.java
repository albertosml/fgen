package deliverynote.presentation.utils;

import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.RemoveDeliveryNote;
import deliverynote.application.usecases.UpdateDeliveryNote;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.utils.Downloader;
import shared.presentation.utils.Printer;

/**
 * Table model for the list delivery notes panel.
 */
public class ListDeliveryNotesTableModel extends DefaultTableModel {

    /**
     * Loaded delivery notes data.
     */
    private ArrayList<DeliveryNoteData> deliveryNotesData;

    /**
     * Table associated to the table model.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     * @param table The table associated to the table model.
     */
    public ListDeliveryNotesTableModel(Vector<? extends Vector> data, Vector<?> columnNames, JTable table) {
        super(data, columnNames);
        this.table = table;
        this.deliveryNotesData = new ArrayList<>();
    }

    /**
     * Find the delivery note data by the given code.
     *
     * @param code The delivery note data code.
     * @return The found delivery note data code, otherwise null.
     */
    private DeliveryNoteData findDeliveryNoteData(int code) {
        for (DeliveryNoteData deliveryNoteData : this.deliveryNotesData) {
            if (deliveryNoteData.getCode() == code) {
                return deliveryNoteData;
            }
        }

        return null;
    }

    /**
     * Download the given delivery note.
     *
     * @param deliveryNoteData The delivery note data.
     */
    private void downloadDeliveryNote(DeliveryNoteData deliveryNoteData) {
        String documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        String deliveryNoteName = Localization.getLocalization(LocalizationKey.DELIVERY_NOTE);
        String filename = String.format("%s_%d.pdf", deliveryNoteName, deliveryNoteData.getCode());
        File destFile = new File(documentsPath, filename);

        boolean isDownloaded = Downloader.downloadDeliveryNote(deliveryNoteData.getFile(), destFile);

        String message;
        if (isDownloaded) {
            String downloadedFileMessage = Localization.getLocalization(LocalizationKey.DOWNLOADED_FILE_MESSAGE);
            message = String.format("%s: %s/%s", downloadedFileMessage, documentsPath, filename);
        } else {
            message = Localization.getLocalization(LocalizationKey.DOWNLOAD_ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(table, message);
    }

    /**
     * Print the given delivery note.
     *
     * @param deliveryNoteData The delivery note data.
     */
    private void printDeliveryNote(DeliveryNoteData deliveryNoteData) {
        boolean hasBeenPrinted = Printer.printDeliveryNote(deliveryNoteData.getFile());

        String message;
        if (hasBeenPrinted) {
            message = Localization.getLocalization(LocalizationKey.PRINTED_FILE_MESSAGE);
        } else {
            message = Localization.getLocalization(LocalizationKey.PRINT_ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(table, message);
    }

    /**
     * Remove the given delivery note.
     *
     * @param deliveryNoteData The delivery note data.
     * @param tableRow The table row index.
     */
    public void removeDeliveryNote(DeliveryNoteData deliveryNoteData, int tableRow) {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            RemoveDeliveryNote removeDeliveryNote = new RemoveDeliveryNote(deliveryNoteRepository);
            boolean isDeleted = removeDeliveryNote.execute(deliveryNoteData);

            String message;
            if (isDeleted) {
                message = Localization.getLocalization(LocalizationKey.REMOVED_DELIVERY_NOTE_MESSAGE);

                this.deliveryNotesData.remove(tableRow); // Remove it before from here, so the listener has the updated data.
                super.removeRow(tableRow);
            } else {
                message = Localization.getLocalization(LocalizationKey.REMOVED_DELIVERY_NOTE_ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(table, message);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListDeliveryNotesTableModel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Delivery note not removed because the database has not been found", ex);
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        // Price and import columns.
        if (columnIndex == 6 || columnIndex == 7) {
            return Float.class;
        }

        return Object.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only edit the chosen action column and the price.
        return column == 6 || column == 8;
    }

    /**
     * Add the delivery notes data to the table.
     *
     * @param deliveryNotesData The delivery notes data.
     */
    public void addDeliveryNotesData(ArrayList<DeliveryNoteData> deliveryNotesData) {
        this.deliveryNotesData = deliveryNotesData;

        // Clean table data before adding the new data.
        super.setRowCount(0);

        for (DeliveryNoteData deliveryNoteData : this.deliveryNotesData) {
            // Column 1: Delivery note data generation datetime.
            String pattern = "dd-MM-yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date date = deliveryNoteData.getDate();
            String formattedDate = df.format(date);

            // Column 2: Delivery note data farmer.
            String farmerCustomerCode = deliveryNoteData.getFarmer().toString();

            // Column 3: Delivery note data trader.
            String traderCustomerCode = deliveryNoteData.getTrader().toString();

            // Column 4: Delivery note data product.
            String productCode = deliveryNoteData.getProduct().toString();

            // Column 5: Delivery note boxes quantity.
            int numBoxes = deliveryNoteData.getNumBoxes();

            // Column 6: Delivery note net weight.
            int netWeight = deliveryNoteData.getNetWeight();

            // Column 7: Price.
            float price = deliveryNoteData.getPrice();

            // Column 8: Import.
            float imp = price * netWeight;

            // The last item indicates that we have to choose the action to execute.
            // The price (next to last column) is not initialized.
            this.addRow(new Object[]{formattedDate, farmerCustomerCode, traderCustomerCode, productCode, numBoxes, netWeight, price, imp, null});
        }
    }

    /**
     * Retrieve the delivery notes data for the associated table.
     *
     * @return The delivery notes data.
     */
    public ArrayList<DeliveryNoteData> getDeliveryNotesData() {
        return this.deliveryNotesData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        if (column == 8) {
            int deliveryNoteCode = deliveryNotesData.get(row).getCode();
            DeliveryNoteData deliveryNoteData = this.findDeliveryNoteData(deliveryNoteCode);
            if (deliveryNoteData != null) {
                String chosenAction = (String) newValue;
                if (chosenAction == null) {
                    return;
                }

                if (chosenAction.equals(Localization.getLocalization(LocalizationKey.DOWNLOAD))) {
                    this.downloadDeliveryNote(deliveryNoteData);
                } else if (chosenAction.equals(Localization.getLocalization(LocalizationKey.PRINT))) {
                    this.printDeliveryNote(deliveryNoteData);
                } else {
                    this.removeDeliveryNote(deliveryNoteData, row);
                    return; // Avoid setting the value for a removed row.
                }
            }
        } else if (column == 6) {
            // Price column.
            float newPrice = (float) newValue;

            // Update import.
            int netWeight = (int) super.getValueAt(row, 5);
            float imp = newPrice * netWeight;
            super.setValueAt(imp, row, 7);

            DeliveryNoteData deliveryNoteData = this.deliveryNotesData.get(row);
            deliveryNoteData.setPrice(newPrice);

            try {
                MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
                UpdateDeliveryNote updateDeliveryNote = new UpdateDeliveryNote(deliveryNoteRepository);
                updateDeliveryNote.execute(deliveryNoteData);
            } catch (NotDefinedDatabaseContextException ex) {
                String className = ListDeliveryNotesTableModel.class.getName();
                Logger.getLogger(className).log(Level.INFO, "Delivery note not updated because the database has not been found", ex);
            }
        }

        super.setValueAt(newValue, row, column);
    }

}
