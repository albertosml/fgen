package invoice.presentation.utils;

import invoice.application.Invoice;
import invoice.application.usecases.RemoveInvoice;
import invoice.persistence.mongo.MongoInvoiceRepository;
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
 * Table model for the list invoices panel.
 */
public class ListInvoicesTableModel extends DefaultTableModel {

    /**
     * Loaded invoices.
     */
    private ArrayList<Invoice> invoices;

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
    public ListInvoicesTableModel(Vector<? extends Vector> data, Vector<?> columnNames, JTable table) {
        super(data, columnNames);
        this.table = table;
        this.invoices = new ArrayList<>();
    }

    /**
     * Find the invoice by the given code.
     *
     * @param code The delivery note data code.
     * @return The found delivery note data code, otherwise null.
     */
    private Invoice findInvoiceBy(int code) {
        for (Invoice invoice : this.invoices) {
            if (invoice.getCode() == code) {
                return invoice;
            }
        }

        return null;
    }

    /**
     * Download the given invoice.
     *
     * @param invoice The invoice.
     */
    private void downloadInvoice(Invoice invoice) {
        try {
            String invoiceName = Localization.getLocalization(LocalizationKey.INVOICE);
            String documentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            String filename = String.format("%s_%d.pdf", invoiceName, invoice.getCode());
            File destFile = new File(documentsPath, filename);

            File file = invoice.getFile();

            Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String downloadedFileMessage = Localization.getLocalization(LocalizationKey.DOWNLOADED_FILE_MESSAGE);
            String infoMessage = String.format("%s: %s/%s", downloadedFileMessage, documentsPath, filename);
            JOptionPane.showMessageDialog(table, infoMessage);
        } catch (IOException ex) {
            Logger.getLogger(ListInvoicesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            String downloadErrorMessage = Localization.getLocalization(LocalizationKey.DOWNLOAD_ERROR_MESSAGE);
            JOptionPane.showMessageDialog(table, downloadErrorMessage);
        }
    }

    /**
     * Print the given invoice.
     *
     * @param invoice The invoice.
     */
    private void printInvoice(Invoice invoice) {
        try {
            PDDocument pdfDocument = PDDocument.load(invoice.getFile());
            PDFPageable pageable = new PDFPageable(pdfDocument);

            PrinterJob printer = PrinterJob.getPrinterJob();
            printer.setCopies(2);
            printer.setPageable(pageable);

            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(MediaSizeName.ISO_A4);
            attributes.add(OrientationRequested.PORTRAIT);

            printer.print(attributes);

            String printedFileMessage = Localization.getLocalization(LocalizationKey.PRINTED_FILE_MESSAGE);
            JOptionPane.showMessageDialog(table, printedFileMessage);
        } catch (IOException | PrinterException ex) {
            Logger.getLogger(ListInvoicesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            String printErrorMessage = Localization.getLocalization(LocalizationKey.PRINT_ERROR_MESSAGE);
            JOptionPane.showMessageDialog(table, printErrorMessage);
        }
    }

    /**
     * Remove the given invoice.
     *
     * @param invoice The invoice.
     * @param tableRow The table row index.
     */
    public void removeInvoice(Invoice invoice, int tableRow) {
        try {
            MongoInvoiceRepository invoiceRepository = new MongoInvoiceRepository();
            RemoveInvoice removeInvoice = new RemoveInvoice(invoiceRepository);
            boolean isDeleted = removeInvoice.execute(invoice);

            String message;
            if (isDeleted) {
                message = Localization.getLocalization(LocalizationKey.REMOVED_INVOICE_MESSAGE);

                this.invoices.remove(tableRow); // Remove it before from here, so the listener has the updated data.
                super.removeRow(tableRow);
            } else {
                message = Localization.getLocalization(LocalizationKey.REMOVED_INVOICE_ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(table, message);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListInvoicesTableModel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Invoice not removed because the database has not been found", ex);
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        // Total column.
        if (columnIndex == 4) {
            return Float.class;
        }

        return Object.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only edit the chosen action column.
        return column == 5;
    }

    /**
     * Set the invoice on the table.
     *
     * @param invoices The invoices to add.
     */
    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;

        // Clean table data before adding the new data.
        super.setRowCount(0);

        for (Invoice invoice : this.invoices) {
            // Column 1: Invoice code.
            int invoiceCode = invoice.getCode();

            // Column 2: Invoice generation datetime.
            String pattern = "dd-MM-yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date date = invoice.getDate();
            String formattedDate = df.format(date);

            // Column 3: Invoice customer.
            String customer = invoice.getCustomer().toString();

            // Column 4: Invoice period.
            Date start = invoice.getStartPeriod();
            Date end = invoice.getEndPeriod();

            String datePattern = "dd/MM/yyyy";
            DateFormat dateFormat = new SimpleDateFormat(datePattern);

            String formattedStartDate = dateFormat.format(start);
            String formattedEndDate = dateFormat.format(end);

            String period = String.format("%s - %s", formattedStartDate, formattedEndDate);

            // Column 5: Invoice total.
            double invoiceTotal = invoice.getTotal();

            // The last item indicates that we have to choose the action to execute.
            // The price (next to last column) is not initialized.
            this.addRow(new Object[]{invoiceCode, formattedDate, customer, period, invoiceTotal, null});
        }
    }

    /**
     * Retrieve the invoices for the associated table.
     *
     * @return A list of invoices.
     */
    public ArrayList<Invoice> getInvoices() {
        return this.invoices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        super.setValueAt(newValue, row, column);

        if (column == 5) {
            int invoiceCode = invoices.get(row).getCode();
            Invoice invoice = this.findInvoiceBy(invoiceCode);
            if (invoice != null) {
                String chosenAction = (String) super.getValueAt(row, 5);
                if (chosenAction == null) {
                    return;
                }

                if (chosenAction.equals(Localization.getLocalization(LocalizationKey.DOWNLOAD))) {
                    this.downloadInvoice(invoice);
                } else if (chosenAction.equals(Localization.getLocalization(LocalizationKey.PRINT))) {
                    this.printInvoice(invoice);
                } else {
                    this.removeInvoice(invoice, row);
                }
            }
        }
    }

}
