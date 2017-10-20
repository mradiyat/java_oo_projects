public class Board {
	
	int [][] holes;
	/*
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * *** *** ***
	 * the board is represented as a 2d array
	 * empty spots are occupied by -1's and non-empty spots are occupied by the respective numbers
	 */

	/**Constructs board with a given array
	 * 
	 * @param b the array to use for the board
	 * @throws NullBoardException
	 */
	public Board(int[][] b) throws NullBoardException{
		if (checkBoard(b)) {
			holes = b;
		}
		else {
			throw new NullBoardException("Invalid board");
		}
	}
	
	/**Constructs empty board with all numbers as -1
	 *
	 */
	public Board(){
		int[][] emptyBoard = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				emptyBoard[i][j] = -1;
			}
		}
		holes = emptyBoard;
	}

	/**
	 * Generates next board states the simple way outlined
	 * in the assignment
	 * @return
	 * @throws NullBoardException
	 */
	public Board[] getChildren() throws NullBoardException{
		//if board is full, return empty board array
		if (ifFull()) {
			return new Board[9];
		}
		int row = -1; int col = -1; //coordinates of the first empty spot
		boolean finished = false; //lets us know we've hit an empty spot
		//iterate through board until you hit an empty spot
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (getHoles()[i][j] == -1 && !finished) {
					row = i; col = j; //now we have our empty spot
					finished = true;
					break;
				}
				if (finished) {
					break;
				}
			}
		}
		return getSpecificChildren(row, col);
	}
	
	/**
	 * Generate array of all possible children for a given cell
	 * @param row 
	 * @param col
	 * @return all children for the board using (row, col)
	 * @throws NullBoardException
	 */
	public Board[] getSpecificChildren(int row, int col) throws NullBoardException {
		Board[] children = new Board[9]; //array of children to be returned
		int currentChild = 0;
		//create 9 different boards by filling in 1-9 at the empty spot
		for (int i = 1; i < 10; i++) {
			Board currentCandidate = cloneBoard();
			currentCandidate.fill(row, col, i);
			//if current candidate child with a new filled spot is valid, include it in the returned array
			if (currentCandidate.validBoardState()) {
				children[currentChild] = currentCandidate;
				currentChild++;
			}
		}
		return children;
	}
	
	/**
	 * Prints out all boards in a given board array
	 * @param x
	 */
	public void printBoardArray(Board[] x) {
		for (Board i : x) {
			if (i != null) {
				i.printBoard();
			}
		}
	}

	/**
	 * Generates next board states in a more intelligent way.
	 * Iterates through the board, generating Board arrays of children for each unfilled spot.
	 * Ultimately returns only the board array of children with fewest children.
	 * @return
	 * @throws NullBoardException
	 */
	public Board [] getIntelligentChildren() throws NullBoardException{
		//if board is full, return empty board array
		if (ifFull()) {
			return new Board[9];
		}
		/* We want to iterate through the board and find the spot with the least amount of children.
		 * Number of children is dictated by number of valid entries, which is 9 minus the number of 
		 * invalid entries. We can iterate from 1-9 and check if the subsquare, row, and column have 
		 * that number already. If so, that number is an invalid entry. 
		 */
		int minChildren = 10; int row = -1; int col = -1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (getHoles()[i][j] == -1) {
					int children = 9; //start with the assumption that there are 9 children
					//iterate from 1-9
					for (int n = 1; n < 10; n++) {
						//if the number is found in the row, column, or square, that means one less viable child
						if (checkSubsquare(i,j,n) || checkRow(i,j,n) || checkColumn(i,j,n)) {
							children--;
						}
					}
					/*Now we have the number of children that this spot will have (without actually generating them yet)
					 * Check if this is fewer than minChildren, and positive, and if so update row and col
					 */
					if (children < minChildren && children > 0) {
						minChildren = children;
						row = i; col = j;
					}
				}
			}
		}
		//Now we have the spot with the minimum number of children
		return getSpecificChildren(row, col);
		
	}
	
	/**Count the number of non-null boards in a board array b
	 * @param b
	 * @return
	 */
	public int childrenCount(Board[] b) {
		int children = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				children++;
			}
		}
		return children;
	}

	/**
	 * fill the hole at (x,y) with the value n
	 * @param row
	 * @param col
	 * @param n
	 */
	public void fill(int row, int col, int n){
		//only fill if n is between 1 and 9
		if (n > 0 && n < 10) {
			holes[row][col] = n;
		}
	}

	/**
	 * check if the 3*3 sub-square contains the specified val
	 * @param col
	 * @param row
	 * @param val
	 * @return true if the subsquare is invalid, false if it is valid
	 */
	public boolean checkSubsquare(int row, int col, int val){
		//change col and row to the first indices of their 3x3 square 
		int colBox = (col / 3) * 3; // will turn col into either 0, 3, or 6
		int rowBox = (row / 3) * 3; // will turn row into either 0, 3, or 6
		for (int i = rowBox; i <= rowBox + 2; i++) {
			for (int j = colBox; j <= colBox + 2; j++) {
				if (holes[i][j] == -1) {
					continue;
				}
				if (i == row && j == col) {
					continue;
				}
				if (holes[i][j] == val) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * to check if the same row has already contained the number n
	 * @param row row number
	 * @param c column number; this is the column we skip
	 * @param n number to check against
	 * @return whether row contains n; true if row is invalid, false if row is valid
	 */
	public boolean checkRow(int row, int c, int n){
		//iterate across the row by changing column number
		for (int i = 0; i < 9; i++) { 
			//skip column c
			if (i == c) {
				continue;
			}
			if (holes[row][i] == -1) {
				continue;
			}
			if (holes[row][i] == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * to check if the same column has already contained the number n
	 * @param col
	 * @param r
	 * @param n
	 * @return whether column contains n; true if invalid column, false if valid
	 */
	public boolean checkColumn(int r, int col, int n){
		for (int i = 0; i < 9; i++) {
			if (i == r) {
				continue;
			}
			if (holes[i][col] == -1) {
				continue;
			}
			if (holes[i][col] == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check if the game board holes are all filled with numbers
	 * @return 
	 */
	public boolean ifFull(){
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (holes[i][j] == -1) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Checks if board state is valid, for whatever degree of filled it is.
	 * @return whether the board is valid
	 */
	public boolean validBoardState(){
		/* Iterate through the board, skipping all -1's (empty cells).
		 * for each cell check if row, col, and 3x3 have the same values
		 * 
		 */
		int[][] b = getHoles();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				/* Ways to invalidate a board: a number is contained twice in the same square, 
				 * row, or column; a number is 0; a number is less than -1 or greater than 9
				 */
				if (checkSubsquare(i, j, b[i][j]) || checkRow(i,j,b[i][j]) 
						|| checkColumn(i,j,b[i][j]) || b[i][j] == 0 
						|| b[i][j] < -1 || b[i][j] > 9) {
					return false;
				}
			}
		}
		return true;
	}
	

	/**
	 * check if the board is in its goal position
	 * @return
	 */
	public boolean ifGoal(){
		return (ifFull() && validBoardState());
	}
	

	/**
	 * make a deep copy of the current board
	 * @return
	 * @throws NullBoardException 
	 */
	public Board cloneBoard() throws NullBoardException{
		int[][] b = this.getHoles();
		int[][] newArray = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				newArray[i][j] = b[i][j];
			}
		}
		Board board = new Board(newArray);
		return board;
	}
	/**
	 * print the array representation of this board
	 */
	public void printBoard(){
		String out = "";
		for (int r = 0; r < 9; r ++){
			String row = "";
			for (int c =0; c <9; c++){
				row = row + this.holes[r][c]; 
			}
			out = out+ (row + "\n");
		}
		System.out.println(out);
	}

	/**
	 * Check if the input board b is a valid board
	 * @param b
	 * @return
	 */
	boolean checkBoard(int[][] b){
		if (b == null) {
			return false;
		}
		if (b.length != 9) {
			return false;
		}
		for (int i = 0; i < 9; i++) {
			if (b[i].length != 9) {
				return false;
			}
		}
		return true;
	}
	/**Get board's holes
	 * @return
	 */
	public int[][] getHoles() {
		return holes;
	}
	/**Set a board's holes to b
	 * @param b
	 */
	public void setHoles(int[][] b) {
		holes = b;
	}
}
