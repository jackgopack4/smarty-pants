import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Iterator;

/**
 * A* algorithm search
 * Author: John L Peterson
 * Date: 10 FEB 2015
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// CLOSED list is a Boolean array that indicates if a state associated with a given position in the maze has already been expanded. 
		boolean[][] closed = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
        // Initialize to false
		for(int i=0;i<maze.getNoOfRows()-1;i++) {
			for(int j=0;j<maze.getNoOfCols()-1;j++) {
				closed[i][j] = false;
			}
		}

		// OPEN list (aka Frontier list)
        // Calculate initial state pair values and push onto Priority Queue
		PriorityQueue<StateFValuePair> open = new PriorityQueue<StateFValuePair>();
		Square solution = maze.getGoalSquare();
		double p = (double) solution.X;
		double q = (double) solution.Y;

		State initial = new State(maze.getPlayerSquare(), null, 0, 0);
		double u = (double) initial.getX();
		double v = (double) initial.getY();
		double initialF = Math.abs(u-p) + Math.abs(v-q);

		StateFValuePair initialPair = new StateFValuePair(initial, initialF);

		open.add(initialPair);
		noOfNodesExpanded = 0;
		cost = 0;
		while (!open.isEmpty()) {
            // Keep the max frontier size updated
			if(open.size() > maxSizeOfFrontier) maxSizeOfFrontier = open.size();
			StateFValuePair tempPair = open.poll();

			double tempFVal = tempPair.getFValue();
			State tempState = tempPair.getState();
			State parentState = tempState.getParent();
			noOfNodesExpanded++;
            
            // if true, this is a solution. loop back to modify the path and return true
			if((tempState.getX() == maze.getGoalSquare().X) && (tempState.getY() == maze.getGoalSquare().Y)) {
				cost++;
				while(parentState.getParent() != null) {
					cost++;
					maze.setOneSquare(parentState.getSquare(), '.');
					tempState = parentState;
					parentState = tempState.getParent();
				}
				return true;
			}
            // otherwise, do calculations to determine if we will use a state or not
			u = (double) tempState.getX();
			v = (double) tempState.getY();
			closed[tempState.getX()][tempState.getY()] = true;
			double tempHVal = Math.abs(u-p) + Math.abs(v-q);
			double tempGVal = (double) tempState.getGValue();
			ArrayList<State> tempList = tempState.getSuccessors(closed, maze);
            
            // loop through successors list and compare to both frontier and expanded
			for(State s:tempList) {
				boolean add = true;
				boolean replace = false;
				boolean discard = false;
				State checkState = s;
				double checkH = Math.abs((double)checkState.getX()-p) + Math.abs((double)checkState.getY()-q);
				double checkG = (double)checkState.getGValue();
				
                // Compare to frontier comes first
                StateFValuePair checkPair = new StateFValuePair(checkState, checkH+checkG);
				Iterator<StateFValuePair> itr = open.iterator();
				StateFValuePair toReplace = new StateFValuePair(new State(new Square(0,0), null, 0, 0), 0);
				while(itr.hasNext()) {
					StateFValuePair sfp = itr.next();
					if((sfp.getState().getSquare().X == checkPair.getState().getSquare().X) && (sfp.getState().getSquare().Y == checkPair.getState().getSquare().Y) && !replace && !discard) {
						double sfpG = (double) sfp.getState().getGValue();
						if(checkG < sfpG) {
							replace = true;
							toReplace = sfp;
						}
						else discard = true;
					}
				}
				
				int i = checkState.getX();
				int j = checkState.getY();
                
                // Compare to expanded list comes next
				if(closed[i][j]) {
					double closedG = checkPair.getFValue()-(Math.abs((double)i - p) + Math.abs((double)j - q));
					if(checkG < closedG) {
						closed[i][j] = false;
					}
					else discard = true;
				}
				if(replace) {
					open.remove(toReplace);
				}
				if(!discard) {
					open.add(checkPair);
				}
			}


		}

		// return false if no solution
		return false;
	}

}
