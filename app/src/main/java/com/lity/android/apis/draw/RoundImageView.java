package com.lity.android.apis.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * 鏆傛椂涓嶆敮鎸丮atrix鍜孖mageView.CropToPadding
 */
public class RoundImageView extends ImageView {

	private int mCropToPadding;

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundImageView(Context context) {
		super(context);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// // 鐢变簬鏃犳硶閬胯繃ImageView.onDraw()鍘昏皟鐢╒iew.onDraw()涓擵iew.onDraw()鏂规硶涓棤鍐呭, 涓嶅啀璋冪敤super.onDraw()
		// super.onDraw(canvas);
		//
		// // 浠ヤ笅浠ｇ爜閫昏緫鎽樿嚜Android-14, ImageView.onDraw().
		// Drawable drawable = getDrawable();
		// if (drawable == null) {
		// return; // couldn't resolve the URI
		// }
		// int drawableWidth = null == drawable ? 0 : drawable.getIntrinsicWidth();
		// int drawableHeight = null == drawable ? 0 : drawable.getIntrinsicHeight();
		// if (drawableWidth == 0 || drawableHeight == 0) {
		// return; // nothing to draw (empty bounds)
		// }
		//
		// if (mPaddingTop == 0 && mPaddingLeft == 0) {
		// drawable.draw(canvas);
		// } else {
		// int saveCount = canvas.getSaveCount();
		// canvas.save();
		//
		// // if (mCropToPadding) {
		// // final int scrollX = mScrollX;
		// // final int scrollY = mScrollY;
		// // canvas.clipRect(scrollX + mPaddingLeft, scrollY + mPaddingTop, scrollX + mRight
		// // - mLeft - mPaddingRight, scrollY + mBottom - mTop - mPaddingBottom);
		// // }
		//
		// canvas.translate(mPaddingLeft, mPaddingTop);
		//
		// // if (mDrawMatrix != null) {
		// // canvas.concat(mDrawMatrix);
		// // }
		// drawable.draw(canvas);
		//
		// drawRoundRect(RectF rect, float rx, float ry, Paint paint)
		// Draw the specified round-rect using the specified paint.
		// canvas.restoreToCount(saveCount);
		// }

		// int count = canvas.save();

		//
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);

		int count = canvas.save();
		Bitmap b = ((BitmapDrawable) getDrawable()).getBitmap();
		Path path = new Path();
		path.addRoundRect(new RectF(0, 0, b.getWidth(), b.getHeight()), 10, 10, Path.Direction.CW);
		canvas.clipPath(path);

		super.onDraw(canvas);

		canvas.drawBitmap(b, 0, 0, paint);
		canvas.restoreToCount(count);

		//
		// // canvas.drawPath(path, paint);
		//
		// boolean result = canvas.clipPath(path);
		// Log.v("DEBUG", "clip path result:" + result);
		//
		// canvas.restoreToCount(count);

		// canvas.draw

	}
}
