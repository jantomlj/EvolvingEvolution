package interfaces;

/**
 * This interface provides methods for mutation in an evolutionary algorithm. It mutates one
 * member of the population to which position it is pointing. Mutation results in another
 * member that is then put into the 'resulting position' in the population.
 */
public interface Mutator extends Instruction {

	/**
	 * Sets the pointer to the population member that is going to be a part of mutation
	 * process.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setPosition(int pos);

	/**
	 * Sets the position to which the product of the mutation will be put into.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setResultPosition(int pos);

	int getPosition();
	int getResultPosition();
}
