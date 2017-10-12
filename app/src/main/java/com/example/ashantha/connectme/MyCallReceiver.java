package com.example.ashantha.connectme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyCallReceiver extends BroadcastReceiver {

    static boolean isRinging=false;
    static boolean isReceived=false;
    static String callerPhoneNumber;

    MainActivity mainAc = null;

    public void setMainActivityHandler(MainActivity main){
        mainAc = main;
    }

    @Override
    public void onReceive(Context mContext, Intent intent){

        // Get current phone state
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state==null)
            return;

        //phone is ringing
        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            isRinging =true;
            //get caller's number
            Bundle bundle = intent.getExtras();
            callerPhoneNumber= bundle.getString("incoming_number");
        }

        //phone is received
        if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            isReceived=true;
        }

        // phone is idle
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            // detect missed call
            if(isRinging==true && isReceived==false){
                //Toast.makeText(mContext, "Got a missed call from : "+callerPhoneNumber, Toast.LENGTH_LONG).show();
                mainAc.doSubscribe();
            }
        }
    }
}