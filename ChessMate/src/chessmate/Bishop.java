package chessmate;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/*
* Encapsulates a bishop piece
*
*/
class Bishop extends Piece
{
    BufferedImage BBishop;
    BufferedImage WBishop;
    int color;
    //reads in images for bishop 
    Bishop(int x,int y, int type, int color)
    {
        super(x,y,type,"Bishop",color);
        this.color= color;
        try
        {
            if(color==0) {
                File file = new File(System.getProperty("user.dir")+"/resources/drawable/blackbishop.png");
                BBishop = ImageIO.read(file);
            }
            else {
                File file = new File(System.getProperty("user.dir")+"/resources/drawable/whitebishop.png");
                WBishop = ImageIO.read(file);
            }
        }
        catch(Exception e){
        e.printStackTrace();
        }
    }
    //moves bishop
    @Override
    public Tile[][] move(Tile t, Tile[][] tiles, AI ai)
    {
        if(t.getType()==0)
            return tiles;
        int diag = checkDiag(t);
        Tile temp;
        if(diag > 0){//if on a diagonal allow move
            temp= checkRouteDiag(t,diag,tiles);
            if(temp!=null)
            {
                return(super.move(temp,tiles,ai));
            }
        }
        return tiles;	
    }
    @Override
    int checkDiag(Tile goal){
        return super.checkDiag(goal);
    }
	
    @Override
    public Tile checkRouteDiag(Tile goal, int direction, Tile[][] tiles){
        return super.checkRouteDiag(goal, direction, tiles);
    }

    @Override
    public void draw(Graphics g, int i)
    {
        super.draw(g,i,BBishop,WBishop,color);
    }
}
