package mazeGenerator;


import java.util.ArrayList;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class KruskalGenerator implements MazeGenerator {

	/*
	 * List of edges to be used to generate paths.
	 * 
	 * List of sets will be used to join cells together when a path exists
	 */
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<ArrayList<Tree>> sets = new ArrayList<ArrayList<Tree>>();
	
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
		 * Initialisation part of algorithm
		 * 
		 * (1) For each pair of adjacent cells, create an edge
		 * (2) All edges are stored in a set (ArrayList)
		 * 
		 * South and east edges are not checked because it would cause some
		 * edges to be added twice
		 */
		Edge pair = null;
		for(int i = 0; i < maze.sizeR; i++)
		{
			ArrayList<Tree> tempSet = new ArrayList<Tree>();
			for(int j = 0; j < maze.sizeC; j++)
			{
				Cell currentCell = maze.map[i][j];
				tempSet.add(new Tree());
				
				// Checking neighbour on north side of cell
				if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0
						&& currentCell.r < maze.sizeR - 1)
				{
					pair = new Edge(currentCell, currentCell.neigh[Maze.NORTH]);
					if(edges.contains(pair)==false)
					{
						edges.add(pair);						
					}
				}

				// Checking neighbour on west side of cell
				if (currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 
						&& currentCell.r <= maze.sizeR)
				{
					pair = new Edge(currentCell, currentCell.neigh[Maze.WEST]);
					if(edges.contains(pair)==false)
					{
						edges.add(pair);						
					}
				}
			}
			sets.add(tempSet);
		}
		/**
		 * Main body of algorithm
		 * ******************************************************************
		 * 
		 * INPUT: Maze[][], List<Edges>, List<Trees>, Cell currCell
		 * OUTPUT: A perfectly generated maze
		 * 
		 * (1) Randomly choose an edge (two adjacent pairs)
		 * (2) If the edge will join two disjoint trees, merge the trees
		 * (3) Carve a path between the two adjacent cells from the edge
		 * (4) Repeat these steps until there are no edges left
		 */
		while(!edges.isEmpty())
		{
			// Randomly select an edge
			Random rand = new Random();
			Edge edge = edges.get(rand.nextInt(edges.size()));
			Cell cellOne = edge.cellOne;
			Cell cellTwo = edge.cellTwo;
			
			// Get the corresponding sets for the two cells
			Tree set1 = (sets.get(cellOne.r)).get(cellOne.c);
			Tree set2 = (sets.get(cellTwo.r)).get(cellTwo.c);
			
			// If the sets are not connected, carve a path and connect them
			if(!set1.connected(set2))
			{
				// Check in which direction the pairs are joined, starting from cellOne
				if(cellOne.neigh[Maze.NORTH]==cellTwo)
				{
					cellOne.wall[Maze.NORTH].present = false;
					
				}
				else if(cellOne.neigh[Maze.EAST]==cellTwo)
				{
					cellOne.wall[Maze.EAST].present = false;
					
				}
				else if(cellOne.neigh[Maze.SOUTH]==cellTwo)
				{
					cellOne.wall[Maze.SOUTH].present = false;
					
				}
				else if(cellOne.neigh[Maze.WEST]==cellTwo)
				{
					cellOne.wall[Maze.WEST].present = false;
					
				}
				set1.connect(set2);
			}
			/*
			 * Always remove any edges that are checked, if they are rejected they
			 * should be removed from the list and not considered again.
			 */
			edges.remove(edge);
		}
		
	}// End of normal generator

	private void hexMaze(Maze maze) {
		/*
		 * Initialisation part of algorithm
		 * 
		 * (1) For each pair of adjacent cells, create an edge
		 * (2) All edges are stored in a set (ArrayList)
		 * 
		 * South and east edges are not checked because it would cause some
		 * edges to be added twice
		 */
		// Get the actual size of the columns, different for hex mazes
		int realSizeC = (maze.sizeC+1)/2+maze.sizeC;
		Edge pair = null;
		for(int i = 0; i < maze.sizeR; i++)
		{
			/* 
			 * tempSet is used to store all the Trees for this row.
			 * Sets are nested for easy retrieval of cells later.
			 */
			ArrayList<Tree> tempSet = new ArrayList<Tree>();
			for(int j = 0; j < realSizeC; j++)
			{
				Cell currentCell = maze.map[i][j];
				tempSet.add(new Tree());
				
				if(currentCell!=null)
				{			
					// Checking neighbour on northwest side of cell
					if (currentCell.r < maze.sizeR - 1 && maze.map[currentCell.r+Maze.deltaR[Maze.NORTHWEST]]
							[currentCell.c+Maze.deltaC[Maze.NORTHWEST]] != null)
					{
						pair = new Edge(currentCell, currentCell.neigh[Maze.NORTHWEST]);
						edges.add(pair);	
					}
					
					// Checking neighbour on west side of cell
					if(currentCell.c > 0
							&& maze.map[currentCell.r+Maze.deltaR[Maze.WEST]]
									[currentCell.c+Maze.deltaC[Maze.WEST]] != null)
					{
						pair = new Edge(currentCell, currentCell.neigh[Maze.WEST]);
						edges.add(pair);
					}
					
					// Checking neighbour on southwest side of cell
					if (currentCell.r > 0 && maze.map[currentCell.r+Maze.deltaR[Maze.SOUTHWEST]]
							[currentCell.c+Maze.deltaC[Maze.SOUTHWEST]] != null)
					{
						pair = new Edge(currentCell, currentCell.neigh[Maze.SOUTHWEST]);
						edges.add(pair);	
					}
				}
			}// End inner for-loop
			sets.add(tempSet);
				
		}
		/**
		 * Main body of algorithm
		 * ******************************************************************
		 * 
		 * INPUT: Maze[][], List<Edges>, List<Trees>, Cell currCell
		 * OUTPUT: A perfectly generated maze
		 * 
		 * (1) Randomly choose an edge (two adjacent pairs)
		 * (2) If the edge will join two disjoint trees, merge the trees
		 * (3) Carve a path between the two adjacent cells from the edge
		 * (4) Repeat these steps until there are no edges left
		 */
		while(!edges.isEmpty())
		{
			// Randomly select an edge
			Random rand = new Random();
			Edge edge = edges.get(rand.nextInt(edges.size()));
			Cell cellOne = edge.cellOne;
			Cell cellTwo = edge.cellTwo;
			
			
			// Get the corresponding sets for the two cells
			Tree set1 = (sets.get(cellOne.r)).get(cellOne.c);
			Tree set2 = (sets.get(cellTwo.r)).get(cellTwo.c);
			
			
			// If the sets are not connected, carve a path and connect them
			if(!set1.connected(set2))
			{
				
				if(cellOne.neigh[Maze.NORTHWEST]==cellTwo)
				{
					cellOne.wall[Maze.NORTHWEST].present = false;
					
				}
				else if(cellOne.neigh[Maze.NORTHEAST]==cellTwo)
				{
					cellOne.wall[Maze.NORTHEAST].present = false;
					
				}
				else if(cellOne.neigh[Maze.EAST]==cellTwo)
				{
					cellOne.wall[Maze.EAST].present = false;
					
				}
				else if(cellOne.neigh[Maze.SOUTHEAST]==cellTwo)
				{
					cellOne.wall[Maze.SOUTHEAST].present = false;
					
				}
				else if(cellOne.neigh[Maze.SOUTHWEST]==cellTwo)
				{
					cellOne.wall[Maze.SOUTHWEST].present = false;
					
				}
				else if(cellOne.neigh[Maze.WEST]==cellTwo)
				{
					cellOne.wall[Maze.WEST].present = false;
					
				}
				set1.connect(set2);
			}
		
			/*
			 * Always remove any edges that are checked, if they are rejected
			 * (by the if-statement) they should be removed from the list 
			 * anyway and not considered again.
			 */
			edges.remove(edge);
		}
		
	}// End of hex generator

	private void tunnelMaze(Maze maze) {
		/*
		 * Initialisation part of algorithm
		 * 
		 * (1) For each pair of adjacent cells, create an edge
		 * (2) All edges are stored in a set (ArrayList)
		 * 
		 * South and east edges are not checked because it would cause some
		 * edges to be added twice
		 */
		Edge pair = null;
		for(int i = 0; i < maze.sizeR; i++)
		{
			ArrayList<Tree> tempSet = new ArrayList<Tree>();
			for(int j = 0; j < maze.sizeC; j++)
			{
				Cell currentCell = maze.map[i][j];
				tempSet.add(new Tree());
				
				// If the currentCell has a tunnel, add its tunnelTo cell as an adjacent pair
				if(currentCell.tunnelTo != null)
				{
					pair = new Edge(currentCell, currentCell.tunnelTo);
					edges.add(pair);
				}
				
				// Checking neighbour on north side of cell
				if (currentCell.c >= 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0
						&& currentCell.r < maze.sizeR - 1)
				{
					pair = new Edge(currentCell, currentCell.neigh[Maze.NORTH]);
					if(edges.contains(pair)==false)
					{
						edges.add(pair);						
					}
				}

				// Checking neighbour on west side of cell
				if (currentCell.c > 0 && currentCell.c <= maze.sizeC && currentCell.r >= 0 
						&& currentCell.r <= maze.sizeR)
				{
					pair = new Edge(currentCell, currentCell.neigh[Maze.WEST]);
					if(edges.contains(pair)==false)
					{
						edges.add(pair);						
					}
				}
			}
			sets.add(tempSet);
		}
		System.out.println(edges.size());
		/**
		 * Main body of algorithm
		 * ******************************************************************
		 * 
		 * INPUT: Maze[][], List<Edges>, List<Trees>, Cell currCell
		 * OUTPUT: A perfectly generated maze
		 * 
		 * (1) Randomly choose an edge (two adjacent pairs)
		 * (2) If the edge will join two disjoint trees, merge the trees
		 * (3) Carve a path between the two adjacent cells picked from the edge
		 * (4) Repeat these steps until there are no edges left
		 * 		If the edge picked out contained a tunnel, force 
		 * 		the algorithm to go through the tunnel
		 */
		while(!edges.isEmpty())
		{
			// Randomly select an edge
			Random rand = new Random();
			Edge edge = edges.get(rand.nextInt(edges.size()));
			Cell cellOne = edge.cellOne;
			Cell cellTwo = null;
			// If the cell is part of a tunnel, make the next move go through
			if(cellOne.tunnelTo != null)
			{
				cellTwo = cellOne.tunnelTo;
			}
			// If it is not part of a tunnel, carry on as usual
			else
			{				
				cellTwo = edge.cellTwo;
			}
			
			// Get the corresponding sets for the two cells
			Tree set1 = (sets.get(cellOne.r)).get(cellOne.c);
			Tree set2 = (sets.get(cellTwo.r)).get(cellTwo.c);
			
			// If the sets are not connected, carve a path and connect them
			if(!set1.connected(set2))
			{
				
				// Check in which direction the pairs are joined, starting from cellOne
				if(cellOne.neigh[Maze.NORTH]==cellTwo)
				{
					cellOne.wall[Maze.NORTH].present = false;
				}
				else if(cellOne.neigh[Maze.EAST]==cellTwo)
				{
					cellOne.wall[Maze.EAST].present = false;
				}
				else if(cellOne.neigh[Maze.SOUTH]==cellTwo)
				{
					cellOne.wall[Maze.SOUTH].present = false;
				}
				else if(cellOne.neigh[Maze.WEST]==cellTwo)
				{
					cellOne.wall[Maze.WEST].present = false;
				}
				set1.connect(set2);
			}
			/*
			 * Always remove any edges that are checked, if they are rejected they
			 * should be removed from the list and not considered again.
			 */
			edges.remove(edge);
		}
		
	}// End of tunnel generator
	
	/*
	 * Tree data structure used to compare sets of cells
	 */
	class Tree {
		
		private Tree parent = null;
		
		public Tree() {
			
		}
		
		/*
		 * Traverse to root of tree to see if there is a parent.
		 * Return parent if exists, otherwise return this object instance.
		 */
		public Tree root() {
			// Recursively get parent until you get to root
			if(parent != null)
			{
				return parent.root();
			}
			else
			{
				return this;
			}
		}
		
		/*
		 * Check to see if the two sets are connected
		 */
		public boolean connected(Tree tree) {
			// Will return true if the two trees share a root
			return this.root() == tree.root();
		}
		
		/*
		 * Connect two sets together
		 */
		public void connect(Tree tree) {
			tree.root().setParent(this);
		}
		
		// Set the parent of the object instance
		public void setParent(Tree parent) {
			this.parent = parent;
		}
	}// End of Tree inner class
	
	/*
	 * Edge data structure to represent an edge between two cells.
	 * 
	 * Each edge contains the two cells that are adjacent to each other.
	 */
	class Edge{
		
		Cell cellOne;
		Cell cellTwo;
		
		Edge(Cell one, Cell two)
		{
			cellOne = one;
			cellTwo = two;
		}
	}// End of Edge inner class
	
} // end of class KruskalGenerator


