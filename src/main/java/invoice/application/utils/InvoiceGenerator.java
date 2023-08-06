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
import invoice.application.usecases.SaveInvoice;
import invoice.persistence.mongo.MongoInvoiceRepository;
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
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import subtotal.application.Subtotal;
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
import variable.application.SubtotalVariable;
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
     * @param subtotal The subtotal associated to the variable if required.
     * @return An object indicating the requested value, as the value can be a
     * string, a list, etc...
     */
    private Object getValue(EntityAttribute entityAttribute, Invoice invoice, Subtotal subtotal) {
        switch (entityAttribute) {
            case FARMER_CUSTOMER_CODE:
                return invoice.getCustomer().getCode();
            case FARMER_CUSTOMER_NAME:
                return invoice.getCustomer().getName();
            case FARMER_CUSTOMER_TIN:
                return invoice.getCustomer().getTin();
            case FARMER_CUSTOMER_ADDRESS:
                return invoice.getCustomer().getAddress();
            case FARMER_CUSTOMER_CITY:
                return invoice.getCustomer().getCity();
            case FARMER_CUSTOMER_PROVINCE:
                return invoice.getCustomer().getProvince();
            case FARMER_CUSTOMER_ZIPCODE:
                return invoice.getCustomer().getZipCode();
            case FARMER_CUSTOMER_IBAN:
                return invoice.getCustomer().getIban();
            case SUPPLIER_CUSTOMER_CODE:
                return invoice.getCustomer().getCode();
            case SUPPLIER_CUSTOMER_NAME:
                return invoice.getCustomer().getName();
            case SUPPLIER_CUSTOMER_TIN:
                return invoice.getCustomer().getTin();
            case SUPPLIER_CUSTOMER_ADDRESS:
                return invoice.getCustomer().getAddress();
            case SUPPLIER_CUSTOMER_CITY:
                return invoice.getCustomer().getCity();
            case SUPPLIER_CUSTOMER_PROVINCE:
                return invoice.getCustomer().getProvince();
            case SUPPLIER_CUSTOMER_ZIPCODE:
                return invoice.getCustomer().getZipCode();
            case SUPPLIER_CUSTOMER_IBAN:
                return invoice.getCustomer().getIban();
            case INVOICE_ITEMS:
                return invoice.getDeliveryNotes();
            case INVOICE_CODE:
                return invoice.getCode();
            case INVOICE_GENERATION_DATETIME:
                String pattern = "dd-MM-yyyy HH:mm:ss";
                DateFormat df = new SimpleDateFormat(pattern);
                Date date = invoice.getDate();
                return df.format(date);
            case INVOICE_TOTAL:
                return total;
            case SUBTOTAL:
                float value = subtotal.calculate(total);
                value = (float) (Math.round(value * 100.0) / 100.0);

                // Update total
                total += value;
                total = (float) (Math.round(total * 100.0) / 100.0);

                return value;
            case INVOICE_SUBTOTAL:
                float invoiceTotal = invoice.calculateTotal();
                invoiceTotal = (float) (Math.round(invoiceTotal * 100.0) / 100.0);

                // First total will be the invoice total.
                this.total = invoiceTotal;

                return invoiceTotal;
            case PERIOD:
                Date start = invoice.getStartPeriod();
                Date end = invoice.getEndPeriod();

                String datePattern = "dd/MM/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(datePattern);

                String formattedStartDate = dateFormat.format(start);
                String formattedEndDate = dateFormat.format(end);

                return String.format("%s - %s", formattedStartDate, formattedEndDate);
        }

        return null;
    }

    /**
     * Obtain all the variables data by executing the use case.
     *
     * @return A list with all variables on the system.
     */
    private Map<String, Variable> getVariables() {
        ArrayList<Variable> variables = new ArrayList<>();

        try {
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            ListVariables listVariables = new ListVariables(variableRepository);
            variables = listVariables.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            variables = new ArrayList<>();
        }

        Map<String, Variable> variablesMap = new HashMap<>();
        for (Variable variable : variables) {
            variablesMap.put(variable.getName(), variable);
        }

        return variablesMap;
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

        String datePattern = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(datePattern);

        ExcelCell cellToWrite;
        for (DeliveryNoteData deliveryNoteData : invoice.getDeliveryNotes()) {
            String formattedDate = dateFormat.format(deliveryNoteData.getDate());

            float price = deliveryNoteData.getPrice();
            float total = deliveryNoteData.getNetWeight() * price;

            Map<Integer, Object> valueToWriteByPosition = new HashMap<>();
            valueToWriteByPosition.put(0, formattedDate);
            valueToWriteByPosition.put(1, deliveryNoteData.getCode());
            valueToWriteByPosition.put(2, deliveryNoteData.getNumBoxes());
            valueToWriteByPosition.put(4, deliveryNoteData.getNetWeight());
            valueToWriteByPosition.put(6, deliveryNoteData.getProduct().getName());
            valueToWriteByPosition.put(10, price);
            valueToWriteByPosition.put(11, String.format("%s €", total));

            for (Map.Entry<Integer, Object> entry : valueToWriteByPosition.entrySet()) {
                int offset = entry.getKey();
                Object valueToWrite = entry.getValue();

                cellToWrite = worksheet.getCell(rowIndex, columnIndex + offset);
                cellToWrite.setValue(valueToWrite);
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
    private void writeVariable(String position, String expression, Pattern pattern, Invoice invoice, Map<String, Variable> variables, ExcelWorksheet worksheet) {
        Matcher matcher = pattern.matcher(expression);
        String replacedExpression = matcher.replaceAll(match -> {
            String variableMatch = match.group();
            // Remove the "${" and "}" from the variable.
            String variableName = variableMatch.substring(2, variableMatch.length() - 1);
            Variable variable = variables.get(variableName);
            EntityAttribute entityAttribute = variable.getAttribute();

            Object variableValue;
            if (entityAttribute == EntityAttribute.SUBTOTAL) {
                SubtotalVariable subtotalVariable = (SubtotalVariable) variable;
                variableValue = this.getValue(entityAttribute, invoice, subtotalVariable.getSubtotal());
            } else {
                variableValue = this.getValue(entityAttribute, invoice, null);
            }

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
        Map<String, Variable> variables = this.getVariables();
        SortedMap<String, String> templateFields = template.getFields();
        for (SortedMap.Entry<String, String> field : templateFields.entrySet()) {
            String position = field.getKey();
            String expression = field.getValue();

            // Specific processing for the invoice items.
            String expressionVariableName = expression.substring(2, expression.length() - 1);
            Variable variable = variables.get(expressionVariableName);

            if (variable != null) {
                EntityAttribute expressionEntityAttribute = variable.getAttribute();
                boolean shouldWriteInvoiceItems = expressionEntityAttribute == EntityAttribute.INVOICE_ITEMS;
                if (shouldWriteInvoiceItems) {
                    this.writeInvoiceItems(position, invoice, worksheet);
                    continue;
                }
            }

            this.writeVariable(position, expression, variablesPattern, invoice, variables, worksheet);
        }

        return this.convertToPdf(workbook, invoice.getDate());
    }

    /**
     * Generate the invoice, using the template from the given code.
     *
     * @param invoice The invoice data.
     * @param templateCode The code of the used template to generate the
     * invoice.
     * @throws IOException if the template file is not found.
     * @throws InterruptedException if the conversion is interrupted.
     *
     * @return Whether the invoices have been generated or not.
     */
    public boolean generate(Invoice invoice, int templateCode) throws IOException, InterruptedException {
        if (invoice == null) {
            return false;
        }

        File invoiceFile = this.createInvoiceFile(templateCode, invoice);
        if (invoiceFile == null) {
            return false;
        }

        invoice.setFile(invoiceFile);

        // Call to use case.
        try {
            MongoInvoiceRepository invoiceRepository = new MongoInvoiceRepository();
            SaveInvoice saveInvoice = new SaveInvoice(invoiceRepository);
            saveInvoice.execute(invoice);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = InvoiceGenerator.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Invoice not saved because the database has not been found", ex);
        }

        return true;
    }

}