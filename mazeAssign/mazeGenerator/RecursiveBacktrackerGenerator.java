package mazeGenerator;

import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;



public class RecursiveBacktrackerGenerator implements MazeGenerator {

	Stack<Cell> stack = new Stack<Cell>();
	Cell north, northEast, east, southEast, south,
		southWest, west, northWest;
	int row, col;
	
	@Override
	public void generateMaze(Maze maze) {
		Cell currentCell = null;
		Cell nextCell = null;
		
		// Creating boolean array to mark cells visited or unvisited
		boolean[] cellVisitor = new boolean[maze.sizeC * maze.sizeR];
		int[] neighbours;

		/*
		 * Set the currentCell as the entrance to the maze
		 * and push it to the stack
		 */
		currentCell = maze.entrance;
		row = currentCell.r;
		col = currentCell.c;
		stack.push(currentCell);

		/*
		 * Main body of function.  Recursively visit all cells marking them as 
		 * visited and carving a path
		 */
		do
		{
			// Mark the currentCell as visited
			cellVisitor[currentCell.c*maze.sizeR + currentCell.r] = true;
			
			// If the maze type is normal
			if(maze.type == Maze.NORMAL)
			{			
				// Assign all nieghbours 
				north = currentCell.neigh[Maze.NORTH];
				east = currentCell.neigh[Maze.EAST];
				south = currentCell.neigh[Maze.SOUTH];
				west = currentCell.neigh[Maze.WEST];
				
			}// End of if normal maze
			
			else if(maze.type == Maze.HEX)
			{
				
				// Assigns all neighbours	
				north = currentCell.neigh[Maze.NORTH];
				northEast = currentCell.neigh[Maze.NORTHEAST];
				east = currentCell.neigh[Maze.EAST];
				southEast = currentCell.neigh[Maze.SOUTHEAST];
				south = currentCell.neigh[Maze.SOUTH];
				southWest = currentCell.neigh[Maze.SOUTHWEST];
				west = currentCell.neigh[Maze.WEST];
				northWest = currentCell.neigh[Maze.NORTHWEST];
				
			}// End of if hex maze
			
			
		}while(!stack.isEmpty());
		
		
		
		currentCell.wall[Maze.NORTH].present = false;
		
		

		
	
		
		
		
		
	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator
