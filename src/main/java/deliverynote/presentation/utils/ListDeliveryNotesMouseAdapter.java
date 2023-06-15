package deliverynote.presentation.utils;

import deliverynote.application.DeliveryNoteData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Mouse adapter for the list delivery notes panel, which includes a button to
 * download and print a delivery note.
 */
public class ListDeliveryNotesMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Loaded delivery notes data.
     */
    private ArrayList<DeliveryNoteData> deliveryNotesData;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListDeliveryNotesMouseAdapter(JTable table) {
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
        String deliveryNoteName = Localization.getLocalization(LocalizationKey.DELIVERY_NOTE);
        String documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        String filename = String.format("%s_%d.pdf", deliveryNoteName, deliveryNoteData.getCode());
        File destFile = new File(documentsPath, filename);

        File file = deliveryNoteData.getFile();
        file.renameTo(destFile);

        String downloadedFileMessage = Localization.getLocalization(LocalizationKey.DOWNLOADED_FILE_MESSAGE);
        String infoMessage = String.format("%s: %s/%s", downloadedFileMessage, documentsPath, filename);
        JOptionPane.showMessageDialog(table, infoMessage);
    }

    /**
     * Print the given delivery note.
     *
     * @param deliveryNoteData The delivery note data.
     */
    private void printDeliveryNote(DeliveryNoteData deliveryNoteData) {
        System.out.println(deliveryNoteData.getCode());
        System.out.println("print delivery note");

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
            Logger.getLogger(ListDeliveryNotesMouseAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());
        int column = table.columnAtPoint(evt.getPoint());

        int deliveryNoteCode = (int) table.getValueAt(row, 0);
        DeliveryNoteData deliveryNoteData = this.findDeliveryNoteData(deliveryNoteCode);
        if (deliveryNoteData == null) {
            return;
        }

        if (column == 4) {
            this.downloadDeliveryNote(deliveryNoteData);
        } else if (column == 5) {
            this.printDeliveryNote(deliveryNoteData);
        }
    }

    /**
     * Add the delivery notes data to the table.
     *
     * @param deliveryNotesData The delivery notes data.
     */
    public void addDeliveryNotesData(ArrayList<DeliveryNoteData> deliveryNotesData) {
        this.deliveryNotesData = deliveryNotesData;

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (DeliveryNoteData deliveryNoteData : this.deliveryNotesData) {
            // Column 1: Delivery note data code.
            int code = deliveryNoteData.getCode();

            // Column 2: Delivery note data generation datetime.
            String pattern = "dd-MM-yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date date = deliveryNoteData.getDate();
            String formattedDate = df.format(date);

            // Column 3: Delivery note data customer.
            String customerCode = deliveryNoteData.getCustomer().toString();

            // Column 4: Delivery note data product.
            String productCode = deliveryNoteData.getProduct().toString();

            // Column 5: Empty name. It will show a button to download the delivery note.
            String downloadName = Localization.getLocalization(LocalizationKey.DOWNLOAD);

            // Column 6: Empty name with a whitespace. It will show a button to
            // print the delivery note.
            String printName = Localization.getLocalization(LocalizationKey.PRINT);

            tableModel.addRow(new Object[]{code, formattedDate, customerCode, productCode, downloadName, printName});
        }
    }

}
