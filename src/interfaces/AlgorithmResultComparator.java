package interfaces;

/**
 * Interface that provides methods for comparing fitnesses of {@link EvolutionaryAlgorithm}s.
 */
public interface AlgorithmResultComparator {

	/**
	 * Decides whether the first given fitness of an algorithm is better then the second
	 * 
	 * @param result1
	 *            first algorithm fitness
	 * @param result2
	 *            second algorithm fitness
	 * @return true if result1 is better fitness then result2 accoridng to the implementation,
	 *         false otherwise
	 */
	boolean isBetter(double[] result1, double[] result2);

	/**
	 * Method with which information is provided wheather the algorithms whose fitnesses can be
	 * compared are searching for a minimum value.
	 * 
	 * @param findingMinimum
	 */
	void setFindingMinimum(boolean findingMinimum);
}
