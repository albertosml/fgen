package product.application.utils;

import product.application.Product;
import product.persistence.ProductRepository;
import shared.application.utils.CodeValidator;
import shared.application.utils.NameValidator;
import shared.application.utils.PriceValidator;

/**
 * Validate the product.
 *
 * @see Product
 */
public class ProductValidator {

    /**
     * Check whether a product is valid or not.
     *
     * Note that the product repository is optional. It only needs to be
     * specified when finding duplicated product codes.
     *
     * @param product The product to validate.
     * @param productRepository The product repository to check for duplicated
     * product codes.
     * @return The validation state for the product.
     */
    public static ProductValidationState isValid(Product product, ProductRepository productRepository) {
        boolean isValidName = NameValidator.isValid(product.getName());
        if (!isValidName) {
            return ProductValidationState.INVALID_NAME;
        }

        boolean isValidPrice = PriceValidator.isValid(product.getPrice());
        if (!isValidPrice) {
            return ProductValidationState.INVALID_PRICE;
        }

        boolean isValidCode = CodeValidator.isValid(product.getCode());
        if (!isValidCode) {
            return ProductValidationState.INVALID_CODE;
        }

        if (productRepository != null && DuplicatedProductValidator.isDuplicated(product, productRepository)) {
            return ProductValidationState.DUPLICATED;
        }

        return ProductValidationState.VALID;
    }

}
