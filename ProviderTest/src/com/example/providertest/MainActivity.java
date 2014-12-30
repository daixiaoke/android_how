package com.example.providertest;

//import com.example.databasetest.MainActivity;
//import com.example.databasetest.R;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.widget.Button;
//import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	public static final String CONTENT_DATABASE_PROVIDER = "content://com.example.databasetest.provider/book";
	
	private String newId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button addData = (Button) findViewById(R.id.add_data);
		addData.setOnClickListener(this);
		
		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(this);
		
		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(this);
		
		Button queryData = (Button) findViewById(R.id.query_data);
		queryData.setOnClickListener(this);
		
	}
	
	@Override 
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.add_data:
			Uri uri = Uri.parse(CONTENT_DATABASE_PROVIDER);
			ContentValues values = new ContentValues();
			values.put("name", "A Clash of Kings");
			values.put("author", "George Martin");
			values.put("pages", 1040);
			values.put("price", 22.85);
			Uri newUri = getContentResolver().insert(uri, values);
			newId = newUri.getPathSegments().get(1);
			break;
		case R.id.update_data:
			Uri uri3 = Uri.parse(CONTENT_DATABASE_PROVIDER + "/" + newId);
			ContentValues values2 = new ContentValues();
			values2.put("name", "A Strom of Swords");
			values2.put("pages", 1216);
			values2.put("price", 20.45);
			getContentResolver().update(uri3,  values2, null, null);
			break;
		case R.id.delete_data:
			Uri uri4 = Uri.parse(CONTENT_DATABASE_PROVIDER + "/" + newId);
			getContentResolver().delete(uri4, null, null);
			break;
		case R.id.query_data:
			Uri uri2 = Uri.parse(CONTENT_DATABASE_PROVIDER);
			Cursor cursor = getContentResolver().query(uri2, null, null, null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String author = cursor.getString(cursor.getColumnIndex("author"));
					int pages = cursor.getInt(cursor.getColumnIndex("pages"));
					double price = cursor.getDouble(cursor.getColumnIndex("price"));
					Log.d("MainActivity", "book name is " + name);
					Log.d("MainActivity", "book author is " + author);
					Log.d("MainActivity", "book pages is " + pages);
					Log.d("MainActivity", "book price is " + price);
				}
			}
			cursor.close();
			break;
		default:
			break;	
		}
	}
	
}
