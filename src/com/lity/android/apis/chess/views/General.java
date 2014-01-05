package com.lity.android.apis.chess.views;

import android.content.Context;

public class General extends Chessman {

    public General(Context context, ChessBoard board) {
        super(context, board);
    }



    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        final ChessPoint srcPoint = mPoint;
        // 防止出米字格
        if (MAN_TYPE_RED == getBelongPlayer() && toPoint.y >= 7 && 3 <= toPoint.x && toPoint.x <= 5) {
            if (0 == Math.abs(srcPoint.x - toPoint.x) && 1 == Math.abs(srcPoint.y - toPoint.y)) {
                return true;
            }
            else if (1 == Math.abs(srcPoint.x - toPoint.x) && 0 == Math.abs(srcPoint.y - toPoint.y))
            {
                return true;
            }
        }
        else if (MAN_TYPE_GREEN == getBelongPlayer() && toPoint.y <= 2 && 3 <= toPoint.x && toPoint.x <= 5) {
            if (0 == Math.abs(srcPoint.x - toPoint.x) && 1 == Math.abs(srcPoint.y - toPoint.y)) {
                return true;
            }
            else if (1 == Math.abs(srcPoint.x - toPoint.x) && 0 == Math.abs(srcPoint.y - toPoint.y)) {
                return true;
            }
        }
        
        // 老将对面
        Chessman child = mParentBoard.getChild(toPoint);
        if (null != child && child instanceof General && toPoint.x == srcPoint.x) {
            
            // 两老将中间是否有棋子
            boolean have = false;
            int min = Math.min(srcPoint.y, toPoint.y);
            int max = Math.max(srcPoint.y, toPoint.y);
            for (int i = min + 1; i < max; i++) {
                if (mParentBoard.haveChessman(toPoint.x, i)) {
                    have = true;
                    break;
                }
            }
            if (!have) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected int onObtainClassId() {
        return CLASS_ID_GENERAL;
    }
}
