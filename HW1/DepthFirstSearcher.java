import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD
		
		// CLOSED list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been expanded.
		boolean[][] closed = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		for(int i=0;i<maze.getNoOfRows()-1;i++) {
			for(int j=0;j<maze.getNoOfCols()-1;j++) {
				closed[i][j] = false;
			}
		}
		// ...

		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();
		State initial = new State(maze.getPlayerSquare(), null, 0, 0);
		noOfNodesExpanded = 0;
		cost = 0;
		stack.push(initial);
		//System.out.println("Goal square is: ("+maze.getGoalSquare().X+","+maze.getGoalSquare().Y+")");
		while (!stack.isEmpty()) {
			// TODO return true if find a solution
			if(stack.size() > maxSizeOfFrontier) maxSizeOfFrontier = stack.size();
			State tempState = stack.pop();
			//System.out.println();
			//System.out.println("Current square: ("+tempState.getX()+","+tempState.getY()+"), \'"+maze.getSquareValue(tempState.getX(),tempState.getY())+"\'");
			State parentState = tempState.getParent();
			noOfNodesExpanded++;
			if((tempState.getSquare().X == maze.getGoalSquare().X) && (tempState.getSquare().Y == maze.getGoalSquare().Y)) {
				//System.out.println("MATCH FOUND");
				cost++;
				while(parentState.getParent() != null) {
					cost++;
					maze.setOneSquare(parentState.getSquare(), '.');
					tempState = parentState;
					parentState = tempState.getParent();
				}
				return true;
			}
			closed[tempState.getX()][tempState.getY()] = true;
			ArrayList<State> tempList = tempState.getSuccessors(closed, maze);
			for(State s:tempList) {
				boolean add = true;
				State checkState = s;
				State checkPrev = checkState.getParent();
				while(checkPrev != null) {
					//System.out.println("checking previous");
					if((checkPrev.getSquare().X == s.getSquare().X) && (checkPrev.getSquare().Y == s.getSquare().Y)) {
						add = false;
						//System.out.println("NOT Pushing Square: ("+s.getX()+","+s.getY()+")");
					}
					checkState = checkPrev;
					checkPrev = checkState.getParent();
				}
				
				if(add){
					//System.out.println("Pushing Square: ("+s.getX()+","+s.getY()+")");
					stack.push(s);
				} 
			}
			// TODO update the maze if a solution found

			// use stack.pop() to pop the stack.
			// use stack.push(...) to elements to stack
		}

		// TODO return false if no solution
		return false;
	}
}
