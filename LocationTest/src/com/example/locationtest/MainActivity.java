package com.example.locationtest;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView positionTextView;
	
	private LocationManager locationManager;
	
	private String provider;	
	
	public static final int SHOW_LOCATION = 0;
	
	private static final String GOOGLE_GEOCODE = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		positionTextView = (TextView)findViewById(R.id.position_text_view);
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		// 获取所有可用的位置提供器
		List<String>providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER; 
		} else {
			// 当前没有可用的位置提供器，弹出Toast提示用户
			Toast.makeText(this,  "没有开启位置服务", Toast.LENGTH_SHORT).show();
			return;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			// 显示当前设备的位置信息
			showLocation(location);
		}
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}

	protected void onDestroy() {
		super.onDestroy();
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}
	
	LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// 更新当前设备的位置信息
			showLocation(location);
		}
	};
	
	public void showLocation(final Location location) {
		/*String currentPosition = "已定位到您所在位置！ \n"
				+ "当前维度： " + location.getLatitude() + "\n"
				+ "当前经度： " + location.getLongitude();
		positionTextView.setText(currentPosition);*/
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 组装反向地理编码的接口地址
					StringBuilder url = new StringBuilder();
					url.append(GOOGLE_GEOCODE);
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude());
					url.append("&sensor=false");
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url.toString());
					//在请求消息头中指定语言，保证服务器会返回中文数据
					httpGet.addHeader("Accept-Language", "zh-CN");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						JSONObject jsonObject = new JSONObject(response);
						// 获取results节点下的位置信息
						JSONArray resultArray = jsonObject.getJSONArray("results");
						if (resultArray.length() > 0) {
							JSONObject subObject = resultArray.getJSONObject(0);
							// 取出格式化后的位置信息
							String address = subObject.getString("formatted_address");
							Message message = new Message();
							message.what = SHOW_LOCATION;
							message.obj = address;
							handler.sendMessage(message);
						}
					}
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_LOCATION:
				String currentPosition = (String)msg.obj;
				positionTextView.setText(currentPosition);
				break;
			default:
				break;
			}
		}
	};
	
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
