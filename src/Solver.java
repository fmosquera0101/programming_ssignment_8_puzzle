import java.util.Stack;

import edu.princeton.cs.algs4.MinPQ;


public class Solver {
	private Node last;
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial){
		MinPQ<Node> minPQ = new MinPQ<Solver.Node>();
		minPQ.insert(new Node(initial));
		
		MinPQ<Node> minPQTwin = new MinPQ<Solver.Node>();
		minPQTwin.insert(new Node(initial.twin()));
		
		while(true){
			last = expand(minPQ);
			if(null != last || expand(minPQTwin) != null){
				return;
			}
		}
		
	}
	private Node expand(MinPQ<Node> minPQ) {
		if(minPQ.isEmpty()){
			return null;
		}
		Node bestMove = minPQ.delMin();
		if(bestMove.board.isGoal()){
			return bestMove;
		}
		for (Board board : bestMove.board.neighbors()) {
			if(null == bestMove.previous || !board.equals(bestMove.previous.board)){
				minPQ.insert(new Node(board, bestMove, bestMove.numMoves + 1));
			}
		}
		return null;
	}
	// is the initial board solvable?
	public boolean isSolvable(){
		return last != null;

	}
	// min number of moves to solve initial board; -1 if unsolvable
	public int moves(){
		return isSolvable() ? last.numMoves: - 1;
	}
	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if(!isSolvable()){
			return null;
		}
		Stack<Board> moves = new Stack<Board>();
		while(null != last){
			moves.push(last.board);
			last = last.previous;
		}
		return moves;
	}
	// solve a slider puzzle (given below)
	public static void main(String[] args) {

	}
	private class Node implements Comparable<Node>{
		private Board board;
		private Node previous;
		int numMoves;
		public Node(Board board, Node previous, int numMoves){
			this.board = board;
			this.previous = previous;
			this.numMoves = numMoves;
		}
		public Node(Board board){
			this.board = board;
		}
		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return (this.board.manhattan() - o.board.manhattan()) + (this.numMoves + o.numMoves);
		}
		
	}
}
