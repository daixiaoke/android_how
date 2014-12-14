package com.example.uiwidgettest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;

// ���gif����֧��
//import com.example.uiwidgettest.MyGifView; 

public class MainActivity extends Activity implements OnClickListener {

	private Button button;
	
	private EditText editText;
	
	private ImageView imageView;
	
	//private MyGifView gifView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button);
		editText = (EditText) findViewById(R.id.edit_text);
		imageView = (ImageView) findViewById(R.id.image_view);
		// gif
		//gifView = (MyGifView) findViewById(R.id.gif_view);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button:
			// ��ʾ�ı�����������
			//String inputText = editText.getText().toString();
			//Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
			
			// �л�ͼƬ
			imageView.setImageResource(R.drawable.android);
			
			// �л��ɶ���
			//gifView.openRawResource(R.drawable.db);

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
