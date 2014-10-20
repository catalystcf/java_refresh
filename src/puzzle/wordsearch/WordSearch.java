package puzzle.wordsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordSearch {

	Field field;
	List<Word> partialWords;
	List<Word> foundWords;
	Dictionary dict;
	
	public WordSearch(Field f, Dictionary dict) {
		
		this.field = f;
		
		partialWords = new ArrayList<Word>();
		foundWords = new ArrayList<Word>();
		this.dict = dict;
	}
	


	void processNextLetter() {
		
	
		PuzzleChar pch = new PuzzleChar(field.currentChar(), field.currentLocation);
		System.out.println("Partial words size = " + partialWords.size());
		
		
		List<Word> newSegments = new ArrayList<Word>();
		// List of partial words that habe been used by prefixing a new letter
		List<Word> partialWordsPrefixed = new ArrayList<Word>();
		List<Word> partialWordsSuffixed = new ArrayList<Word>();
		// Check what new segments we can form by adding a new char to existing segments
		for( Word segment:partialWords)
		{
			// check if the letter is close to the beginnng or an end
			if ( segment.begin().isNeighbour(pch) )
			{
				Word newSegment = segment.prePend(pch);
				if (dict.isValidSegment(newSegment))
				{
					newSegments.add(newSegment);
					partialWordsPrefixed.add(segment);
				}
			}
			
			
			
			if ( segment.end().isNeighbour(pch))
			{
				Word newSegment = segment.append(pch);
				if (dict.isValidSegment(newSegment))
				{
					newSegments.add(newSegment);
					partialWordsSuffixed.add(segment);
				}
			}
			
		}
		
		
		// Generate all permutations of prefixed and suffixed segments
		// maybe a new letter joins the two?
		for( Word suffixed:partialWordsSuffixed )
		{
			for(Word prefixed:partialWordsPrefixed)
			{
				if ( !suffixed.sharesAny(prefixed))
				{
					Word newSegment = suffixed.append(pch, prefixed);
					if ( dict.isValidSegment(newSegment) ) 
					{
						newSegments.add(newSegment);
					}	
				}
		
			}
		}
		
		List <Word> newWords = newSegments.stream()
				.filter( ww -> dict.isValidWord(ww) )
				.collect(Collectors.toList());
		
		// add the letter to the word segments
		Word firstLetterWord = new Word(pch);
		
		
		partialWords.add(firstLetterWord);
		partialWords.addAll(newSegments);
		
		foundWords.addAll(newWords);
	}

	
	public static void main( String args[]) throws Exception
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
		
		Dictionary d = new Dictionary();
		d.init();
		
		WordSearch ws = new WordSearch(f, d);
		List <Word> allWords = ws.findAllWords();
		
		allWords.forEach( w -> System.out.println( w.asString() ));
	}

	List<Word> findAllWords() {
		while(!field.isEnd())
		{
			processNextLetter();
			field.next();
		}
		
		return foundWords;
	}
}
