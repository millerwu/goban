package com.example.wu.gobang.game;

import android.util.Log;

import com.inaka.galgo.Galgo;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by intel on 2016/4/5.
 */
public class Game {

    private final static String TAG = "Game";
    public static final int SCALE_SMALL = 11;
    public static final int SCALE_MEDIUM = 15;
    public static final int SCALE_LARGE = 19;

    public static int SIZE = SCALE_MEDIUM;

    int mWidth = 0;
    int mHeight = 0;

    int mActive = 1;

    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public static final int SPACE = 0;
    private Deque<Coordinate> mActions;

    int mGameMap[][];

    public Game()
    {
        this(SIZE, SIZE);
    }

    public Game(int width, int height)
    {
        mWidth = width;
        mHeight = height;
        mGameMap = new int[mWidth][mHeight];
        mActions = new LinkedList<Coordinate>();
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }
    public int[][] getGameMap()
    {
        return mGameMap;
    }

    public boolean addChess(Coordinate coor)
    {
        if (coor.x < 0 || coor.x >= SCALE_MEDIUM || coor.y < 0 || coor.y >= SCALE_MEDIUM)
        {
            return false;
        }
        if (mGameMap[coor.x][coor.y] == 0)
        {
            if (mActive == BLACK) {
                mGameMap[coor.x][coor.y] = BLACK;
            } else {
                mGameMap[coor.x][coor.y] = WHITE;
            }
            changeActive();
            mActions.addLast(new Coordinate(coor));
            Log.d(TAG, "Action add x = " + coor.x + " y = " + coor.y);
            return true;
        }
        return false;
    }

    private void changeActive()
    {
        if (mActive == BLACK)
        {
            mActive = WHITE;
        } else {
            mActive = BLACK;
        }
    }

    public Deque<Coordinate> getActions()
    {
        return mActions;
    }

    public boolean gameEnd()
    {
        int live4 = GameJudgeLogic.getLive4(mGameMap, mActions.getLast());
        if (live4 > 0)
        {
            Galgo.log("********* live four =  " + live4 + " **********");
        }
        int deid4 = GameJudgeLogic.getDied4(mGameMap, mActions.getLast());
        if (deid4 > 0)
        {
            Galgo.log("********* died four =  " + deid4 + " **********");
        }
        int live3 = GameJudgeLogic.getLive3(mGameMap, mActions.getLast());
        if (live3 > 0)
        {
            Galgo.log("********* live three =  " + live3 + " **********");
        }
        int died3 = GameJudgeLogic.getDied3(mGameMap, mActions.getLast());
        if (died3 > 0)
        {
            Galgo.log("********* died three =  " + died3 + " **********");
        }
        int live2 = GameJudgeLogic.getLive2(mGameMap, mActions.getLast());
        if (live2 > 0)
        {
            Galgo.log("********* live two =  " + live2 + " **********");
        }
        int died2 = GameJudgeLogic.getDied2(mGameMap, mActions.getLast());
        if (died2 > 0)
        {
            Galgo.log("********* died two =  " + died2 + " **********");
        }
        return GameJudgeLogic.isGameEnd(mGameMap, mActions.getLast());
    }



}
