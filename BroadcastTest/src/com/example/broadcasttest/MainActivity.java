package com.example.broadcasttest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	
	private IntentFilter intentFilter;
	
	private NetworkChangeReceiver networkChangeReceiver;
	
	// ʹ�� ���ع㲥
	private LocalReceiver localReceiver;
	
	private LocalBroadcastManager localBroadcastManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ��ӹ㲥���� ȫ�ֹ㲥
		/*intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver = new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver, intentFilter);*/
		
		// ���ͱ�׼�㲥��Ϣ
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
		
		// ���ع㲥
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		// ע�᱾�ع㲥������
		intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		localReceiver = new LocalReceiver();
		localBroadcastManager.registerReceiver(localReceiver, intentFilter);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button:
			/*Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
			// ��׼�㲥
			//sendBroadcast(intent);
			
			// ����㲥
			sendOrderedBroadcast(intent, null);*/
			
			// ���ͱ��ع㲥
			Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
			localBroadcastManager.sendBroadcast(intent);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ȫ�ֹ㲥
		/*Toast.makeText(MainActivity.this, "unregister networkChangeReceiver", Toast.LENGTH_SHORT).show();
		unregisterReceiver(networkChangeReceiver);*/
		
		// ���ع㲥
		Toast.makeText(MainActivity.this, "unregister localReceiver", Toast.LENGTH_SHORT).show();
		localBroadcastManager.unregisterReceiver(localReceiver);
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
	
	class NetworkChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();
			/*ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkinfo = connectionManager.getActiveNetworkInfo();
			if (networkinfo!=null && networkinfo.isAvailable()) {
				Toast.makeText(context, "3G���������ӣ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "3G�����ѶϿ���", Toast.LENGTH_SHORT).show();
			}*/
			
			//���wifi״̬���
			WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			int wifiState = wifiMan.getWifiState();
			if (wifiMan!=null && wifiState==wifiMan.WIFI_STATE_ENABLED) {
				Toast.makeText(context, "Wifi�����ӣ�", Toast.LENGTH_SHORT).show();
			} /*else if (wifiState == wifiMan.WIFI_STATE_ENABLING){
				Toast.makeText(context, "Wifi�������ã�", Toast.LENGTH_SHORT).show();
			} */else {
				Toast.makeText(context, "Wifi�ѶϿ���", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	// ���ع㲥
	class LocalReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
		}
	}
	
}
