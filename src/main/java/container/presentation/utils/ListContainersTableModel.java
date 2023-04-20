package container.presentation.utils;

import container.application.ContainerAttribute;
import container.application.usecases.EditContainer;
import container.persistence.mongo.MongoContainerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import shared.persistence.exceptions.NotDefinedDatabaseContextException;
import shared.presentation.localization.Localization;
import shared.presentation.localization.LocalizationKey;

/**
 * Table model for the list containers panel.
 */
public class ListContainersTableModel extends DefaultTableModel {

    /**
     * Constructor.
     *
     * @param data Table data.
     * @param columnNames Table column names.
     */
    public ListContainersTableModel(Vector<? extends Vector> data, Vector<?> columnNames) {
        super(data, columnNames);
    }

    /**
     * Obtain all the container attributes based on the new value and its
     * position defined by the row and the column.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     * @return A map with all the attributes of the edited container.
     */
    private Map<ContainerAttribute, Object> getContainerAttributes(Object newValue, int row, int column) {
        Map<ContainerAttribute, Object> containerAttributes = new HashMap<>();

        Object containerCode = super.getValueAt(row, 0);
        containerAttributes.put(ContainerAttribute.CODE, containerCode);

        Object containerName = column == 1 ? newValue : super.getValueAt(row, 1);
        containerAttributes.put(ContainerAttribute.NAME, containerName);

        Object containerWeight = column == 2 ? newValue : super.getValueAt(row, 2);
        containerAttributes.put(ContainerAttribute.WEIGHT, containerWeight);

        // Container can be only updated if it is not deleted.
        containerAttributes.put(ContainerAttribute.ISDELETED, false);

        return containerAttributes;
    }

    /**
     * Execute the edit container use case.
     *
     * @param newValue The new value for the modified cell.
     * @param row The row of the modified cell.
     * @param column The column of the modified cell.
     */
    private void editContainer(Object newValue, int row, int column) {
        try {
            Map<ContainerAttribute, Object> containerAttributes = this.getContainerAttributes(newValue, row, column);

            MongoContainerRepository containerRepository = new MongoContainerRepository();
            EditContainer editContainer = new EditContainer(containerRepository);
            editContainer.execute(containerAttributes);
        } catch (NotDefinedDatabaseContextException ex) {
            String className = ListContainersTableModel.class.getName();
            Logger.getLogger(className).log(Level.INFO, "Container not edited because the database has not been found", ex);
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
            // Weight column.
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
        // Removed containers cannot be edited.
        String buttonValue = (String) super.getValueAt(row, 3);
        boolean isRemovedContainer = buttonValue.equals(Localization.getLocalization(LocalizationKey.RESTORE));

        if (isRemovedContainer) {
            return false;
        }

        int nameColumnIndex = 1;
        int weightColumnIndex = 2;

        // Only the name and the weight can be edited.
        return column == nameColumnIndex || column == weightColumnIndex;
    }

    /**
     * {@inheritDoc}
     *
     * Note that it also edits the container on the system.
     */
    @Override
    public void setValueAt(Object newValue, int row, int column) {
        super.setValueAt(newValue, row, column);

        if (this.isCellEditable(row, column)) {
            this.editContainer(newValue, row, column);
        }
    }

}
