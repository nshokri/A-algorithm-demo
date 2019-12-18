import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Grid {
	
	private Cell[][] grid;
	private int gridWidth;
	private int gridHeight;
	private int cellWidth;
	private int cellHeight;
	
	private Cell startCell;
	private Cell endCell;
	
	private Point2D startCellPoint;
	private Point2D endCellPoint;
	
	private HashMap<Integer, Integer> walls;
	
	public Grid(int windowWidth, int windowHeight, 
			int cellWidth, int cellHeight, 
			Point2D startCellPoint, Point2D endCellPoint, 
			HashMap<Integer, Integer> walls)
	{
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		this.startCellPoint = startCellPoint;
		this.endCellPoint = endCellPoint;
		
		//The -1 and -2 are for an enhanced visual view
		this.gridWidth = (int) (windowWidth / cellWidth) - 1;
		this.gridHeight = (int) (windowHeight / cellHeight) - 2;
		
		this.walls = walls;
		
		this.grid = new Cell[gridWidth][gridHeight];
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				double distanceFromStart = getDistance(startCellPoint, new Point2D(i, j));
				//double distanceFromEnd = getDistance(startCellPoint, endCellPoint);
				double distanceFromEnd = getDistance(new Point2D(i, j), endCellPoint);
				
				grid[i][j] = new Cell(new Point2D(i, j), false, distanceFromStart, distanceFromEnd);
			}
		}
		
		startCell = grid[startCellPoint.getX()][startCellPoint.getY()];
		endCell = grid[endCellPoint.getX()][endCellPoint.getY()];
		
	}
	
	public void findNewNeighbors()
	{
		
		//Find and set neighbors
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				ArrayList<Cell> Neighbors = new ArrayList<Cell>();
				
				//Up
				if (j > 0 && grid[i][j - 1].getCanCollide() == false)
				{
					Neighbors.add(grid[i][j - 1]);
				}
				//Down
				if (j < grid[i].length - 1 && grid[i][j + 1].getCanCollide() == false)
				{
					Neighbors.add(grid[i][j + 1]);
				}
				//Right
				if (i < grid.length - 1 && grid[i + 1][j].getCanCollide() == false)
				{
					Neighbors.add(grid[i + 1][j]);
				}
				//Left
				if (i > 0 && grid[i - 1][j].getCanCollide() == false)
				{
					Neighbors.add(grid[i - 1][j]);
				}
				//Up Right
				if (j > 0 && i < grid.length - 1 && grid[i + 1][j - 1].getCanCollide() == false)
				{
					Neighbors.add(grid[i + 1][j - 1]);
				}
				//Down Right
				if (j < grid[i].length - 1 && i < grid.length - 1 && grid[i + 1][j + 1].getCanCollide() == false)
				{
					Neighbors.add(grid[i + 1][j + 1]);
				}
				//Up Left
				if (j > 0 && i > 0 && grid[i - 1][j - 1].getCanCollide() == false)
				{
					Neighbors.add(grid[i - 1][j - 1]);
				}
				//Down Left
				if (j < grid[i].length - 1 && i > 0 && grid[i - 1][j + 1].getCanCollide() == false)
				{
					Neighbors.add(grid[i - 1][j + 1]);
				}
				
				grid[i][j].setNeighbors(Neighbors);
			}
		}
	}
	
	public double getDistance(Point2D start, Point2D end)
	{
		//return Math.hypot(end.getX() - start.getX(), end.getY() - start.getY());
		double distX = Math.abs(end.getX() - start.getX());
		double distY = Math.abs(end.getY() - start.getY());
		
		if (distX > distY)
		{
			return 14 * distX + 10 * (distX - distY);
		}
		
		return 14 * distY + 10 * (distY - distX);
	}
	
	public Cell getCell(int x, int y)
	{
		return grid[x][y];
	}
	
	public int getCellWidth()
	{
		return cellWidth;
	}
	
	public int getCellHeight()
	{
		return cellHeight;
	}
	
	public int getGridWidth()
	{
		return gridWidth;
	}
	
	public int getGridHeight()
	{
		return gridHeight;
	}
	
	public void fillCell(int x, int y)
	{
		grid[x][y].setCanCollide(true);
	}
	
	public void setStartCell(int x, int y)
	{
		this.startCell = grid[x][y];
		startCellPoint = new Point2D(x, y);
	}
	
	public void setEndCell(int x, int y)
	{
		this.endCell = grid[x][y];
		endCellPoint = new Point2D(x, y);
	}
	
	public Cell getStartCell()
	{
		return startCell;
	}
	
	public Cell getEndCell()
	{
		return endCell;
	}
}