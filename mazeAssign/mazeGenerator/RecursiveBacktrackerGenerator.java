package mazeGenerator;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		
		// Sets the current cell to the entrance of the maze
		Cell currentCell = maze.entrance;
		
		// Printing out all cells
		for(int i = 0; i < maze.sizeR; i++)
		{
			for(int j = 0; j < maze.sizeC; j ++)
			{
				Cell cell = maze.map[i][j];
				System.out.println(cell.r + " " + cell.c);
			}
		}
		
		// Removes the wall from the east side of the current cell
		currentCell.wall[Maze.EAST].present = false;
		
		// Assigns the neighbouring cells		
		Cell north = currentCell.neigh[Maze.NORTH];
		Cell south = currentCell.neigh[Maze.SOUTH];
		Cell east = currentCell.neigh[Maze.EAST];
		Cell west = currentCell.neigh[Maze.WEST];
		
		System.out.println("position of current: " + currentCell.r + " " + currentCell.c);
		System.out.println("position of east: " + east.r + " " + east.c);
		System.out.println("position of north: " + north.r + " " + north.c);
		
		
		
		
	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator
