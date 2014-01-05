package com.lity.android.apis.chess.views;

import android.content.Context;

public class Car extends Chessman {


    public Car(Context context, ChessBoard board) {
        super(context, board);
    }

    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        
        boolean result = true;
        final ChessPoint srcPoint = mPoint;
//        if (srcPoint.x == toPoint.x && srcPoint.y == toPoint.y) {
//            result = false;
//        }
//        else if (srcPoint.x != toPoint.x && srcPoint.y != toPoint.y) {
//            throw new IllegalArgumentException();
//            result = false;
//        }
        
        if (srcPoint.x == toPoint.x) {
            if (srcPoint.y == toPoint.y) {
                result = false;
            }
            else {
                int min = Math.min(srcPoint.y, toPoint.y);
                int max = Math.max(srcPoint.y, toPoint.y);
                for (int i = min + 1; i < max; i++) {
                    if (mParentBoard.haveChessman(srcPoint.x, i)) {
                        result = false;
                        break;
                    }
                }
            }
        } else if (srcPoint.y == toPoint.y) {
            if (srcPoint.x == toPoint.x) {
                result = false;
            }
            else {
                int min = Math.min(srcPoint.x, toPoint.x);
                int max = Math.max(srcPoint.x, toPoint.x);
                for (int i = min + 1; i < max; i++) {
                    if (mParentBoard.haveChessman(i, srcPoint.y)) {
                        result = false;
                        break;
                    }
                }
            }
        } else {
            result = false;
        }
        
        return result;
    }

    @Override
    protected int onObtainClassId() {
        return CLASS_ID_CAR;
    }

}
