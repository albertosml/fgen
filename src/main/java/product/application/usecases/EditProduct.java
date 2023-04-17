package product.application.usecases;

import java.util.Map;
import product.application.Product;
import product.application.ProductAttribute;
import product.persistence.ProductRepository;

/**
 * Edit product use case.
 */
public class EditProduct {

    /**
     * @see ProductRepository
     */
    private ProductRepository productRepository;

    /**
     * Constructor.
     *
     * @param productRepository Product repository.
     */
    public EditProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Update the given product.
     *
     * @param productAttributes The product attributes.
     */
    public void execute(Map<ProductAttribute, Object> productAttributes) {
        Product product = Product.from(productAttributes);
        productRepository.update(product);
    }

}
