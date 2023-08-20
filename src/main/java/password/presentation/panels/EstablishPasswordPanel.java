package password.presentation.panels;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import password.application.usecases.EstablishPassword;
import password.persistence.mongo.MongoPasswordRepository;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Panel which establishes the password.
 */
public class EstablishPasswordPanel extends javax.swing.JPanel {

    /**
     * Constructor.
     */
    public EstablishPasswordPanel() {
        initComponents();

        this.setLabelText(establishPasswordLabel, LocalizationKey.NEW_PASSWORD);
        this.setButtonText(establishButton, LocalizationKey.ESTABLISH);
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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passwordValue = new javax.swing.JPasswordField();
        establishPasswordLabel = new javax.swing.JLabel();
        establishButton = new javax.swing.JButton();

        establishPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        establishPasswordLabel.setText("${NEW_PASSWORD}:");

        establishButton.setText("${ESTABLISH}");
        establishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                establishButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(establishPasswordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(passwordValue))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(establishButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(25, 25, 25)))
                .addGap(115, 115, 115))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(establishPasswordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(establishButton)
                .addContainerGap(133, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void establishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_establishButtonActionPerformed
        char[] password = passwordValue.getPassword();

        try {
            MongoPasswordRepository passwordRepository = new MongoPasswordRepository();
            EstablishPassword establishPassword = new EstablishPassword(passwordRepository);
            boolean isPasswordEstablished = establishPassword.execute(password);

            LocalizationKey key = isPasswordEstablished ? LocalizationKey.PASSWORD_ESTABLISHED_MESSAGE : LocalizationKey.PASSWORD_NOT_ESTABLISHED_MESSAGE;
            String infoMessage = Localization.getLocalization(key);
            JOptionPane.showMessageDialog(this, infoMessage);

            if (isPasswordEstablished) {
                this.passwordValue.setText("");
            }
        } catch (NotDefinedDatabaseContextException ex) {
            String className = EstablishPasswordPanel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Password not established because the database has not been found", ex);
        }
    }//GEN-LAST:event_establishButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton establishButton;
    private javax.swing.JLabel establishPasswordLabel;
    private javax.swing.JPasswordField passwordValue;
    // End of variables declaration//GEN-END:variables
}
