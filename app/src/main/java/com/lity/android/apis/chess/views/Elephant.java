package com.lity.android.apis.chess.views;

import android.content.Context;

public class Elephant extends Chessman {

    public Elephant(Context context, ChessBoard board) {
        super(context, board);
    }
    

    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        final ChessPoint srcPoint = mPoint;
        if (2 == Math.abs(srcPoint.x - toPoint.x) && 2 == Math.abs(srcPoint.y - toPoint.y)) {
            // 象眼点
            ChessPoint dot = new ChessPoint(Math.min(srcPoint.x, toPoint.x) + 1, Math.min(srcPoint.y, toPoint.y) + 1);
            
            //加防过河
            int line = (srcPoint.y + toPoint.y) / 2;
            if (!mParentBoard.haveChessman(dot) && line != 4 && line != 5) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected int onObtainClassId() {
        return CLASS_ID_ELEPHANT;
    }
}
