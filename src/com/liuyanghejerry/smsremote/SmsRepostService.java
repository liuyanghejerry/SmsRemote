package com.liuyanghejerry.smsremote;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class SmsRepostService extends IntentService {
	
	// define your HTTP address here
	public static final String WEBSITE = "http://www.example.com/login";

	public SmsRepostService() {
		super("SmsRepostService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {		
		System.out.println("Got intent");
		ArrayList<String> data = intent.getStringArrayListExtra("SMS_ORDER");
		
		if(data.size() < 2) {
			return ;
		}
		
		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();

		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(WEBSITE);
		
		// Building post parameters, key and value pair
		// insert content you're interested in here
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("sender", data.get(0)));
		nameValuePair.add(new BasicNameValuePair("content", data.get(1)));
		
		// Url Encoding the POST parameters
		try {
		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch (UnsupportedEncodingException e) {
		    // writing error to Log
		    e.printStackTrace();
		}
		
		// Making HTTP Request
		try {
		    HttpResponse response = httpClient.execute(httpPost);
		 
		    // writing response to log
		    Log.d("Http Response:", response.toString());
		 
		} catch (ClientProtocolException e) {
		    // writing exception to log
		    e.printStackTrace();
		         
		} catch (IOException e) {
		    // writing exception to log
		    e.printStackTrace();
		}
	}

}
