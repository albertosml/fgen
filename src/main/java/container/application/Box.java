package container.application;

/**
 * Box container.
 */
public class Box extends Container {

    /**
     * Constructor.
     *
     * @param code Box code.
     * @param name Box name.
     * @param weight Box weight.
     * @param isDeleted Whether the box is deleted or not.
     */
    public Box(int code, String name, double weight, boolean isDeleted) {
        super(code, name, weight, isDeleted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBox() {
        return true;
    }

}
