package com.example.wu.gobang.game;

/**
 * Created by intel on 2016/4/8.
 */
public class Coordinate {

    public int x = 0;
    public int y = 0;

    Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    Coordinate(Coordinate coordinate)
    {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equal(Coordinate coord)
    {
        if (this.x == coord.x && this.y == coord.y)
        {
            return true;
        }
        return false;
    }
}
