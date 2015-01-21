package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	public static final int SHOW_RESPONSE = 0;
	
	private Button sendRequest;
	
	private TextView responseText;
	
	private static final String BAIDU_URL = "http://www.baidu.com";
	//笔记本用无线，和手机处于同一局域网
	private static final String LOCAL_SERVER_XML_FILE = "http://192.168.0.133:8080/get_data.xml";
	private static final String LOCAL_SERVER_JSON_FILE = "http://192.168.0.133:8080/get_data.json";
	
	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				responseText.setText(response);
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendRequest = (Button)findViewById(R.id.send_request);
		responseText = (TextView)findViewById(R.id.response);
		sendRequest.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.send_request){
			// HttpURLConnection 发起http请求
			//sendRequestWithHttpURLConnection();
			
			// HttpClient 发起Http请求
			sendRequestWithHttpClient();
		}
	}
	
	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 请求本地服务器上的xml文件
					HttpClient httpclient = new DefaultHttpClient();
					// 访问局域网内本地服务器
					//HttpGet httpGet = new HttpGet(LOCAL_SERVER_XML_FILE);
					HttpGet httpGet = new HttpGet(LOCAL_SERVER_JSON_FILE);
					HttpResponse httpResponse = httpclient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						// Pull解析XML
						//parseXMLWithPull(response);
						
						// SAX解析XML
						//parseXMLWithSAX(response);
						
						// JSONObject解析JSON
						//parseJSONWithJSONObject(response);
						
						// GSON解析JSON
						parseJSONWithGSON(response);
					}
					
					// 请求百度首页
					/*
					HttpClient httpClient = new DefaultHttpClient();
					//HttpGet httpGet = new HttpGet(BAIDU_URL);
					HttpGet httpGet = new HttpGet("http://www.baidu.com");
					// 发起http请求
					HttpResponse httpResponse = httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						// 请求和响应都成功了
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						
						// 通过Message发送响应内容给主线程
						Message message = new Message();
						message.what = SHOW_RESPONSE;
						message.obj = response.toString();
						handler.sendMessage(message);
					}
					*/
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void parseJSONWithJSONObject(String jsonData) {
		try {
			JSONArray jsonArray = new JSONArray(jsonData);
			for (int i=0; i<jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String id = jsonObject.getString("id");
				String name = jsonObject.getString("name");
				String version = jsonObject.getString("version");
				Log.d("MainActivity", "id is " + id);
				Log.d("MainActivity", "name is " + name);
				Log.d("MainActivity", "version is " + version);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseJSONWithGSON(String jsonData) {
		Gson gson = new Gson();
		List<app> appList = gson.fromJson(jsonData,  new TypeToken<List<app>>() {}.getType());
		for (app a : appList) {
			Log.d("MainActivity", "id is " + a.getId());
			Log.d("MainActivity", "name is " + a.getName());
			Log.d("MainActivity", "version is " + a.getVersion());
		}
	}
	
	private void parseXMLWithPull(String xmlData) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xmlData));
			int eventType = parser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while(eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = parser.getName();
				switch(eventType) {
				// 开始解析某个结点
				case XmlPullParser.START_TAG:
					if ("id".equals(nodeName)) {
						id = parser.nextText();
					} else if ("name".equals(nodeName)) {
						name = parser.nextText();
					} else if ("version".equals(nodeName)) {
						version = parser.nextText();
					}
					break;
				// 完成解析某个结点
				case XmlPullParser.END_TAG:
					if ("app".equals(nodeName)) {
						Log.d("MainActivity", "id is " + id);
						Log.d("MainActivity", "name is " + name);
						Log.d("MainActivity", "version is " + version);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseXMLWithSAX(String xmlData) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			ContentHandler handler = new ContentHandler();
			//将ContentHandler的实例设置到XMLReader中
			xmlReader.setContentHandler(handler);
			//开始解析
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendRequestWithHttpURLConnection() {
		// 开启线程来发起网络请求
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					// Http 请求
					URL url = new URL(BAIDU_URL);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					
					// 获取http请求响应
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line=reader.readLine()) != null) {
						response.append(line);
					}
					
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					// 将http响应存放到Message中
					message.obj = response.toString();
					handler.sendMessage(message);
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
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
