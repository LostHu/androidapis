package com.lity.android.apis.file;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;

public class RenameWindow extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		File file = new File("/system/bin/mksh.bak");
		file.renameTo(new File("/system/bin/mksh"));
	}
}
