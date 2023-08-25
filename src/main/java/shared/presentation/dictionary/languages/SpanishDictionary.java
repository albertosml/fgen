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
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_CODE, "Código del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_NAME, "Nombre del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_TIN, "DNI, NIE o CIF del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_ADDRESS, "Dirección del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_CITY, "Ciudad del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_PROVINCE, "Provincia del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_ZIPCODE, "Código postal del agricultor");
        super.setTranslation(LocalizationKey.FARMER_CUSTOMER_IBAN, "IBAN del agricultor");
        super.setTranslation(LocalizationKey.PRODUCT_CODE, "Código del producto");
        super.setTranslation(LocalizationKey.PRODUCT_NAME, "Nombre del producto");
        super.setTranslation(LocalizationKey.PRODUCT_PRICE, "Precio del producto");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_TOTAL_WEIGHT, "Peso total del albarán");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_NET_WEIGHT, "Peso neto del albarán");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_ITEMS, "Elementos del albarán");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_CODE, "Código de albarán");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_GENERATION_DATETIME, "Fecha de generación del albarán");
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
        super.setTranslation(LocalizationKey.ITEMS, "Unidades");
        super.setTranslation(LocalizationKey.QTY, "Cantidad (en Kilos)");
        super.setTranslation(LocalizationKey.PRICE, "Precio (en Euros)");
        super.setTranslation(LocalizationKey.INVALID_INVOICE_ITEM_MESSAGE, "Unidad no añadida porque es inválida");
        super.setTranslation(LocalizationKey.ITEM, "Unidad");
        super.setTranslation(LocalizationKey.GENERATE, "Generar");
        super.setTranslation(LocalizationKey.INVOICE, "Factura");
        super.setTranslation(LocalizationKey.INVALID_INVOICE_ITEM_DESCRIPTION_MESSAGE, "La descripción introducida para la unidad de la factura no es válida");
        super.setTranslation(LocalizationKey.INVALID_CODE_MESSAGE, "Código inválido (Debe ser numérico)");
        super.setTranslation(LocalizationKey.INVALID_PRICE_MESSAGE, "Precio inválido");
        super.setTranslation(LocalizationKey.REGISTERED_PRODUCT_MESSAGE, "El producto ha sido registrado con éxito");
        super.setTranslation(LocalizationKey.DUPLICATED_PRODUCT_MESSAGE, "El producto a registrar tiene un código ya existente en el sistema.");                
        super.setTranslation(LocalizationKey.PRODUCT, "Producto");
        super.setTranslation(LocalizationKey.WEIGHT, "Peso (en kilos)");
        super.setTranslation(LocalizationKey.INVALID_WEIGHT_MESSAGE, "Peso inválido");
        super.setTranslation(LocalizationKey.REGISTERED_CONTAINER_MESSAGE, "El contenedor (palé o caja) ha sido registrado con éxito");
        super.setTranslation(LocalizationKey.DUPLICATED_CONTAINER_MESSAGE, "El contenedor (palé o caja) a registrar tiene un código ya existente en el sistema.");
        super.setTranslation(LocalizationKey.CONTAINER, "Palé/Caja");
        super.setTranslation(LocalizationKey.INVALID_DELIVERY_NOTE_ITEM_MESSAGE, "Elemento no añadido porque no es válido");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE, "Albarán");
        super.setTranslation(LocalizationKey.INVALID_CONTAINER_MESSAGE, "El contenedor introducido es inválido");
        super.setTranslation(LocalizationKey.INVALID_QTY_MESSAGE, "La cantidad introducida es inválida");
        super.setTranslation(LocalizationKey.GENERATED_DELIVERY_NOTE_MESSAGE, "El albarán se ha generado con éxito");
        super.setTranslation(LocalizationKey.INVALID_FARMER_CUSTOMER_MESSAGE, "Agricultor no válido");
        super.setTranslation(LocalizationKey.INVALID_SUPPLIER_CUSTOMER_MESSAGE, "Proveedor no válido");
        super.setTranslation(LocalizationKey.INVALID_PRODUCT_MESSAGE, "Producto no válido");
        super.setTranslation(LocalizationKey.INVALID_TEMPLATE_MESSAGE, "Plantilla no válida");
        super.setTranslation(LocalizationKey.INVALID_WEIGHT_MESSAGE, "Peso no válido");
        super.setTranslation(LocalizationKey.INVALID_WEIGHINGS_MESSAGE, "Pesadas no válidas");
        super.setTranslation(LocalizationKey.NOT_GENERATED_DELIVERY_NOTE_MESSAGE, "El albarán no se ha podido generar");
        super.setTranslation(LocalizationKey.IS_BOX, "¿Es caja?");
        super.setTranslation(LocalizationKey.WEIGHINGS, "Pesadas");
        super.setTranslation(LocalizationKey.BOX, "Caja");
        super.setTranslation(LocalizationKey.BOXES_QTY, "Cantidad de cajas");
        super.setTranslation(LocalizationKey.GROSS_WEIGHT, "Peso bruto (en kilos)");
        super.setTranslation(LocalizationKey.INVALID_BOX_MESSAGE, "Caja inválida");
        super.setTranslation(LocalizationKey.NUM_PALLETS, "Número de pallets");
        super.setTranslation(LocalizationKey.INVALID_PALLET_MESSAGE, "Palé inválido");
        super.setTranslation(LocalizationKey.INVALID_NUM_PALLETS_MESSAGE, "Número de palés inválido");
        super.setTranslation(LocalizationKey.PALLET, "Pallet");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_TOTAL_PALLETS, "Número total de palés");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_TOTAL_BOXES, "Número total de cajas");
        super.setTranslation(LocalizationKey.DELIVERY_NOTE_TOTAL_WEIGHT_PER_BOX, "Peso total por caja");
        super.setTranslation(LocalizationKey.IS_SUPPLIER, "¿Es proveedor?");
        super.setTranslation(LocalizationKey.DATE, "Fecha");
        super.setTranslation(LocalizationKey.DOWNLOAD, "Descargar");
        super.setTranslation(LocalizationKey.PRINT, "Imprimir");
        super.setTranslation(LocalizationKey.DOWNLOADED_FILE_MESSAGE, "El archivo se ha descargado en");
        super.setTranslation(LocalizationKey.PRINTED_FILE_MESSAGE, "Archivo imprimido");
        super.setTranslation(LocalizationKey.PRINT_ERROR_MESSAGE, "Error al imprimir");
        super.setTranslation(LocalizationKey.DOWNLOAD_ERROR_MESSAGE, "Error al descargar");
        super.setTranslation(LocalizationKey.NET_WEIGHT, "Peso neto (en kilos)");
        super.setTranslation(LocalizationKey.TOTAL_NET_WEIGHT, "Peso neto total (en kilos)");
        super.setTranslation(LocalizationKey.TOTAL_NUM_BOXES, "Número total de cajas");
        super.setTranslation(LocalizationKey.START_DATE, "Fecha de inicio");
        super.setTranslation(LocalizationKey.END_DATE, "Fecha de fin");
        super.setTranslation(LocalizationKey.REMOVED_DELIVERY_NOTE_MESSAGE, "Albarán eliminado con éxito");
        super.setTranslation(LocalizationKey.REMOVED_DELIVERY_NOTE_ERROR_MESSAGE, "Error al eliminar el albarán");
        super.setTranslation(LocalizationKey.FARMER, "Agricultor");
        super.setTranslation(LocalizationKey.SUPPLIER, "Proveedor");
        super.setTranslation(LocalizationKey.BILL, "Facturar");
        super.setTranslation(LocalizationKey.PERIOD, "Periodo");
        super.setTranslation(LocalizationKey.INVOICE_ITEMS, "Elementos de la factura");
        super.setTranslation(LocalizationKey.INVOICE_SUBTOTAL, "Subtotal de la factura");
        super.setTranslation(LocalizationKey.INVOICE_TOTAL, "Total de la factura");
        super.setTranslation(LocalizationKey.INVOICE_CODE, "Código de la factura");
        super.setTranslation(LocalizationKey.INVOICE_GENERATION_DATETIME, "Fecha de generación de la factura");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_CODE, "Código del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_NAME, "Nombre del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_TIN, "DNI, NIE o CIF del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_ADDRESS, "Dirección del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_CITY, "Ciudad del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_PROVINCE, "Provincia del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_ZIPCODE, "Código postal del proveedor");
        super.setTranslation(LocalizationKey.SUPPLIER_CUSTOMER_IBAN, "IBAN del proveedor");
        super.setTranslation(LocalizationKey.INVOICE_GENERATED_MESSAGE, "La factura ha sido generada con éxito");
        super.setTranslation(LocalizationKey.INVOICE_NOT_GENERATED_MESSAGE, "Error al generar la factura");
        super.setTranslation(LocalizationKey.TOTAL, "Total");
        super.setTranslation(LocalizationKey.REMOVED_INVOICE_MESSAGE, "Factura eliminada con éxito");
        super.setTranslation(LocalizationKey.REMOVED_INVOICE_ERROR_MESSAGE, "Error al eliminar la factura");
        super.setTranslation(LocalizationKey.COMPANY_COMMISSION, "Comisión de la empresa (4%)");
        super.setTranslation(LocalizationKey.INDIVIDUAL_COMMISSION, "Comisión del particular (2%)");
        super.setTranslation(LocalizationKey.CLOSE, "Cerrar");
        super.setTranslation(LocalizationKey.CLOSED_INVOICE_MESSAGE, "Factura cerrada con éxito");
        super.setTranslation(LocalizationKey.CLOSED_INVOICE_ERROR_MESSAGE, "Error al cerrar la factura");
        super.setTranslation(LocalizationKey.NEW_PASSWORD, "Nueva contraseña");
        super.setTranslation(LocalizationKey.ESTABLISH, "Establecer");
        super.setTranslation(LocalizationKey.PASSWORD_ESTABLISHED_MESSAGE, "Contraseña establecida");
        super.setTranslation(LocalizationKey.PASSWORD_NOT_ESTABLISHED_MESSAGE, "Error al establecer la contraseña");
        super.setTranslation(LocalizationKey.PASSWORD, "Contraseña");
        super.setTranslation(LocalizationKey.INTRODUCE_PASSWORD_MESSAGE, "Introduzca la contraseña");
        super.setTranslation(LocalizationKey.CORRECT_PASSWORD_MESSAGE, "La contraseña introducida es correcta");
        super.setTranslation(LocalizationKey.INCORRECT_PASSWORD_MESSAGE, "Contraseña incorrecta. Inténtelo otra vez.");
        super.setTranslation(LocalizationKey.NON_INTRODUCED_PASSWORD_MESSAGE, "Contraseña no introducida correctamente después de los intentos. Saliendo de la aplicación. Contacte con el administrador si quiere restaurar la contraseña.");
        super.setTranslation(LocalizationKey.INVOICE_TOTAL_WEIGHT, "Peso total de la factura");
        super.setTranslation(LocalizationKey.TOTAL_WEIGHT, "Peso total (en kilos)");
        super.setTranslation(LocalizationKey.TOTAL_AMOUNT, "Cantidad total (en euros)");
    }

}
