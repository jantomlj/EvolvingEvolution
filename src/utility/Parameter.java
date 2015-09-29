package utility;

/**
 * Class that describes a parameter of an evolutionary algorithm.
 */
public class Parameter {

	private double value;
	private final double min;
	private final double max;
	private final double defaultValue;

	/**
	 * Creates a parameter with given minimum, maximum and defaultValue.
	 * @param min minimum allowed value of the parameter
	 * @param max maximum allowed value of the parameter
	 * @param defaultValue value is firstly set to this value
	 */
	public Parameter(double min, double max, double defaultValue) {
		this.min = min;
		this.max = max;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}

	/**
	 * @return current value of the parameter
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value of the parameter to the given value
	 */
	public void setValue(double value) {
		if (value < min || value > max) {
			throw new IllegalArgumentException("Parameter value was tried to set to an illegal value");
		}
		this.value = value;
	}

	/**
	 * @return the minimal value of the parameter
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @return the maximal value of the parameter
	 */
	public double getMax() {
		return max;
	}
	
	/**
	 * @return the default value of the parameter
	 */
	public double getDefaultValue () {
		return defaultValue;
	}
	
	public Parameter copy() {
		Parameter p = new Parameter(min, max, defaultValue);
		p.value = value;
		return p;
	}


}
