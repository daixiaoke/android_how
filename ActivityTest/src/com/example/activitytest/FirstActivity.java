package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends Activity {
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		Button button1 = (Button) findViewById(R.id.button_1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast
				//Toast.makeText(FirstActivity.this, "����Ƿ��",
					//	Toast.LENGTH_SHORT).show();
				
				// ��ʾintent
				Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
				//startActivity(intent);
				
				//��ʽintent
				/*Intent intent = new Intent("com.example.activitytest.ACTION_START");
				intent.addCategory("com.example.activitytest.MY_CATEGORY");*/
				/*Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.baidu.com"));*/
				
				// 	�������ݸ��2
				String data = "�´��ٴ�����Ŷ";
				intent.putExtra("extra_data", data);
				startActivity(intent);
			}
		});
		/*Button button2 = (Button) findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(FirstActivity.this, "Bye Bye!",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});*/
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_item:
			Toast.makeText(this, "�ñ��ӳ�", Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(this, "�ù��ӳ�", Toast.LENGTH_SHORT).show();
			break;
		default:
		}
		return true;
	}
	
}
