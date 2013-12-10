package com.liuyanghejerry.smsremote;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void saveSettings(View view) {
    	EditText sender = (EditText) findViewById(R.id.phoneNumber);
    	String phoneNumber = sender.getText().toString();
    	EditText post_url = (EditText) findViewById(R.id.postUrl);
    	String postUrl = post_url.getText().toString();
    	CheckBox enabled = (CheckBox) findViewById(R.id.isEnabled);
    	boolean isEnabled = enabled.isChecked();
    	System.out.println(phoneNumber+postUrl);
    }
}
 