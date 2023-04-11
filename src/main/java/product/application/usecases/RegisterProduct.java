package product.application.usecases;

import java.util.Map;
import product.application.Product;
import product.application.ProductAttribute;
import product.application.utils.ProductValidationState;
import product.application.utils.ProductValidator;
import product.persistence.ProductRepository;

/**
 * Register product use case.
 */
public class RegisterProduct {

    /**
     * @see ProductRepository
     */
    private ProductRepository productRepository;

    /**
     * Constructor.
     *
     * @param productRepository Product repository.
     */
    public RegisterProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Execute the product registration.
     *
     * Keep in mind that the product is validated before the registration.
     *
     * @param newProductAttributes The attributes for the product to register.
     * @return The validation state for the product to register.
     */
    public ProductValidationState execute(Map<ProductAttribute, Object> newProductAttributes) {
        Product product = Product.from(newProductAttributes);

        ProductValidationState productValidationState = ProductValidator.isValid(product, productRepository);
        if (productValidationState == ProductValidationState.VALID) {
            productRepository.register(product);
        }

        return productValidationState;
    }

}
