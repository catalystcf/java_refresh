package puzzle.wordsearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dictionary {

	Set<String> words;
	
	HashMap<Integer, Set<String>> segments;
	
	public Dictionary()
	{
	}
	
	public void init() throws IOException
	{
			
			String homePath = System.getenv("HOMEPATH");
			String fileName =  homePath + File.separator + "words.txt";
			System.out.println("Reading " + fileName);
				
			List<String> allWords = Files.readAllLines(Paths.get(fileName));
			
			System.out.println("Read " + fileName + " with " + allWords.size() + " words");
			
			Stream<String> filteredWordsStream = allWords.stream().filter(x->x.length() >= 3);
			// TODO: lowercase them
			
			Collection<String> s  = filteredWordsStream.map( String::toLowerCase ).collect(Collectors.toCollection(()  -> new HashSet<String>() ) );
			this.words = new HashSet<String>(s);
			this.segments = new HashMap<Integer, Set<String>>();
			
			System.out.println("Filtered to " + this.words.size() + " words");
	}
	
	/**
	 * Produce all char pairs for a given word 
	 * @param word
	 * @return
	 */
	public Set<String> charPairs(String word)
	{
		
		Set <String> pairs = IntStream.range(0,  word.length()-1).
			mapToObj( chI -> word.substring(chI, chI+2)).collect(Collectors.toSet());
		
		
		return pairs;
	}
	
	private Collection<String> substrings(String word, int size) {
		Set <String> pairs = IntStream.range(0,  word.length()-size+1).
				mapToObj( chI -> word.substring(chI, chI+size)).collect(Collectors.toSet());
			
		return pairs;
	}
	
	/**
	 * produce all letter pairs for the given dictionary
	 * @return
	 */
	public Set<String> allPairs()
	{
		// return words.stream().flatMap( word -> this.charPairs(word).stream()).collect(Collectors.toSet());
		return allSubStrings(2);
	}
	
	public Set<String> allSubStrings(int size)
	{
		long ms = System.currentTimeMillis();
		assert(size >=2 );
		Set<String> substr= words.stream().flatMap( word -> this.substrings(word, size).stream()).collect(Collectors.toSet());
		

		long afterMs = System.currentTimeMillis();
		System.out.println("allSubString (" + size + ") found [" + substr.size() + "] in " + (afterMs -ms) + " ms");

		
		
		return substr;
	}
		
	

	
	public static void main(String[] args) throws Exception {
		
		Dictionary d = new Dictionary();
		d.init();
		
		Set<String> allPairs = d.allPairs();
		System.out.println("All Pairs: " + allPairs.size() );
		System.out.println("Theoretical Maximum = " + (26*26) );
		System.out.println("% of possible = " + ( allPairs.size()/26.0/26.0 *100));
	

		Set<String> allTripples = d.allSubStrings(3);
		System.out.println("All Pairs: " + allTripples.size() );
		System.out.println("Theoretical Maximum = " + (26*26*26) );
		System.out.println("% of possible = " + allTripples.size()/26.0/26/26*100);
		
	
	}

	boolean isValidWord(Word ww) {
		String sz = ww.asString();
		return  this.words.contains(sz) ;
	}

	boolean isValidSegment(Word ww) {
		
			assert( ww.length() >= 2);
	
			Integer key = Integer.valueOf( ww.length());
			if (!this.segments.containsKey(key))
			{
				this.segments.put(key,  this.allSubStrings(ww.length()));
			}
			
			return this.segments.get(key).contains(ww.asString());		
	}

}
