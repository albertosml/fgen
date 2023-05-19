package product.presentation.panels;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import product.application.ProductAttribute;
import product.application.usecases.RegisterProduct;
import product.application.utils.ProductValidationState;
import product.persistence.mongo.MongoProductRepository;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which shows the form to register a product.
 */
public class RegisterProductPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public RegisterProductPanel() {
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
        this.setLabelText(codeLabel, LocalizationKey.CODE);
        this.setLabelText(nameLabel, LocalizationKey.NAME);
        this.setButtonText(registerButton, LocalizationKey.REGISTER);
    }

    /**
     * Show the information message after the registration process.
     *
     * @param state The product validation state.
     */
    private void showInfoMessage(ProductValidationState state) {
        Map<ProductValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(ProductValidationState.VALID, LocalizationKey.REGISTERED_PRODUCT_MESSAGE);
        localizationKeysByState.put(ProductValidationState.INVALID_CODE, LocalizationKey.INVALID_CODE_MESSAGE);
        localizationKeysByState.put(ProductValidationState.INVALID_NAME, LocalizationKey.INVALID_NAME_MESSAGE);
        localizationKeysByState.put(ProductValidationState.DUPLICATED, LocalizationKey.DUPLICATED_PRODUCT_MESSAGE);

        LocalizationKey key = localizationKeysByState.get(state);
        String infoMessage = Localization.getLocalization(key);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Clear all the form fields to register a product.
     */
    private void clearForm() {
        this.codeInput.setText("");
        this.nameInput.setText("");
    }

    /**
     * Execute the register product use case.
     *
     * @param newProductAttributes The attributes for the product to register.
     */
    private void registerProduct(Map<ProductAttribute, Object> newProductAttributes) {
        try {
            MongoProductRepository productRepository = new MongoProductRepository();
            RegisterProduct registerProduct = new RegisterProduct(productRepository);
            ProductValidationState state = registerProduct.execute(newProductAttributes);

            this.showInfoMessage(state);

            if (state == ProductValidationState.VALID) {
                this.clearForm();
            }
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterProductPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Product not registered because the database has not been found", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codeLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        registerButton = new javax.swing.JButton();
        codeInput = new javax.swing.JTextField();

        codeLabel.setText("${CODE}:");

        nameLabel.setText("${NAME}:");

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
                    .addComponent(codeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameInput)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(codeInput))
                .addGap(61, 61, 61))
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
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(registerButton)
                .addGap(33, 33, 33))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<ProductAttribute, Object> newProductAttributes = new HashMap<>();
        newProductAttributes.put(ProductAttribute.CODE, codeInput.getText());
        newProductAttributes.put(ProductAttribute.NAME, nameInput.getText());

        this.registerProduct(newProductAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codeInput;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
