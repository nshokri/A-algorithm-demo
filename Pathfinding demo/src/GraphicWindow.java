import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import javax.swing.JFrame;

public class GraphicWindow {

	private static final int windowWidth = 800;
	private static final int windowHeight = 600;

	private static final int cellWidth = 20;
	private static final int cellHeight = 20;

	private static Point2D startPoint = new Point2D(0, 0);
	private static Point2D endPoint = new Point2D(20, 0);

	private static Grid grid;
	private static DrawGrid dg;

	private static HashMap<Integer, Integer> walls = new HashMap<Integer, Integer>();

	public static void main(String[] args)
	{
		//Add walls
		walls.put(1, 0);
		walls.put(2, 0);
		walls.put(3, 1);
		//Code for making GUI

		JFrame window = new JFrame();
		//The -3 is for an enhanced visual effect due to a slight offset while drawing the grid
		window.setSize(windowWidth - 3, windowHeight);
		window.setTitle("Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setBackground(Color.gray);
		window.setFocusable(true);
		window.requestFocusInWindow();

		grid = new Grid(windowWidth, windowHeight, cellWidth, cellHeight, startPoint, endPoint, walls);
		dg = new DrawGrid(grid);

		window.add(dg);
		window.revalidate();


		dg.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int x = (int) (e.getX() / cellWidth);
				int y =(int) (e.getY() / cellHeight);
				grid.fillCell(x, y);
				window.repaint();
			} 
		});

		window.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				if (e.getKeyChar() == ' ')
				{
					grid.findNewNeighbors();
					A_star();
					dg.repaint();
				}
			}
		});
	}


	private static void A_star()
	{
		//Code for A* Algorithm
		//The "Cell" class will act as our node
		PriorityQueue<Cell> openSet = new PriorityQueue<Cell>();
		PriorityQueue<Cell> closedSet = new PriorityQueue<Cell>();

		openSet.add(grid.getStartCell());

		while (!openSet.isEmpty())
		{
			//Remove cell with lowest total cost from openSet and add's that cell to the closedSett
			Cell current = openSet.poll();
			closedSet.add(current);

			if (current.equals(grid.getEndCell()))
			{
				break;
			}

			for (Cell c : current.getNeighbors())
			{
				//Only traversable Cell's are returned from .getNeighbors()
				if (!closedSet.contains(c))
				{
					//If the new path to the neighbor is SHORTER than the old path
					
					double neighborPathDistance = current.getDistanceFromStart() + grid.getDistance(current.getPoint(), c.getPoint());
	
					if (neighborPathDistance < c.getDistanceFromStart()
							|| !openSet.contains(c))
					{
						c.setTotalCost();
						c.setParent(current);
						if (!openSet.contains(c))
						{
							openSet.add(c);
						}
					}
				}

			}

		}

		ArrayList<Cell> path = getPath(grid.getEndCell().getParent(), new ArrayList<Cell>());
		dg.setPath(path);
	}

	//Recursive method to get the path
	private static ArrayList<Cell> getPath(Cell cell, ArrayList<Cell> arr)
	{
		if (cell == null || cell.equals(grid.getStartCell()))
		{
			return arr;
		}
		else
		{
			getPath(cell.getParent(), arr);
			arr.add(cell);
			return arr;
		}
	}
}
