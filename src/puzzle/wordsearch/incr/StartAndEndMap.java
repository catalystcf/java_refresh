package puzzle.wordsearch.incr;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




import java.util.stream.Collectors;
import java.util.stream.Stream;

import puzzle.wordsearch.Loc;
import puzzle.wordsearch.PuzzleChar;
import puzzle.wordsearch.Word;

public class StartAndEndMap {
	
	Map<Loc, Set<Word>> byStartMap = new HashMap<Loc, Set<Word>>();
	Map<Loc, Set<Word>> byEndMap =new HashMap<Loc, Set<Word>>();
	
	
	/**
	 * Add a segment to the word
	 * @param ref
	 * @param segment
	 */
	private void put(Map<Loc, Set<Word>> ref, Loc loc, Word segment)
	{
		Set<Word> segmentsByLocation = ref.get(loc);
		if ( segmentsByLocation == null)
		{
			segmentsByLocation = new HashSet<Word>();
			ref.put(loc, segmentsByLocation);
			
		}
		
		segmentsByLocation.add(segment);
		
		
	}
	
	void put(Word segment)
	{
		put( byStartMap, segment.begin().loc, segment);
		
	
		put( byEndMap, segment.end().loc, segment);
	}
	
	public Set<Word> get(Loc start)
	{
		Set<Word> startSet = byStartMap.get(start);
		Set<Word> endSet = byEndMap.get(start);
		
		if ( startSet != null)
			startSet = new HashSet<Word>(startSet);
		else
			return new HashSet<Word>(endSet);
		
		if ( endSet != null )
			startSet.addAll(endSet);
		
		
		
		return startSet;
	}
	
	public Set<Word> getAllWords()
	{
		Set<Word> set = Stream.of( byStartMap.values(), byEndMap.values()).flatMap( x->x.stream()).flatMap(x->x.stream()).collect(Collectors.toSet());
		return set;
	}

	public Set<Word>  getByStart(Loc loc) {
		return byStartMap.get(loc);
	}

	Set<Word> getByEnd(Loc loc) {
		return byEndMap.get(loc);
	}

	public void addAll(List<Word> newSegments) {
		
		for(Word segment:newSegments)
		{
			put(segment);
		}
		
	}

	
}
