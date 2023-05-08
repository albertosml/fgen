package deliverynote.presentation.panels;

import container.presentation.panels.*;
import container.application.ContainerAttribute;
import container.application.usecases.RegisterContainer;
import container.application.utils.ContainerValidationState;
import container.persistence.mongo.MongoContainerRepository;
import java.awt.GridLayout;
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
            String className = GenerateDeliveryNotePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Container not registered because the database has not been found", ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        weightLabel = new javax.swing.JLabel();
        weightInput = new javax.swing.JSpinner();
        registerButton = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        nameLabel1 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        nameLabel2 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();

        nameLabel.setText("${PRODUCT}:");

        weightLabel.setText("${WEIGHT}:");

        weightInput.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));

        registerButton.setText("${REGISTER}");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        nameLabel1.setText("${CUSTOMER}:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        nameLabel2.setText("${TEMPLATE}:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        bookedPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout bookedPanelLayout = new javax.swing.GroupLayout(bookedPanel);
        bookedPanel.setLayout(bookedPanelLayout);
        bookedPanelLayout.setHorizontalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        bookedPanelLayout.setVerticalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(weightLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(weightInput)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(61, 61, 61))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightLabel)
                    .addComponent(weightInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(registerButton)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(bookedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        Map<ContainerAttribute, Object> newContainerAttributes = new HashMap<>();
        newContainerAttributes.put(ContainerAttribute.NAME, nameInput.getText());
        newContainerAttributes.put(ContainerAttribute.WEIGHT, weightInput.getValue());

        this.registerContainer(newContainerAttributes);
    }//GEN-LAST:event_registerButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameLabel1;
    private javax.swing.JLabel nameLabel2;
    private javax.swing.JButton registerButton;
    private javax.swing.JSpinner weightInput;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JPanel bookedPanel;
    // End of variables declaration//GEN-END:variables
}
