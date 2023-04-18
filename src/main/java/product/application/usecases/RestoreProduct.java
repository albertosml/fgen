package product.application.usecases;

import product.application.Product;
import product.persistence.ProductRepository;

/**
 * Restore product use case.
 */
public class RestoreProduct {

    /**
     * @see ProductRepository
     */
    private ProductRepository productRepository;

    /**
     * Constructor.
     *
     * @param productRepository Product repository.
     */
    public RestoreProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Restore the product which matches with the given code.
     *
     * @param code The code of the product to restore.
     * @return Whether the product has been restored or not.
     */
    public boolean execute(String code) {
        Product product = productRepository.find(code);

        if (product == null) {
            return false;
        }

        product.setIsDeleted(false);
        return productRepository.update(product);
    }

}
