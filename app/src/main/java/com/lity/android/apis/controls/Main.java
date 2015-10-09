package com.lity.android.apis.controls;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lity.android.apis.R;
import com.lity.android.apis.net.HttpUrlWindow;
import com.lity.android.apis.tabs.Tab1;

public class Main extends Activity implements View.OnClickListener{
	private Button googleMap;
	private Button listContracts;
	private Button listGeneric;
	private Button tabActivity;
	private Button uriContracts;
	private Button editText;
	//private TextView textView; 
	//private int i = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_main);
        
        googleMap = (Button)findViewById(R.id.button1);
        listContracts = (Button)findViewById(R.id.button2);
        listGeneric = (Button)findViewById(R.id.button3);
        tabActivity = (Button)findViewById(R.id.button4);
        uriContracts = (Button)findViewById(R.id.button5);
        //editText = (Button)findViewById(R.id.editText);

        googleMap.setOnClickListener(this);
        listContracts.setOnClickListener(this);
        listGeneric.setOnClickListener(this);
        tabActivity.setOnClickListener(this);
        uriContracts.setOnClickListener(this);
        
//        PackageManager pManager = getPackageManager();
//        pManager.

    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//textView.setText(Integer.toString(i++));
		Object obj = findViewById(R.id.editText);
//        editText = (Button)(Object)findViewById(R.id.editText);
		Intent intent = new Intent();
		if(v == googleMap){
//			intent.setClass(Main.this, GoogleMap.class);
			intent.setClass(this, HttpUrlWindow.class);
		}
		else if (v == listContracts){
			//intent.setClass(Main.this, ContractListActivity.class);
		}
		else if (v == listGeneric) {
			intent.setClass(this, GenericListActivity.class); 
		}
		else if (v == tabActivity){
			intent.setClass(this, Tab1.class);
		}
		else if(v == uriContracts) {
			//intent.putExtra(Intent.ACTION_SEARCH, value)
            //startActivityForResult(new Intent(Intent.ACTION_PICK, People.CONTENT_URI), 0);
            //startActivityForResult(new Intent(Intent.ACTION_SEARCH), 0);
			intent.setAction(Intent.ACTION_SEARCH);
			intent.putExtra(SearchManager.QUERY, "a");
			//intent.setType(SearchManager.APP_DATA);
			startActivity(intent);
            return;
		}
		startActivity(intent);
	}
}