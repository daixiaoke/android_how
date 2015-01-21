package com.example.servicebestpractice;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class LongRunningService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override 
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d("LongRunningService", "executed at " + new Date().toString());
			}
		}).start();
		
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int delay_time = 2 * 1000;
		int interval = 4 * 1000;
		long AHEAD_TIME_MILLIS = 5 * 60 * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + delay_time;// - AHEAD_TIME_MILLIS;
		long time = System.currentTimeMillis() + interval;
		Intent i = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		
		// MIUI�϶��ӳٻ������˶��룬���5���ӣ������������Ӧ��ÿ5���ӻ���һ���ֻ�
		// HTC��ROM���ӳ�ok
		//manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		//manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, delay_time, pi);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pi);
		
		return super.onStartCommand(intent, flags, startId);
	}

}
