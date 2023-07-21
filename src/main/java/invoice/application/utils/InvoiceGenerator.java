package invoice.application.utils;

import com.gembox.spreadsheet.ExcelCell;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.FreeLimitReachedAction;
import com.gembox.spreadsheet.HtmlSaveOptions;
import com.gembox.spreadsheet.SpreadsheetInfo;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import deliverynote.application.DeliveryNoteData;
import invoice.application.Invoice;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import template.application.Template;
import template.application.usecases.ShowTemplate;
import template.persistence.mongo.MongoTemplateRepository;
import variable.application.EntityAttribute;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_ADDRESS;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_CITY;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_CODE;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_IBAN;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_NAME;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_PROVINCE;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_TIN;
import static variable.application.EntityAttribute.FARMER_CUSTOMER_ZIPCODE;
import static variable.application.EntityAttribute.PRODUCT_CODE;
import static variable.application.EntityAttribute.PRODUCT_NAME;
import variable.application.Variable;
import variable.application.usecases.ListVariables;
import variable.persistence.mongo.MongoVariableRepository;

/**
 * Represents the class responsible of generating the invoice.
 */
public class InvoiceGenerator {

    /**
     * Store the current invoice total.
     */
    private float total;

    /**
     * Constructor.
     */
    public InvoiceGenerator() {
        this.total = 0;
    }

    /**
     * Obtain the template associate to the given code by executing the use
     * case.
     *
     * @param code The code of the template to give.
     * @return The template object if found, otherwise null.
     */
    private Template getTemplate(int code) {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            ShowTemplate showTemplate = new ShowTemplate(templateRepository);
            return showTemplate.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = InvoiceGenerator.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Template cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Convert excel file to PDF.
     *
     * @param workbook The excel file to convert.
     * @param date Date to set on the filename.
     * @throws java.io.IOException if the file is not found.
     * @throws java.lang.InterruptedException if the conversion is interrupted.
     */
    private File convertToPdf(ExcelFile workbook, Date date) throws IOException, InterruptedException {
        String timestamp = Long.toString(date.getTime());

        File tmpHtmlFile = File.createTempFile(timestamp, ".html");
        tmpHtmlFile.deleteOnExit();
        OutputStream htmlStream = new FileOutputStream(tmpHtmlFile);
        workbook.save(htmlStream, new HtmlSaveOptions());

        String executable = WrapperConfig.findExecutable();
        Pdf pdf = new Pdf(new WrapperConfig(executable));
        pdf.addParam(new Param("--page-size", "A4"));
        pdf.addPageFromFile(tmpHtmlFile.getAbsolutePath());

        File tmpPdfFile = File.createTempFile(timestamp, ".pdf");
        tmpPdfFile.deleteOnExit();
        return pdf.saveAs(tmpPdfFile.getAbsolutePath());
    }

    /**
     * Obtain the value from the given entity attribute and invoice.
     *
     * @param entityAttribtue The entity attribute used to get the value.
     * @param invoice The invoice used to retrieve the value.
     * @return An object indicating the requested value, as the value can be a
     * string, a list, etc...
     */
    private Object getValue(EntityAttribute entityAttribute, Invoice invoice) {
        switch (entityAttribute) {
            case FARMER_CUSTOMER_CODE:
                return invoice.getFarmer().getCode();
            case FARMER_CUSTOMER_NAME:
                return invoice.getFarmer().getName();
            case FARMER_CUSTOMER_TIN:
                return invoice.getFarmer().getTin();
            case FARMER_CUSTOMER_ADDRESS:
                return invoice.getFarmer().getAddress();
            case FARMER_CUSTOMER_CITY:
                return invoice.getFarmer().getCity();
            case FARMER_CUSTOMER_PROVINCE:
                return invoice.getFarmer().getProvince();
            case FARMER_CUSTOMER_ZIPCODE:
                return invoice.getFarmer().getZipCode();
            case FARMER_CUSTOMER_IBAN:
                return invoice.getFarmer().getIban();
            case SUPPLIER_CUSTOMER_CODE:
                return invoice.getSupplier().getCode();
            case SUPPLIER_CUSTOMER_NAME:
                return invoice.getSupplier().getName();
            case SUPPLIER_CUSTOMER_TIN:
                return invoice.getSupplier().getTin();
            case SUPPLIER_CUSTOMER_ADDRESS:
                return invoice.getSupplier().getAddress();
            case SUPPLIER_CUSTOMER_CITY:
                return invoice.getSupplier().getCity();
            case SUPPLIER_CUSTOMER_PROVINCE:
                return invoice.getSupplier().getProvince();
            case SUPPLIER_CUSTOMER_ZIPCODE:
                return invoice.getSupplier().getZipCode();
            case SUPPLIER_CUSTOMER_IBAN:
                return invoice.getSupplier().getIban();
            case PRODUCT_CODE:
                return invoice.getProduct().getCode();
            case PRODUCT_NAME:
                return invoice.getProduct().getName();
            case INVOICE_ITEMS:
                return invoice.getDeliveryNotes();
            case INVOICE_CODE:
                return invoice.getCode();
            case INVOICE_GENERATION_DATETIME:
                String pattern = "dd-MM-yyyy HH:mm:ss";
                DateFormat df = new SimpleDateFormat(pattern);
                Date date = invoice.getDate();
                return df.format(date);
        }

        return null;
    }

    /**
     * Obtain all the variables data by executing the use case.
     *
     * @return A list with all variables on the system.
     */
    private Map<String, EntityAttribute> getVariables() {
        ArrayList<Variable> variables = new ArrayList<>();

        try {
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            ListVariables listVariables = new ListVariables(variableRepository);
            variables = listVariables.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            variables = new ArrayList<>();
        }

        Map<String, EntityAttribute> entityAttributePerVariable = new HashMap<>();
        for (Variable variable : variables) {
            entityAttributePerVariable.put(variable.getName(), variable.getAttribute());
        }

        return entityAttributePerVariable;
    }

    /**
     * Load the given template file.
     *
     * @param template The given template.
     * @return The excel file associated to the specified template.
     * @throws FileNotFoundException if the file is not found.
     * @throws IOException if the file cannot be read.
     */
    private ExcelFile loadTemplate(Template template) throws FileNotFoundException, IOException {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
        SpreadsheetInfo.addFreeLimitReachedListener(args -> args.setFreeLimitReachedAction(FreeLimitReachedAction.CONTINUE_AS_TRIAL));

        File templateFile = template.getFile();
        InputStream fileStream = new FileInputStream(templateFile);
        return ExcelFile.load(fileStream);
    }

    /**
     * Write invoice items into the spreadsheet cells.
     *
     * @param position Start position.
     * @param invoice The invoice.
     * @param worksheet The worksheet where we are going to write the delivery
     * note data.
     */
    private void writeInvoiceItems(String position, Invoice invoice, ExcelWorksheet worksheet) {
        ExcelCell cell = worksheet.getCell(position);
        int rowIndex = cell.getRow().getIndex();
        int columnIndex = cell.getColumn().getIndex();

        ExcelCell cellToWrite;
        for (DeliveryNoteData deliveryNoteData : invoice.getDeliveryNotes()) {
            Object[] dataToAdd = new Object[]{
                deliveryNoteData.getDate(),
                deliveryNoteData.getCode(),
                deliveryNoteData.getNumBoxes(),
                deliveryNoteData.getNetWeight(),
                deliveryNoteData.getProduct(),
                deliveryNoteData.getPrice(),
                deliveryNoteData.getNetWeight() * deliveryNoteData.getPrice()
            };

            for (int i = 0; i < columnIndex; i++) {
                cellToWrite = worksheet.getCell(rowIndex, columnIndex + i);
                cellToWrite.setValue(dataToAdd[i]);
            }

            // Move to next row.
            rowIndex++;
        }
    }

    /**
     * Write variable on the spreadsheet file.
     *
     * @param position Position to write.
     * @param expression Expression to set.
     * @param pattern Pattern to detect variables.
     * @param invoice The invoice.
     * @param variables Variables list.
     * @param worksheet Worksheet to write the variable.
     */
    private void writeVariable(String position, String expression, Pattern pattern, Invoice invoice, Map<String, EntityAttribute> variables, ExcelWorksheet worksheet) {
        Matcher matcher = pattern.matcher(expression);
        String replacedExpression = matcher.replaceAll(match -> {
            String variable = match.group();
            // Remove the "${" and "}" from the variable.
            String variableName = variable.substring(2, variable.length() - 1);
            EntityAttribute entityAttribute = variables.get(variableName);
            Object variableValue = this.getValue(entityAttribute, invoice);
            return variableValue.toString();
        });

        ExcelCell cell = worksheet.getCell(position);
        cell.setValue(replacedExpression);
    }

    /**
     * Create the invoice file.
     *
     * @param templateCode The code of the used template.
     * @param invoice The invoice.
     * @return The invoice file if created, otherwise null.
     * @throws IOException if the template file is not found.
     * @throws InterruptedException if the conversion is interrupted.
     */
    private File createInvoiceFile(int templateCode, Invoice invoice) throws IOException, InterruptedException {
        Template template = this.getTemplate(templateCode);

        if (template == null) {
            return null;
        }

        ExcelFile workbook = this.loadTemplate(template);
        ExcelWorksheet worksheet = workbook.getWorksheet(0);

        Pattern variablesPattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Map<String, EntityAttribute> variables = this.getVariables();
        Map<String, String> templateFields = template.getFields();
        for (Map.Entry<String, String> field : templateFields.entrySet()) {
            String position = field.getKey();
            String expression = field.getValue();

            // Specific processing for the invoice items.
            String expressionVariableName = expression.substring(2, expression.length() - 1);
            EntityAttribute expressionEntityAttribute = variables.get(expressionVariableName);
            boolean shouldWriteInvoiceItems = expressionEntityAttribute == EntityAttribute.INVOICE_ITEMS;
            if (shouldWriteInvoiceItems) {
                this.writeInvoiceItems(position, invoice, worksheet);
                continue;
            }

            this.writeVariable(position, expression, variablesPattern, invoice, variables, worksheet);
        }

        return this.convertToPdf(workbook, invoice.getDate());
    }

    /**
     * Generate the invoices, one for the supplier and another for the farmer.
     *
     * @param invoice The invoice data.
     * @throws IOException if the template file is not found.
     * @throws InterruptedException if the conversion is interrupted.
     *
     * @return Whether the invoices have been generated or not.
     */
    public boolean generate(Invoice invoice) throws IOException, InterruptedException {
        File supplierInvoice = this.createInvoiceFile(2, invoice);
        if (supplierInvoice == null) {
            return false;
        }

        File farmerInvoice = this.createInvoiceFile(3, invoice);
        if (farmerInvoice == null) {
            return false;
        }

        // Call to use case.
        try {
            MongoInvoiceRepository invoiceRepository = new MongoInvoiceRepository();
            SaveInvoice saveInvoice = new SaveInvoice(invoiceRepository);
            saveInvoice.execute(invoice, farmerInvoice, supplierInvoice);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = InvoiceGenerator.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Invoice not saved because the database has not been found", ex);
        }
    }

}
