package variable.presentation.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import subtotal.application.Subtotal;
import subtotal.application.usecases.ListSubtotals;
import subtotal.persistence.mongo.MongoSubtotalRepository;
import variable.application.EntityAttribute;
import variable.application.VariableAttribute;
import variable.application.usecases.RegisterVariable;
import variable.application.utils.VariableValidationState;
import variable.persistence.mongo.MongoVariableRepository;

/**
 * Panel which shows the form to register a variable.
 */
public class RegisterVariablePanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public RegisterVariablePanel() {
        initComponents();
        initializeFormLabels();
        initializeFormSelectors();
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
        this.setLabelText(descriptionLabel, LocalizationKey.DESCRIPTION);
        this.setLabelText(attributeLabel, LocalizationKey.ATTRIBUTE);
        this.setLabelText(subtotalLabel, LocalizationKey.SUBTOTAL);
        this.setButtonText(registerButton, LocalizationKey.REGISTER);
    }

    /**
     * Obtain all the subtotals data by executing the use case.
     *
     * @return A list with all subtotals on the system.
     */
    private ArrayList<Subtotal> getSubtotals() {
        try {
            MongoSubtotalRepository subtotalRepository = new MongoSubtotalRepository();
            ListSubtotals listSubtotals = new ListSubtotals(subtotalRepository);
            return listSubtotals.execute();
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterVariablePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Subtotals cannot be shown because the database has not been found", ex);
        }

        return new ArrayList<>();
    }

    /**
     * Configure the visibility of the subtotal label and selector.
     *
     * @param isVisible Whether the subtotal field will be displayed or not.
     */
    private void configureSubtotalVisibility(boolean isVisible) {
        if (isVisible) {
            // List non-removed subtotals in the subtotal selector.
            Vector<Subtotal> nonRemovedSubtotals = new Vector<>();

            for (Subtotal subtotal : this.getSubtotals()) {
                if (!subtotal.isDeleted()) {
                    nonRemovedSubtotals.add(subtotal);
                }
            }

            this.subtotalSelector.setModel(new DefaultComboBoxModel(nonRemovedSubtotals));
        } else {
            this.subtotalSelector.removeAllItems();
        }

        // Update view.
        this.subtotalLabel.setVisible(isVisible);
        this.subtotalSelector.setVisible(isVisible);
    }

    /**
     * Initialize form selectors.
     */
    private void initializeFormSelectors() {
        // Attribute selector.
        this.attributeSelector.removeAllItems();

        for (EntityAttribute entityAttribute : EntityAttribute.values()) {
            this.attributeSelector.addItem(entityAttribute);
        }

        // Subtotal selector.
        this.configureSubtotalVisibility(false);
    }

    /**
     * Show the information message after the registration process.
     *
     * @param state The validation state for the variable.
     */
    private void showInfoMessage(VariableValidationState state) {
        Map<VariableValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(VariableValidationState.VALID, LocalizationKey.REGISTERED_VARIABLE_MESSAGE);
        localizationKeysByState.put(VariableValidationState.INVALID_NAME, LocalizationKey.INVALID_NAME_MESSAGE);
        localizationKeysByState.put(VariableValidationState.DUPLICATED, LocalizationKey.DUPLICATED_VARIABLE_MESSAGE);

        LocalizationKey key = localizationKeysByState.get(state);
        String infoMessage = Localization.getLocalization(key);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Clear all the form fields to register a subtotal.
     */
    private void clearForm() {
        this.nameInput.setText("");
        this.descriptionInput.setText("");
        this.attributeSelector.setSelectedIndex(0);
        this.configureSubtotalVisibility(false);
    }

    /**
     * Execute the register variable use case.
     *
     * @param newVariableAttributes The attributes for the variable to register.
     */
    private void registerSubtotal(Map<VariableAttribute, Object> newVariableAttributes) {
        try {
            MongoVariableRepository variableRepository = new MongoVariableRepository();
            RegisterVariable registerVariable = new RegisterVariable(variableRepository);
            VariableValidationState state = registerVariable.execute(newVariableAttributes);

            this.showInfoMessage(state);

            if (state.equals(VariableValidationState.VALID)) {
                this.clearForm();
            }
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterVariablePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Variable not registered because the database has not been found", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionInput = new javax.swing.JTextField();
        attributeLabel = new javax.swing.JLabel();
        attributeSelector = new javax.swing.JComboBox<>();
        subtotalLabel = new javax.swing.JLabel();
        subtotalSelector = new javax.swing.JComboBox<>();
        registerButton = new javax.swing.JButton();

        nameLabel.setText("${NAME}:");

        descriptionLabel.setText("${DESCRIPTION}:");

        attributeLabel.setText("${ATTRIBUTE}:");

        attributeSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        attributeSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attributeSelectorActionPerformed(evt);
            }
        });

        subtotalLabel.setText("${SUBTOTAL}:");

        subtotalSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(attributeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(subtotalLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(descriptionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(descriptionInput, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameInput, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(attributeSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subtotalSelector, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61))
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
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descriptionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attributeLabel)
                    .addComponent(attributeSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(subtotalLabel)
                    .addComponent(subtotalSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(registerButton)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<VariableAttribute, Object> newVariableAttributes = new HashMap<>();
        newVariableAttributes.put(VariableAttribute.NAME, nameInput.getText());
        newVariableAttributes.put(VariableAttribute.DESCRIPTION, descriptionInput.getText());

        EntityAttribute selectedAttribute = (EntityAttribute) attributeSelector.getSelectedItem();
        newVariableAttributes.put(VariableAttribute.ATTRIBUTE, selectedAttribute);

        if (selectedAttribute == EntityAttribute.INVOICE_SUBTOTAL) {
            newVariableAttributes.put(VariableAttribute.SUBTOTAL, subtotalSelector.getSelectedItem());
        }

        this.registerSubtotal(newVariableAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void attributeSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attributeSelectorActionPerformed
        // Chosen attribute will be null initially.
        EntityAttribute chosenAttribute = (EntityAttribute) this.attributeSelector.getSelectedItem();

        if (chosenAttribute != null) {
            // The subtotal label will be only shown when the entity attribute
            // is the invoice subtotal.
            boolean isInvoiceSubtotal = chosenAttribute.equals(EntityAttribute.INVOICE_SUBTOTAL);
            this.configureSubtotalVisibility(isInvoiceSubtotal);
        }
    }//GEN-LAST:event_attributeSelectorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attributeLabel;
    private javax.swing.JComboBox<Object> attributeSelector;
    private javax.swing.JTextField descriptionInput;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel subtotalLabel;
    private javax.swing.JComboBox<Object> subtotalSelector;
    // End of variables declaration//GEN-END:variables
}
