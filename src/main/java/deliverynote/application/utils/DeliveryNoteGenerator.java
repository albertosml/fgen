package deliverynote.application.utils;

import com.gembox.spreadsheet.ExcelCell;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.FreeLimitReachedAction;
import com.gembox.spreadsheet.HtmlSaveOptions;
import com.gembox.spreadsheet.SpreadsheetInfo;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import deliverynote.application.DeliveryNote;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import template.application.Template;
import variable.application.EntityAttribute;
import static variable.application.EntityAttribute.DELIVERY_NOTE_NET_WEIGHT;
import variable.application.Variable;
import variable.application.usecases.ListVariables;
import variable.persistence.mongo.MongoVariableRepository;
import weighing.application.Weighing;

/**
 * Represents the class responsible of generating the delivery note.
 */
public class DeliveryNoteGenerator {

    /**
     * Obtain the value from the given entity attribute and delivery note.
     *
     * @param entityAttribtue The entity attribute used to get the value.
     * @param deliveryNote The delivery note used to retrieve the value.
     * @return An object indicating the requested value, as the value can be a
     * string, a list, etc...
     */
    private static Object getValue(EntityAttribute entityAttribute, DeliveryNote deliveryNote) {
        switch (entityAttribute) {
            case CUSTOMER_CODE:
                return deliveryNote.getCustomer().getCode();
            case CUSTOMER_NAME:
                return deliveryNote.getCustomer().getName();
            case CUSTOMER_TIN:
                return deliveryNote.getCustomer().getTin();
            case CUSTOMER_ADDRESS:
                return deliveryNote.getCustomer().getAddress();
            case CUSTOMER_CITY:
                return deliveryNote.getCustomer().getCity();
            case CUSTOMER_PROVINCE:
                return deliveryNote.getCustomer().getProvince();
            case CUSTOMER_ZIPCODE:
                return deliveryNote.getCustomer().getZipCode();
            case CUSTOMER_IBAN:
                return deliveryNote.getCustomer().getIban();
            case PRODUCT_CODE:
                return deliveryNote.getProduct().getCode();
            case PRODUCT_NAME:
                return deliveryNote.getProduct().getName();
            case DELIVERY_NOTE_ITEMS:
                return deliveryNote.getWeighings();
            case DELIVERY_NOTE_CODE:
                return deliveryNote.getCode();
            case DELIVERY_NOTE_GENERATION_DATETIME:
                String pattern = "dd-MM-yyyy HH:mm:ss";
                DateFormat df = new SimpleDateFormat(pattern);
                Date date = deliveryNote.getDate();
                return df.format(date);
            case DELIVERY_NOTE_NET_WEIGHT:
                return deliveryNote.calculateNetWeight();
            case DELIVERY_NOTE_TOTAL_PALLETS:
                return deliveryNote.calculateTotalPallets();
            case DELIVERY_NOTE_TOTAL_BOXES:
                return deliveryNote.calculateTotalBoxes();
            case DELIVERY_NOTE_TOTAL_WEIGHT_PER_BOX:
                return deliveryNote.calculateTotalWeightPerBox();
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
     * Generate the delivery note.
     *
     * @param deliveryNote The delivery note.
     * @param file The file where the invoice must be saved.
     * @throws java.io.IOException
     */
    public static void generate(DeliveryNote deliveryNote, File file) throws IOException, InterruptedException {
        Template template = deliveryNote.getTemplate();

        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
        SpreadsheetInfo.addFreeLimitReachedListener(args -> args.setFreeLimitReachedAction(FreeLimitReachedAction.CONTINUE_AS_TRIAL));

        File templateFile = template.getFile();
        InputStream fileStream = new FileInputStream(templateFile);
        ExcelFile workbook = ExcelFile.load(fileStream);
        ExcelWorksheet worksheet = workbook.getWorksheet(0);

        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Map<String, EntityAttribute> variables = DeliveryNoteGenerator.getVariables();
        Map<String, String> templateFields = template.getFields();
        for (Map.Entry<String, String> field : templateFields.entrySet()) {
            String position = field.getKey();
            String expression = field.getValue();

            // Specific processing for the delivery note items.
            if (variables.get(expression.substring(2, expression.length() - 1)) == EntityAttribute.DELIVERY_NOTE_ITEMS) {
                ExcelCell cell = worksheet.getCell(position);
                int rowIndex = cell.getRow().getIndex();
                int firstColumnIndex = cell.getColumn().getIndex();

                for (Weighing weighing : deliveryNote.getWeighings()) {
                    // Number of pallets.
                    ExcelCell numPalletsCell = worksheet.getCell(rowIndex, firstColumnIndex);
                    numPalletsCell.setValue(1);

                    // Number of boxes.
                    ExcelCell numBoxesCell = worksheet.getCell(rowIndex, firstColumnIndex + 1);
                    numBoxesCell.setValue(weighing.getQty());

                    // Net weight.
                    int boxWeight = (int) weighing.getBox().getWeight();
                    int netWeight = weighing.getWeight() - (weighing.getQty() * boxWeight);
                    ExcelCell netWeightCell = worksheet.getCell(rowIndex, firstColumnIndex + 2);
                    netWeightCell.setValue(netWeight);

                    // Weight per box.
                    ExcelCell weightPerBoxCell = worksheet.getCell(rowIndex, firstColumnIndex + 3);
                    weightPerBoxCell.setValue(Math.round(netWeight / weighing.getQty() * 100.0) / 100.0);

                    // Move to next row.
                    rowIndex++;
                }

                continue;
            }

            Matcher matcher = pattern.matcher(expression);
            String replacedExpression = matcher.replaceAll(match -> {
                String variable = match.group();
                String variableName = variable.substring(2, variable.length() - 1);
                EntityAttribute entityAttribute = variables.get(variableName);
                Object variableValue = DeliveryNoteGenerator.getValue(entityAttribute, deliveryNote);
                return variableValue.toString();
            });

            ExcelCell cell = worksheet.getCell(position);
            cell.setValue(replacedExpression);
        }

        File tmpHtmlFile = File.createTempFile(Long.toString(deliveryNote.getDate().getTime()), ".html");
        tmpHtmlFile.deleteOnExit();
        OutputStream htmlStream = new FileOutputStream(tmpHtmlFile);
        workbook.save(htmlStream, new HtmlSaveOptions());

        String executable = WrapperConfig.findExecutable();
        Pdf pdf = new Pdf(new WrapperConfig(executable));
        pdf.addParam(new Param("--page-size", "A5"));
        pdf.addPageFromFile(tmpHtmlFile.getAbsolutePath());
        pdf.saveAs(file.getAbsolutePath());
    }

}
