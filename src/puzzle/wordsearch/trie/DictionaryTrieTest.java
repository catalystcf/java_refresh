package puzzle.wordsearch.trie;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionaryTrieTest {

	@Test
	public void testSimpleTrieCreation() {
		
		DictionaryTrie dt = new DictionaryTrie();
		String word = "word";
		
		dt.addWord( word );
		
		assertTrue( dt.isWord( word ) );
		
		assertFalse( dt.isWord( "wor"));
		
		assertFalse(dt.isWord( "wordl"));
		
		
	}

	@Test
	public void testMultiWordCreation() {
		
		DictionaryTrie dt = new DictionaryTrie();

		
		dt.addWord( "hello" );
		dt.addWord("world");
		dt.addWord("of");
		dt.addWord("hell");
		
		
		assertTrue( dt.isWord( "world" ) );
		assertTrue( dt.isWord( "hell" ) );
		
		assertEquals( dt.getWordCount() , 4);
		
		
	}
}
