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
		Cell currentCell = null;

		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];

		/*
		 * Set the currentCell as the entrance to the maze and push it to the
		 * stack
		 */
		currentCell = maze.entrance;
		stack.push(currentCell);

		/*
		 * Main body of function. Recursively visit all cells marking them as
		 * visited and carving a path.
		 * 
		 * This will continue until all cells have been visited, and therefore
		 * the stack is empty
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
		Cell currentCell = null;

		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

		/*
		 * Set the currentCell as the entrance to the maze and push it to the
		 * stack
		 */
		currentCell = maze.entrance;
		stack.push(currentCell);

		/*
		 * Main body of function. Recursively visit all cells marking them as
		 * visited and carving a path.
		 * 
		 * This will continue until all cells have been visited, and therefore
		 * the stack is empty
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
			if (currentCell.r < maze.sizeR - 1)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.NORTHEAST]][currentCell.c
				                                                          + Maze.deltaC[Maze.NORTHEAST]] != null
				                                                          && cellVisitor[currentCell.r + Maze.deltaR[Maze.NORTHEAST]][currentCell.c
				                                                                                                                      + Maze.deltaC[Maze.NORTHEAST]] != true)
				{
					availableNeighbours.add("NORTH EAST");
				}				
			}
			if (currentCell.r < maze.sizeR - 1)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.NORTHWEST]][currentCell.c
				                                                          + Maze.deltaC[Maze.NORTHWEST]] != null
				                                                          && cellVisitor[currentCell.r + Maze.deltaR[Maze.NORTHWEST]][currentCell.c
				                                                                                                                      + Maze.deltaC[Maze.NORTHWEST]] != true)
				{
					availableNeighbours.add("NORTH WEST");
				}
			}
			if (currentCell.c < (maze.sizeC + 1) / 2 + maze.sizeC - 1)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.EAST]][currentCell.c + Maze.deltaC[Maze.EAST]] != null
						&& cellVisitor[currentCell.r][currentCell.c + 1] != true)
				{
					availableNeighbours.add("EAST");
				}
			}
			if (currentCell.r > 0)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.SOUTHEAST]][currentCell.c
						+ Maze.deltaC[Maze.SOUTHEAST]] != null
						&& cellVisitor[currentCell.r + Maze.deltaR[Maze.SOUTHEAST]][currentCell.c
								+ Maze.deltaC[Maze.SOUTHEAST]] != true)
				{
					availableNeighbours.add("SOUTH EAST");
				}
			}
			if (currentCell.r > 0)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.SOUTHWEST]][currentCell.c
						+ Maze.deltaC[Maze.SOUTHWEST]] != null
						&& cellVisitor[currentCell.r + Maze.deltaR[Maze.SOUTHWEST]][currentCell.c
								+ Maze.deltaC[Maze.SOUTHWEST]] != true)
				{
					availableNeighbours.add("SOUTH WEST");
				}
			}
			if (currentCell.c > 0)
			{
				if (maze.map[currentCell.r + Maze.deltaR[Maze.WEST]][currentCell.c + Maze.deltaC[Maze.WEST]] != null
						&& cellVisitor[currentCell.r][currentCell.c - 1] != true)
				{
					availableNeighbours.add("WEST");
				}
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

	private void tunnelMaze(Maze maze)
	{
		// TODO Auto-generated method stub

	}// End of tunnelMaze()

} // end of class RecursiveBacktrackerGenerator