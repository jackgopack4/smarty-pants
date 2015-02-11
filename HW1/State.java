import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the DFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            closed[i][j] is true if (i,j) is already expanded
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] closed, Maze maze) {
		// FILL THIS METHOD
		ArrayList<State> successors = new ArrayList<State>();

		Square currentSquare = this.getSquare();
		int boundX = maze.getNoOfRows();
		int boundY = maze.getNoOfCols();
		//System.out.println("boundX = "+boundX+" boundY = "+boundY);
		boolean checkUp, checkRight, checkDown, checkLeft;

		int upX=currentSquare.X-1;
		int rightY=currentSquare.Y+1;
		int downX=currentSquare.X+1;
		int leftY=currentSquare.Y-1;

		if(currentSquare.X > 0) checkUp = true;
		else checkUp = false;
		if(currentSquare.Y < boundY-1) checkRight = true;
		else checkRight = false;
		if(currentSquare.X < boundX-1) checkDown = true;
		else checkDown = false;
		if(currentSquare.Y > 0) checkLeft = true;
		else checkLeft = false;

		if(checkLeft) {
				if(maze.getSquareValue(currentSquare.X,leftY)!='%' /*&& !closed[currentSquare.X][leftY]*/) {
					successors.add(new State(new Square(currentSquare.X,leftY), this, gValue+1, depth+1));
					//System.out.println("Adding to left");
				}
		}

		if(checkDown) {
				if(maze.getSquareValue(downX,currentSquare.Y)!='%' /*&& !closed[downX][currentSquare.Y]*/) {
					successors.add(new State(new Square(downX,currentSquare.Y), this, gValue+1, depth+1));
					//System.out.println("Adding below");
				}
		}
		/*System.out.println("Right square coordinate is: ("+currentSquare.X+","+rightY+")");
		System.out.println("Right square value is: "+maze.getSquareValue(currentSquare.X,rightY));
		*/
		if(checkRight) {
				if(maze.getSquareValue(currentSquare.X,rightY)!='%' /*&& !closed[currentSquare.X][rightY]*/) {
					successors.add(new State(new Square(currentSquare.X,rightY), this, gValue+1, depth+1));
					//System.out.println("Adding to right");
				}
		}

		if(checkUp) {
				// check if a wall or open
				if(maze.getSquareValue(upX,currentSquare.Y)!='%' /*&& !closed[upX][currentSquare.Y]*/) {
					successors.add(new State(new Square(upX,currentSquare.Y), this, gValue+1, depth+1));
					//System.out.println("Adding above");
				}
		}
		//System.out.println("Successors = "+successors);
		return successors;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the DFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
