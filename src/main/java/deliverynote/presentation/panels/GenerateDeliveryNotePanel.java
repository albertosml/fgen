package deliverynote.presentation.panels;

import container.application.Pallet;
import container.application.usecases.ListPallets;
import container.persistence.mongo.MongoContainerRepository;
import customer.application.Customer;
import customer.application.usecases.ListCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteAttribute;
import deliverynote.application.usecases.CreateDeliveryNote;
import deliverynote.application.utils.DeliveryNoteGenerator;
import deliverynote.application.utils.DeliveryNoteValidationState;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import product.application.Product;
import product.application.usecases.ListProducts;
import product.persistence.mongo.MongoProductRepository;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import shared.application.Pair;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import template.application.Template;
import template.application.usecases.ListTemplates;
import template.persistence.mongo.MongoTemplateRepository;
import weighing.presentation.panels.WeighingsPanel;

/**
 * Panel which shows the form to register a container.
 */
public class GenerateDeliveryNotePanel extends javax.swing.JPanel {

    /**
     * Weighings panel.
     */
    private WeighingsPanel weighingsPanel;

    /**
     * Constructor.
     */
    public GenerateDeliveryNotePanel() {
        initComponents();
        initializeFormLabels();
        initializeInputs();

        this.weighingsPanel = new WeighingsPanel();
        this.bookedPanel.setLayout(new GridLayout());
        this.bookedPanel.add(weighingsPanel);
    }

    /**
     * Obtain all the non-removed customers data by executing the use case.
     *
     * @return A list with all non-removed customers on the system.
     */
    private ArrayList<Customer> getCustomers() {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ListCustomers listCustomers = new ListCustomers(customerRepository);
            return listCustomers.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customers cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Obtain all the pallets data by executing the use case.
     *
     * @return A list with all non-removed pallets on the system.
     */
    private ArrayList<Pallet> getPallets() {
        try {
            MongoContainerRepository containerRepository = new MongoContainerRepository();
            ListPallets listPallets = new ListPallets(containerRepository);
            return listPallets.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = WeighingsPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Pallets cannot be shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Obtain all the non-removed products data by executing the use case.
     *
     * @return A list with all non-removed products on the system.
     */
    private ArrayList<Product> getProducts() {
        try {
            MongoProductRepository productRepository = new MongoProductRepository();
            ListProducts listProducts = new ListProducts(productRepository);
            return listProducts.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Products cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Obtain all the non-removed templates data by executing the use case.
     *
     * @return A list with all non-removed templates on the system.
     */
    private ArrayList<Template> getTemplates() {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            ListTemplates listTemplates = new ListTemplates(templateRepository);
            return listTemplates.execute(false);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Templates cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Set the text for a label component.
     *
     * @param label The label component.
     * @param key The localization key to use for getting the label text.
     */
    private void setLabelText(JLabel label, LocalizationKey key) {
        String localization = Localization.getLocalization(key);
        String labelText = String.format("%s:", localization);
        label.setText(labelText);
    }

    /**
     * Set the text for a button component.
     *
     * @param button The button component.
     * @param key The localization key to use for getting the button text.
     */
    private void setButtonText(JButton button, LocalizationKey key) {
        String localization = Localization.getLocalization(key);
        button.setText(localization);
    }

    /**
     * Initialize form labels.
     */
    private void initializeFormLabels() {
        this.setLabelText(customerLabel, LocalizationKey.CUSTOMER);
        this.setLabelText(productLabel, LocalizationKey.PRODUCT);
        this.setLabelText(templateLabel, LocalizationKey.TEMPLATE);
        this.setLabelText(palletLabel, LocalizationKey.PALLET);
        this.setLabelText(numPalletsLabel, LocalizationKey.NUM_PALLETS);
        this.setButtonText(registerButton, LocalizationKey.REGISTER);
    }

    /**
     * Initialize inputs.
     */
    private void initializeInputs() {
        // Customers input.
        Vector<Customer> customers = new Vector<>(this.getCustomers());
        customerInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Customer>(customers));
        AutoCompleteDecorator.decorate(customerInput);

        // Product input.
        Vector<Product> products = new Vector<>(this.getProducts());
        productInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Product>(products));
        AutoCompleteDecorator.decorate(productInput);

        // Templates input.
        Vector<Template> templates = new Vector<>(this.getTemplates());
        templateInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Template>(templates));
        AutoCompleteDecorator.decorate(templateInput);

        // Pallets input.
        Vector<Pallet> pallets = new Vector<>(this.getPallets());
        palletInput.setModel((ComboBoxModel) new DefaultComboBoxModel<Pallet>(pallets));
        AutoCompleteDecorator.decorate(palletInput);

        // Number of pallets input.
        SpinnerNumberModel numPalletsSpinnerNumberModel = new SpinnerNumberModel(0, 0, 4, 1);
        this.numPalletsInput.setModel(numPalletsSpinnerNumberModel);
    }

    /**
     * Show the information message after the generation process.
     *
     * @param state The delivery note validation state.
     */
    private void showInfoMessage(DeliveryNoteValidationState state) {
        Map<DeliveryNoteValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(DeliveryNoteValidationState.VALID, LocalizationKey.GENERATED_DELIVERY_NOTE_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID_CUSTOMER, LocalizationKey.INVALID_CUSTOMER_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID_PRODUCT, LocalizationKey.INVALID_PRODUCT_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID_TEMPLATE, LocalizationKey.INVALID_TEMPLATE_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID_PALLET, LocalizationKey.INVALID_PALLET_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID_NUM_PALLETS, LocalizationKey.INVALID_NUM_PALLETS_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID_WEIGHINGS, LocalizationKey.INVALID_WEIGHINGS_MESSAGE);
        localizationKeysByState.put(DeliveryNoteValidationState.INVALID, LocalizationKey.NOT_GENERATED_DELIVERY_NOTE_MESSAGE);

        LocalizationKey key = localizationKeysByState.get(state);
        String infoMessage = Localization.getLocalization(key);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Clear all the form fields.
     */
    private void clearForm() {
        this.customerInput.setSelectedItem(null);
        this.productInput.setSelectedItem(null);
        this.templateInput.setSelectedItem(null);
        this.palletInput.setSelectedItem(null);
        this.numPalletsInput.setValue(0);
        this.weighingsPanel.clear();
    }

    /**
     * Execute the create delivery note use case.
     *
     * @param attributes Map containing the value for each delivery note
     * attribute.
     * @return A pair indicating the delivery note and its validation state.
     */
    private Pair<DeliveryNote, DeliveryNoteValidationState> createDeliveryNote(Map<DeliveryNoteAttribute, Object> attributes) {
        try {
            MongoDeliveryNoteRepository deliveryNoteRepository = new MongoDeliveryNoteRepository();
            CreateDeliveryNote createDeliveryNote = new CreateDeliveryNote(deliveryNoteRepository);
            return createDeliveryNote.execute(attributes);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Delivery note cannot be created because the database has not been found", ex);
        }

        return new Pair<>(null, DeliveryNoteValidationState.INVALID);
    }

    /**
     * Select the file where the delivery note will be saved.
     *
     * Note that the generated file will be a PDF.
     *
     * @return The file where the delivery note will be generated.
     */
    private File selectDeliveryNoteFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Supported formats (PDF).
        FileNameExtensionFilter pdfFilesFilter = new FileNameExtensionFilter("PDF files", "pdf");
        fileChooser.setFileFilter(pdfFilesFilter);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    public void generateDeliveryNote() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerLabel = new javax.swing.JLabel();
        customerInput = new javax.swing.JComboBox<>();
        productLabel = new javax.swing.JLabel();
        productInput = new javax.swing.JComboBox<>();
        templateLabel = new javax.swing.JLabel();
        templateInput = new javax.swing.JComboBox<>();
        palletLabel = new javax.swing.JLabel();
        bookedPanel = new javax.swing.JPanel();
        registerButton = new javax.swing.JButton();
        numPalletsLabel = new javax.swing.JLabel();
        numPalletsInput = new javax.swing.JSpinner();
        palletInput = new javax.swing.JComboBox<>();

        customerLabel.setText("${CUSTOMER}:");

        customerInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        productLabel.setText("${PRODUCT}:");

        productInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        templateLabel.setText("${TEMPLATE}:");

        templateInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        palletLabel.setText("${PALLET}:");

        bookedPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout bookedPanelLayout = new javax.swing.GroupLayout(bookedPanel);
        bookedPanel.setLayout(bookedPanelLayout);
        bookedPanelLayout.setHorizontalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bookedPanelLayout.setVerticalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        registerButton.setText("${REGISTER}");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        numPalletsLabel.setText("${NUM_PALLETS}:");

        numPalletsInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                numPalletsInputStateChanged(evt);
            }
        });

        palletInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palletLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(templateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(customerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numPalletsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numPalletsInput)
                    .addComponent(productInput, 0, 269, Short.MAX_VALUE)
                    .addComponent(templateInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(palletInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customerInput, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(templateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(templateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(palletInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(palletLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numPalletsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numPalletsLabel))
                .addGap(18, 18, 18)
                .addComponent(bookedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(registerButton)
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<DeliveryNoteAttribute, Object> newDeliveryNoteAttributes = new HashMap<>();
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.CUSTOMER, customerInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.PRODUCT, productInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.TEMPLATE, templateInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.PALLET, palletInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.NUM_PALLETS, numPalletsInput.getValue());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.WEIGHINGS, weighingsPanel.getWeighings());

        Pair<DeliveryNote, DeliveryNoteValidationState> deliveryNotePair = this.createDeliveryNote(newDeliveryNoteAttributes);

        DeliveryNoteValidationState state = deliveryNotePair.getSecond();
        if (state != DeliveryNoteValidationState.VALID) {
            this.showInfoMessage(state);
            return;
        }

        DeliveryNote deliveryNote = deliveryNotePair.getFirst();
        if (deliveryNote != null) {
            try {
                DeliveryNoteGenerator.generate(deliveryNote);
            } catch (IOException | InterruptedException ex) {
                this.showInfoMessage(DeliveryNoteValidationState.INVALID);
                return;
            }
        }

        this.showInfoMessage(state);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void numPalletsInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_numPalletsInputStateChanged
        this.weighingsPanel.setTotalWeighings((int) this.numPalletsInput.getValue());
    }//GEN-LAST:event_numPalletsInputStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bookedPanel;
    private javax.swing.JComboBox<String> customerInput;
    private javax.swing.JLabel customerLabel;
    private javax.swing.JSpinner numPalletsInput;
    private javax.swing.JLabel numPalletsLabel;
    private javax.swing.JComboBox<String> palletInput;
    private javax.swing.JLabel palletLabel;
    private javax.swing.JComboBox<String> productInput;
    private javax.swing.JLabel productLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JComboBox<String> templateInput;
    private javax.swing.JLabel templateLabel;
    // End of variables declaration//GEN-END:variables
}
