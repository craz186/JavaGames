import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.image.*;
class Snake extends JPanel implements KeyListener
{
	int score=0;
	int width, length, size=10;
	static int speed = 50;
	static LinkedList<Point> points= new LinkedList<>();
	int x1=250, y1=250;
	int x3,x2,y3,y2;
	static LinkedList<Integer> direction= new LinkedList<>();
	int num=1;
	
	Random r = new Random();
	Color c= Color.BLACK;
	Snake(int width, int length)
	{
		direction.add(2);
		points.add(new Point(x1,y1));
		this.width=width;
		this.length=length;
		setBackground(Color.WHITE);
		addKeyListener(this);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.fillRect(x3,y3,size,size);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g.drawString(""+score, 50,50);
		g.setColor(c);
		x2=x1; y2=y1;
		for(int i=0;i<num;i++)
		{
			g.drawRect(x2,y2,size,size);
			changeValue(i);									
		}
		g.drawRect(0,0,width+10,length+10);
	}
	public void changeValue(int i)
	{
		if(direction.get(i)==0)
		{
			if(y2==length)
				y2=0;
			else
				y2+=10;
		}
		else if(direction.get(i)==1)
		{
			
			if(x2==0)
				x2=width;
			else
				x2-=10;
		}
		else if(direction.get(i)==2)
		{
			if(y2==0)
				y2=length;
			else
				y2-=10;
		}
		else
			if(x2==width)
				x2=0;
			else
				x2+=10;
	}
	public static void main(String[] args)
	{	
		int width= 500;
		int length= 500;
		JFrame jp1 = new JFrame();
        Snake p =new Snake(width,length);
		p.addBlock();
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
			if(direction.get(0)!=2)
				direction.set(0,0);
		}
		else if(e.getKeyCode()==39)
		{
			if(direction.get(0)!=3)
				direction.set(0,1);
		}
		else if(e.getKeyCode()==37)
		{
			if(direction.get(0)!=1)
				direction.set(0,3);
		}
		else if(e.getKeyCode()==40)
		{
			if(direction.get(0)!=0)
				direction.set(0,2);
		}
	}
	public void up()
	{
		if(y1==0)
			y1=length;
		else
			y1-=10;
		checkBlock();	
		repaint();
	}
	public void right()
	{
		if(x1==width)
			x1=0;
		else
			x1+=10;
		checkBlock();	
		repaint();
	}
	public void down()
	{
		if(y1==length)
			y1=0;
		else
			y1+=10;
		checkBlock();	
		repaint();
	}
	public void left()
	{
		if(x1==0)
			x1=width;
		else
			x1-=10;
		checkBlock();	
		
		repaint();
	}
	public void move()
	{
		if(direction.get(0)==0)
			up();
		else if(direction.get(0)==1)
			right();
		else if(direction.get(0)==2)
			down();
		else
			left();
			
		for(int i=direction.size()-1;i>0;i--)
		{
			direction.set(i,direction.get(i-1));
		}
	}
	public boolean foundBlock()
	{
		return x1==x3&&y1==y3;
	}
	public void checkBlock()
	{
		if(foundBlock())
		{
			addBlock();
			if(speed>15)
			speed--;
			num++;
			score++;
			direction.add(direction.get(direction.size()-1));
			points.add(new Point(x1,y1));
		}
	}
	public void addBlock()
	{
		x3=r.nextInt(50);
		y3=r.nextInt(50);
		if((x3*10)==x1||(y3*10)==y1)
			addBlock();
		else 
		{
			x3*=10;
			y3*=10;
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e) {
			
	}
	class Point
	{
		int x,y;
		Point(int x,int y)
		{
			this.x=x/10;
			this.y=y/10;
		}
		int getPoint()
		{
			return x*y;
		}
	}
}
