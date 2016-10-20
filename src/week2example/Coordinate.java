/**
 * 
 */
package week2example;

import java.util.LinkedList;
import java.util.List;

/**
 * Coordinate.java
 * 
 * @author UCSD Intermediate Programming MOOC team
 * 
 * A class to represent a Node in a graph which is a Maze that the robot navigates.
 */
class Coordinate {
	private List<Coordinate> neighbors;
	// Since our maze is always a grid, nodes keep track of their row and column
	private int row;
	private int column;
	private char displayChar;
	
	public static final char EMPTY = '-';
	public static final char PATH = 'o';
	public static final char START = 'S';
	public static final char GOAL = 'G';

	/**
	 * @return the displayChar
	 */
	public char getDisplayChar() {
		return displayChar;
	}

	/**
	 * @param displayChar the displayChar to set
	 */
	public void setDisplayChar(char displayChar) {
		this.displayChar = displayChar;
	}

	public Coordinate(int row, int col)
	{
		this.row = row;
		this.column = col;
		neighbors = new LinkedList<Coordinate>();
		displayChar = EMPTY;
	}

	public void addNeighbor(Coordinate neighbor) 
	{
		neighbors.add(neighbor);
	}
	
	/**
	 * @return the neighbors
	 */
	public List<Coordinate> getNeighbors() {
		return neighbors;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	
	
}
