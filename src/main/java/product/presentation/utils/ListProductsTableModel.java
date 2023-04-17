package product.presentation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import product.application.ProductAttribute;
import product.application.usecases.EditProduct;
import product.persistence.mongo.MongoProductRepository;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Table model for the list products panel.
 */
public class ListProductsTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public ListProductsTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * Obtain all the product attributes based on the new value and its position
     * defined by the row and the column.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return A map with all the attributes of the edited product.
     */
    private Map<ProductAttribute, Object> getProductAttributes(Object newValue, int row, int column) {
        Map<ProductAttribute, Object> productAttributes = new HashMap<>();

        Object productCode = super.getValueAt(row, 0);
        productAttributes.put(ProductAttribute.CODE, productCode);

        Object productName = column == 1 ? newValue : super.getValueAt(row, 1);
        productAttributes.put(ProductAttribute.NAME, productName);

        Object productPrice = column == 2 ? newValue : super.getValueAt(row, 2);
        productAttributes.put(ProductAttribute.PRICE, productPrice);

        // Product can be only updated if it is not deleted.
        productAttributes.put(ProductAttribute.ISDELETED, false);

        return productAttributes;
    }

    /**
     * Execute the edit product use case.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     */
    private void editProduct(Object newValue, int row, int column) {
        try {
            Map<ProductAttribute, Object> productAttributes = this.getProductAttributes(newValue, row, column);

            MongoProductRepository productRepository = new MongoProductRepository();
            EditProduct editProduct = new EditProduct(productRepository);
            editProduct.execute(productAttributes);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListProductsTableModel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Product not edited because the database has not been found", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            // Code column.
            case 0:
                return Integer.class;
            // Name column.
            case 1:
                return String.class;
            // Price column.
            case 2:
                return Double.class;
            default:
                return Object.class;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        int nameColumnIndex = 1;
        int priceColumnIndex = 2;

        // Only the name and the price can be edited.
        return column == nameColumnIndex || column == priceColumnIndex;
    }

    /**
     * {@inheritDoc}
     *
     * Note that it also edits the product on the system.
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        super.setValueAt(newValue, row, column);

        if (this.isCellEditable(row, column)) {
            this.editProduct(newValue, row, column);
        }
    }

}
