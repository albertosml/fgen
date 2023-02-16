package invoice.application.usecases;

import invoice.application.InvoiceItemAttribute;
import java.util.Map;
import shared.application.utils.DescriptionValidator;

/**
 * Edit invoice item use case.
 */
public class EditInvoiceItem {

    /**
     * Constructor.
     */
    public EditInvoiceItem() {
    }

    /**
     * Edit an invoice item based on the given attributes.
     *
     * @param attributes The invoice item attributes.
     * @return Whether the invoice item has been edited or not.
     */
    public boolean execute(Map<InvoiceItemAttribute, Object> attributes) {
        String description = (String) attributes.getOrDefault(InvoiceItemAttribute.DESCRIPTION, null);

        // The invoice item can be edited if it has a valid description.
        return DescriptionValidator.isValid(description);
    }

}
