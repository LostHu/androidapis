package com.lity.android.apis.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.StaticLayout;
import android.text.Layout.Alignment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class ChineseTextView extends TextView {
    private final boolean debug = true;
    private final String DEBUG_TAG ="DEBUG";

    public ChineseTextView(Context context) {
        super(context);
    }

    public ChineseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChineseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Paint paint = getPaint();
        if (debug) {
            Log.v(DEBUG_TAG, "canvas clipBounds:" + canvas.getClipBounds());
        }
        FontMetrics fmFontMetrics = paint.getFontMetrics();
        
//        // 原点
//        int originX = 150, originY = (getBottom() - getTop()) / 2;
//        
//        // 画坐标系
//        Path path = new Path();
//        path.moveTo(10, (getBottom() - getTop()) / 2);
//        path.lineTo(300, (getBottom() - getTop()) / 2);
//        path.lineTo(290, ((getBottom() - getTop()) / 2) - 5);
//
//        path.moveTo(300, (getBottom() - getTop()) / 2);
//        path.lineTo(290, ((getBottom() - getTop()) / 2) + 5);
//        
//        path.moveTo(150, 10);
//        path.lineTo(150, 445);
//        
//        // 画圆
////        path.addCircle(originX, originY, 100, Direction.CW);
//        
//        final int width = 200, height = 100;
//        path.addArc(new RectF(originX - width / 2, originY - height / 2, width / 2 + originX, height / 2 + originY), 0, 360);
//        
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(1.0F);
//        paint.setStyle(Style.STROKE);
//        canvas.drawPath(path, paint);
//        setGravity(Gravity.LEFT);
        
        StaticLayout layout = new StaticLayout(getText(), 0, getText().length(), 
                getPaint(), 320, Alignment.ALIGN_NORMAL, 1.0F, 1.0F, true, null, 320);
        Log.v(DEBUG_TAG, "lineCount:" + layout.getLineCount());
        Log.v(DEBUG_TAG, "layout width:" + layout.getWidth() + ", layout height:" + layout.getHeight());
//        Log.v(DEBUG_TAG, (new Character('A')));
        String str;
        char c = 'a';
        if (c == 20) {
            
        }
        layout.draw(canvas);
        
    }
    
    public static class ChineseLayout extends Object {
        
    }
    

}
