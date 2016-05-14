package mazeGenerator;

import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;
import maze.NormalMaze;

public class RecursiveBacktrackerGenerator implements MazeGenerator
{
dgdfg
	@Override
	public void generateMaze(Maze maze)
	{
		// variable for storing the path
		Stack<Cell> path = new Stack<Cell>();

		// variable for storing maze type
		String mazeType = maze.getClass().getSimpleName();

		// if (mazeType.equals("NormalMaze"))
		// {
		// NormalMaze maze = new NormalMaze();
		// maze = maze;
		// }

		// variable for checking visited cell
		boolean[][] isVisited = new boolean[maze.sizeR][maze.sizeC];

		// variable for storing all possible neighbours of the current cell
		String[] neighbour = null;

		if (mazeType.equals("NormalMaze") || mazeType.equals("TunnelMaze"))
		{
			neighbour = new String[4];
		}
		else // hexa maze neighbour
		{
			neighbour = new String[6];
		}

		// set the current cell to the entrance
		Cell currentCell = maze.entrance;

		// mark the current cell to visited
		isVisited[currentCell.r][currentCell.c] = true;

		// neighbour on the north side
		if (currentCell.r >= 0 && currentCell.r < maze.sizeR && currentCell.c >= 0 && currentCell.c <= maze.sizeC
				&& !isVisited[currentCell.r + 1][currentCell.c])
		{
			neighbour[0] = "NORTH";
		}

		// neighbour on the east side
		if (currentCell.r >= 0 && currentCell.r <= maze.sizeR && currentCell.c >= 0 && currentCell.c < maze.sizeC
				&& !isVisited[currentCell.r][currentCell.c + 1])
		{
			neighbour[1] = "EAST";
		}

		// neighbour on the south side
		if (currentCell.r > 0 && currentCell.r <= maze.sizeR && currentCell.c >= 0 && currentCell.c <= maze.sizeC
				&& !isVisited[currentCell.r - 1][currentCell.c])
		{
			neighbour[2] = "SOUTH";
		}

		// neighbour on the west side
		if (currentCell.r >= 0 && currentCell.r <= maze.sizeR && currentCell.c > 0 && currentCell.c <= maze.sizeC
				&& !isVisited[currentCell.r][currentCell.c - 1])
		{
			neighbour[3] = "WEST";
		}

		// variable for moving randomly to the current cell neighbour
		Random rand = new Random();
		int randomMove;

		do
		{
			randomMove = rand.nextInt(neighbour.length);

			if (neighbour[randomMove] != null)
			{
				// move to north
				if (neighbour[randomMove].equals("NORTH"))
				{
					currentCell.wall[maze.NORTH].present = false;
					currentCell = new Cell(currentCell.r + 1, currentCell.c);
				}
				// move to east
				if (neighbour[randomMove].equals("EAST"))
				{
					currentCell.wall[maze.EAST].present = false;
					currentCell = new Cell(currentCell.r, currentCell.c + 1);
				}
				// move to south
				if (neighbour[randomMove].equals("SOUTH"))
				{
					currentCell.wall[maze.SOUTH].present = false;
					currentCell = new Cell(currentCell.r - 1, currentCell.c);
				}
				// move to west
				if (neighbour[randomMove].equals("WEST"))
				{
					currentCell.wall[maze.WEST].present = false;
					currentCell = new Cell(currentCell.r, currentCell.c - 1);
				}
			}
		} while (neighbour[randomMove] == null);

		System.out.println(currentCell.r + " " + currentCell.c);

		// do
		// {
		// // mark the current cell as visited
		// isVisited[currentCell.r][currentCell.c] = true;
		//
		//
		//
		// } while (currentCell.c == maze.exit.c && currentCell.r ==
		// maze.exit.r);
	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator
