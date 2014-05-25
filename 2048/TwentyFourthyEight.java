import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
class TwentyFourthyEight extends JFrame 
{
	private Block[][] buttons= new Block[4][4];
	private JPanel jf= new JPanel();
	TwentyFourthyEight()
	{
		super("2048");
		setLayout(new GridLayout(4,4)); 
		setSize(700,700);
		for (int i=0; i<4; i++) {	// add button i
			for(int j=0; j<4; j++)
			{
				buttons[i][j] = new Block(i,j);
				buttons[i][j].addKeyListener(buttons[i][j]);
				buttons[i][j].setFont(new Font("Arial",Font.BOLD,26));
				
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setForeground(Color.DARK_GRAY);
				add(buttons[i][j]);
			}
		}        
		
		//jf.addKeyListener(new Tiles());
		setVisible(true);
		buttons[0][0].newTwo();
	}
	class Block extends JButton implements KeyListener
	{	
		private int row,column;
		private int value=0;
		Block(int row, int column)
		{
			this.row=row;
			this.column=column;
		}
		public void keyTyped(KeyEvent event) {
			
		}
		 public void keyPressed(KeyEvent e) {
			System.out.println("I noticed you pressed a key");
			if (e.getKeyCode()==38) {
				if(moveUp())
				{
					newTwo();
				}
			}
			if (e.getKeyCode()==39) {
				if(moveRight())
				{
					newTwo();
				}
			}
			if (e.getKeyCode()==40) {
				if(moveDown())
				{
					newTwo();
				}
			}
			if (e.getKeyCode()==37) {
				if(moveLeft())
				{
					newTwo();
				}
			}
			System.out.println(e.toString());
		}
		public void keyReleased(KeyEvent e) {
			
		}
		boolean moveUp()
		{
			boolean change=false;
			for(int i=1;i<4;i++)//=1 because 1st row can't go up
			{
				for(int j=3;j>-1;j--)
				{

					if(buttons[i][j].up())
					{
						change=true;
					}
				}
			}
			return change;
		}
		boolean moveRight()
		{
			boolean change=false;
			for(int j=3;j>-1;j--)//=1 because 1st row can't go up
			{
				for(int i=0;i<4;i++)
				{

					if(buttons[i][j].right())
					{
						change=true;
					}
				}
			}
			return change;
		}
		boolean moveLeft()
		{
			boolean change=false;
			for(int j=0;j<4;j++)//=1 because 1st row can't go up
			{
				for(int i=0;i<4;i++)
				{

					if(buttons[i][j].left())
					{
						change=true;
					}
				}
			}
			return change;
		}
		boolean moveDown()
		{
			boolean change=false;
			for(int i=3;i>-1;i--)//=1 because 1st row can't go up
			{
				for(int j=0;j<4;j++)
				{
					if(buttons[i][j].down())
					{
						change=true;
					}
				}
			}
			return change;
		}
		boolean up()
		{
			boolean change= false;
			if(row==0||value==0)
				return change;
			else if(buttons[row-1][column].value==0)
			{
				buttons[row-1][column].setValue(value);
				this.removeBlock();
				change=true;
				buttons[row-1][column].up();
				return change;
			}
			else if(value==buttons[row-1][column].value)
			{
				buttons[row-1][column].setValue(value*2);
				this.removeBlock();
				change=true;
				return change;
			}
			else
			{
				return(buttons[row-1][column].up());
			}

		}
		boolean right()
		{
			boolean change= false;
			if(column==3||value==0)
				return change;
			else if(buttons[row][column+1].value==0)
			{
				buttons[row][column+1].setValue(value);
				this.removeBlock();
				change=true;
				buttons[row][column+1].right();
				return change;
			}
			else if(value==buttons[row][column+1].value)
			{
				buttons[row][column+1].setValue(value*2);
				this.removeBlock();
				change=true;
				
				return change;
			}
			else
			{
				return(buttons[row][column+1].right());
			}

		}
		boolean left()
		{
			boolean change= false;
			if(column==0||value==0)
				return change;
			else if(buttons[row][column-1].value==0)
			{
				buttons[row][column-1].setValue(value);
				this.removeBlock();
				change=true;
				buttons[row][column-1].left();
				return change;
			}
			else if(value==buttons[row][column-1].value)
			{
				buttons[row][column-1].setValue(value*2);
				this.removeBlock();
				change=true;
				
				return change;
			}
			else
			{
				return(buttons[row][column-1].left());
			}

		}
		boolean down()
		{
			boolean change= false;
			if(row==3||value==0)
				return change;
			else if(buttons[row+1][column].value==0)
			{
					buttons[row+1][column].setValue(value);
					this.removeBlock();
					change=true;
					buttons[row+1][column].down();
					return change;
			}
			else if(value==buttons[row+1][column].value)
			{
				buttons[row+1][column].setValue(value*2);
				this.removeBlock();
				change=true;
				
				return change;
			}
			else
			{
				return(buttons[row+1][column].down());
			}

		}
		void newTwo()
		{
			Random r= new Random();
			int temp= r.nextInt(4*4);
			int nextValue = r.nextInt(10)<9 ? 2:4;
			if(buttons[temp/4][temp%4].value==0)
			{
				buttons[temp/4][temp%4].setValue(nextValue);
			}
			else 
				newTwo();
		}
		void setValue(int i)
		{
			value=i;
			setBackground(getBackground());
			setForeground(getForeground());
			setText(""+value);
			
		}
		public Color getBackground()
		{
			switch (value) {
				case 2: return new Color(0xeee4da);
				case 4: return new Color(0xede0c8);
				case 8: return new Color(0xf2b179);
				case 16: return new Color(0xf59563);
				case 32: return new Color(0xf67c5f);
				case 64: return new Color(0xf65e3b);
				case 128: return new Color(0xedcf72);
				case 256: return new Color(0xedcc61);
				case 512: return new Color(0xedc850);
				case 1024: return new Color(0xedc53f);
				case 2048: return new Color(0xedc22e);
			}
			return new Color(0xcdc1b4);
		}
		public Color getForeground()
		{
			if(value<5) 
				return Color.DARK_GRAY;
			else 
				return Color.WHITE;
		
		}
		void removeBlock()
		{
			value=0;
			setBackground(Color.WHITE);
			setText("");
		
		}
	}
	public static void main(String[] args)
	{
		new TwentyFourthyEight();
	}
}