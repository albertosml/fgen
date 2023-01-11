package shared.presentation;

import customer.presentation.panels.ListCustomersPanel;
import customer.presentation.panels.RegisterCustomerPanel;
import customer.presentation.panels.ShowCustomerPanel;
import java.awt.Container;
import javax.swing.JPanel;
import shared.application.configuration.ApplicationConfiguration;
import shared.application.configuration.ConfigurationVariable;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;
import shared.presentation.panels.AboutPanel;
import subtotal.presentation.panels.ListSubtotalsPanel;
import subtotal.presentation.panels.RegisterSubtotalPanel;
import variable.presentation.panels.RegisterVariablePanel;

/**
 * Main application frame.
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Constructor.
     */
    public MainFrame() {
        initComponents();

        this.initializeMenu();
        this.redirectToAbout();
    }

    /**
     * Initialize all the menu items by setting the localized text for them.
     */
    private void initializeMenu() {
        String aboutName = Localization.getLocalization(LocalizationKey.ABOUT);
        this.aboutButton.setText(aboutName);

        String registerName = Localization.getLocalization(LocalizationKey.REGISTER);
        this.registerCustomer.setText(registerName);
        this.registerSubtotal.setText(registerName);
        this.registerVariable.setText(registerName);

        String customerName = Localization.getLocalization(LocalizationKey.CUSTOMER);
        this.customer.setText(customerName);

        String listName = Localization.getLocalization(LocalizationKey.LIST);
        this.listCustomers.setText(listName);
        this.listSubtotals.setText(listName);

        String subtotalName = Localization.getLocalization(LocalizationKey.SUBTOTAL);
        this.subtotal.setText(subtotalName);

        String variableName = Localization.getLocalization(LocalizationKey.VARIABLE);
        this.variable.setText(variableName);
    }

    /**
     * Set the main frame title.
     *
     * Keep in mind that the application name will be always added at the end of
     * the title separated by a hyphen.
     *
     * @param title The title to set.
     */
    @Override
    public void setTitle(String title) {
        String applicationName = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.NAME);
        String frameTitle = String.format("%s - %s", title, applicationName);
        super.setTitle(frameTitle);
    }

    /**
     * Open the given panel.
     *
     * @param panel The panel to open.
     */
    private void openPanel(JPanel panel) {
        Container layoutContainer = this.getContentPane();
        layoutContainer.removeAll();
        layoutContainer.add(panel);
        layoutContainer.repaint();
        layoutContainer.revalidate();
    }

    /**
     * Redirect to the given panel.
     *
     * @param panel The panel that we want to show.
     * @param panelTitle The title to set when showing the panel.
     */
    private void redirectTo(JPanel panel, String panelTitle) {
        this.setTitle(panelTitle);
        this.openPanel(panel);
    }

    /**
     * Redirect to the about panel.
     */
    private void redirectToAbout() {
        String aboutName = Localization.getLocalization(LocalizationKey.ABOUT);
        this.redirectTo(new AboutPanel(), aboutName);
        this.aboutButton.setText(aboutName);
    }

    /**
     * Redirect to the register customer panel.
     */
    private void redirectToRegisterCustomer() {
        String registerName = Localization.getLocalization(LocalizationKey.REGISTER);
        String customerName = Localization.getLocalization(LocalizationKey.CUSTOMER);
        String panelTitle = String.format("%s %s", registerName, customerName);
        this.redirectTo(new RegisterCustomerPanel(), panelTitle);
    }

    /**
     * Redirect to the show customer panel.
     *
     * @param customerCode The customer code.
     */
    public void redirectToShowCustomer(int customerCode) {
        String showName = Localization.getLocalization(LocalizationKey.SHOW);
        String customerName = Localization.getLocalization(LocalizationKey.CUSTOMER);
        String panelTitle = String.format("%s %s", showName, customerName);
        this.redirectTo(new ShowCustomerPanel(customerCode), panelTitle);
    }

    /**
     * Redirect to the list customers panel.
     */
    public void redirectToListCustomers() {
        String listName = Localization.getLocalization(LocalizationKey.LIST);
        String customersName = Localization.getLocalization(LocalizationKey.CUSTOMERS);
        String panelTitle = String.format("%s %s", listName, customersName);
        this.redirectTo(new ListCustomersPanel(), panelTitle);
    }

    /**
     * Redirect to the register subtotal panel.
     */
    private void redirectToRegisterSubtotal() {
        String registerName = Localization.getLocalization(LocalizationKey.REGISTER);
        String subtotalName = Localization.getLocalization(LocalizationKey.SUBTOTAL);
        String panelTitle = String.format("%s %s", registerName, subtotalName);
        this.redirectTo(new RegisterSubtotalPanel(), panelTitle);
    }

    /**
     * Redirect to the list subtotals panel.
     */
    public void redirectToListSubtotals() {
        String listName = Localization.getLocalization(LocalizationKey.LIST);
        String subtotalsName = Localization.getLocalization(LocalizationKey.SUBTOTALS);
        String panelTitle = String.format("%s %s", listName, subtotalsName);
        this.redirectTo(new ListSubtotalsPanel(), panelTitle);
    }

    /**
     * Redirect to the register variable panel.
     */
    public void redirectToRegisterVariable() {
        String registerName = Localization.getLocalization(LocalizationKey.REGISTER);
        String variableName = Localization.getLocalization(LocalizationKey.VARIABLE);
        String panelTitle = String.format("%s %s", registerName, variableName);
        this.redirectTo(new RegisterVariablePanel(), panelTitle);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar = new javax.swing.JMenuBar();
        customer = new javax.swing.JMenu();
        registerCustomer = new javax.swing.JMenuItem();
        listCustomers = new javax.swing.JMenuItem();
        subtotal = new javax.swing.JMenu();
        registerSubtotal = new javax.swing.JMenuItem();
        listSubtotals = new javax.swing.JMenuItem();
        variable = new javax.swing.JMenu();
        registerVariable = new javax.swing.JMenuItem();
        aboutButton = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        customer.setText("${CUSTOMER}");

        registerCustomer.setText("${REGISTER}");
        registerCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerCustomerActionPerformed(evt);
            }
        });
        customer.add(registerCustomer);

        listCustomers.setText("${LIST}");
        listCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listCustomersActionPerformed(evt);
            }
        });
        customer.add(listCustomers);

        menuBar.add(customer);

        subtotal.setText("${SUBTOTAL}");

        registerSubtotal.setText("${REGISTER}");
        registerSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerSubtotalActionPerformed(evt);
            }
        });
        subtotal.add(registerSubtotal);

        listSubtotals.setText("${LIST}");
        listSubtotals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listSubtotalsActionPerformed(evt);
            }
        });
        subtotal.add(listSubtotals);

        menuBar.add(subtotal);

        variable.setText("${VARIABLE}");

        registerVariable.setText("${REGISTER}");
        registerVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerVariableActionPerformed(evt);
            }
        });
        variable.add(registerVariable);

        menuBar.add(variable);

        aboutButton.setText("${ABOUT}");
        aboutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutButtonMouseClicked(evt);
            }
        });
        menuBar.add(aboutButton);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void aboutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutButtonMouseClicked
        this.redirectToAbout();
    }//GEN-LAST:event_aboutButtonMouseClicked

    private void registerCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerCustomerActionPerformed
        this.redirectToRegisterCustomer();
    }//GEN-LAST:event_registerCustomerActionPerformed

    private void listCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listCustomersActionPerformed
        this.redirectToListCustomers();
    }//GEN-LAST:event_listCustomersActionPerformed

    private void registerSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerSubtotalActionPerformed
        this.redirectToRegisterSubtotal();
    }//GEN-LAST:event_registerSubtotalActionPerformed

    private void listSubtotalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listSubtotalsActionPerformed
        this.redirectToListSubtotals();
    }//GEN-LAST:event_listSubtotalsActionPerformed

    private void registerVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerVariableActionPerformed
        this.redirectToRegisterVariable();
    }//GEN-LAST:event_registerVariableActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutButton;
    private javax.swing.JMenu customer;
    private javax.swing.JMenuItem listCustomers;
    private javax.swing.JMenuItem listSubtotals;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem registerCustomer;
    private javax.swing.JMenuItem registerSubtotal;
    private javax.swing.JMenuItem registerVariable;
    private javax.swing.JMenu subtotal;
    private javax.swing.JMenu variable;
    // End of variables declaration//GEN-END:variables
}
