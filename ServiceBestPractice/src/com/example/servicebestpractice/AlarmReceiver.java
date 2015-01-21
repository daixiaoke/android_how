package com.example.servicebestpractice;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		//Log.d("LongRunningService", "receiver at " + new Date().toString());
		Intent i = new Intent(arg0, LongRunningService.class);
		arg0.startService(i);
	}

}
