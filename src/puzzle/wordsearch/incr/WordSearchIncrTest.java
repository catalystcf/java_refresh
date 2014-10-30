package puzzle.wordsearch.incr;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import puzzle.wordsearch.Field;
import puzzle.wordsearch.PuzzleChar;
import puzzle.wordsearch.SearchTestUtils;
import puzzle.wordsearch.Word;

public class WordSearchIncrTest {
	
	SearchTestUtils STU;
	// Dictionary d;
	
	@Before
	public void setup() throws IOException
	{
		this.STU = new SearchTestUtils();
	}
	

	@Test
	public void testMovement() {

		char[][] fieldMap = { { 'a', 'b', 'c' , 'd'}, { 'd', 'e', 'f', 'g' },
				{ 'g', 'h', 'i', 'j' } };

		Field f = new Field(fieldMap);
		f.start();

		List<Character> res = new ArrayList<Character>();

		while (!f.isEnd())
		{
			res.add(f.currentChar());
			f.next();
			System.out.println("Current Location" + f.currentLocation);
		
		} 
			

		Character[] expected = { 'a', 'b', 'c', 'd', 'd', 'e', 'f', 'g', 'g', 'h', 'i', 'j' };

		Character actual[] = res.toArray(new Character[0]);
		Arrays.sort(actual);
		assertArrayEquals(expected, actual);

	}
	
	
	/** Test the initial simple case of getting a first letter 
	 * Letter Acquisition 
	 * @throws Exception */
	@Test
	public void testFirstLetterAcquisition() throws Exception {

		char[][] fieldMap = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' },
				{ 'g', 'h', 'i' } };

		Field f = new Field(fieldMap);
		f.start();
		

		WordSearchIncr ws = new WordSearchIncr(STU.getDictionary());
		ws.init(f);
		ws.processNextLetter();
		
		Set<Word> allpartialWords = ws.partialWordsMap.getAllWords();
		
		assertTrue( allpartialWords.size() == 1);
		
		Word firstWord = allpartialWords.iterator().next();
		PuzzleChar firstChar = firstWord.begin();
		PuzzleChar lastChar =  firstWord.end();
		
		assertEquals(1, firstWord.length());
		assertEquals(firstChar, lastChar);
		
		assertEquals('a', firstChar.ch);
		assertEquals(0, firstChar.loc.row);
		assertEquals(0, firstChar.loc.col);
		
		
		// TEST SECOND Letter 
		ws.processNextLetter();
		
	}

	@Test
	public void testAllLetterAcquisition() throws Exception {

		char[][] fieldMap = { { 'a', 'b', 'c' }, { 'd', 'e', 'f' },
				{ 'g', 'h', 'i' } };

		Field f = new Field(fieldMap);
		f.start();
		
		WordSearchIncr ws = new WordSearchIncr(STU.getDictionary());
		ws.init(f);
		
		while(!ws.field.isEnd())
		{
			ws.processNextLetter();
			ws.field.next();
		}
		
		Set<Word> allpartialWords = ws.partialWordsMap.getAllWords();
		
		System.out.println(allpartialWords);
		System.out.println("SIZE = " + allpartialWords.size());
		
		System.out.println(ws.foundWords);
		System.out.println("WORDS FOUND = " + ws.foundWords.size());
	
		
		ws.foundWords.forEach( w -> System.out.println( w.asString() ));
		
		List<String> wordsAsString = Word.convertToStings(ws.foundWords);
		assertTrue( wordsAsString.contains( "bad") );
	}

	@Test
	public void testRectangularMatrix() throws Exception
	{
		/**
		 *   This should have been very simple
		 *   
		 *   t o u l n
		 *   h h a d e
		 *   i s v e b
		 *   m p l r y
		 */    
		char [][] fieldMap = {
		 { 't', 'o', 'u', 'l', 'n' },
		 { 'h', 'h', 'a', 'd', 'e' },
		 { 'i', 's', 'v', 'e', 'b' },
		 { 'm', 'p', 'l', 'r', 'y' }
		};
	
		Field f = new Field(fieldMap);
		f.start();
		
		
		WordSearchIncr ws = new WordSearchIncr(STU.getDictionary());
		ws.init(f);
		
		List <Word> allWords = ws.findAllWords();
		
		List<String>  allWordsAsString = Word.convertToStings(allWords);
		allWords.stream()
				.map( x->x.asString() ).collect(Collectors.toList());
		
		allWords.forEach( w -> System.out.println( w.asString() ));
		
		
		assertTrue( allWordsAsString.contains( "this"));
		assertTrue( allWordsAsString.contains( "should"));
		assertTrue( allWordsAsString.contains( "have"));
		assertTrue( allWordsAsString.contains( "been"));
		assertTrue( allWordsAsString.contains( "very"));
		assertTrue( allWordsAsString.contains( "simple"));
		

	}
	
	@Test
	public void testSimpleSimple() throws Exception
	{
		char [][] fieldMap = {
				 { 'i', 's', 'v', 'e'},
				 { 'm', 'p', 'l' , 'k'}
				};

		String [] expected = { "simple" };
		WordSearchIncr ws = new WordSearchIncr(STU.getDictionary());
		
		SearchTestUtils.runSearch(fieldMap, expected, ws);
	}

	@Test
	public void testSimpleSimpleReverse() throws Exception
	{
		char [][] fieldMap = {
				 { 'e', 'l', 'p', 'm', 'i', 's'},
		};

		String [] expected = { "simple" };
		WordSearchIncr ws = new WordSearchIncr(STU.getDictionary());
		
		SearchTestUtils.runSearch(fieldMap, expected, ws);
		
		
	}

	

	
	
	}
