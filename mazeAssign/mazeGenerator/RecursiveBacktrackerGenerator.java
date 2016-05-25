package mazeGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator
{

	Stack<Cell> stack = new Stack<Cell>();

	@Override
	public void generateMaze(Maze maze)
	{

		/*
		 * Each if statement checks what type of maze is being generated and
		 * calls the appropriate method
		 */
		if (maze.type == Maze.NORMAL)
		{
			normalMaze(maze);
		}
		else if (maze.type == Maze.HEX)
		{
			hexMaze(maze);
		}
		else if (maze.type == Maze.TUNNEL)
		{
			tunnelMaze(maze);
		}
	} // end of generateMaze()

	private void normalMaze(Maze maze)
	{
		/*
		 * Boolean array used to create a copy of the maze with all cells set to false.
		 * As each cell is visited it will be set to true, indicating it has been
		 * visisted.
		 */
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];

		/*
		 * Set the currentCell as the entrance to the maze and push it to the
		 * stack
		 */
		Cell currentCell = null;
		currentCell = maze.entrance;
		stack.push(currentCell);

		/*
		 * Main body of function. 
		 * 
		 * (1)Choose a random neighbour of the current cell that is not
		 * 		visisted
		 * (2)Push the current cell to the top of the stack
		 * (3)Move to the neighbour, carving a path, and mark is as visisted
		 * (4)Update the neighbours list for the current cell
		 * (5)If the neighbours list is empty, backtrack to the previous
		 * 		cell by popping the stack and check if it has any available neighbours
		 * (6)Continue this process until the stack is empty, indicating that
		 * 		all cells have been marked as visisted
		 */
		do
		{
			// Mark the currentCell as visited
			cellVisitor[currentCell.r][currentCell.c] = true;
			/*
			 * ArrayList holds all the neighbours of the current cell that
			 * have not been visisted.
			 */
			ArrayList<String> availableNeighbours = new ArrayList<String>();

			/*
			 * Each if statement checks if the neighbour is within bounds, and
			 * has not been visited
			 */
			if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0
					&& currentCell.r < maze.sizeR - 1 && cellVisitor[currentCell.r + 1][currentCell.c] != true)
			{
				availableNeighbours.add("NORTH");
			}
			if (currentCell.c >= 0 && currentCell.c < maze.sizeC - 1 && currentCell.r >= 0
					&& currentCell.r <= maze.sizeR && cellVisitor[currentCell.r][currentCell.c + 1] != true)
			{

				availableNeighbours.add("EAST");
			}
			if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r > 0 && currentCell.r <= maze.sizeR
					&& cellVisitor[currentCell.r - 1][currentCell.c] != true)
			{

				availableNeighbours.add("SOUTH");
			}
			if (currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r <= maze.sizeR
					&& cellVisitor[currentCell.r][currentCell.c - 1] != true)
			{

				availableNeighbours.add("WEST");
			}

			/*
			 * If none of the neighbours are viable next cells then move back to
			 * the last cell visited
			 */
			if (availableNeighbours.isEmpty())
			{
				currentCell = stack.pop();
			}

			/*
			 * This block will choose a random neighbour, carve a wall between
			 * the currentCell and the neighbour, push the currentCell to the stack
			 *  and set the currentCell to the neighbour
			 */
			else
			{
				Random rand = new Random();
				String direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));

				if (direction.equals("NORTH"))
				{
					currentCell.wall[Maze.NORTH].present = false;
					currentCell = maze.map[currentCell.r + 1][currentCell.c];
				}
				if (direction.equals("EAST"))
				{
					currentCell.wall[Maze.EAST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
				}
				if (direction.equals("SOUTH"))
				{
					currentCell.wall[Maze.SOUTH].present = false;
					currentCell = maze.map[currentCell.r - 1][currentCell.c];
				}
				if (direction.equals("WEST"))
				{
					currentCell.wall[Maze.WEST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
				}

				stack.push(currentCell);
			}

		} while (stack.isEmpty() == false);

	}// End of normalMaze()

	private void hexMaze(Maze maze)
	{

		/*
		 * Boolean array used to create a copy of the maze with all cells set to false.
		 * As each cell is visited it will be set to true, indicating it has been
		 * visisted.
		 */
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

		/*
		 * Set the currentCell as the entrance to the maze and push it to the
		 * stack
		 */
		Cell currentCell = null;
		currentCell = maze.entrance;
		stack.push(currentCell);

		/*
		 * Main body of function. 
		 * 
		 * (1)Choose a random neighbour of the current cell that is not
		 * 		visisted
		 * (2)Push the current cell to the top of the stack
		 * (3)Move to the neighbour, carving a path, and mark is as visisted
		 * (4)Update the neighbours list for the current cell
		 * (5)If the neighbours list is empty, backtrack to the previous
		 * 		cell by popping the stack and check if it has any available neighbours
		 * (6)Continue this process until the stack is empty, indicating that
		 * 		all cells have been marked as visisted
		 */
		do
		{
			// Mark the currentCell as visited
			cellVisitor[currentCell.r][currentCell.c] = true;
			ArrayList<String> availableNeighbours = new ArrayList<String>();

			/*
			 * Each if statement checks if the neighbour is within bounds, and
			 * has not been visited
			 */
			// If moving north
			if (currentCell.r < maze.sizeR - 1)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.NORTHEAST]]
							[currentCell.c + Maze.deltaC[Maze.NORTHEAST]] != null 
						&& cellVisitor[currentCell.r + Maze.deltaR[Maze.NORTHEAST]]
								[currentCell.c + Maze.deltaC[Maze.NORTHEAST]] != true)
				{
					availableNeighbours.add("NORTH EAST");
				}				
				if (maze.map[currentCell.r + Maze.deltaR[Maze.NORTHWEST]]
							[currentCell.c + Maze.deltaC[Maze.NORTHWEST]] != null 
						&& cellVisitor[currentCell.r + Maze.deltaR[Maze.NORTHWEST]]
								[currentCell.c + Maze.deltaC[Maze.NORTHWEST]] != true)
				{
					availableNeighbours.add("NORTH WEST");
				}
			}
			// If moving east
			if (currentCell.c < (maze.sizeC+1)/2+maze.sizeC-1
					&& maze.map[currentCell.r+Maze.deltaR[Maze.EAST]]
							[currentCell.c+Maze.deltaC[Maze.EAST]] != null
						&& cellVisitor[currentCell.r][currentCell.c+1] != true)
			{
				
				availableNeighbours.add("EAST");
				
			}
			// If moving south
			if (currentCell.r > 0)
			{
				if (maze.map[currentCell.r+Maze.deltaR[Maze.SOUTHEAST]]
							[currentCell.c+Maze.deltaC[Maze.SOUTHEAST]] != null
						&& cellVisitor[currentCell.r+Maze.deltaR[Maze.SOUTHEAST]]
								[currentCell.c+Maze.deltaC[Maze.SOUTHEAST]] != true)
				{
					availableNeighbours.add("SOUTH EAST");
				}
				if (maze.map[currentCell.r+Maze.deltaR[Maze.SOUTHWEST]]
							[currentCell.c+Maze.deltaC[Maze.SOUTHWEST]] != null
						&& cellVisitor[currentCell.r+Maze.deltaR[Maze.SOUTHWEST]]
								[currentCell.c+Maze.deltaC[Maze.SOUTHWEST]] != true)
				{
					availableNeighbours.add("SOUTH WEST");
				}
			}
			// If moving west
			if (currentCell.c > 0 && maze.map[currentCell.r+Maze.deltaR[Maze.WEST]]
						[currentCell.c+Maze.deltaC[Maze.WEST]] != null
					&& cellVisitor[currentCell.r][currentCell.c-1] != true)
			{
				availableNeighbours.add("WEST");	
			}

			/*
			 * If none of the neighbours are viable next cells then move back to
			 * the last cell visited
			 */
			if (availableNeighbours.isEmpty())
			{
				currentCell = stack.pop();
			}
			else
			{
				Random rand = new Random();
				String direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));

				if (direction.equals("NORTH EAST"))
				{
					currentCell.wall[Maze.NORTHEAST].present = false;
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.NORTHEAST]][currentCell.c
							+ Maze.deltaC[Maze.NORTHEAST]];
				}
				if (direction.equals("NORTH WEST"))
				{
					currentCell.wall[Maze.NORTHWEST].present = false;
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.NORTHWEST]][currentCell.c
							+ Maze.deltaC[Maze.NORTHWEST]];
				}
				if (direction.equals("EAST"))
				{
					currentCell.wall[Maze.EAST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
				}
				if (direction.equals("SOUTH EAST"))
				{
					currentCell.wall[Maze.SOUTHEAST].present = false;
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.SOUTHEAST]][currentCell.c
							+ Maze.deltaC[Maze.SOUTHEAST]];
				}
				if (direction.equals("SOUTH WEST"))
				{
					currentCell.wall[Maze.SOUTHWEST].present = false;
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.SOUTHWEST]][currentCell.c
							+ Maze.deltaC[Maze.SOUTHWEST]];
				}
				if (direction.equals("WEST"))
				{
					currentCell.wall[Maze.WEST].present = false;
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
				}

				stack.push(currentCell);
			}
		} while (stack.isEmpty() == false);

	}// End of hexMaze()

	private void tunnelMaze(Maze maze) {
		
		/*
		 * Boolean array used to create a copy of the maze with all cells set to false.
		 * As each cell is visited it will be set to true, indicating it has been
		 * visisted.
		 */
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];
		
		/*
		 * Set the currentCell as the entrance to the maze
		 * and push it to the stack
		 */
		Cell currentCell = null;
		currentCell = maze.entrance;
		stack.push(currentCell);
		
		
		/*
		 * Main body of function. 
		 * 
		 * (1)Choose a random neighbour of the current cell that is not
		 * 		visisted
		 * (2)Push the current cell to the top of the stack
		 * (3)Move to the neighbour, carving a path, and mark is as visisted
		 * (4)Update the neighbours list for the current cell
		 * (5)If the neighbours list is empty, backtrack to the previous
		 * 		cell by popping the stack and check if it has any available neighbours
		 * (6)Continue this process until the stack is empty, indicating that
		 * 		all cells have been marked as visisted
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