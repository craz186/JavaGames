package chessmate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
/*
* Encapsulates the idea of a knight piece
*
*/
class Knight extends Piece
{
	BufferedImage BKnight = null;
	BufferedImage WKnight = null;
	int color;
        //Declares Knight and sets up images.
	Knight(int x,int y, int type, int color)
	{
            super(x,y,type,"Knight",color);
            this.color= color;
            try {
                if (color == 0) {
                    File file = new File(System.getProperty("user.dir")+"/resources/drawable/blackknight.png");
                    BKnight = ImageIO.read(file);
                } else {
                    File file = new File(System.getProperty("user.dir")+"/resources/drawable/whiteknight.png");
                    WKnight = ImageIO.read(file);
                }
            }
            catch(FileNotFoundException e){}
            catch(IOException e){}
	}
        //moves Knight to given tile
        @Override
	public Tile[][] move(Tile t, Tile[][] tiles,AI ai)//once the destination tile is valid the knight can move there regardless.
	{
            if(t==null)
                return tiles;
            int tempX = t.getX();
            int tempY = t.getY();

            if(t.getType()==0)
                return tiles;
            if(checkRoute(t,0,tiles).equals(t))
            {	
                tiles =(super.move(t,tiles,ai));
            }
            return tiles;
	}
        //gets possible moves for knight
	@Override
	public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)//direction is here for generics sake
	{
            LinkedList<Tile> moves = new LinkedList<>();
            int[] knightX = { -1, 1, -2, 2, -2, 2, -1, 1 };
            int[] knightY = { -2, -2, -1, -1, 1, 1, 2, 2 };
            for(int i=0; i<knightX.length; i++)//8 possible positions for a knight to move.
            {
                int newX = x+ knightX[i];
                int newY = y+ knightY[i];
                if(newX<0||newX>=tiles[0].length||newY<0||newY>=tiles.length||tiles[newX][newY].getType()==0)
                        continue;
                if(tiles[newX][newY].getOccupied()&&color==tiles[newX][newY].getColor())
                        continue;
                moves.add(tiles[newX][newY]);
            }
            return moves;
	}
        //checks route is valid to given tile
	@Override
	public Tile checkRoute(Tile t,int dir,Tile[][] tiles)//generic sake
	{
            if(this.getColor()==0&&t.getType()==3)
                return this;
            int xNum= 2, yNum=1;
            int tempX=t.getX(), tempY=t.getY();
            for(int i=0; i<2; i++)//8 possible positions for a knight to move.
            {
                if(x-xNum==tempX&&y-yNum==tempY)
                        return t;
                else if(x-xNum==tempX&&y+yNum==tempY)
                        return t;
                else if(x+xNum==tempX&&y-yNum==tempY)
                        return t;
                else if(x+xNum==tempX&&y+yNum==tempY)
                        return t;
 
                xNum=1;
                yNum=2;
            }
            return this;
	}
        //draws to panel
	public void draw(Graphics g, int i)
	{
            super.draw(g,i,BKnight,WKnight,color);
	}
}
