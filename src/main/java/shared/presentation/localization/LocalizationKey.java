package shared.presentation.localization;

/**
 * It will store all the localization keys, which can be used on any UI place.
 */
public enum LocalizationKey {
    /**
     * Name of the program.
     */
    PROGRAM_NAME,
    /**
     * Application version.
     */
    VERSION,
    /**
     * Project details.
     */
    PROJECT_DETAILS,
    /**
     * About application section.
     */
    ABOUT,
    /**
     * Register.
     */
    REGISTER,
    /**
     * Customer.
     */
    CUSTOMER,
    /**
     * Name.
     */
    NAME,
    /**
     * Customer tax identification number (TIN).
     */
    TIN,
    /**
     * Address.
     */
    ADDRESS,
    /**
     * City.
     */
    CITY,
    /**
     * Province.
     */
    PROVINCE,
    /**
     * ZIP code.
     */
    ZIPCODE,
    /**
     * IBAN.
     */
    IBAN,
    /**
     * Registered customer message.
     */
    REGISTERED_CUSTOMER_MESSAGE,
    /**
     * Invalid name message.
     */
    INVALID_NAME_MESSAGE,
    /**
     * Invalid TIN message.
     */
    INVALID_TIN_MESSAGE,
    /**
     * Invalid ZIP code message.
     */
    INVALID_ZIPCODE_MESSAGE,
    /**
     * Invalid IBAN message.
     */
    INVALID_IBAN_MESSAGE,
    /**
     * Duplicated customer message.
     */
    DUPLICATED_CUSTOMER_MESSAGE,
    /**
     * List.
     */
    LIST,
    /**
     * Customers
     */
    CUSTOMERS,
    /**
     * Code.
     */
    CODE,
    /**
     * Data.
     */
    DATA,
    /**
     * Remove.
     */
    REMOVE,
    /**
     * Restore.
     */
    RESTORE,
    /**
     * Show.
     */
    SHOW,
    /**
     * Update.
     */
    UPDATE,
    /**
     * Whether it is deleted or not.
     */
    IS_DELETED,
    /**
     * Updated customer message.
     */
    UPDATED_CUSTOMER_MESSAGE,
    /**
     * Not updated customer message.
     */
    NOT_UPDATED_CUSTOMER_MESSAGE,
    /**
     * Percentage.
     */
    PERCENTAGE,
    /**
     * Whether it is a discount or a tax.
     */
    IS_DISCOUNT,
    /**
     * Registered subtotal message.
     */
    REGISTERED_SUBTOTAL_MESSAGE,
    /**
     * Subtotal.
     */
    SUBTOTAL,
    /**
     * Subtotals.
     */
    SUBTOTALS,
    /**
     * Variable.
     */
    VARIABLE,
    /**
     * Description.
     */
    DESCRIPTION,
    /**
     * Attribute.
     */
    ATTRIBUTE,
    /**
     * Registered variable message.
     */
    REGISTERED_VARIABLE_MESSAGE,
    /**
     * Duplicated variable message.
     */
    DUPLICATED_VARIABLE_MESSAGE,
    /**
     * Sender customer code.
     */
    SENDER_CUSTOMER_CODE,
    /**
     * Sender customer name.
     */
    SENDER_CUSTOMER_NAME,
    /**
     * Sender customer TIN.
     */
    SENDER_CUSTOMER_TIN,
    /**
     * Sender customer address.
     */
    SENDER_CUSTOMER_ADDRESS,
    /**
     * Sender customer city.
     */
    SENDER_CUSTOMER_CITY,
    /**
     * Sender customer province.
     */
    SENDER_CUSTOMER_PROVINCE,
    /**
     * Sender customer ZIP code.
     */
    SENDER_CUSTOMER_ZIPCODE,
    /**
     * Sender customer IBAN.
     */
    SENDER_CUSTOMER_IBAN,
    /**
     * Recipient customer code.
     */
    RECIPIENT_CUSTOMER_CODE,
    /**
     * Recipient customer name.
     */
    RECIPIENT_CUSTOMER_NAME,
    /**
     * Recipient customer TIN.
     */
    RECIPIENT_CUSTOMER_TIN,
    /**
     * Recipient customer address.
     */
    RECIPIENT_CUSTOMER_ADDRESS,
    /**
     * Recipient customer city.
     */
    RECIPIENT_CUSTOMER_CITY,
    /**
     * Recipient customer province.
     */
    RECIPIENT_CUSTOMER_PROVINCE,
    /**
     * Recipient customer ZIP code.
     */
    RECIPIENT_CUSTOMER_ZIPCODE,
    /**
     * Recipient customer IBAN.
     */
    RECIPIENT_CUSTOMER_IBAN,
    /**
     * Invoice code.
     */
    INVOICE_CODE,
    /**
     * Invoice generation date time.
     */
    INVOICE_GENERATION_DATETIME,
    /**
     * Invoice items.
     */
    INVOICE_ITEMS,
    /**
     * Invoice items total.
     */
    INVOICE_ITEMS_TOTAL,
    /**
     * Invoice Subtotal.
     */
    INVOICE_SUBTOTAL,
    /**
     * Invoice total.
     */
    INVOICE_TOTAL,
    /**
     * Variables.
     */
    VARIABLES,
    /**
     * Message shown when the subtotal is associated to a variable.
     */
    SUBTOTAL_ASSOCIATED_WITH_VARIABLE_MESSAGE,
    /**
     * Message shown when the variable cannot be restored because the variable
     * is associated with a deleted subtotal.
     */
    VARIABLE_ASSOCIATED_WITH_DELETED_SUBTOTAL_MESSAGE,
    /**
     * Template field spreadsheet position.
     */
    POSITION,
    /**
     * Template field expression.
     */
    EXPRESSION,
    /**
     * Add action.
     */
    ADD,
    /**
     * Template.
     */
    TEMPLATE,
    /**
     * Fields.
     */
    FIELDS,
    /**
     * Message introduced when the introduced template field is invalid.
     */
    INVALID_TEMPLATE_FIELD_MESSAGE,
    /**
     * Field.
     */
    FIELD,
    /**
     * Message shown when we introduce an invalid template field position.
     */
    INVALID_POSITION_MESSAGE,
    /**
     * File.
     */
    FILE,
    /**
     * Choose action.
     */
    CHOOSE,
    /**
     * Registered template message.
     */
    REGISTERED_TEMPLATE_MESSAGE,
    /**
     * Invalid file message.
     */
    INVALID_FILE_MESSAGE,
    /**
     * Templates.
     */
    TEMPLATES,
    /**
     * Message shown when the template has been updated.
     */
    UPDATED_TEMPLATE_MESSAGE,
    /**
     * Message shown when the template has not been updated.
     */
    NOT_UPDATED_TEMPLATE_MESSAGE,
    /**
     * Items.
     */
    ITEMS,
    /**
     * Quantity.
     */
    QTY,
    /**
     * Price.
     */
    PRICE,
    /**
     * Message introduced when the introduced invoice item is invalid.
     */
    INVALID_INVOICE_ITEM_MESSAGE,
    /**
     * Item.
     */
    ITEM,
    /**
     * Invoice.
     */
    INVOICE,
    /**
     * Generate.
     */
    GENERATE,
    /**
     * Message shown when an invalid invoice item is introduced.
     */
    INVALID_INVOICE_ITEM_DESCRIPTION_MESSAGE,
    /**
     * Registered product message.
     */
    REGISTERED_PRODUCT_MESSAGE,
    /**
     * Invalid code message.
     */
    INVALID_CODE_MESSAGE,
    /**
     * Invalid price message.
     */
    INVALID_PRICE_MESSAGE,
    /**
     * Duplicated product message.
     */
    DUPLICATED_PRODUCT_MESSAGE,
    /**
     * Product.
     */
    PRODUCT,
    /**
     * Weight.
     */
    WEIGHT,
    /**
     * Register container message.
     */
    REGISTERED_CONTAINER_MESSAGE,
    /**
     * Invalid weight message.
     */
    INVALID_WEIGHT_MESSAGE,
    /**
     * Duplicated container message.
     */
    DUPLICATED_CONTAINER_MESSAGE,
    /**
     * Container.
     */
    CONTAINER,
    /**
     * Message shown when the introduced delivery note item is invalid.
     */
    INVALID_DELIVERY_NOTE_ITEM_MESSAGE,
    /**
     * Delivery note.
     */
    DELIVERY_NOTE,
    /**
     * Message shown when the introduced container is invalid.
     */
    INVALID_CONTAINER_MESSAGE,
    /**
     * Message shown when the introduced quantity is invalid.
     */
    INVALID_QTY_MESSAGE,
    /**
     * Generated delivery note message.
     */
    GENERATED_DELIVERY_NOTE_MESSAGE,
    /**
     * Invalid customer message.
     */
    INVALID_CUSTOMER_MESSAGE,
    /**
     * Invalid product message.
     */
    INVALID_PRODUCT_MESSAGE,
    /**
     * Invalid template message.
     */
    INVALID_TEMPLATE_MESSAGE,
    /**
     * Invalid delivery note items message.
     */
    INVALID_DELIVERY_NOTE_ITEMS_MESSAGE,
    /**
     * Not generated delivery note message.
     */
    NOT_GENERATED_DELIVERY_NOTE_MESSAGE,
}
