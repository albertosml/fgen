package shared.presentation;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import password.application.usecases.CheckPassword;
import password.persistence.mongo.MongoPasswordRepository;
import shared.application.configuration.ApplicationConfiguration;
import shared.application.configuration.ConfigurationVariable;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.persistence.mongo.MongoDatabaseConnection;
import shared.presentation.dictionary.languages.SpanishDictionary;
import shared.presentation.exceptions.MissingSystemArgumentsContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Main application class, which will load the system UI.
 */
public class Main {

    /**
     * Check whether the password matches or not.
     *
     * @param password The introduced password.
     * @return Whether the password matches with the user or not.
     */
    private static boolean checkPassword(char[] password) {
        try {
            MongoPasswordRepository passwordRepository = new MongoPasswordRepository();
            CheckPassword checkPassword = new CheckPassword(passwordRepository);
            return checkPassword.execute(password);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = Main.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Password not checked because the database has not been found", ex);
            return false;
        }
    }

    /**
     * Read the password from the input.
     *
     * Note that it will be retrieved from the console (without showing the
     * password) or from the scanner object (which will obtain the password
     * value from the input).
     *
     * @return A char array representing the given password characters.
     */
    private static char[] readPassword() {
        Console console = System.console();

        String introducePasswordMessage = String.format("%s: ", Localization.getLocalization(LocalizationKey.INTRODUCE_PASSWORD_MESSAGE));

        char[] password;
        if (console == null) {
            // IDE case: Read password from input stream.
            System.out.println(introducePasswordMessage);

            Scanner scanner = new Scanner(System.in);
            String passwordText = scanner.next();
            password = passwordText.toCharArray();
        } else {
            password = console.readPassword(introducePasswordMessage);
        }

        return password;
    }

    /**
     * Do the authentication by checking if the introduced password is the right
     * one.
     *
     * The user will have the maximum number of retries specified on the
     * "NUM_PASSWORD_RETRIES" application configuration variable.
     *
     * @return Whether a successful authentication has been done or not.
     */
    private static boolean authenticate() {
        char[] password;
        boolean isPasswordCorrect;

        int numPasswordRetries = ApplicationConfiguration.getConfigurationVariable(ConfigurationVariable.NUM_PASSWORD_RETRIES);
        for (int i = 0; i < numPasswordRetries; i++) {
            password = Main.readPassword();
            isPasswordCorrect = Main.checkPassword(password);

            if (isPasswordCorrect) {
                System.out.println(Localization.getLocalization(LocalizationKey.CORRECT_PASSWORD_MESSAGE));
                return true;
            }

            System.out.println(Localization.getLocalization(LocalizationKey.INCORRECT_PASSWORD_MESSAGE));
        }

        System.out.println(Localization.getLocalization(LocalizationKey.NON_INTRODUCED_PASSWORD_MESSAGE));
        return false;
    }

    /**
     * Creates the template mapping to indicate the association between the
     * customer, which will be the key, and the template, which will be the
     * value.
     *
     * The format to specify the mapping will be:
     * "<customer1>:<templateCode1>;<customer2>:<templateCode2>"
     *
     * Note that ";" limits each mapping entry and the ":" limits the key and
     * the value.
     *
     * @param mapping The template mapping on the specified format.
     * @return A map containing the association between customers and templates.
     */
    private static Map<String, Integer> createTemplateMapping(String mapping) {
        Map<String, Integer> templatesMapping = new HashMap<>();

        String[] entries = mapping.split(";");
        for (String entry : entries) {
            String[] templateMapping = entry.split(":");
            if (templateMapping.length == 2) {
                String customer = templateMapping[0];
                Integer templateCode = Integer.valueOf(templateMapping[1]);
                templatesMapping.put(customer, templateCode);
            }
        }

        return templatesMapping;
    }

    /**
     * Runs the application.
     *
     * @param args System arguments.
     * @throws MissingSystemArgumentsContextException Thrown when an expected
     * argument is missing.
     */
    public static void main(String[] args) throws MissingSystemArgumentsContextException {
        if (args.length < 6) {
            throw new MissingSystemArgumentsContextException("The system is expecting four arguments, which are used to connect to the Mongo database: the database user name, the database password, the database host and the database name");
        }

        String dbUsername = args[0];
        String dbPassword = args[1];
        String dbHost = args[2];
        String dbName = args[3];
        String username = args[4];
        String salt = args[5];
        String invoiceItemsPerPage = args[6];
        String deliveryNoteTemplateByCustomer = args[7];
        String invoiceTemplateByCustomer = args[8];

        // Set the localization for the Spanish language.
        Localization.load(new SpanishDictionary());

        // Set the database to connect.
        MongoDatabaseConnection.setInstance(dbUsername, dbPassword, dbHost, dbName);

        // Indicate application details.
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.VERSION, "1.5.1");
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.NAME, "FGEN");
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.PROJECT_URL, "https://github.com/albertosml/fgen");
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.COMPANY_COMMISSION_PERCENTAGE, 4.0);
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.INDIVIDUAL_COMMISSION_PERCENTAGE, 2.0);
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.USERNAME, username);
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.PASSWORD_SALT, salt);
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.NUM_PASSWORD_RETRIES, 3);
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.INVOICE_ITEMS_PER_PAGE, Integer.valueOf(invoiceItemsPerPage));
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.DELIVERY_NOTE_TEMPLATE_BY_CUSTOMER, Main.createTemplateMapping(deliveryNoteTemplateByCustomer));
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.INVOICE_TEMPLATE_BY_CUSTOMER, Main.createTemplateMapping(invoiceTemplateByCustomer));

        // Authentication.
        boolean isAuthenticated = Main.authenticate();
        if (!isAuthenticated) {
            return;
        }

        // Open the main frame on full screen mode.
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        mf.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
