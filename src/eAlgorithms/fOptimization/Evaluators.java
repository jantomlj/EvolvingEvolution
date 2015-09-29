package eAlgorithms.fOptimization;

import java.util.Random;

import interfaces.FitnessEvaluator;

/**
 * Class that stores different function evaluators.
 */
public class Evaluators {

	public static Random rand = new Random();
	
	/**
	 * {@link FitnessEvaluator} of the {@link Vector}. Function for evaluation is: <br>
	 * <code> sum (i to n) of i * x[i]^2 </code>
	 */
	public static class Function1 extends FitnessEvaluator<Vector> {

		public Function1() {
			max = 10;
			min = -10;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum = 0;
			for (int i = 0; i < component.getSize(); i++) {
				sum += i * component.get(i) * component.get(i);
			}
			return sum;
		}
	}

	/**
	 * {@link FitnessEvaluator} of the {@link Vector}. Function for evaluation is: <br>
	 * <code> sum (i to n) of x[i]^2 </code>
	 */
	public static class Function2 extends FitnessEvaluator<Vector> {

		public Function2() {
			max = 100;
			min = -100;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum = 0;
			for (int i = 0; i < component.getSize(); i++) {
				sum += component.get(i) * component.get(i);
			}
			return sum;
		}
	}

	/**
	 * {@link FitnessEvaluator} of {@link Vector}. Function for evaluation is: <br>
	 * <code> sum (i to n-1) of 100*(x[i+1] - x[i]^2)^2 + (1 - x[i])^2 </code>
	 */
	public static class Function6 extends FitnessEvaluator<Vector> {

		public Function6() {
			max = 30;
			min = -30;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum = 0;
			for (int i = 0; i < component.getSize() - 1; i++) {
				double x2 = component.get(i + 1);
				double x1 = component.get(i);
				double a = (x2 - x1 * x1) * (x2 - x1 * x1);
				double b = (1 - x1) * (1 - x1);
				sum += 100 * a + b;
			}
			return sum;
		}
	}
	

	/**
	 * Evaluates fitness using Ackley's benchmark function
	 */
	public static class Auckley extends FitnessEvaluator<Vector> {
		
		public Auckley() {
			max = 32;
			min = -32;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum1 = 0.0;
            double sum2 = 0.0;

            for (int i = 0 ; i < component.getSize() ; i ++) {
                    sum1 += (component.get(i) * component.get(i));
                    sum2 += (Math.cos(2*Math.PI*component.get(i)));
            }

            return (-20.0 * Math.exp(-0.2 * Math.sqrt(sum1 / ((double )component.getSize()))) - 
                            Math.exp(sum2 / ((double )component.getSize())) + 20.0 + Math.E);
		}
		
	}
	
	/**
	 * Evaluates fitness using Griewank benchmark function
	 */
	public static class Griewank extends FitnessEvaluator<Vector> {
	
		public Griewank() {
			max = 512;
			min = -512;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum = 0.0;
            double product = 1.0;

            for (int i = 0 ; i < component.getSize() ; i ++) {
                    sum += ((component.get(i) * component.get(i)) / 4000.0);
                    product *= Math.cos(component.get(i) / Math.sqrt(i+1));
            }

            return (sum - product + 1.0);
		}
		
	}
	
	/**
	 * Evaluates fitness using Lunacek benchmark function
	 */
	public static class Lunacek extends FitnessEvaluator<Vector> {
	
		public Lunacek() {
			max = 100;
			min = -100;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum1 = 0.0, sum2 = 0.0, sum3 = 0.0;
	        double u1, u2, s, d;

	        d = 1;
	        s = 1 - (1/(2*Math.sqrt(component.getSize()+20)-8.2));
	        u1 = 2.5;
	        u2 = - Math.sqrt((Math.pow(u1, 2)-d)/s);

	        for(int i = 0; i < component.getSize(); i++) {
	            sum1 += Math.pow(component.get(i) - u1, 2);
	            sum2 += Math.pow(component.get(i) - u2, 2);
	            sum3 += (1 - Math.cos(2 * Math.PI * (component.get(i) - u1)));
	        }

	        sum2 = d * component.getSize() + s * sum2;

	        return Math.min(sum1, sum2) + 10 * sum3;
		}
		
	}
	

	/**
	 * Evaluates fitness using modified double sum function.
	 */
	public static class ModifiedDoubleSum extends FitnessEvaluator<Vector> {
		
		public ModifiedDoubleSum() {
			max = 10;
			min = -10;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum=0;
			for (int i = 0; i < component.getSize(); i++) {
				for (int j = 0; j < i; j++) {
						double f = component.get(j) - (j+1);
						sum += f*f;
				}
			}
			return sum;
		}
	}
	
	
	/**
	 * Evaluates fitness using Rastrigin benchmark function.
	 */
	public static class Rastrigin extends FitnessEvaluator<Vector> {
		
		public Rastrigin() {
			max = 5;
			min = -5;
		}
		
		@Override
		public double evaluate(Vector component) {
			double sum = 0.0;
            for (int i = 0; i < component.getSize(); i++) {
                    double xi = component.get(i);
                    sum += (xi * xi) - (10.0 * Math.cos(2.0 * Math.PI * xi));
            }
            return (10.0 * component.getSize()) + sum;
		}
	}
	
}
