package product.application.usecases;

import product.application.Product;
import product.persistence.ProductRepository;

/**
 * Remove product use case.
 */
public class RemoveProduct {

    /**
     * @see ProductRepository
     */
    private ProductRepository productRepository;

    /**
     * Constructor.
     *
     * @param productRepository Product repository.
     */
    public RemoveProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Remove the product which matches with the given code.
     *
     * @param code The code of the product to remove.
     * @return Whether the product has been removed or not.
     */
    public boolean execute(String code) {
        Product product = productRepository.find(code);

        if (product == null) {
            return false;
        }

        product.setIsDeleted(true);
        return productRepository.update(product);
    }

}
