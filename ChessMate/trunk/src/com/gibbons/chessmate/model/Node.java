package com.gibbons.chessmate.model;
/*
 * Node class is used to hold a tile with the value given by the minimax
 * evaluation 
 */
class Node
{
    Tile t;
    int value;

    Node(){}

    Node(int value)
    {
        this.value=value;
    }
    Node(int value,Tile t)
    {
        this.value= value;
        this.t=t;
    }
    void addTile(Tile t)
    {
        this.t=t;
    }
    void addValue(int value)
    {
        this.value=value;
    }
    int getValue()
    {
        return value;
    }
    Tile getTile()
    {
        return t;
    }
}