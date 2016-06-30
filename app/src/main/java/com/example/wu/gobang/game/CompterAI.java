package com.example.wu.gobang.game;

import android.util.Log;

/**
 * Created by intel on 2016/4/22.
 */
public class CompterAI {
    public static final String TAG = "CompterAI";

    Game mGame;

    public CompterAI(Game game)
    {
        mGame = game;
    }

    public Coordinate getBestCoor()
    {
        int x = 0; int y = 0;
        Coordinate bestCoor = new Coordinate(x, y);
        int bestSorce = 0;
        for (x = 0; x < Game.SIZE; x++)
        {
            for (y = 0; y < Game.SIZE; y++)
            {
                if (bestSorce < GameJudgeLogic.getScore(mGame.getGameMap(), new Coordinate(x, y)))
                {
                    Log.d(TAG, "bestSorce = " + bestSorce + " x = " + x + " y = " + y);
                    bestSorce = GameJudgeLogic.getScore(mGame.getGameMap(), new Coordinate(x, y));
                    bestCoor = new Coordinate(x, y);
                }
            }
        }

        return bestCoor;
    }
}
