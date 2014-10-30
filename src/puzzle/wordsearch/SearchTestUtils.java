package puzzle.wordsearch;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import puzzle.wordsearch.incr.WordSearchIncr;
import puzzle.wordsearch.trie.WordSearchTrie;

public class SearchTestUtils {

	Dictionary d;
	WordSearchIncr wsi;
	WordSearchTrie wst;

	public SearchTestUtils() throws IOException {
		this.d = new Dictionary();
		this.d.init();

		wsi = new WordSearchIncr(d);
		wst = new WordSearchTrie(d);
	};

	static char[][] getRealPuzzleField() {
		// http://www.vangviet.com/amazing-word-search-puzzle/
		String fieldMapAsStrings[] = { "S P S L H O L A W L D R C L R",
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

		char[][] fieldMap = new char[fieldMapAsStrings.length][fieldMapAsStrings.length];

		for (int i = 0; i < fieldMapAsStrings.length; i++) {
			String line = fieldMapAsStrings[i].toLowerCase();
			String[] chars = line.split(" ");
			for (int j = 0; j < chars.length; j++) {
				fieldMap[i][j] = chars[j].charAt(0);
			}
		}

		return fieldMap;

	}

	/**
	 * Strings that a human is expected to find which are subject-align.
	 * Computer will be finding more strings as the rules are different.
	 * 
	 * But this is a very valid subset.
	 * 
	 * @return
	 */
	static String[] getRealPuzzleHumanResults() {
		String[] expected = { "humpback", "whale", "sponge", "harbor", "seal",
				"squid", "hermit", "crab", "urchin", "lobster", "surf", "clam",
				"herring", "gull", "flounder", "moon", "snail", "barnacle",
				"green", "turtle", "sand", "dollar", "eel", "grass",
				"jellyfish", "anemone", "shark", "skate" };

		return expected;
	}

	public static SearchResults runSearch(char[][] fieldMap, String[] expected,
			WordSearch ws) {

		Field f = new Field(fieldMap);
		f.start();

		ws.init(f);

		long start = System.currentTimeMillis();
		List<Word> allWords = ws.findAllWords();
		long end = System.currentTimeMillis();

		System.out.println("Search " + ws + " took " + (end - start) + " ms");

		List<String> allWordsAsString = Word.convertToStings(allWords);
		allWords.stream().map(x -> x.asString()).collect(Collectors.toList());

		for (String exp : expected) {
			if (!allWordsAsString.contains(exp)) {
				System.out.println("NOT FOUND: = [" + exp + "]");
			}
			assertTrue(allWordsAsString.contains(exp));
		}

		System.out.println("Search: found " + allWords.size() + " words");

		SearchResults results = new SearchResults(allWordsAsString, end - start);
		return results;
	}

	public static void main( String [] args ) throws IOException
	{
		SearchTestUtils STU = new SearchTestUtils();
		char [][] fieldMap = getRealPuzzleField();
		String [] humanExpected = getRealPuzzleHumanResults();
		
		// allow for priming
		SearchResults R1_WSI = runSearch(fieldMap, humanExpected, STU.wsi);
		SearchResults R1_WST = runSearch(fieldMap, humanExpected, STU.wst);
		
		
		// match each other
		SearchResults R2_WST = runSearch(fieldMap, R1_WSI.words.toArray(new String[0]), STU.wst);
		SearchResults R2_WSI = runSearch(fieldMap, R1_WST.words.toArray( new String [0]), STU.wsi);
		
		
		System.out.println( "FINAL:");
		System.out.println( "Incremenatal  = " + R2_WSI.timeTaken);
		System.out.println( "Trie Based    = " + R2_WST.timeTaken);
	}

	public Dictionary getDictionary() {

		return this.d;
	}
}
