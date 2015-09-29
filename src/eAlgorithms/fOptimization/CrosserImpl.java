package eAlgorithms.fOptimization;

import java.util.Map;

import interfaces.Crosser;
import interfaces.FitnessEvaluator;
import interfaces.Instruction;

/**
 * Crosser class that knows how to do a crossover over two members of the population
 */
public class CrosserImpl implements Crosser{

	/** places in the population on which the crossover is made */
	private int i;
	private int j;

	private int k;
	
	/**
	 * Creates an class instance with two given positions and the population
	 * @param i first position in the population that will take part in the crossover
	 * @param j second position in the population that will take part in the crossover
	 * @param k position in the population where result will be stored
	 */
	public CrosserImpl(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	@Override
	public void execute(Object[] population, FitnessEvaluator<Object> evaluator, Map<Integer, Double> hash) {
		Vector[] population0 = (Vector[]) population;
		int length = population0[i].getSize();
		Vector v1 = new Vector(length);
		for (int k = 0; k < length; k++) {
			v1.set(k, (population0[i].get(k) + population0[j].get(k)) / 2.0);
		}
		population[k] = v1;
		hash.remove(k);
	}

	
	@Override
	public double getResult() {
		throw new UnsupportedOperationException();
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
		return 0;
	}
	
	@Override
	public Instruction copy() {
		return new CrosserImpl(i, j, k);
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
