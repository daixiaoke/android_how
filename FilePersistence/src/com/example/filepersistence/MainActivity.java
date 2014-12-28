package com.example.filepersistence;

import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText edit;
	
	
	// use c/c++ library
	static {
		try {
		System.loadLibrary("fileio");
		} catch (UnsatisfiedLinkError ule) {
			Log.e("JNI", "WARNING: Could not load libfileio.so");
		}
	}	
	
	public native void saveData(String inputText);
	public native String loadData(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edit = (EditText) findViewById(R.id.edit_text);
		// restore saved data
		//String savedData = load();
		String savedData = loadData();
		if (!TextUtils.isEmpty(savedData)) {
			edit.setText(savedData);
			edit.setSelection(savedData.length());
			Toast.makeText(MainActivity.this, "Restroing succeeded", Toast.LENGTH_SHORT).show();
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		// save data for restoring
		String inputText = edit.getText().toString();
		//save(inputText);
		saveData(inputText);
	}
	
	/*public String load() {
	String line = "";
	FileInputStream in = null;
	BufferedReader reader = null;
	StringBuilder content = new StringBuilder();
	try {
		in = openFileInput("data_save.txt");
		reader = new BufferedReader(new InputStreamReader(in));
		while ( (line=reader.readLine()) != null ) {
			content.append(line);
		}
	} catch(IOException e) {
		e.printStackTrace();
	} finally {	
		if (reader != null) {
			try {
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}	
		}
	}
	return content.toString();
	}*/
	/*
	public void save(String saveData) {
		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {
			out = openFileOutput("data_save", MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(saveData);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();				
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	*/
}