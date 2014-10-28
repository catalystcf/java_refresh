package puzzle.wordsearch;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WordTest {

	@Test
	public void testAsString() {
		
		List<PuzzleChar> chars = new ArrayList<PuzzleChar>();
		
		chars.add(new PuzzleChar('a', new Loc(0, 0)));
		Word w = new Word( chars );
		assertEquals( "a", w.asString());
		
		chars.add(new PuzzleChar('b', new Loc(0, 0)));
		w = new Word(chars);
		assertEquals( "ab", w.asString());
		
	}

}
