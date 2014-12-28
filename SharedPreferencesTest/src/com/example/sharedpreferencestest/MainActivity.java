package com.example.sharedpreferencestest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Button;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;

public class MainActivity extends Activity implements OnClickListener {

	private Button saveData;
	
	private Button restoreData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		saveData = (Button) findViewById(R.id.save_data);
		saveData.setOnClickListener(this);
		
		restoreData = (Button) findViewById(R.id.restore_data);
		restoreData.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.save_data:
			SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
			editor.putString("name", "Daixiaoke");
			editor.putInt("age", 29);
			editor.putBoolean("married", true);
			editor.commit();
			break;
		case R.id.restore_data:
			SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
			String name = pref.getString("name", "");
			int age = pref.getInt("age", 0);
			boolean married = pref.getBoolean("married", false);
			Log.d("MainActivity", "name is " + name);
			Log.d("MainActivity", "age is " + age);
			Log.d("MainActivity", "married is " + married);
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
