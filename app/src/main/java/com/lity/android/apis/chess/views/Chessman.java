package com.lity.android.apis.chess.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.lity.android.apis.R;

public abstract class Chessman {

    /**
     * ���ӵĿ��
     */
    protected final static int WIDTH = 30;
    
    /**
     * ���ӵĸ߶�
     */
    protected final static int HEIGHT = 30;
    
    /**
     * �췽player����������
     */
    public final static int MAN_TYPE_RED = 0x1;
    
    /**
     * �̷�player����������
     */
    public final static int MAN_TYPE_GREEN = 0x2;
    
    /**
     * ���ӵ�״̬,����
     */
    public final static int MAN_STATE_NORMAL = 0x1;
    
    /**
     * ���ӵ�״̬,ѡ��
     */
    public final static int MAN_STATE_SELECTED = 0x2;
    

    public final static int INDEX_RED_NORMAL = 0x0;
    public final static int INDEX_RED_SELECTED = 0x1;
    public final static int INDEX_GREEN_NORMAL = 0x2;
    public final static int INDEX_GREEN_SELECTED = 0x3;
    public final static int INDEX_STATE_COUNT = 0x4;
    
    public final static int CLASS_ID_NONE = 0x0;
    public final static int CLASS_ID_CAR = 0x1;
    public final static int CLASS_ID_HORSE = 0x2;
    public final static int CLASS_ID_ELEPHANT = 0x3;
    public final static int CLASS_ID_WHO = 0x4;
    public final static int CLASS_ID_GENERAL = 0x5;
    public final static int CLASS_ID_GUN = 0x6;
    public final static int CLASS_ID_SOLDIER = 0x7;
    public final static int CLASS_ID_COUNT = 0x8;
   
    
    /**
     * ��ǰ������
     */
    protected Context mContext;
    
    /**
     * ���̵�����
     */
    protected ChessBoard mParentBoard;
    
    /**
     * �����������ϵ�λ��
     */
    protected ChessPoint mPoint = new ChessPoint();
    
    /**
     * ��������player������
     */
    private int mPlayerType;
    
    /**
     * �����õ�������ͼƬ
     */
    
    private int mStateType = MAN_STATE_NORMAL;
    
    /**
     * ��������ı�ʶ,����:����1,����2
     * ��ʱ�Ȳ�ʹ��,Ϊ�Ժ���չ
     */
    @Deprecated
    protected final int mClassId;
    
    /**
     * ͼƬ������������
     */
    protected ImageManager mImageManager;
    
    /**
     * ����һ������,ʹ������
     * @param board
     */
    public Chessman(Context context, ChessBoard board){
        mContext = context;
        mParentBoard = board;
        
        ImageManager.init(context);
        mImageManager = ImageManager.instance();
        mClassId = CLASS_ID_NONE;
    }
    
    /**
     * �ߵ�ĳ����(��������ͳ���)
     * @param toPoint
     * @return true �ػ�
     */
    public final boolean arrive(Chessman.ChessPoint toPoint) {
        boolean result = false;
        if (canArriveTo(toPoint)) {
            // �˵㴦������
            if (mParentBoard.haveChessman(toPoint)) {
                result = onCanKill(toPoint);
                if (result) {
                    mParentBoard.onKill(mPoint, toPoint);       
                }
            }else {
                result = true;
                mParentBoard.onArrive(mPoint, toPoint);
            }
        }
        return result;
    }
    
    /**
     * �Ƿ����ɱ��point��������
     * @param point
     * @return ����ɱ��,true.
     */
    protected boolean onCanKill(ChessPoint point) {
        // ֻҪ�����Ӳ�����ͬһ����,����ɱ��
        final Chessman child = mParentBoard.getChild(point);
        if (null == child) {
            throw new NullPointerException();
        }
        if (child != this && mPlayerType != child.mPlayerType) {
            return true;
        }
        return false;
    }
    
    /**
     * subclass must override this method
     * �Ƿ�����ߵ�ĳ����(ֻ���Ϲ������,���ÿ��Ǻ���������)
     * @return ���ߵ� true.
     */
    protected abstract boolean canArriveTo(ChessPoint toPoint);
    
    
    /**
     * �����������
     * @param canvas
     */
    public void draw(Canvas canvas) {
        int index = -1;
        if (MAN_TYPE_RED == mPlayerType) {
            if (MAN_STATE_NORMAL == mStateType) {
                index = INDEX_RED_NORMAL;
            }
            else if (MAN_STATE_SELECTED == mStateType) {
                index = INDEX_RED_SELECTED;
            }
        }
        else if (MAN_TYPE_GREEN == mPlayerType) {
            if (MAN_STATE_NORMAL == mStateType) {
                index = INDEX_GREEN_NORMAL;
            }
            else if (MAN_STATE_SELECTED == mPlayerType) {
                index = INDEX_GREEN_SELECTED;
            }
        }
        if (-1 == index) {
             throw new java.lang.IllegalStateException();
        }
//        canvas.drawBitmap(mBitmaps[index], 0, 0, null);
        final Bitmap drawbBitmap = getDrawBitmap();
        float ratiox = (float)WIDTH / drawbBitmap.getWidth();
        float ratioy = (float)HEIGHT / drawbBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(ratiox, ratioy);
        canvas.drawBitmap(drawbBitmap, matrix, null);
    }
    
    /**
     * �����ϵĵ�
     * @author Lity
     *
     */
    public static class ChessPoint extends Object {
        public int x;
        public int y;
        
        public ChessPoint() {
            x = 0;
            y = 0;
        }
        /**
         * @param x
         * @param y
         */
        public ChessPoint(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }
        
        public ChessPoint copy() {
            return new ChessPoint(x, y);
        }
        
    }
    
    public ChessPoint getPoint() {
        return mPoint;
    }
    
    public void setBelongPlayer(int playType) {
        if (MAN_TYPE_RED != playType && MAN_TYPE_GREEN != playType) {
            throw new IllegalArgumentException();
        }
        mPlayerType = playType;
    }
    public int getBelongPlayer() {
        return mPlayerType;
    }
    
    /**
     * set state 
     * package method
     * @param state
     */
    void setState(int state){
        if (MAN_STATE_NORMAL != state && MAN_STATE_SELECTED != state) {
            throw new IllegalStateException();
        }
        mStateType = state;
    }
    
    /**
     * �õ���������ӵ�ͼƬ
     * @return
     */
    private Bitmap getDrawBitmap() {
        int stateIndex = -1;
        if (MAN_TYPE_RED == mPlayerType) {
            if (MAN_STATE_NORMAL == mStateType) {
                stateIndex = INDEX_RED_NORMAL;
            }
            else if (MAN_STATE_SELECTED == mStateType) {
                stateIndex = INDEX_RED_SELECTED;
            }
        }
        else if (MAN_TYPE_GREEN == mPlayerType) {
            if (MAN_STATE_NORMAL == mStateType) {
                stateIndex = INDEX_GREEN_NORMAL;
            }
            else if (MAN_STATE_SELECTED == mStateType) {
                stateIndex = INDEX_GREEN_SELECTED;
            }
        }
        if (-1 == stateIndex) {
            throw new IllegalStateException();
        }
        
        final int classId = onObtainClassId();
        return mImageManager.getImage(classId, stateIndex);
    }
    
    /**
     * ����Ψһ��ʶ���������͵�id
     * subclass should override this method.
     * @return
     */
    protected int onObtainClassId() {
        return CLASS_ID_NONE;
    }

}
final class ImageManager extends Object {
    
    private Context mContext;
    
    private static ImageManager mInstance;
    
    /**
     * ����ͼƬ
     */
    private Bitmap[][] mBitmaps = new Bitmap[Chessman.CLASS_ID_COUNT][Chessman.INDEX_STATE_COUNT];
                                        
    
    /**
     * 
     */
    private ImageManager(Context context) {
        super();
        mContext = context;
    }
    
    /**
     * ��ʼ��ͼƬ������,�˷������ٱ�����һ��
     * @param context
     */
    public static void init(Context context) {
        if (null == mInstance) {
            if (null == context) {
                throw new NullPointerException();
            }
            mInstance = new ImageManager(context);
            mInstance.init();
        }
    }
    
    /**
     * �ڲ���ʼ������
     */
    private void init() {
        final Resources res = mContext.getResources();
        
        // ��ͼƬ
        mBitmaps[Chessman.CLASS_ID_CAR][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.car_red_normal);
        mBitmaps[Chessman.CLASS_ID_CAR][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.car_red_selected);
        mBitmaps[Chessman.CLASS_ID_CAR][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.car_green_normal);
        mBitmaps[Chessman.CLASS_ID_CAR][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.car_green_selected);

        // ��ͼƬ
        mBitmaps[Chessman.CLASS_ID_HORSE][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.horse_red_normal);
        mBitmaps[Chessman.CLASS_ID_HORSE][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.horse_red_selected);
        mBitmaps[Chessman.CLASS_ID_HORSE][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.horse_green_normal);
        mBitmaps[Chessman.CLASS_ID_HORSE][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.horse_green_selected);
        
        // ��ͼƬ
        mBitmaps[Chessman.CLASS_ID_ELEPHANT][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.elephant_red_normal);
        mBitmaps[Chessman.CLASS_ID_ELEPHANT][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.elephant_red_selected);
        mBitmaps[Chessman.CLASS_ID_ELEPHANT][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.elephant_green_normal);
        mBitmaps[Chessman.CLASS_ID_ELEPHANT][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.elephant_green_selected);
        
        // ʿͼƬ
        mBitmaps[Chessman.CLASS_ID_WHO][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.who_red_normal);
        mBitmaps[Chessman.CLASS_ID_WHO][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.who_red_selected);
        mBitmaps[Chessman.CLASS_ID_WHO][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.who_green_normal);
        mBitmaps[Chessman.CLASS_ID_WHO][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.who_green_selected);
        
        // ��ͼƬ
        mBitmaps[Chessman.CLASS_ID_GENERAL][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.general_red_normal);
        mBitmaps[Chessman.CLASS_ID_GENERAL][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.general_red_selected);
        mBitmaps[Chessman.CLASS_ID_GENERAL][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.general_green_normal);
        mBitmaps[Chessman.CLASS_ID_GENERAL][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.general_green_selected);
    
        // ��ͼƬ
        mBitmaps[Chessman.CLASS_ID_GUN][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.gun_red_normal);
        mBitmaps[Chessman.CLASS_ID_GUN][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.gun_red_selected);
        mBitmaps[Chessman.CLASS_ID_GUN][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.gun_green_normal);
        mBitmaps[Chessman.CLASS_ID_GUN][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.gun_green_selected);
        
        // ��ͼƬ
        mBitmaps[Chessman.CLASS_ID_SOLDIER][Chessman.INDEX_RED_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.soldier_red_normal);
        mBitmaps[Chessman.CLASS_ID_SOLDIER][Chessman.INDEX_RED_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.soldier_red_selected);
        mBitmaps[Chessman.CLASS_ID_SOLDIER][Chessman.INDEX_GREEN_NORMAL] = BitmapFactory.decodeResource(res, R.drawable.soldier_green_normal);
        mBitmaps[Chessman.CLASS_ID_SOLDIER][Chessman.INDEX_GREEN_SELECTED] = BitmapFactory.decodeResource(res, R.drawable.soldier_green_selected);
        
    }

    /**
     * �õ�ͼƬ����ʵ��
     * @return
     */
    public static ImageManager instance() {
        if (null == mInstance) {
            throw new IllegalStateException("ImageManager has not initialized");
        }
        return mInstance;
    }
    
    /**
     * �õ�ָ���������ͺ�����״̬��ͼƬ
     * @param classId
     * @param stateId
     * @return
     */
    protected Bitmap getImage(int classId, int stateId) {
        if (stateId < 0 || stateId > 3) {
            throw new IllegalArgumentException();
        }
        return mBitmaps[classId][stateId];
    }
    
    /**
     * �õ�ָ���������ͺ�����״̬��ͼƬ
     * @param classId
     * @param playerId
     * @param stateId
     * @return
     */
    protected Bitmap getImage(int classId, int playerId, int stateId) {
        int index = -1;
        if (Chessman.MAN_TYPE_RED == playerId) {
            if (Chessman.MAN_STATE_NORMAL == stateId) {
                index = Chessman.INDEX_RED_NORMAL;
            }
            else if (Chessman.MAN_STATE_SELECTED == stateId) {
                index = Chessman.INDEX_RED_SELECTED;
            }
        }
        else if (Chessman.MAN_TYPE_GREEN == playerId) {
            if (Chessman.MAN_STATE_NORMAL == stateId) {
                index = Chessman.INDEX_GREEN_NORMAL;
            }
            else if (Chessman.MAN_STATE_SELECTED == stateId) {
                index = Chessman.INDEX_GREEN_SELECTED;
            }
        }
        if (-1 == index) {
             throw new java.lang.IllegalStateException();
        }
        return getImage(classId, index);
    }

    @Override
    protected void finalize() throws Throwable {
        for (int i = 0; i < Chessman.CLASS_ID_COUNT; i++) {
            for (int j = 0; j < Chessman.INDEX_STATE_COUNT; j++) {
                Bitmap bitmap = mBitmaps[i][j];
                if (null != bitmap) {
                    bitmap.recycle();
                    mBitmaps[i][j] = null;
                }
            }
        }
        super.finalize();
    }
    
    
}
