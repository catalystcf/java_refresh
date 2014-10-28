package puzzle.wordsearch;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class WordSearchIncrTest {
	
	Dictionary d;
	
	@Before
	public void setup() throws IOException
	{
		d = new Dictionary();
		d.init();	
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
		

		WordSearch ws = new WordSearch(f,d);
		ws.processNextLetter();
		
		assertTrue( ws.partialWords.size() == 1);
		
		Word firstWord = ws.partialWords.get(0);
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
		
		WordSearch ws = new WordSearch(f,d);
		
		while(!ws.field.isEnd())
		{
			ws.processNextLetter();
			ws.field.next();
		}
		
		System.out.println(ws.partialWords);
		System.out.println("SIZE = " + ws.partialWords.size());
		
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
		
		
		WordSearch ws = new WordSearch(f, d);
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

		runSimpleSimple(fieldMap);
	}

	@Test
	public void testSimpleSimpleReverse() throws Exception
	{
		char [][] fieldMap = {
				 { 'e', 'l', 'p', 'm', 'i', 's'},
		};

		runSimpleSimple(fieldMap);
	}

	
	/**
	 * Run a fieldMap that should result in a word 'simple'
	 * @param fieldMap
	 */
	void runSimpleSimple(char [][] fieldMap)
	{
		String [] expected = { "simple" };
		
		runSearch( fieldMap, expected);
	}
	
	void runSearch( char[][] fieldMap, String [] expected)
	{
				Field f = new Field(fieldMap);
				f.start();
					
				WordSearch ws = new WordSearch(f, d);
				List <Word> allWords = ws.findAllWords();
				
				List<String>  allWordsAsString = Word.convertToStings(allWords);
				allWords.stream()
						.map( x->x.asString() ).collect(Collectors.toList());
				
				allWords.forEach( w -> System.out.println( w.asString() ));
				
				for(String exp:expected)
				{
					if (!allWordsAsString.contains( exp ) )
					{
						System.out.println("NOT FOUND: = [" + exp + "]");
					}
					assertTrue( allWordsAsString.contains( exp ));		
				}
			
	}
	
	@Test
	public void  testRealPuzzle()
	{
		// http://www.vangviet.com/amazing-word-search-puzzle/
String fieldMapAsStrings [] = {
"S P S L H O L A W L D R C L R",
"A U S E A S T A R E T S B O L", 
"H S S H B G T E L C R L R S R", 
"E L A H W K C A B P M U H L T",
"R T R R E D N U O L F A A K K",
"R C G R M R E Y F S R N N R E",
"I A L U O S M M W B F E D A L",
"N U E A O I U I O R I M I H E",
"G R E E N T U R T L E O U S D",
"G C T B S I S L F C O N Q R E",
"U H A N N E R P G C R E S R A",
"L I K S A N D D O L L A R I O",
"L N S L I E L C A N R A B I E",
"S J E L L Y F I S H G F M A T",
"S O C I R R N A R S T E S L N" };
	
	char [][] fieldMap = new char[fieldMapAsStrings.length][fieldMapAsStrings.length];
	
	for(int i=0; i < fieldMapAsStrings.length; i++)
	{
		String line = fieldMapAsStrings[i].toLowerCase();
		String [] chars = line.split(" ");
		for (int j=0; j < chars.length; j++)
		{
			fieldMap[i][j] = chars[j].charAt(0);
		}
	}
	
		String [] expected = { 
				"humpback",
				"whale",
				"sponge",
				"harbor",
				"seal",
				"squid",
				"hermit",
				"crab",
				"urchin",
				"lobster",
				"surf",
				"clam",
				"herring",
				"gull",
				"flounder",
				"moon",
				"snail",
				"barnacle",
				"green",
				"turtle",
				"sand",
				"dollar",
				"eel",
				"grass",
				"jellyfish",
				"anemone",
				"shark",
				"skate"
		};
		runSearch(fieldMap, expected );
	}
}
