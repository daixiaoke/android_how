package com.example.activitytest;

import android.app.Activity;
import android.content.Context;
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

public class SecondActivity extends BaseActivity {

	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//Log.d("SecondActivity", this.toString());
		Log.d("SecondActivity", "Task id is " + getTaskId());
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
		/*Intent intent2 = getIntent();
		String data = intent2.getStringExtra("extra_data");
		Log.d("SecondaryActivity", data);*/
		//Toast.makeText(SecondActivity.this, data, Toast.LENGTH_SHORT).show();
		
		Button button2 = (Button) findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Toast.makeText(SecondActivity.this, "Bye Bye!",
						Toast.LENGTH_SHORT).show();
				finish();*/
				
				Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
				startActivity(intent);
				
				
				// 调用系统浏览器打开网页
				/*Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://www.baidu.com"));
				startActivity(intent);*/
				
				// 调用系统拨号界面
				/*Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:10010"));
				startActivity(intent);*/
				
				// 提取活动1发来的数据
				Intent intent2 = getIntent();
				String data1 = intent2.getStringExtra("param1");
				Log.d("SecondActivity", data1);
				String data2 = intent2.getStringExtra("param2");
				Log.d("SecondActivity", data2);
				//Toast.makeText(SecondActivity.this, data1, Toast.LENGTH_SHORT).show();
				
				// 向上一个活动返回数据
				/*Intent intent = new Intent();
				intent.putExtra("data_return", "data return to activity 1 from activity 2");
				setResult(RESULT_OK, intent);
				finish();*/
				
			}
		});
	}
	
	// 清晰的表达SecondActivity启动所需的参数
	public static void actionStart(Context context, String data1, String data2) {
		Intent intent = new Intent(context, SecondActivity.class);
		intent.putExtra("param1", data1);
		intent.putExtra("param2", data2);
		context.startActivity(intent);
	}
	
	/*public boolean onCreateOptionsMenu(Menu menu) {
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

	// 通过点击back键返回上一活动时也返回数据
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("data_return", "onBackPressed data return from activity 2");
		setResult(RESULT_OK, intent);
		finish();
	}*/

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("SecondActivity", "onDestory");
	}
	
	
}
