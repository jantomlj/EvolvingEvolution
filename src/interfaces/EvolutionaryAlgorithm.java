package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Parameter;

/**
 * Interface that evolutionary algorithm needs to provide in order for it to be evolved.
 */
public abstract class EvolutionaryAlgorithm<T> {

	private List<Instruction> algorithm;

	/**
	 * This variable must be given to selector, crosser and mutator implementations for the
	 * execute method.
	 */
	protected T[] population;

	private int fitnessEvalNum;

	private FitnessEvaluator<T> evaluator;

	/**
	 * Constructor that initializes the population size and creates an empty list for the
	 * algorithm.
	 * 
	 * @param fitnessEvalNum
	 *            allowed number of fitness evaluations
	 * @param evaluator
	 *            that is used to evaluate the fitness of a single population memeber
	 */
	public EvolutionaryAlgorithm(int fitnessEvalNum, FitnessEvaluator<T> evaluator) {
		this.fitnessEvalNum = fitnessEvalNum;
		this.evaluator = evaluator;
		algorithm = new ArrayList<>();
		initializeEmpyPopulationArray();
	}

	/**
	 * Method that initializes population to an empty array of the correct future population
	 * size.<br>
	 * This method is called only once, during the object creation.
	 */
	public abstract void initializeEmpyPopulationArray();

	/**
	 * Function that fills out the population array with population members. It mustn't create
	 * a new array.<br>
	 * This method is called every time before running the algorithm
	 */
	public abstract void initializePopulation();

	/**
	 * Gets a new Selector that can select from the two units in the population specified by
	 * their indexes. <br>
	 * Generated Selector should select among the units in the population of this object.
	 * 
	 * @param i
	 *            position in the population that selector is going to select
	 * @param j
	 *            position in the population that selector is going to select
	 * @param k
	 *            position in the population where the result of the selection will be stored
	 */
	public abstract Selector getSelector(int i, int j, int k);

	/**
	 * Gets a new Crosser that can do crossover on the two units in the population specified by
	 * their indexes.<br>
	 * Generated Crosser should do crossover among the units in the population of this object.
	 * 
	 * @param i
	 *            position in the population that crosser will use for crossover
	 * @param j
	 *            position in the population that crosser will use for crossover
	 * @param k
	 *            position in the population where the result of the crossover will be stored
	 */
	public abstract Crosser getCrosser(int i, int j, int k);

	/**
	 * Gets a new Mutator that can mutate a unit in the population that is specified by given
	 * index.<br>
	 * Generated Mutator should do mutations among the units in the population of this object.
	 * 
	 * @param i
	 *            position in the population where mutation will occur
	 * @param k
	 *            position in the population where the result of the mutation will be stored
	 */
	public abstract Mutator getMutator(int i, int k);

	/**
	 * Get the parameter at the given position
	 * 
	 * @param position
	 *            index
	 */
	public abstract Parameter getParameter(int position);

	/**
	 * Calculates the number of parameters that algorithms have.
	 * 
	 * @return number of parameters
	 */
	public abstract int getParameterNumber();

	/**
	 * Method that tells whether algorithm is looking for the minimum or the maximum.
	 * 
	 * @return if algorithm is looking for the minimum return true, false otherwise
	 */
	public abstract boolean findingMinimum();

	/**
	 * Method that produces a fresh instance of this object just by creating another one by
	 * calling its contructor with the same arguments.
	 */
	public abstract EvolutionaryAlgorithm<T> newInstance();

	/**
	 * Puts a selector to a given position in the algorithm. <br>
	 * 
	 * @param position
	 *            index in the population
	 * @param i
	 *            position in the population that selector is going to select
	 * @param j
	 *            position in the population that selector is going to select
	 * @param k
	 *            position in the population where the result of the selection will be stored
	 */
	public final void putSelector(int position, int i, int j, int k) {
		Selector s = getSelector(i, j, k);
		putInstruction(position, (Instruction) s);
	}

	/**
	 * Puts a crosser to a given position in the algorithm. <br>
	 * 
	 * @param position
	 *            index in the population
	 * @param i
	 *            position in the population that crosser will use for crossover
	 * @param j
	 *            position in the population that crosser will use for crossover
	 * @param k
	 *            position in the population where the result of the crossover will be stored
	 */
	public final void putCrosser(int position, int i, int j, int k) {
		Crosser s = getCrosser(i, j, k);
		putInstruction(position, (Instruction) s);
	}

	/**
	 * Puts a mutator to a given position in the algorithm. <br>
	 * 
	 * @param position
	 *            index in the population
	 * @param i
	 *            position in the population where mutation will occur
	 * @param k
	 *            position in the population where the result of the mutation will be stored
	 */
	public final void putMutator(int position, int i, int k) {
		Mutator s = getMutator(i, k);
		putInstruction(position, (Instruction) s);
	}

	/**
	 * Gets the instruction from the algorithm.
	 * 
	 * @param index
	 *            of the instruction
	 * @return indexed instruction
	 */
	public final Instruction getInstruction(int index) {
		return algorithm.get(index);
	}

	/**
	 * @return number of instructions in the algorithm
	 */
	public final int getInstructionNumber() {
		return algorithm.size();
	}

	/**
	 * Deletes the instruction from the algorithm.
	 * 
	 * @param index
	 *            of the instruction that will be deleted.
	 */
	public final void deleteInstruction(int index) {
		algorithm.remove(index);
	}

	/**
	 * Puts a new {@link Instruction} in the algorithm.
	 * 
	 * @param index
	 *            of the place where instruction will be placed
	 * @param instruction
	 *            to be added
	 */
	public final void putInstruction(int index, Instruction instruction) {
		if (index >= algorithm.size()) {
			algorithm.add(instruction);
		}
		algorithm.set(index, instruction);
	}

	/**
	 * @return number of allowed fitness evaluations.
	 */
	public final int getFitnessEvalNum() {
		return fitnessEvalNum;
	}

	/**
	 * Sets the number of allowed evaluations of fitness.
	 * 
	 * @param number
	 *            new number of possible fitness evaluations
	 */
	public final void setFitnessEvalNUm(int number) {
		fitnessEvalNum = number;
	}

	/**
	 * Sets new fitness evaluator for the algorithm.
	 * 
	 * @param evaluator
	 *            that will be used for fitness evaluation of population members.
	 */
	@SuppressWarnings("unchecked")
	public final void setFitnessEvaluator(FitnessEvaluator<?> evaluator) {
		if (evaluator == null) {
			throw new IllegalArgumentException("evaluator can not be set to null");
		}
		this.evaluator = (FitnessEvaluator<T>) evaluator;
	}

	/**
	 * Generates random number in the defined domain. Domain is defined by the evaluator that is currently in use.
	 */
	public double generateDomainNumber() {
		return evaluator.generateDomainRandomNumber();
	}
	
	/**
	 * The maximum number of the {@link FitnessEvaluator} domain.
	 */
	public double getMaxDomainNumber() {
		return evaluator.getMax();
	}
	
	/**
	 * The minimum number of the {@link FitnessEvaluator} domain.
	 */
	public double getMinDomainNumber() {
		return evaluator.getMin();
	}
	
	/**
	 * Calculates the size of the population.
	 * 
	 * @return population length
	 */
	public final int getPopulationSize() {
		return population.length;
	}

	/**
	 * Copies the current algorithm (including instructions)
	 * 
	 * @return copy of this algorithm
	 */
	public final EvolutionaryAlgorithm<T> copy() {
		EvolutionaryAlgorithm<T> newOne = newInstance();
		for (int i = 0; i < getInstructionNumber(); i++) {
			newOne.putInstruction(i, getInstruction(i).copy());
		}
		return newOne;
	}

	/**
	 * Function that evaluates the fitness of a single unit in the population
	 * 
	 * @param unit
	 *            which fitness will be calculated
	 * @return fitness of the unit
	 */

	public final double evaluate(T unit) {
		return evaluator.evaluate(unit);
	}

	/**
	 * Method that runs the algorithm and returns the best computed fitness.<br>
	 * Before the call of this method, population must be initialized, as well as the list of
	 * instructions.
	 * 
	 * @return best computed fitness during the execution.
	 */
	@SuppressWarnings("unchecked")
	public final double run() {
		initializePopulation();
		double best = findingMinimum() ? Double.MAX_VALUE : Double.MIN_VALUE;
		int countFitnessEvaluations = 0;
		Map<Integer, Double> hash = new HashMap<>();
		algo: while (true) {
			for (Instruction ins : algorithm) {
				ins.execute(population, (FitnessEvaluator<Object>) evaluator, hash);
				try {
					double result = ins.getResult();
					best = adjustBest(result, best);
				} catch (UnsupportedOperationException ignore) {
				}
				countFitnessEvaluations += ins.getNumOfFitnessEvaluations();
				if (countFitnessEvaluations >= fitnessEvalNum) {
					break algo;
				}
			}
		}
		// evaluates the whole population one more time
		for (int i = 0; i < population.length; i++) {
			double result = evaluate(population[i]);
			best = adjustBest(result, best);
		}
		return best;
	}

	private double adjustBest(double result, double best) {
		if (findingMinimum() && result < best) {
			best = result;
		} else if (!findingMinimum() && result > best) {
			best = result;
		}
		return best;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < getInstructionNumber(); i++) {
			Instruction ins = getInstruction(i);
			if (ins instanceof Selector) {
				Selector s = (Selector) ins;
				sb.append("pop[" + s.getResultPosition() + "] = select(" + s.getPosition1() + ", " + s.getPosition2()
						+ ");\n");
			} else if (ins instanceof Crosser) {
				Crosser s = (Crosser) ins;
				sb.append("pop[" + s.getResultPosition() + "] = crossover(" + s.getPosition1() + ", "
						+ s.getPosition2() + ");\n");
			} else {
				Mutator s = (Mutator) ins;
				sb.append("pop[" + s.getResultPosition() + "] = mutate(" + s.getPosition() + ");\n");
			}
		}
		return sb.toString();
	}
}
