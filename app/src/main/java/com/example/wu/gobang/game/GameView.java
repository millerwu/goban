package com.example.wu.gobang.game;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.wu.gobang.R;
import com.inaka.galgo.Galgo;

import java.util.HashMap;
import java.util.Queue;
import java.util.jar.Attributes;

/**
 * Created by intel on 2016/3/31.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    enum CHESS_TYPE{
        FORCE_TYPE,
        BLACK_TYPE,
        WHITE_TYPE,
        NEW_BLACK_TYPE,
        NEW_WHITE_TYPE,
    }

    private static final String TAG = "GameView";

    Context mContext;
    private int mColor;
    private float mBoardWidth;
    SurfaceHolder mSurfaceHolder;
    int mChessSize = 0;
    int mStrokeWidth= 0;
    int mChessBoardWidth = 0;
    int mChessBoardHeight = 0;
    Game mGame = null;
    private float mAnchorWidth = 0.0f;
    private boolean mDrawFocus = false;

    Bitmap mFocusBitmap = null;
    Bitmap mBlackBitmap = null;
    Bitmap mWhiteBitmap = null;
    Bitmap mNewBlackBitmap = null;
    Bitmap mNewWhiteBitmap = null;

    Coordinate mFocusCoord =  new Coordinate(0, 0);
    Coordinate mFocusCoord_old = new Coordinate(0, 0);

    private Paint mBoardPaint = new Paint();
    private Paint mChessPaint = new Paint();
    private Paint mClearPaint = new Paint();

    private boolean mGameEnd = false;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        mColor = Color.BLACK;
        setFocusable(true);
        mStrokeWidth = getResources().getDimensionPixelSize(R.dimen.stroke_width);
        mAnchorWidth = getResources().getDimensionPixelSize(R.dimen.anchor_width);
        mChessPaint.setAntiAlias(true);
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        init();
    }

    protected void init()
    {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);

        mBoardPaint.setStrokeWidth(mStrokeWidth);
        mBoardPaint.setColor(mColor);

    }
    public void setGame(Game game)
    {
        mGame = game;
    }

    private void drawChessBoard(){
        Canvas canvas = mSurfaceHolder.lockCanvas();

        if (canvas == null)
        {
            return;
        }
        drawChessBoard(canvas);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }
    private void drawChessBoard(Canvas canvas)
    {
        int startX = mChessSize/2;
        int startY = mChessSize/2;

        int endX = (mChessBoardWidth-1) * mChessSize + mChessSize/2;
        int endY = (mChessBoardHeight-1) * mChessSize + mChessSize/2;


        for (int i = 0; i < mChessBoardWidth; i++)
        {
            canvas.drawLine(startX, startY+(mChessSize*i) ,endX, startY+(mChessSize*i), mBoardPaint);
        }
        for (int i = 0; i < mChessBoardHeight; i++)
        {
            canvas.drawLine(startX+(mChessSize*i), startY, startX+(mChessSize*i), endY, mBoardPaint);
        }

        int centerX = mChessBoardWidth*mChessSize/2;
        int centerY = mChessBoardHeight*mChessSize/2;

        canvas.drawCircle(centerX, centerY, mAnchorWidth, mBoardPaint);
    }

    private Bitmap createBitmap(int width, int height, CHESS_TYPE type)
    {
        int roleSize = width/Game.SCALE_MEDIUM;
        Bitmap bitmap = Bitmap.createBitmap(roleSize, roleSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable d = null;
        if (type == CHESS_TYPE.FORCE_TYPE)
        {
            d = getResources().getDrawable(R.drawable.focus);
        } else if (type == CHESS_TYPE.BLACK_TYPE) {
            d = getResources().getDrawable(R.drawable.black);
        } else if (type == CHESS_TYPE.WHITE_TYPE) {
            d = getResources().getDrawable(R.drawable.white);
        } else if (type == CHESS_TYPE.NEW_BLACK_TYPE) {
            d = getResources().getDrawable(R.drawable.black_new);
        } else if (type == CHESS_TYPE.NEW_WHITE_TYPE) {
            d = getResources().getDrawable(R.drawable.white_new);
        }
        d.setBounds(0, 0, roleSize, roleSize);
        d.draw(canvas);
        return bitmap;
    }

    private void drawFocus(Canvas canvas)
    {
        if (mDrawFocus)
        {
            canvas.drawBitmap(mFocusBitmap, mFocusCoord.x*mChessSize, mFocusCoord.y*mChessSize, mChessPaint);
        }
    }

    private void drawChess(Canvas canvas)
    {
        int map[][] = mGame.getGameMap();
        for (int i = 0; i < mGame.getWidth(); i++)
        {
            for (int j = 0; j < mGame.getHeight(); j++)
            {
                if (map[i][j] == Game.BLACK)
                {
                    canvas.drawBitmap(mBlackBitmap, i*mChessSize, j*mChessSize, mChessPaint);
                } else if (map[i][j] == Game.WHITE)
                {
                    canvas.drawBitmap(mWhiteBitmap, i*mChessSize, j*mChessSize, mChessPaint);
                }
            }
        }
        Log.d(TAG, "getActions().size() = " + mGame.getActions().size());
        if (mGame.getActions().size() > 0) {
            Coordinate coor = mGame.getActions().getLast();
            Log.d(TAG, "coor.x = " + coor.x + " coor.y = " + coor.y);
            if (map[coor.x][coor.y] == Game.BLACK) {
                canvas.drawBitmap(mNewBlackBitmap, coor.x * mChessSize, coor.y * mChessSize, mChessPaint);
            } else if (map[coor.x][coor.y] == Game.WHITE) {
                canvas.drawBitmap(mNewWhiteBitmap, coor.x * mChessSize, coor.y * mChessSize, mChessPaint);
            }
        }
    }

    private void refreshGame()
    {
        Canvas canvas = mSurfaceHolder.lockCanvas();

        if (mSurfaceHolder == null || canvas == null)
        {
            Log.e(TAG, "refreshGame error");
            return;
        }

        canvas.drawPaint(mClearPaint);
        drawChessBoard(canvas);
        drawChess(canvas);
        drawFocus(canvas);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: format = " + format + " width = " + width + " height = " + height);
        drawChessBoard();
        mFocusBitmap = createBitmap(width, height, CHESS_TYPE.FORCE_TYPE);
        mBlackBitmap = createBitmap(width, height, CHESS_TYPE.BLACK_TYPE);
        mWhiteBitmap = createBitmap(width, height, CHESS_TYPE.WHITE_TYPE);
        mNewBlackBitmap = createBitmap(width, height, CHESS_TYPE.NEW_BLACK_TYPE);
        mNewWhiteBitmap = createBitmap(width, height, CHESS_TYPE.NEW_WHITE_TYPE);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: left = " + left + " top = " + top + " right = " + right + " bottom = " + bottom);
        if (mGame != null) {
            mChessBoardWidth = mGame.getWidth();
            mChessBoardHeight = mGame.getHeight();
            mChessSize = (right - left) / mChessBoardWidth;
            Log.d(TAG, "mChessSize = " + mChessSize + " mChessBoardWidth = " + mChessBoardWidth + " mChessBoardHeight = " + mChessBoardHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                mFocusCoord.x = (int) (x/mChessSize);
                mFocusCoord.y = (int) (y/mChessSize);
                Galgo.log("MotionEvent.ACTION_UP  x = " + mFocusCoord.x + " y = " + mFocusCoord.y);
                mDrawFocus = true;
                if (mGame != null)
                {
                    mGame.addChess(mFocusCoord);
                    if (mGame.gameEnd()){
                        Galgo.log("*********gameEnd **********");
                    }
                }
                refreshGame();
                break;
            case MotionEvent.ACTION_DOWN:
                mFocusCoord_old.x = mFocusCoord.x = (int) (x/mChessSize);
                mFocusCoord_old.y = mFocusCoord.y = (int) (y/mChessSize);
                mDrawFocus = true;
                refreshGame();
                break;
            case MotionEvent.ACTION_MOVE:
                mFocusCoord.x = (int) (x/mChessSize);
                mFocusCoord.y = (int) (y/mChessSize);
                if (!mFocusCoord_old.equal(mFocusCoord))
                {
                    mDrawFocus = true;
                    refreshGame();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: widthMeasureSpec = " + widthMeasureSpec + " heightMeasureSpec = " + heightMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        if(mGame != null){
            if (width % mGame.getWidth() == 0){
                float scale = ((float)mGame.getHeight()) / mGame.getWidth();
                int height = (int) (width*scale);
                setMeasuredDimension(width, height);
            } else {
                width = width / mGame.getWidth() * mGame.getWidth();
                float scale = ((float)mGame.getHeight()) / mGame.getWidth();
                int height = (int) (width*scale);
                setMeasuredDimension(width, height);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
