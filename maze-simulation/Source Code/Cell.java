// Michael Tanjuakio mat200000
public class Cell {
	
	// Constructor
	public Cell() {}
	
	// Variables
	
	// 4 Booleans:
	// Used to show the cells walls
	// true = no wall
	// false = walled
	public Boolean north = false;
	public Boolean south = false;
	public Boolean east = false;
	public Boolean west = false;
	
	// path boolean:
	// Used to print the maze solution path
	// true = path cell
	public Boolean path = false;
	
	
	// Make wall open
	public void openWall(char open) {
		if (open == 'N') // Open North Wall
			north = true;
		if (open == 'S') // Open South Wall
			south = true;
		if (open == 'E') // Open East Wall
			east = true;
		if (open == 'W') // Open West Wall
			west = true;
	}
	
	/*
	printOpenWalls function:
	This function shows the orthogonal directions of
	each cell, NESW
	Goes from left to right, then up to down
	*/
	public void printOpenWalls(Cell[][] cells, int width, int height) {
		// Go through each cell
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				System.out.print("Cell[" + i + "][" + j + "] = ");
				if (cells[i][j].north)
					System.out.print("N");
				if (cells[i][j].east)
					System.out.print("E");
				if (cells[i][j].south)
					System.out.print("S");
				if (cells[i][j].west)
					System.out.print("W");
				System.out.println();
			}
	}
}
