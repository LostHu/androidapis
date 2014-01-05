package com.lity.android.apis.draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

public class View3DWindow extends Activity {

    private View3D mView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = new View3D(this);
        mView.setBackgroundColor(Color.WHITE);
        setContentView(mView);
    }

    public class View3D extends View {

        private Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq); 
        /**
         * @param context
         * @param attrs
         * @param defStyle
         */
        public View3D(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        /**
         * @param context
         * @param attrs
         */
        public View3D(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * @param context
         */
        public View3D(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
//            Matrix matrix = new Matrix();
//            canvas.getMatrix(matrix);
//            matrix.postTranslate(100, 100);
//            Matrix matrix2 = new Matrix();
//            Camera camera = new Camera();
//            camera.rotateY(60.0f);
//            camera.getMatrix(matrix2);
//            matrix.preConcat(matrix2);
//            canvas.setMatrix(matrix);
//            canvas.drawBitmap(mBitmap, 0, 0, new Paint());
            if (App.DEBUG) {
                Log.v(App.TAG, this + "onDraw method");
            }
        }
        
    }
    
}
