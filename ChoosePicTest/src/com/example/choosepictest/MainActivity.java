package com.example.choosepictest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener{
	
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	private Button takePhoto;
	private ImageView picture;
	private Uri imageUri;

	private Button chooseFromAlbum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		takePhoto = (Button) findViewById(R.id.take_photo);
		picture = (ImageView) findViewById(R.id.picture);
		takePhoto.setOnClickListener(this);
		
		chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
		chooseFromAlbum.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.take_photo:
			File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
			try {
				if (outputImage.exists()) {
					outputImage.delete();
				}
				outputImage.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(outputImage);
			// TODO: ͼ�����ش���80��ʱ�޷���ͼƬ���вü������޸�
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, TAKE_PHOTO);
			break;
		case R.id.choose_from_album:
			File outputImage2 = new File(Environment.getExternalStorageDirectory(), "output_image2.jpg");
			try {
				if (outputImage2.exists()) {
					outputImage2.delete();
				}
				outputImage2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri = Uri.fromFile(outputImage2);
			// �����޷���ͼƬ���вü�
			//Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
			//intent2.setType("image/*");
			//intent2.putExtra("crop", true);
			//intent2.putExtra("scale", true);
			Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent2.setType("image/*");
			intent2.putExtra("crop", "true");
			intent2.putExtra("aspectX", 1);
			intent2.putExtra("aspectY", 1);
			intent2.putExtra("outputX", 1000);
			intent2.putExtra("outputY", 1000);
			intent2.putExtra("scale", true);
			intent2.putExtra("return-data", false);
			intent2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent2.putExtra("noFaceDetection", true);
			intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent2, CROP_PHOTO);
			break;
		default:
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent ("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,  imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				// ���ͼƬ����: ��֤ok
				/*
				String picturePath = Environment.getExternalStorageDirectory() + "/output_image3.jpg";
				//String picturePath = getRealPathFromURI(imageUri);
				System.out.printf("picturePath=%s", picturePath);
				BitmapFactory.Options options = new BitmapFactory.Options();
		        // options ��Ϊtrueʱ���������bitmapû��ͼƬ��ֻ��һЩ�����������Ϣ�����ȽϿ죬��Ϊfalseʱ������ͼƬ
		        options.inJustDecodeBounds = true;
		        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
		        int scale = (int)( options.outWidth / (float)300);
		        if(scale <= 0)
		        	scale = 1;
		        options.inSampleSize = scale;
		        options.inJustDecodeBounds = false;
		        bitmap = BitmapFactory.decodeFile(picturePath, options);
		        */
		        
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));					
					picture.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
	
	public String getRealPathFromURI(Uri contentUri) {
	       String[] proj = { MediaStore.Images.Media.DATA };
	       Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       cursor.moveToFirst();
	       return cursor.getString(column_index);
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
