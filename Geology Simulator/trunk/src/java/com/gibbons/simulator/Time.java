package com.gibbons.simulator;

import com.gibbons.simulator.objects.Forest;

/**
 * Created by sgib0001 on 07/08/15.
 */
public class Time
{
    int month;
    int year;

    public void tick(Forest forest)
    {
        month++;
        System.out.println(month);
        if(month%12<month)
        {
            month%=12;
            year++;
            forest.growTrees();
        }
        forest.move();
    }

}