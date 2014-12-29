package com.example.databasetest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class MainActivity extends Activity implements OnClickListener{

	private MyDatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
		Button createDatabase = (Button) findViewById(R.id.create_database);
		createDatabase.setOnClickListener(this);
		
		Button addData = (Button) findViewById(R.id.add_data);
		addData.setOnClickListener(this);
		
		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(this);
		
		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(this);
		
		Button queryData = (Button) findViewById(R.id.query_data);
		queryData.setOnClickListener(this);
		
		Button replaceData = (Button) findViewById(R.id.replace_data);
		replaceData.setOnClickListener(this);
	}

	@Override 
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.create_database:
			dbHelper.getWritableDatabase();
			break;
		case R.id.add_data:
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", "The Da Vinci Code");
			values.put("author", "Dan Brown");
			values.put("pages", 454);
			values.put("price", 16.96);
			db.insert("Book", null, values);	
			values.clear();
			values.put("name", "The Lost Symbol");
			values.put("author", "Dan Brown");
			values.put("pages", 510);
			values.put("price", 19.35);
			db.insert("Book", null, values);
			Toast.makeText(MainActivity.this, "Add two items succeeded",  Toast.LENGTH_SHORT).show();
			break;
		case R.id.update_data:
			SQLiteDatabase db2 = dbHelper.getWritableDatabase();
			ContentValues values2 = new ContentValues();
			values2.put("price", 10.99);
			db2.update("Book",  values2, "name=?", new String[] {"The Da Vinci Code"});
			Toast.makeText(MainActivity.this, "Update price succeeded",  Toast.LENGTH_SHORT).show();
			break;
		case R.id.delete_data:
			SQLiteDatabase db3 = dbHelper.getWritableDatabase();
			
			db3.delete("Book", "pages>?", new String[] {"500"});
			Toast.makeText(MainActivity.this, "Delete items whose page is larger than 500",  Toast.LENGTH_SHORT).show();
			break;
		case R.id.query_data:
			SQLiteDatabase db4 = dbHelper.getWritableDatabase();
			Cursor cursor = db4.query("Book", null, null, null, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String author = cursor.getString(cursor.getColumnIndex("author"));
					int pages = cursor.getInt(cursor.getColumnIndex("pages"));
					double price = cursor.getDouble(cursor.getColumnIndex("price"));
					Log.d("MainActivity", "book name is " + name);
					Log.d("MainActivity", "book author is " + author);
					Log.d("MainActivity", "book price is " + price);
					Log.d("MainActivity", "book pages is " + pages);
				} while (cursor.moveToNext());
			}
			cursor.close();
			break;
		case R.id.replace_data:
			SQLiteDatabase db5 = dbHelper.getWritableDatabase();
			db5.beginTransaction();
			try {
				db5.delete("Book", null, null);
				/*if (true) {
					Thread.sleep(5000);
					throw new NullPointerException();
				}*/
			
				ContentValues values3 = new ContentValues();
				values3.put("name", "Game of Thrones");
				values3.put("author", "George Martin");
				values3.put("pages", 720);
				values3.put("price", 20.85);
				db5.insert("Book", null, values3);
				db5.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db5.endTransaction();
			}
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