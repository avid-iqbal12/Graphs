/**
 * A class that represents a maze to navigate through.
 */
package week2example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

/**
 * @author Christine
 *
 */
public class Maze {
	private Coordinate[][] cells;
	private int width;
	private int height;

	private final int DEFAULT_SIZE = 10;

	public Maze() {
		cells = new Coordinate[DEFAULT_SIZE][DEFAULT_SIZE];
		this.width = DEFAULT_SIZE;
		this.height = DEFAULT_SIZE;
	}

	/** Create a new empty Maze */
	public Maze(int width, int height) {
		cells = new Coordinate[height][width];
		this.width = width;
		this.height = height;
	}

	public void initialize(int width, int height) {
		cells = new Coordinate[height][width];
		this.width = width;
		this.height = height;

	}

	public void addNode(int row, int col) {
		cells[row][col] = new Coordinate(row, col);
	}

	/**
	 * Link the nodes that are adjacent (and not null) to each other with an
	 * edge. There is an edge between any two adjacent nodes up, down, left or
	 * right.
	 */
	public void linkEdges() {
		int numRows = cells.length;
		for (int row = 0; row < numRows; row++) {
			int numCols = cells[row].length;
			for (int col = 0; col < numCols; col++) {
				if (cells[row][col] != null) {
					if (row > 0 && cells[row - 1][col] != null) {
						cells[row][col].addNeighbor(cells[row - 1][col]);
					}
					if (col > 0 && cells[row][col - 1] != null) {
						cells[row][col].addNeighbor(cells[row][col - 1]);
					}
					if (row < numRows - 1 && cells[row + 1][col] != null) {
						cells[row][col].addNeighbor(cells[row + 1][col]);
					}
					if (col < numCols - 1 && cells[row][col + 1] != null) {
						cells[row][col].addNeighbor(cells[row][col + 1]);
					}
				}
			}
		}
	}

	public void printMaze() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				if (cells[r][c] == null) {
					System.out.print('*');
				} else {
					System.out.print(cells[r][c].getDisplayChar());
				}
			}
			System.out.print("\n");
		}

	}

	public void setPath(List<Coordinate> path) {
		int index = 0;
		for (Coordinate n : path) {
			if (index == 0) {
				n.setDisplayChar(Coordinate.START);
			} else if (index == path.size() - 1) {
				n.setDisplayChar(Coordinate.GOAL);
			} else {
				n.setDisplayChar(Coordinate.PATH);
			}
			index++;
		}

	}

	public void clearPath() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[r].length; c++) {
				Coordinate n = cells[r][c];
				if (n != null) {
					n.setDisplayChar(Coordinate.EMPTY);
				}
			}
		}
	}

	/** depth first search from (startRow,startCol) to (endRow,endCol)
	 * 
	 * @param startRow  The row of the starting position
	 * @param startCol  The column of the starting position
	 * @param endRow The row of the end position
	 * @param endCol The column of the end position
	 * @return the path from starting position to ending position, or
	 * an empty list if there is no path.
	 */
	public List<Coordinate> dfs(int startRow, int startCol, int endRow, int endCol) {
		
		// Initialize everything
		Coordinate start = cells[startRow][startCol];
		Coordinate goal = cells[endRow][endCol];

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<Coordinate>();
		}

		HashMap<Coordinate, Coordinate> parentMap = new HashMap<Coordinate, Coordinate>();
		// Do the search
		boolean found = dfsSearch(start, goal, parentMap);
		
		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<Coordinate>();
		}

		// reconstruct the path
		return constructPath(start, goal, parentMap);
	}
	
	private List<Coordinate> constructPath(Coordinate start, Coordinate goal,
			HashMap<Coordinate, Coordinate> parentMap)
	{
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		Coordinate curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

	private boolean dfsSearch(Coordinate start, Coordinate goal, 
			HashMap<Coordinate, Coordinate> parentMap){
		HashSet<Coordinate> visited = new HashSet<Coordinate>();
		Stack<Coordinate> toExplore = new Stack<Coordinate>();
		toExplore.push(start);
		boolean found = false;

		// Do the search
		while (!toExplore.empty()) {
			Coordinate curr = toExplore.pop();
			if (curr == goal) {
				found = true;
				break;
			}
			List<Coordinate> neighbors = curr.getNeighbors();
			ListIterator<Coordinate> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				Coordinate next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.push(next);
				}
			}
		}
		return found;
	}

	public List<Coordinate> bfs(int startRow, int startCol, int endRow, int endCol) {
		Coordinate start = cells[startRow][startCol];
		Coordinate goal = cells[endRow][endCol];

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<Coordinate>();
		}

		HashSet<Coordinate> visited = new HashSet<Coordinate>();
		Queue<Coordinate> toExplore = new LinkedList<Coordinate>();
		HashMap<Coordinate, Coordinate> parentMap = new HashMap<Coordinate, Coordinate>();
		toExplore.add(start);
		boolean found = false;
		while (!toExplore.isEmpty()) {
			Coordinate curr = toExplore.remove();
			if (curr == goal) {
				found = true;
				break;
			}
			List<Coordinate> neighbors = curr.getNeighbors();
			ListIterator<Coordinate> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				Coordinate next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.add(next);
				}
			}
		}

		if (!found) {
			System.out.println("No path exists");
			return new ArrayList<Coordinate>();
		}
		// reconstruct the path
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		Coordinate curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

/*	public List<Coordinate> dfsRefactored(int startRow, int startCol, 
										int endRow, int endCol) {
		// Initialize
		Coordinate start = cells[startRow][startCol];
		Coordinate goal = cells[endRow][endCol];

		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return new LinkedList<Coordinate>();
		}

		HashMap<Coordinate, Coordinate> parentMap = new HashMap<Coordinate, Coordinate>();
		boolean found = dfsSearch(start, goal, parentMap);

		if (!found) {
			System.out.println("No path exists");
			return new LinkedList<Coordinate>();
		}

		// reconstruct the path
		return constructPath(start, goal, parentMap);

	}

	private static boolean dfsSearch(Coordinate start, Coordinate goal, 
									HashMap<Coordinate, Coordinate> parentMap) {
		HashSet<Coordinate> visited = new HashSet<Coordinate>();
		Stack<Coordinate> toExplore = new Stack<Coordinate>();
		toExplore.push(start);
		boolean found = false;

		while (!toExplore.empty()) {
			Coordinate curr = toExplore.pop();
			if (curr == goal) {
				found = true;
				break;
			}
			List<Coordinate> neighbors = curr.getNeighbors();
			ListIterator<Coordinate> it = neighbors.listIterator(neighbors.size());
			while (it.hasPrevious()) {
				Coordinate next = it.previous();
				if (!visited.contains(next)) {
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.push(next);
				}
			}
		}
		return found;
	}

	private static List<Coordinate> constructPath(Coordinate start, Coordinate goal, HashMap<Coordinate, Coordinate> parentMap) {
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		Coordinate curr = goal;
		while (curr != start) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

*/
	public static void main(String[] args) {
		String mazeFile = "data/mazes/maze1.maze";
		Maze maze = new Maze();
		MazeLoader.loadMaze(mazeFile, maze);
		maze.printMaze();
		List<Coordinate> path = maze.dfs(3, 3, 2, 0);
		maze.setPath(path);
		System.out.println("\n");
		maze.printMaze();
		maze.clearPath();
		maze.setPath(maze.bfs(3, 3, 2, 0));
		System.out.println("\n");
		maze.printMaze();
	}
}
