package com.lity.android.apis.net;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lity.android.apis.R;

public class SocketWindow extends Activity implements View.OnClickListener {

	private static final int port = 54321;
	private EditText mEditView;
	private Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_window);
		
		// 构建文本编辑
		mEditView = (EditText)findViewById(R.id.sendtext_edittext);
		mButton = (Button) findViewById(R.id.sure_button);
		mButton.setOnClickListener(this);
		
	}
 
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		String str = mEditView.getText().toString() + "\r\n";
		try{
			InetAddress inet = InetAddress.getLocalHost();
			Socket msocket = new Socket("10.0.2.2", port);
			
			// 向服务器发送消息
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(msocket.getOutputStream())), true);
			out.println(str);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
