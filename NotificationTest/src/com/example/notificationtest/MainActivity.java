package com.example.notificationtest;

import android.app.Activity;
import android.app.Notification;
//import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Color;
import java.io.File;

public class MainActivity extends Activity implements OnClickListener {

	private Button sendNotice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendNotice = (Button) findViewById(R.id.send_notice);
		sendNotice.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.send_notice:
			NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			
			// Notification is deprecated
			//Notification notice = new Notification(R.drawable.notice, "This is ticker text", System.currentTimeMillis());
			
			
			Intent intent = new Intent(this, NotificationActivity.class);
			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

			// add sound to notificaton
			Uri soundUri = Uri.fromFile( new File("/system/media/audio/notifications/FadeIn.ogg") );
			// add vibrate to notification
			long [] vibrate = {0, 500, 500, 500};
			
			// use Notification.Builder instead (minimum sdk version is 16)
			Notification notice = new Notification.Builder(this)
				.setContentTitle("This is content title")
				.setContentText("This is content text")
				.setSmallIcon(R.drawable.notice)
				.setTicker("This is ticker text")
				// setLatestEventInfo is deprecated
				//notice.setLatestEventInfo(this, "This is content title", "This is content text", pi);
				// use setContentIntent(PendingIntent) instead
				.setContentIntent(pi)
				.setSound(soundUri)
				.setVibrate(vibrate)
				.build();
			
			// 默认的效果
			//notice.defaults = Notification.DEFAULT_ALL;
			
			// use led green notice
			notice.ledARGB = Color.RED;
			notice.ledOnMS = 500;
			notice.ledOffMS = 500;
			notice.flags = Notification.FLAG_SHOW_LIGHTS;
			
			manager.notify(1, notice);
			break;
		default:
			break;	
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
