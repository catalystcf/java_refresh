package puzzle.wordsearch;

class Field {

	Field( char[][] newField)
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
	Loc currentLocation;
	
	/** integer index that we keep for travesring the grid */
	int index;

	

	/** start in the middle */
	void start() {

		index = 0;		
				
		currentLocation = new Loc(0, 0);
		System.out.println("Startin at " + currentLocation);

	}

	/**
	 * Simple traversal logic where we going to go left to right, down, right to
	 * left. field is (0,0) in upper left.
	 */
	void next() {
		index = index +1;
		int col = index % width();
		int row = index / width();
		
		currentLocation = new Loc(row, col);
	};

	boolean isEnd() {
		int max = width()*height();
	
		return index >= max;
	}

	public char currentChar() {
		
		return field[currentLocation.row][currentLocation.col];
	};

}