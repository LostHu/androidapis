package com.lity.android.apis.draw;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

public class GraphicBitmap extends Activity {

	public static final int DEEPTH = 4;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createBitmap();
	}

	private void createBitmap() {
		InputStream stream = null;
		FileOutputStream out = null;

		try {
			stream = new FileInputStream("/sdcard/fb0");

			byte[] piex = new byte[WIDTH * HEIGHT * DEEPTH];
			stream.read(piex, 0, piex.length);
			int[] colors = new int[WIDTH * HEIGHT];
			for (int i = 0; i < piex.length; i++) {
				if (i % 4 == 0) {
					int r = (piex[i] & 0xFF);
					int g = (piex[i + 1] & 0xFF);
					int b = (piex[i + 2] & 0xFF);
					int a = (piex[i + 3] & 0xFF);
					colors[i / 4] = (a << 24) + (r << 16) + (g << 8) + b;
				}
			}

			Bitmap bitmap = Bitmap.createBitmap(colors, WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);

			out = new FileOutputStream("/sdcard/fb.png");
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

		} catch (Exception e) {
			System.err.println(e);
		}

	}
}
