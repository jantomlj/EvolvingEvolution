package simpleGAalgorithm;

import java.util.Random;

import eAlgorithms.fOptimization.Vector;
import gpalgorithm.Pair;
import interfaces.FitnessEvaluator;

public class SimpleGA {

	/** population size */
	public static final int POP_SIZE = 20;
	/** size of the vector that is the input of the function */
	public static final int VECTOR_SIZE = 5;
	/** number of iterations through the algorithm instructions */
	public static final int EVALUATIONS_NUMBER = 200;

	/** number of participants in the tournament */
	public static final int TOURNAMENT_SIZE = 3;
	
	/** sigma parameter for gaussian mutation */
	public static final double SIGMA = 0.75;

	private Random rand;

	private FitnessEvaluator<Vector> evaluator;

	private Vector[] population;
	
	public SimpleGA(FitnessEvaluator<Vector> evaluator) {
		this.evaluator = evaluator;
	}

	// fitness evaluator and number of allowed fitness evaluations during the executaion of
	// the algorithm
	public double run() {
		rand = new Random();
		initializePopulation();

		double result = Double.MAX_VALUE;
		for (int i = 0; i < EVALUATIONS_NUMBER; i += TOURNAMENT_SIZE) {
			double newResult = playTournament();
			result = newResult < result ? newResult : result;
		}

		for (int i = 0; i < POP_SIZE; i++) {
			double newResult = evaluator.evaluate(population[i]);
			result = newResult < result ? newResult : result;
		}
		return result;
	}

	private double playTournament() {
		int[] indexes = new int[TOURNAMENT_SIZE];
		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = generateRandomPosition(POP_SIZE);
		}
		
		// position of the worst and result of the best
		Pair<Integer, Double> wb = findWorstAndBest(indexes);
		
		for (int i = 0; i < 2; i++) {
			if (indexes[i] == wb.first) {
				int tmp = indexes[indexes.length - 1];
				indexes[indexes.length - 1] = indexes[i];
				indexes[i] = tmp;
				break;
			}
		}
		
		population[wb.first] = mutate(crossover(population[indexes[0]], population[indexes[1]]));
		return wb.second;
	}
	
	private Pair<Integer, Double> findWorstAndBest(int[] indexes) {
		double best = Double.MAX_VALUE;
		double worst = Double.MIN_VALUE;
		int worstPos = -1;
		for (int i = 0; i < indexes.length; i++) {
			double num = evaluator.evaluate(population[indexes[i]]);
			if (num < best) best = num;
			if (num > worst) {
				worst = num;
				worstPos = indexes[i];
			}
		}
		return new Pair<Integer, Double>(worstPos, best);
	}

	private Vector crossover (Vector one, Vector two) {
		Vector newOne = new Vector(one.getSize());
		for (int i = 0; i < one.getSize(); i++) {
			newOne.set(i, 0.5 * (one.get(i) + two.get(i)) );
		}	
		return newOne;
	}
	
	private Vector mutate (Vector one) {
		for (int i = 0; i < one.getSize(); i++) {
			double num = one.get(i) + SIGMA * rand.nextGaussian();
			if (num > evaluator.getMax()) num = evaluator.getMax();
			if (num < evaluator.getMin()) num = evaluator.getMin();
			one.set(i, num);
		}
		return one;
	}
	
	/**
	 * Initializes the population as an array of vectors. Every vector is randomly generated.
	 */
	private void initializePopulation() {
		population = new Vector[POP_SIZE];
		for (int i = 0; i < population.length; i++) {
			population[i] = initializeRandomVector();
		}
	}

	/**
	 * Initializes a vector that consists of random values.
	 */
	private Vector initializeRandomVector() {
		Vector v = new Vector(VECTOR_SIZE);
		for (int i = 0; i < v.getSize(); i++) {
			v.set(i, generateDomainRandomNumber());
		}
		return v;
	}

	/**
	 * Function that returns a random number within the defined domain.
	 * 
	 * @return random number
	 */
	private double generateDomainRandomNumber() {
		double num = Math.abs(rand.nextInt() * rand.nextDouble()) % (evaluator.getMax() - evaluator.getMin());
		return num + evaluator.getMin();
	}

	private int generateRandomPosition(int size) {
		return Math.abs(rand.nextInt()) % size;
	}

}
