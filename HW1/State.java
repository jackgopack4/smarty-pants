import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * Editor: John L Peterson
 * Date: 10 FEB 2015
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
		// Checks if nearby states are open and returns ArrayList of available successors
		ArrayList<State> successors = new ArrayList<State>();

        // Initialize current coordinates and determine if we can check directions (at edges or not)
		Square currentSquare = this.getSquare();
		int boundX = maze.getNoOfRows();
		int boundY = maze.getNoOfCols();
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

        // If we are allowed to check squares (i.e. not at edge of maze, if space is empty add it
        // If not, ignore it
		if(checkLeft) {
				if(maze.getSquareValue(currentSquare.X,leftY)!='%') {
					successors.add(new State(new Square(currentSquare.X,leftY), this, gValue+1, depth+1));
				}
		}

		if(checkDown) {
				if(maze.getSquareValue(downX,currentSquare.Y)!='%') {
					successors.add(new State(new Square(downX,currentSquare.Y), this, gValue+1, depth+1));
				}
		}
		if(checkRight) {
				if(maze.getSquareValue(currentSquare.X,rightY)!='%') {
					successors.add(new State(new Square(currentSquare.X,rightY), this, gValue+1, depth+1));
				}
		}

		if(checkUp) {
				if(maze.getSquareValue(upX,currentSquare.Y)!='%') {
					successors.add(new State(new Square(upX,currentSquare.Y), this, gValue+1, depth+1));
				}
		}
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
