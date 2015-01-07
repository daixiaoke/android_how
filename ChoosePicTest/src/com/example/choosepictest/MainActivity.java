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
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String LOG_TAG = "MainActivity";
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
			Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
			//Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent2.setType("image/*");
			intent2.putExtra("crop", "true");
			//intent2.putExtra("circleCrop", "true");
			// 若设置了aspectX|Y值，则裁剪时，裁剪框将按此固定比例缩放
			// 未设置它们时，裁剪框可按任意比例缩放
			//intent2.putExtra("aspectX", 2);
			//intent2.putExtra("aspectY", 1);
			// 若设置了outputX|Y，经裁剪后的图片将按此XY像素值进行显示（图像一般会被拉伸或压缩）
			//intent2.putExtra("outputX", 640);
			//intent2.putExtra("outputY", 480);
			//intent2.putExtra("scale", true);
			intent2.putExtra("return-data", false);
			intent2.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			// 系统的裁剪图片默认对图片进行人脸识别，当识别到有人脸时，会按aspectX和aspectY为1来处理
			// 如果想设置成自定义的裁剪比例，需要设置noFaceDetection为true。
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
				// TODO: 图像像素太大时，常常无法打开图片进行裁剪
				Intent intent = new Intent ("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("scale", true);				
				intent.putExtra("aspectX", 4);
				intent.putExtra("aspectY", 3);
				intent.putExtra("outputX", 640);
				intent.putExtra("outputY", 480);
				intent.putExtra("return-data", false);
				intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
				intent.putExtra("noFaceDetection", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,  imageUri);
				startActivityForResult(intent, CROP_PHOTO);
				/*if (data != null)
                {
                    // 没有指定特定存储路径的时候
                    Log.d(LOG_TAG,
                            "data is NOT null, file on default position.");

                    // 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
                    // Image captured and saved to fileUri specified in the
                    // Intent
                    Toast.makeText(this, "Image saved to:\n" + data.getData(),
                            Toast.LENGTH_SHORT).show();

                    if (data.hasExtra("data"))
                    {
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        picture.setImageBitmap(thumbnail);
                    }
                } else {

                    Log.d(LOG_TAG,
                            "data IS null, file saved on target position.");
                    // If there is no thumbnail image data, the image
                    // will have been stored in the target output URI.

                    // Resize the full image to fit in out image view.
                    int width = picture.getWidth();
                    int height = picture.getHeight();

                    BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

                    factoryOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imageUri.getPath(), factoryOptions);

                    int imageWidth = factoryOptions.outWidth;
                    int imageHeight = factoryOptions.outHeight;

                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(imageWidth / width, imageHeight
                            / height);

                    // Decode the image file into a Bitmap sized to fill the
                    // View
                    factoryOptions.inJustDecodeBounds = false;
                    factoryOptions.inSampleSize = scaleFactor;
                    factoryOptions.inPurgeable = true;

                    Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath(),
                            factoryOptions);

                    picture.setImageBitmap(bitmap);
                }*/
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				/*if (data == null) {
					Toast.makeText(this, "intent data is null", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "intent data is not null", Toast.LENGTH_SHORT).show();
				}*/
				try {
					//Bitmap bitmap = decodeUriAsBitmap(imageUri);
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

//添加图片缩放: 验证ok
/*
String picturePath = Environment.getExternalStorageDirectory() + "/output_image3.jpg";
//String picturePath = getRealPathFromURI(imageUri);
System.out.printf("picturePath=%s", picturePath);
BitmapFactory.Options options = new BitmapFactory.Options();
// options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
options.inJustDecodeBounds = true;
Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
int scale = (int)( options.outWidth / (float)300);
if(scale <= 0)
	scale = 1;
options.inSampleSize = scale;
options.inJustDecodeBounds = false;
bitmap = BitmapFactory.decodeFile(picturePath, options);

	public String getRealPathFromURI(Uri contentUri) {
	       String[] proj = { MediaStore.Images.Media.DATA };
	       Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       cursor.moveToFirst();
	       return cursor.getString(column_index);
	   }
	
*/
