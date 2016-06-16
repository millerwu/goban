package com.example.wu.gobang.player;

import com.example.wu.gobang.game.Coordinate;
import com.example.wu.gobang.game.Game;

/**
 * Created by intel on 2016/4/22.
 */
public class Player {

    int type;
    String name;
    public Player (int type)
    {
        if (type == Game.BLACK)
        {
            this.name = "Black";
        } else {
            this.name = "White";
        }
        this.type = type;
    }

    public Player (String name, int type){
        this.type = type;
        this.name = name;
    }

    public int getType()
    {
        return this.type;
    }

}
