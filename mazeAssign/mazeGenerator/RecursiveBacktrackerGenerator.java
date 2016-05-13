package mazeGenerator;

import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;



public class RecursiveBacktrackerGenerator implements MazeGenerator {

	Stack<Cell> stack = new Stack<Cell>();
	Cell north, northEast, east, southEast, south,
		southWest, west, northWest;
	
	@Override
	public void generateMaze(Maze maze) {
		Cell currentCell = null;
		Cell nextCell = null;
		
		// neighbour index used to select nextCell
		int neighbourIndex = -1;
		
		/*
		 * Entering maze for the first time, set the current cell
		 * to the entrance cell
		 */
		if(stack.isEmpty())
		{
			currentCell = maze.entrance;
			stack.push(currentCell);
		}
		
		// The entrance has already been visited
		else
		{
			//currentCell = 
		}
		
		// If the maze type is normal
		if(maze.type == Maze.NORMAL)
		{			
			// Assign all nieghbours 
			north = currentCell.neigh[Maze.NORTH];
			east = currentCell.neigh[Maze.EAST];
			south = currentCell.neigh[Maze.SOUTH];
			west = currentCell.neigh[Maze.WEST];
			
			/*
			 * Create an int array of the possible selections for
			 * the next cell. 
			 * NORTH = 2, EAST = 0, SOUTH = 5, WEST = 3
			 */
			int[] neighbours = {2, 0, 5, 3};
						
			// Randomly chooses a neighbouring cell to visit next
			while(nextCell == null && nextCell != maze.exit)
			{
				//Randomly select a neighbour
				Random rand = new Random();
				neighbourIndex = rand.nextInt(neighbours.length);
				nextCell = currentCell.neigh[neighbourIndex];
			}
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
			
			/*
			 * Create an int array of the possible selections for
			 * the next cell. 
			 * NORTH = 2, EAST = 0, SOUTH = 5, WEST = 3
			 * NORTHEAST = 1, SOUTHEAST = 5, SOUTHWEST = 4, NORTHWEST = 2
			 */
			int[] neighbours = {2, 0, 5, 3, 1, 5, 4, 2};
						
			// Randomly chooses a neighbouring cell to visit next
			while(nextCell == null && nextCell != maze.exit)
			{
				//Randomly select a neighbour
				Random rand = new Random();
				neighbourIndex = rand.nextInt(neighbours.length);
				nextCell = currentCell.neigh[neighbourIndex];
			}
		}// End of if hex maze

		
		/*
		 * Removes the wall between the current cell
		 * and the chosen neighbour
		 */
		currentCell.wall[neighbourIndex].present = false;
		
		
		
		/*		
		for(int i = 0; i < maze.sizeR; i++)
		{
			for(int j = 0; j < maze.sizeC; j ++)
			{
				Cell cell = maze.map[i][j];
				System.out.println(cell.r + " " + cell.c);
			}
		}*/
		
		
	
		
		
		
		
	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator
