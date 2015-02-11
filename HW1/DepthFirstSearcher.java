import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * Author: John L. Peterson
 * Date: 10 FEB 2015
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
        // Method to perform a depth first search on the provided maze
        
		// CLOSED list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been expanded.
		boolean[][] closed = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
        // Initialize Closed to false
		for(int i=0;i<maze.getNoOfRows()-1;i++) {
			for(int j=0;j<maze.getNoOfCols()-1;j++) {
				closed[i][j] = false;
			}
		}

		// Stack implementing the Frontier list
        // Create initial state and push to it
		LinkedList<State> stack = new LinkedList<State>();
		State initial = new State(maze.getPlayerSquare(), null, 0, 0);
		noOfNodesExpanded = 0;
		cost = 0;
		stack.push(initial);
		while (!stack.isEmpty()) {
            // Keep max size of Frontier updated
			if(stack.size() > maxSizeOfFrontier) maxSizeOfFrontier = stack.size();
			State tempState = stack.pop();
			State parentState = tempState.getParent();
            // Keep track of nodes expanded
			noOfNodesExpanded++;
            
            // If it is a solution, track back, edit the maze and return true
			if((tempState.getSquare().X == maze.getGoalSquare().X) && (tempState.getSquare().Y == maze.getGoalSquare().Y)) {
				cost++;
                // make sure to return proper path cost (add one for start space)
				while(parentState.getParent() != null) {
					cost++;
					maze.setOneSquare(parentState.getSquare(), '.');
					tempState = parentState;
					parentState = tempState.getParent();
				}
				return true;
			}
            // Since we visited it, update the closed matrix
			closed[tempState.getX()][tempState.getY()] = true;
			ArrayList<State> tempList = tempState.getSuccessors(closed, maze);
			
            // Loop through the successors list and determine if they've been visited or not on the current path
            // This means heading back through parent pointers on current path for each one (not fast but works)
            for(State s:tempList) {
				boolean add = true;
				State checkState = s;
				State checkPrev = checkState.getParent();
                // if add variable set to false, don't push it to the stack
				while(checkPrev != null) {
					if((checkPrev.getSquare().X == s.getSquare().X) && (checkPrev.getSquare().Y == s.getSquare().Y)) {
						add = false;
					}
					checkState = checkPrev;
					checkPrev = checkState.getParent();
				}
				
				if(add){
					stack.push(s);
				} 
			}
		}

		// return false if no solution
		return false;
	}
}
