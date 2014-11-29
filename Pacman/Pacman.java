/* To do: 
* Change map to include 3's which represent blank space.
* Add balls when we see a 1. This is done in lab version.
* Add special balls when we see a 2. Not going to include.
*/ 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
class Pacman extends JPanel implements KeyListener
{
	int score=0;
	int width, length;
	static int speed=200;
	static int x1=14, y1=19;
	
	int direction=3;
	int frameX=0;
	int frameY=0;
	Scanner in; 
	BufferedImage pacman;
	ChaserGhost ghost1;
	ChaserGhost ghost3;
	RandomGhost ghost2;
	RandomGhost ghost4;
	int SIZE= 28;
	int rows = 25;
	int cols = 28;
	int[][] map= new int[rows][cols];
	Random r = new Random();
	Color c= Color.BLACK;
	
	Pacman(int width, int length)
	{
		try{
			
			pacman= ImageIO.read(new File("pacman.png"));
			in = new Scanner(new File("pacmanMaze.txt"));
			ghost1 = new ChaserGhost(1,1,"blinky.png");
			ghost3 = new ChaserGhost(26,23,"clyde.png");
			ghost2 = new RandomGhost(26,1,"inky.png");
			ghost4 = new RandomGhost(1,23,"pinky.png");
		}catch(IOException e){System.out.println("Error");}
		this.width=width;
		this.length=length;
		setBackground(Color.WHITE);
		addKeyListener(this);
		readMaze();
		ghost1.start();
		ghost3.start();
		ghost2.start();
		ghost4.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintMaze(g);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		//g.drawString(""+score, 50,50);
		g.setColor(c);
		ghost1.draw(g);
		ghost3.draw(g);
		ghost2.draw(g);
		ghost4.draw(g);
		g.drawImage(pacman.getSubimage(frameX*30,frameY*30,SIZE,SIZE),(x1*SIZE),y1*SIZE,null);
		
	}
	public void readMaze()//reads in perfectly
	{
		for(int i=0; i<rows;i++)
		{
			for(int j=0; j<cols; j++)
			{
				map[i][j]=in.nextInt();
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}	
	public void paintMaze(Graphics g)
	{
		g.setColor(new Color(80,0,0));
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(map[i][j]==0)//rows is the y, cols is the x
					g.fillRect(j*SIZE,i*SIZE,SIZE,SIZE);
			}
		}
	}
	public void update()
	{
		frameX++;
		if(frameX==3)
			frameX=0;
	}
	public static void main(String[] args)
	{	
		int width=1000;
		int length=1000;
		JFrame jp1 = new JFrame();
        Pacman p =new Pacman(width,length);
		jp1.addKeyListener(p);
		jp1.add(p);
        jp1.setBounds(0,0,width+30,length+50);
        jp1.setVisible(true);
		jp1.setResizable(true);
		
		while(true)
		{
			p.move();
			try{
				Thread.sleep(speed);
			}catch(Exception e){}
		}
	}
	public void keyPressed(KeyEvent e)
	{
		//up 38 //down 40 //right 39 //left 37
		
		if(e.getKeyCode()==38)
		{
			if(map[y1-1][x1]!=0)
			{	
				direction=0;
				frameY=1;
			}
		}
		else if(e.getKeyCode()==39)
		{
			if((x1+1)==cols||map[y1][x1+1]!=0)
			{
				direction=1;
				frameY=2;
			}
		}
		else if(e.getKeyCode()==37)
		{
			if((x1-1)==-1||map[y1][x1-1]!=0)
			{
				direction=3;
				frameY=0;
			}
		}
		else if(e.getKeyCode()==40)
		{
			if(map[y1+1][x1]!=0)
			{
				direction=2;
				frameY=3;
			}
		}
	}
	public void up()
	{
		if(map[y1-1][x1]!=0)
		{
			y1--;
			
		}
		repaint();
	}
	public void right()
	{
		if(x1>=cols-1)
			x1=0;
		else if(map[y1][x1+1]!=0)
			x1++;
		repaint();
	}
	public void down()
	{
		
		if(map[y1+1][x1]!=0)
			y1++;	
		repaint();
	}
	public void left()
	{
		if(x1<=0)
			x1=cols-1;
		else if(map[y1][x1-1]!=0)
			x1--;
		repaint();
	}
	public void move()
	{
		update();
		if(direction==0)
			up();
		else if(direction==1)
			right();
		else if(direction==2)
			down();
		else
			left();	
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e) {}
	
	//nested class so we have access to the pacman's x and y.
	abstract class Ghost extends Thread
	{
		int x, y;
		BufferedImage ghost;
		
		Ghost(int x, int y, String file) throws IOException
		{
			this.x=x;
			this.y=y;
			this.ghost= ImageIO.read(new File(file));
		}
		public void run()
		{
			while(true)
			{
				ghostMove();
			}
		}	
		void draw(Graphics g) 
		{
			g.drawImage(ghost,x*SIZE,y*SIZE,null);
		}
		boolean up()
		{
			if(map[y-1][x]!=0)
			{
				y--;
				repaint();
				try{
					Thread.sleep(speed);
				}catch(Exception e){}
				return true;
			}
			return false;
		}
		boolean down()
		{
			if(map[y+1][x]!=0)
			{
				y++;
				repaint();
				try{
					Thread.sleep(speed);
				}catch(Exception e){}
				return true;
			}
			return false;
		}
		boolean left()
		{
			if(x==0)
			{
				x=cols-1;
				return true;
			}
			else if(map[y][x-1]!=0)
			{
				x--;
				repaint();
				try{
					Thread.sleep(speed);
				}catch(Exception e){}
				return true;
			}
			return false;
		}
		boolean right()
		{
			if(x==cols-1)
			{
				x=0;
				repaint();
				return true;
			}
			else if(map[y][x+1]!=0)
			{	
				x++;
				repaint();
				try{
					Thread.sleep(speed);
				}catch(Exception e){}
				return true;
			}	
			return false;
		}
		void moveRandomly() //Move until we get close to the pacman
		{
			Random r= new Random();
			int i= r.nextInt(4);//0-3
			System.out.println(i);
			switch(i){
				case 0: while(up()){
							if(right()||left())
							{
								moveRandomly();
								break;
							}
							
						}
						break;
				case 1: while(down()){
							if(left()||right())
							{
								moveRandomly();
								break;
							}
							
						}
						break;
				case 2: while(right()){
							if(up()||down())
							{
								moveRandomly();
								break;
							}
							
						}
						break;
				case 3: while(left()){
							if(down()||up())
							{
								moveRandomly();
								break;
							}
							
						}
						
			}
		}
		abstract void ghostMove();
	}
	
	class ChaserGhost extends Ghost //always chases pacman when possible.
	{
		ChaserGhost(int x, int y, String file) throws IOException
		{
			super(x,y,file);
		}
		
		public void ghostMove()//moves based on pacman coords
		{
			if(x==x1&&y==y1)
			{
				//System.out.println(0);
			}
			else if(y>y1&&up()){}

			else if(y<y1&&down()){}
			
			else if(x>x1&&left()){}
			
			else if(x<x1&&right()){}
			
			else {//we lost him just move randomly
				moveRandomly();
				
			}	
			
		}
	}
	class RandomGhost extends Ghost
	{
		RandomGhost(int x, int y, String file) throws IOException
		{
			super(x,y,file);
		}
		public void ghostMove()
		{
			moveRandomly(-1);
		}
		void moveRandomly(int i) //Move until we get close to the pacman
		{
			System.out.println(x+ " " +x1+ " " + y+ " "+ y1);
			if(x==x1&&y==y1)
			{
				System.exit(0);
			}
			if(i==-1)
				i= r.nextInt(4);//0-3
			System.out.println(i);
			switch(i){
				case 0: while(up()){ 
							i=r.nextInt(3);//0 or 1
							if(i==0) //this ensures we check right or left at random instead of always checking right first.
							{
								if(right())
								{
									moveRandomly(2);
									break;
								}
								else if(left())
								{
									moveRandomly(3);
									break;
								}
							}
							else 
							{
								if(left())
								{
									moveRandomly(3);
									break;
								}
								else if(right())
								{
									moveRandomly(2);
									break;
								}
							}
						}
						break;
				case 1: while(down()){
							i=r.nextInt(3);//0 or 1
							if(i==0)
							{
								if(right())
								{
									moveRandomly(2);
									break;
								}
								else if(left())
								{
									moveRandomly(3);
									break;
								}
							}
							else if(i==1)
							{
								if(left())
								{
									moveRandomly(3);
									break;
								}
								else if(right())
								{
									moveRandomly(2);
									break;
								}
							}
						}
						break;
				case 2: while(right()){
							i=r.nextInt(3);//0 or 1
							if(i==0)
							{
								if(up())
								{
									moveRandomly(0);
									break;
								}
								else if(down())
								{
									moveRandomly(1);
									break;
								}
							}
							else 
							{
								if(down())
								{
									moveRandomly(1);
									break;
								}
								else if(up())
								{
									moveRandomly(0);
									break;
								}
							}
							
						}
						break;
				case 3: while(left()){
							i=r.nextInt(3);//0 or 1
							if(i==0)
							{
								if(up())
								{
									moveRandomly(0);
									break;
								}
								else if(down())
								{
									moveRandomly(1);
									break;
								}
							}
							else 
							{
								if(down())
								{
									moveRandomly(1);
									break;
								}
								else if(up())
								{
									moveRandomly(0);
									break;
								}
							}
							
						}
						
			}
		}
	}
}
