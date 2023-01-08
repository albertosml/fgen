package subtotal.application.usecases;

import java.util.Map;
import shared.application.utils.CodeAutoGenerator;
import subtotal.application.Subtotal;
import subtotal.application.SubtotalAttribute;
import subtotal.persistence.SubtotalRepository;

/**
 * Register subtotal use case.
 */
public class RegisterSubtotal {

    /**
     * @see SubtotalRepository
     */
    private final SubtotalRepository subtotalRepository;

    /**
     * Constructor.
     *
     * @param subtotalRepository Subtotal repository.
     */
    public RegisterSubtotal(SubtotalRepository subtotalRepository) {
        this.subtotalRepository = subtotalRepository;
    }

    /**
     * Execute the subtotal registration.
     *
     * Note that the subtotal code is automatically generated if it has not been
     * introduced manually.
     *
     * @param newSubtotalAttributes The attributes for the subtotal to register.
     */
    public void execute(Map<SubtotalAttribute, Object> newSubtotalAttributes) {
        boolean isCodeManuallyAdded = newSubtotalAttributes.containsKey(SubtotalAttribute.CODE);
        if (!isCodeManuallyAdded) {
            int generatedSubtotalCode = CodeAutoGenerator.generate(subtotalRepository);
            newSubtotalAttributes.put(SubtotalAttribute.CODE, generatedSubtotalCode);
        }

        Subtotal subtotal = Subtotal.from(newSubtotalAttributes);
        subtotalRepository.register(subtotal);
    }

}
