package com.hospital.androidui2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
    
 String URL;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	View formBtn =  findViewById(R.id.buttonForm);
	formBtn.setOnClickListener(this);
	
	View NFCBtn = findViewById(R.id.buttonNFC);
	NFCBtn.setOnClickListener(this);
	
	View ScanBtn = findViewById(R.id.buttonBarcode);
	ScanBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
    }

    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	EditText et = (EditText) findViewById(R.id.url);
	URL = et.getText().toString();
	if(v.getId() == R.id.buttonForm){
		//define a new Intent for the second Activity
	    	
	    	
		Intent intent = new Intent(this,SecondActivity.class);
		intent.putExtra("URLKEY", URL);

		//start the second Activity
		this.startActivity(intent);
	}
	if(v.getId() == R.id.buttonNFC)
	{
	    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("nfc.tag");
	    startActivity(LaunchIntent);
	}
	if(v.getId() == R.id.buttonBarcode)
	{
	    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.simonmacdonald.scan");
	    startActivity(LaunchIntent);
	}
	
	
    }

    
    
}
