import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;

class CountReps extends JPanel implements KeyListener
{
	private int count=0;
	
	CountReps()
	{
		addKeyListener(this);
		setSize(100,100);
		setVisible(true);
		repaint();
	}
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Tahoma",Font.PLAIN,30));
		g.drawString(count+"",45,40);
	}
	
	public void keyReleased(KeyEvent e)
	{	
		count++;
		repaint();
	}
	public void keyPressed(KeyEvent e){
		
	}
	public void keyTyped(KeyEvent e){}
	
	public static void main(String[] args)
	{
		JFrame jf= new JFrame();
		CountReps cp= new CountReps();
		jf.add(cp);
		jf.setVisible(true);
		//jf.setSize(100,100);
		jf.addKeyListener(cp);
	}
}