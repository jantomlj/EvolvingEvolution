package interfaces;

/**
 * This interface provides methods for selection in an evolutionary algorithm. Its execute
 * method should do selection on the two members that are at set positions in the population
 * and put the resulting unit in the 'result position' in the population.
 */
public interface Selector extends Instruction {

	/**
	 * Sets the pointer to the population member that is going to be a part of selection
	 * process.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setPosition1(int pos);

	/**
	 * Sets the pointer to the population member that is going to be a part of selection.
	 * process.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setPosition2(int pos);

	/**
	 * Sets the position into which will the product of selection be put into.
	 * 
	 * @param pos
	 *            index in the population
	 */
	void setResultPosition(int pos);
	
	int getPosition1();
	int getPosition2();
	int getResultPosition();
	
}
