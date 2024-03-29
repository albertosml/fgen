package product.persistence;

import java.util.ArrayList;
import product.application.Product;
import shared.persistence.Repository;

/**
 * Represents the repository model for the product entity.
 */
public interface ProductRepository extends Repository {

    /**
     * Find the product which matches with the given code.
     *
     * @param code The code of the product to find.
     * @return The found product, otherwise null.
     */
    public Product find(String code);

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
     * @param includeRemoved Whether we should include removed products or not.
     * @return A list with all products.
     */
    public ArrayList<Product> get(boolean includeRemoved);

    /**
     * Register the given product.
     *
     * @param product The product to register.
     */
    public void register(Product product);

    /**
     * Update the given product with its associated data.
     *
     * @param product The product to update.
     * @return Whether the product has been updated or not.
     */
    public boolean update(Product product);

}
