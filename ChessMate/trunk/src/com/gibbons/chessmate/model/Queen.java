package com.gibbons.chessmate.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Encapsulates a queen piece
 */

class Queen extends Piece
{
    int color;
    BufferedImage BQueen;
    BufferedImage WQueen;
    Queen(int x,int y, int type, int color)
    {
        super(x,y,type,"Queen",color);
        this.color= color;
        try
        {
            if(color==0) {
                File file = new File(System.getProperty("user.dir")+"/resources/drawable/blackqueen.png");
                BQueen = ImageIO.read(file);
            }
            else {
                File file = new File(System.getProperty("user.dir")+"/resources/drawable/whitequeen.png");
                WQueen = ImageIO.read(file);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public Tile[][] move(Tile t, Tile[][] tiles, AI ai)
    {
        if(t.getType()==0)
            return tiles;
        int diag = checkDirQ(t);
        Tile temp;
        if(diag > 0){
            temp= checkRouteQ(t,diag, tiles);
            if(temp!=null)
            {
                tiles = (super.move(temp,tiles,ai));
            }
        }
        return tiles;
    }
    int checkDirQ(Tile goal){
        if(super.checkDiag(goal)==0)
            return super.checkOrth(goal);
        else return (super.checkDiag(goal));
    }

    Tile checkRouteQ(Tile goal, int direction, Tile[][] tiles){
        if(direction > 4)
            return super.checkRouteDiag(goal, direction, tiles);
        else
            return super.checkRoute(goal, direction, tiles);
    }
    @Override
    public void draw(Graphics g, int i)
    {
        super.draw(g,i,BQueen,WQueen,color);
    }
}