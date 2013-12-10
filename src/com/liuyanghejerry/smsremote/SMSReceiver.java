package com.liuyanghejerry.smsremote;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);
    	System.out.println("enabled: " + sharedSettings.getBoolean("enabled", false));
    	boolean enabled = sharedSettings.getBoolean("enabled", false);
    	
    	if(!enabled) {
    		return;
    	}
    	
    	String rule = sharedSettings.getString("regex", "");
//    	if(rule.trim().length() < 1) {
//    		rule = ".*";
//    	}
    	String accept_sender = sharedSettings.getString("accept_sender", "");
    	
        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        
        for (int n = 0; n < messages.length; n++) {
        	
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            String sms_content = smsMessage[n].getMessageBody();
            String sms_address = smsMessage[n].getOriginatingAddress().trim();
            
            System.out.println("accept_sender: " + accept_sender);
            System.out.println("rule: " + rule);
            boolean is_sender_match = accept_sender.equals(sms_address);
            boolean is_content_macth = sms_content.matches(rule);
            boolean is_rule_empty = rule.trim().length() < 1;
            // write your own filter rule here
            if(  is_sender_match
            		&& ( is_content_macth || is_rule_empty ) ){ // apply rule

            	// do not notify system's mms
            	this.abortBroadcast();
                
                // repost to service
                Intent reposter = new Intent(context, SmsRepostService.class);
                reposter.addCategory(Intent.CATEGORY_DEFAULT);
                
                ArrayList<String> info = new ArrayList<String>(2);
                info.add(sms_address);
                info.add(sms_content);
                reposter.putExtra("SMS_ORDER", info);
                
                context.startService(reposter);
            } else {
            	System.out.println("unmactched message, ignored");
            }
            
//            System.out.println(sms_address
//            		+" "
//            		+sms_content
//            		+" "
//            		+smsMessage[n].getIndexOnIcc());

        }
    }

}