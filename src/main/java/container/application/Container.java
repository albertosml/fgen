package container.application;

import java.util.Map;

/**
 * Container entity class.
 */
public class Container {

    /**
     * Container code.
     */
    private int code;

    /**
     * Container name.
     */
    private String name;

    /**
     * Container weight.
     */
    private double weight;

    /**
     * Whether the container is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code Container code.
     * @param name Container name.
     * @param weight Container weight.
     * @param isDeleted Whether the container is deleted or not.
     */
    private Container(int code, String name, double weight, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.isDeleted = isDeleted;
    }

    /**
     * Retrieve the container code.
     *
     * @return The container code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the container name.
     *
     * @return The container name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the container weight.
     *
     * @return The container weight.
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Whether the container is deleted from the system or not.
     *
     * @return true if the container is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Update the deletion state for the container.
     *
     * @param isDeleted Whether the container is deleted from the system or not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%d - %s", this.code, this.name);
    }

    /**
     * Create a container given its attribute values.
     *
     * @param attributes Map containing the value for each container attribute.
     * @return The created container.
     */
    public static Container from(Map<ContainerAttribute, Object> attributes) {
        int unregisteredContainerCode = 0;

        int code = (int) attributes.getOrDefault(ContainerAttribute.CODE, unregisteredContainerCode);
        String name = (String) attributes.getOrDefault(ContainerAttribute.NAME, "");
        Object weightValue = attributes.getOrDefault(ContainerAttribute.WEIGHT, "0");
        double weight = Double.parseDouble(weightValue.toString());
        boolean isDeleted = (boolean) attributes.getOrDefault(ContainerAttribute.ISDELETED, false);

        return new Container(code, name, weight, isDeleted);
    }

}
