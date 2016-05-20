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
		
		// The frontier set of cells that can be added to form a passage
		ArrayList<Cell> frontier = new ArrayList<Cell>();
		ArrayList<Cell> passage = new ArrayList<Cell>();
		Cell startCell = null;
		
		/*
		 *  Set the current cell to the entrance of the maze, add it to the cells
		 *  that are in passage.
		 *  
		 *  Add the maze entrance adjacent cells to frontier set to begin with
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
		 * While loop will iterate until all cells in the maze have been added
		 * to passage, which indicates the maze is complete
		 */
		while(passage.size() != maze.map.length*maze.map[0].length)
		{
			Cell chosenFrontier = null;
			Cell chosenPassage = null;
			Random rand = new Random();
			
			// Randomly choose a cell that is in the frontier set
			chosenFrontier = frontier.get(rand.nextInt(frontier.size()));
			frontier.remove(chosenFrontier);
			System.out.println("frontier removed");
			
			//System.out.println("chosen frontier is: " + chosenFrontier.r + " " + chosenFrontier.c);
			
			/*
			 *  Randomly choose a cell that is already in passage, and is adjacent
			 *  to chosenFrontier
			 */
			while(chosenPassage == null)
			{
				System.out.println("got a new passage");
				chosenPassage = passage.get(rand.nextInt(passage.size()));
				/*
				 * Each if statement will find which direction the chosenFrontier cell is
				 * adjacent to the chosenPassage cell
				 */
				try
				{
					System.out.println("on line 101");
					if(maze.map[chosenPassage.r+1][chosenPassage.c]==chosenFrontier)
					{
						System.out.println("gone north");
						chosenPassage.wall[Maze.NORTH].present = false;
						passage.add(chosenPassage);
						System.out.println("passage added");
					}
					
					else if(maze.map[chosenPassage.r-1][chosenPassage.c]==chosenFrontier)
					{
						System.out.println("gone south");
						chosenPassage.wall[Maze.SOUTH].present = false;
						passage.add(chosenPassage);
						System.out.println("passage added");
					}
					
					else if(maze.map[chosenPassage.r][chosenPassage.c+1]==chosenFrontier)
					{
						System.out.println("gone east");
						chosenPassage.wall[Maze.EAST].present = false;
						passage.add(chosenPassage);
						System.out.println("passage added");
					}
					
					else if(maze.map[chosenPassage.r][chosenPassage.c-1]==chosenFrontier)
					{
						System.out.println("gone west");
						chosenPassage.wall[Maze.WEST].present = false;
						passage.add(chosenPassage);
						System.out.println("passage added");
					}
				}
				catch(Exception e)
				{
					/*
					 * Allows us to reference the areas that are out of bounds of maze.map.
					 * Allows the while loop to continue until the adjacent cell is found.
					 */
					System.out.println("tried to get null");
					//chosenPassage = null;
					continue;
				}
				System.out.println("on line 144");
			}
				
			
			if (chosenFrontier.c >= 0 && chosenFrontier.c <= maze.sizeC && chosenFrontier.r >= 0
					&& startCell.r < maze.sizeR - 1 && passage.contains(chosenFrontier.neigh[Maze.NORTH]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.NORTH]);
				System.out.println("frontier added");
			}
			if (chosenFrontier.c >= 0 && chosenFrontier.c < maze.sizeC - 1 && chosenFrontier.r >= 0
					&& chosenFrontier.r <= maze.sizeR && passage.contains(chosenFrontier.neigh[Maze.EAST]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.EAST]);
				System.out.println("frontier added");
			}
			if (chosenFrontier.c >= 0 && chosenFrontier.c <= maze.sizeC && chosenFrontier.r > 0 
					&& chosenFrontier.r <= maze.sizeR && passage.contains(chosenFrontier.neigh[Maze.SOUTH]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.SOUTH]);
				System.out.println("frontier added");
			}
			if (chosenFrontier.c > 0 && chosenFrontier.c <= maze.sizeC && chosenFrontier.r >= 0 
					&& chosenFrontier.r <= maze.sizeR && passage.contains(chosenFrontier.neigh[Maze.WEST]) == false)
			{
				frontier.add(chosenFrontier.neigh[Maze.WEST]);
				System.out.println("frontier added");
			}
			
			System.out.println(frontier.size());
			System.out.println(passage.size());
			
			//DEBUGGING
			//break;
		}
		
		
	} // end of class ModifiedPrimsGenerator

	private void hexMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}

	private void tunnelMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}
}