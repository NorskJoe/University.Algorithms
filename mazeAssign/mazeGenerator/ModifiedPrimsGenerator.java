package mazeGenerator;

import java.util.ArrayList;
import java.util.Random;

import maze.Cell;
import maze.Maze;
import maze.Wall;

public class ModifiedPrimsGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
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

	private void normalMaze(Maze maze) {
		
		/*
		 *  The frontier set of cells that can be added to form a passage.
		 *  The passage set of cells that have already been visited.
		 */
		ArrayList<Cell> frontier = new ArrayList<Cell>();
		ArrayList<Cell> passage = new ArrayList<Cell>();
		Cell startCell = null;
		
		/*
		 *  Set the current cell to the entrance of the maze, add it to the cells
		 *  that are in passage.
		 *  
		 *  Add the maze entrance's adjacent cells to frontier set to begin with.
		 */
		startCell = maze.entrance;
		passage.add(startCell);
		
		if (startCell.c >= 0 && startCell.c <= maze.sizeC && startCell.r >= 0
				&& startCell.r < maze.sizeR - 1)
		{
			frontier.add(startCell.neigh[Maze.NORTH]);
		}
		if (startCell.c >= 0 && startCell.c < maze.sizeC - 1 && startCell.r >= 0
				&& startCell.r <= maze.sizeR)
		{
			frontier.add(startCell.neigh[Maze.EAST]);
		}
		if (startCell.c >= 0 && startCell.c <= maze.sizeC && startCell.r > 0 
				&& startCell.r <= maze.sizeR)
		{
			frontier.add(startCell.neigh[Maze.SOUTH]);
		}
		if (startCell.c > 0 && startCell.c <= maze.sizeC && startCell.r >= 0 
				&& startCell.r <= maze.sizeR)
		{
			frontier.add(startCell.neigh[Maze.WEST]);
		}
		
		/*
		 * Main body of function.
		 * 
		 * While loop will iterate until all cells in the maze have been added
		 * to passage, which indicates the maze is complete
		 */
		while(passage.size() != maze.sizeR*maze.sizeC)
		{
			Cell chosenFrontier = null;
			Cell chosenPassage = null;
			Random rand = new Random();
			
			// Randomly choose a cell that is in the frontier set
			chosenFrontier = frontier.get(rand.nextInt(frontier.size()));
			frontier.remove(chosenFrontier);
			
			/*
			 *  Randomly choose a cell that is already in passage, and is adjacent
			 *  to the cell we chose from the frontier set
			 *  
			 *  Once adjacent cells are found, carve a passage and add the frontier cell
			 *  to the set of passage cells
			 */
			boolean foundPair = false;
			do
			{
				chosenPassage = passage.get(rand.nextInt(passage.size()));
				
				if(chosenPassage.neigh[Maze.NORTH]==chosenFrontier)
				{
					chosenPassage.wall[Maze.NORTH].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.EAST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.EAST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.SOUTH]==chosenFrontier)
				{
					chosenPassage.wall[Maze.SOUTH].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.WEST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.WEST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
			}while(foundPair == false);
				
			/*
			 * Add the new frontier cells to the frontier set.  Will only add cells
			 * that are in the range of the maze
			 */
			if (chosenFrontier.c >= 0 && chosenFrontier.c <= maze.sizeC && chosenFrontier.r >= 0
					&& chosenFrontier.r < maze.sizeR - 1 && passage.contains(chosenFrontier.neigh[Maze.NORTH]) == false
					&& frontier.contains(chosenFrontier.neigh[Maze.NORTH]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.NORTH]);				
			}
			if (chosenFrontier.c >= 0 && chosenFrontier.c < maze.sizeC - 1 && chosenFrontier.r >= 0
					&& chosenFrontier.r <= maze.sizeR && passage.contains(chosenFrontier.neigh[Maze.EAST]) == false
					&& frontier.contains(chosenFrontier.neigh[Maze.EAST]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.EAST]);				
			}
			if (chosenFrontier.c >= 0 && chosenFrontier.c <= maze.sizeC && chosenFrontier.r > 0 
					&& chosenFrontier.r <= maze.sizeR && passage.contains(chosenFrontier.neigh[Maze.SOUTH]) == false
					&& frontier.contains(chosenFrontier.neigh[Maze.SOUTH]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.SOUTH]);				
			}
			if (chosenFrontier.c > 0 && chosenFrontier.c <= maze.sizeC && chosenFrontier.r >= 0 
					&& chosenFrontier.r <= maze.sizeR && passage.contains(chosenFrontier.neigh[Maze.WEST]) == false
					&& frontier.contains(chosenFrontier.neigh[Maze.WEST]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.WEST]);
			}
			
		}
	} // end of class ModifiedPrimsGenerator

	private void hexMaze(Maze maze) {
		/*
		 *  The frontier set of cells that can be added to form a passage.
		 *  The passage set of cells that have already been visited.
		 */
		ArrayList<Cell> frontier = new ArrayList<Cell>();
		ArrayList<Cell> passage = new ArrayList<Cell>();
		Cell startCell = null;
		
		/*
		 *  Set the current cell to the entrance of the maze, add it to the cells
		 *  that are in passage.
		 *  
		 *  Add the maze entrance's adjacent cells to frontier set to begin with.
		 *  Each if statement checks the bounds of the maze to see if there is a 
		 *  valid neighbour
		 */
		startCell = maze.entrance;
		passage.add(startCell);
		
		// Checking northern neighbours
		if(startCell.r < maze.sizeR - 1)
		{
			if(maze.map[startCell.r+Maze.deltaR[Maze.NORTHEAST]]
					[startCell.c+Maze.deltaC[Maze.NORTHEAST]] != null)
			{
				frontier.add(startCell.neigh[Maze.NORTHEAST]);
			}
			if(maze.map[startCell.r+Maze.deltaR[Maze.NORTHWEST]]
					[startCell.c+Maze.deltaC[Maze.NORTHWEST]] != null)
			{
				frontier.add(startCell.neigh[Maze.NORTHWEST]);
			}
		}
		// Checking southern neighbours
		if(startCell.r > 0)
		{
			if(maze.map[startCell.r+Maze.deltaR[Maze.SOUTHEAST]]
					[startCell.c+Maze.deltaC[Maze.SOUTHEAST]] != null)
			{
				frontier.add(startCell.neigh[Maze.SOUTHEAST]);
			}
			if(maze.map[startCell.r+Maze.deltaR[Maze.SOUTHWEST]]
					[startCell.c+Maze.deltaC[Maze.SOUTHWEST]] != null)
			{
				frontier.add(startCell.neigh[Maze.SOUTHWEST]);
			}
		}
		// Checking eastern neighbour
		if(startCell.c < (maze.sizeC+1)/2+maze.sizeC-1
				&& maze.map[startCell.r+Maze.deltaR[Maze.EAST]]
						[startCell.c+Maze.deltaC[Maze.EAST]] != null)
		{
			frontier.add(startCell.neigh[Maze.EAST]);
		}
		// Checking western neighbour
		if(startCell.c > 0 && maze.map[startCell.r+Maze.deltaR[Maze.WEST]]
				[startCell.c+Maze.deltaC[Maze.WEST]] != null)
		{
			frontier.add(startCell.neigh[Maze.WEST]);
		}
		
		/*
		 * Main body of function.
		 * 
		 * While loop will iterate until all cells in the maze have been added
		 * to passage, which indicates the maze is complete
		 */
		while(passage.size() != maze.sizeR*maze.sizeC)
		{
			Cell chosenFrontier = null;
			Cell chosenPassage = null;
			Random rand = new Random();
			
			// Randomly choose a cell that is in the frontier set
			chosenFrontier = frontier.get(rand.nextInt(frontier.size()));
			frontier.remove(chosenFrontier);
			
			/*
			 *  Randomly choose a cell that is already in passage, and is adjacent
			 *  to the cell we chose from the frontier set
			 *  
			 *  Once adjacent cells are found, carve a passage and add the frontier cell
			 *  to the set of passage cells
			 */
			boolean foundPair = false;
			do
			{
				chosenPassage = passage.get(rand.nextInt(passage.size()));
				
				if(chosenPassage.neigh[Maze.NORTHEAST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.NORTHEAST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.NORTHWEST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.NORTHWEST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.EAST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.EAST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.SOUTHEAST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.SOUTHEAST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.SOUTHWEST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.SOUTHWEST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
				if(chosenPassage.neigh[Maze.WEST]==chosenFrontier)
				{
					chosenPassage.wall[Maze.WEST].present = false;
					passage.add(chosenFrontier);
					foundPair = true;
				}
			}while(foundPair == false);
				
			/*
			 * Add the new frontier cells to the frontier set.  Will only add cells
			 * that are in the range of the maze
			 */
			// Checking northern neighbours
			if(chosenFrontier.r < maze.sizeR - 1)
			{
				if(maze.map[chosenFrontier.r+Maze.deltaR[Maze.NORTHEAST]]
							[chosenFrontier.c+Maze.deltaC[Maze.NORTHEAST]] != null
						&& passage.contains(chosenFrontier.neigh[Maze.NORTHEAST])==false
						&& frontier.contains(chosenFrontier.neigh[Maze.NORTHEAST])==false)
				{
					frontier.add(chosenFrontier.neigh[Maze.NORTHEAST]);
				}
				if(maze.map[chosenFrontier.r+Maze.deltaR[Maze.NORTHWEST]]
							[chosenFrontier.c+Maze.deltaC[Maze.NORTHWEST]] != null
						&& passage.contains(chosenFrontier.neigh[Maze.NORTHWEST])==false
						&& frontier.contains(chosenFrontier.neigh[Maze.NORTHWEST])==false)
				{
					frontier.add(chosenFrontier.neigh[Maze.NORTHWEST]);
				}
			}
			// Checking southern neighbours
			if(chosenFrontier.r > 0)
			{
				if(maze.map[chosenFrontier.r+Maze.deltaR[Maze.SOUTHEAST]]
							[chosenFrontier.c+Maze.deltaC[Maze.SOUTHEAST]] != null
						&& passage.contains(chosenFrontier.neigh[Maze.SOUTHEAST])==false
						&& frontier.contains(chosenFrontier.neigh[Maze.SOUTHEAST])==false)
				{
					frontier.add(chosenFrontier.neigh[Maze.SOUTHEAST]);
				}
				if(maze.map[chosenFrontier.r+Maze.deltaR[Maze.SOUTHWEST]]
							[chosenFrontier.c+Maze.deltaC[Maze.SOUTHWEST]] != null
						&& passage.contains(chosenFrontier.neigh[Maze.SOUTHWEST])==false
						&& frontier.contains(chosenFrontier.neigh[Maze.SOUTHWEST])==false)
				{
					frontier.add(chosenFrontier.neigh[Maze.SOUTHWEST]);
				}
			}
			// Checking eastern neighbour
			if(chosenFrontier.c < (maze.sizeC+1)/2+maze.sizeC-1
					&& maze.map[chosenFrontier.r+Maze.deltaR[Maze.EAST]]
							[chosenFrontier.c+Maze.deltaC[Maze.EAST]] != null
					&& passage.contains(chosenFrontier.neigh[Maze.EAST])==false
					&& frontier.contains(chosenFrontier.neigh[Maze.EAST])==false)
			{
				frontier.add(chosenFrontier.neigh[Maze.EAST]);
			}
			// Checking western neighbour
			if(chosenFrontier.c > 0 && maze.map[chosenFrontier.r+Maze.deltaR[Maze.WEST]]
						[chosenFrontier.c+Maze.deltaC[Maze.WEST]] != null
					&& passage.contains(chosenFrontier.neigh[Maze.WEST])==false
					&& frontier.contains(chosenFrontier.neigh[Maze.WEST])==false)
			{
				frontier.add(chosenFrontier.neigh[Maze.WEST]);
			}
		}
	}

	private void tunnelMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}
}