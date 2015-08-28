package com.gibbons.simulator.objects;

import java.awt.*;

import static com.gibbons.simulator.SimulatorConstants.HEIGHT;
import static com.gibbons.simulator.SimulatorConstants.WIDTH;
import static com.gibbons.simulator.SimulatorConstants.debug;
import static com.gibbons.simulator.objects.InhabitantType.JACK;

public class Jack extends Inhabitant {
    private Color c= Color.RED;
    private Tree t;
    boolean hasTree = false;

    Jack(int x,int y) {
        this.type = JACK;
        this.x = x;
        this.y = y;
    }
    Jack(Point p) {
        this.type = JACK;
        x = p.x;
        y = p.y;
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(c);

        if(debug) {
            g.drawString("J", x * WIDTH, y * HEIGHT);
        }

        else {
            g.fillRect(x * WIDTH, y * HEIGHT, WIDTH, HEIGHT);
        }
    }

    @Override
    public String toString() {
        return "J";
    }
}
