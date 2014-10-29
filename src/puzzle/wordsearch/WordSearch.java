package puzzle.wordsearch;

import java.util.List;

public interface WordSearch {

	public abstract List<Word> findAllWords();

	/** initialize search field */
	public abstract void init(Field f);

}