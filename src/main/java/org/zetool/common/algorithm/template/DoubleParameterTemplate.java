package org.zetool.common.algorithm.template;

/**
 * A class representing algorithmic parameters that take double values and is capable of checking whether new values are
 * in a specified interval. The interval bounds are considered to be included in the interval.
 *
 * @author Martin Groß
 */
public class DoubleParameterTemplate extends DefaultParameterTemplate<Double> {

    /**
     * The lower bound (inclusive) for the range check.
     */
    private double lowerBound;
    /**
     * The upper bound (inclusive) for the range check.
     */
    private double upperBound;

    /**
     * Creates a new DoubleParameterTemplate with the given name and description, that belongs to the specified
     * parameter set and has the given default value. The interval of allowed values is initialized to
     * <code>Double.NEGATIVE_INFINITY</code> and <code>Double.POSITIVE_INFINITY</code>.
     *
     * @param name the name of this parameter.
     * @param description the description of this parameter.
     * @param value the default value for this parameter.
     */
    public DoubleParameterTemplate(String name, String description, Double value) {
        this(name, description, value, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * Creates a new DoublerParameter with the given name and description, that belongs to the specified parameter set
     * and has the given default value.Furthermore, it specifies an interval, which new values are checked with.
     *
     * @param name the name of this parameter.
     * @param description the description of this parameter.
     * @param value the default value for this parameter.
     * @param lowerBound the lower bound (inclusive)
     * @param upperBound the upper bound (inclusive)
     */
    public DoubleParameterTemplate(String name, String description, Double value, double lowerBound, double upperBound) {
        super(name, description, Double.TYPE, value);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Returns the lower bound of the range check.
     *
     * @return the lower bound of the range check.
     */
    public double getLowerBound() {
        return lowerBound;
    }

    /**
     * Sets the upper bound of the range check.
     *
     * @param lowerBound the upper bound of the range check.
     */
    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Returns the upper bound of the range check.
     *
     * @return the upper bound of the range check.
     */
    public double getUpperBound() {
        return upperBound;
    }

    /**
     * Sets the upper bound of the range check.
     *
     * @param upperBound the upper bound of the range check.
     */
    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * Checks whether the <code>lower bound &lt;= value &lt;= upper bound</code> holds.
     *
     * @param value the value which is being validated.
     * @return the result of the validation.
     */
    @Override
    public ValidationResult isValid(Double value) {
        if (value < lowerBound) {
            return new ValidationResult(false, value + " is smaller than " + lowerBound + ".");
        }
        if (value > upperBound) {
            return new ValidationResult(false, value + " is greater than " + upperBound + ".");
        }
        return ValidationResult.SUCCESS;
    }
}
