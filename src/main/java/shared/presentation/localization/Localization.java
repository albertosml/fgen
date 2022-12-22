package shared.presentation.localization;

import shared.presentation.dictionary.Dictionary;

/**
 * Localize the user interface values, so the application can be translated to
 * different languages.
 */
public class Localization {

    /**
     * Associated dictionary with all the language translations.
     */
    private static Dictionary DICTIONARY;

    /**
     * Loads a given dictionary.
     *
     * @param dictionary The dictionary to associate with the localization.
     */
    public static void load(Dictionary dictionary) {
        DICTIONARY = dictionary;
    }

    /**
     * Obtain the localized value for the given key.
     *
     * @param key The localization key.
     * @return The corresponding value for the localization key on the
     * associated dictionary.
     */
    public static String getLocalization(LocalizationKey key) {
        return DICTIONARY.getTranslation(key);
    }

}
