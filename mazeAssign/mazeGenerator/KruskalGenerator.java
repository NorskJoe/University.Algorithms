package mazeGenerator;

import maze.Maze;

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
		// TODO Auto-generated method stub
		
	}// End of normal generator

	private void hexMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// End of hex generator

	private void tunnelMaze(Maze maze) {
		// TODO Auto-generated method stub
		
	}// Enf of tunnel generator

} // end of class KruskalGenerator
