package container.application;

/**
 * Pallet container.
 */
public class Pallet extends Container {

    /**
     * Constructor.
     *
     * @param code Pallet code.
     * @param name Pallet name.
     * @param weight Pallet weight.
     * @param isDeleted Whether the pallet is deleted or not.
     */
    public Pallet(int code, String name, double weight, boolean isDeleted) {
        super(code, name, weight, isDeleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBox() {
        return false;
    }

}
