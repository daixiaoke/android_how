package com.example.uisizetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		float xdpi = getResources().getDisplayMetrics().xdpi;
		float ydpi = getResources().getDisplayMetrics().ydpi;
		Log.d("MainActivity", "xdpi is " + xdpi);
		Log.d("MainActivity", "ydpi is " + ydpi);
		
		// ���͹㲥��Ϣ
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button:
			Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
			sendBroadcast(intent);
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
