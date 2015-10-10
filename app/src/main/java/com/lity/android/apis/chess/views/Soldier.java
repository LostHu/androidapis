package com.lity.android.apis.chess.views;

import android.content.Context;

public class Soldier extends Chessman {

    public Soldier(Context context, ChessBoard board) {
        super(context, board);
    }  

    @Override
    public boolean canArriveTo(ChessPoint toPoint) {
        final ChessPoint srcPoint = mPoint;
        
        // ���
        if (MAN_TYPE_RED == getBelongPlayer()) {
            // ����������
            if (srcPoint.y <= 4 && 1 == Math.abs(toPoint.x - srcPoint.x) && srcPoint.y == toPoint.y) {
                return true;
            }
            // ��������ǰ��
            else if (1 == srcPoint.y - toPoint.y && srcPoint.x == toPoint.x) {
                return true;
            } 
        }
        else if (MAN_TYPE_GREEN == getBelongPlayer()) {
            // ����������
            if (srcPoint.y >= 5 && 1 == Math.abs(toPoint.x - srcPoint.x) && srcPoint.y == toPoint.y) {
                return true;
            }
            // ��������ǰ��
            else if (1 == toPoint.y - srcPoint.y && srcPoint.x == toPoint.x) {
                return true;
            } 
        }
        return false;
    }

    @Override
    protected int onObtainClassId() {
        return CLASS_ID_SOLDIER;
    }
}
