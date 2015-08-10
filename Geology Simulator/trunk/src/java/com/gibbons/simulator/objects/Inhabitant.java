package com.gibbons.simulator.objects;

import java.awt.*;

/**
 * Created by sgib0001 on 09/08/15.
 */
public abstract class Inhabitant {

    int x,y;
    InhabitantType type;


    public InhabitantType getType() {
        return type;
    }

    public abstract void draw(Graphics g);
}
