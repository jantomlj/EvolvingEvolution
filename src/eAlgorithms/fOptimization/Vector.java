package eAlgorithms.fOptimization;

import java.util.Arrays;

/**
 * Class that provides the possibility of dealing with vectors of real numbers.
 */
public class Vector {
	
	private double[] vector;
	
	/**
	 * Initializes a new vector of given size. All values inside are set to 0.
	 * @param n size of the vector
	 */
	public Vector(int n) {
		if (n <=0) {
			throw new IllegalArgumentException("Vector size cannot be smaller than 1");
		}
		vector = new double[n];
	}
	
	/**
	 * Gets the value in the i-th place in the vector. Starting index is 0.
	 * @param i vector index
	 * @return value in the i-th position
	 */
	public double get(int i) {
		return vector[i];
	}
	
	/**
	 * Sets i-th position in the vector to the given value
	 * @param i position
	 * @param value value to be set
	 */
	public void set (int i, double value) {
		if (i < 0) {
			throw new IndexOutOfBoundsException();
		}
		vector[i] = value;
	}
	
	/**
	 * Returns the size of the vector.
	 * @return size
	 */
	public int getSize () {
		return vector.length;
	}
	
	/**
	 * Copies this vector.
	 * @return new instance which has all the same values and size as this vector.
	 */
	public Vector copy() {
		Vector v = new Vector(getSize());
		for (int i = 0; i < getSize(); i++) {
			v.set(i, get(i));
		}
		return v;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < vector.length; i++) {
			if (i == 0) {
				sb.append(vector[i]);
			}
			else {
				sb.append(", " + vector[i]);
			}
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(vector);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (!Arrays.equals(vector, other.vector))
			return false;
		return true;
	}
	
	
}
