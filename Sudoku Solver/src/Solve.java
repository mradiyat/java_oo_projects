import java.util.*;

/**
 * this class is the method for solving the given board
 * @author hs
 *
 */
public class Solve {
		
	/**
	 * count all the possible number of solutions to the input board in
	 * @param in
	 * @return number of solutions
	 * @throws NullBoardException
	 */
	public int countSol(Board in) throws NullBoardException{
		Set<Board> boardSet = new HashSet<Board>();
		getSolutions(in, boardSet);
		return boardSet.size();
	}
	
	/**
	 * Solves the puzzle and simply prints solution
	 * @param in
	 * @throws NullBoardException
	 */
	
	public void printSolve(Board in) throws NullBoardException{
		if (!in.validBoardState()) {
			Board b = new Board();
			b.printBoard();
		}
		else {
			solve(in).printBoard();
		}
	}
	
	public Board solve(Board in) throws NullBoardException {
		if (in.ifGoal()) {
			return in;
		}
		else {
			Board[] children = in.getChildren();
			for (Board i : children) {
				if (i != null) {
					Board b = solve(i);
					if (b != null) {
						return b;
					}
				}
			}
			return null;
		}
	}
	
	
	
	/**
	 * Compiles a set of all solutions for board in
	 * @param in the board to compile solutions for
	 * @param boardSet the set to recursively add solutions to
	 * @throws NullBoardException
	 */
	public void getSolutions(Board in, Set<Board> boardSet) throws NullBoardException {
		if (in.ifGoal()) {
			boardSet.add(in);
		}
		else {
			Board[] children = in.getChildren();
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null) {
					getSolutions(children[i], boardSet);
				}
			}
		}
	}
	
	
	/**
	 * Compiles all states of the board 
	 * @param in
	 * @param boardSet
	 * @throws NullBoardException
	 */
	public void getStates(Board in, Set<Board> boardSet) throws NullBoardException {
		if (in.validBoardState()) {
			boardSet.add(in);
		}
		if (in.ifGoal()) {
			return;
		}
		Board[] children = in.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				getStates(children[i], boardSet);
			}
		}
		
	}
	
	/**
	 * Solve with statistics
	 * @param in
	 * @return one valid solution
	 * @throws NullBoardException
	 */
	public Board[] solveWithStats(Board in) throws NullBoardException{
		Set<Board> boardSet = new HashSet<Board>(); //will keep all of our solutions
		Set<Board> stateSet = new HashSet<Board>(); //will keep all of our solutions
		getSolutions(in, boardSet);
		getStates(in, stateSet);
		System.out.println("Solutions:" + boardSet.size());
		System.out.println("States:" + stateSet.size());
		if (boardSet.isEmpty()) {
			return new Board[0];
		}
		else {
			int size = boardSet.size();
			Board[] allSolutions = new Board[size];
			int current = 0;
			for (Board i : boardSet) {
				allSolutions[current] = i;
				current++;
			}
			return allSolutions;
		}
	}
	
	public Board solveSmart(Board in) throws NullBoardException{
		Board b = solveSmartBoard(in);
		if (b == null) {
			return new Board();
		}
		else return b;
	}
	
	public Board solveSmartBoard(Board in) throws NullBoardException {
		if (in.ifGoal()) {
			return in;
		}
		else {
			Board[] children = in.getIntelligentChildren();
			for (Board i : children) {
				if (i != null) {
					Board b = solveSmart(i);
					if (b != null) {
						return b;
					}
				}
			}
			return null;
		}
	}
	
	

}