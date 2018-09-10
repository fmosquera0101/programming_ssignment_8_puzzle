import java.util.LinkedList;


public class Board {

	private int[][] blocks;
	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks){
		if(null == blocks && blocks.length != blocks[0].length){
			throw new IllegalArgumentException();
		}
		this.blocks  = copy(blocks);

	}

	private int[][] copy(int[][] blocks) {
		int n = this.blocks.length;
		int[][] copy = new int[n][n];
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		return copy;
	}

	// board dimension n
	public int dimension(){
		return this.blocks.length;
	}
	//number of blocks out of place
	public int hamming(){
		int counter = 0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if(blocks[i][j] == 0 && blocks[i][j] != (i * dimension() + j + 1)){
					counter++;
				}
			}
		}
		return counter;
	}
	// sum of Manhattan distances between blocks and goal
	public int manhattan(){
		int distance = 0;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if(blocks[i][j] == 0){
					distance += 0;
				}else{
					distance += Math.abs(i - ((blocks[i][j] - 1) / dimension())) -  Math.abs(j - ((blocks[i][j] - 1) % dimension()));
				}
			}
		}
		return distance;
	}
	//is this board the goal board?
	public boolean isGoal(){
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if(blocks[i][j] == 0 && blocks[i][j] != (i * dimension() + j + 1)){
					return false;
				}
			}

		}
		return true;
	}
	//a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length - 1; j++) {
				if(blocks[i][j] != 0 && blocks[i][j + 1] != 0){
					return new Board(swap(i, j, i, j + 1));
				}
			}
		}
		return null;
	}
	private int[][] swap(int i, int j, int i1, int j1) {
		int [][] copy = copy(this.blocks);
		int temp = copy[i][j];
		copy[i][j] = copy[i1][j1];
		copy[i1][j1] = temp;
		return copy;
	}

	//does this board equal y?
	public boolean equals(Object y){
		if( this == y){
			return true;
		}
		if(null == y || !(y instanceof Board) || ((Board)y).blocks.length !=  blocks.length){
			return false;
		}

		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length - 1; j++) {
				if(((Board)y).blocks[i][j] !=  blocks[i][j]){
					return false;
				}

			}
		}
		return true;
	}
	//all neighboring boards
	public Iterable<Board> neighbors(){
		LinkedList<Board> linkedListNeighbors = new LinkedList<Board>();
		int [] oneDimArray = oneDimArray();
		int i = oneDimArray[0];
		int j = oneDimArray[1];
		if(i > 0){
			linkedListNeighbors.add(new Board(swap(i, j, i - 1, j)));
			
		}
		if(i < dimension() - 1){
			linkedListNeighbors.add(new Board(swap(i, j, i + 1, j)));
		}
		
		if(j > 0){
			linkedListNeighbors.add(new Board(swap(i, j, i , j - 1)));
			
		}
		if(j < dimension() - 1){
			linkedListNeighbors.add(new Board(swap(i, j, i , j + 1)));
		}
		return null;
	}
	private int[] oneDimArray() {
		int [] oneDimArr = null;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				if(blocks[i][j] == 0){
					oneDimArr = new int [2];
					oneDimArr[0] = i;
					oneDimArr[1] = j;
					return oneDimArr;
				}
			}
		}
		return oneDimArr;
	}

	//string representation of this board (in the output format specified below)
	public String toString(){
		int n = dimension();
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	//unit tests (not graded)
	public static void main(String[] args) {

	}
}
