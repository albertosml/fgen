package product.application.utils;

import product.application.Product;
import product.persistence.ProductRepository;

/**
 * Validate if the product to register already exists on the repository by
 * checking its code.
 *
 * @see Product
 */
public class DuplicatedProductValidator {

    /**
     * Check if there is a product with the same code or not.
     *
     * @param product The product to validate.
     * @param productRepository The product repository.
     * @return true if the product is duplicated, otherwise false.
     */
    public static boolean isDuplicated(Product product, ProductRepository productRepository) {
        String productCode = product.getCode();

        for (String code : productRepository.getCodeList()) {
            if (code.equals(productCode)) {
                return true;
            }
        }

        return false;
    }

}
