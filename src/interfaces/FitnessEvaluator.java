package interfaces;

import java.util.Random;

/**
 * Interface that provides possibility of fitness evaluation.
 */
public abstract class FitnessEvaluator<T> {

	protected double min;
	protected double max;
	
	private static Random rand = new Random();
	
	/**
	 * Evaluates fitness of the component returns the result.
	 * @param component input of the function
	 */
	public abstract double evaluate(T component);
	
	/**
	 * Function that returns a random number within the defined domain.
	 * 
	 * @return random number
	 */
	public double generateDomainRandomNumber() {
		double num = Math.abs(rand.nextInt()*rand.nextDouble()) % (max - min);
		return num + min;
	}

	/**
	 * @return minimum value for domain number used for this function.
	 */
	public double getMin() {
		return min;
	}

	/**
	 * @return maximum value for domain number used for this function.
	 */
	public double getMax() {
		return max;
	}
	
	
}
