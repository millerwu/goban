package com.example.wu.gobang;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wu.gobang.game.Game;
import com.example.wu.gobang.game.GameView;
import com.inaka.galgo.Galgo;
import com.inaka.galgo.GalgoOptions;

public class SingleGameActivity extends AppCompatActivity {

    private GameView mGameView;
    private Game mGame;

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
        mGame = new Game();
    }
}
