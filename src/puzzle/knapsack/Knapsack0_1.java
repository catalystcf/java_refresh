package puzzle.knapsack;

import java.util.HashMap;


/** solution to the 0/1 Knapsack problem using 
 * memoization
 * 
 * 
 * http://cse.unl.edu/~goddard/Courses/CSCE310J/Lectures/Lecture8-DynamicProgramming.pdf
 * 
 * @author alexar
 *
 */
public class Knapsack0_1 {

	int[] values = { 3,4,7,6,4,8, 9, 3, 4, 5 };
	int[] weights = {6,7,8,3,4,5, 6, 4, 3, 5 };
	int W = 20;
	
	// Examples from the PDF above
	// Elements (weight, benefit):
	// (2,3), (3,4), (4,5), (5,6)
    // int[] weights = {2,3,4,5}; 
	// int[] values = { 3,4,5,6 }; 
	
	// int W = 5;
	int total_calculations;
	
	/** cache of previous calculations
	 * first by item 
	 * then by the remaining weight
	 */
	HashMap<Integer, HashMap<Integer, Integer>> cache = new HashMap<Integer, HashMap<Integer, Integer> >();
	
	/**
	 * Find optimal solution for the remaining weight in the backpack:
	 * * if we already calculated the value, return that value
	 * * Calculate the best knapsack value with and without the item 'i'
	 * * Choose a max of the two (with and without current item)
	 * * store results in case of recursion by current item and remaining weight
	 * 
	 * @param i - Item to consider
	 * @param curr_weight - Remaining weight in the backpack
	 * @return maximum value of the backpack
	 */
	int _recursive_max_values(int i, int curr_weight) {
		
		
		if (i<0) return 0;
		if (curr_weight < 0) return 0;
		
		if ( cache.containsKey(i) && cache.get(i).containsKey(curr_weight))
		{
			System.out.println(" Cached for [" + i + ", " + curr_weight + "]");
			return cache.get(i).get(curr_weight);
		}
		
		total_calculations ++;
		
		
		System.out.println("" + i + " => " + curr_weight);
		
		
		int with_last_item = values[i] + _recursive_max_values(i-1, curr_weight-weights[i]);
		int without_last_item =  _recursive_max_values(i-1, curr_weight);
		
		int optimal_sub_value;
		
		if ( curr_weight < weights[i])
		{
			optimal_sub_value =  without_last_item;
		} else {
			optimal_sub_value = Math.max( with_last_item,  without_last_item);
		}
		
		// store in case it is needed in the future
		if ( !cache.containsKey(i) ) cache.put(i,  new HashMap<Integer, Integer>());
		
		// Comment this line to see calculations without caching.
		cache.get(i).put(curr_weight, optimal_sub_value);
		
		return optimal_sub_value;
	}

	public static void main(String[] args) {
		System.out.println("Start");
		Knapsack0_1 k = new Knapsack0_1();
		System.out.println("MAX Value " + k._recursive_max_values(k.values.length-1, k.W));
		System.out.println("Total Calcs " + k.total_calculations);
	}

}
