package com.hospital.androidui2;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import com.hospital.androidui2.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
 
public class SecondActivity extends Activity {
   
    String URL;
    JSONArray form;
    String JSONTEXT;
    JSONObject jObj = null; // JSONObject to hold whole form
    JSONObject element = null; // JSONObject to be used to store each form element
    
    private static final String TAG_FORMNAME="name";
    private static final String TAG_FORM = "form";
    private static final String TAG_TXTFIELD = "text-field";
    private static final String TAG_CHECK = "check-box";
    private static final String TAG_RADIO = "radio-group";
    private static final String TAG_DROPLIST = "drop-down-list";
    private static final String TAG_TIME = "time-picker";
    private static final String TAG_DATE = "date-picker";
    private static final String TAG_CALENDAR = "calendar-date-picker";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "input-type";
    private static final String TAG_DEFTEXT = "default-text";
    private static final String TAG_OPTIONS = "options";
    private static final String TAG_DEFCHOICE = "default-choice";
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View v = inflater.inflate(R.layout.second, null);
	
	ScrollView sv = (ScrollView) v.findViewById(R.id.scrForm);
	
	
	
	
	LinearLayout ll = new LinearLayout(this);
	ll.setOrientation(LinearLayout.VERTICAL);
	Bundle extras = getIntent().getExtras();
	
	if(extras !=null) {
	    URL = extras.getString("URLKEY");
	}
	String str = DownloadText(URL);
        
	
	
	JSONTEXT = str;
        
	try {
	    jObj = new JSONObject(JSONTEXT);
	} catch (JSONException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	
	try {
	    // getting array of elements
	    form = jObj.getJSONArray(TAG_FORM);
	    String formName = jObj.getString(TAG_FORMNAME);
	    
	    setTitle(formName);

	    for (int i = 0; i < form.length(); i++) {
		JSONObject c = form.getJSONObject(i);

		if (c.has(TAG_TXTFIELD)) {
		    element = c.getJSONObject(TAG_TXTFIELD);
		    String id = element.getString(TAG_ID);
		    String name = element.getString(TAG_NAME);
		    String type = element.getString(TAG_TYPE);
		    String defText = element.getString(TAG_DEFTEXT);
		    
		    TextView tv = new TextView(this);
		    tv.setText(name);
		    EditText et = new EditText(this);
		    et.setText(defText);
		    ll.addView(tv);
		    ll.addView(et);
		}
		
		if (c.has(TAG_CHECK)) {
		    element = c.getJSONObject(TAG_CHECK);
		    String id = element.getString(TAG_ID);
		    String name = element.getString(TAG_NAME);
		    JSONArray options = element.getJSONArray(TAG_OPTIONS);
		    
		    TextView tv = new TextView (this);
		    tv.setText(name);
		    ll.addView(tv);
		    for (int j =0; j<options.length(); j++)
		    {
			CheckBox cb = new CheckBox(this);
			cb.setText(options.getString(j));
			//cb.setChecked(true);//checked state
			ll.addView(cb);
			
		    }
		}
		
		if (c.has(TAG_RADIO))
		{
		    element = c.getJSONObject(TAG_RADIO);
		    String id = element.getString(TAG_ID);
		    String name = element.getString(TAG_NAME);
		    JSONArray options = element.getJSONArray(TAG_OPTIONS);
		    
		    TextView tv = new TextView (this);
		    tv.setText(name);
		    ll.addView(tv);
		    RadioGroup rg = new RadioGroup(this);
		    for (int j=0;j<options.length();j++)
		    {
			RadioButton rb = new RadioButton(this);
			rb.setText(options.getString(j));
			rg.addView(rb);
			
			//setting default choice
			//rb.setChecked(j==1);
		    }
		    ll.addView(rg);
		}
		if (c.has(TAG_DROPLIST))
		{
		    
		}
		if (c.has(TAG_TIME))
		{
		    
		}
		if (c.has(TAG_DATE))
		{
		    
		}
		if (c.has(TAG_CALENDAR))
		{
		    
		}
		

	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}
	
	sv.addView(ll);
	
	setContentView(v);
 
    }

    /**
private String readTxt() {

	
   	InputStream inputStream = getResources()
   		.openRawResource(R.raw.example);
   	System.out.println(inputStream);
   	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

   	int i;
   	try {
   	    i = inputStream.read();
   	    while (i != -1) {
   		byteArrayOutputStream.write(i);
   		i = inputStream.read();
   	    }
   	    inputStream.close();
   	} catch (IOException e) {
   	    // TODO Auto-generated catch block
   	    e.printStackTrace();
   	}

   	return byteArrayOutputStream.toString();
   	
   	
       }
       */

private String DownloadText(String URL)
{
    int BUFFER_SIZE = 2000;
    InputStream in = null;
    try {
        in = OpenHttpConnection(URL);
    } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        return "";
    }
     
    InputStreamReader isr = new InputStreamReader(in);
    int charRead;
    String str = "";
    char[] inputBuffer = new char[BUFFER_SIZE];          
    try {
        while ((charRead = isr.read(inputBuffer))>0)
        {                    
            //---convert the chars to a String---
            String readString = String.copyValueOf(inputBuffer, 0, charRead);
            str += readString;
            inputBuffer = new char[BUFFER_SIZE];
        }
        in.close();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return "";
    }    
    return str;        
}

private InputStream OpenHttpConnection(String urlString) 
throws IOException
{
    InputStream in = null;
    int response = -1;
            
    URL url = new URL(urlString); 
    URLConnection conn = url.openConnection();
              
    if (!(conn instanceof HttpURLConnection))                     
        throw new IOException("Not an HTTP connection");
     
    try{
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setAllowUserInteraction(false);
        httpConn.setInstanceFollowRedirects(true);
        httpConn.setRequestMethod("GET");
        httpConn.connect(); 

        response = httpConn.getResponseCode();                 
        if (response == HttpURLConnection.HTTP_OK) {
            in = httpConn.getInputStream();                                 
        }                     
    }
    catch (Exception ex)
    {
        throw new IOException("Error connecting");            
    }
    return in;     
}

}