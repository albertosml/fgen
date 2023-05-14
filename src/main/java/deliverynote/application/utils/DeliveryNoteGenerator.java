package deliverynote.application.utils;

import com.gembox.spreadsheet.ExcelCell;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.FreeLimitReachedAction;
import com.gembox.spreadsheet.HtmlSaveOptions;
import com.gembox.spreadsheet.SpreadsheetInfo;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import customer.application.Customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import subtotal.application.Subtotal;
import template.application.Template;
import variable.application.EntityAttribute;
import variable.application.SubtotalVariable;
import variable.application.Variable;
import variable.application.usecases.ListVariables;
import variable.persistence.mongo.MongoVariableRepository;

/**
 * Represents the class responsible of generating the delivery note.
 */
public class DeliveryNoteGenerator {

    /**
     * Obtain the value from the given variable and invoice.
     *
     * @param variable The variable used to retrieve the value.
     * @param deliveryNote The delivery note used to retrieve the value.
     * @return An object indicating the requested value, as the value can be a
     * string, a list, etc...
     */
    private static Object getValue(Variable variable, Invoice invoice) {
        EntityAttribute entityAttribute = variable.getAttribute();

        switch (entityAttribute) {
            case SENDER_CUSTOMER_CODE:
                return invoice.getSender().getCode();
            case SENDER_CUSTOMER_NAME:
                return invoice.getSender().getName();
            case SENDER_CUSTOMER_TIN:
                return invoice.getSender().getTin();
            case SENDER_CUSTOMER_ADDRESS:
                return invoice.getSender().getAddress();
            case SENDER_CUSTOMER_CITY:
                return invoice.getSender().getCity();
            case SENDER_CUSTOMER_PROVINCE:
                return invoice.getSender().getProvince();
            case SENDER_CUSTOMER_ZIPCODE:
                return invoice.getSender().getZipCode();
            case SENDER_CUSTOMER_IBAN:
                return invoice.getSender().getIban();
            case RECIPIENT_CUSTOMER_CODE:
                return invoice.getRecipient().getCode();
            case RECIPIENT_CUSTOMER_NAME:
                return invoice.getRecipient().getName();
            case RECIPIENT_CUSTOMER_TIN:
                return invoice.getRecipient().getTin();
            case RECIPIENT_CUSTOMER_ADDRESS:
                return invoice.getRecipient().getAddress();
            case RECIPIENT_CUSTOMER_CITY:
                return invoice.getRecipient().getCity();
            case RECIPIENT_CUSTOMER_PROVINCE:
                return invoice.getRecipient().getProvince();
            case RECIPIENT_CUSTOMER_ZIPCODE:
                return invoice.getRecipient().getZipCode();
            case RECIPIENT_CUSTOMER_IBAN:
                return invoice.getRecipient().getIban();
            case INVOICE_CODE:
                return invoice.getCode();
            case INVOICE_GENERATION_DATETIME:
                return invoice.getDatetime();
            case INVOICE_ITEMS:
                return invoice.getItems();
            case INVOICE_ITEMS_TOTAL:
                return invoice.getItemsTotal();
            case INVOICE_SUBTOTAL:
                SubtotalVariable subtotalVariable = (SubtotalVariable) variable;
                Subtotal subtotal = subtotalVariable.getSubtotal();
                float invoiceItemsTotal = invoice.getItemsTotal();

                //invoice.getTemplate().g
                return subtotal.calculate(invoiceItemsTotal);
            case INVOICE_TOTAL:
                //return invoice.getTotal();
                return 4;
        }

        return null;
    }

    /**
     * Obtain all the variables data by executing the use case.
     *
     * @return A list with all variables on the system.
     */
    private static Map<String, EntityAttribute> getVariables() {
        ArrayList<Variable> variables = null;

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
     * Generate the invoice.
     *
     * @param invoice The invoice.
     * @param file The file where the invoice must be saved.
     * @throws java.io.IOException
     */
    public static void generate(Invoice invoice, File file) throws IOException, InterruptedException {
        Template template = invoice.getTemplate();

        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
        SpreadsheetInfo.addFreeLimitReachedListener(args -> args.setFreeLimitReachedAction(FreeLimitReachedAction.CONTINUE_AS_TRIAL));

        File templateFile = template.getFile();
        InputStream fileStream = new FileInputStream(templateFile);
        ExcelFile workbook = ExcelFile.load(fileStream);
        ExcelWorksheet worksheet = workbook.getWorksheet(0);

        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Map<String, EntityAttribute> variables = InvoiceGenerator.getVariables();
        Map<String, String> templateFields = template.getFields();
        for (Map.Entry<String, String> field : templateFields.entrySet()) {
            String position = field.getKey();
            String expression = field.getValue();

            Matcher matcher = pattern.matcher(expression);
            String replacedExpression = matcher.replaceAll(match -> {
                String variable = match.group();
                String variableName = variable.substring(2, variable.length() - 1);
                return variables.get(variableName).name();
            });

            // PENSAR SOBRE RANGOS Y ORDEN DE SUBTOTALES DE CARA A CALCULAR EL TOTAL GLOBAL
            ExcelCell cell = worksheet.getCell(position);
            cell.setValue(replacedExpression);
        }

        Timestamp timestamp = Timestamp.from(Instant.now());
        File tmpHtmlFile = File.createTempFile(timestamp.toString(), ".html");
        tmpHtmlFile.deleteOnExit();
        OutputStream htmlStream = new FileOutputStream(tmpHtmlFile);
        workbook.save(htmlStream, new HtmlSaveOptions());

        String executable = WrapperConfig.findExecutable();
        Pdf pdf = new Pdf(new WrapperConfig(executable));
        pdf.addPageFromFile(tmpHtmlFile.getAbsolutePath());
        pdf.saveAs(file.getAbsolutePath());
    }

}