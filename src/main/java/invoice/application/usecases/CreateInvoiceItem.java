package invoice.application.usecases;

import invoice.application.InvoiceItem;
import invoice.application.InvoiceItemAttribute;
import java.util.Map;
import shared.application.utils.DescriptionValidator;

/**
 * Create invoice item use case.
 */
public class CreateInvoiceItem {

    /**
     * Constructor.
     */
    public CreateInvoiceItem() {
    }

    /**
     * Create an invoice item based on the given attributes.
     *
     * @param attributes The invoice item attributes.
     * @return The created invoice item if the attributes are valid, otherwise
     * null.
     */
    public InvoiceItem execute(Map<InvoiceItemAttribute, Object> attributes) {
        String description = (String) attributes.get(InvoiceItemAttribute.DESCRIPTION);

        boolean isDescriptionValid = DescriptionValidator.isValid(description);

        if (!isDescriptionValid) {
            return null;
        }

        return InvoiceItem.from(attributes);
    }
}
