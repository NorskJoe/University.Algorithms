package mazeSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;

/**
 * Implements Bi-directional BFS maze solving algorithm.
 */
public class BiDirectionalBFSSolver implements MazeSolver
{
	Cell startCell = null;
	Cell endCell = null;
	Cell nextCell = null;

	Boolean foundPath = false;

	// use a queue to implement BFS
	Queue<Cell> startPath = new ArrayDeque<Cell>();
	Queue<Cell> exitPath = new ArrayDeque<Cell>();
	int step = 2;

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
		if (foundPath)
		{
			return true;
		}
		return false;
	} // end of isSolved()

	@Override
	public int cellsExplored()
	{
		return step;
	} // end of cellsExplored()

	private void normalMaze(Maze maze)
	{
		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitorStart = new boolean[maze.sizeR][maze.sizeC];
		boolean[][] cellVisitorExit = new boolean[maze.sizeR][maze.sizeC];

		/*
		 * Set the startCell as the entrance to the maze, endCell as the exit of
		 * the maze and enqueue its to the queue
		 */
		startCell = maze.entrance;
		endCell = maze.exit;

		// Mark both cell as visited
		cellVisitorStart[startCell.r][startCell.c] = true;
		cellVisitorExit[endCell.r][endCell.c] = true;

		startPath.add(startCell);
		exitPath.add(endCell);

		// while the path lead to exit has not been found
		do
		{
			/*
			 * check for possible neighbour from starting point and mark them as
			 * visited. 
			 * 
			 * if the next neighbour is already marked as visited from
			 * the frontier backward(exit point) then it mean we found a path from starting
			 * point to exit
			 */
			if (startCell.wall[Maze.NORTH].present == false)
			{
				if (cellVisitorExit[startCell.r + 1][startCell.c] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[startCell.r + 1][startCell.c]);
				}
				else if (cellVisitorStart[startCell.r + 1][startCell.c] != true)
				{
					startPath.add(maze.map[startCell.r + 1][startCell.c]);
					cellVisitorStart[startCell.r + 1][startCell.c] = true;
					step++;
					maze.drawFtPrt(startCell);
				}
			}
			if (startCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitorExit[startCell.r][startCell.c + 1] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[startCell.r][startCell.c + 1]);
				}
				else if (cellVisitorStart[startCell.r][startCell.c + 1] != true)
				{
					startPath.add(maze.map[startCell.r][startCell.c + 1]);
					cellVisitorStart[startCell.r][startCell.c + 1] = true;
					step++;
					maze.drawFtPrt(startCell);
				}
			}
			if (startCell.wall[Maze.SOUTH].present == false)
			{
				if (cellVisitorExit[startCell.r - 1][startCell.c] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[startCell.r - 1][startCell.c]);
				}
				else if (cellVisitorStart[startCell.r - 1][startCell.c] != true)
				{
					startPath.add(maze.map[startCell.r - 1][startCell.c]);
					cellVisitorStart[startCell.r - 1][startCell.c] = true;
					step++;
					maze.drawFtPrt(startCell);
				}
			}
			if (startCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitorExit[startCell.r][startCell.c - 1] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[startCell.r][startCell.c - 1]);
				}
				else if (cellVisitorStart[startCell.r][startCell.c - 1] != true)
				{
					startPath.add(maze.map[startCell.r][startCell.c - 1]);
					cellVisitorStart[startCell.r][startCell.c - 1] = true;
					step++;
					maze.drawFtPrt(startCell);
				}
			}
			/*
			 * end of checking possible neighbour from starting point and
			 * marking as visited
			 */

			/*
			 * check for possible neighbour from exit point and mark them as //
			 * visited.
			 * 
			 * if the next neighbour is already marked as visited from
			 * the frontier forward(starting point) then it mean we found a path from starting
			 * point to exit
			 */
			if (endCell.wall[Maze.NORTH].present == false)
			{
				if (cellVisitorStart[endCell.r + 1][endCell.c] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[endCell.r + 1][endCell.c]);
				}
				else if (cellVisitorExit[endCell.r + 1][endCell.c] != true)
				{
					exitPath.add(maze.map[endCell.r + 1][endCell.c]);
					cellVisitorExit[endCell.r + 1][endCell.c] = true;
					step++;
					maze.drawFtPrt(endCell);
				}
			}
			if (endCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitorStart[endCell.r][endCell.c + 1] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[endCell.r][endCell.c + 1]);
				}
				else if (cellVisitorExit[endCell.r][endCell.c + 1] != true)
				{
					exitPath.add(maze.map[endCell.r][endCell.c + 1]);
					cellVisitorExit[endCell.r][endCell.c + 1] = true;
					step++;
					maze.drawFtPrt(endCell);
				}
			}
			if (endCell.wall[Maze.SOUTH].present == false)
			{
				if (cellVisitorStart[endCell.r - 1][endCell.c] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[endCell.r - 1][endCell.c]);
				}
				else if (cellVisitorExit[endCell.r - 1][endCell.c] != true)
				{
					exitPath.add(maze.map[endCell.r - 1][endCell.c]);
					cellVisitorExit[endCell.r - 1][endCell.c] = true;
					step++;
					maze.drawFtPrt(endCell);
				}
			}
			if (endCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitorStart[endCell.r][endCell.c - 1] == true)
				{
					foundPath = true;
					maze.drawFtPrt(maze.map[endCell.r][endCell.c - 1]);
				}
				else if (cellVisitorExit[endCell.r][endCell.c - 1] != true)
				{
					exitPath.add(maze.map[endCell.r][endCell.c - 1]);
					cellVisitorExit[endCell.r][endCell.c - 1] = true;
					step++;
					maze.drawFtPrt(endCell);
				}
			}
			/*
			 * end of checking possible neighbour from starting point and
			 * marking as visited
			 */

			startCell = startPath.poll();
			endCell = exitPath.poll();

			if (startCell != null)
			{
				maze.drawFtPrt(startCell);
			}
			if (endCell != null)
			{
				maze.drawFtPrt(endCell);
			}

		} while (foundPath == false);
	}
	// end of normalMaze()

	private void hexMaze(Maze maze)
	{

	}
	// end of hexMaze()

	private void tunnelMaze(Maze maze)
	{

	}
	// end of tunnelMaze()

} // end of class BiDirectionalBFSSolver
