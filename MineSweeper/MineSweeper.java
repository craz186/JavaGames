/*Notes:
*
* Reduce code down to one class preferrably block.
*
* Make an image handling method. 
*
* Change font colours and add buttons to retry and change difficulty in future.
*
*/
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MineSweeper extends JFrame
{
	
	private int clicks=0;
	private int totalRows= 20;
	private int totalCols= 30;
	private Block[][] grid= new Block[totalRows][totalCols];
	private int total= totalRows*totalCols;
	private int mines= 50;
	//Scales image to size;
	ImageIcon image1 = new ImageIcon("mine.ico");
	ImageIcon image2 = new ImageIcon("flag_red.ico");
	Image img = image1.getImage();  
	Image img2 = image2.getImage();  
	Image newimg = img.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);  
	Image newimg2 = img2.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);  
	ImageIcon image = new ImageIcon(newimg); 
	ImageIcon image3 = new ImageIcon(newimg2); 
	MineSweeper()
	{
		super("MineSweeper");
		setLayout(new GridLayout(totalRows,totalCols)); 
		setSize(1300,700);
		for (int i=0; i<totalRows; i++) {	// add button i
			for(int j=0; j<totalCols; j++)
			{
				grid[i][j] = new Block(i,j);
				add(grid[i][j]);
				grid[i][j].setBackground(Color.LIGHT_GRAY);
				grid[i][j].addMouseListener(grid[i][j]);
			}
		}
		Random r= new Random();
		int randI=0;
		int randJ=0;
		for(int i=0; i<mines;i++)//Need to make unique locations later.
		{
			randI=r.nextInt(totalRows);
			randJ=r.nextInt(totalCols);
			if(grid[randI][randJ].setMine())
				continue;
			
		}
		setVisible(true);
		setResizable(false);
	}
	class Block extends JButton implements MouseListener
	{	
		private boolean revealed = false;
		private boolean flag= false;
		private boolean mine= false;
		private int count=0;
		private int row,column;
		
		Block(int row, int column)
		{
			this.row=row;
			this.column=column;
		}
		public boolean hasMine()
		{
			return mine;
		}  
		public boolean setMine()
		{
			//grid[row][column].setIcon(image);
			
			if(mine)
				return false;
			else
				mine=true;
			return true;
		}	
		public boolean setFlag()
		{
			if(flag)
			{
				flag=false;
				grid[row][column].setIcon(null);
			}
			else 
			{
				flag=true; 
				grid[row][column].setIcon(image3);
			}
			return flag;
		}
		public boolean hasFlag()
		{
			return flag;
		}
		public boolean getRevealed()
		{
			return revealed;
		}
		public boolean setRevealed()
		{
			if(flag||revealed)
				return false;
			else
			{
				revealed=true;
				grid[row][column].setIcon(null);
				return true;
			}
		}
		public void incCount()
		{
			if(!revealed)
			{
				count++;
			}
		}
		public String getCount()
		{
			if(count==0)
				return "";
			else 
				return count+"";
		}
		public String countMines()
		{
			
			for(int ii=row-1; ii<=row+1;ii++)
			{
				for (int jj = column - 1; jj <= column + 1; jj++)
				{
					if(!((ii==-1||ii>=totalRows)||(jj==-1||jj>=totalCols)))
					{
						if (grid[ii][jj].hasMine()) grid[row][column].incCount();
					}
				}
			}
			
			return grid[row][column].getCount();
		}
		public void mousePressed(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				if(!grid[row][column].getRevealed())
				{
					grid[row][column].setFlag();
					
				}
			}
			else
			{
		
				checkMine();
				grid[row][column].countMines();
				reveal(row,column);
				if(grid[row][column].getCount().equals(""))
				{
					revealNearbyBlanks(row,column);
				}
				checkWinner();
			}
		
		}
		public void reveal(int row0,int column0)
		{
			if(grid[row0][column0].setRevealed())
			{
				clicks++;
				grid[row0][column0].setText("" + (grid[row0][column0].countMines()));
				grid[row0][column0].setBackground(Color.WHITE);
			}
		}
		public void checkMine()//works
		{
			if(grid[row][column].hasMine()&&!grid[row][column].hasFlag())
			{
				//revealMines();
				JOptionPane.showMessageDialog(null,"You hit a mine hard luck.");
				System.exit(0);
			}
		}
		public void revealNearbyBlanks(int row0, int column0)
		{
			for(int ii=row0-1; ii<=row0+1;ii++)
			{  
				for (int jj = column0 - 1; jj <= column0 + 1; jj++)
				{
					if(!((ii==-1||ii>=totalRows)||(jj==-1||jj>=totalCols)))
					{
						
						if (!(grid[ii][jj].getRevealed()))
						{
							String count = grid[ii][jj].countMines();
							reveal(ii,jj);
							if(count.equals(""))
								revealNearbyBlanks(ii,jj);
							
						}
					}
				}
			}
		
		}
		public void checkWinner()
		{
			System.out.println("clicks :"+clicks+ " Mines: "+mines+ " Total: "+total);
			if(clicks+mines==total)
			{
				JOptionPane.showMessageDialog(null,"You win");
				System.exit(0);
			}
		}
		public void mouseExited(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
	}
	
		
	
	
	
	public static void main(String[] args)
	{
		new MineSweeper();
	}
}