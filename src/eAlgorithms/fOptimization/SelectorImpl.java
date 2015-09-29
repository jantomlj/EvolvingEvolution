package eAlgorithms.fOptimization;

import java.util.Map;

import interfaces.FitnessEvaluator;
import interfaces.Instruction;
import interfaces.Selector;

/**
 * Selector class that can evaluate members of the population, and select the better.
 */
public class SelectorImpl implements Selector {

	/** positions in the population where it selects from */
	private int i;
	private int j;
	
	private int k;

	/** Minimum value that was caught in the last computation */
	private double lastResult;
	
	private int countEvals;

	/**
	 * Creates a class instance with two given positions, {@link FitnessEvaluator} and the
	 * population.
	 * @param i position in the population which will be evaluated
	 * @param j position in the population which will be evaulated
	 * @param k position in the population where result will be stored
	 * @param evaluator that is used for population evaluation
	 */
	public SelectorImpl(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
		countEvals = 0;
	}

	@Override
	public void execute(Object[] population, FitnessEvaluator<Object> evaluator, Map<Integer, Double> hash) {
		countEvals = 0;
		Vector[] population0 = (Vector[]) population;
		double result1 = getFitness(i, hash, population0, evaluator);
		double result2 = getFitness(j, hash, population0, evaluator);
		
		if (result1 < result2) {
			population0[k] = population0[i].copy();
			lastResult = result1;
		} else {
			population0[k] = population0[j].copy();
			lastResult = result2;
		}
		hash.remove(k);
	}
	
	private double getFitness(int i, Map<Integer, Double> hash, Vector[] population, FitnessEvaluator<Object> evaluator) {
		double result;
		if (hash.containsKey(i)) {
			result = hash.get(i);
		} else {
			result = evaluator.evaluate(population[i]);
			hash.put(i, result);
			countEvals++;
		}
		return result;
	}

	@Override
	public double getResult() {
		return lastResult;
	}

	@Override
	public void setPosition1(int pos) {
		this.i = pos;
	}
	
	@Override
	public void setPosition2(int pos) {
		this.j = pos;
	}
	
	@Override
	public void setResultPosition(int pos) {
		this.k = pos;
	}
	
	@Override
	public int getNumOfFitnessEvaluations() {
		return countEvals;
	}
	
	@Override
	public Instruction copy() {
		return new SelectorImpl(i, j, k);
	}
	
	@Override
	public int getPosition1() {
		return i;
	}
	
	@Override
	public int getPosition2() {
		return j;
	}
	
	@Override
	public int getResultPosition() {
		return k;
	}
}
