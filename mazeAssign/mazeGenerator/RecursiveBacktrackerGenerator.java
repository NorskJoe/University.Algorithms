package mazeGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	Stack<Cell> stack = new Stack<Cell>();
	
	@Override
	public void generateMaze(Maze maze) {
		
		/*
		 * Each if statement checks what type of maze
		 * is being generated and calls the appropriate method
		 */
		if(maze.type == Maze.NORMAL)
		{
			normalMaze(maze);
		}
		else if(maze.type == Maze.HEX)
		{
			hexMaze(maze);
		}
		else if(maze.type == Maze.TUNNEL)
		{
			tunnelMaze(maze);
		}
	} // end of generateMaze()


	private void normalMaze(Maze maze) {
		Cell currentCell = null;
		
		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];
		
		/*
		 * Set the currentCell as the entrance to the maze
		 * and push it to the stack
		 */
		currentCell = maze.entrance;
		stack.push(currentCell);
		
		/*
		 * Main body of function.  Recursively visit all cells marking them as 
		 * visited and carving a path.
		 * 
		 * This will continue until all cells have been visited, and therefore the stack is empty
		 */
		do
		{
			// Mark the currentCell as visited
			cellVisitor[currentCell.r][currentCell.c] = true;
			ArrayList<String> availableNeighbours = new ArrayList<String>();
			
			/*
			 *  Each if statement checks if the neighbour is within bounds, and has not been visited
			 */
			if(currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r < maze.sizeR - 1
					&& cellVisitor[currentCell.r + 1][currentCell.c] != true)
			{
				availableNeighbours.add("NORTH");
			}
			if(currentCell.c >= 0 && currentCell.c < maze.sizeC - 1 && currentCell.r >= 0 && currentCell.r <= maze.sizeR
					&& cellVisitor[currentCell.r][currentCell.c + 1] != true)
			{
				
				availableNeighbours.add("EAST");
			}
			if(currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r > 0 && currentCell.r <= maze.sizeR
					&& cellVisitor[currentCell.r - 1][currentCell.c] != true)
			{
				
				availableNeighbours.add("SOUTH");
			}
			if(currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r <= maze.sizeR
					&& cellVisitor[currentCell.r][currentCell.c - 1] != true)
			{
				
				availableNeighbours.add("WEST");
			}
				
			System.out.println(availableNeighbours);
			System.out.println("current row, col: " + currentCell.r + " " + currentCell.c);
				
			/*
			 * If none of the neighbours are viable next cells then move back to the last cell visited
			 */
			if(availableNeighbours.isEmpty())
			{
				currentCell = stack.pop();
			}
			/*
			 * This block will choose a random neighbour, destroy the wall between
			 * the currentCell and the neighbour, push the currentCell to the stack
			 *  and set the currentCell to the neighbour
			 */
			else
			{				
				Random rand = new Random();
				String direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));
				
				if(direction.equals("NORTH"))
				{
					currentCell.wall[Maze.NORTH].present = false;
					currentCell = maze.map[currentCell.r + 1][currentCell.c];
				}
				if(direction.equals("EAST"))
				{
					currentCell.wall[Maze.EAST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
				}
				if(direction.equals("SOUTH"))
				{
					currentCell.wall[Maze.SOUTH].present = false;
					currentCell = maze.map[currentCell.r - 1][currentCell.c];
				}
				if(direction.equals("WEST"))
				{
					currentCell.wall[Maze.WEST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
				}
				
				stack.push(currentCell);
			}
			
		}while(stack.isEmpty() == false);
		
	}// End of normalMaze()
	
	private void hexMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// End of hexMaze()
	

	private void tunnelMaze(Maze maze) {
		Cell currentCell = null;
		
		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];
		
		/*
		 * Set the currentCell as the entrance to the maze
		 * and push it to the stack
		 */
		currentCell = maze.entrance;
		stack.push(currentCell);
		
		
		/*
		 * Main body of function.  Recursively visit all cells marking them as 
		 * visited and carving a path.
		 * 
		 * This will continue until all cells have been visited, and therefore the stack is empty
		 */
		do
		{
			// Mark the currentCell as visited
			cellVisitor[currentCell.r][currentCell.c] = true;
			ArrayList<String> availableNeighbours = new ArrayList<String>();
			
			/*
			 *  Each if statement checks if the neighbour is within bounds, and has not been visited.
			 *  
			 *  The tunnel implementation also checks if a cell has a 'tunnel neighbour'.
			 *  If the currentCell is a tunnel, then you move through the tunnel
			 */
			if(currentCell.tunnelTo != null && cellVisitor[currentCell.tunnelTo.r][currentCell.tunnelTo.c] != true)
			{
				availableNeighbours.add("TUNNEL");
			}
			else
			{
				
				if(currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r < maze.sizeR - 1
						&& cellVisitor[currentCell.r + 1][currentCell.c] != true)
				{
					availableNeighbours.add("NORTH");
				}
				if(currentCell.c >= 0 && currentCell.c < maze.sizeC - 1 && currentCell.r >= 0 && currentCell.r <= maze.sizeR
						&& cellVisitor[currentCell.r][currentCell.c + 1] != true)
				{
					
					availableNeighbours.add("EAST");
				}
				if(currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r > 0 && currentCell.r <= maze.sizeR
						&& cellVisitor[currentCell.r - 1][currentCell.c] != true)
				{
					
					availableNeighbours.add("SOUTH");
				}
				if(currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r <= maze.sizeR
						&& cellVisitor[currentCell.r][currentCell.c - 1] != true)
				{
					
					availableNeighbours.add("WEST");
				}
			}
				
			/*
			 * If none of the neighbours are viable next cells then move back to the last cell visited
			 */
			if(availableNeighbours.isEmpty())
			{
				currentCell = stack.pop();
			}

			/*
			 * This block will choose a random neighbour, destroy the wall between
			 * the currentCell and the neighbour, push the currentCell to the stack
			 *  and set the currentCell to the neighbour
			 */
			else
			{				
				Random rand = new Random();
				String direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));
				
				if(direction.equals("TUNNEL"))
				{
					currentCell = maze.map[currentCell.tunnelTo.r][currentCell.tunnelTo.c];
				}
				if(direction.equals("NORTH"))
				{
					currentCell.wall[Maze.NORTH].present = false;
					currentCell = maze.map[currentCell.r + 1][currentCell.c];
				}
				if(direction.equals("EAST"))
				{
					currentCell.wall[Maze.EAST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
				}
				if(direction.equals("SOUTH"))
				{
					currentCell.wall[Maze.SOUTH].present = false;
					currentCell = maze.map[currentCell.r - 1][currentCell.c];
				}
				if(direction.equals("WEST"))
				{
					currentCell.wall[Maze.WEST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
				}
				
				stack.push(currentCell);
			}
			
		}while(stack.isEmpty() == false);
		
		
	}// End of tunnelMaze()

} // end of class RecursiveBacktrackerGenerator