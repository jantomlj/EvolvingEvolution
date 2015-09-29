package utility;

public class Median {
	
	// simple recursive partitioning median
	/**
	 * Returns the median of the given array of numbers. If the number of elements is even, from the two
	 * middle elements the bigger is taken.
	 * @param numbers array in which the medain will be found
	 * @return median of the array 
	 */
	public static double getMedian (double[] numbers) {
		return getRanked(numbers, numbers.length / 2, 0, numbers.length - 1);
	}
	
	private static double getRanked (double[] numbers, int ranked, int l, int r) {
		if (l == r) return numbers[ranked];
		double pivot = numbers[l];
		int j = l;
		for (int i = j+1; i <= r; i++) {
			if (numbers[i] <= pivot) {
				swap(numbers, i, ++j);
			}
		}
		swap(numbers, j, l);
		if (ranked == j) {
			return pivot;
		}
		if (ranked > j) {
			return getRanked(numbers, ranked, j+1, r);
		}
		return getRanked(numbers, ranked, l, j-1);
	}
		
	private static void swap (double[] numbers, int i, int j) {
		double temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}

}
