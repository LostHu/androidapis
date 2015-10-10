package com.lity.android.apis.chess.views;

import android.content.Context;

public class Horse extends Chessman {

    public Horse(Context context, ChessBoard board) {
        super(context, board);
    }


    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        final ChessPoint srcPoint = mPoint;
        if (2 == Math.abs(srcPoint.x - toPoint.x) && 1 == Math.abs(srcPoint.y - toPoint.y)) {
            // 马腿点
            ChessPoint dot = new ChessPoint(Math.min(srcPoint.x, toPoint.x) + 1, srcPoint.y);
            if (!mParentBoard.haveChessman(dot)) {
                return true;
            }
        }
        else if (2 == Math.abs(srcPoint.y - toPoint.y) && 1 == Math.abs(srcPoint.x - toPoint.x)) {
            // 马腿点
            ChessPoint dot = new ChessPoint(srcPoint.x, Math.min(srcPoint.y, toPoint.y) + 1);
            if (!mParentBoard.haveChessman(dot)) {
                return true;
            }
        } 
        return false;
    }
    
    @Override
    protected int onObtainClassId() {
        return CLASS_ID_HORSE;
    }
}
