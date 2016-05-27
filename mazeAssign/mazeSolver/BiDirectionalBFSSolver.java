package mazeSolver;

import java.util.ArrayDeque;
import java.util.Queue;

import maze.Cell;
import maze.Maze;

/**
 * Implements Bi-directional BFS maze solving algorithm.
 */
public class BiDirectionalBFSSolver implements MazeSolver
{
	Cell startCell = null;
	Cell endCell = null;

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

	/** 
     * Bi-Directional BFS Solver of input maze.
     * 
     * ******************************************************************************************
     * 
     * Bi-Directional BFS Solver
     * ALGORITHM normalMaze ( maze )
     * Perform a Bi-Directional BFS solver of a maze.
     * Input: Maze maze.
     * OUTPUT : a path lead from starting point to exit point
     * 
     * 1: cellVisitorStart = {}
     * 2: cellVisitorExit = {}
     * 
     * // set the start and end cell to maze entrance and exit respectively,
     *    push its to stack and mark its as visited cell
     * 3: startCell = maze.entrance
     * 4: endCell = maze.exit
     * 5: startPath.add(startCell)
     * 6: endPath.add(endCell)
     * 
     * // while startCell and endCell does not meet
     * 7: while(startCell does not meet with endCell)
     * 8:   check every possible neighbour of startCell and endCell,
     *      then move both of the cells randomly to one of it neigbours.
     *    end while
     *    
     *    // draw the recursive movement
     *    maze.drawFtPrt(currentCell);
     * 
     * ******************************************************************************************
     * 
     * @param maze Input Maze.
     */
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

		startPath.add(startCell);
		exitPath.add(endCell);

		// Mark both cell as visited
		cellVisitorStart[startCell.r][startCell.c] = true;
		cellVisitorExit[endCell.r][endCell.c] = true;

		// while the path lead to exit has not been found
		do
		{
			/*
			 * check for possible neighbour from starting point and mark them as
			 * visited.
			 * 
			 * if the next neighbour is already marked as visited from the
			 * frontier backward(exit point) then it mean we found a path from
			 * starting point to exit
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
			 * if the next neighbour is already marked as visited from the
			 * frontier forward(starting point) then it mean we found a path
			 * from starting point to exit
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

	/** 
     * Bi-Directional BFS Solver of input maze.
     * 
     * ******************************************************************************************
     * 
     * Bi-Directional BFS Solver
     * ALGORITHM hexMaze ( maze )
     * Perform a Bi-Directional BFS solver of a maze.
     * Input: Maze maze.
     * OUTPUT : a path lead from starting point to exit point
     * 
     * 1: cellVisitorStart = {}
     * 2: cellVisitorExit = {}
     * 
     * // set the start and end cell to maze entrance and exit respectively,
     *    push its to stack and mark its as visited cell
     * 3: startCell = maze.entrance
     * 4: endCell = maze.exit
     * 5: startPath.add(startCell)
     * 6: endPath.add(endCell)
     * 
     * // while startCell and endCell does not meet
     * 7: while(startCell does not meet with endCell)
     * 8:   check every possible neighbour of startCell and endCell,
     *      then move both of the cells randomly to one of it neigbours.
     *    end while
     *    
     *    // draw the recursive movement
     *    maze.drawFtPrt(currentCell);
     * 
     * ******************************************************************************************
     * 
     * @param maze Input Maze.
     */
	private void hexMaze(Maze maze)
	{
		// Creating boolean array to mark cells visited or unvisited
		boolean[][] cellVisitorStart = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
		boolean[][] cellVisitorExit = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

		/*
		 * Set the startCell as the entrance to the maze, endCell as the exit of
		 * the maze and enqueue its to the queue
		 */
		startCell = maze.entrance;
		endCell = maze.exit;

		startPath.add(startCell);
		exitPath.add(endCell);

		// Mark both cell as visited
		cellVisitorStart[startCell.r][startCell.c] = true;
		cellVisitorExit[endCell.r][endCell.c] = true;

		// while the path lead to exit has not been found
		do
		{
			/*
			 * check for possible neighbour from starting point and mark them as
			 * visited.
			 * 
			 * if the next neighbour is already marked as visited from the
			 * frontier backward(exit point) then it mean we found a path from
			 * starting point to exit
			 */
			if (startCell.wall[Maze.NORTHEAST].present == false)
			{
				if (cellVisitorExit[startCell.r + Maze.deltaR[Maze.NORTHEAST]][startCell.c
						+ Maze.deltaC[Maze.NORTHEAST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r + Maze.deltaR[Maze.NORTHEAST]][startCell.c
						+ Maze.deltaC[Maze.NORTHEAST]] != true)
				{
					startPath.add(maze.map[startCell.r + Maze.deltaR[Maze.NORTHEAST]][startCell.c
							+ Maze.deltaC[Maze.NORTHEAST]]);
					cellVisitorStart[startCell.r + Maze.deltaR[Maze.NORTHEAST]][startCell.c
							+ Maze.deltaC[Maze.NORTHEAST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[startCell.r + Maze.deltaR[Maze.NORTHEAST]][startCell.c + Maze.deltaC[Maze.NORTHEAST]]);
			}
			if (startCell.wall[Maze.NORTHWEST].present == false)
			{
				if (cellVisitorExit[startCell.r + Maze.deltaR[Maze.NORTHWEST]][startCell.c
						+ Maze.deltaC[Maze.NORTHWEST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r + Maze.deltaR[Maze.NORTHWEST]][startCell.c
						+ Maze.deltaC[Maze.NORTHWEST]] != true)
				{
					startPath.add(maze.map[startCell.r + Maze.deltaR[Maze.NORTHWEST]][startCell.c
							+ Maze.deltaC[Maze.NORTHWEST]]);
					cellVisitorStart[startCell.r + Maze.deltaR[Maze.NORTHWEST]][startCell.c
							+ Maze.deltaC[Maze.NORTHWEST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[startCell.r + Maze.deltaR[Maze.NORTHWEST]][startCell.c + Maze.deltaC[Maze.NORTHWEST]]);
			}
			if (startCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitorExit[startCell.r][startCell.c + 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r][startCell.c + 1] != true)
				{
					startPath.add(maze.map[startCell.r][startCell.c + 1]);
					cellVisitorStart[startCell.r][startCell.c + 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[startCell.r][startCell.c + 1]);
			}
			if (startCell.wall[Maze.SOUTHEAST].present == false)
			{
				if (cellVisitorExit[startCell.r + Maze.deltaR[Maze.SOUTHEAST]][startCell.c
						+ Maze.deltaC[Maze.SOUTHEAST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r + Maze.deltaR[Maze.SOUTHEAST]][startCell.c
						+ Maze.deltaC[Maze.SOUTHEAST]] != true)
				{
					startPath.add(maze.map[startCell.r + Maze.deltaR[Maze.SOUTHEAST]][startCell.c
							+ Maze.deltaC[Maze.SOUTHEAST]]);
					cellVisitorStart[startCell.r + Maze.deltaR[Maze.SOUTHEAST]][startCell.c
							+ Maze.deltaC[Maze.SOUTHEAST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[startCell.r + Maze.deltaR[Maze.SOUTHEAST]][startCell.c + Maze.deltaC[Maze.SOUTHEAST]]);
			}
			if (startCell.wall[Maze.SOUTHWEST].present == false)
			{
				if (cellVisitorExit[startCell.r + Maze.deltaR[Maze.SOUTHWEST]][startCell.c
						+ Maze.deltaC[Maze.SOUTHWEST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r + Maze.deltaR[Maze.SOUTHWEST]][startCell.c
						+ Maze.deltaC[Maze.SOUTHWEST]] != true)
				{
					startPath.add(maze.map[startCell.r + Maze.deltaR[Maze.SOUTHWEST]][startCell.c
							+ Maze.deltaC[Maze.SOUTHWEST]]);
					cellVisitorStart[startCell.r + Maze.deltaR[Maze.SOUTHWEST]][startCell.c
							+ Maze.deltaC[Maze.SOUTHWEST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[startCell.r + Maze.deltaR[Maze.SOUTHWEST]][startCell.c + Maze.deltaC[Maze.SOUTHWEST]]);
			}
			if (startCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitorExit[startCell.r][startCell.c - 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r][startCell.c - 1] != true)
				{
					startPath.add(maze.map[startCell.r][startCell.c - 1]);
					cellVisitorStart[startCell.r][startCell.c - 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[startCell.r][startCell.c - 1]);
			}
			/*
			 * end of checking possible neighbour from starting point and
			 * marking as visited
			 */

			/*
			 * check for possible neighbour from exit point and mark them as //
			 * visited.
			 * 
			 * if the next neighbour is already marked as visited from the
			 * frontier forward(starting point) then it mean we found a path
			 * from starting point to exit
			 */
			if (endCell.wall[Maze.NORTHEAST].present == false)
			{
				if (cellVisitorStart[endCell.r + Maze.deltaR[Maze.NORTHEAST]][endCell.c
						+ Maze.deltaC[Maze.NORTHEAST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r + Maze.deltaR[Maze.NORTHEAST]][endCell.c
						+ Maze.deltaC[Maze.NORTHEAST]] != true)
				{
					exitPath.add(
							maze.map[endCell.r + Maze.deltaR[Maze.NORTHEAST]][endCell.c + Maze.deltaC[Maze.NORTHEAST]]);
					cellVisitorExit[endCell.r + Maze.deltaR[Maze.NORTHEAST]][endCell.c
							+ Maze.deltaC[Maze.NORTHEAST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[endCell.r + Maze.deltaR[Maze.NORTHEAST]][endCell.c + Maze.deltaC[Maze.NORTHEAST]]);
			}
			if (endCell.wall[Maze.NORTHWEST].present == false)
			{
				if (cellVisitorStart[endCell.r + Maze.deltaR[Maze.NORTHWEST]][endCell.c
						+ Maze.deltaC[Maze.NORTHWEST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r + Maze.deltaR[Maze.NORTHWEST]][endCell.c
						+ Maze.deltaC[Maze.NORTHWEST]] != true)
				{
					exitPath.add(
							maze.map[endCell.r + Maze.deltaR[Maze.NORTHWEST]][endCell.c + Maze.deltaC[Maze.NORTHWEST]]);
					cellVisitorExit[endCell.r + Maze.deltaR[Maze.NORTHWEST]][endCell.c
							+ Maze.deltaC[Maze.NORTHWEST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[endCell.r + Maze.deltaR[Maze.NORTHWEST]][endCell.c + Maze.deltaC[Maze.NORTHWEST]]);

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
				}
				maze.drawFtPrt(maze.map[endCell.r][endCell.c + 1]);
			}
			if (endCell.wall[Maze.SOUTHEAST].present == false)
			{
				if (cellVisitorStart[endCell.r + Maze.deltaR[Maze.SOUTHEAST]][endCell.c
						+ Maze.deltaC[Maze.SOUTHEAST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r + Maze.deltaR[Maze.SOUTHEAST]][endCell.c
						+ Maze.deltaC[Maze.SOUTHEAST]] != true)
				{
					exitPath.add(
							maze.map[endCell.r + Maze.deltaR[Maze.SOUTHEAST]][endCell.c + Maze.deltaC[Maze.SOUTHEAST]]);
					cellVisitorExit[endCell.r + Maze.deltaR[Maze.SOUTHEAST]][endCell.c
							+ Maze.deltaC[Maze.SOUTHEAST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[endCell.r + Maze.deltaR[Maze.SOUTHEAST]][endCell.c + Maze.deltaC[Maze.SOUTHEAST]]);
			}
			if (endCell.wall[Maze.SOUTHWEST].present == false)
			{
				if (cellVisitorStart[endCell.r + Maze.deltaR[Maze.SOUTHWEST]][endCell.c
						+ Maze.deltaC[Maze.SOUTHWEST]] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r + Maze.deltaR[Maze.SOUTHWEST]][endCell.c
						+ Maze.deltaC[Maze.SOUTHWEST]] != true)
				{
					exitPath.add(
							maze.map[endCell.r + Maze.deltaR[Maze.SOUTHWEST]][endCell.c + Maze.deltaC[Maze.SOUTHWEST]]);
					cellVisitorExit[endCell.r + Maze.deltaR[Maze.SOUTHWEST]][endCell.c
							+ Maze.deltaC[Maze.SOUTHWEST]] = true;
					step++;
				}
				maze.drawFtPrt(
						maze.map[endCell.r + Maze.deltaR[Maze.SOUTHWEST]][endCell.c + Maze.deltaC[Maze.SOUTHWEST]]);

			}
			if (endCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitorStart[endCell.r][endCell.c - 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r][endCell.c - 1] != true)
				{
					exitPath.add(maze.map[endCell.r][endCell.c - 1]);
					cellVisitorExit[endCell.r][endCell.c - 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[endCell.r][endCell.c - 1]);
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
	// end of hexMaze()

	/** 
     * Bi-Directional BFS Solver of input maze.
     * 
     * Tunnel Maze Bi-Directional BFS Solver condition when hit the tunnel cell:
     *   -->If the startCell or endCell hit a tunnel when move randomly, it will directly goes to
     *      the other end of the tunnel and continue solving the maze from that point.
     *      
     * ******************************************************************************************
     * 
     * Bi-Directional BFS Solver
     * ALGORITHM tunnelMaze ( maze )
     * Perform a Bi-Directional BFS solver of a maze.
     * Input: Maze maze.
     * OUTPUT : a path lead from starting point to exit point
     * 
     * 1: cellVisitorStart = {}
     * 2: cellVisitorExit = {}
     * 
     * // set the start and end cell to maze entrance and exit respectively,
     *    push its to stack and mark its as visited cell
     * 3: startCell = maze.entrance
     * 4: endCell = maze.exit
     * 5: startPath.add(startCell)
     * 6: endPath.add(endCell)
     * 
     * // while startCell and endCell does not meet
     * 7: while(startCell does not meet with endCell)
     * 8:   check every possible neighbour of startCell and endCell,
     *      then move both of the cells randomly to one of it neigbours.
     *    end while
     *    
     *    // draw the recursive movement
     *    maze.drawFtPrt(currentCell);
     * 
     * ******************************************************************************************
     * 
     * @param maze Input Maze.
     */
	private void tunnelMaze(Maze maze)
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

		//startPath.add(startCell);
		//exitPath.add(endCell);

		// Mark both cell as visited
		cellVisitorStart[startCell.r][startCell.c] = true;
		cellVisitorExit[endCell.r][endCell.c] = true;

		// while the path lead to exit has not been found
		do
		{
			/*
			 * check for possible neighbour from starting point and mark them as
			 * visited.
			 * 
			 * if the next neighbour is already marked as visited from the
			 * frontier backward(exit point) then it mean we found a path from
			 * starting point to exit
			 */
			if (startCell.tunnelTo != null)
			{
				if(cellVisitorStart[startCell.tunnelTo.r][startCell.tunnelTo.c] != true)
				{
					if (cellVisitorExit[startCell.tunnelTo.r][startCell.tunnelTo.c] == true)
					{
						foundPath = true;
					}
					else if (cellVisitorStart[startCell.tunnelTo.r][startCell.tunnelTo.c] != true)
					{
						startPath.add(maze.map[startCell.tunnelTo.r][startCell.tunnelTo.c]);
						cellVisitorStart[startCell.tunnelTo.r][startCell.tunnelTo.c] = true;
						step++;
					}
					maze.drawFtPrt(maze.map[startCell.tunnelTo.r][startCell.tunnelTo.c]);
				}
			}
			if (startCell.wall[Maze.NORTH].present == false)
			{
				if (cellVisitorExit[startCell.r + 1][startCell.c] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r + 1][startCell.c] != true)
				{
					startPath.add(maze.map[startCell.r + 1][startCell.c]);
					cellVisitorStart[startCell.r + 1][startCell.c] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[startCell.r + 1][startCell.c]);
			}
			if (startCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitorExit[startCell.r][startCell.c + 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r][startCell.c + 1] != true)
				{
					startPath.add(maze.map[startCell.r][startCell.c + 1]);
					cellVisitorStart[startCell.r][startCell.c + 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[startCell.r][startCell.c + 1]);
			}
			if (startCell.wall[Maze.SOUTH].present == false)
			{
				if (cellVisitorExit[startCell.r - 1][startCell.c] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r - 1][startCell.c] != true)
				{
					startPath.add(maze.map[startCell.r - 1][startCell.c]);
					cellVisitorStart[startCell.r - 1][startCell.c] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[startCell.r - 1][startCell.c]);
			}
			if (startCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitorExit[startCell.r][startCell.c - 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorStart[startCell.r][startCell.c - 1] != true)
				{
					startPath.add(maze.map[startCell.r][startCell.c - 1]);
					cellVisitorStart[startCell.r][startCell.c - 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[startCell.r][startCell.c - 1]);
			}
			/*
			 * end of checking possible neighbour from starting point and
			 * marking as visited
			 */

			/*
			 * check for possible neighbour from exit point and mark them as
			 * visited.
			 * 
			 * if the next neighbour is already marked as visited from the
			 * frontier forward(starting point) then it mean we found a path
			 * from starting point to exit
			 */
			if (endCell.tunnelTo != null)
			{
				if(cellVisitorExit[endCell.tunnelTo.r][endCell.tunnelTo.c] != true)
				{
					
					if (cellVisitorStart[endCell.tunnelTo.r][endCell.tunnelTo.c] == true)
					{
						foundPath = true;
					}
					else if (cellVisitorExit[endCell.tunnelTo.r][endCell.tunnelTo.c] != true)
					{
						exitPath.add(maze.map[endCell.tunnelTo.r][endCell.tunnelTo.c]);
						cellVisitorExit[endCell.tunnelTo.r][endCell.tunnelTo.c] = true;
						step++;
					}
					maze.drawFtPrt(maze.map[endCell.tunnelTo.r][endCell.tunnelTo.c]);
				}
			}
			if (endCell.wall[Maze.NORTH].present == false)
			{
				if (cellVisitorStart[endCell.r + 1][endCell.c] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r + 1][endCell.c] != true)
				{
					exitPath.add(maze.map[endCell.r + 1][endCell.c]);
					cellVisitorExit[endCell.r + 1][endCell.c] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[endCell.r + 1][endCell.c]);
			}
			if (endCell.wall[Maze.EAST].present == false)
			{
				if (cellVisitorStart[endCell.r][endCell.c + 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r][endCell.c + 1] != true)
				{
					exitPath.add(maze.map[endCell.r][endCell.c + 1]);
					cellVisitorExit[endCell.r][endCell.c + 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[endCell.r][endCell.c + 1]);
			}
			if (endCell.wall[Maze.SOUTH].present == false)
			{
				if (cellVisitorStart[endCell.r - 1][endCell.c] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r - 1][endCell.c] != true)
				{
					exitPath.add(maze.map[endCell.r - 1][endCell.c]);
					cellVisitorExit[endCell.r - 1][endCell.c] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[endCell.r - 1][endCell.c]);
			}
			if (endCell.wall[Maze.WEST].present == false)
			{
				if (cellVisitorStart[endCell.r][endCell.c - 1] == true)
				{
					foundPath = true;
				}
				else if (cellVisitorExit[endCell.r][endCell.c - 1] != true)
				{
					exitPath.add(maze.map[endCell.r][endCell.c - 1]);
					cellVisitorExit[endCell.r][endCell.c - 1] = true;
					step++;
				}
				maze.drawFtPrt(maze.map[endCell.r][endCell.c - 1]);
			}
			/*
			 * end of checking possible neighbour from exit point and
			 * marking as visited
			 */
			
			
			// Get the next cells from the queue
			startCell = startPath.poll();
			endCell = exitPath.poll();

		} while (foundPath == false);
	}
	// end of tunnelMaze()

} // end of class BiDirectionalBFSSolver
