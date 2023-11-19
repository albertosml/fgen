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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import shared.application.configuration.ApplicationConfiguration;
import shared.application.configuration.ConfigurationVariable;
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
     * Store the current invoice total amount.
     */
    private float totalAmount;

    /**
     * Store the current invoice total weight.
     */
    private int totalWeight;

    /**
     * Entity attributes which represents totals, so they are only included on
     * the last page.
     */
    private ArrayList<EntityAttribute> entityAttributes;

    /**
     * Constructor.
     */
    public InvoiceGenerator() {
        this.totalAmount = 0;
        this.totalWeight = 0;

        // Those entities can be only inserted on the last invoice page.
        this.entityAttributes = new ArrayList<>();
        this.entityAttributes.add(EntityAttribute.SUBTOTAL);
        this.entityAttributes.add(EntityAttribute.INVOICE_TOTAL);
        this.entityAttributes.add(EntityAttribute.INVOICE_SUBTOTAL);
        this.entityAttributes.add(EntityAttribute.INVOICE_TOTAL_WEIGHT);
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
     * Get the timestamp from the current instant.
     *
     * @return A string indicating the current timestamp.
     */
    private String getTimestamp() {
        Date now = Date.from(Instant.now());
        return Long.toString(now.getTime());
    }

    /**
     * Convert excel file to HTML.
     *
     * @param workbook The excel file to convert.
     * @throws java.io.IOException if the file is not found.
     */
    private File convertToHtml(ExcelFile workbook) throws IOException {
        String timestamp = this.getTimestamp();

        File tmpHtmlFile = File.createTempFile(timestamp, ".html");
        tmpHtmlFile.deleteOnExit();
        OutputStream htmlStream = new FileOutputStream(tmpHtmlFile);
        workbook.save(htmlStream, new HtmlSaveOptions());

        return tmpHtmlFile;
    }

    /**
     * Combine the set of HTML files on one PDF file.
     *
     * @param htmlFiles The list of HTML files to convert.
     * @param date Date to set on the filename.
     * @throws java.io.IOException if the file is not found.
     * @throws java.lang.InterruptedException if the conversion is interrupted.
     */
    private File convertToPdf(ArrayList<File> htmlFiles) throws IOException, InterruptedException {
        String executable = WrapperConfig.findExecutable();
        Pdf pdf = new Pdf(new WrapperConfig(executable));
        pdf.addParam(new Param("--page-size", "A4"));

        for (File htmlFile : htmlFiles) {
            pdf.addPageFromFile(htmlFile.getAbsolutePath());
        }

        String timestamp = this.getTimestamp();
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
            case TRADER_CUSTOMER_CODE:
                return invoice.getCustomer().getCode();
            case TRADER_CUSTOMER_NAME:
                return invoice.getCustomer().getName();
            case TRADER_CUSTOMER_TIN:
                return invoice.getCustomer().getTin();
            case TRADER_CUSTOMER_ADDRESS:
                return invoice.getCustomer().getAddress();
            case TRADER_CUSTOMER_CITY:
                return invoice.getCustomer().getCity();
            case TRADER_CUSTOMER_PROVINCE:
                return invoice.getCustomer().getProvince();
            case TRADER_CUSTOMER_ZIPCODE:
                return invoice.getCustomer().getZipCode();
            case TRADER_CUSTOMER_IBAN:
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
                return String.format("%.2f €", totalAmount);
            case SUBTOTAL:
                float value = subtotal.calculate(totalAmount);
                value = (float) (Math.round(value * 100.0) / 100.0);

                // Update total
                totalAmount += value;
                totalAmount = (float) (Math.round(totalAmount * 100.0) / 100.0);

                return String.format("%.2f €", value);
            case INVOICE_SUBTOTAL:
                float invoiceTotal = invoice.calculateTotal();
                invoiceTotal = (float) (Math.round(invoiceTotal * 100.0) / 100.0);

                // First total will be the invoice total.
                this.totalAmount = invoiceTotal;

                return String.format("%.2f €", invoiceTotal);
            case PERIOD:
                Date start = invoice.getStartPeriod();
                Date end = invoice.getEndPeriod();

                String datePattern = "dd/MM/yyyy";
                DateFormat dateFormat = new SimpleDateFormat(datePattern);

                String formattedStartDate = dateFormat.format(start);
                String formattedEndDate = dateFormat.format(end);

                return String.format("%s - %s", formattedStartDate, formattedEndDate);
            case INVOICE_TOTAL_WEIGHT:
                int totalWeight = invoice.calculateTotalWeight();

                // Set the current total weight.
                this.totalWeight = totalWeight;
                
                return String.format("%d KGS", totalWeight);
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
     * Write delivery notes into the spreadsheet cells.
     *
     * @param position Start position.
     * @param deliveryNotes The delivery notes.
     * @param worksheet The worksheet where we are going to write the delivery
     * note data.
     */
    private void writeDeliveryNotes(String position, List<DeliveryNoteData> deliveryNotes, ExcelWorksheet worksheet) {
        ExcelCell cell = worksheet.getCell(position);
        int rowIndex = cell.getRow().getIndex();
        int columnIndex = cell.getColumn().getIndex();

        String datePattern = "dd/MM/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(datePattern);

        ExcelCell cellToWrite;
        for (DeliveryNoteData deliveryNoteData : deliveryNotes) {
            String formattedDate = dateFormat.format(deliveryNoteData.getDate());

            float price = deliveryNoteData.getPrice();
            float total = deliveryNoteData.getNetWeight() * price;

            Map<Integer, Object> valueToWriteByPosition = new HashMap<>();
            valueToWriteByPosition.put(0, formattedDate);
            valueToWriteByPosition.put(1, deliveryNoteData.getCode());
            valueToWriteByPosition.put(2, deliveryNoteData.getNumBoxes());
            valueToWriteByPosition.put(4, deliveryNoteData.getNetWeight());
            valueToWriteByPosition.put(6, deliveryNoteData.getProduct().getName());
            valueToWriteByPosition.put(10, Math.round(price * 100.0) / 100.0);
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
     * @param isLastPage Whether we are on the last invoice page or not.
     */
    private void writeVariable(String position, String expression, Pattern pattern, Invoice invoice, Map<String, Variable> variables, ExcelWorksheet worksheet, boolean isLastPage) {
        Matcher matcher = pattern.matcher(expression);
        String cellText = expression;
        while (matcher.find()) {
            String variableMatch = matcher.group();
            // Remove the "${" and "}" from the variable.
            String variableName = variableMatch.substring(2, variableMatch.length() - 1);
            Variable variable = variables.get(variableName);
            EntityAttribute entityAttribute = variable.getAttribute();

            Object variableValue;
            if (!isLastPage && this.entityAttributes.contains(entityAttribute)) {
                // This entity attribute is only inserted on last page.
                variableValue = "";
            } else if (entityAttribute == EntityAttribute.SUBTOTAL) {
                SubtotalVariable subtotalVariable = (SubtotalVariable) variable;
                variableValue = this.getValue(entityAttribute, invoice, subtotalVariable.getSubtotal());
            } else {
                variableValue = this.getValue(entityAttribute, invoice, null);
            }

            String variableValueText = variableValue.toString();
            cellText = cellText.replace(variableMatch, variableValueText);
        }

        ExcelCell cell = worksheet.getCell(position);
        cell.setValue(cellText);
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

        ArrayList<File> htmlFiles = new ArrayList<>();
        ArrayList<DeliveryNoteData> deliveryNotes = invoice.getDeliveryNotes();

        int chunkSize = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.INVOICE_ITEMS_PER_PAGE);
        for (int i = 0; i < deliveryNotes.size(); i += chunkSize) {
            int chunkEnd = i + chunkSize;
            List<DeliveryNoteData> deliveryNotesChunk;

            if (chunkEnd > deliveryNotes.size()) {
                deliveryNotesChunk = deliveryNotes.subList(i, deliveryNotes.size());
            } else {
                deliveryNotesChunk = deliveryNotes.subList(i, chunkEnd);
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
                        this.writeDeliveryNotes(position, deliveryNotesChunk, worksheet);
                        continue;
                    }
                }

                boolean isLastPage = chunkEnd >= deliveryNotes.size();
                this.writeVariable(position, expression, variablesPattern, invoice, variables, worksheet, isLastPage);
            }

            File htmlFile = this.convertToHtml(workbook);
            htmlFiles.add(htmlFile);
        }

        return this.convertToPdf(htmlFiles);
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
        if (templateCode < 1) {
            return false;
        }

        if (invoice == null) {
            return false;
        }

        File invoiceFile = this.createInvoiceFile(templateCode, invoice);
        if (invoiceFile == null) {
            return false;
        }

        invoice.setFile(invoiceFile);

        invoice.setTotal(this.totalAmount);
        invoice.setTotalWeight(this.totalWeight);

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
