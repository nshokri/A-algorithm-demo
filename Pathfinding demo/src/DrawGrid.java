import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

public class DrawGrid extends JComponent{

	private Grid grid;
	private Cell startCell;
	private Cell endCell;
	
	private int cellWidth;
	private int cellHeight;
	
	private ArrayList<Cell> path;
	
	public DrawGrid(Grid grid)
	{
		this.grid = grid;
		this.startCell = grid.getStartCell();
		this.endCell = grid.getEndCell();
		this.cellWidth = grid.getCellWidth();
		this.cellHeight = grid.getCellHeight();
		this.path = new ArrayList<Cell>();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 =  (Graphics2D) g;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		
		
		//Draw grid
		for (int i = 0; i < grid.getGridWidth(); i++)
		{
			for (int j = 0; j < grid.getGridHeight(); j++)
			{
				Cell cell = grid.getCell(i, j);

				Rectangle rect = new Rectangle(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
				
				//Check to see if it is a start or end cell
				if (cell.equals(startCell) || cell.equals(endCell))
				{
					g2.setColor(Color.GREEN);
					g2.fill(rect);
					g2.setColor(Color.BLACK);
				}
				
				//Check if the cell is a wall or not
				if (cell.getCanCollide() == false)
				{
					g2.draw(rect);
				}
				else
				{
					g2.fill(rect);
				}
			}
		}
		
		//Draw Path
		if (!path.isEmpty())
		{
			for (Cell cell: path)
			{
				Rectangle rect = new Rectangle(cell.getPoint().getX() * cellWidth, cell.getPoint().getY() * cellHeight, cellWidth, cellHeight);
				g2.setColor(Color.BLUE);
				g2.fill(rect);
			}
		}	
	}
	
	public void setPath(ArrayList<Cell> arr)
	{
		path = arr;
	}
}