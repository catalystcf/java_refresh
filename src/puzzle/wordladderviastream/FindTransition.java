package puzzle.wordladderviastream;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Goal of this puzzle is to find if there is a way
 * to transition from one dictionary word to another, changing
 * one letter a time.
 *  Example -> PLANE -> TRUCK
 *  
 *  Proposed Logic:
 *  * Restrict Dictionary to the words with the same number of letters
 *    as the target 
 *  * Stash the words into a hashmap 
 *  * Starting with the first word, find all word combinations that are off by one letter
 *  * Find all matches in the dictionary
 *  * Continue until the final word is found or no more searches 
 * @author Alex Ar
 *
 */
public class FindTransition {
	
	StreamDictionary sd;
	
	public FindTransition( StreamDictionary sd ) 
	{
		this.sd = sd;
	}
	
	public List<String> findTransitions(String input, String output) throws Exception
	{
		assert (input.length() == output.length());
			
		LinkedList<String> l = new LinkedList<String>();
	
		
		List<String> allTransitions = findNextTransition(input, output, l );
		
		if ( allTransitions.size() == 0 )
		{
			System.out.println("No transition from [" + input + "] to [" + output + "]");
			return null;
		}
		
		return allTransitions;
		
	}
	
	List<String> findNextTransition(String startWord, String desiredWord, List<String> pathSoFar)
	{
		List<String> newPathSoFar = new LinkedList<String>(pathSoFar);
		newPathSoFar.add(startWord);
		
		if ( desiredWord.equals(startWord) )
		{
			System.out.println("FOUND: " + newPathSoFar);
			return newPathSoFar;
		};
		
		// find all words that can match
		Set<String> potentialMatches = generatePotentialMatches(startWord);
		Set<String> nextMatches = filterByDict(potentialMatches, sd, newPathSoFar);
		if ( nextMatches.contains(desiredWord))
			return( findNextTransition(desiredWord, desiredWord, newPathSoFar) );
		
		
        for(String nextMatch : nextMatches)
        {
        	List<String> res = findNextTransition(nextMatch, desiredWord, newPathSoFar);
        	if ( res != null)
        		return res;
        }
		
		return null;
		
	}
	
	/**
	 * return a list of matches that is in the dictionary
	 * and is not in the list
	 * @param potentialMatches
	 * @param sd
	 * @return
	 */
	Set<String> filterByDict(Set<String> potentialMatches, StreamDictionary sd, List<String> pathSoFar ) 
	{
		
		Set<String> wordMatches = potentialMatches.stream().filter( (String madeUpWord)->!pathSoFar.contains(madeUpWord) && sd.contains(madeUpWord) ).collect(Collectors.toSet());
		return wordMatches;
	}

/**
 * Create all words with a given char in the given position
 * @param ch
 * @param input
 */
static Set<String> allWordsForChar(char ch, String input)
{
	return IntStream.range(0,  input.length() )
			.mapToObj( i -> input.substring(0,i) + ch + input.substring(i+1) )
			.collect(Collectors.toCollection(TreeSet<String>::new) );
			
};

/**
 * Generate all permutations of the word with one letter replaced.
 * @param input
 * @return
 */
static Set<String> generatePotentialMatches( String input)
{	
	
	Set<String> oneWordList = IntStream.rangeClosed('a',  'z')
			.mapToObj( ch->allWordsForChar((char)ch, input ) )
			.flatMap(ll -> ll.stream() )
			.collect(Collectors.toSet());
				
	return oneWordList;
}

	/*
	private List<String> flatten( List<List<String>> input)
	{
		List<String> list = input.stream().flatMap( subList -> subList.stream()).collect(Collectors.toList());
		return list;
	}
	*/
	public static void main(String args[]) throws Exception
	{
		String input = "more";
		String output = "less";
		
		StreamDictionary sd = new StreamDictionary(input.length());
		sd.init();
		
		FindTransition fd = new FindTransition(sd);
		List<String> transitions = fd.findTransitions(input, output);
		
		System.out.println("transitions:" + transitions);
	};
	
	
}
