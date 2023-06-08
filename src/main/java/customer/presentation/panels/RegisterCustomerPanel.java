package customer.presentation.panels;

import customer.application.CustomerAttribute;
import customer.application.usecases.RegisterCustomer;
import customer.application.utils.CustomerValidationState;
import customer.persistence.mongo.MongoCustomerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which shows the form to register a customer.
 */
public class RegisterCustomerPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public RegisterCustomerPanel() {
        initComponents();
        initializeFormLabels();
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
        this.setLabelText(nameLabel, LocalizationKey.NAME);
        this.setLabelText(tinLabel, LocalizationKey.TIN);
        this.setLabelText(addressLabel, LocalizationKey.ADDRESS);
        this.setLabelText(cityLabel, LocalizationKey.CITY);
        this.setLabelText(provinceLabel, LocalizationKey.PROVINCE);
        this.setLabelText(zipcodeLabel, LocalizationKey.ZIPCODE);
        this.setLabelText(ibanLabel, LocalizationKey.IBAN);
        this.setLabelText(isSupplierLabel, LocalizationKey.IS_SUPPLIER);
        this.setButtonText(registerButton, LocalizationKey.REGISTER);
    }

    /**
     * Show the information message after the registration process.
     *
     * @param state The validation state for the customer.
     */
    private void showInfoMessage(CustomerValidationState state) {
        Map<CustomerValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(CustomerValidationState.VALID, LocalizationKey.REGISTERED_CUSTOMER_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_NAME, LocalizationKey.INVALID_NAME_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_TIN, LocalizationKey.INVALID_TIN_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_ZIPCODE, LocalizationKey.INVALID_ZIPCODE_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_IBAN, LocalizationKey.INVALID_IBAN_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.DUPLICATED, LocalizationKey.DUPLICATED_CUSTOMER_MESSAGE);

        LocalizationKey key = localizationKeysByState.get(state);
        String infoMessage = Localization.getLocalization(key);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Clear all the form fields to register a customer.
     */
    private void clearForm() {
        this.nameInput.setText("");
        this.tinInput.setText("");
        this.addressInput.setText("");
        this.cityInput.setText("");
        this.provinceInput.setText("");
        this.zipcodeInput.setText("");
        this.ibanInput.setText("");
        this.isSupplierInput.setSelected(false);
    }

    /**
     * Execute the register customer use case.
     *
     * @param newCustomerAttributes The attributes for the customer to register.
     */
    private void registerCustomer(Map<CustomerAttribute, Object> newCustomerAttributes) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            RegisterCustomer registerCustomer = new RegisterCustomer(customerRepository);
            CustomerValidationState state = registerCustomer.execute(newCustomerAttributes);

            this.showInfoMessage(state);

            if (state == CustomerValidationState.VALID) {
                this.clearForm();
            }
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterCustomerPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customer not registered because the database has not been found", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        tinLabel = new javax.swing.JLabel();
        tinInput = new javax.swing.JTextField();
        addressLabel = new javax.swing.JLabel();
        addressInput = new javax.swing.JTextField();
        cityLabel = new javax.swing.JLabel();
        cityInput = new javax.swing.JTextField();
        provinceLabel = new javax.swing.JLabel();
        provinceInput = new javax.swing.JTextField();
        zipcodeLabel = new javax.swing.JLabel();
        zipcodeInput = new javax.swing.JTextField();
        ibanLabel = new javax.swing.JLabel();
        ibanInput = new javax.swing.JTextField();
        registerButton = new javax.swing.JButton();
        isSupplierLabel = new javax.swing.JLabel();
        isSupplierInput = new javax.swing.JCheckBox();

        nameLabel.setText("${NAME}:");

        tinLabel.setText("${TIN}:");

        addressLabel.setText("${ADDRESS}:");

        cityLabel.setText("${CITY}:");

        provinceLabel.setText("${PROVINCE}:");

        zipcodeLabel.setText("${ZIP_CODE}:");

        ibanLabel.setText("${IBAN}:");

        registerButton.setText("${REGISTER}");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        isSupplierLabel.setText("${IS_SUPPLIER}:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tinLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(provinceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zipcodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ibanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(isSupplierLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameInput, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(tinInput)
                            .addComponent(addressInput)
                            .addComponent(cityInput)
                            .addComponent(provinceInput)
                            .addComponent(zipcodeInput)
                            .addComponent(ibanInput))
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(isSupplierInput)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(cityInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(provinceLabel)
                    .addComponent(provinceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zipcodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipcodeLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ibanLabel)
                    .addComponent(ibanInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isSupplierLabel)
                    .addComponent(isSupplierInput))
                .addGap(18, 18, 18)
                .addComponent(registerButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<CustomerAttribute, Object> newCustomerAttributes = new HashMap<>();
        newCustomerAttributes.put(CustomerAttribute.NAME, nameInput.getText());
        newCustomerAttributes.put(CustomerAttribute.TIN, tinInput.getText());
        newCustomerAttributes.put(CustomerAttribute.ADDRESS, addressInput.getText());
        newCustomerAttributes.put(CustomerAttribute.CITY, cityInput.getText());
        newCustomerAttributes.put(CustomerAttribute.PROVINCE, provinceInput.getText());
        newCustomerAttributes.put(CustomerAttribute.ZIPCODE, zipcodeInput.getText());
        newCustomerAttributes.put(CustomerAttribute.IBAN, ibanInput.getText());
        newCustomerAttributes.put(CustomerAttribute.ISSUPPLIER, isSupplierInput.isSelected());

        this.registerCustomer(newCustomerAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressInput;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField cityInput;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField ibanInput;
    private javax.swing.JLabel ibanLabel;
    private javax.swing.JCheckBox isSupplierInput;
    private javax.swing.JLabel isSupplierLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField provinceInput;
    private javax.swing.JLabel provinceLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JTextField tinInput;
    private javax.swing.JLabel tinLabel;
    private javax.swing.JTextField zipcodeInput;
    private javax.swing.JLabel zipcodeLabel;
    // End of variables declaration//GEN-END:variables
}
