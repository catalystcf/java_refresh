package puzzle.wordsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Word {
	List<PuzzleChar> chars;
	String asString = null;
	
	public Word()
	{
		this.chars = new ArrayList<PuzzleChar>();
	};
	
	Word(Word word) 
	{
		this(word.chars);
	}
	
	Word( List<PuzzleChar> pchs)
	{
		// deep copy is not needed as chars are immutable
		this.chars = new ArrayList<PuzzleChar>( pchs );
	}

	

	public Word(PuzzleChar pch) {
		this();
		this.chars.add(pch);
	}

	public PuzzleChar begin()
	{
		return chars.get(0);
	}
	
	public PuzzleChar end()
	{
		
		return chars.get(chars.size() -1);
	}

	public int length() {
		return chars.size();
	}

	/** 
	 * Create a new word that contains
	 * current word + new letter
	 * @param pch
	 * @return
	 */
	 public Word append(PuzzleChar pch) {
		Word w= new Word(this);
		w.chars.add(pch);
		
		return w;
	}

	public Word append(PuzzleChar pch, Word tail) {
		List<PuzzleChar> chars = new ArrayList<PuzzleChar>(this.length() + 1 + tail.length());
		chars.addAll(this.chars);
		chars.add(pch);
		chars.addAll(tail.chars);
	
		return new Word(chars);
	}
 
	 
	public Word prePend(PuzzleChar pch) {
		List<PuzzleChar> chars = new ArrayList<PuzzleChar>(this.chars.size() + 1);
		
		chars.add(pch);
		chars.addAll(this.chars);
		Word w = new Word( chars );
		return w;
	}

	@Override
	public String toString() {
		return "Word [" + chars + "]";
	}

	/** return a string representation of the word */
	public String asString() {
		if ( this.asString == null )
		{
			
		
			StringBuilder sb = this.chars.stream().collect(  StringBuilder::new, 
					(  buf,  wwww) -> buf.append("" + wwww.ch), 
					StringBuilder::append  
					);
			
			this.asString = sb.toString();
		}
		
		return this.asString;
	}

	/**
	 * Convert a list of Words into a list of Strings
	 * @param allWords
	 * @return
	 */
	public static List<String> convertToStings(List<Word> allWords) {
		return allWords.stream()
				.map( x->x.asString() ).collect(Collectors.toList());
	}

	/** 
	 * Checks if this word contains any characters from the 
	 * argument
	 * @param other
	 * @return
	 */
	public boolean sharesAny(Word other) {
		for( PuzzleChar pch:this.chars)
		{
			if (other.contains(pch))
				return true;
		}
		return false;
	}

	public boolean contains(PuzzleChar pch) {
		
		return this.chars.contains(pch);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chars == null) ? 0 : chars.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (chars == null) {
			if (other.chars != null)
				return false;
		} else if (!chars.equals(other.chars))
			return false;
		return true;
	}

	
	
}
