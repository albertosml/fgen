package variable.application;

import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Entity attributes.
 */
public enum EntityAttribute {
    /**
     * Farmer customer code.
     */
    FARMER_CUSTOMER_CODE,
    /**
     * Farmer customer name.
     */
    FARMER_CUSTOMER_NAME,
    /**
     * Farmer customer TIN.
     */
    FARMER_CUSTOMER_TIN,
    /**
     * Farmer customer address.
     */
    FARMER_CUSTOMER_ADDRESS,
    /**
     * Farmer customer city.
     */
    FARMER_CUSTOMER_CITY,
    /**
     * Farmer customer province.
     */
    FARMER_CUSTOMER_PROVINCE,
    /**
     * Farmer customer ZIP code.
     */
    FARMER_CUSTOMER_ZIPCODE,
    /**
     * Farmer customer IBAN.
     */
    FARMER_CUSTOMER_IBAN,
    /**
     * Trader customer code.
     */
    TRADER_CUSTOMER_CODE,
    /**
     * Trader customer name.
     */
    TRADER_CUSTOMER_NAME,
    /**
     * Trader customer TIN.
     */
    TRADER_CUSTOMER_TIN,
    /**
     * Trader customer address.
     */
    TRADER_CUSTOMER_ADDRESS,
    /**
     * Trader customer city.
     */
    TRADER_CUSTOMER_CITY,
    /**
     * Trader customer province.
     */
    TRADER_CUSTOMER_PROVINCE,
    /**
     * Trader customer ZIP code.
     */
    TRADER_CUSTOMER_ZIPCODE,
    /**
     * Trader customer IBAN.
     */
    TRADER_CUSTOMER_IBAN,
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
    DELIVERY_NOTE_GENERATION_DATETIME,
    /**
     * Subtotal.
     */
    SUBTOTAL,
    /**
     * Delivery note total number of pallets.
     */
    DELIVERY_NOTE_TOTAL_PALLETS,
    /**
     * Delivery note total number of boxes.
     */
    DELIVERY_NOTE_TOTAL_BOXES,
    /**
     * Delivery note total weight per box.
     */
    DELIVERY_NOTE_TOTAL_WEIGHT_PER_BOX,
    /**
     * Period.
     */
    PERIOD,
    /**
     * Invoice items.
     */
    INVOICE_ITEMS,
    /**
     * Invoice subtotal.
     */
    INVOICE_SUBTOTAL,
    /**
     * Invoice subtotal.
     */
    INVOICE_TOTAL,
    /**
     * Invoice code. 
     */
    INVOICE_CODE,
    /**
     * Invoice generation date time.
     */
    INVOICE_GENERATION_DATETIME,
    /**
     * Invoice total weight.
     */
    INVOICE_TOTAL_WEIGHT;

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
