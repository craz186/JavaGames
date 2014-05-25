import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class TouchTheWhiteLine extends JFrame
{
	private Block[][] buttons= new Block[4][4];
	private int count=0;
	boolean startTimer= true;
	long time1=0;
	long time2=0;
	TouchTheWhiteLine()
	{
		super("Don't Touch The White Line");
		setLayout(new GridLayout(4,4)); 
		setSize(500,700);
		for (int i=0; i<4; i++) {	// add button i
			for(int j=0; j<4; j++)
			{
				buttons[i][j] = new Block(i,j);
				buttons[i][j].addMouseListener(buttons[i][j]);
				
				buttons[i][j].setBackground(Color.WHITE);
				add(buttons[i][j]);
			}
		}        
		
		//jf.addKeyListener(new Tiles());
		setVisible(true);
		buttons[0][0].setBlacks();
		
	}
	class Block extends JButton implements MouseListener
	{	
		private int row,column;
		private boolean black= false;
		Block(int row, int column)
		{
			this.row=row;
			this.column=column;
		}
		public void mousePressed(MouseEvent e)
		{
			if(startTimer)
			{
				begin();
			}
			checkWhite();
			System.out.println(count);
			//checkWinner();
			startTimer=false;
		}
		void moveDown()
		{
			for(int i=3;i>-1;i--)
			{
				for(int j=0;j<4;j++)
				{
					buttons[i][j].down();
				}
			}
		}
		void begin()
		{
			time1= System.currentTimeMillis();
		}
		void down()
		{
			if(row==3)
				return;
			else
			if(black)
			{
				System.out.println(row+ " "+column);
				setBackground(Color.WHITE);
				black=false;
				buttons[row+1][column].setBlack();
			}
		}
		public void newBlack(int i)
		{
			Random r= new Random();
			int temp= r.nextInt(4);
	
			if(!(buttons[i][temp].black))
			{
				buttons[i][temp].setBlack();
			}
			else 
				newBlack(i);
			
		}
		public void setBlacks()
		{
			for(int i=0;i<3;i++)
			{
				newBlack(i);
			}
		}
		public void setBlack()
		{
			if(row!=3)
			{
				setBackground(Color.BLACK);
				black=true;
			}
		}
		public void checkWhite()
		{
			if((!black)&&row==2){System.exit(0);}
			else if(count==19)
			{
				moveDown();
				time2 = System.currentTimeMillis();
				JOptionPane.showMessageDialog(null,"You Win. Your Time: "+ (time2-time1)/1000+"."+(time2-time1)%1000);
			}
			else if(count>=17&&black&&row==2)
			{
				moveDown();
				count++;
			}
			else if(black&&row==2) 
			{
				moveDown();
				newBlack(0);
				count++;
				
			}
			else 
				return;
				//you lose
		}
		public void mouseExited(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
	}
	public static void main(String[] args)
	{
		new TouchTheWhiteLine();
	}
}