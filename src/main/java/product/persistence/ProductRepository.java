package product.persistence;

import java.util.ArrayList;
import product.application.Product;
import shared.persistence.Repository;

/**
 * Represents the repository model for the product entity.
 */
public interface ProductRepository extends Repository {

    /**
     * Get the code from all products from the repository.
     *
     * @return A list with all the retrieved product codes.
     */
    public ArrayList<String> getCodeList();

    /**
     * Obtain all the products registered on the system, even if they have been
     * removed after.
     *
     * @return A list with all products.
     */
    public ArrayList<Product> get();

    /**
     * Register the given product.
     *
     * @param product The product to register.
     */
    public void register(Product product);

}
