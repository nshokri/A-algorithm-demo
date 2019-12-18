import java.util.ArrayList;

public class Cell implements Comparable<Cell>
{
	private Point2D point;
	private boolean canCollide;
	private double distanceFromStart;
	private double distanceFromEnd;
	private double totalDistance;
	private ArrayList<Cell> Neighbors;
	private Cell parent;
	
	public Cell(Point2D point, boolean canCollide, double distanceFromStart, double distanceFromEnd)
	{
		this.point = point;
		this.canCollide = canCollide;
		this.distanceFromStart = distanceFromStart;
		this.distanceFromEnd = distanceFromEnd;
		this.totalDistance = Integer.MAX_VALUE;
		this.parent = null;
	}
	
	public boolean getCanCollide()
	{
		return canCollide;
	}
	
	public void setCanCollide(boolean collision)
	{
		canCollide = collision;
	}
	
	public double getDistanceFromStart()
	{
		return distanceFromStart;
	}
	
	public double getDistanceFromEnd()
	{
		return distanceFromEnd;
	}
	
	public double getTotalCost()
	{
		return totalDistance;
	}
	
	public void setTotalCost()
	{
		totalDistance = distanceFromStart + distanceFromEnd;
	}
	
	public Cell getParent()
	{
		return parent;
	}
	
	public void setParent(Cell c)
	{
		parent = c;
	}
	
	@Override
	public int compareTo(Cell cell)
	{
		if (this.getTotalCost() > cell.getTotalCost())
		{
			return 1;
		}
		else if (this.getTotalCost() < cell.getTotalCost())
		{
			return -1;
		}
		
		return 0;
	}
	
	public void setNeighbors(ArrayList<Cell> neighbors)
	{
		Neighbors = neighbors;
		
	}
	
	public ArrayList<Cell> getNeighbors()
	{
		return Neighbors;
	}
	
	public Point2D getPoint()
	{
		return point;
	}
}