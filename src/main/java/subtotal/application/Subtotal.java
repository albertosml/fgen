package subtotal.application;

import java.util.Map;

/**
 * Subtotal entity class.
 */
public class Subtotal {

    /**
     * Code.
     */
    private int code;

    /**
     * Name.
     */
    private String name;

    /**
     * Percentage.
     *
     * It is a number between 0 and 100.
     */
    private int percentage;

    /**
     * Whether the subtotal is a discount or a tax.
     */
    private boolean isDiscount;

    /**
     * Whether the subtotal is deleted or not.
     */
    private boolean isDeleted;

    /**
     * Constructor.
     *
     * @param code Subtotal code.
     * @param name Subtotal name.
     * @param percentage Subtotal percentage.
     * @param isDiscount Whether the subtotal is a discount or a tax.
     * @param isDeleted Whether the subtotal is deleted or not.
     */
    private Subtotal(int code, String name, int percentage, boolean isDiscount, boolean isDeleted) {
        this.code = code;
        this.name = name;
        this.isDiscount = isDiscount;
        this.isDeleted = isDeleted;

        this.setPercentage(percentage);
    }

    /**
     * Retrieve the subtotal code.
     *
     * @return The subtotal code.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Retrieve the subtotal name.
     *
     * @return The subtotal name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the subtotal percentage.
     *
     * @return The subtotal percentage.
     */
    public int getPercentage() {
        return this.percentage;
    }

    /**
     * Indicate whether the subtotal is a discount or a tax.
     *
     * @return true if the subtotal is a discount, false if it is a tax.
     */
    public boolean isDiscount() {
        return this.isDiscount;
    }

    /**
     * Whether the subtotal is deleted from the system or not.
     *
     * @return true if the subtotal is removed, otherwise false.
     */
    public boolean isDeleted() {
        return this.isDeleted;
    }

    /**
     * Update the deletion state for the subtotal.
     *
     * @param isDeleted Whether the subtotal is deleted from the system or not.
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Set the subtotal percentage.
     *
     * @param percentage A percentage between 0 and 100.
     */
    private void setPercentage(int percentage) {
        if (percentage < 0) {
            this.percentage = 0;
        } else if (percentage > 100) {
            this.percentage = 100;
        } else {
            this.percentage = percentage;
        }
    }

    /**
     * Create a subtotal given its attribute values.
     *
     * @param attributes Map containing the value for each subtotal attribute.
     * @return The created subtotal.
     */
    public static Subtotal from(Map<SubtotalAttribute, Object> attributes) {
        int code = (int) attributes.get(SubtotalAttribute.CODE);
        String name = (String) attributes.getOrDefault(SubtotalAttribute.NAME, "");
        int percentage = (int) attributes.getOrDefault(SubtotalAttribute.PERCENTAGE, 0);
        boolean isDiscount = (boolean) attributes.getOrDefault(SubtotalAttribute.ISDISCOUNT, false);
        boolean isDeleted = (boolean) attributes.getOrDefault(SubtotalAttribute.ISDELETED, false);

        return new Subtotal(code, name, percentage, isDiscount, isDeleted);
    }

    /**
     * Calculate the subtotal for the given value.
     *
     * @param value The value used to calculate the subtotal.
     * @return The result after calculating the subtotal.
     */
    public float calculate(float value) {
        float percentageDecimal = this.percentage / 100;
        float subtotalResult = value * percentageDecimal;
        return this.isDiscount ? -subtotalResult : subtotalResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%d - %s", this.code, this.name);
    }
}
