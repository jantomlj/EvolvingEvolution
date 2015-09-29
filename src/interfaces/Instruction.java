package interfaces;

import java.util.Map;

/**
 * This interface unites operations for selection, mutation and crossover. These operations are
 * encoded as objects Selector, Mutator and Crosser.
 */
public interface Instruction {

	/**
	 * This method does either selection, mutation or the crossover, depending on the object
	 * that uses it. The result of that operation is then put somewhere (decided seperatly by
	 * each object) back into the population.
	 * 
	 * @param population
	 *            on which the instruction will be executed
	 * @param evaluator
	 *            that will be used for fitness evaluator if needed
	 * @param hash
	 *            maping from position in the population to the fitness of the unit
	 */
	void execute(Object[] population, FitnessEvaluator<Object> evaluator, Map<Integer, Double> hash);

	/**
	 * Returns the fitness of the best unit that was evaluated when selection was done. In case
	 * this object is a mutator or crosser, this method will result in
	 * {@link UnsupportedOperationException}
	 * 
	 * @throws UnsupportedOperationException
	 */
	double getResult();

	/**
	 * Calculates how many times has this instruction evaluated fitness of a population member
	 * in the last call of the execute method
	 * 
	 * @return number of fitness evaluations in the last call of execute
	 */
	int getNumOfFitnessEvaluations();

	/**
	 * Creates a new object with exatly the same characteristics as the original one. It should
	 * be a shallow copy, no copy contructurs should be called.
	 * 
	 * @return exact same copy of this object
	 */
	Instruction copy();
}
