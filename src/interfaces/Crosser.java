package interfaces;

/**
 * This interface provides methods for crossover in an evolutionary algorithm. Its execute
 * method should do crossover on the two members that are at set positions in the population
 * and put the resulting unit in the 'result position' in the population.
 */
public interface Crosser extends Instruction {

	/**
	 * Sets the pointer to the population member that is going to be a part of a crossover.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setPosition1(int pos);

	/**
	 * Sets the pointer to the population member that is going to be a part of a crossover.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setPosition2(int pos);

	/**
	 * Sets the position where the product of the crossover will be put
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setResultPosition(int pos);
	
	int getPosition1();
	int getPosition2();
	int getResultPosition();
}
