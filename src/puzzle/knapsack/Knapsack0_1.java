package puzzle.knapsack;

import java.util.HashMap;



public class Knapsack0_1 {

	int[] values = { 3,4,7,6,4,8, 9, 3, 4, 5 };
	int[] weights = {6,7,8,3,4,5, 6, 4, 3, 5 };
	
	int W = 20;
	int total_calculations;
	
	/** cache of previous calculations
	 * first by item 
	 * then by the remaining weight
	 */
	HashMap<Integer, HashMap<Integer, Integer>> cache = new HashMap<Integer, HashMap<Integer, Integer> >();
	
	
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
		
		// store before we leave
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
