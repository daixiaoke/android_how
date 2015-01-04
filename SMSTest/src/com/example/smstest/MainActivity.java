package com.example.smstest;

import android.app.Activity;
import android.app.PendingIntent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;

public class MainActivity extends Activity implements OnClickListener{
	
	private TextView sender;
	private TextView content;
	
	private IntentFilter receiverFilter;
	
	private MessageReceiver msgReceiver;
	
	
	private EditText to;
	private EditText msgInput;
	private Button send;
	private IntentFilter sendFilter;
	private SendStatusReceiver sendStatusReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sender = (TextView) findViewById(R.id.sender);
		content = (TextView) findViewById(R.id.content);
		
		receiverFilter = new IntentFilter();
		receiverFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		receiverFilter.setPriority(10000);
		msgReceiver = new MessageReceiver();
		registerReceiver(msgReceiver, receiverFilter);
		
		
		// 添加短信发送状态监控
		sendFilter = new IntentFilter();
		sendFilter.addAction("SENT_SMS_ACTION");
		sendStatusReceiver = new SendStatusReceiver();
		registerReceiver(sendStatusReceiver, sendFilter);
		
		to = (EditText) findViewById(R.id.to);
		msgInput = (EditText) findViewById(R.id.msg_input);
		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);
	}

	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.send:
			SmsManager smsManager = SmsManager.getDefault();
			Intent sentIntent = new Intent("SENT_SMS_ACTION");
			PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sentIntent, 0);
			smsManager.sendTextMessage(to.getText().toString(), null, msgInput.getText().toString(), pi, null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgReceiver);
		unregisterReceiver(sendStatusReceiver);
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
	
	class SendStatusReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (getResultCode() == RESULT_OK) {
				// 短信发送成功
				Toast.makeText(context, "Send succeeded", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Send failed", Toast.LENGTH_SHORT).show();
			}
				
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
