package puzzle.wordsearch;

import java.util.List;

public class SearchResults {
	List<String> words;
	Long timeTaken;
	
	public SearchResults(List<String> allWordsAsString, long timeTaken) {
		
		this.words = allWordsAsString;
		this.timeTaken = timeTaken;
	}

}
