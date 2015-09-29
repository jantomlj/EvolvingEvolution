package simpleGAalgorithm;

import interfaces.FitnessEvaluator;
import eAlgorithms.fOptimization.Evaluators;
import eAlgorithms.fOptimization.Vector;
import utility.Median;

public class SimpleMain {

	private static final int EVALUATIONS_NUMBER = 200;

	public static void main(String[] args) {
		FitnessEvaluator<Vector> e1 = new Evaluators.Function1();
		FitnessEvaluator<Vector> e2 = new Evaluators.Function2();
		FitnessEvaluator<Vector> e3 = new Evaluators.Function6();
		FitnessEvaluator<Vector> e4 = new Evaluators.Auckley();
		FitnessEvaluator<Vector> e5 = new Evaluators.Griewank();
		FitnessEvaluator<Vector> e6 = new Evaluators.Lunacek();
		FitnessEvaluator<Vector> e7 = new Evaluators.ModifiedDoubleSum();
		FitnessEvaluator<Vector> e8 = new Evaluators.Rastrigin();
		
		SimpleGA ga = new SimpleGA(e8);
		System.out.println(median(EVALUATIONS_NUMBER, ga));
	}

	private static double median(int num, SimpleGA algo) {
		double[] results = new double[num];
		for (int i = 0; i < num; i++) {
			results[i] = algo.run();
		}
		return Median.getMedian(results);
	}
}
