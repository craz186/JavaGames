import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.image.*;

class MyFirstFlappy extends JPanel implements KeyListener
{ 
	//get ball moving down first.
	//create walls
	//Add collision detection
	JFrame j= new JFrame();
	private int width,length;
	private int x1= 100,x2,y1= 250,y2;
	private int size=20;
	private int score=0;
	MyFirstFlappy(int width, int length)
	{
		this.width=width;
		this.length=length;
		setBackground(Color.WHITE);
		addKeyListener(this);
		j.add(this);
		j.setSize(width,length);
		j.setVisible(true);
		j.setResizable(false);
		j.requestFocusInWindow();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString(""+score, 50,50);
		g.Oval(x1,y1,size,size);
		g.drawRect(0,0,width+10,length+10);
	}
	public void keyPressed(KeyEvent e)
	{
		//up 38 //down 40 //right 39 //left 37
		System.out.println(1);
	}
	public void keyTyped(KeyEvent e){
		System.out.println(1);
	}
	public void keyReleased(KeyEvent e) {
		System.out.println(1);
	}
	public static void main(String[] args)
	{
		JFrame j= new JFrame();
		MyFirstFlappy mff= new MyFirstFlappy(750,500);
		mff.addKeyListener(mff);

		j.setVisible(true);
		j.setResizable(false);
		j.requestFocusInWindow();
	}
}