package gpalgorithm;

import interfaces.AlgorithmResultComparator;
import interfaces.EvolutionaryAlgorithm;

/**
 * Class that compares fitnesses of {@link EvolutionaryAlgorithm}s. One fitness vector is
 * better than the other if in comparing vector components, it has a larger number of better
 * results.
 */
public class AlgorithmResultComparator2 implements AlgorithmResultComparator {

	private boolean findingMinimum;

	/**
	 * Creates a new instance with a given value of findingMinimum
	 * 
	 * @param findingMinimum
	 *            true if smaller fitness is considered better, false otherwise
	 */
	public AlgorithmResultComparator2(boolean findingMinimum) {
		this.findingMinimum = findingMinimum;
	}

	@Override
	public void setFindingMinimum(boolean findingMinimum) {
		this.findingMinimum = findingMinimum;
	}

	@Override
	public boolean isBetter(double[] result1, double[] result2) {
		if (result1.length != result2.length) {
			throw new IllegalArgumentException("Results of different size, impossible to compare");
		}
		int first = 0;
		int second = 0;
		for (int i = 0; i < result1.length; i++) {
			double r1 = result1[i];
			double r2 = result2[i];
			if (findingMinimum && r1 < r2 || !findingMinimum && r1 > r2) {
				first++;
			} else {
				second++;
			}
		}
		return first > second;
	}

}
