package mazeSolver;

import java.util.ArrayList;
import java.util.Currency;
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

	@Override
	public void solveMaze(Maze maze)
	{
		normalMaze(maze);
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
		// TODO Auto-generated method stub
		return 0;
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

		String direction = null;

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

			System.out.println(availableNeighbours);
			System.out.println("current row, col: " + currentCell.r + " " + currentCell.c);

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
				direction = availableNeighbours.get(rand.nextInt(availableNeighbours.size()));

				System.out.println("NORTH WALL PRESENT? " + currentCell.wall[Maze.NORTH].present);
				System.out.println("EAST WALL PRESENT? " + currentCell.wall[Maze.EAST].present);
				System.out.println("SOUTH WALL PRESENT? " + currentCell.wall[Maze.SOUTH].present);
				System.out.println("WEST WALL PRESENT? " + currentCell.wall[Maze.WEST].present);
				System.out.println("Chosen cell position is: " + direction);

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
		} while (currentCell.r != maze.exit.r && currentCell.c != maze.exit.c);

		for (Cell cell : stack)
		{
			maze.drawFtPrt(cell);
		}

	} // end of normalMaze()

	private void hexMaze(Maze maze)
	{
		// TODO Auto-generated method stub

	} // end of hexMaze()

	private void tunnelMaze(Maze maze)
	{
		// TODO Auto-generated method stub

	} // end of tunnelMaze()

} // end of class RecursiveBackTrackerSolver
