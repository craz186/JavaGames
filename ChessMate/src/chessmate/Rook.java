package chessmate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File; 

/*
 * Encapsulates the Rook piece
 */

class Rook extends Piece
{
    int x,y;
    BufferedImage BRook;
    BufferedImage WRook;
    //reads in rook images
    Rook(int x, int y, int type, int color)
    {
        super(x,y,type,"Rook",color);
        this.x = x;
        this.y = y;
        this.color=color;
        try {
            if(color==0) {
                File file = new File(System.getProperty("user.dir")+"/resources/drawable/blackrook.png");
                BRook = ImageIO.read(file);
            }
            else {
                File file = new File(System.getProperty("user.dir")+"/resources/drawable/whiterook.png");
                WRook = ImageIO.read(file);
            }
        }catch(Exception e){}
    }
    //moves the rook
    public Tile[][] move(Tile t, Tile[][] tiles,AI ai)
    {
        if(t==null)
            return tiles;
        if(t.getType()==0)
            return tiles;
        int dir = checkOrth(t);
        Tile temp;
        if(dir > 0){//if on a direction allow move

            temp = checkRoute(t,dir,tiles);

            if(temp!=null)
            {
                tiles=(super.move(temp,tiles,ai));
            }
        }
        return tiles;
    }
    //check direction of rook
    @Override
    public int checkOrth(Tile goal){
        return super.checkOrth(goal);
    }
    //check route of rook
    @Override
    public Tile checkRoute(Tile goal, int direction, Tile[][] tiles){
        return super.checkRoute(goal, direction, tiles);
    }
    //draw to panel
    @Override
    public void draw(Graphics g, int i)
    {
        super.draw(g,i,BRook,WRook,color);
    }
}
