package deliverynote.presentation.utils;

import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.RemoveDeliveryNote;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

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
        try {
            String deliveryNoteName = Localization.getLocalization(LocalizationKey.DELIVERY_NOTE);
            String documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            String filename = String.format("%s_%d.pdf", deliveryNoteName, deliveryNoteData.getCode());
            File destFile = new File(documentsPath, filename);

            File file = deliveryNoteData.getFile();

            Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String downloadedFileMessage = Localization.getLocalization(LocalizationKey.DOWNLOADED_FILE_MESSAGE);
            String infoMessage = String.format("%s: %s/%s", downloadedFileMessage, documentsPath, filename);
            JOptionPane.showMessageDialog(table, infoMessage);
        } catch (IOException ex) {
            Logger.getLogger(ListDeliveryNotesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            String downloadErrorMessage = Localization.getLocalization(LocalizationKey.DOWNLOAD_ERROR_MESSAGE);
            JOptionPane.showMessageDialog(table, downloadErrorMessage);
        }
    }

    /**
     * Print the given delivery note.
     *
     * @param deliveryNoteData The delivery note data.
     */
    private void printDeliveryNote(DeliveryNoteData deliveryNoteData) {
        try {
            PDDocument pdfDocument = PDDocument.load(deliveryNoteData.getFile());
            PDFPageable pageable = new PDFPageable(pdfDocument);

            PrinterJob printer = PrinterJob.getPrinterJob();
            printer.setCopies(2);
            printer.setPageable(pageable);

            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(MediaSizeName.ISO_A5);
            attributes.add(OrientationRequested.PORTRAIT);

            printer.print(attributes);

            String printedFileMessage = Localization.getLocalization(LocalizationKey.PRINTED_FILE_MESSAGE);
            JOptionPane.showMessageDialog(table, printedFileMessage);
        } catch (IOException | PrinterException ex) {
            Logger.getLogger(ListDeliveryNotesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            String printErrorMessage = Localization.getLocalization(LocalizationKey.PRINT_ERROR_MESSAGE);
            JOptionPane.showMessageDialog(table, printErrorMessage);
        }
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
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only edit the chosen action column.
        return column == 6;
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

            // Column 3: Delivery note data supplier.
            String supplierCustomerCode = deliveryNoteData.getSupplier().toString();

            // Column 4: Delivery note data product.
            String productCode = deliveryNoteData.getProduct().toString();

            // Column 5: Delivery note boxes quantity.
            int numBoxes = deliveryNoteData.getNumBoxes();

            // Column 6: Delivery note net weight.
            int netWeight = deliveryNoteData.getNetWeight();

            // The last item indicates that we have to choose the action to execute.
            this.addRow(new Object[]{formattedDate, farmerCustomerCode, supplierCustomerCode, productCode, numBoxes, netWeight, null});
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        super.setValueAt(newValue, row, column);

        if (column == 6) {
            int deliveryNoteCode = deliveryNotesData.get(row).getCode();
            DeliveryNoteData deliveryNoteData = this.findDeliveryNoteData(deliveryNoteCode);
            if (deliveryNoteData != null) {
                String chosenAction = (String) super.getValueAt(row, 6);

                if (chosenAction.equals(Localization.getLocalization(LocalizationKey.DOWNLOAD))) {
                    this.downloadDeliveryNote(deliveryNoteData);
                } else if (chosenAction.equals(Localization.getLocalization(LocalizationKey.PRINT))) {
                    this.printDeliveryNote(deliveryNoteData);
                } else {
                    this.removeDeliveryNote(deliveryNoteData, row);
                }
            }
        }
    }

}
