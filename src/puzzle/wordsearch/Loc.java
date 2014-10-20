package puzzle.wordsearch;

/**
 * represents a single location on the board
 * 
 * @author Alex Ar
 *
 */
class Loc {
	int row;
	int col;

	public Loc(int x2, int y2) {
		this.row = x2;
		this.col = y2;
	}

	public String toString() {
		return "(" + row + "," + col + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
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
		Loc other = (Loc) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}