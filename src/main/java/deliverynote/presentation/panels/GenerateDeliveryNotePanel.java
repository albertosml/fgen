package deliverynote.presentation.panels;

import java.awt.GridLayout;
import javax.swing.JLabel;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import weighing.presentation.panels.WeighingsPanel;

/**
 * Panel which shows a form to generate the delivery note.
 */
public class GenerateDeliveryNotePanel extends javax.swing.JPanel {

    /**
     * Weighings panel.
     */
    private WeighingsPanel weighingsPanel;

    /**
     * Constructor.
     */
    public GenerateDeliveryNotePanel() {
        initComponents();
        initLabels();

        this.weighingsPanel = new WeighingsPanel();
        this.bookedPanel.setLayout(new GridLayout());
        this.bookedPanel.add(weighingsPanel);
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
     * Initialize the labels.
     */
    private void initLabels() {
        this.setLabelText(numPalletsLabel, LocalizationKey.NUM_PALLETS);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bookedPanel = new javax.swing.JPanel();
        numPalletsLabel = new javax.swing.JLabel();
        numPalletsInput = new javax.swing.JSpinner();

        bookedPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout bookedPanelLayout = new javax.swing.GroupLayout(bookedPanel);
        bookedPanel.setLayout(bookedPanelLayout);
        bookedPanelLayout.setHorizontalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        bookedPanelLayout.setVerticalGroup(
            bookedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        numPalletsLabel.setText("${NUM_PALLETS}:");

        numPalletsInput.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                numPalletsInputStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(numPalletsLabel)
                        .addGap(18, 18, 18)
                        .addComponent(numPalletsInput, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numPalletsLabel)
                    .addComponent(numPalletsInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(bookedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void numPalletsInputStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_numPalletsInputStateChanged
        this.weighingsPanel.setTotalWeighings((int) this.numPalletsInput.getValue());
    }//GEN-LAST:event_numPalletsInputStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bookedPanel;
    private javax.swing.JSpinner numPalletsInput;
    private javax.swing.JLabel numPalletsLabel;
    // End of variables declaration//GEN-END:variables
}
