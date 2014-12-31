package com.example.smstest;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

public class MainActivity extends Activity {
	
	private TextView sender;
	private TextView content;
	
	private IntentFilter receiverFilter;
	
	private MessageReceiver msgReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sender = (TextView) findViewById(R.id.sender);
		content = (TextView) findViewById(R.id.content);
		
		receiverFilter = new IntentFilter();
		receiverFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		receiverFilter.setPriority(100);
		msgReceiver = new MessageReceiver();
		registerReceiver(msgReceiver, receiverFilter);
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
	
	class MessageReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			// 提取短信消息
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			
			// 获取发送号码
			String address = messages[0].getOriginatingAddress();
			String fullMessage = "";
			for (SmsMessage message : messages) {
				// 获取短信内容
				fullMessage += message.getMessageBody();
			}
			
			sender.setText(address);
			content.setText(fullMessage);
			abortBroadcast();
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
