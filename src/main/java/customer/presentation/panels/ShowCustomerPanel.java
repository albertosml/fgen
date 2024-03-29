package customer.presentation.panels;

import customer.application.Customer;
import customer.application.CustomerAttribute;
import customer.application.usecases.ShowCustomer;
import customer.application.usecases.UpdateCustomer;
import customer.application.utils.CustomerValidationState;
import customer.persistence.mongo.MongoCustomerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.MainFrame;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which shows the form with the customer details.
 */
public class ShowCustomerPanel extends javax.swing.JPanel {

    /**
     * Initial TIN.
     */
    private String initialTin;

    /**
     * Constructor.
     *
     * @param customerCode The customer code.
     */
    public ShowCustomerPanel(int customerCode) {
        initComponents();
        initializeFormLabels();
        initializeFormInputs(customerCode);
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
        this.setLabelText(codeLabel, LocalizationKey.CODE);
        this.setLabelText(nameLabel, LocalizationKey.NAME);
        this.setLabelText(tinLabel, LocalizationKey.TIN);
        this.setLabelText(addressLabel, LocalizationKey.ADDRESS);
        this.setLabelText(cityLabel, LocalizationKey.CITY);
        this.setLabelText(provinceLabel, LocalizationKey.PROVINCE);
        this.setLabelText(zipcodeLabel, LocalizationKey.ZIPCODE);
        this.setLabelText(ibanLabel, LocalizationKey.IBAN);
        this.setLabelText(isFarmerLabel, LocalizationKey.IS_FARMER);
        this.setLabelText(isDeletedLabel, LocalizationKey.IS_DELETED);
        this.setButtonText(updateButton, LocalizationKey.UPDATE);
    }

    /**
     * Find the customer given the customer code.
     *
     * @param customerCode The customer code.
     * @return The found customer, otherwise null.
     */
    private Customer findCustomer(int customerCode) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            ShowCustomer showCustomer = new ShowCustomer(customerRepository);
            return showCustomer.execute(customerCode);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ShowCustomerPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customer not shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Fill the inputs with the customer data.
     *
     * @param customer The customer.
     */
    private void fillCustomerData(Customer customer) {
        this.codeInput.setText(Integer.toString(customer.getCode()));
        this.nameInput.setText(customer.getName());
        this.tinInput.setText(customer.getTin());
        this.addressInput.setText(customer.getAddress());
        this.cityInput.setText(customer.getCity());
        this.provinceInput.setText(customer.getProvince());
        this.zipcodeInput.setText(customer.getZipCode());
        this.ibanInput.setText(customer.getIban());
        this.isFarmerCheckbox.setSelected(customer.isFarmer());
        this.isDeletedCheckbox.setSelected(customer.isDeleted());
    }

    /**
     * Disable inputs so they cannot be edited.
     */
    private void disableInputs() {
        this.nameInput.setEditable(false);
        this.tinInput.setEditable(false);
        this.addressInput.setEditable(false);
        this.cityInput.setEditable(false);
        this.provinceInput.setEditable(false);
        this.zipcodeInput.setEditable(false);
        this.ibanInput.setEditable(false);
        this.isFarmerCheckbox.setEnabled(false);
    }

    /**
     * Disable the update button.
     */
    private void disableButton() {
        this.updateButton.setEnabled(false);
    }

    /**
     * Initialize form inputs.
     *
     * @param customerCode The customer code.
     */
    private void initializeFormInputs(int customerCode) {
        Customer customer = this.findCustomer(customerCode);

        if (customer == null) {
            MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            mainFrame.redirectToListCustomers();
        } else {
            this.initialTin = customer.getTin();
            this.fillCustomerData(customer);

            if (customer.isDeleted()) {
                this.disableInputs();
                this.disableButton();
            }
        }
    }

    /**
     * Show the information message after the update process.
     *
     * @param state The customer validation state.
     */
    private void showInfoMessage(CustomerValidationState state) {
        Map<CustomerValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(CustomerValidationState.VALID, LocalizationKey.UPDATED_CUSTOMER_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_NAME, LocalizationKey.INVALID_NAME_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_TIN, LocalizationKey.INVALID_TIN_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_ZIPCODE, LocalizationKey.INVALID_ZIPCODE_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.INVALID_IBAN, LocalizationKey.INVALID_IBAN_MESSAGE);
        localizationKeysByState.put(CustomerValidationState.DUPLICATED, LocalizationKey.DUPLICATED_CUSTOMER_MESSAGE);

        LocalizationKey key = localizationKeysByState.getOrDefault(state, LocalizationKey.NOT_UPDATED_CUSTOMER_MESSAGE);
        String infoMessage = Localization.getLocalization(key);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Execute the update customer use case.
     *
     * @param customerAttributes The customer attributes.
     */
    private void updateCustomer(Map<CustomerAttribute, Object> customerAttributes) {
        try {
            MongoCustomerRepository customerRepository = new MongoCustomerRepository();
            UpdateCustomer updateCustomer = new UpdateCustomer(customerRepository);

            String inputTin = (String) customerAttributes.get(CustomerAttribute.TIN);
            boolean modifiedTin = !inputTin.equals(this.initialTin);

            CustomerValidationState state = updateCustomer.execute(customerAttributes, modifiedTin);

            this.showInfoMessage(state);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ShowCustomerPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Customer not updated because the database has not been found", ex);
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
        codeLabel = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        codeInput = new javax.swing.JTextField();
        isFarmerLabel = new javax.swing.JLabel();
        isFarmerCheckbox = new javax.swing.JCheckBox();
        isDeletedLabel = new javax.swing.JLabel();
        isDeletedCheckbox = new javax.swing.JCheckBox();

        nameLabel.setText("${NAME}:");

        tinLabel.setText("${TIN}:");

        addressLabel.setText("${ADDRESS}:");

        cityLabel.setText("${CITY}:");

        provinceLabel.setText("${PROVINCE}:");

        zipcodeLabel.setText("${ZIP_CODE}:");

        ibanLabel.setText("${IBAN}:");

        codeLabel.setText("${CODE}:");

        updateButton.setText("${UPDATE}");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        codeInput.setEditable(false);

        isFarmerLabel.setText("${IS_FARMER}:");

        isDeletedLabel.setText("${IS_DELETED}:");

        isDeletedCheckbox.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addressLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tinLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(provinceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zipcodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ibanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(codeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(isFarmerLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(isDeletedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codeInput)
                            .addComponent(tinInput)
                            .addComponent(addressInput)
                            .addComponent(cityInput)
                            .addComponent(provinceInput)
                            .addComponent(zipcodeInput)
                            .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(ibanInput, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nameInput))
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isFarmerCheckbox)
                            .addComponent(isDeletedCheckbox))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeLabel)
                    .addComponent(codeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tinInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(cityInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(provinceLabel)
                    .addComponent(provinceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zipcodeLabel)
                    .addComponent(zipcodeInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ibanLabel)
                    .addComponent(ibanInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isFarmerLabel)
                    .addComponent(isFarmerCheckbox))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isDeletedLabel)
                    .addComponent(isDeletedCheckbox))
                .addGap(18, 18, 18)
                .addComponent(updateButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        Map<CustomerAttribute, Object> customerAttributes = new HashMap<>();
        customerAttributes.put(CustomerAttribute.CODE, Integer.valueOf(this.codeInput.getText()));
        customerAttributes.put(CustomerAttribute.NAME, this.nameInput.getText());
        customerAttributes.put(CustomerAttribute.TIN, this.tinInput.getText());
        customerAttributes.put(CustomerAttribute.ADDRESS, this.addressInput.getText());
        customerAttributes.put(CustomerAttribute.PROVINCE, this.provinceInput.getText());
        customerAttributes.put(CustomerAttribute.CITY, this.cityInput.getText());
        customerAttributes.put(CustomerAttribute.ZIPCODE, this.zipcodeInput.getText());
        customerAttributes.put(CustomerAttribute.IBAN, this.ibanInput.getText());
        customerAttributes.put(CustomerAttribute.ISFARMER, this.isFarmerCheckbox.isSelected());
        customerAttributes.put(CustomerAttribute.ISDELETED, this.isDeletedCheckbox.isSelected());

        this.updateCustomer(customerAttributes);
    }//GEN-LAST:event_updateButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressInput;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField cityInput;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField codeInput;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JTextField ibanInput;
    private javax.swing.JLabel ibanLabel;
    private javax.swing.JCheckBox isDeletedCheckbox;
    private javax.swing.JLabel isDeletedLabel;
    private javax.swing.JCheckBox isFarmerCheckbox;
    private javax.swing.JLabel isFarmerLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField provinceInput;
    private javax.swing.JLabel provinceLabel;
    private javax.swing.JTextField tinInput;
    private javax.swing.JLabel tinLabel;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField zipcodeInput;
    private javax.swing.JLabel zipcodeLabel;
    // End of variables declaration//GEN-END:variables
}
