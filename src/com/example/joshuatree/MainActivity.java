package com.example.joshuatree;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

//	private static final String TAG = "JoshuaTree";
	
	private Uri fileUri;
	private Uri mImageUri;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAMERA_REQUEST_CODE = 1337;

	private Bitmap mImageBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void takePhoto(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		
		startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
	}
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	@SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "JoshuaTree");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("JoshuaTree", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				handleSmallCameraPhoto(data);
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Capture Cancelled", Toast.LENGTH_LONG)
	               .show();
			} else {
	            Toast.makeText(this, "Capture failed", Toast.LENGTH_LONG)
	               .show();
			}
		}
	}
	
	private void handleSmallCameraPhoto(Intent intent) {
		Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");
		Intent displayIntent = new Intent(this, DisplayPhotoActivity.class);
		displayIntent.putExtra("BitmapImage", mImageBitmap);
		startActivity(displayIntent);
        }
}

