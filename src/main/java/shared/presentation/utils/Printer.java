package shared.presentation.utils;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * Responsible for printing files.
 */
public class Printer {
    /**
     * Print the given delivery note.
     *
     * @param file The delivery note file.
     * @return Whether the delivery note has been printed or not.
     */
    public static boolean printDeliveryNote(File file) {
        if (file == null) {
            return false;
        }

        try {
            PDDocument pdfDocument = PDDocument.load(file);
            PDFPageable pageable = new PDFPageable(pdfDocument);

            PrinterJob printer = PrinterJob.getPrinterJob();
            printer.setCopies(2);
            printer.setPageable(pageable);

            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(MediaSizeName.ISO_A5);
            attributes.add(OrientationRequested.PORTRAIT);

            printer.print(attributes);

            return true;
        } catch (IOException | PrinterException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
