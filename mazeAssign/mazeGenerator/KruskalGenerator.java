package mazeGenerator;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import maze.Cell;
import maze.Maze;
import maze.Wall;

public class KruskalGenerator implements MazeGenerator {


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
		 * First add an edge for every pair of adjacent cells to a list.
		 * 
		 * Iterate through the whole maze, getting all adjacent pairs and add the 
		 * pair to the list.
		 * Each edge is represented as an adjacent pair of cells.
		 */
		ArrayList<ArrayList<Cell>> edges = new ArrayList<ArrayList<Cell>>();
		ArrayList<TreeNode<Cell>> trees = new ArrayList<TreeNode<Cell>>();
		for(int i = 0; i < maze.sizeR; i++)
			for(int j = 0; j < maze.sizeC; j ++)
			{
				Cell currentCell = maze.map[i][j];
				
				// Checking neighbour on north side of cell
				if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0
						&& currentCell.r < maze.sizeR - 1 && edges.contains(currentCell.wall[Maze.NORTH])==false)
				{
					ArrayList<Cell> temp = new ArrayList<Cell>();
					temp.add(currentCell);
					temp.add(currentCell.neigh[Maze.NORTH]);
					edges.add(temp);
				}
				// Checking neighbour on east side of cell
				if (currentCell.c >= 0 && currentCell.c < maze.sizeC - 1 && currentCell.r >= 0
						&& currentCell.r <= maze.sizeR && edges.contains(currentCell.wall[Maze.EAST])==false)
				{
					ArrayList<Cell> temp = new ArrayList<Cell>();
					temp.add(currentCell);
					temp.add(currentCell.neigh[Maze.EAST]);
					edges.add(temp);
				}
				// Checking neighbour on south side of cell
				if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r > 0 && currentCell.r <= maze.sizeR
						&& edges.contains(currentCell.wall[Maze.SOUTH])==false)
				{
					ArrayList<Cell> temp = new ArrayList<Cell>();
					temp.add(currentCell);
					temp.add(currentCell.neigh[Maze.SOUTH]);
					edges.add(temp);
				}
				// Checking neighbour on west side of cell
				if (currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 && currentCell.r <= maze.sizeR
						&& edges.contains(currentCell.wall[Maze.WEST])==false)
				{
					ArrayList<Cell> temp = new ArrayList<Cell>();
					temp.add(currentCell);
					temp.add(currentCell.neigh[Maze.WEST]);
					edges.add(temp);
				}
				TreeNode<Cell> cell = new TreeNode<Cell>(currentCell);
				trees.add(cell);
			}
		
		/*
		 * Continue to choose random edges until there are none left
		 */
		while(!edges.isEmpty())
		{
			
			System.out.println(edges.size());
			
			Random rand = new Random();
			
			ArrayList<Cell> temp = edges.get(rand.nextInt(edges.size()));
			Cell cellOne = temp.get(0);
			Cell cellTwo = temp.get(1);
			
			TreeNode<Cell> first = null;
			TreeNode<Cell> second = null;
			
			for(TreeNode<Cell> t : trees)
			{
				if(t.cell==cellOne)
				{
					first = t;
				}
				if(t.cell==cellTwo){
					second = t;
				}
			}
			
			System.out.println(first.connections.size());
			
			if(first.testConnected(second)==false)
			{				
				if(cellOne.neigh[Maze.NORTH]==cellTwo)
				{
					cellOne.wall[Maze.NORTH].present = false;
					first.connect(second);
				}
				if(cellOne.neigh[Maze.EAST]==cellTwo)
				{
					cellOne.wall[Maze.EAST].present = false;
					first.connect(second);
				}
				if(cellOne.neigh[Maze.SOUTH]==cellTwo)
				{
					cellOne.wall[Maze.SOUTH].present = false;
					first.connect(second);
				}
				if(cellOne.neigh[Maze.WEST]==cellTwo)
				{
					cellOne.wall[Maze.WEST].present = false;
					first.connect(second);
				}
				
				edges.remove(temp);
			}
			//trees.remove(second);
			if(edges.size()==40)break;
		}
		
	}// End of normal generator

	private void hexMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// End of hex generator

	private void tunnelMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// End of tunnel generator
	
	/*
	 * Tree data structure used to compare sets of cells
	 */
	public class TreeNode<T> 
	{
	     private T cell;
	     private ArrayList<TreeNode<T>> connections = new ArrayList<TreeNode<T>>();
	     
	     public TreeNode(T cell)
	     {
	    	 this.cell = cell;
	     }
	     
	     public TreeNode<T> addChild(T child)
	     {
	    	 TreeNode<T> childNode = new TreeNode<T>(child);
	    	 return childNode;
	     }
	     
	     public boolean testConnected(TreeNode<T> treeTwo)
	     {
	    	 
	    	 if(this.connections.contains(treeTwo) || treeTwo.connections.contains(this))
	    	 {
	    		 return true;
	    	 }
	    	 else
	    	 {
	    		 return false;	    		 
	    	 }
	     }
	     
	     public void connect(TreeNode<T> tree)
	     {
	    	 this.connections.add(tree);
	     }
	}
	
	

} // end of class KruskalGenerator


