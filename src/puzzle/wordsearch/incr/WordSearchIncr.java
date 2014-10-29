package puzzle.wordsearch.incr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import puzzle.wordsearch.Dictionary;
import puzzle.wordsearch.Field;
import puzzle.wordsearch.PuzzleChar;
import puzzle.wordsearch.Word;
import puzzle.wordsearch.WordSearch;

public class WordSearchIncr implements WordSearch {

	Field field;
	List<Word> partialWords;
	List<Word> foundWords;
	Dictionary dict;
	
	public WordSearchIncr(Dictionary dict) {
		this.dict = dict;
	
	}
	
	@Override
	public void init(Field f) {
		
		this.field = f;
		partialWords = new ArrayList<Word>();
		foundWords = new ArrayList<Word>();
		
	}
	


	void processNextLetter() {
		
	
		PuzzleChar pch = new PuzzleChar(field.currentChar(), field.currentLocation);
		
		
		List<Word> newSegments = new ArrayList<Word>();
		// List of partial words that have been used by prefixing a new letter
		List<Word> partialWordsPrefixed = new ArrayList<Word>();
		List<Word> partialWordsSuffixed = new ArrayList<Word>();
		// Check what new segments we can form by adding a new char to existing segments
		for( Word segment:partialWords)
		{
			// check if the letter is close to the beginning or an end
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


	/**
	 * @see puzzle.wordsearch.incr.WordSearch#findAllWords()
	 */
	@Override
	public List<Word> findAllWords() {
		while(!field.isEnd())
		{
			processNextLetter();
			field.next();
		}
		
		return foundWords;
	}
}
