// Michael Tanjuakio mat200000
import java.util.Random;

public class DisjSets {
	
	// Variables
	int width, height;
	Cell[][] cells; // Create 2D array of cells

	/*
	DisjSets constructor:
	Sets all the array values to -1
	Sets the open wall for start cell and end cell
	start cell has N and W walls open
	end cell has S and E walls open
	*/
	public DisjSets(int numElements, int width_, int height_) {
		s = new int[numElements];
		for (int i = 0; i < s.length; i++)
			s[i] = -1;
		width = width_;
		height = height_;
		cells = new Cell[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				cells[x][y] = new Cell();
		cells[0][0].openWall('N');
		cells[0][0].openWall('W');
		cells[width-1][height-1].openWall('S');
		cells[width-1][height-1].openWall('E');
	}
	
	/*
	union function:
	This function unions two roots
	If the roots are the same, do not union
	*/
	public boolean union(int root1, int root2) {
		
		// Find the roots of given nodes
		root1 = this.find(root1);
		root2 = this.find(root2);
		/*
		System.out.println("find(root1) = " + root1);
		System.out.println("find(root2) = " + root2);
		*/
		
		// Not Union
		if (root1 == root2) { // same root
			//System.out.println("same root: didn't union");
			return false;
		}
		
		// Union
		if (s[root2] < s[root1])
			s[root1] = root2;
		else {
			if (s[root1] == s[root2])
				s[root1]--;
			s[root2] = root1;
		}
		return true;
	}
	
	/*
	find function:
	This function finds the root of the node
	given
	Recursively go through
	*/
	public int find(int x) {
			// with path compression
			if (s[x] < 0) {
				return x;
			}
			else {
				//System.out.print(s[x] + " ");
				return s[x] = find(s[x]);
			}
	}
	
	private int [] s;
	
	/*
	knockWall function:
	This function takes a random cell and breaks a random wall
	This wall applies to the original cell and the affected cell
	This function also takes to account edge cases and cases where
	the cell is the start cell and the end cell
	*/
	public void knockWall(int width, int height) {
		
		// Knock down a random wall
		// Pick a number between 0 to (width * height))
		Random rand1 = new Random();
		int wall_ = rand1.nextInt(width*height);
		double i = (double) wall_;
			
		// Pick a random direction
		// 0 = North
		// 1 = East
		// 2 = South
		// 3 = West
		Random rand2 = new Random();
		int rand_int = rand2.nextInt(4);
		//System.out.print("Index " + (int)i + ": ");
		/*
		if (rand_int == 0)
			System.out.print("North ");
		if (rand_int == 1)
			System.out.print("East ");
		if (rand_int == 2)
			System.out.print("South ");
		if (rand_int == 3)
			System.out.print("West ");
			*/
		
		// Maze edge cases
		// First Row Case (North)
		if (i < width && rand_int == 0) {
			//System.out.println();
			return;
		}
			
		// First Column Case (West)
		if (i % width == 0 && rand_int == 3){
			//System.out.println();
			return;
		}
		
		// Last Column Case (East)
		if ((i+1) % width == 0 && rand_int == 1){
			//System.out.println();
			return;
		}
		
		// Last Row Case (South)
		if (i >= s.length - width && rand_int == 2){
			//System.out.println();
			return;
		}
		
		// Break wall
		
		Boolean success; // tests if its able to union
		
		// North
		if (rand_int == 0) {
			
			// Call union function
			//System.out.println("\tUnion: " + (int)(i - width) + ", " + (int)(i));
			success = this.union((int) i - width, (int) i);
			
			if (success) {
			
			// Open North Wall of called cell
			//System.out.println("opening N wall for "
			//				+ "[" + (wall_ / width) + "][" + (wall_ % width) + "]");
			cells[wall_ / width][wall_ % width].openWall('N');
			
			// Open South Wall of north cell
			int northCell = wall_ - width;
			//System.out.println("opening S wall for "
			//		+ "[" + (northCell / width) + "][" + (northCell % width) + "]");
			cells[northCell / width][northCell % width].openWall('S');
			
			}
			
		}
		
		// East
		if (rand_int == 1) {
			
			// Call union function
			//System.out.println("\tUnion: " + (int)(i) + ", " + (int)(i+1));
			success = this.union((int) i, 		(int) (i + 1));
			
			if (success) {
			
			// Open East Wall of called cell
			//System.out.println("opening E wall for "
			//		+ "[" + (wall_ / width) + "][" + (wall_ % width) + "]");
			cells[wall_ / width][wall_ % width].openWall('E');
			
			// Open West Wall of east cell
			int westCell = wall_ + 1;
			//System.out.println("opening W wall for "
			//		+ "[" + (westCell / width) + "][" + (westCell % width) + "]");
			cells[westCell / width][westCell % width].openWall('W');
			}
		}
		
		// South
		if (rand_int == 2) {
			
			// Call union function
			//System.out.println("\tUnion: " + (int)(i) + ", " + (int)(i+width));
			success = this.union((int) i,			(int) (i + width));
			
			if (success) {
			
			// Open South Wall of called cell
			//System.out.println("opening S wall for "
			//		+ "[" + (wall_ / width) + "][" + (wall_ % width) + "]");
			cells[wall_ / width][wall_ % width].openWall('S');
			
			// Open North Wall of south cell
			int southCell = wall_ + width;
			//System.out.println("opening N wall for "
			//		+ "[" + (southCell / width) + "][" + (southCell % width) + "]");
			cells[southCell / width][southCell % width].openWall('N');
			}
		}
		
		// West
		if (rand_int == 3) {
			
			// Call union function
			//System.out.println("\tUnion: " + (int)(i-1) + ", " + (int)(i));
			success = this.union((int) (i - 1),	(int) i);
			
			if (success) {
			// Open West Wall of called Cell
			//System.out.println("opening W wall for "
			//		+ "[" + (wall_ / width) + "][" + (wall_ % width) + "]");
			cells[wall_ / width][wall_ % width].openWall('W');
			
			// Open East Wall of west cell
			int westCell = wall_ - 1;
			//System.out.println("opening E wall for "
			//		+ "[" + (westCell / width) + "][" + (westCell % width) + "]");
			cells[westCell / width][westCell % width].openWall('E');
			}
		}		
	}
	
	/*
	displayList function:
	This function displays the root value and then the array value
	In an organized list
	*/
	public void displayList() {
		// Go through each one
		System.out.println("Display Maze");
		for (int i = 0; i < s.length; i++) {
			System.out.print("index = " + (i) + " \t");
			System.out.println(this.find(i) + " \t" + s[i] + " ");
		}
		System.out.println();
	}
	
	/*
	checkAllConnected function:
	Checks if only one value has an negative array value
	If it does, this means that all the nodes are connected
	and can return true
	If not, it returns false
	*/
	public boolean checkAllConnected() {
		// go through all nodes
		int j = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] < 0) // if negative
				j++;
			if (j > 1)
				return false;
		}
		return true;
	}
	
}
