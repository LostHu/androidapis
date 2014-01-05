package com.lity.android.apis.chess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;

import com.lity.android.apis.App;
import com.lity.android.apis.R;
import com.lity.android.apis.chess.views.ChessBoard;

public class ChessWindow extends Activity {
    
    public static final int HANDLER_REDRAW = 1;
    public static final int HANDLER_QUITE = 2;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private ChessBoard mBoard;
    
    public DrawHandler mDrawHandler;
    
    private Bitmap mBg;
    
    private Thread mDrawThread = new Thread() {

        @Override
        public void run() {
            super.run();
            
            // TODO 用handler 实现绘图
            Looper.prepare();
            mDrawHandler = new DrawHandler();
            if (App.DEBUG) {
                Log.v(App.TAG, "drawThread run method");
            }
            mDrawHandler.sendEmptyMessage(HANDLER_REDRAW);
            Looper.loop();
        }
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chess_window);
        mSurfaceView = (SurfaceView)findViewById(R.id.chess_chessborad_surfaceview);
        mSurfaceView.setFocusable(true);
        
        mSurfaceView.getHolder().addCallback(new BoardHolderCallBack());
        mSurfaceView.setOnTouchListener(new TouchEventSource());
              
        mBoard = new ChessBoard(this);
        mBoard.createNew();
        
        // 整个窗口的背景
        mBg = BitmapFactory.decodeResource(getResources(), R.drawable.window_bg);
    }
    
    private void startDraw(){
        if (App.DEBUG) {
            Log.v(App.TAG, "start drawing thread:" + Thread.currentThread());
        }
        
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawBitmap(mBg, 0, 0, null);
        
        mBoard.draw(canvas);
        mHolder.unlockCanvasAndPost(canvas);
    }

    
    class BoardHolderCallBack extends Object implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
            int x = (width - ChessBoard.BOARD_WIDTH) / 2;
            int y = (height - ChessBoard.BOARD_HEIGHT) / 2;
            if (App.DEBUG) {
                Log.v(App.TAG, "set board startPoint x:" + x + ", y:" + y);
            }
            mBoard.setStartPoint(x, y);
//            mBoard.setStartPoint(0, 0);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mHolder = holder;
            if (App.DEBUG) {
                Log.v(App.TAG, "surfaceCreated method, Thread:" + Thread.currentThread());
            }
            mDrawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mDrawHandler.sendEmptyMessage(HANDLER_QUITE);
        }
        
    }
    
    // touch event 的源头
    class TouchEventSource extends Object implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mBoard.dispatchEvent(event)) {
                mDrawHandler.sendEmptyMessage(HANDLER_REDRAW);
            }
            return true;
        }
        
    }
    
    // handler
    class DrawHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case HANDLER_REDRAW:
                startDraw();
                break;
            case HANDLER_QUITE:
                Looper.myLooper().quit();
            default:
                break;
            }
        }
        
    }
}
