package com.example.wu.gobang.player;

import com.example.wu.gobang.game.Coordinate;

/**
 * Created by intel on 2016/4/22.
 */
public interface Player {
    public void setRole(int role);
    public int getRole();

    public int addChess(Coordinate coor);


}
