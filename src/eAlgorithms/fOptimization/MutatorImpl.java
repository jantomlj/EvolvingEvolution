package eAlgorithms.fOptimization;

import java.util.Map;
import java.util.Random;

import utility.Parameter;
import interfaces.FitnessEvaluator;
import interfaces.Instruction;
import interfaces.Mutator;

/**
 * Mutator class that knows how to mutate a member of the population.
 */
public class MutatorImpl implements Mutator {

	/** standard deviation of the gaussian mutation */
	private Parameter sigma;
	
	/** position in the population that will be mutated */
	private int pos;
	
	private int k;
	
	private double min;
	private double max;
	
	private Random rand;
	
	/**
	 * Creates an class instance with given position in the population, and the population
	 * @param pos position in the population that will be the target of the mutation.
	 * @param k position in the population where result will be stored
	 * @param population
	 * @param min minimum value that a single vector element can have
	 * @param max maximum value that a single vector element can have
	 */
	public MutatorImpl(int pos, int k, double min, double max, Parameter sigma) {
		this.pos = pos;
		rand = new Random();
		this.min = min;
		this.max = max;
		this.sigma = sigma;
		this.k = k;
	}

	@Override
	public void execute(Object[] population, FitnessEvaluator<Object> evaluator, Map<Integer, Double> hash) {
		Vector[] population0 = (Vector[]) population;
		for (int i = 0; i < population0[pos].getSize(); i++) {
			double num = population0[pos].get(i) + sigma.getValue() * rand.nextGaussian();
			if (num > max) num = max;
			if (num < min) num = min;
			population0[k].set(i, num);
			hash.remove(k);
		}
	}

	@Override
	public double getResult() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setPosition(int pos) {
		this.pos = pos;
	}
	
	@Override
	public void setResultPosition(int pos) {
		this.k = pos;
	}
	
	@Override
	public int getNumOfFitnessEvaluations() {
		return 0;
	}
	
	@Override
	public Instruction copy() {
		return new MutatorImpl(pos, k, min, max, sigma.copy());
	}

	@Override
	public int getPosition() {
		return pos;
	}
	
	@Override
	public int getResultPosition() {
		return k;
	}
}
