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
    }

}
