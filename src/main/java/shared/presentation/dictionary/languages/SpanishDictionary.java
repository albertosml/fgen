package shared.presentation.dictionary.languages;

import shared.presentation.dictionary.Dictionary;
import shared.presentation.localization.LocalizationKey;

/**
 * Dictionary with all the translations for the Spanish language.
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
        super.setTranslation(LocalizationKey.REGISTER, "Registrar");
        super.setTranslation(LocalizationKey.CUSTOMER, "Cliente");
        super.setTranslation(LocalizationKey.NAME, "Nombre");
        super.setTranslation(LocalizationKey.TIN, "DNI, NIE o CIF");
        super.setTranslation(LocalizationKey.ADDRESS, "Dirección (Opcional)");
        super.setTranslation(LocalizationKey.CITY, "Ciudad (Opcional)");
        super.setTranslation(LocalizationKey.PROVINCE, "Provincia (Opcional)");
        super.setTranslation(LocalizationKey.ZIPCODE, "Código postal (Opcional)");
        super.setTranslation(LocalizationKey.IBAN, "IBAN (Opcional)");
        super.setTranslation(LocalizationKey.REGISTERED_CUSTOMER_MESSAGE, "El cliente ha sido registrado con éxito");
        super.setTranslation(LocalizationKey.INVALID_NAME_MESSAGE, "Nombre inválido (No puede estar vacío)");
        super.setTranslation(LocalizationKey.INVALID_TIN_MESSAGE, "DNI, NIE o CIF introducido con un formato incorrecto. Debe añadirse sin guiones.");
        super.setTranslation(LocalizationKey.INVALID_ZIPCODE_MESSAGE, "Código postal inválido (solo debe contener números)");
        super.setTranslation(LocalizationKey.INVALID_IBAN_MESSAGE, "IBAN inválido");
        super.setTranslation(LocalizationKey.DUPLICATED_CUSTOMER_MESSAGE, "El cliente a registrar tiene un DNI, NIE o CIF de un cliente ya existente en el sistema.");
        super.setTranslation(LocalizationKey.LIST, "Listar");
        super.setTranslation(LocalizationKey.CUSTOMERS, "Clientes");
        super.setTranslation(LocalizationKey.CODE, "Código");
        super.setTranslation(LocalizationKey.DATA, "Datos");
        super.setTranslation(LocalizationKey.REMOVE, "Eliminar");
        super.setTranslation(LocalizationKey.RESTORE, "Restaurar");
        super.setTranslation(LocalizationKey.SHOW, "Mostrar");
        super.setTranslation(LocalizationKey.UPDATE, "Actualizar");
        super.setTranslation(LocalizationKey.IS_DELETED, "¿Está eliminado?");
        super.setTranslation(LocalizationKey.UPDATED_CUSTOMER_MESSAGE, "El cliente ha sido actualizado con éxito");
        super.setTranslation(LocalizationKey.NOT_UPDATED_CUSTOMER_MESSAGE, "Ha habido un problema al actualizar los datos del cliente");
        super.setTranslation(LocalizationKey.PERCENTAGE, "Porcentaje");
        super.setTranslation(LocalizationKey.IS_DISCOUNT, "¿Es descuento?");
        super.setTranslation(LocalizationKey.REGISTERED_SUBTOTAL_MESSAGE, "El subtotal ha sido registrado con éxito");
        super.setTranslation(LocalizationKey.SUBTOTAL, "Subtotal");
        super.setTranslation(LocalizationKey.SUBTOTALS, "Subtotales");
        super.setTranslation(LocalizationKey.VARIABLE, "Variable");
        super.setTranslation(LocalizationKey.DESCRIPTION, "Descripción");
        super.setTranslation(LocalizationKey.ATTRIBUTE, "Atributo");
        super.setTranslation(LocalizationKey.REGISTERED_VARIABLE_MESSAGE, "La variable ha sido registrada con éxito");
        super.setTranslation(LocalizationKey.DUPLICATED_VARIABLE_MESSAGE, "La variable a registrar tiene un nombre de una variable ya existente en el sistema.");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_CODE, "Código del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_NAME, "Nombre del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_TIN, "DNI, NIE o CIF del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_ADDRESS, "Dirección del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_CITY, "Ciudad del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_PROVINCE, "Provincia del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_ZIPCODE, "Código postal del cliente emisor");
        super.setTranslation(LocalizationKey.SENDER_CUSTOMER_IBAN, "IBAN del cliente emisor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_CODE, "Código del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_NAME, "Nombre del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_TIN, "DNI, NIE o CIF del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_ADDRESS, "Dirección del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_CITY, "Ciudad del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_PROVINCE, "Provincia del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_ZIPCODE, "Código postal del cliente receptor");
        super.setTranslation(LocalizationKey.RECIPIENT_CUSTOMER_IBAN, "IBAN del cliente receptor");
        super.setTranslation(LocalizationKey.INVOICE_CODE, "Código de la factura");
        super.setTranslation(LocalizationKey.INVOICE_GENERATION_DATETIME, "Hora de generación de la factura");
        super.setTranslation(LocalizationKey.INVOICE_ITEMS, "Elementos de la factura");
        super.setTranslation(LocalizationKey.INVOICE_ITEMS_TOTAL, "Total de los elementos de la factura");
        super.setTranslation(LocalizationKey.INVOICE_SUBTOTAL, "Subtotal de la factura");
        super.setTranslation(LocalizationKey.INVOICE_TOTAL, "Total de la factura");
        super.setTranslation(LocalizationKey.VARIABLES, "Variables");
        super.setTranslation(LocalizationKey.SUBTOTAL_ASSOCIATED_WITH_VARIABLE_MESSAGE, "El subtotal no se puede eliminar porque está asociado a una variable");
        super.setTranslation(LocalizationKey.VARIABLE_ASSOCIATED_WITH_DELETED_SUBTOTAL_MESSAGE, "La variable no puede ser restaurada porque está asociada a una subtotal eliminado");
        super.setTranslation(LocalizationKey.ADD, "Añadir");
        super.setTranslation(LocalizationKey.TEMPLATE, "Plantilla");
        super.setTranslation(LocalizationKey.POSITION, "Posición");
        super.setTranslation(LocalizationKey.EXPRESSION, "Expresión");
        super.setTranslation(LocalizationKey.INVALID_TEMPLATE_FIELD_MESSAGE, "Campo no añadido porque es inválido");
        super.setTranslation(LocalizationKey.FIELDS, "Campos");
        super.setTranslation(LocalizationKey.FIELD, "Campo");
        super.setTranslation(LocalizationKey.INVALID_POSITION_MESSAGE, "La posición del campo de la plantilla introducida no es válida");
        super.setTranslation(LocalizationKey.FILE, "Archivo");
        super.setTranslation(LocalizationKey.CHOOSE, "Elegir");
        super.setTranslation(LocalizationKey.REGISTERED_TEMPLATE_MESSAGE, "La plantilla ha sido registrada con éxito");
        super.setTranslation(LocalizationKey.INVALID_FILE_MESSAGE, "Archivo inválido");
        super.setTranslation(LocalizationKey.TEMPLATES, "Plantillas");
        super.setTranslation(LocalizationKey.UPDATED_TEMPLATE_MESSAGE, "La plantilla ha sido actualizada con éxito");
        super.setTranslation(LocalizationKey.NOT_UPDATED_TEMPLATE_MESSAGE, "La plantilla no ha podido ser actualizada");
    }

}
