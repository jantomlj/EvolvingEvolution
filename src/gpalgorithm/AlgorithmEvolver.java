package gpalgorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import utility.Parameter;
import interfaces.AlgorithmResultComparator;
import interfaces.Crosser;
import interfaces.EvolutionaryAlgorithm;
import interfaces.FitnessEvaluator;
import interfaces.Instruction;
import interfaces.Mutator;
import interfaces.Selector;

/**
 * Class that evolves {@link EvolutionaryAlgorithm}s.
 * 
 */
public class AlgorithmEvolver {

	/** Size of the population of algorithms */
	private static final int POPULATION_SIZE = 50;

	/** Number of tournamets played before stopping */
	private static final int TOURNAMENTS_NUMBER = 200;

	/** Number of algorithm instructions per population member */
	private static final int INTRUCTIONS_PER_UNIT = 3;


	/**
	 * Probabilities of generating an instruction in the initialization of the instruction
	 * list. Sum of them must be 1
	 */
	private static final float MUTATOR_PROBABILITY = 0.34f;
	@SuppressWarnings("unused")
	private static final float CROSSER_PROBABILITY = 0.33f;
	private static final float SELECTOR_PROBABILITY = 0.33f;


	/** percentage of calculations done when algorithm will adjust itself to final computations */
	private static final float NORMAL_TO_LATER_TIME = 0.85f;

	/** chance that crossover will happen in the normal phase */
	private static final float CROSSOVER_CHANCE_NORMAL = 0.50f;
	/** chance that crossover will happen in the ending phase */
	private static final float CROSSOVER_CHANCE_LATER = 0.15f;


	/** up to this percentage of calculations made tournament will have three competitiors */
	private static final float TIME_FOR_THREE = 0.6f;
	/**
	 * up to this percentage of calculations made, after passing the one above, tournament will
	 * have four competitiors
	 */
	private static final float TIME_FOR_FOUR = 0.8f;
	/**
	 * up to this percentage of calculations made, after passing the one above, tournament will
	 * have five competitiors
	 */
	private static final float TIME_FOR_FIVE = 0.9f;
	/**
	 * up to this percentage of calculations made, after passing the one above, tournament will
	 * have six competitiors
	 */
	@SuppressWarnings("unused")
	private static final float TIME_FOR_SIX = 1f;


	/** Percentage of instructions mutated with one mutation */
	private static final float PERCENTAGE_OF_INSTRUCTIONS_MUTATED = 0.25f;

	/** Chance that a parameter will be mutated */
	private static final float CHANCE_OF_PARAMETER_MUTATION = 0.6f;

	/**
	 * Number by which will parameter value be multiplied with and then added/subtracted from
	 * the value during the mutation
	 */
	private static final float PAR_CONST = 0.3f;

	private static Random rand = new Random();

	/** population of algorithms */
	private EvolutionaryAlgorithm<?>[] population;

	private AlgorithmEvaluator algEvaluator;

	private AlgorithmResultComparator algResultComparator;

	/**
	 * Main method of this class. It takes an {@link EvolutionaryAlgorithm} as input and
	 * evolves it using genetic programming. The best algorithm found in the process of
	 * evolution it returned.
	 * 
	 * @param algorithm
	 *            algorithm that will be evolved
	 * @param evaluators
	 *            {@link FitnessEvaluator} that will be used for evaluating an algorithm. All
	 *            fitness results will equaly be taken into account.
	 * @return the best found algorithm
	 * @throws IOException
	 */
	public EvolutionaryAlgorithm<?> evolve(EvolutionaryAlgorithm<?> algorithm, FitnessEvaluator<?>... evaluators)
			throws IOException {
		this.algEvaluator = new AlgorithmEvaluator(evaluators);
		this.algResultComparator = new AlgorithmResultComparator2(algorithm.findingMinimum());
		algResultComparator.setFindingMinimum(algorithm.findingMinimum());

		initializePopulation(algorithm);
		initializeStartingAlgorithms();

		EvolutionaryAlgorithm<?> best = population[0];
		double[] value = algEvaluator.evaluate(best);
		for (int i = 0; i < TOURNAMENTS_NUMBER; i++) {
			if (i % 1000 == 0) {
				System.out.println("Tisuca: " + i);///////////////////////////////////////////////////////////////////////
			}
			EvolutionaryAlgorithm<?> newOne = playTournament(i);

			double[] newValue = algEvaluator.evaluate(newOne);
			if (algResultComparator.isBetter(newValue, value)) {
				value = newValue;
				best = newOne.copy();
			}
		}

		int bestPosition = findBestAlgorithmInPopulation();

		double[] result1 = algEvaluator.evaluate(best);
		double[] result2 = algEvaluator.evaluate(population[bestPosition]);
		if (algResultComparator.isBetter(result1, result2)) {
			return best;
		}
		return population[bestPosition];
	}

	/**
	 * Method that executes one tournament. It selects some number of participants for the
	 * tournament, and then replaces the worst one with the mutation or the crossover of some
	 * of the better participants.
	 * 
	 * @param time
	 *            number of the tournament
	 * @return the best found algorithm that was participating in the tournament
	 */
	private EvolutionaryAlgorithm<?> playTournament(int time) {
		int k = getK(time); // number of competitors;

		int indexes[] = new int[k];
		for (int i = 0; i < k; i++) {
			indexes[i] = generateRandomPosition(POPULATION_SIZE);
		}

		// first is worst, second is best
		Pair<Integer, Integer> wb = findWorstAndBest(indexes); // gives the positions in the
																// population array

		// gets worst out of first two positions
		for (int i = 0; i < 2; i++) {
			if (indexes[i] == wb.first) {
				int tmp = indexes[k - 1];
				indexes[k - 1] = indexes[i];
				indexes[i] = tmp;
				break;
			}
		}

		executeOperators(time, indexes, wb); // mutation, crossover
		return population[wb.second];
	}

	/**
	 * Method that does mutation or crossover on the winners of the tournament and replaces the
	 * worst found algorithm in the tournament with the result.
	 * 
	 * @param time
	 *            number of the tournament
	 * @param indexes
	 *            of the population memebers that are participating in the tournament
	 * @param wb
	 *            pair that carries pointers to the worst and the best participant
	 */
	private void executeOperators(int time, int[] indexes, Pair<Integer, Integer> wb) {
		float gen = rand.nextFloat();
		if ((float) time / TOURNAMENTS_NUMBER > NORMAL_TO_LATER_TIME) {
			if (gen < CROSSOVER_CHANCE_LATER) {
				population[wb.first] = doCrossover(population[indexes[0]], population[indexes[1]]);
			} else {
				population[wb.first] = doMutation(population[wb.second]);
			}
		} else {
			if (gen < CROSSOVER_CHANCE_NORMAL) {
				population[wb.first] = doCrossover(population[indexes[0]], population[indexes[1]]);
			} else {
				population[wb.first] = doMutation(population[indexes[0]]);
			}
		}
	}

	/**
	 * Mutates the given {@link EvolutionaryAlgorithm} and returns the result. The given
	 * {@link EvolutionaryAlgorithm} is left unchanged.
	 * 
	 * @param algo
	 *            algorithm that will be the base for the mutation
	 * @return the mutated algorithm
	 */
	private EvolutionaryAlgorithm<?> doMutation(EvolutionaryAlgorithm<?> algo) {
		EvolutionaryAlgorithm<?> fresh = algo.copy();
		doInstructionMutations(fresh);
		doParameterMutations(fresh);
		return fresh;
	}

	/**
	 * Method that mutates parameters of the given {@link EvolutionaryAlgorithm}.
	 * 
	 * @param fresh
	 *            algorithm whose parameters will be mutated
	 */
	private void doParameterMutations(EvolutionaryAlgorithm<?> fresh) {
		int parNum = fresh.getParameterNumber();
		for (int i = 0; i < parNum; i++) {
			if (rand.nextDouble() < CHANCE_OF_PARAMETER_MUTATION) {
				Parameter p = fresh.getParameter(i);
				double result = p.getValue() + (rand.nextDouble() > 0.5 ? 1 : -1) * PAR_CONST * p.getValue();
				if (result > p.getMax())
					result = p.getMax();
				if (result < p.getMin())
					result = p.getMin();
				p.setValue(result);
			}
		}
	}

	/**
	 * Method that does instruction mutations on the given {@link EvolutionaryAlgorithm}
	 * 
	 * @param fresh
	 *            algorithm whose instructions will be mutated
	 */
	private void doInstructionMutations(EvolutionaryAlgorithm<?> fresh) {
		int changes = (int) (PERCENTAGE_OF_INSTRUCTIONS_MUTATED * fresh.getInstructionNumber());
		int bigChanges = changes / 2;
		int smallChanges = changes - bigChanges;

		doBigChanges(fresh, bigChanges);
		doSmallChanges(fresh, smallChanges);
	}

	/**
	 * Method that does a number of small mutations. Each mutation changes only one position in
	 * a single instruction.
	 * 
	 * @param fresh
	 *            the algorithm which will be mutated
	 * @param smallChanges
	 *            number of mutations
	 */
	private void doSmallChanges(EvolutionaryAlgorithm<?> fresh, int smallChanges) {
		int popSize = fresh.getPopulationSize();
		int insNum = fresh.getInstructionNumber();
		for (int i = 0; i < smallChanges; i++) {
			int gen = generateRandomPosition(popSize);
			int pos = generateRandomPosition(insNum);

			Instruction ins = fresh.getInstruction(pos);

			if (ins instanceof Selector) {
				Selector s = (Selector) ins;
				int gen2 = generateRandomPosition(3);
				switch (gen2) {
				case 0:
					s.setPosition1(gen);
					break;
				case 1:
					s.setPosition2(gen);
					break;
				default:
					s.setResultPosition(gen);
					break;
				}
			} else if (ins instanceof Crosser) {
				Crosser s = (Crosser) ins;
				int gen2 = generateRandomPosition(3);
				switch (gen2) {
				case 0:
					s.setPosition1(gen);
					break;
				case 1:
					s.setPosition2(gen);
					break;
				default:
					s.setResultPosition(gen);
					break;
				}
			} else {
				Mutator s = (Mutator) ins;
				int gen2 = generateRandomPosition(2);
				switch (gen2) {
				case 0:
					s.setPosition(gen);
					break;
				default:
					s.setResultPosition(gen);
					break;
				}
			}
		}
	}

	/**
	 * Method that does a number of mutations on the given algorithm. Each mutation places a
	 * new instruction in the algorithm, or deletes one.
	 * 
	 * @param fresh
	 *            algorithm that will be mutated
	 * @param bigChanges
	 *            number of mutations
	 */
	private void doBigChanges(EvolutionaryAlgorithm<?> fresh, int bigChanges) {
		for (int i = 0; i < bigChanges; i++) {
			int gen = generateRandomPosition(5);
			int pos = generateRandomPosition(fresh.getInstructionNumber());
			switch (gen) {
			case 0:
				placeInstructionIn(fresh, pos, 0);
				break;
			case 1:
				placeInstructionIn(fresh, pos, 1);
				break;
			case 2:
				placeInstructionIn(fresh, pos, 2);
				break;
			case 3:
				fresh.deleteInstruction(pos);
				break;
			default:
				placeInstructionIn(fresh, fresh.getInstructionNumber(), generateRandomPosition(3));
				break;
			}
		}
	}

	/**
	 * Method that puts a new Instruction in the given algorithm, to a given position in the
	 * list of instructions. Third argument decides which instruction will be put.
	 * 
	 * @param alg
	 *            algorithm to which a new instruction will be put
	 * @param pos
	 *            position in the list of instructions where the new instruction will be put
	 * @param which
	 *            if this is 0 a new {@link Selector} will be put, else if this is 1 a new
	 *            {@link Mutator} will be put. In any other case, a new {@link Crosser} will be
	 *            put.
	 */
	private void placeInstructionIn(EvolutionaryAlgorithm<?> alg, int pos, int which) {
		int popSize = alg.getPopulationSize();
		switch (which) {
		case 0:
			alg.putSelector(pos, generateRandomPosition(popSize), generateRandomPosition(popSize),
					generateRandomPosition(popSize));
			break;
		case 1:
			alg.putMutator(pos, generateRandomPosition(popSize), generateRandomPosition(popSize));
			break;
		default:
			alg.putCrosser(pos, generateRandomPosition(popSize), generateRandomPosition(popSize),
					generateRandomPosition(popSize));
			break;
		}
	}

	/**
	 * This method does a crossover of two given {@link EvolutionaryAlgorithm}s. They are both
	 * left unchanged in the procces and the resulting algorithm is returned.
	 * 
	 * @param algo1
	 * @param algo2
	 * @return the algorithm that was created in a crossover of the given arguments
	 */
	private EvolutionaryAlgorithm<?> doCrossover(EvolutionaryAlgorithm<?> algo1, EvolutionaryAlgorithm<?> algo2) {
		EvolutionaryAlgorithm<?> fresh = algo1.newInstance();
		int crossingPlace = generateRandomPosition(algo1.getInstructionNumber());
		for (int i = 0; i < crossingPlace; i++) {
			fresh.putInstruction(i, algo1.getInstruction(i).copy());
		}
		for (int i = crossingPlace; i < algo2.getInstructionNumber(); i++) {
			fresh.putInstruction(i, algo2.getInstruction(i).copy());
		}
		return fresh;
	}

	/**
	 * @param time
	 *            number that determines the state in which the algorithm is.
	 * @return number depending on the state the algorithm is in. This number should determine
	 *         the number of participants in a tournament.
	 */
	private int getK(int time) {
		if (time < TIME_FOR_THREE * TOURNAMENTS_NUMBER)
			return 3;
		else if (time < TIME_FOR_FOUR * TOURNAMENTS_NUMBER)
			return 4;
		else if (time < TIME_FOR_FIVE * TOURNAMENTS_NUMBER)
			return 5;
		else
			return 6;
	}

	/**
	 * Method that finds the best and the worst algorithm from the array of given pointers to
	 * algorithms. Pair of pointers to them is returned.
	 * 
	 * @param indexes
	 *            array of pointers to algorithms
	 * @return pair where pointer to worst algorithm is in the first place, and pointer to the
	 *         best algorithm is in the second place.
	 */
	private Pair<Integer, Integer> findWorstAndBest(int[] indexes) {
		int worst = 0;
		int best = 0;
		double[] worstResult;
		double[] bestResult;
		worstResult = bestResult = algEvaluator.evaluate(population[indexes[0]]);
		for (int i = 1; i < indexes.length; i++) {
			double[] result = algEvaluator.evaluate(population[indexes[i]]);
			if (algResultComparator.isBetter(result, bestResult)) {
				bestResult = result;
				best = indexes[i];
			}
			if (algResultComparator.isBetter(worstResult, result)) {
				worstResult = result;
				worst = indexes[i];
			}
		}
		return new Pair<Integer, Integer>(worst, best);
	}

	/**
	 * Method that finds the place of the best algorithm in the population.
	 * 
	 * @return pointer to the best algorithm in the population
	 */
	private int findBestAlgorithmInPopulation() {
		double[] bestResult = algEvaluator.evaluate(population[0]);
		int bestPosition = 0;
		for (int i = 1; i < POPULATION_SIZE; i++) {
			double[] result = algEvaluator.evaluate(population[i]);
			if (algResultComparator.isBetter(result, bestResult)) {
				bestResult = result;
				bestPosition = i;
			}
		}
		return bestPosition;
	}


	/**
	 * Method that assignes starting instructions to all the algorithms in the population.
	 */
	private void initializeStartingAlgorithms() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
			initInstructions(population[i]);
		}
	}

	/**
	 * Method that assinges instructions to the given algorithm.
	 * 
	 * @param algo
	 *            algorithm that will get its instructions
	 */
	private void initInstructions(EvolutionaryAlgorithm<?> algo) {
		int popSize = algo.getPopulationSize(); // size of the population of units that are to
												// be evolved
		int num = INTRUCTIONS_PER_UNIT * popSize;
		for (int i = 0; i < num; i++) {
			float gen = rand.nextFloat();
			if (gen < SELECTOR_PROBABILITY) {
				algo.putSelector(i, generateRandomPosition(popSize), generateRandomPosition(popSize),
						generateRandomPosition(popSize));
			} else if (gen < SELECTOR_PROBABILITY + MUTATOR_PROBABILITY) {
				algo.putMutator(i, generateRandomPosition(popSize), generateRandomPosition(popSize));
			} else {
				algo.putCrosser(i, generateRandomPosition(popSize), generateRandomPosition(popSize),
						generateRandomPosition(popSize));
			}
		}
		System.out.println("inits: " + Arrays.toString(algEvaluator.evaluate(algo)));////////////////////////////////////////////////////
	}

	/**
	 * Method that initializes the population of algorithms the same as the given one.
	 * 
	 * @param algo
	 *            the algorithm that will be cloned to obtain a population of algorithms
	 */
	private void initializePopulation(EvolutionaryAlgorithm<?> algo) {
		population = new EvolutionaryAlgorithm<?>[POPULATION_SIZE];
		for (int i = 0; i < POPULATION_SIZE; i++) {
			population[i] = algo.newInstance();
		}
	}

	// generates random position in the population of the given size
	/**
	 * Generates a random integer in the range {0, ..., size-1}
	 * 
	 * @param size
	 *            determines the range
	 * @return random integer in the specified range
	 */
	private int generateRandomPosition(int size) {
		return Math.abs(rand.nextInt()) % size;
	}

}
