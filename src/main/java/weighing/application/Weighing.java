package weighing.application;

import container.application.Box;
import java.util.Map;

/**
 * Weighing entity class.
 */
public class Weighing {

    /**
     * The weighing box.
     */
    private Box box;

    /**
     * The boxes quantity.
     */
    private int qty;

    /**
     * The gross weight.
     */
    private int weight;

    /**
     * Constructor.
     *
     * @param box The weighing box.
     * @param qty The boxes quantity in the weighing
     * @param weight The gross weight on the weighing.
     */
    private Weighing(Box box, int qty, int weight) {
        this.box = box;
        this.qty = qty;
        this.weight = weight;
    }

    /**
     * Retrieve the weighing box.
     *
     * @return The weighing box.
     */
    public Box getBox() {
        return this.box;
    }

    /**
     * Retrieve the boxes quantity on the weighing.
     *
     * @return An integer indicating the boxes quantity.
     */
    public int getQty() {
        return this.qty;
    }

    /**
     * Retrieve the gross weight.
     *
     * @return A double indicating the gross weight.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Create a weighing given its attribute values.
     *
     * @param attributes Map containing the value for each weighing attribute.
     * @return The created weighing.
     */
    public static Weighing from(Map<WeighingAttribute, Object> attributes) {
        Box box = (Box) attributes.get(WeighingAttribute.BOX);
        int qty = (int) attributes.getOrDefault(WeighingAttribute.QTY, 0);
        int weight = (int) attributes.getOrDefault(WeighingAttribute.WEIGHT, 0);

        return new Weighing(box, qty, weight);
    }
}
