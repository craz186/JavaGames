import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class XsOs extends JFrame
{
	private JButton[][] buttons= new JButton[3][3];
	private boolean isBlack=true;
	ImageIcon wuz = new ImageIcon("wuz.png");
	ImageIcon craz= new ImageIcon("craz.png");
	XsOs()
	{
		super("X's and O's");
		setLayout(new GridLayout(3,3)); 
		setSize(960,960);
		for (int i=0; i<3; i++) {	// add button i
			for(int j=0; j<3; j++)
			{
				buttons[i][j] = new JButton();
				add(buttons[i][j]);
				buttons[i][j].setForeground(Color.WHITE);
				buttons[i][j].setBackground(Color.LIGHT_GRAY);
				buttons[i][j].addActionListener(new PlayButtons(i,j));

			}
		}        
		setVisible(true);
	}
	class PlayButtons implements ActionListener { // for numbered buttons
		private int row;
		private int column;// button number (0<=k<15)
		private String color;
		PlayButtons(int r, int c) { row = r; column= c;}
				  
		private void changeColor()
		{
			if(!isBlack)
			{
				buttons[row][column].setBackground(Color.WHITE);
				buttons[row][column].setIcon(wuz);
				color="Wuz";
				isBlack=true;
			}
			else 
			{
				buttons[row][column].setBackground(Color.BLACK);
				buttons[row][column].setIcon(craz);
				color="Craz";
				isBlack=false;
			}
		}
		int left()
		{
			if(column==0)
				return 2;
			else 
				return column-1;
		}
		int right()
		{	
			if(column==2)
				return 0;
			else 
				return column+1;
		}
		int up()
		{
			if(row==0)
			return 2;
			else 
			return row-1;
		}
		int down()
		{
			if(row==2)
			return 0;
			else 
			return row+1;
		}
		boolean checkRows()
		{
			return(buttons[row][column].getBackground().equals(buttons[row][right()].getBackground())&&
			buttons[row][column].getBackground().equals(buttons[row][left()].getBackground()));
		}
		boolean checkColumns()
		{
			return(buttons[row][column].getBackground().equals(buttons[up()][column].getBackground())&&
			buttons[row][column].getBackground().equals(buttons[down()][column].getBackground()));
		}
		boolean checkDiag1() 
		{
			if(column+row==2)
				return(buttons[row][column].getBackground().equals(buttons[up()][right()].getBackground())&&
						buttons[row][column].getBackground().equals(buttons[down()][left()].getBackground()));	
			else 
				return false;
		}
		boolean checkDiag2()
		{
			if(column==row)
				return(buttons[row][column].getBackground().equals(buttons[up()][left()].getBackground())&&
						buttons[row][column].getBackground().equals(buttons[down()][right()].getBackground()));
			else 
				return false;
		}
		private void checkWinner()
		{
			if(checkRows()||checkColumns()||checkDiag1()||checkDiag2())
			{
				JOptionPane.showMessageDialog(null,"The winner is: " +color);
			}
		}
		public void actionPerformed (ActionEvent e) {
	
			changeColor();
			checkWinner(); 
		}
	}
	public static void main(String[] args)
	{
	new XsOs();
	}
}