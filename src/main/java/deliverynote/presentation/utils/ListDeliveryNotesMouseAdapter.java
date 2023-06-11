package deliverynote.presentation.utils;

import deliverynote.application.DeliveryNoteData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
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
     * Delivery notes data.
     */
    private ArrayList<DeliveryNoteData> deliveryNotesData;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     * @param deliveryNotesData The delivery notes data.
     */
    public ListDeliveryNotesMouseAdapter(JTable table, ArrayList<DeliveryNoteData> deliveryNotesData) {
        this.table = table;
        this.deliveryNotesData = deliveryNotesData;
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

}
