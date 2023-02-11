package template.presentation.panels;

import java.awt.GridLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import template.application.Template;
import template.application.TemplateAttribute;
import template.application.usecases.ShowTemplate;
import template.application.usecases.UpdateTemplate;
import template.application.utils.TemplateValidationState;
import template.persistence.mongo.MongoTemplateRepository;

/**
 * Panel which shows the template details.
 */
public class ShowTemplatePanel extends javax.swing.JPanel {

    /**
     * Chosen file.
     */
    private File chosenFile;

    /**
     * Template fields panel.
     */
    private TemplateFieldsPanel templateFieldsPanel;

    /**
     * Constructor.
     *
     * @param code The code of the template to show the data.
     */
    public ShowTemplatePanel(int code) {
        initComponents();
        initializeLabels();
        initializeInputs(code);
    }

    /**
     * Initialize the form labels.
     */
    private void initializeLabels() {
        String codeLabelText = Localization.getLocalization(LocalizationKey.CODE);
        String codeLabelFormattedText = String.format("%s:", codeLabelText);
        this.codeLabel.setText(codeLabelFormattedText);

        String nameLabelText = Localization.getLocalization(LocalizationKey.NAME);
        String nameLabelFormattedText = String.format("%s:", nameLabelText);
        this.nameLabel.setText(nameLabelFormattedText);

        String fileLabelText = Localization.getLocalization(LocalizationKey.FILE);
        String fileLabelFormattedText = String.format("%s:", fileLabelText);
        this.fileLabel.setText(fileLabelFormattedText);

        String isDeletedLabelText = Localization.getLocalization(LocalizationKey.IS_DELETED);
        String isDeletedFormattedText = String.format("%s:", isDeletedLabelText);
        this.isDeletedLabel.setText(isDeletedFormattedText);

        String chooseText = Localization.getLocalization(LocalizationKey.CHOOSE);
        this.chooseFileButton.setText(chooseText);

        String updateText = Localization.getLocalization(LocalizationKey.UPDATE);
        this.updateButton.setText(updateText);
    }

    /**
     * Initialize the form inputs.
     */
    private void initializeInputs(int templateCode) {
        Template template = this.showTemplate(templateCode);

        Map<String, String> templateFields = new HashMap<>();
        boolean isDeleted = false;

        if (template != null) {
            if (template.isDeleted()) {
                isDeleted = true;
                this.nameInput.setEditable(false);
                this.chooseFileButton.setEnabled(false);
                this.updateButton.setEnabled(false);
            }

            this.codeInput.setText(Integer.toString(templateCode));
            this.nameInput.setText(template.getName());
            this.isDeletedCheckbox.setSelected(template.isDeleted());

            // File extension.
            File file = template.getFile();
            String fileName = file.getName();
            int lastPointIndex = fileName.lastIndexOf(".");
            String fileExtension = fileName.substring(lastPointIndex);

            String fileLabelText = String.format("Template%d%s", templateCode, fileExtension);
            this.fileInput.setText(fileLabelText);
            this.chosenFile = file;

            // Template fields.
            templateFields = template.getFields();
        }

        this.templateFieldsPanel = new TemplateFieldsPanel(templateFields, isDeleted);
        this.bookedPanel.setLayout(new GridLayout());
        this.bookedPanel.add(templateFieldsPanel);
    }

    /**
     * Execute the show template use case.
     *
     * @param code The code of the template to show.
     */
    private Template showTemplate(int code) {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            ShowTemplate showTemplate = new ShowTemplate(templateRepository);
            return showTemplate.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ShowTemplatePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Template not shown because the database has not been found", ex);
        }

        return null;
    }

    /**
     * Show the information message after the update.
     *
     * @param state The validation state for the template.
     */
    private void showInfoMessage(TemplateValidationState state) {
        Map<TemplateValidationState, LocalizationKey> localizationKeysByState = new HashMap<>();
        localizationKeysByState.put(TemplateValidationState.VALID, LocalizationKey.UPDATED_TEMPLATE_MESSAGE);
        localizationKeysByState.put(TemplateValidationState.INVALID_NAME, LocalizationKey.INVALID_NAME_MESSAGE);
        localizationKeysByState.put(TemplateValidationState.INVALID_FILE, LocalizationKey.INVALID_FILE_MESSAGE);
        localizationKeysByState.put(TemplateValidationState.INVALID, LocalizationKey.NOT_UPDATED_TEMPLATE_MESSAGE);

        LocalizationKey key = localizationKeysByState.get(state);
        String infoMessage = Localization.getLocalization(key);
        JOptionPane.showMessageDialog(this, infoMessage);
    }

    /**
     * Execute the update template use case.
     *
     * @param attributes Map containing each template attribute.
     */
    private void updateTemplate(Map<TemplateAttribute, Object> attributes) {
        try {
            MongoTemplateRepository templateRepository = new MongoTemplateRepository();
            UpdateTemplate updateTemplate = new UpdateTemplate(templateRepository);
            TemplateValidationState state = updateTemplate.execute(attributes);

            this.showInfoMessage(state);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ShowTemplatePanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Template not updated because the database has not been found", ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bookedPanel = new javax.swing.JPanel();
        codeLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        fileLabel = new javax.swing.JLabel();
        codeInput = new javax.swing.JLabel();
        isDeletedLabel = new javax.swing.JLabel();
        isDeletedCheckbox = new javax.swing.JCheckBox();
        nameLabel = new javax.swing.JLabel();
        fileInput = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        chooseFileButton = new javax.swing.JButton();

        bookedPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout bookedPanelLayout = new javax.swing.GroupLayout(bookedPanel);
        bookedPanel.setLayout(bookedPanelLayout);
        bookedPanelLayout.setHorizontalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bookedPanelLayout.setVerticalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 188, Short.MAX_VALUE)
        );

        codeLabel.setText("${CODE}:");

        fileLabel.setText("${FILE}:");

        codeInput.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        isDeletedLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        isDeletedLabel.setText("${IS_DELETED}:");

        isDeletedCheckbox.setEnabled(false);

        nameLabel.setText("${NAME}:");

        fileInput.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        updateButton.setText("${UPDATE}");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        chooseFileButton.setText("${CHOOSE}");
        chooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fileLabel)
                            .addComponent(nameLabel)
                            .addComponent(codeLabel))
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(isDeletedLabel)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(isDeletedCheckbox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codeInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameInput, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileInput, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chooseFileButton)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(9, 9, 9))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codeInput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeLabel))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addGap(18, 18, 18)
                        .addComponent(fileLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fileInput, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooseFileButton))))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(isDeletedLabel)
                    .addComponent(isDeletedCheckbox))
                .addGap(18, 18, 18)
                .addComponent(bookedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateButton)
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        Map<TemplateAttribute, Object> templateAttributes = new HashMap<>();
        templateAttributes.put(TemplateAttribute.CODE, Integer.valueOf(this.codeInput.getText()));
        templateAttributes.put(TemplateAttribute.NAME, this.nameInput.getText());
        templateAttributes.put(TemplateAttribute.FILE, this.chosenFile);
        templateAttributes.put(TemplateAttribute.FIELDS, this.templateFieldsPanel.getFields());
        templateAttributes.put(TemplateAttribute.ISDELETED, this.isDeletedCheckbox.isSelected());

        this.updateTemplate(templateAttributes);
    }//GEN-LAST:event_updateButtonActionPerformed

    private void chooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseFileButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Supported formats (XLSX, XLS, XLSB, ODS, CSV).
        FileNameExtensionFilter spreadsheetFilesFilter = new FileNameExtensionFilter("Spreadsheet files", "xlsx", "xls", "xlsb", "ods", "csv");
        fileChooser.setFileFilter(spreadsheetFilesFilter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.fileInput.setText(file.getAbsolutePath());
            this.fileInput.setToolTipText(file.getAbsolutePath());
            this.chosenFile = file;
        }
    }//GEN-LAST:event_chooseFileButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bookedPanel;
    private javax.swing.JButton chooseFileButton;
    private javax.swing.JLabel codeInput;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JLabel fileInput;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JCheckBox isDeletedCheckbox;
    private javax.swing.JLabel isDeletedLabel;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
