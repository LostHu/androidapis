package com.lity.android.apis.transformation;

import com.lity.android.apis.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

public class Matrix1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = new DrawView(this);
		setContentView(v);
	}
	
	class DrawView extends View {
		public DrawView(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(R.drawable.icon);
			Matrix matrix = new Matrix();
			canvas.drawBitmap(drawable.getBitmap(), matrix, new Paint());
			
			
			float[] f = new float[9];
			matrix.getValues(f);
			f[Matrix.MSCALE_Y] = -1;
			f[Matrix.MTRANS_Y] = drawable.getBitmap().getHeight();
			matrix.setValues(f);
			
			matrix.postTranslate(0, drawable.getBitmap().getHeight());
			Paint paint = new Paint();
			
			ColorMatrix colorMatrix = new ColorMatrix();
			Options options = new Options();
			
			paint.setAlpha(80);
			canvas.drawBitmap(drawable.getBitmap(), matrix, paint);
		}
	}
}
