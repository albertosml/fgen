package invoice.application.usecases;

import invoice.application.Invoice;
import invoice.application.InvoiceAttribute;
import invoice.application.utils.InvoiceValidationState;
import invoice.application.utils.InvoiceValidator;
import invoice.persistence.InvoiceRepository;
import java.util.Map;
import shared.application.utils.CodeAutoGenerator;

/**
 * Create invoice use case.
 */
public class CreateInvoice {

    /**
     * @see InvoiceRepository
     */
    private InvoiceRepository invoiceRepository;

    /**
     * Constructor.
     *
     * @param invoiceRepository Invoice repository.
     */
    public CreateInvoice(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Execute the invoice object creation.
     *
     * Note that the invoice code is automatically generated if it has not been
     * introduced manually.
     *
     * @param newInvoiceAttributes The attributes for the invoice to create.
     * @return A pair indicating the delivery note and its validation state.
     */
    public Invoice execute(Map<InvoiceAttribute, Object> newInvoiceAttributes) {
        boolean isCodeManuallyAdded = newInvoiceAttributes.containsKey(InvoiceAttribute.CODE);
        if (!isCodeManuallyAdded) {
            int generatedInvoiceCode = CodeAutoGenerator.generate(invoiceRepository);
            newInvoiceAttributes.put(InvoiceAttribute.CODE, generatedInvoiceCode);
        }

        Invoice invoice = Invoice.from(newInvoiceAttributes);

        InvoiceValidationState state = InvoiceValidator.isValid(invoice);
        return state == InvoiceValidationState.VALID ? invoice : null;
    }

}
