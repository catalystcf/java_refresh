package puzzle.wordsearch.trie;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import puzzle.wordsearch.Dictionary;
import puzzle.wordsearch.Field;
import puzzle.wordsearch.PuzzleChar;
import puzzle.wordsearch.Word;
import puzzle.wordsearch.WordSearch;
import puzzle.wordsearch.trie.DictionaryTrie.TreeNode;

public class WordSearchTrie implements WordSearch {

	Dictionary d;
	DictionaryTrie dt;
	Field field;
	List<Word> foundWords;
	
	public WordSearchTrie( Dictionary d) {
		this.d = d;
		DictionaryTrie trie = new DictionaryTrie();
		
		for( String word:d.getAllWords())
		{
			//ignore hyphenated words
			if ( word.contains("-"))
				continue;
			
			trie.addWord(word);
		};
		this.dt = trie;
		
	}


	public void init( Field f )
	{
		this.field = f;
		foundWords = new ArrayList<Word>();
	}

	@Override
	public List<Word> findAllWords() {
		while(!field.isEnd())
		{
			processNextLetter();
			field.next();
		}
		
		return foundWords;
	}
	
	/**
	 * For each letter, do surround search
	 * and see if a next letter can be advanced.
	 */
	void processNextLetter() {
		PuzzleChar firstLetter = new PuzzleChar(field.currentChar(), field.currentLocation);
		Word w = new Word();
			
		processNextLetter( dt.root, w, firstLetter);
		
		
	};
	
	void processNextLetter( TreeNode  node, Word w, PuzzleChar nextChar )
	{
		TreeNode nextNode = node.get(nextChar.ch);
		if ( nextNode == null) return;
		
		Word word = w.append(nextChar);
		if ( nextNode.isWord)
		{
			addAndCheck( word );
		 //	foundWords.add(word);
		}
	
		List<PuzzleChar> neighbours = field.getNeighbours( nextChar );
		
		for( PuzzleChar neighbour : neighbours)
		{
			if ( !word.contains(neighbour))
			{
				processNextLetter( nextNode, word, neighbour );
			}
		}
	}
	
	/**
	 * Add a word to the found words collection
	 * throw an exception if the word has been previously added
	 **/
	private void addAndCheck(Word word) {
		if ( foundWords.contains(word) )
			throw( new RuntimeException( "Found duplicate word " + word ));
		
		foundWords.add(word);
	}


	public static void main(String[] args) throws IOException {
		
		
		
	}
	


}
