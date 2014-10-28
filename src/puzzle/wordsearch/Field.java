package puzzle.wordsearch;

import java.util.ArrayList;

public class Field {

	public Field( char[][] newField)
	{
		this.field = newField;
				
	}
	
	char[][] field;

	int height() {
		return field.length;
	}

	int width() {
		return field[0].length;
	}

	/** where are we currently at */
	public Loc currentLocation;
	
	/** integer index that we keep for travesring the grid */
	int index;

	

	/** start in the corner */
	public void start() {

		index = 0;		
				
		currentLocation = new Loc(0, 0);
		System.out.println("Startin at " + currentLocation);

	}

	/**
	 * Simple traversal logic where we going to go left to right, down, right to
	 * left. field is (0,0) in upper left.
	 */
	public void next() {
		index = index +1;
		int col = index % width();
		int row = index / width();
		
		currentLocation = new Loc(row, col);
	};

	public boolean isEnd() {
		int max = width()*height();
	
		return index >= max;
	}

	public char currentChar() {
		
		return field[currentLocation.row][currentLocation.col];
	}

	/** return a list of letters immediately adjucent to the current letter
	 * @return collection of letters 
	 */
	public ArrayList<PuzzleChar> getNeighbours(PuzzleChar pChar) {
		ArrayList<PuzzleChar> neighbours = new ArrayList<PuzzleChar>();
		
		for(int ri = -1; ri <= 1; ri++)
		{
			for (int ci  = -1; ci <= 1; ci++)
			{
				int nRow = pChar.loc.row + ri;
				int nCol = pChar.loc.col  + ci;
				
				if ( nRow < 0 || nRow >= height())
					continue;
				
				if ( nCol <0 || nCol >= width())
					continue;
				
				if (nRow == pChar.loc.row && nCol == pChar.loc.col)
					continue;
				
				// TODO: move to factory of PuzzleChars
				// no need to create a new one
				Loc loc = new Loc(nRow, nCol);
				neighbours.add(new PuzzleChar(field[nRow][nCol], loc));
			}
		}
		return neighbours;
	};

}