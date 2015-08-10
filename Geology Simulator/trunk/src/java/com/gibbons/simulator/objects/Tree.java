package com.gibbons.simulator.objects;

import java.awt.*;

import static com.gibbons.simulator.SimulatorConstants.HEIGHT;
import static com.gibbons.simulator.SimulatorConstants.WIDTH;
import static com.gibbons.simulator.SimulatorConstants.debug;
import static com.gibbons.simulator.objects.InhabitantType.TREE;

public class Tree extends Inhabitant
{
    int x,y;
    int age = 12;
    private Color c = Color.GREEN;
    private Color s = Color.BLUE;
    private Jack occupiedJack;
    private Bear occupied;

    Tree(int x,int y)
    {
        this.type = TREE;
        this.x=x;
        this.y=y;
    }

    Tree(Point p) {
        this.type = TREE;
        x = p.x;
        y = p.y;
    }

    @Override
    public void draw(Graphics g)
    {
        if(age < 12) {
            g.setColor(s);

            if(debug) {
                g.drawString("S", x*WIDTH, y*HEIGHT);
            }
        }
        else {
            g.setColor(c);

            if(debug) {
                g.drawString("T", x*WIDTH, y*HEIGHT);
            }
        }

        if(!debug) {
            g.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        }
    }

    public boolean sapling() {
        return age < 12;
    }

    public void age() {
        age++;
    }

    public static Tree makeSapling(int x, int y) {
        Tree t = new Tree(x,y);
        t.age = 0;
        return t;
    }

    @Override
    public String toString() {
        return "";
    }
}