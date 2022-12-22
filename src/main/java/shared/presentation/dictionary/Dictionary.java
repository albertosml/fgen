package shared.presentation.dictionary;

import java.util.HashMap;
import shared.presentation.localization.LocalizationKey;

/**
 * Defines the translations for all localizations.
 */
public abstract class Dictionary {

    /**
     * Store all the translations for a concrete language.
     */
    private HashMap<LocalizationKey, String> translations;

    /**
     * Constructor.
     */
    protected Dictionary() {
        this.translations = new HashMap<>();
    }

    /**
     * Set the translation for a given localization key.
     *
     * @param key Localization key.
     * @param value The value to set for the localization value.
     */
    protected void setTranslation(LocalizationKey key, String value) {
        this.translations.put(key, value);
    }

    /**
     * Get the translation for a concrete localization key.
     *
     * @param key Localization key.
     * @return The value associated to the localization key for the dictionary.
     */
    public String getTranslation(LocalizationKey key) {
        String defaultValue = String.format("${%s}", key.toString());
        return this.translations.getOrDefault(key, defaultValue);
    }

}
