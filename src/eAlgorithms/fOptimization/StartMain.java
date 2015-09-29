package eAlgorithms.fOptimization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import interfaces.EvolutionaryAlgorithm;
import interfaces.FitnessEvaluator;
import gpalgorithm.AlgorithmEvaluator;
import gpalgorithm.AlgorithmEvolver;

public class StartMain {

	public static void main(String[] args) throws IOException {
		FunctionOptimization f = new FunctionOptimization();
		
		AlgorithmEvolver evolver = new AlgorithmEvolver();
		
		FitnessEvaluator<Vector> e1 = new Evaluators.Function1();
		FitnessEvaluator<Vector> e2 = new Evaluators.Function2();
		FitnessEvaluator<Vector> e3 = new Evaluators.Function6();
		FitnessEvaluator<Vector> e4 = new Evaluators.Auckley();
		FitnessEvaluator<Vector> e5 = new Evaluators.Griewank();
		FitnessEvaluator<Vector> e6 = new Evaluators.Lunacek();
		FitnessEvaluator<Vector> e7 = new Evaluators.ModifiedDoubleSum();
		FitnessEvaluator<Vector> e8 = new Evaluators.Rastrigin();
		
		EvolutionaryAlgorithm<?> alg = evolver.evolve(f, e1, e2, e3, e4, e5, e7, e8);
		
		
		Files.write(Paths.get("log.txt"), "".getBytes());
		Files.write(Paths.get("log.txt"), alg.toString().getBytes() , StandardOpenOption.APPEND);
		Files.write(Paths.get("log.txt"), Double.toString(alg.getParameter(0).getValue()).getBytes() , StandardOpenOption.APPEND);
		
		AlgorithmEvaluator eval = new AlgorithmEvaluator(e1, e2, e3, e4, e5, e7, e8, e6);
		System.out.println(Arrays.toString(eval.evaluate(alg)));
	}
	
}
