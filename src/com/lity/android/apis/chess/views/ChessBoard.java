package com.lity.android.apis.chess.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;

import com.lity.android.apis.App;
import com.lity.android.apis.R;
import com.lity.android.apis.chess.views.Chessman.ChessPoint;

public class ChessBoard extends Object {

    /**
     * ���̵Ŀ��
     */
    public final static int BOARD_WIDTH = 280;
    
    /**
     * ���̵ĸ߶�
     */
    public final static int BOARD_HEIGHT = 309;
    
    /**
     * ���̵�����
     */
    public final static int ROW_COUNT = 10;
    
    /**
     * ���̵�����
     */
    public final static int COLUMN_COUNT = 9;
    
    /**
     * ���̸��ӵĿ��
     */
    public final static int GRID_WIDHT = 33;
    
    /**
     * ���̸��ӵĸ߶�
     */
    public final static int GRID_HEIGHT = 33;
    
    /**
     * ���̱�����㵽(0,0)��x�����ϵ�ƫ����
     */
    public final static int OFFSET_X = 9;
    
    /**
     * ���̱�����㵽(0,0)��y�����ϵ�ƫ����
     */
    public final static int OFFSET_Y = 9;
    
    public final static int MASK_PLACED = 0x00000001;
    public final static int MASK_X = 0x0000FFFF;
    public final static int MASK_Y = 0xFFFF0000;
    
    /**
     * ��Ϸ��״̬,�¾�
     */
    public final static int GAME_STATE_NEW = 1;
    
    /**
     * ��Ϸ��״̬,�ѿ�ʼ
     */
    public final static int GAME_STATE_START = 2;
    
    /**
     * ��Ϸ��״̬,����
     */
    public final static int GAME_STATE_KILL = 3;
    
    /**
     * ��Ϸ��״̬,����
     */
    public final static int GAME_STATE_OVER = 4;
    
    /**
     * ��Ϸ��״̬,��ʱ����
     */
    @Deprecated
    protected int mGameState;
    
    /**
     * ��ǰѡ�е�����
     */
    protected Chessman mCurrentChessman;
    
    /**
     * ��ǰ���ֵ�id
     */
    private int mCurrentPlayer;
    
    
    private Context mContext;
    private Bitmap mBg;
    
    /**
     * ӳ���
     */
    private int mMapDot[][] = new int[COLUMN_COUNT][ROW_COUNT];
    private Chessman mChessman[][] = new Chessman[COLUMN_COUNT][ROW_COUNT];
    
    private int mStartX;
    private int mStartY;
    
    private boolean mKillRedGeneral = false;
    private boolean mKillGreenGeneral = false;
    
    public ChessBoard (Context context) {
        if (null == context) {
            throw new NullPointerException();
        }
        mContext = context;
        
        mBg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chess_board_bg);
        
        // TODO ����ӳ���
        final int left = OFFSET_X;
        final int top = OFFSET_Y;
        for (int i = 0; i < COLUMN_COUNT; i++) {
            for (int j = 0; j < ROW_COUNT; j++) {
                int x = i * GRID_WIDHT + left;
                int y = j * GRID_HEIGHT + top;
                if (App.DEBUG) {
                    Log.v(App.TAG, "MapDot i:" + i + ", j:" + j + ", x:" + x + ", y:" + y);
                }
                y <<= 16;
                mMapDot[i][j] = x | y;
            }
        }
    }
    
    public void createNew(){
        // ��
        Chessman chessman = new Car(mContext, this);
        chessman.getPoint().x = 0;
        chessman.getPoint().y = 0;
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        addChild(chessman);
        
        chessman = new Car(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 8;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new Car(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 0;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        chessman = new Car(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 8;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        // ��
        chessman = new Horse(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 1;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new Horse(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 7;
        chessman.getPoint().y = 0;
        addChild(chessman);
       
        chessman = new Horse(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 1;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        chessman = new Horse(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 7;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        // ��
        chessman = new Elephant(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 2;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new Elephant(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 6;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new Elephant(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 2;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        chessman = new Elephant(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 6;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        // ʿ
        chessman = new Who(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 3;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new Who(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 5;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new Who(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 3;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        chessman = new Who(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 5;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        // ˧
        chessman = new General(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 4;
        chessman.getPoint().y = 0;
        addChild(chessman);
        
        chessman = new General(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 4;
        chessman.getPoint().y = 9;
        addChild(chessman);
        
        // ��
        chessman = new Gun(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 1;
        chessman.getPoint().y = 2;
        addChild(chessman);
        
        chessman = new Gun(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
        chessman.getPoint().x = 7;
        chessman.getPoint().y = 2;
        addChild(chessman);
        
        chessman = new Gun(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 1;
        chessman.getPoint().y = 7;
        addChild(chessman);
        
        chessman = new Gun(mContext, this);
        chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
        chessman.getPoint().x = 7;
        chessman.getPoint().y = 7;
        addChild(chessman);
        
        // ��
        for (int i = 0; i < COLUMN_COUNT; i += 2) {
            chessman = new Soldier(mContext, this);
            chessman.setBelongPlayer(Chessman.MAN_TYPE_GREEN);
            chessman.getPoint().x = i;
            chessman.getPoint().y = 3;
            addChild(chessman);
        }
        
        for (int i = 0; i < COLUMN_COUNT; i += 2) {
            chessman = new Soldier(mContext, this);
            chessman.setBelongPlayer(Chessman.MAN_TYPE_RED);
            chessman.getPoint().x = i;
            chessman.getPoint().y = 6;
            addChild(chessman);
        }
        
        mGameState = GAME_STATE_NEW;
        
        mCurrentChessman = null;
        mCurrentPlayer = Chessman.MAN_TYPE_RED;
        
    }
    
    public final void draw(Canvas canvas) {
        
        // ��ˢ��
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        canvas.drawRect(0, 0, 320, 450, paint);
        
        // TODO ��������
//        canvas.translate(mStartX, mStartY);
//        canvas.drawBitmap(mBg, 0, 0, null);
        
        // TODO ������������
        for (int i = 0; i < COLUMN_COUNT; i++) {
            for (int j = 0; j < ROW_COUNT; j++){
                Chessman child = mChessman[i][j];
                if (null != child) {
                    drawChild(canvas, child);
                }
            }
        }
        
        // ����Ҫ������
        int x = (BOARD_WIDTH) / 2 - (Chessman.WIDTH) / 2 + 40;
        int y = BOARD_HEIGHT + 20;
        ImageManager.init(mContext);
        Bitmap bitmap = ImageManager.instance().getImage(Chessman.CLASS_ID_GENERAL, mCurrentPlayer, Chessman.MAN_STATE_NORMAL);
        Matrix matrix = new Matrix();
        matrix.postScale((float)Chessman.WIDTH / bitmap.getWidth(), (float)Chessman.HEIGHT / bitmap.getHeight());
        matrix.postTranslate(x, y);
        canvas.drawBitmap(bitmap, matrix, null);
        
        // ���ƽ���
        if (mKillRedGeneral) {
            x = mStartX;
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.general_red_killed);
            canvas.drawBitmap(bitmap, x, y, null);
            mKillRedGeneral = false;
        }
        if (mKillGreenGeneral) {
            x = mStartX;
            y = mStartY;
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.general_green_killed);
            canvas.drawBitmap(bitmap, x, y, null);
            mKillGreenGeneral = false;
        }
        
    }
    
    void drawChild(Canvas canvas, Chessman child) {
        int i = child.getPoint().x;
        int j = child.getPoint().y;
        int x = mMapDot[i][j] & MASK_X;
        int y = mMapDot[i][j] & MASK_Y;
        y >>= 16;
        if (App.DEBUG) {
            Log.v(App.TAG, "drawing i:" + i + ", j:" + j + ", x:" + x + ", y:" + y);
        }
        int count = canvas.save();
        canvas.translate(x - Chessman.WIDTH / 2, y - Chessman.HEIGHT / 2);
        child.draw(canvas);
        canvas.restoreToCount(count);
    }
    
    /**
     * �Ƿ�˴�������
     * @param x
     * @param y
     * @return true ����˴�������
     */
    public boolean haveChessman(Chessman.ChessPoint point){       
        return null != mChessman[point.x][point.y];
    }
    
    /**
     * �Ƿ�˴�������
     * @param x
     * @param y
     * @return true ����˴�������
     */
    public boolean haveChessman(int x, int y) {
        return null != mChessman[x][y];
    }
    
    public void addChild(Chessman child) {
        if (null == child) {
            throw new NullPointerException();
        }
        final ChessPoint point = child.getPoint();
        mChessman[point.x][point.y] = child; 
    }
    
    /**
     * 
     * @param child
     * @param point
     */
    public void addChild(Chessman child, Chessman.ChessPoint point){
        if (null == child) {
            throw new NullPointerException();
        }
        mChessman[point.x][point.y] = child;
    }
    
    Chessman getChild(int x, int y) {
        return mChessman[x][y];
    }
    
    Chessman getChild(ChessPoint point) {
        return mChessman[point.x][point.y];
    }
    
    public void setStartPoint(int x, int y) {
        mStartX = x;
        mStartY = y;
    }
    
    /**
     * �����¼�
     * @param event
     * @return �Ƿ��ػ�
     */
    public boolean dispatchEvent(MotionEvent event) {
        
        // TODO ����ֻ����action_down �¼�
        if (MotionEvent.ACTION_DOWN != event.getAction()) {
            return false;
        }
        int x = (int)event.getX() - mStartX;
        int y = (int)event.getY() - mStartY;
        
        int i = (int)((float)(x - OFFSET_X) / GRID_WIDHT + 0.5);
        int j = (int)((float)(y - OFFSET_Y) / GRID_HEIGHT + 0.5);
        
        if (App.DEBUG) {
            Log.v(App.TAG, "dispatch touch event, x:" + event.getX() + ", y:" + event.getY());
            Log.v(App.TAG, "dispatch touch event, i:" + i + ", j:" + j);
        }
        
        if (0 <= i && i < COLUMN_COUNT && 0 <= j && j < ROW_COUNT) {
            Chessman child = mChessman[i][j];
   
            // ��ʱ��ѡ�е�����
            if (null != mCurrentChessman) {
                if (mCurrentChessman.getBelongPlayer() == mCurrentPlayer && mCurrentChessman.arrive(new ChessPoint(i, j))) {
                    return true;
                }else {
                    if (null != child && child.getBelongPlayer() == mCurrentPlayer) {
                        mCurrentChessman.setState(Chessman.MAN_STATE_NORMAL);
                        child.setState(Chessman.MAN_STATE_SELECTED);
                        mCurrentChessman = child;
                        return true;
                    }
                }
            }
            else {
                if (null != child) {
                    child.setState(Chessman.MAN_STATE_SELECTED);
                    mCurrentChessman = child;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * ɱ��һ������<br/>
     * �˷�����Chessman����
     * @param killer ɱ������
     * @param victim ��ɱ����
     */
    void onKill(ChessPoint killer, ChessPoint victim) {
        if (!haveChessman(killer) || !haveChessman(victim)) {
            throw new IllegalArgumentException();
        }
        
        // TODO ��¼����ʷ��,Ϊ�Ժ�ʵ�ֻ���
        
        step(killer, victim);
    }
    
    /**
     * �����ߵ�ĳ����
     * @param manPoint Ҫ�ߵ����ӵĵ�
     * @param toPoint �ߵ��µĵ�
     */
    void onArrive(ChessPoint manPoint, ChessPoint toPoint) {
        if (!haveChessman(manPoint) || haveChessman(toPoint)) {
            throw new IllegalArgumentException();
        }
        // TODO ��¼����ʷ��,Ϊ�Ժ�ʵ�ֻ���
        step(manPoint, toPoint);
    }
    
    // srcPoint���������ߵ�toPoint��
    private void step(ChessPoint srcPoint, ChessPoint toPoint) {
        
        final Chessman[][] board = mChessman;
        final Chessman child = mChessman[srcPoint.x][srcPoint.y];
        mChessman[srcPoint.x][srcPoint.y] = null;
        
        child.getPoint().x = toPoint.x;
        child.getPoint().y = toPoint.y;

        board[toPoint.x][toPoint.y] = child;
        
        // ��������
        if (Chessman.MAN_TYPE_RED == mCurrentPlayer) {
            mCurrentPlayer = Chessman.MAN_TYPE_GREEN;
        }
        else if (Chessman.MAN_TYPE_GREEN == mCurrentPlayer) {
            mCurrentPlayer = Chessman.MAN_TYPE_RED;
        }
        else {
            throw new IllegalStateException();
        }
        
        killGeneral(srcPoint, toPoint);
        
    }
    
    // �����ж�
    // srcPoint��ʾ����ߵ��ǲ������ʼ��,toPointΪ�յ�
    private boolean killGeneral(ChessPoint srcPoint, ChessPoint toPoint) {
        
        // ���һ��,�������ƽ���ߡ�������ֱ��,�ж������������Ƿ��������ܽ���
        ChessPoint redGeneralPoint = null, greenGeneralPoint = null;
        boolean killRedGeneral = false, killGreenGeneral = false;
        final Chessman lastActionChild = getChild(toPoint);
        if (null == lastActionChild) {
            throw new IllegalArgumentException();
        }
        
        // ���콫
        for (int i = 3; i <= 5; i++) {
            for (int j = 7; j <= 9; j++) {
                Chessman child = getChild(i, j);
                if (null != child && child instanceof General && Chessman.CLASS_ID_GENERAL == child.onObtainClassId()) {
                    redGeneralPoint = child.getPoint().copy();
                }
            }
        }
        
        for (int i = 0; i < COLUMN_COUNT && !killRedGeneral; i++) {
            Chessman child = getChild(i, srcPoint.y);
            if (null != child && child.onCanKill(redGeneralPoint) && child.canArriveTo(redGeneralPoint)) {
                killRedGeneral = true;
                break;
            }
            if (srcPoint.y != toPoint.y) {
                child = getChild(i, toPoint.y);
                if (null != child && child.onCanKill(redGeneralPoint) && child.canArriveTo(redGeneralPoint)) {
                    killRedGeneral = true;
                    break;
                }
            }
        }
        for (int j = 0; j < ROW_COUNT && !killRedGeneral; j++) {
            Chessman child = getChild(srcPoint.x, j);
            if (null != child && child.onCanKill(redGeneralPoint) && child.canArriveTo(redGeneralPoint)) {
                killRedGeneral = true;
                break;
            }
            if (srcPoint.x != toPoint.x) {
                child = getChild(toPoint.x, j);
                if (null != child && child.onCanKill(redGeneralPoint) && child.canArriveTo(redGeneralPoint)) {
                    killRedGeneral = true;
                    break;
                }  
            }
        }
        
        // ���̽�
        for (int i = 3; i <= 5; i++) {
            for (int j = 0; j <= 2; j++) {
                Chessman child = getChild(i, j);
                if (null != child && child instanceof General && Chessman.CLASS_ID_GENERAL == child.onObtainClassId()) {
                    greenGeneralPoint = child.getPoint().copy();
                }
            }
        }

        for (int i = 0; i < COLUMN_COUNT && !killGreenGeneral; i++) {
            Chessman child = getChild(i, srcPoint.y);
            if (null != child && child.onCanKill(greenGeneralPoint) && child.canArriveTo(greenGeneralPoint)) {
                killGreenGeneral = true;
                break;
            }
            if (srcPoint.y != toPoint.y) {
                child = getChild(i, toPoint.y);
                if (null != child && child.onCanKill(greenGeneralPoint) && child.canArriveTo(greenGeneralPoint)) {
                    killGreenGeneral = true;
                    break;
                }
            }
        }
        for (int j = 0; j < ROW_COUNT && !killGreenGeneral; j++) {
            Chessman child = getChild(srcPoint.x, j);
            if (null != child && child.onCanKill(greenGeneralPoint) && child.canArriveTo(greenGeneralPoint)) {
                killGreenGeneral = true;
                break;
            }
            if (srcPoint.x != toPoint.x) {
                child = getChild(toPoint.x, j);
                if (null != child && child.onCanKill(greenGeneralPoint) && child.canArriveTo(greenGeneralPoint)) {
                    killGreenGeneral = true;
                    break;
                }  
            }
        }
        if (killRedGeneral) {
            mKillRedGeneral = true;
            if (App.DEBUG) {
                Log.v(App.TAG, "�̽��콫");
            }
        }
        if (killGreenGeneral) {
            mKillGreenGeneral = true;
            if (App.DEBUG) {
                Log.v(App.TAG, "�콫�̽�");
            }
        }
        
        return true;
    }
    
}
