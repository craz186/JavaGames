/*	Add actual graphics later.
*	Interesting project about a co existing environment of bears lumberjacks and trees.
*
*   Need to implement Jacks & Bears on Trees
*/

package com.gibbons.simulator.controller;

import com.gibbons.simulator.Time;
import com.gibbons.simulator.objects.Forest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.gibbons.simulator.SimulatorConstants.SIZE;

public class SimulatorController extends JPanel implements KeyListener
{
	private Forest forest;
	private Time t;

    public SimulatorController()
	{
		setBackground(Color.WHITE);
		forest= new Forest((int)((SIZE*SIZE)*.5),(int)((SIZE*SIZE)*.1),(int)((SIZE*SIZE)*.02));
		setVisible(true);
		repaint();
		t = new Time();
	}
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		forest.drawArea(g);
	}

	public void keyReleased(KeyEvent e)
	{
	}
	public void keyPressed(KeyEvent e)
	{
		System.out.println("ALRI");
		t.tick(forest);

        forest.printData();
        repaint();

	}
	public void keyTyped(KeyEvent e)
	{
	
	}

    public static void main(String[] args)
    {
        SimulatorController es= new SimulatorController();

        JFrame jf= new JFrame();
        jf.add(es);
        jf.addKeyListener(es);
        jf.setBounds(0,0,1000+30,1000+50);
        jf.setVisible(true);
    }
}
