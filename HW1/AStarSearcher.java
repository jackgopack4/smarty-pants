import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Iterator;

/**
 * A* algorithm search
 * 
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

		// FILL THIS METHOD

		// CLOSED list is a Boolean array that indicates if a state associated with a given position in the maze has already been expanded. 
		boolean[][] closed = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		for(int i=0;i<maze.getNoOfRows()-1;i++) {
			for(int j=0;j<maze.getNoOfCols()-1;j++) {
				closed[i][j] = false;
			}
		}

		// OPEN list (aka Frontier list)
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
		// TODO initialize the root state and add
		// to OPEN list
		// ...
		//System.out.println("Goal square is: ("+maze.getGoalSquare().X+","+maze.getGoalSquare().Y+")");
		while (!open.isEmpty()) {
			if(open.size() > maxSizeOfFrontier) maxSizeOfFrontier = open.size();
			StateFValuePair tempPair = open.poll();

			double tempFVal = tempPair.getFValue();
			State tempState = tempPair.getState();
			//System.out.println("Popped new state ("+tempState.getX()+","+tempState.getY()+")");
			State parentState = tempState.getParent();
			noOfNodesExpanded++;
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
			u = (double) tempState.getX();
			v = (double) tempState.getY();
			closed[tempState.getX()][tempState.getY()] = true;
			double tempHVal = Math.abs(u-p) + Math.abs(v-q);
			double tempGVal = (double) tempState.getGValue();
			ArrayList<State> tempList = tempState.getSuccessors(closed, maze);
			for(State s:tempList) {
				//System.out.println("Processing State ("+s.getX()+","+s.getY()+")");
				boolean add = true;
				boolean replace = false;
				boolean discard = false;
				State checkState = s;
				double checkH = Math.abs((double)checkState.getX()-p) + Math.abs((double)checkState.getY()-q);
				double checkG = (double)checkState.getGValue();
				StateFValuePair checkPair = new StateFValuePair(checkState, checkH+checkG);
				Iterator<StateFValuePair> itr = open.iterator();
				StateFValuePair toReplace = new StateFValuePair(new State(new Square(0,0), null, 0, 0), 0);
				while(itr.hasNext()) {
					StateFValuePair sfp = itr.next();
					if((sfp.getState().getSquare().X == checkPair.getState().getSquare().X) && (sfp.getState().getSquare().Y == checkPair.getState().getSquare().Y) && !replace && !discard) {
						//System.out.println("Open state is being reexamined here");
						double sfpG = (double) sfp.getState().getGValue();
						if(checkG < sfpG) {
							//System.out.println("New G value lower than frontier");
							replace = true;
							toReplace = sfp;
						}
						else discard = true;
					}
				}
				
				int i = checkState.getX();
				int j = checkState.getY();

				if(closed[i][j]) {
					//System.out.println("Closed state is being reexamined here");
					double closedG = checkPair.getFValue()-(Math.abs((double)i - p) + Math.abs((double)j - q));
					if(checkG < closedG) {
						//System.out.println("New G value lower than expanded");
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

			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded,
			// TODO update the maze if a solution found

			// use open.poll() to extract the minimum stateFValuePair.
			// use open.add(...) to add stateFValue pairs
		}

		// TODO return false if no solution
		return false;
	}

}
