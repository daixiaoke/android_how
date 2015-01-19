package com.example.servicetest;

//import com.example.notificationtest.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.os.Binder;

public class MyService extends Service {
	
	private DownloadBinder mBinder = new DownloadBinder();
	
	class DownloadBinder extends Binder {
		public void startDownload() {
			Log.d("MyService", "startDownload executed");
		}
		
		public int getProgress() {
			Log.d("MyService", "getProgress executed");
			return 0;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("MyService", "onBind executed");
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		//Notification notification = new Notification(R.drawable.ic_launcher, 
			//	"Notification comes", System.currentTimeMillis());
		Intent ntfIntent = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, ntfIntent, 0);
		//notification.setLatestEventInfo(this, "This is title", "This is content", pi);
		// use Notification.Builder instead (minimum sdk version is 16)
					Notification notice = new Notification.Builder(this)
						.setContentTitle("This is content title")
						.setContentText("This is content text")
						.setSmallIcon(R.drawable.ic_launcher)
						.setTicker("This is ticker text")
						// setLatestEventInfo is deprecated
						//notice.setLatestEventInfo(this, "This is content title", "This is content text", pi);
						// use setContentIntent(PendingIntent) instead
						.setContentIntent(pi)
						.build();
		startForeground(1, notice);
		
		Log.d("MyService", "onCreate executed");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService", "onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("MyService", "onDestroy executed");
	}

}
