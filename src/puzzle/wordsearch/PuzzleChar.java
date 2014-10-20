
package puzzle.wordsearch;


import static java.lang.Math.abs;

public class PuzzleChar {

	final char ch;
	final Loc loc;
	
	public PuzzleChar(char ch, Loc loc) {
		this.ch = ch;
		this.loc = loc;
		
	}

	/**
	 * Checks that the char provides is a reachable neighbor
	 * @param pch     
	 * @return true if a valid neighbor
	 */
	public boolean isNeighbour(PuzzleChar pch) {
		
		return ( abs(pch.loc.row -loc.row) <= 1 && abs(pch.loc.col - loc.col) <= 1 );
		
	}

	@Override
	public String toString() {
		return "" + ch + loc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ch;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
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
		
		PuzzleChar other = (PuzzleChar) obj;
		if (ch != other.ch)
			return false;
		
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		return true;
	}
	
	
	
	
}
