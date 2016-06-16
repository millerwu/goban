package com.example.wu.gobang.game;

/**
 * Created by intel on 2016/4/22.
 */
public class CompterAI {

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
                    bestSorce = GameJudgeLogic.getScore(mGame.getGameMap(), new Coordinate(x, y));
                    bestCoor = new Coordinate(x, y);
                }
            }
        }

        return bestCoor;
    }
}
