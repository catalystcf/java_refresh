package puzzle.wordsearch.incr;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import puzzle.wordsearch.Loc;
import puzzle.wordsearch.PuzzleChar;
import puzzle.wordsearch.Word;

public class StartAndEndMapTest {

	@Test
	public void testPutSmallWord() {
		
		StartAndEndMap saeMap = new StartAndEndMap();
		Loc start = new Loc(0,0);
		Loc end = new Loc(0,1);
		
		PuzzleChar p1 = new PuzzleChar('s', start);
		PuzzleChar p2 = new PuzzleChar('e', end);
		Word seed = new Word();
		Word segment = seed.append(p1).append(p2);
		
		saeMap.put(segment);
	
		Set<Word> fromStart = saeMap.get(start);
		
		assertEquals(1, fromStart.size());
		assertEquals(segment, fromStart.iterator().next());
		
				
	}
}
