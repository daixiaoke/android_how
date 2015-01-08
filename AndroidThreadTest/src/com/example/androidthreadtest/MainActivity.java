package com.example.androidthreadtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	private static final int UPDATE_TEXT = 1;
	
	private Button changeText;
	private TextView text;
	
	protected ProgressDialog progressDialog;
	
	protected int dlPercent = 0;
	
	// 采用异步通信机制， 主线程中用handleMessage()方法处理子线程发来的Message
	/*private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case UPDATE_TEXT:
				// Handler 的消息处理接口中可以进行UI操作
				text.setText("Nice to meet you");
				break;
			default:
				break;
			}
		}
	};*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView)findViewById(R.id.text);
		
		changeText = (Button)findViewById(R.id.change_text);
		changeText.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_text:
			// 创建线程
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					// fatal error: 无法在线程中更新UI
					//text.setText("Nice to meet you");
					
					// 采用异步通信机制， 用Handler对象发送Message
					Message message = new Message();
					message.what = UPDATE_TEXT;
					handler.sendMessage(message);
				}
			}).start();
			*/
			
			// 启动下载任务
			new DownloadTask().execute();
			break;
		default:
			break;
		}
	}

	class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setTitle("数据下载中");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected Boolean doInBackground(Void...params) {
			try {
				while(true) {
					int downloadPercent = doDownload();
					publishProgress(downloadPercent);
					if (downloadPercent >= 100) {
						break;
					}
				}
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		
		protected int doDownload() {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dlPercent+=10;
			return dlPercent;
		}
		
		@Override
		protected void onProgressUpdate(Integer...values) {
			progressDialog.setMessage("已下载 " + values[0] + "% !");
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			if (result) {
				Toast.makeText(MainActivity.this, "下载完成 ^_^", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
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
