package com.example.wu.gobang;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wu.gobang.game.CompterAI;
import com.example.wu.gobang.game.Coordinate;
import com.example.wu.gobang.game.Game;
import com.example.wu.gobang.game.GameView;
import com.example.wu.gobang.player.Player;
import com.inaka.galgo.Galgo;
import com.inaka.galgo.GalgoOptions;

public class SingleGameActivity extends AppCompatActivity {

    public static final String TAG = "SingleGameActivity";
    private GameView mGameView;
    private Game mGame;
    private Player mLocal;
    private Player mChanllenger;

    private CompterAI mComputerAI;
    private Handler mRefreshHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case Game.MESSAGE_ADD_CHESS:
                    Log.d(TAG, "handleMessage MESSAGE_ADD_CHESS");
                    Coordinate bestcoor = mComputerAI.getBestCoor();
                    mGame.addChess(bestcoor);
                    mGameView.refreshGame();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_game_layout);
        initViews();
        initGame();
        mGameView.setGame(mGame);
        initGalgo();
    }

    @Override
    protected void onDestroy() {
        uninitGalgo();
        super.onDestroy();
    }

    private void initGalgo()
    {
        GalgoOptions options = new GalgoOptions.Builder()
                .numberOfLines(9)
                .backgroundColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(15)
                .build();
        Galgo.enable(this, options);
    }

    private void uninitGalgo()
    {
        Galgo.disable(this);
    }

    private void initViews() {
       mGameView = (GameView) findViewById(R.id.game_view);
    }
    private void initGame()
    {
        mLocal = new Player(" ", Game.BLACK);
        mChanllenger = new Player(" ", Game.WHITE);
        mGame = new Game(mRefreshHandler, mLocal, mChanllenger);
        mComputerAI = new CompterAI(mGame);
    }



}
