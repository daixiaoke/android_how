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
	
	// 使用 本地广播
	private LocalReceiver localReceiver;
	
	private LocalBroadcastManager localBroadcastManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 添加广播接收 全局广播
		/*intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver = new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver, intentFilter);*/
		
		// 发送标准广播消息
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
		
		// 本地广播
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		// 注册本地广播监听器
		intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		localReceiver = new LocalReceiver();
		localBroadcastManager.registerReceiver(localReceiver, intentFilter);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button:
			/*Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
			// 标准广播
			//sendBroadcast(intent);
			
			// 有序广播
			sendOrderedBroadcast(intent, null);*/
			
			// 发送本地广播
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
		// 全局广播
		/*Toast.makeText(MainActivity.this, "unregister networkChangeReceiver", Toast.LENGTH_SHORT).show();
		unregisterReceiver(networkChangeReceiver);*/
		
		// 本地广播
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
				Toast.makeText(context, "3G网络已连接！", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "3G网络已断开！", Toast.LENGTH_SHORT).show();
			}*/
			
			//添加wifi状态监测
			WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			int wifiState = wifiMan.getWifiState();
			if (wifiMan!=null && wifiState==wifiMan.WIFI_STATE_ENABLED) {
				Toast.makeText(context, "Wifi已连接！", Toast.LENGTH_SHORT).show();
			} /*else if (wifiState == wifiMan.WIFI_STATE_ENABLING){
				Toast.makeText(context, "Wifi正在启用！", Toast.LENGTH_SHORT).show();
			} */else {
				Toast.makeText(context, "Wifi已断开！", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	// 本地广播
	class LocalReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
		}
	}
	
}
