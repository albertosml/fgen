package subtotal.presentation.panels;

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
import subtotal.application.SubtotalAttribute;
import subtotal.application.usecases.RegisterSubtotal;
import subtotal.persistence.mongo.MongoSubtotalRepository;

/**
 * Panel which shows the form to register a subtotal.
 */
public class RegisterSubtotalPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public RegisterSubtotalPanel() {
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
        this.setLabelText(percentageLabel, LocalizationKey.PERCENTAGE);
        this.setLabelText(isDiscountLabel, LocalizationKey.IS_DISCOUNT);
        this.setButtonText(registerButton, LocalizationKey.REGISTER);
    }

    /**
     * Show the information message after the registration process.
     */
    private void showInfoMessage() {
        String infoMessage = Localization.getLocalization(LocalizationKey.REGISTERED_SUBTOTAL_MESSAGE);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Clear all the form fields to register a subtotal.
     */
    private void clearForm() {
        this.nameInput.setText("");
        this.percentageInput.setValue(0);
        this.isDiscountCheckbox.setSelected(false);
    }

    /**
     * Execute the register subtotal use case.
     *
     * @param newSubtotalAttributes The attributes for the subtotal to register.
     */
    private void registerSubtotal(Map<SubtotalAttribute, Object> newSubtotalAttributes) {
        try {
            MongoSubtotalRepository subtotalRepository = new MongoSubtotalRepository();
            RegisterSubtotal registerSubtotal = new RegisterSubtotal(subtotalRepository);
            registerSubtotal.execute(newSubtotalAttributes);

            this.showInfoMessage();
            this.clearForm();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterSubtotalPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Subtotal not registered because the database has not been found", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        percentageLabel = new javax.swing.JLabel();
        isDiscountLabel = new javax.swing.JLabel();
        registerButton = new javax.swing.JButton();
        percentageInput = new javax.swing.JSpinner();
        isDiscountCheckbox = new javax.swing.JCheckBox();

        nameLabel.setText("${NAME}:");

        percentageLabel.setText("${PERCENTAGE}:");

        isDiscountLabel.setText("${IS_DISCOUNT}:");

        registerButton.setText("${REGISTER}");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        percentageInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(isDiscountLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(percentageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameInput)
                            .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(percentageInput))
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(isDiscountCheckbox)
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
                    .addComponent(percentageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(percentageInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isDiscountLabel)
                    .addComponent(isDiscountCheckbox))
                .addGap(28, 28, 28)
                .addComponent(registerButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<SubtotalAttribute, Object> newSubtotalAttributes = new HashMap<>();
        newSubtotalAttributes.put(SubtotalAttribute.NAME, nameInput.getText());
        newSubtotalAttributes.put(SubtotalAttribute.PERCENTAGE, percentageInput.getValue());
        newSubtotalAttributes.put(SubtotalAttribute.ISDISCOUNT, isDiscountCheckbox.isSelected());

        this.registerSubtotal(newSubtotalAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox isDiscountCheckbox;
    private javax.swing.JLabel isDiscountLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JSpinner percentageInput;
    private javax.swing.JLabel percentageLabel;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
