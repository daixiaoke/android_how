package com.example.playvideotest2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.File;


import android.os.Environment;
//import android.view.LayoutInflater;
import android.view.View;
//import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends Activity implements OnClickListener{

private VideoView videoView;
	
	private Button play;
	private Button pause;
	private Button replay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		replay = (Button) findViewById(R.id.replay);
		videoView = (VideoView) findViewById(R.id.video_view);
		
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		replay.setOnClickListener(this);
		
		initVideoPath();
	}

	public void initVideoPath() {
		try {
			File file;
			if (false) {
				file = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/VID_20140727_092001.mp4");
			} else {
				file = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/VID_20131214_180203.m4v");
			}
			videoView.setVideoPath(file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.play:
			if (!videoView.isPlaying()) {
				videoView.start();
			}
			break;
		case R.id.pause:
			if (videoView.isPlaying()) {
				videoView.pause();
			}
			break;
		case R.id.replay:
			if (videoView.isPlaying()) {
				videoView.resume();
			}
			break;
		default:
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (videoView != null) {
			videoView.suspend();
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
