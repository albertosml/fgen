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
import container.application.Pallet;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteData;
import deliverynote.application.usecases.SaveDeliveryNote;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
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
import javax.swing.JOptionPane;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import template.application.Template;
import template.application.usecases.ShowTemplate;
import template.persistence.mongo.MongoTemplateRepository;
import variable.application.EntityAttribute;
import static variable.application.EntityAttribute.DELIVERY_NOTE_CODE;
import static variable.application.EntityAttribute.DELIVERY_NOTE_GENERATION_DATETIME;
import static variable.application.EntityAttribute.DELIVERY_NOTE_ITEMS;
import static variable.application.EntityAttribute.DELIVERY_NOTE_NET_WEIGHT;
import static variable.application.EntityAttribute.DELIVERY_NOTE_TOTAL_BOXES;
import static variable.application.EntityAttribute.DELIVERY_NOTE_TOTAL_PALLETS;
import static variable.application.EntityAttribute.DELIVERY_NOTE_TOTAL_WEIGHT_PER_BOX;
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
import weighing.application.Weighing;

/**
 * Represents the class responsible of generating the invoice.
 */
public class InvoiceGenerator {

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
    private static File convertToPdf(ExcelFile workbook, Date date) throws IOException, InterruptedException {
        String timestamp = Long.toString(date.getTime());

        File tmpHtmlFile = File.createTempFile(timestamp, ".html");
        tmpHtmlFile.deleteOnExit();
        OutputStream htmlStream = new FileOutputStream(tmpHtmlFile);
        workbook.save(htmlStream, new HtmlSaveOptions());

        String executable = WrapperConfig.findExecutable();
        Pdf pdf = new Pdf(new WrapperConfig(executable));
        pdf.addParam(new Param("--page-size", "A5"));
        pdf.addPageFromFile(tmpHtmlFile.getAbsolutePath());

        File tmpPdfFile = File.createTempFile(timestamp, ".pdf");
        tmpPdfFile.deleteOnExit();
        return pdf.saveAs(tmpPdfFile.getAbsolutePath());
    }

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
            case FARMER_CUSTOMER_CODE:
                return deliveryNote.getFarmer().getCode();
            case FARMER_CUSTOMER_NAME:
                return deliveryNote.getFarmer().getName();
            case FARMER_CUSTOMER_TIN:
                return deliveryNote.getFarmer().getTin();
            case FARMER_CUSTOMER_ADDRESS:
                return deliveryNote.getFarmer().getAddress();
            case FARMER_CUSTOMER_CITY:
                return deliveryNote.getFarmer().getCity();
            case FARMER_CUSTOMER_PROVINCE:
                return deliveryNote.getFarmer().getProvince();
            case FARMER_CUSTOMER_ZIPCODE:
                return deliveryNote.getFarmer().getZipCode();
            case FARMER_CUSTOMER_IBAN:
                return deliveryNote.getFarmer().getIban();
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
    private static ExcelFile loadTemplate(Template template) throws FileNotFoundException, IOException {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
        SpreadsheetInfo.addFreeLimitReachedListener(args -> args.setFreeLimitReachedAction(FreeLimitReachedAction.CONTINUE_AS_TRIAL));

        File templateFile = template.getFile();
        InputStream fileStream = new FileInputStream(templateFile);
        return ExcelFile.load(fileStream);
    }

    /**
     * Save delivery note on the database.
     *
     * @param workbook The excel file to save.
     * @param deliveryNote The delivery note data.
     * @throws java.io.IOException if the file is not found.
     * @throws java.lang.InterruptedException if the conversion is interrupted.
     */
    private static void save(ExcelFile workbook, DeliveryNote deliveryNote) throws IOException, InterruptedException {
        File pdfFile = DeliveryNoteGenerator.convertToPdf(workbook, deliveryNote.getDate());

        // Call to use case.
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            SaveDeliveryNote saveDeliveryNote = new SaveDeliveryNote(deliveryNoteRepository);
            saveDeliveryNote.execute(deliveryNote, pdfFile);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = DeliveryNoteGenerator.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Delivery note not saved because the database has not been found", ex);
        }
    }

    /**
     * Write delivery note items into the spreadsheet cells.
     *
     * @param position Start position.
     * @param weighings Weighings to write.
     * @param pallet The pallet to remove from the gross weight to calculate the
     * net weight.
     * @param worksheet The worksheet where we are going to write the delivery
     * note data.
     */
    private static void writeDeliveryNoteItems(String position, ArrayList<Weighing> weighings, Pallet pallet, ExcelWorksheet worksheet) {
        ExcelCell cell = worksheet.getCell(position);
        int rowIndex = cell.getRow().getIndex();
        int firstColumnIndex = cell.getColumn().getIndex();

        for (Weighing weighing : weighings) {
            // Number of pallets.
            ExcelCell numPalletsCell = worksheet.getCell(rowIndex, firstColumnIndex);
            numPalletsCell.setValue(1);

            // Number of boxes.
            ExcelCell numBoxesCell = worksheet.getCell(rowIndex, firstColumnIndex + 1);
            numBoxesCell.setValue(weighing.getQty());

            // Net weight.
            double boxWeight = weighing.getBox().getWeight();
            int netWeight = (int) (weighing.getWeight() - (weighing.getQty() * boxWeight) - pallet.getWeight());
            ExcelCell netWeightCell = worksheet.getCell(rowIndex, firstColumnIndex + 2);
            netWeightCell.setValue(netWeight);

            // Weight per box.
            double weightPerBox = (double) netWeight / weighing.getQty();
            ExcelCell weightPerBoxCell = worksheet.getCell(rowIndex, firstColumnIndex + 3);
            weightPerBoxCell.setValue(Math.round(weightPerBox * 100.0) / 100.0);

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
     * @param deliveryNote Delivery note.
     * @param variables Variables list.
     * @param worksheet Worksheet to write the variable.
     */
    private static void writeVariable(String position, String expression, Pattern pattern, DeliveryNote deliveryNote, Map<String, EntityAttribute> variables, ExcelWorksheet worksheet) {
        Matcher matcher = pattern.matcher(expression);
        String replacedExpression = matcher.replaceAll(match -> {
            String variable = match.group();
            // Remove the "${" and "}" from the variable.
            String variableName = variable.substring(2, variable.length() - 1);
            EntityAttribute entityAttribute = variables.get(variableName);
            Object variableValue = DeliveryNoteGenerator.getValue(entityAttribute, deliveryNote);
            return variableValue.toString();
        });

        ExcelCell cell = worksheet.getCell(position);
        cell.setValue(replacedExpression);
    }

    private boolean generateFarmerInvoice() {
        Template farmerInvoiceTemplate = this.getTemplate(3);

        if (farmerInvoiceTemplate == null) {
            return false;
        }

    }

    /**
     * Generate the invoices, one for the supplier and another for the farmer.
     *
     * @param deliveryNotesData The delivery notes data.
     * @throws java.io.IOException if the file is not found.
     * @throws java.lang.InterruptedException if the conversion is interrupted.
     */
    public void generate(ArrayList<DeliveryNoteData> deliveryNotesData, Date startPeriod, Date endPeriod) throws IOException, InterruptedException {
        boolean isFarmerInvoiceGenerated = this.generateFarmerInvoice();

        ExcelFile workbook = DeliveryNoteGenerator.loadTemplate(template);
        ExcelWorksheet worksheet = workbook.getWorksheet(0);

        Pattern variablesPattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Map<String, EntityAttribute> variables = DeliveryNoteGenerator.getVariables();
        Map<String, String> templateFields = template.getFields();
        for (Map.Entry<String, String> field : templateFields.entrySet()) {
            String position = field.getKey();
            String expression = field.getValue();

            // Specific processing for the delivery note items.
            String expressionVariableName = expression.substring(2, expression.length() - 1);
            EntityAttribute expressionEntityAttribute = variables.get(expressionVariableName);
            boolean shouldWriteDeliveryNoteItems = expressionEntityAttribute == EntityAttribute.DELIVERY_NOTE_ITEMS;
            if (shouldWriteDeliveryNoteItems) {
                DeliveryNoteGenerator.writeDeliveryNoteItems(position, deliveryNote.getWeighings(), deliveryNote.getPallet(), worksheet);
                continue;
            }

            DeliveryNoteGenerator.writeVariable(position, expression, variablesPattern, deliveryNote, variables, worksheet);
        }

        DeliveryNoteGenerator.save(workbook, deliveryNote);
    }

}
