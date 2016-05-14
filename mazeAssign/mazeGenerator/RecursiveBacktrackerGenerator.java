package mazeGenerator;

import java.util.ArrayList;
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
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];
		int neighbourIndex = -10;
		

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
			cellVisitor[currentCell.r][currentCell.c] = true;
			ArrayList<String> availableNeighbours = new ArrayList<String>();
			
			/*
			 *  If the maze type is normal.
			 *  Each if statement checks if the neighbour exists, and has not been visited
			 */
			if(maze.type == Maze.NORMAL)
			{			
				if(currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r < maze.sizeR - 1
						&& cellVisitor[currentCell.r + 1][currentCell.c] != true)
				{
					north = currentCell.neigh[Maze.NORTH];
					availableNeighbours.add("NORTH");
				}
				if(currentCell.c >= 0 && currentCell.c < maze.sizeC - 1 && currentCell.r >= 0 && currentCell.r <= maze.sizeR
						&& cellVisitor[currentCell.r][currentCell.c + 1] != true)
				{
					east = currentCell.neigh[Maze.EAST];
					availableNeighbours.add("EAST");
				}
				if(currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r > 0 && currentCell.r <= maze.sizeR
						&& cellVisitor[currentCell.r - 1][currentCell.c] != true)
				{
					south = currentCell.neigh[Maze.SOUTH];
					availableNeighbours.add("SOUTH");
				}
				if(currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r <= maze.sizeR
						&& cellVisitor[currentCell.r][currentCell.c - 1] != true)
				{
					west = currentCell.neigh[Maze.WEST];	
					availableNeighbours.add("WEST");
				}
				
				System.out.println(availableNeighbours);
				System.out.println("current row, col: " + currentCell.r + " " + currentCell.c);
				
				
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
			
			if(availableNeighbours.isEmpty())
			{
				currentCell = stack.pop();
			}

			else
			{				
				Random rand = new Random();
				String direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));
				
				System.out.println("Chosen cell position is: " + direction);
				
				System.out.println("NORTH WALL PRESENT? " + currentCell.wall[Maze.NORTH].present);
				System.out.println("EAST WALL PRESENT? " + currentCell.wall[Maze.EAST].present);
				System.out.println("SOUTH WALL PRESENT? " + currentCell.wall[Maze.SOUTH].present);
				System.out.println("WEST WALL PRESENT? " + currentCell.wall[Maze.WEST].present);
				
				if(direction.equals("NORTH"))
				{
					currentCell.wall[Maze.NORTH].present = false;
					currentCell = maze.map[currentCell.r + 1][currentCell.c];
					//currentCell = new Cell(currentCell.r + 1, currentCell.c);
				}
				if(direction.equals("EAST"))
				{
					currentCell.wall[Maze.EAST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
					//currentCell = new Cell(currentCell.r, currentCell.c + 1);
				}
				if(direction.equals("SOUTH"))
				{
					currentCell.wall[Maze.SOUTH].present = false;
					currentCell = maze.map[currentCell.r - 1][currentCell.c];
					//currentCell = new Cell(currentCell.r - 1, currentCell.c);
				}
				if(direction.equals("WEST"))
				{
					currentCell.wall[Maze.WEST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
					//currentCell = new Cell(currentCell.r, currentCell.c - 1);
				}
				
				stack.push(currentCell);
			}
			
			
			
		}while(stack.isEmpty() == false);
		
		
		
		

		
	
		
		
		
		
	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator