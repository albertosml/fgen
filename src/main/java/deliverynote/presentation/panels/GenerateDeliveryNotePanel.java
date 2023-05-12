package deliverynote.presentation.panels;

import container.application.ContainerAttribute;
import container.application.usecases.RegisterContainer;
import container.application.utils.ContainerValidationState;
import container.persistence.mongo.MongoContainerRepository;
import customer.application.Customer;
import customer.application.usecases.ListCustomers;
import customer.persistence.mongo.MongoCustomerRepository;
import deliverynote.application.DeliveryNote;
import deliverynote.application.DeliveryNoteAttribute;
import deliverynote.application.utils.DeliveryNoteValidationState;
import deliverynote.persistence.mongo.MongoDeliveryNoteRepository;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
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

/**
 * Panel which shows the form to register a container.
 */
public class GenerateDeliveryNotePanel extends javax.swing.JPanel {

    /**
     * Delivery note items panel.
     */
    private DeliveryNoteItemsPanel deliveryNoteItemsPanel;

    /**
     * Constructor.
     */
    public GenerateDeliveryNotePanel() {
        initComponents();
        initializeFormLabels();
        initializeInputs();

        this.deliveryNoteItemsPanel = new DeliveryNoteItemsPanel();
        this.bookedPanel.setLayout(new GridLayout());
        this.bookedPanel.add(deliveryNoteItemsPanel);
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
        this.setLabelText(weightLabel, LocalizationKey.WEIGHT);
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

        // Weight input.
        SpinnerNumberModel weightSpinnerNumberModel = new SpinnerNumberModel(0d, 0d, 1e11d, 0.01d);
        this.weightInput.setModel(weightSpinnerNumberModel);
    }

    /**
     * Show the information message after the registration process.
     *
     * @param state The container validation state.
     */
    private void showInfoMessage(ContainerValidationState state) {
        Map<ContainerValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(ContainerValidationState.VALID, LocalizationKey.REGISTERED_CONTAINER_MESSAGE);
        localizationKeysByState.put(ContainerValidationState.INVALID_CODE, LocalizationKey.INVALID_CODE_MESSAGE);
        localizationKeysByState.put(ContainerValidationState.INVALID_NAME, LocalizationKey.INVALID_NAME_MESSAGE);
        localizationKeysByState.put(ContainerValidationState.INVALID_WEIGHT, LocalizationKey.INVALID_WEIGHT_MESSAGE);
        localizationKeysByState.put(ContainerValidationState.DUPLICATED, LocalizationKey.DUPLICATED_CONTAINER_MESSAGE);

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
        this.weightInput.setValue(0);
        this.deliveryNoteItemsPanel.clear();
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
        weightLabel = new javax.swing.JLabel();
        weightInput = new javax.swing.JSpinner();
        bookedPanel = new javax.swing.JPanel();
        registerButton = new javax.swing.JButton();

        customerLabel.setText("${CUSTOMER}:");

        customerInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        productLabel.setText("${PRODUCT}:");

        productInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        templateLabel.setText("${TEMPLATE}:");

        templateInput.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        weightLabel.setText("${WEIGHT}:");

        weightInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        bookedPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout bookedPanelLayout = new javax.swing.GroupLayout(bookedPanel);
        bookedPanel.setLayout(bookedPanelLayout);
        bookedPanelLayout.setHorizontalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(weightLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(productLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(customerLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(templateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(weightInput)
                    .addComponent(customerInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(templateInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(templateInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(templateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightLabel)
                    .addComponent(weightInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(bookedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(registerButton)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<DeliveryNoteAttribute, Object> newDeliveryNoteAttributes = new HashMap<>();
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.CUSTOMER, customerInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.PRODUCT, productInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.TEMPLATE, templateInput.getSelectedItem());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.WEIGHT, weightInput.getValue());
        newDeliveryNoteAttributes.put(DeliveryNoteAttribute.ITEMS, deliveryNoteItemsPanel.getItems());

        this.generateDeliveryNote(newDeliveryNoteAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bookedPanel;
    private javax.swing.JComboBox<String> customerInput;
    private javax.swing.JLabel customerLabel;
    private javax.swing.JComboBox<String> productInput;
    private javax.swing.JLabel productLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JComboBox<String> templateInput;
    private javax.swing.JLabel templateLabel;
    private javax.swing.JSpinner weightInput;
    private javax.swing.JLabel weightLabel;
    // End of variables declaration//GEN-END:variables
}
