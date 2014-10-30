package puzzle.wordsearch.trie;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import puzzle.wordsearch.Dictionary;
import puzzle.wordsearch.Field;
import puzzle.wordsearch.Word;

public class WordSearchTrieTest {
    Dictionary d;
    
    @Before
    public void setup() throws IOException
    {
    	d = new Dictionary();
    	d.init();
    }
	
	@Test
	public void testVerySimple() throws Exception
	{
		char [][] fieldMap = {
				 { 'a', 'a' },
				 { 'i', 'l' }
				};

		String [] expected = { "aal" };
		
		runSearch( fieldMap, expected);
	}
	
	@Test
	public void testSimpleSimple() throws Exception
	{
		char [][] fieldMap = {
				 { 's', 'i', 'm', 'p'},
				 { 'x', 'x', 'e' , 'l'}
				};

		runSimpleSimple(fieldMap);
	}
	
	
	void runSimpleSimple(char [][] fieldMap) throws Exception
	{
		String [] expected = { "simple" };
		
		runSearch( fieldMap, expected);
	}
	
	
	void runSearch( char[][] fieldMap, String [] expected) throws Exception
	{
				Field f = new Field(fieldMap);
				f.start();
			
				WordSearchTrie ws = new WordSearchTrie(d);
				ws.init(f);
				
				// Just in case, check that expected words are in the dictionary
				for( String expectedW: expected)
				{
					if ( ! ws.dt.isWord(expectedW) )
						fail( "Pre-condition failed: [" + expectedW + "] was not found in dictionary trie");
				}
				
				
				List <Word> allWords = ws.findAllWords();
				
				List<String>  allWordsAsString = Word.convertToStings(allWords);
				allWords.stream()
						.map( x->x.asString() ).collect(Collectors.toList());
				
			//	allWords.forEach( w -> System.out.println( w.asString() ));
				
				for(String exp:expected)
				{
					if (!allWordsAsString.contains( exp ) )
					{
						System.out.println("NOT FOUND: = [" + exp + "]");
					}
					assertTrue( allWordsAsString.contains( exp ));		
				}
				
				System.out.println( "RunSearch: found " + allWords.size() + " words");
			
	}
	
	@Test
	public void test() {

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
		
	}

}
