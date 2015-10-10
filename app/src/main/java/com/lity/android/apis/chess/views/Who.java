package com.lity.android.apis.chess.views;

import android.content.Context;

public class Who extends Chessman {

    public Who(Context context, ChessBoard board) {
        super(context, board);
    }


    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        final ChessPoint srcPoint = mPoint;
        // ·ÀÖ¹³öÃ××Ö¸ñ
        if (MAN_TYPE_RED == getBelongPlayer() && toPoint.y >= 7 && 3 <= toPoint.x && toPoint.x <= 5) {
            if (1 == Math.abs(srcPoint.x - toPoint.x) && 1 == Math.abs(srcPoint.y - toPoint.y)) {
                return true;
            }
        }
        else if (MAN_TYPE_GREEN == getBelongPlayer() && toPoint.y <= 2 && 3 <= toPoint.x && toPoint.x <= 5) {
            if (1 == Math.abs(srcPoint.x - toPoint.x) && 1 == Math.abs(srcPoint.y - toPoint.y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected int onObtainClassId() {
        return CLASS_ID_WHO;
    }
}
