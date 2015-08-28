package com.gibbons.chessmate.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
/*
* Encapsulates the idea of piece on a tile
*
*/  
abstract class Piece extends Tile{
		
    String name;
    Tile goal;
    Tile last;
    boolean moved=false;
    Piece(int x, int y, int type, String name, int color)
    {
        super(x,y,type);
        occupied=true;
        this.name=name;
        this.color= color;
    }
    String getName()
    {
        return name;
    }
    void setGoal(Tile goal) {this.goal=goal;}
    Tile getGoal() {return goal;}
    void setMoved(){moved=!moved;}
    boolean getMoved(){return moved;}

    //moves the position of the piece without checking if it's valid
    public Tile[][] changePos(Tile t,Tile[][] tiles)
    {
        last = tiles[x][y];
        Tile temp= tiles[x][y];
        tiles[x][y] = tiles[t.x][t.y];
        tiles[t.x][t.y] = temp;
        x=t.x;
        y=t.y;
        return tiles;
    }
    //moves the position of the piece and checks if it's valid.
    public Tile[][] move(Tile t, Tile[][] tiles,AI ai)
    {
        if(t.equals(this))
            return tiles;
        last = tiles[x][y];
        if(t.getType()==3)
        {
            tiles[t.x][t.y]= tiles[this.x][this.y];
            tiles[this.x][this.y] = new Tile(this.x,this.y,1);
        }
        else if(t.getOccupied())
        {
            //we took the user
            if(t.color==1)
            {
                ai.removeUser();
            }
            //we took an ai
            else if(t.color!=this.color)
            {
                ai.removePiece(t);
            }
            t= new Tile(t.x,t.y,0);
            tiles[t.x][t.y]= tiles[this.x][this.y];
            tiles[this.x][this.y] = new Tile(this.x,this.y,1);
        }
        else
        {
            Tile temp = tiles[this.x][this.y];
            tiles[this.x][this.y] = tiles[t.x][t.y];
            tiles[t.x][t.y]= temp;
        }
        int tempX=this.x, tempY=this.y;
        this.x=t.x;
        t.x=tempX;
        this.y=t.y;
        t.y=tempY;
        return tiles;
    }
    //draws on the panel
    public void draw(Graphics g, int i, BufferedImage BPiece, BufferedImage WPiece, int color)
    {
        super.draw(g,i);
        if(color==0)
            g.drawImage(BPiece,x*width,y*width,null);
        else
            g.drawImage(WPiece,x*width,y*width,null);
    }
    //gets all possible moves for any piece bar knight
    public LinkedList<Tile> getMoves(Tile[][] tiles, int direction)
    {
        if(direction==0||direction>4)
                return(new LinkedList<>());
        int tempX=x;
        int tempY=y;
        LinkedList<Tile> moves = new LinkedList<>();
        boolean occupied= false;
        boolean outOfBounds;
        boolean cantMoveGoal=false;
        while(true)
        {				
            //down left, up left, up right, down right
            if(direction==1)//down,up,right,left
            {
                tempY+=1;
                if(name.equals("Bishop"))
                {
                        tempX-=1;
                }
            }
            else if(direction==2)
            {
                tempY-=1;
                if(name.equals("Bishop"))
                {
                        tempX-=1;
                }
            }
            else if(direction==3)
            {
                tempX+=1;
                if(name.equals("Bishop"))
                {
                        tempY-=1;
                }
            }
            else if(direction==4)
            {
                tempX-=1;
                if(name.equals("Bishop"))
                {	
                        tempX+=2;
                        tempY+=1;
                }
            }
            outOfBounds=tempY>=tiles.length||tempX>=tiles[0].length||tempY<0||tempX<0;
            if(!outOfBounds) {
                occupied = tiles[tempX][tempY].getOccupied();
                cantMoveGoal = (tiles[tempX][tempY].getType() == 3 && color == 0);

            }
            if(outOfBounds||occupied||(tiles[tempX][tempY].getType()==0)||cantMoveGoal)
            {
                if(occupied&&color != tiles[tempX][tempY].getColor())
                {
                    moves.add(tiles[tempX][tempY]);
                }
                moves.addAll(getMoves(tiles,(direction+1)));
                return moves;
            }

            moves.add(tiles[tempX][tempY]);
        }
    }

}
