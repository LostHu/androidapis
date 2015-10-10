/**
 * 
 */
package com.lity.android.apis.controls;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lity.android.apis.R;

/**
 * @author Lity
 *
 */
public class RadioGroupWindow extends Activity {

	private RadioGroup mRadioGroup;
	private static final int rightId = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radiogroup_window);
		
		mRadioGroup = (RadioGroup) findViewById(R.id.select_radiogroup);
		
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (rightId == checkedId) {
					Toast.makeText(RadioGroupWindow.this, "回答正确", Toast.LENGTH_LONG);
				}
				else {
					Toast.makeText(RadioGroupWindow.this, "回答错误", Toast.LENGTH_LONG);
				}
			}
		});
	}

	
}
