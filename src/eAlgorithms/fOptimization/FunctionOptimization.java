package eAlgorithms.fOptimization;

import interfaces.Crosser;
import interfaces.EvolutionaryAlgorithm;
import interfaces.Mutator;
import interfaces.Selector;

import utility.Parameter;

/**
 * Class that uses genetic algorithm to find an input for which a function reaches it's minimum
 * value.
 */
public class FunctionOptimization extends EvolutionaryAlgorithm<Vector> {

	/** population size */
	public static final int POP_SIZE = 20;
	/** size of the vector that is the input of the function */
	public static final int VECTOR_SIZE = 5;
	/** number of iterations through the algorithm instructions */
	public static final int EVALUATIONS_NUMBER = 200;

	private Parameter sigmaMutation = new Parameter(0.01, 100, 0.75);
	
	private Parameter[] parameters = new Parameter[] {sigmaMutation};
	
	/**
	 * Creates an instance that can execute function optimizarion algorithm.
	 */
	public FunctionOptimization() {
		super(EVALUATIONS_NUMBER, new Evaluators.Function1());
	}
	
	@Override
	public void initializeEmpyPopulationArray() {
		population = new Vector[POP_SIZE];
	}

	/**
	 * Initializes the population as an array of vectors. Every vector is randomly generated.
	 */
	@Override
	public void initializePopulation() {
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
			v.set(i, generateDomainNumber());
		}
		return v;
	}

	@Override
	public boolean findingMinimum() {
		return true;
	}

	@Override
	public Parameter getParameter(int position) {
		if (position >= parameters.length) {
			throw new IndexOutOfBoundsException();
		}
		return parameters[position];
	}

	@Override
	public int getParameterNumber() {
		return parameters.length;
	}
	
	@Override
	public Crosser getCrosser(int i, int j, int k) {
		return new CrosserImpl(i, j, k);
	}
	
	@Override
	public Mutator getMutator(int i, int k) {
		return new MutatorImpl(i, k, getMinDomainNumber(), getMaxDomainNumber(), parameters[0]);
	}
	
	@Override
	public Selector getSelector(int i, int j, int k) {
		return new SelectorImpl(i, j, k);
	}
	
	@Override
	public EvolutionaryAlgorithm<Vector> newInstance() {
		return new FunctionOptimization();
	}
	
}
