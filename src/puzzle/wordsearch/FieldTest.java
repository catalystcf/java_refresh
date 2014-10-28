package puzzle.wordsearch;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

import org.junit.Test;

public class FieldTest {

	

	@Test
	public void testSimpleSimple() throws Exception
	{
		char [][] fieldMap = {
				 { 's', 'i', 'm', 'p'},
				 { 'x', 'x', 'e' , 'l'}
				};

		Field f = new Field(fieldMap);
		f.start();
		f.currentLocation.col = 1;
		f.currentLocation.row = 0;
//		assertEquals( 'i', f.currentChar());
	
		PuzzleChar pzch = new PuzzleChar(f.currentChar(),  f.currentLocation); 
		List<PuzzleChar> neighbours = f.getNeighbours(pzch);
		
	
		
		Character [] chars = (Character[]) neighbours.stream().map( pz->new Character(pz.ch) ).toArray(x -> new Character[x]);
		Arrays.sort(chars);
		
		Character [] expected =  { 'e', 'm', 's', 'x', 'x' };
		assertArrayEquals( expected, chars);
		
		
		System.out.println( neighbours);
		
	}
	}
