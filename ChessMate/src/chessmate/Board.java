package chessmate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.io.*;
/*
* Encapsulates the idea of a board which contains a list of tiles 
*
*/
class Board extends JPanel implements MouseListener
{  
    JFrame mainMenu;
    Tile[][] tiles = new Tile[8][8];//the entire board.
    AI ai= null;
    Piece userPiece= null;
    Tile goal= null;
    int level;
    Board(int level)
    {
        this.level=level;
        ai= new AI();
        readLevel(level);
        addMouseListener(this);
    }	
    Tile getUser()
    {
        return ai.getUser();
    }
    void setMainMenu(JFrame f)
    {
        this.mainMenu =f;
    }
    //calls draw on every tile and asks each tile to draw itself 
    @Override
    public void paintComponent(Graphics g)
    {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            int count=0;
            for(int i=0;i<tiles.length;i++)
            {
                    for(int j=0;j<tiles[i].length;j++)
                    {
                            count%=2;
                            tiles[i][j].draw(g,count);
                            count++;
                    }
                    count++; 
            }
    }
    //reads in a map for the current level. 
    void readLevel(int level)
    {
        
        Scanner sc= null;
        try{
            File file = new File(System.getProperty("user.dir")+"/resources/map/level"+level+".txt");
            sc = new Scanner(file);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        int token= sc.nextInt();//player token.

        for(int i=0;sc.hasNextInt();i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                int temp = sc.nextInt();
                if(temp>3)//AI tile
                {
                    tiles[j][i]= getPiece(j,i,temp,0);
                    ai.addPiece((Piece)tiles[j][i]);
                }
                else if(temp==2)//0 is immovable, 1 is regular
                {
                    userPiece= getPiece(j,i,token,1);
                    tiles[j][i]= userPiece;
                }
                else 
                {
                    if(temp==3)//goal
                    {
                            goal= new Tile(j,i,temp);
                    }
                    tiles[j][i] = new Tile(j,i,temp);
                }
            }
        }
        userPiece.setGoal(goal);
        ai.addUser(userPiece);
    }

    //various listener methods.
    public Piece getPiece(int x, int y, int i, int color)
    {
        switch(i)
        {
            case(4):return(new Rook(x,y,4,color));
            case(5):return(new Knight(x,y,5,color));
            case(6):return(new Bishop(x,y,6,color));
            case(7):return(new Queen(x,y,7,color));
        }
        return null;
    }
    //mouse event handler, handles user turns and ai calls
    public void mousePressed(MouseEvent e)
    {

        Tile moveLoc = null;
        outer: for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                moveLoc = tiles[j][i].check(e);

                if(moveLoc!=null)
                {
                    break outer;
                }
            }
        }
        Tile tempPiece = new Tile(userPiece);
        Tile[][] temp= userPiece.move(moveLoc,tiles,ai);
        
        //user reached the goal?
        if(userPiece.equals(userPiece.getGoal()))
        {
            repaint();
            JOptionPane.showMessageDialog(null,"You win");
            mainMenu.dispose();
            mainMenu= new MainFrame();
            mainMenu.setVisible(true);
        }
        //valid move?
        else if(!(userPiece.equals(tempPiece)))
        {
            tiles=temp;
            if(!ai.isEmpty())
            {
                ai.updateUser(userPiece);

                tiles= ai.decision(tiles);
                
                //user got taken?
                if(ai.getUser()==null)
                {
                    repaint();
                    JOptionPane.showMessageDialog(null,"Game Over");
                    mainMenu.dispose();
                    mainMenu= new MainFrame();
                    mainMenu.setVisible(true);
                }
            } 
        }
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                System.out.print(tiles[j][i].getType()+ ", ");
            }
            System.out.println();
        }
        repaint();
    }
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e){}
}	
