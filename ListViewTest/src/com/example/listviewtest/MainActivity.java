package com.example.listviewtest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/*private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
		"Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
		"Mango1", "Mango2", "Mango3", "Mango4", "Mango5", "Mango6", "Mango7", 
		"Mango8", "Mango9", "Mango10", "Mango11", "Mango12"};
	*/
	
	private List<Fruit> fruitList = new ArrayList<Fruit>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initFruits();
		
		FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
		
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_list_item_1, data);*/
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Fruit fruit = fruitList.get(position);
				Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initFruits() {
		Fruit apple = new Fruit("Apple", R.drawable.apple);
		fruitList.add(apple);
		Fruit banana = new Fruit("Banana", R.drawable.banana);
		fruitList.add(banana);
		Fruit orange = new Fruit("Orange", R.drawable.orange);
		fruitList.add(orange);
		Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon);
		fruitList.add(watermelon);
		Fruit pear = new Fruit("Pear", R.drawable.pear);
		fruitList.add(pear);
		Fruit grapes = new Fruit("Grapes", R.drawable.grapes);
		fruitList.add(grapes);
		Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple);
		fruitList.add(pineapple);
		Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry);
		fruitList.add(strawberry);
		Fruit cherry = new Fruit("Cherry", R.drawable.cherry);
		fruitList.add(cherry);
		Fruit mango = new Fruit("Mango0", R.drawable.mango);
		fruitList.add(mango);
		Fruit mango1 = new Fruit("Mango1", R.drawable.mango);
		fruitList.add(mango1);
		Fruit mango2 = new Fruit("Mango2", R.drawable.mango);
		fruitList.add(mango2);
		Fruit mango3 = new Fruit("Mango3", R.drawable.mango);
		fruitList.add(mango3);
		Fruit mango4 = new Fruit("Mango4", R.drawable.mango);
		fruitList.add(mango4);
		Fruit mango5 = new Fruit("Mango5", R.drawable.mango);
		fruitList.add(mango5);
		Fruit mango6 = new Fruit("Mango6", R.drawable.mango);
		fruitList.add(mango6);
		Fruit mango7 = new Fruit("Mango7", R.drawable.mango);
		fruitList.add(mango7);
		Fruit mango8 = new Fruit("Mango8", R.drawable.mango);
		fruitList.add(mango8);
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
