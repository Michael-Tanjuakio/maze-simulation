// Michael Tanjuakio mat200000
import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
	
	// Variables
	public Boolean showPath = false; // This allows the panel to print the path
	public String path = ""; // path string (f.e. "ESES")
	int width;
	int height;
	Cell[][] cells = new Cell[width][height];
	
	/* 
	showThePath function:
	This function allows the path to print
	*/
	public void showThePath(String path_) {
		path = path_;
		showPath = true;
	}
	
	// Panel Constructor
	Panel(int n, int m, Cell[][] cells_) {
		width = n;
		height = m;
		cells = cells_;
	}
	
	/*
	paintComponent function:
	This function creates the path into a new window
	This function gets multiple files (maze files)
	It analyzes the cells to see which image file to use
	When user wants to show the path, this function
	calls the showPath function
	*/
	public void paintComponent(Graphics g) {
		
		// ImageIcon minecraft = new ImageIcon("minecraft.png");
		
		// 4 line cells (2)
		ImageIcon boxed = new ImageIcon("boxed.png");
		ImageIcon OpenNESW = new ImageIcon("openNESW.png");
		
		// 3 line cells (4)
		ImageIcon OpenNES = new ImageIcon("openNES.png");
		ImageIcon OpenNEW = new ImageIcon("openNEW.png");
		ImageIcon OpenNSW = new ImageIcon("openNSW.png");
		ImageIcon OpenESW = new ImageIcon("openESW.png");
		
		// 2 line cells (6)
		ImageIcon OpenNE = new ImageIcon("openNE.png");
		ImageIcon OpenES = new ImageIcon("openES.png");
		ImageIcon OpenSW = new ImageIcon("openSW.png");
		ImageIcon OpenNW = new ImageIcon("openNW.png");
		ImageIcon OpenNS = new ImageIcon("openNS.png");
		ImageIcon OpenEW = new ImageIcon("openEW.png");
		
		// 1 line cells (4)
		ImageIcon OpenN = new ImageIcon("openN.png");
		ImageIcon OpenE = new ImageIcon("openE.png");
		ImageIcon OpenS = new ImageIcon("openS.png");
		ImageIcon OpenW = new ImageIcon("openW.png");
		
		// Go through cell matrix
		
		for (int i = 0; i < width; i++) 
			for (int j = 0; j < height; j++)  {
				
				// empty cell
				if (cells[i][j].north == true && 
					cells[i][j].east == true &&
					cells[i][j].south == true &&
					cells[i][j].west == true) {
					//System.out.println("Print NESW");
					g.drawImage(OpenNESW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				// 3 line cells (4)
				if (cells[i][j].north == true && 
					cells[i][j].east == true &&
					cells[i][j].south == true) {
					//System.out.println("Print NES");
					g.drawImage(OpenNES.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}

				if (cells[i][j].north == true && 
					cells[i][j].east == true &&
					cells[i][j].west == true) {
					//System.out.println("Print NEW");
					g.drawImage(OpenNEW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].north == true && 
					cells[i][j].south == true &&
					cells[i][j].west == true) {
					//System.out.println("Print NSW");
					g.drawImage(OpenNSW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].east == true && 
					cells[i][j].south == true &&
					cells[i][j].west == true) {
					//System.out.println("Print ESW");
					g.drawImage(OpenESW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				// 2 line cells (6)
				if (cells[i][j].north == true && 
					cells[i][j].east == true) {
					//System.out.println("Print NE");
					g.drawImage(OpenNE.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}

				if (cells[i][j].east == true && 
					cells[i][j].south == true) {
					//System.out.println("Print ES");
					g.drawImage(OpenES.getImage(), j*50, i*50, 50, 50, null);	
					continue;
				}

				if (cells[i][j].south == true && 
					cells[i][j].west == true) {
					//System.out.println("Print SW");
					g.drawImage(OpenSW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].north == true && 
					cells[i][j].west == true) {
					//System.out.println("Print NW");
					g.drawImage(OpenNW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].north == true && 
					cells[i][j].south == true) {
					//System.out.println("Print NS");
					g.drawImage(OpenNS.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].east == true && 
					cells[i][j].west == true) {
					//System.out.println("Print EW");
					g.drawImage(OpenEW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				// 1 line cells (4)
				if (cells[i][j].north == true) {
					//System.out.println("Print N");
					g.drawImage(OpenN.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].east == true) {
					//System.out.println("Print E");
					g.drawImage(OpenE.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].south == true) {
					//System.out.println("Print S");
					g.drawImage(OpenS.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
				
				if (cells[i][j].west == true) {
					//System.out.println("Print W");
					g.drawImage(OpenW.getImage(), j*50, i*50, 50, 50, null);
					continue;
				}
			}	
		
		// Shows path
		if (showPath) {
			paintPath(g, 25, 25, 0);
			this.repaint();
		}		
	}
	
	/*
	paintPath function:
	This function shows the path in the same frame
	as in the paintComponent function
	This function analyzes the path string and determines
	where the images go
	When it goes a specific direction (NESW), it affects the
	original cell and the affected cell
	*/
	public void paintPath(Graphics g, int i, int j, int index) {
		
		if (index == path.length())
			return;
		
		// Path liens (2)
		ImageIcon drawHori = new ImageIcon("drawHori.png");
		ImageIcon drawVert = new ImageIcon("drawVert.png");
		
		// Draw north
		if (path.charAt(index) == 'N') {
			g.drawImage(drawVert.getImage(), j, i - 25,	5, 25, null);// original
			g.drawImage(drawVert.getImage(), j, i - 50, 5, 25, null);// north cell
			paintPath(g, i - 50, j, index + 1);
		}
		
		// Draw east
		if (path.charAt(index) == 'E') {
			g.drawImage(drawHori.getImage(), j, i, 		25, 5, null);// original
			g.drawImage(drawHori.getImage(), j + 25, i, 25, 5, null);// east cell
			paintPath(g, i, j + 50, index + 1);
		}
		
		// Draw south
			if (path.charAt(index) == 'S') {
			g.drawImage(drawVert.getImage(), j, i, 		5, 25, null);// original
			g.drawImage(drawVert.getImage(), j, i + 25, 5, 25, null);// south cell
			paintPath(g, i + 50, j, index + 1);
		}
		
		// Draw west
			if (path.charAt(index) == 'W') {
			g.drawImage(drawHori.getImage(), j - 25, i, 25, 5, null);// original
			g.drawImage(drawHori.getImage(), j - 50, i, 25, 5, null);// west cell
			paintPath(g, i, j - 50, index + 1);
		}
		
	}



}
