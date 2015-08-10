package com.gibbons.simulator.objects;

import java.awt.*;

import static com.gibbons.simulator.SimulatorConstants.HEIGHT;
import static com.gibbons.simulator.SimulatorConstants.WIDTH;
import static com.gibbons.simulator.SimulatorConstants.debug;
import static com.gibbons.simulator.objects.InhabitantType.BEAR;

/**
 * Created by sgib0001 on 07/08/15.
 */
public class Bear extends Inhabitant {

    private Color c = Color.BLACK;

    Bear(int x,int y) {
        this.type = BEAR;
        this.x = x;
        this.y = y;
    }

    Bear(Point p) {
        this.type = BEAR;
        x = p.x;
        y = p.y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(c);


        if(debug) {
            g.drawString("B", x*WIDTH, y*HEIGHT);
        }

        else {
            g.fillRect(x* WIDTH,y* HEIGHT,WIDTH,HEIGHT);
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}
