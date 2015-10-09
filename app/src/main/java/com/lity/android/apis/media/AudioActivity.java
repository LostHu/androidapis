package com.lity.android.apis.media;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class AudioActivity extends Activity implements OnClickListener {

	RelativeLayout relativeLayout;
	//LayoutInflater layoutInflater;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		relativeLayout = new RelativeLayout(this);
		//layoutInflater = LayoutInflater.from(this);
		button = new Button(this);
		button.setText("play");
		button.setOnClickListener(this);
		RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 1);
		relativeLayout.addView(button, params);
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource("/sdcard/music/1.mp3");
			mp.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.start(); 
		
	}
	
}
