package gpalgorithm;

import utility.Median;
import interfaces.EvolutionaryAlgorithm;
import interfaces.FitnessEvaluator;

/**
 * Class that evaluates the fitness of a single {@link EvolutionaryAlgorithm}.
 */
public class AlgorithmEvaluator {

	/** Number of times single algorithm will be run to get its average result value */
	private static final int ALGO_EVALUATION_NUMBER = 200;

	private FitnessEvaluator<?>[] evaluators;

	/**
	 * Create a new instance that will know how to evaluate fitness of an
	 * {@link EvolutionaryAlgorithm} using provided evaluators
	 * 
	 * @param evaluators
	 *            that will be used for algorithm fitness evaluation
	 */
	public AlgorithmEvaluator(FitnessEvaluator<?> ...evaluators) {
		if (evaluators == null) {
			throw new IllegalArgumentException("No evaluator was given");
		}
		this.evaluators = evaluators;
	}

	/**
	 * Evalutes the algorithm fitness that is represented as a array of doubles.
	 * 
	 * @param algorithm
	 *            whose fitness will be evaluated
	 * @return the fitness of the given algorithm
	 */
	public double[] evaluate(EvolutionaryAlgorithm<?> algorithm) {
		double[] results = new double[evaluators.length];

		for (int i = 0; i < results.length; i++) {
			algorithm.setFitnessEvaluator(evaluators[i]);
			results[i] = evaluateAlgorithm(algorithm);
		}
		return results;
	}

	/**
	 * Method that runs the given {@link EvolutionaryAlgorithm} some number of times and
	 * returns the median result value.
	 * 
	 * @param algo
	 *            algorithm that will be evaluated
	 * @return the median value the algorithm returns
	 */
	private double evaluateAlgorithm(EvolutionaryAlgorithm<?> algo) {
		double[] results = new double[ALGO_EVALUATION_NUMBER];
		for (int i = 0; i < ALGO_EVALUATION_NUMBER; i++) {
			results[i] = algo.run();
		}
		return Median.getMedian(results);
	}
}
