package puzzle.wordladderviastream;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class FindTransitionTest {

	@Test
	public void testAllWordsForCharTrivial() {
		String a = "a";
		
		Set<String> res = FindTransition.allWordsForChar('b', a);
		
		assertTrue( res.contains("b"));
		assertTrue( !res.contains("a"));
	}

	
	@Test
	public void testGeneratePotentialMatches()
	{
		String a = "a";
		Set<String> matches = FindTransition.generatePotentialMatches(a);
		
		// expect 26 letters
		assertTrue(matches.size() == 26);
		assertTrue( matches.contains("b"));
		assertTrue( matches.contains("z"));
		
	}
	
	
	@Test
	public void testGeneratePotentialMatches2()
	{
		String a = "ab";
		Set<String> matches = FindTransition.generatePotentialMatches(a);
		
		List<String> sortedMatches = new ArrayList<String>(matches);
		Collections.sort(sortedMatches);
		
		// expect 26 letters
		assertTrue( matches.contains("aa"));
		assertTrue( matches.contains("az"));
		assertTrue( matches.contains("zb"));
		
		assertEquals(26+25, matches.size());
			
	}
	@Test
	public void testAllWordsForCharThreeLetterWord() {
		String a = "abc";
		
		String [] expected = { "bbc", "abc", "abb"};
		
		String [] res = FindTransition.allWordsForChar('b', a).toArray( new String[0] ); 
		Arrays.sort(expected);
		Arrays.sort(res); 
		assertArrayEquals( expected, res);
	}
	
	@Test
	public void testAll_oneLetterWords() throws Exception {
		String input = "a";
		String output = "z";
		
		String [] sampleDictionary = { "a", "b",  "z" };
		
		StreamDictionary sd = new StreamDictionary(1);
		sd.filteredWords = new HashSet<String>( Arrays.asList(sampleDictionary));
		
		FindTransition fd = new FindTransition(sd);
		List<String> transitions = fd.findTransitions(input, output);
		
		System.out.println("transitions:" + transitions);
	};
	
	@Test
	public void testAll_oneLetterWordsFullDictionary() throws Exception {
		String input = "a";
		String output = "z";
		
		StreamDictionary sd = new StreamDictionary(input.length());
		sd.init();

		FindTransition fd = new FindTransition(sd);
		List<String> transitions = fd.findTransitions(input, output);
		
		System.out.println("transitions:" + transitions);
		
	
	};
	
	@Test
	public void testTruckToPlane() throws Exception {
		String input = "train";
		String output = "plane";
		
		
		StreamDictionary sd = new StreamDictionary(input.length());
		sd.init();
		
		FindTransition fd = new FindTransition(sd);
		List<String> transitions = fd.findTransitions(input, output);
		
		assertTrue( transitions.get(0) == input);
		assertTrue( transitions.get(transitions.size()-1) == output);
	
	};
	



}
