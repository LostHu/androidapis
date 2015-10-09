package com.lity.android.apis.draw;

import android.app.Activity;
import android.os.Bundle;

import com.lity.android.apis.R;

public class RoundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RoundImageView round = new RoundImageView(this);
		round.setImageResource(R.drawable.qq);
		setContentView(round);
	}
}
