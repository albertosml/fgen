package product.presentation.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import product.application.usecases.RemoveProduct;
import product.application.usecases.RestoreProduct;
import product.persistence.mongo.MongoProductRepository;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Mouse adapter for the list products panel, which includes a button to remove
 * or restore a product.
 */
public class ListProductsMouseAdapter extends MouseAdapter {

    /**
     * Table associated to the mouse adapter.
     */
    private JTable table;

    /**
     * Constructor.
     *
     * @param table The table associated to the mouse adapter.
     */
    public ListProductsMouseAdapter(JTable table) {
        this.table = table;
    }

    /**
     * Execute the remove product use case.
     *
     * @param code The code of the product to remove.
     * @return Whether the product with the given code has been removed or not.
     */
    private boolean removeProduct(String code) {
        try {
            MongoProductRepository productRepository = new MongoProductRepository();
            RemoveProduct removeProduct = new RemoveProduct(productRepository);
            return removeProduct.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListProductsMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Product not removed because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Execute the restore product use case.
     *
     * @param code The code of the product to restore.
     * @return Whether the product with the given code has been restored or not.
     */
    private boolean restoreProduct(String code) {
        try {
            MongoProductRepository productRepository = new MongoProductRepository();
            RestoreProduct restoreProduct = new RestoreProduct(productRepository);
            return restoreProduct.execute(code);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListProductsMouseAdapter.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Product not restored because the database has not been found", ex);
        }

        return false;
    }

    /**
     * Remove or restore the product depending on the deletion state.
     *
     * @param evt The mouse event.
     */
    private void removeOrRestoreProduct(MouseEvent evt) {
        int removeRestoreColumn = 2;
        int row = table.rowAtPoint(evt.getPoint());

        TableModel tableModel = table.getModel();

        String cellText = (String) tableModel.getValueAt(row, removeRestoreColumn);
        String removeText = Localization.getLocalization(LocalizationKey.REMOVE);
        String restoreText = Localization.getLocalization(LocalizationKey.RESTORE);

        String productCode = (String) tableModel.getValueAt(row, 0);

        if (cellText.equals(removeText)) {
            boolean isProductRemoved = this.removeProduct(productCode);

            if (isProductRemoved) {
                // Button state needs to be updated as the product state has changed.
                tableModel.setValueAt(restoreText, row, removeRestoreColumn);
            }
        } else {
            boolean isProductRestored = this.restoreProduct(productCode);

            if (isProductRestored) {
                // Button state needs to be updated as the product state has changed.
                tableModel.setValueAt(removeText, row, removeRestoreColumn);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        int column = table.columnAtPoint(evt.getPoint());

        if (column == 2) {
            // In column 2, there is a button to restore or remove
            // the chosen product defined by the clicked row.
            this.removeOrRestoreProduct(evt);
        }
    }

}
