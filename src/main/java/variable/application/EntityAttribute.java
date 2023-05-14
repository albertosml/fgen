package variable.application;

import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Entity attributes.
 */
public enum EntityAttribute {
    /**
     * Customer code.
     */
    CUSTOMER_CODE,
    /**
     * Customer name.
     */
    CUSTOMER_NAME,
    /**
     * Customer TIN.
     */
    CUSTOMER_TIN,
    /**
     * Customer address.
     */
    CUSTOMER_ADDRESS,
    /**
     * Customer city.
     */
    CUSTOMER_CITY,
    /**
     * Customer province.
     */
    CUSTOMER_PROVINCE,
    /**
     * Customer ZIP code.
     */
    CUSTOMER_ZIPCODE,
    /**
     * Customer IBAN.
     */
    CUSTOMER_IBAN,
    /**
     * Product code.
     */
    PRODUCT_CODE,
    /**
     * Product name.
     */
    PRODUCT_NAME,
    /**
     * Product price.
     */
    PRODUCT_PRICE,
    /**
     * Delivery note total weight.
     */
    DELIVERY_NOTE_TOTAL_WEIGHT,
    /**
     * Delivery note net weight.
     */
    DELIVERY_NOTE_NET_WEIGHT,
    /**
     * Delivery note items.
     */
    DELIVERY_NOTE_ITEMS,
    /**
     * Delivery note code.
     */
    DELIVERY_NOTE_CODE,
    /**
     * Delivery note date time.
     */
    DELIVERY_NOTE_GENERATION_DATETIME;

    /**
     * Gets the text to show for the corresponding enumeration value.
     *
     * @return The localized text for the enumeration value.
     */
    @Override
    public String toString() {
        LocalizationKey key = LocalizationKey.valueOf(super.toString());
        return Localization.getLocalization(key);
    }
}
