package com.gibbons.simulator.objects;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import static com.gibbons.simulator.SimulatorConstants.SIZE;
import static com.gibbons.simulator.objects.InhabitantType.BEAR;
import static com.gibbons.simulator.objects.InhabitantType.JACK;
import static com.gibbons.simulator.objects.InhabitantType.TREE;


/**
 * Need to implement a manager for the forest to act on the changes
 *
 *
 */
public class Forest {
    int numTrees = 0;
    int numJacks = 0;
    int numBears = 0;
    int timber = 0;

    Inhabitant[][] inhabitants = new Inhabitant[SIZE][SIZE];

    public Forest(int numTrees, int numBears, int numJacks) {
        this.numTrees = numTrees;
        this.numBears = numBears;
        this.numJacks = numJacks;

        init();
    }
    public void growTrees() {
        Random r= new Random();
        int temp=0;
        int x0=0;
        int y0=0;
        for(int i=0;i<SIZE;i++) {
            for(int j=0;j<SIZE;j++) {
                Inhabitant currentInhabitant = inhabitants[i][j];
                Tree currentTree = null;

                if(currentInhabitant.getClass().equals(Tree.class)) {
                    currentTree = (Tree)currentInhabitant;
                }

                if(currentTree!=null&&!currentTree.sapling()) {
                    do {
                        temp = r.nextInt(9);
                        x0 = (i - 1) + (temp % 3);
                        y0 = (j - 1) + (temp / 3);
                    }while (temp == 4 || x0 < 0 || x0 >= SIZE || y0 < 0 || y0 >= SIZE);

                    if(inhabitants[x0][y0] == null) {
                        inhabitants[x0][y0] = Tree.makeSapling(x0,y0);
                    }
                }
                if(currentTree!=null) {
                    currentTree.age();
                }
            }
        }

        System.out.println("DONE NEW TREES");
    }
    void init() {
        Random r= new Random();
        int temp=0;
        int tempTrees = numTrees;
        int tempBears = numBears;
        int tempJacks = numJacks;

        for(int i=0;i<SIZE;i++) {
            for(int j=0;j<SIZE;) {
                temp= r.nextInt((SIZE)*(SIZE));

                if(temp>50&&tempTrees!=0) {

                    tempTrees--;
                    inhabitants[i][j]=new Tree(getRandomUnoccupied());

                }
                else if(temp>40&&tempJacks!=0) {
                    tempJacks--;
                    inhabitants[i][j]=new Jack(getRandomUnoccupied());
                }
                else if(temp>2) {
                }
                else if(tempBears!=0) {
                    tempBears--;
                    inhabitants[i][j]=new Bear(getRandomUnoccupied());
                }
                else {
                    if(tempJacks==0&&tempTrees==0)
                        break;
                    else
                        continue;
                }
                j++;
            }
        }
    }

    private Point getRandomUnoccupied() {
        Random r = new Random();
        int x = 0,y = 0;
        do {
            x = r.nextInt(SIZE);
            y = r.nextInt(SIZE);
        }
        while(inhabitants[x][y] != null);
        return new Point(x,y);
    }

    public void move()
    {
        LinkedList<Point> tempBears= new LinkedList<>();
        LinkedList<Point> tempJacks= new LinkedList<>();
        for(int i=0;i<SIZE;i++) {
            for(int j=0;j<SIZE;j++) {
                if(inhabitants[i][j]!=null) {
                    if(inhabitants[i][j].getType().equals(BEAR)) {
                        tempBears.add(new Point(i,j));
                    }
                    else if(inhabitants[i][j].getType().equals(JACK)) {
                        tempJacks.add(new Point(i,j));
                    }
                }

            }
        }

        for(int i=0; i<tempJacks.size();i++) {
            moveJack(tempJacks.get(i));
        }

        for(int i=0; i<tempBears.size();i++) {
            moveBear(tempBears.get(i));
        }

    }
    void moveBear(Point p) {
        int x = p.x;
        int y = p.y;
        int i=0;
        int temp=0;
        int x0=0;
        int y0=0;
        Random r= new Random();

        while(i<5) {
            temp=r.nextInt(9);
            x0=(x-1)+(temp%3);
            y0=(y-1)+(temp/3);
            if(temp==4||x0<0||x0>=SIZE||y0<0||y0>=SIZE) {
                continue;
            }
            if(inhabitants[x0][y0]!=null && inhabitants[x0][y0].getType().equals(JACK)) {
                System.out.println("A LumberJack was killed");
                numJacks--;
                inhabitants[x0][y0]=null;
                inhabitants[x0][y0]=new Bear(x0,y0);
                inhabitants[x][y]=null;
                return;
            }
            inhabitants[x0][y0]=new Bear(x0,y0);
            inhabitants[x][y]=null;
            x=x0;
            y=y0;
            i++;
        }
    }
    void moveJack(Point p) {
        int x = p.x;
        int y = p.y;
        int i=0;
        int temp=0;
        int x0=0;
        int y0=0;
        int count=0;
        Random r= new Random();
        while(i<3) {
            temp=r.nextInt(9);
            x0=(x-1)+(temp%3);
            y0=(y-1)+(temp/3);
            if(temp==4||x0<0||x0>=SIZE||y0<0||y0>=SIZE) {
                continue;
            }
            if(inhabitants[x0][y0] != null) {
                InhabitantType type = inhabitants[x0][y0].getType();
                if (type.equals(BEAR)) {
                    inhabitants[x][y] = null;
                    System.out.println(".. A LumberJack was killed");
                    numJacks--;
                    return;
                } else if (type.equals(JACK)) {
                    if (count == 2)
                        return;
                    count++;
                    continue;
                } else if (type.equals(TREE)) {
                    Tree tree = (Tree) inhabitants[x0][y0];
                    if (!tree.sapling()) {
                        inhabitants[x0][y0] = null;
                        timber++;
                        numTrees--;
                        inhabitants[x0][y0] = new Jack(x0, y0);
                        inhabitants[x][y] = null;
                        System.out.println("A tree was chopped down");
                        return;
                    }
                }
            }
            inhabitants[x0][y0]=new Jack(x0,y0);
            inhabitants[x][y]=null;
            x=x0;
            y=y0;
            i++;
        }

    }

    public void drawArea(Graphics g) {

        for(int i=0;i<SIZE;i++) {

            for(int j=0;j<SIZE;j++) {

                if(inhabitants[i][j] != null) {
                    inhabitants[i][j].draw(g);
                }
            }
        }
    }
    public void printData() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(inhabitants[i][j] != null) {
                    System.out.print("(" + inhabitants[i][j] + ")");
                }
                else {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("Bears remaining: "+ numBears);
        System.out.println("Trees remaining: "+ numTrees);
        System.out.println("LumberJacks remaining "+ numJacks);
    }
}