// Michael Tanjuakio mat200000
// Maze dimensions are inputted by user on console
// When asked, type "Y" on the console to display the path of the maze
// note: the maze AND maze path are displayed on a different window
// max viewable size: 200x200, however this is editable in the code

import java.awt.Dimension;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {

	public static void main(String[] args) {
		
		// Variables
		Scanner scan = new Scanner(System.in);
		
		// Maze Generation
		System.out.println("Maze Generation");
		System.out.print("Input width (n): "); 
		String a = scan.next(); // input
		a = checkNum(a, scan); // input validation
		System.out.print("Input height (m): ");
		String b = scan.next(); // input
		b = checkNum(b, scan); // input validation
		int n = Integer.parseInt(a);
		int m = Integer.parseInt(b);
		System.out.println("Generating Maze " + n + "x" + m);

		// Create maze
		// Create a disjoint set for the created maze
		DisjSets maze = new DisjSets(n*m, n, m);
		
		// Show original list (testing)
		// maze.displayList();		
		
		// Knock down a wall in each cell until its all connected
		while (!maze.checkAllConnected()) {
			maze.knockWall(m, n);
			//maze.displayList(); // testing
		}
		
		// Generate Maze with Walls everywhere (except enter and exits cells)
		JFrame frame = new JFrame("Maze"); // frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel maze_ = new Panel(n,m, maze.cells); // maze panel
		
		// USE THIS LINE TO CHANGE THE MAZE LIMIT
		maze_.setPreferredSize(new Dimension(10000,10000)); // maze limit 
		
		JScrollPane scroll = new JScrollPane(maze_, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // scrollable panel
	    frame.add(scroll); // add maze to frame
	    frame.setSize(1000,1000); // set default size
		frame.setVisible(true); // show maze
		
		// Shows the open walls for each cell (testing)
		// Cell p = new Cell();
		// p.printOpenWalls(maze.cells, n, m);
		
		// Print the Path in NESW form
		
		// Marks the cells that are in the path
		findPath(maze.cells, 0, 0);
		// printMarked(maze.cells); (shows the marked cells in a nxm grid - testing)
		
		// Writing the path from given marked cells in a string
		String[] path = new String[]{""};
		writePath(maze.cells, 0, 0, path);
		
		// Prints path
		System.out.println("Path: " + path[0]);
		
		// Asks user to display the path in frame
		System.out.println("Do you want to see the path? Y/N");
		String c = scan.next();
		if (c.equals("Y")) {
			System.out.println("Showing path");
			maze_.showThePath(path[0]); // displays path in frame
			maze_.repaint(); //refreshes page
		}
		else {
			return;
		}
	}
	
	/*
	printMarked function:
	Prints the marked cells
	From left to right and up to down
	*/
	public static void printMarked(Cell[][] cells) {
		for (int i = 0; i < cells.length; i++) {
			System.out.println("\n");
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j].path)
					System.out.print("m ");
				else
					System.out.print("o ");
			}
		}
	}
	
	/*
	checkNum function:
	input validation function for number
	*/
	public static String checkNum(String a, Scanner scan) {
		while (!isNum(a)) {
			System.out.print("Invalid value, input again: ");
			a = scan.next();
		}
		return a;
	}
	
	/*
	isNum function:
	input validation for number in string
	*/
	public static boolean isNum(String a) {
		
		for (int i = 0; i < a.length(); i++) {
			if (!Character.isDigit(a.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/*
	findPath function:
	Marks the cells that are part of the path
	This is done recrusively
	This is a version of depth first search
	If the function returns the last cell, it returns true
	and its marked true in the path boolean
	otherwise, marked false
	*/
	public static boolean findPath(Cell[][] cells, int x, int y) {
		
		// If cell is end cell, return true
		if ((x == cells.length - 1 && y == cells[0].length - 1)) {
			cells[x][y].path = true;
			return true;
		}
		
		// Pick starting cell [0, 0]
		// Mark cell as path
		cells[x][y].path = true;
		//System.out.println("Marked [" + x + "][" + y + "]\n");
		
		// For each unvisited cell
		boolean markedPath;
		
		// Check unvisited north cell
		if (x != 0 && 
		   !(x == 0 && y == 0) &&
		    cells[x][y].north &&
		    !cells[x-1][y].path) {
			
			//System.out.println("Going north");

			// Call findPath, returns boolean
			markedPath = findPath(cells, x-1, y);
			
			// if !boolean, unmark cell
			if (!markedPath) {
				//System.out.println("Unmarking [" + (x-1) + "][" + y + "]");
				cells[x-1][y].path = false;
			}
			else {
				return true;
			}
			
		}
		
		// Check unvisited east cell
		if (y != cells[0].length - 1 &&
		   !(x == cells[0].length - 1 && y == cells.length - 1) && 
		   cells[x][y].east &&
		   !cells[x][y+1].path) {
			
			//System.out.println("Going east");
			
			// Call findPath, returns boolean
			markedPath = findPath(cells, x, y+1);
			
			// if !boolean, unmark cell
			if (!markedPath) {
				//System.out.println("Unmarking [" + x + "][" + (y+1) + "]");
				cells[x][y+1].path = false;
			}
			else {
				return true;
			}
			
		}
		
		// Check unvisited south cell
		if (x != cells.length - 1 &&
		   !(x == cells[0].length - 1 && y == cells.length - 1) &&
		    cells[x][y].south &&
		    !cells[x+1][y].path) {
			
			//System.out.println("Going south");
			
			// Call findPath, returns boolean
			markedPath = findPath(cells, x+1, y);
			
			// if !boolean, unmark cell
			if (!markedPath) {
				//System.out.println("Unmarking [" + (x+1) + "][" + y + "]");
				cells[x+1][y].path = false;
			}
			else {
				return true;
			}
			
		}
		
		// Check unvisited west cell
		if (y != 0 && 
		   !(x == 0 && y == 0) && 
		    cells[x][y].west&&
		    !cells[x][y-1].path) {
			
			//System.out.println("Going west");
			
			// Call findPath, returns boolean
			markedPath = findPath(cells, x, y-1);
			
			// if !boolean, unmark cell
			if (!markedPath) {
				//System.out.println("Unmarking [" + x + "][" + (y-1) + "]");
				cells[x][y-1].path = false;
			}
			else {
				return true;
			}
			
		}
		
		// If cell is starting cell
		if (x == 0 && y == 0) {
			//path[0] = writePath(cells, 0, 0);
			return true;
		}
		
		// All unvisited cells are checked
		// If cell is not end cell
		if (!(x == cells[0].length - 1 && y == cells.length - 1))
			return false;
		
		return true;
	}
	
	/*
	writePath function:
	This function write the path in a string according to 
	the orthogonal directions, north, east, south, west
	This is done recursively similar to the findPath function
	Before every recursive call, it makes path boolean in that
	cell false, in order to avoid looping
	*/
	public static String writePath(Cell[][] cells, int x, int y, String[] path) {
		
		// start from 0, 0
		
		// Check north
		if (x != 0 && 
		   !(x == 0 && y == 0) &&
	        cells[x][y].north &&
	        cells[x-1][y].path) {
			//System.out.println("add N");
			path[0] += "N"; // add to string
			cells[x][y].path = false;
			writePath(cells, x-1, y, path); // recurse
		}
		
		// check east
		if (y != cells[0].length - 1 &&
		   !(x == cells[0].length - 1 && y == cells.length - 1) && 
	        cells[x][y].east &&
	        cells[x][y+1].path) {
			//System.out.println("add E");
			path[0] += "E";
			cells[x][y].path = false;
			writePath(cells, x, y+1, path); // recurse
		}
		
		// check south
		if (x != cells.length - 1 &&
		   !(x == cells[0].length - 1 && y == cells.length - 1) &&
		    cells[x][y].south &&
		    cells[x+1][y].path) {
			//System.out.println("add S");
			path[0] += "S";
			cells[x][y].path = false;
			writePath(cells, x+1, y, path); // recurse
		}
		
		// check west
		if (y != 0 && 
		   !(x == 0 && y == 0) && 
		   cells[x][y].west &&
		   cells[x][y-1].path) {
			//System.out.println("add W");
			path[0] += "W";
			cells[x][y].path = false;
			writePath(cells, x, y-1, path); // recurse
		}
		
		return path[0];
	}

	
}
