package com.lity.android.apis.chess.views;

import android.content.Context;

public class Gun extends Chessman {

    public Gun(Context context, ChessBoard board) {
        super(context, board);
    }
   

    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        final ChessPoint srcPoint = mPoint;
        
        // 两上位置中间棋子的个数
        int count = 0;
        
        // 竖直走棋
        if (srcPoint.x == toPoint.x && srcPoint.y != toPoint.y) {
            int min = Math.min(srcPoint.y, toPoint.y);
            int max = Math.max(srcPoint.y, toPoint.y);
            for (int i = min + 1; i < max; i++) {
                if (mParentBoard.haveChessman(srcPoint.x, i)) {
                    count ++;
                }
            }
        }
        else if (srcPoint.y == toPoint.y && srcPoint.x != toPoint.x) {
            int min = Math.min(srcPoint.x, toPoint.x);
            int max = Math.max(srcPoint.x, toPoint.x);
            for (int i = min + 1; i < max; i++) {
                if (mParentBoard.haveChessman(i, srcPoint.y)) {
                    count ++;
                }
            }
        }
        if (0 == count && !mParentBoard.haveChessman(toPoint)) {
            return true;
        }
        else if (1 == count && mParentBoard.haveChessman(toPoint)) {
            return true;
        } 
        return false;
    }

    @Override
    protected int onObtainClassId() {
        return CLASS_ID_GUN;
    }
}
