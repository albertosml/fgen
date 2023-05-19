package container.presentation.panels;

import container.application.ContainerAttribute;
import container.application.usecases.RegisterContainer;
import container.application.utils.ContainerValidationState;
import container.persistence.mongo.MongoContainerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which shows the form to register a container.
 */
public class RegisterContainerPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public RegisterContainerPanel() {
        initComponents();
        initializeFormLabels();
        initializeInputs();
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
        this.setLabelText(weightLabel, LocalizationKey.WEIGHT);
        this.setLabelText(isBoxLabel, LocalizationKey.IS_BOX);
        this.setButtonText(registerButton, LocalizationKey.REGISTER);
    }

    /**
     * Initialize inputs.
     */
    private void initializeInputs() {
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
     * Clear all the form fields to register a container.
     */
    private void clearForm() {
        this.nameInput.setText("");
        this.weightInput.setValue(0);
    }

    /**
     * Execute the register container use case.
     *
     * @param newContainerAttributes The attributes for the container to
     * register.
     */
    private void registerContainer(Map<ContainerAttribute, Object> newContainerAttributes) {
        try {
            MongoContainerRepository containerRepository = new MongoContainerRepository();
            RegisterContainer registerContainer = new RegisterContainer(containerRepository);
            ContainerValidationState state = registerContainer.execute(newContainerAttributes);

            this.showInfoMessage(state);

            if (state == ContainerValidationState.VALID) {
                this.clearForm();
            }
        } catch (NotDefinedDatabaseContextException ex) {
            String className = RegisterContainerPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Container not registered because the database has not been found", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        isBoxLabel = new javax.swing.JLabel();
        weightInput = new javax.swing.JSpinner();
        registerButton = new javax.swing.JButton();
        weightLabel = new javax.swing.JLabel();
        isBoxInput = new javax.swing.JCheckBox();

        nameLabel.setText("${NAME}:");

        isBoxLabel.setText("${IS_BOX}:");

        weightInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        registerButton.setText("${REGISTER}");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        weightLabel.setText("${WEIGHT}:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(isBoxLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(weightLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameInput)
                            .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(weightInput))
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(isBoxInput)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightLabel))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isBoxLabel)
                    .addComponent(isBoxInput))
                .addGap(18, 18, 18)
                .addComponent(registerButton)
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<ContainerAttribute, Object> newContainerAttributes = new HashMap<>();
        newContainerAttributes.put(ContainerAttribute.NAME, nameInput.getText());
        newContainerAttributes.put(ContainerAttribute.WEIGHT, weightInput.getValue());
        newContainerAttributes.put(ContainerAttribute.ISBOX, isBoxInput.isSelected());

        this.registerContainer(newContainerAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox isBoxInput;
    private javax.swing.JLabel isBoxLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JSpinner weightInput;
    private javax.swing.JLabel weightLabel;
    // End of variables declaration//GEN-END:variables
}
