package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends Activity {

	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*Button button1 = (Button) findViewById(R.id.button_1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(FirstActivity.this, "你真欠抽",
						Toast.LENGTH_SHORT).show();
			}
		});*/
		
		// 提取活动1发来的数据
		Intent intent2 = getIntent();
		String data = intent2.getStringExtra("extra_data");
		Log.d("SecondaryActivity", data);
		//Toast.makeText(SecondActivity.this, data, Toast.LENGTH_SHORT).show();
		
		Button button2 = (Button) findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Toast.makeText(SecondActivity.this, "Bye Bye!",
						Toast.LENGTH_SHORT).show();
				finish();*/
				// 调用系统浏览器打开网页
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.baidu.com"));
				// 调用系统拨号界面
				/*Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:10010"));*/
				startActivity(intent);
				// 提取活动1发来的数据
				/*Intent intent2 = getIntent();
				String data = intent2.getStringExtra("extra_data");
				//Log.d("SecondaryActivity", data);
				Toast.makeText(SecondActivity.this, data,
						Toast.LENGTH_SHORT).show();*/
				
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_item:
			Toast.makeText(this, "用馒头砸", Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(this, "用冰水浇", Toast.LENGTH_SHORT).show();
			break;
		default:
		}
		return true;
	}
	

}
