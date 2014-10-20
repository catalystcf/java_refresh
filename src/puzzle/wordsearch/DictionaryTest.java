package puzzle.wordsearch;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {

	Dictionary d;
	
	@Before
	public void setup() throws IOException
	{
		d = new Dictionary();
		d.init();	
	}
	@Test
	public void test() {
		
		
		String [] pairs = d.charPairs("BLAH").toArray( new String[0]);
		
		String [] expected = { "BL", "LA", "AH" };
		Arrays.sort(expected);
		Arrays.sort(pairs);
		
		assertArrayEquals( pairs, expected);
	}

	/** check that dictionary actually checks for a valid segment*/
	@Test
	public void testIsValidSegment()
	{
		testAWord("for");
		testAWord("bad");
		testAWord("simple");
			
	}
		
	void testAWord(String word)
	{
		List<PuzzleChar> chars = word.chars()
				.mapToObj(x -> new PuzzleChar((char)x,  new Loc(0,0)) )
				.collect( Collectors.toList() );
				
		Word w = new Word(chars);
		
		assertTrue( d.isValidSegment(w) );
		assertTrue( d.isValidWord(w)    );

	};
	
	@Test
	public void testDictionaryContains()
	{

		assertTrue( d.words.contains("for") );
		assertTrue( d.words.contains( "bad") ); 
		assertTrue( d.words.contains( "simple"));
	};
}
