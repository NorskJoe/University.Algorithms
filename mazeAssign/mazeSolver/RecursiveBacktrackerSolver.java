package mazeSolver;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

/**
 * Implements the recursive backtracking maze solving algorithm.
 */
public class RecursiveBacktrackerSolver implements MazeSolver
{
	Cell currentCell = null;
	Stack<Cell> stack = new Stack<Cell>();
	int step = 1;

	@Override
	public void solveMaze(Maze maze)
	{
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
	} // end of solveMaze()

	@Override
	public boolean isSolved()
	{
		if (stack.isEmpty() == false)
		{
			return true;
		}
		return false;
	} // end if isSolved()

	@Override
	public int cellsExplored()
	{
		return step;
	} // end of cellsExplored()

	private void normalMaze(Maze maze)
	{
		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitor = new boolean[maze.sizeR][maze.sizeC];

		/*
		 * Set the currentCell as the entrance to the maze and push it to the
		 * stack
		 */
		currentCell = maze.entrance;
		stack.push(currentCell);

		/*
		 * Main body of function. Recursively visit random cells looking for a
		 * path towards exit while marking them as visited
		 * 
		 * This will continue until current cell reach the exit
		 */
		do
		{
			// Mark the currentCell as visited
			cellVisitor[currentCell.r][currentCell.c] = true;
			ArrayList<String> availableNeighbours = new ArrayList<String>();

			maze.drawFtPrt(currentCell);

			/*
			 * Each if statement checks if the neighbour is within bounds, and
			 * has not been visited
			 */
			if (currentCell.wall[Maze.NORTH].present == false)
			{
				if (cellVisitor[currentCell.r + 1][currentCell.c] != true)
				{
					availableNeighbours.add("NORTH");
				}
			}
			if (currentCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitor[currentCell.r][currentCell.c + 1] != true)
				{
					availableNeighbours.add("EAST");
				}
			}
			if (currentCell.wall[Maze.SOUTH].present == false)
			{
				if (cellVisitor[currentCell.r - 1][currentCell.c] != true)
				{
					availableNeighbours.add("SOUTH");
				}
			}
			if (currentCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitor[currentCell.r][currentCell.c - 1] != true)
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

				if (direction.equals("NORTH"))
				{
					currentCell = maze.map[currentCell.r + 1][currentCell.c];
				}
				if (direction.equals("EAST"))
				{
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
				}
				if (direction.equals("SOUTH"))
				{
					currentCell = maze.map[currentCell.r - 1][currentCell.c];
				}
				if (direction.equals("WEST"))
				{
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
				}

				step++;
				stack.push(currentCell);
			}

			if (currentCell.r != maze.exit.r && currentCell.c != maze.exit.c)
			{
				System.out.println(" NOT EXIT");
			}
			else if (currentCell.r == maze.exit.r && currentCell.c == maze.exit.c)
			{
				System.out.println("EXIT");
			}
		} while (currentCell.equals(maze.exit) == false);

		maze.drawFtPrt(currentCell);
	} // end of normalMaze()

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

			maze.drawFtPrt(currentCell);

			/*
			 * Each if statement checks if the neighbour is within bounds, and
			 * has not been visited
			 */

			if (currentCell.wall[Maze.NORTHEAST].present == false)
			{
				if (cellVisitor[currentCell.r + Maze.deltaR[Maze.NORTHEAST]][currentCell.c
						+ Maze.deltaC[Maze.NORTHEAST]] != true)
				{
					availableNeighbours.add("NORTH EAST");
				}
			}
			if (currentCell.wall[Maze.NORTHWEST].present == false)
			{
				if (cellVisitor[currentCell.r + Maze.deltaR[Maze.NORTHWEST]][currentCell.c
						+ Maze.deltaC[Maze.NORTHWEST]] != true)
				{
					availableNeighbours.add("NORTH WEST");
				}
			}
			if (currentCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitor[currentCell.r + Maze.deltaR[Maze.EAST]][currentCell.c + Maze.deltaC[Maze.EAST]] != true)
				{
					availableNeighbours.add("EAST");
				}
			}
			if (currentCell.wall[Maze.SOUTHEAST].present == false)
			{
				if (cellVisitor[currentCell.r + Maze.deltaR[Maze.SOUTHEAST]][currentCell.c
						+ Maze.deltaC[Maze.SOUTHEAST]] != true)
				{
					availableNeighbours.add("SOUTH EAST");
				}
			}
			if (currentCell.wall[Maze.SOUTHWEST].present == false)
			{
				if (cellVisitor[currentCell.r + Maze.deltaR[Maze.SOUTHWEST]][currentCell.c
						+ Maze.deltaC[Maze.SOUTHWEST]] != true)
				{
					availableNeighbours.add("SOUTH WEST");
				}
			}
			if (currentCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitor[currentCell.r + Maze.deltaR[Maze.WEST]][currentCell.c + Maze.deltaC[Maze.WEST]] != true)
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
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.NORTHEAST]][currentCell.c
							+ Maze.deltaC[Maze.NORTHEAST]];
				}
				if (direction.equals("NORTH WEST"))
				{
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.NORTHWEST]][currentCell.c
							+ Maze.deltaC[Maze.NORTHWEST]];
				}
				if (direction.equals("EAST"))
				{
					currentCell = maze.map[currentCell.r][currentCell.c + 1];
				}
				if (direction.equals("SOUTH EAST"))
				{
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.SOUTHEAST]][currentCell.c
							+ Maze.deltaC[Maze.SOUTHEAST]];
				}
				if (direction.equals("SOUTH WEST"))
				{
					currentCell = maze.map[currentCell.r + Maze.deltaR[Maze.SOUTHWEST]][currentCell.c
							+ Maze.deltaC[Maze.SOUTHWEST]];
				}
				if (direction.equals("WEST"))
				{
					currentCell = maze.map[currentCell.r][currentCell.c - 1];
				}

				step++;
				stack.push(currentCell);
			}
		} while (currentCell.equals(maze.exit) == false);

		maze.drawFtPrt(currentCell);

	} // end of hexMaze()

	private void tunnelMaze(Maze maze)
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

			maze.drawFtPrt(currentCell);

			/*
			 * Each if statement checks if the neighbour is within bounds, and
			 * has not been visited.
			 * 
			 * The tunnel implementation also checks if a cell has a 'tunnel
			 * neighbour'. If the currentCell is a tunnel, then you move through
			 * the tunnel
			 */
			if (currentCell.tunnelTo != null && cellVisitor[currentCell.tunnelTo.r][currentCell.tunnelTo.c] != true)
			{
				availableNeighbours.add("TUNNEL");
			}
			else
			{
				if (currentCell.wall[Maze.NORTH].present == false)
				{
					if (cellVisitor[currentCell.r + 1][currentCell.c] != true)
					{
						availableNeighbours.add("NORTH");
					}
				}
				if (currentCell.wall[Maze.EAST].present == false)
				{
					if (cellVisitor[currentCell.r][currentCell.c + 1] != true)
					{
						availableNeighbours.add("EAST");
					}
				}
				if (currentCell.wall[Maze.SOUTH].present == false)
				{
					if (cellVisitor[currentCell.r - 1][currentCell.c] != true)
					{
						availableNeighbours.add("SOUTH");
					}
				}
				if (currentCell.wall[Maze.WEST].present == false)
				{
					if (cellVisitor[currentCell.r][currentCell.c - 1] != true)
					{
						availableNeighbours.add("WEST");
					}
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

			/*
			 * This block will choose a random neighbour, destroy the wall
			 * between the currentCell and the neighbour, push the currentCell
			 * to the stack and set the currentCell to the neighbour
			 */
			else
			{
				Random rand = new Random();
				String direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));

				if (direction.equals("TUNNEL"))
				{
					currentCell = maze.map[currentCell.tunnelTo.r][currentCell.tunnelTo.c];
				}
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

				step++;
				stack.push(currentCell);
			}

		} while (currentCell.equals(maze.exit) == false);

		maze.drawFtPrt(currentCell);

	} // end of tunnelMaze()

} // end of class RecursiveBackTrackerSolver
