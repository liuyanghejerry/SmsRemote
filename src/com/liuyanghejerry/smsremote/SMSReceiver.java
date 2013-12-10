package com.liuyanghejerry.smsremote;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];
        for (int n = 0; n < messages.length; n++) {
            smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            
            // write your own filter rule here
            if(smsMessage[n].getMessageBody().contains("hahaha")){ // hahaha
                this.abortBroadcast();
                
                // repost to service
                
                Intent reposter = new Intent(context, SmsRepostService.class);
                reposter.addCategory(Intent.CATEGORY_DEFAULT);
                
                ArrayList<String> info = new ArrayList<String>(2);
                info.add(smsMessage[n].getOriginatingAddress());
                info.add(smsMessage[n].getMessageBody());
                reposter.putExtra("SMS_ORDER", info);
                
                context.startService(reposter);
            }
            
            System.out.println(smsMessage[n].getOriginatingAddress()
            		+" "
            		+smsMessage[n].getMessageBody()
            		+" "
            		+smsMessage[n].getIndexOnIcc());
            
            
            
        }
    }

}