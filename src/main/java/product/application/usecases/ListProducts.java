package product.application.usecases;

import java.util.ArrayList;
import product.application.Product;
import product.persistence.ProductRepository;

/**
 * List customers use case.
 */
public class ListProducts {

    /**
     * @see ProductRepository
     */
    private ProductRepository productRepository;

    /**
     * Constructor.
     *
     * @param productRepository Product repository.
     */
    public ListProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * List all products.
     *
     * @return A list with all available products.
     */
    public ArrayList<Product> execute() {
        return productRepository.get();
    }

}
