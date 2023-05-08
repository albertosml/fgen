package shared.presentation;

import javax.swing.JFrame;
import shared.application.configuration.ApplicationConfiguration;
import shared.application.configuration.ConfigurationVariable;
import shared.persistence.mongo.MongoDatabaseConnection;
import shared.presentation.dictionary.languages.SpanishDictionary;
import shared.presentation.exceptions.MissingSystemArgumentsContextException;
import shared.presentation.localization.Localization;

/**
 * Main application class, which will load the system UI.
 */
public class Main {

    /**
     * Runs the application.
     *
     * @param args System arguments.
     * @throws MissingSystemArgumentsContextException Thrown when an expected
     * argument is missing.
     */
    public static void main(String[] args) throws MissingSystemArgumentsContextException {
        if (args.length < 4) {
            throw new MissingSystemArgumentsContextException("The system is expecting four arguments, which are used to connect to the Mongo database: the database user name, the database password, the database host and the database name");
        }

        String dbUsername = args[0];
        String dbPassword = args[1];
        String dbHost = args[2];
        String dbName = args[3];

        // Set the localization for the Spanish language.
        Localization.load(new SpanishDictionary());

        // Set the database to connect.
        MongoDatabaseConnection.setInstance(dbUsername, dbPassword, dbHost, dbName);

        // Indicate application details.
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.VERSION, "0.46.0");
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.NAME, "FGEN");
        ApplicationConfiguration.addConfigurationVariable(ConfigurationVariable.PROJECT_URL, "https://github.com/albertosml/fgen");

        // Open the main frame on full screen mode.
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        mf.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
