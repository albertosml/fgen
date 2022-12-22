package shared.presentation.dictionary.languages;

import shared.presentation.dictionary.Dictionary;
import shared.presentation.localization.LocalizationKey;

/**
 * Dictionary wit all the translations for the Spanish language.
 */
public class SpanishDictionary extends Dictionary {

    /**
     * Constructor.
     */
    public SpanishDictionary() {
        super.setTranslation(LocalizationKey.PROGRAM_NAME, "Nombre del programa");
        super.setTranslation(LocalizationKey.ABOUT, "Acerca de");
        super.setTranslation(LocalizationKey.VERSION, "Versión");
        super.setTranslation(LocalizationKey.PROJECT_DETAILS, "Más detalles sobre el proyecto");
    }
}
