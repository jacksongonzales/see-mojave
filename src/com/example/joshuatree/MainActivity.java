package com.example.joshuatree;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;

public class MainActivity extends Activity {

//	private static final String TAG = "JoshuaTree";
	
	private Uri fileUri;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAMERA_REQUEST_CODE = 1337;

	static String mCurrentPhotoPath;
	
//	private static LocationManager mLocationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LocationManager mLocationManager =
		        (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		LocationProvider provider =
		        mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onStart() {
	    super.onStart();

	    // This verification should be done during onStart() because the system calls
	    // this method when the user returns to the activity, which ensures the desired
	    // location provider is enabled each time the activity resumes from the stopped state.
	    LocationManager locationManager =
	            (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	    if (!gpsEnabled) {
	        // Do a Toast here asking to enable location grabbing
	    	// Maybe this
	    	// if hitOkay {
	    	enableLocationSettings();
	    	// }
	    }
	}

	private void enableLocationSettings() {
	    Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    startActivity(settingsIntent);
	}
	
	LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
	      // Called when a new location is found by the network location provider.
//	      makeUseOfNewLocation(location);
	    }

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
	};
	    
	
	public void takePhoto(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		
//		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
	}
	
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	@SuppressLint("SimpleDateFormat")
	private static File getOutputMediaFile(int type) {
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
	    if (type == MEDIA_TYPE_IMAGE) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }
	    mCurrentPhotoPath = mediaFile.getAbsolutePath();
	    return mediaFile;
	}

	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				galleryAddPic();
				handleSmallCameraPhoto(data);
//				mLocationManager.removeUpdates(locationListener); 
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
//		Bundle extras = intent.getExtras();
//		mImageBitmap = (Bitmap) extras.get("data");
//		mCurrentPhotoPath.toString(); 
		Intent displayIntent = new Intent(this, DisplayPhotoActivity.class);
//		displayIntent.putExtra("File Path", mCurrentPhotoPath);
		startActivity(displayIntent);
        }
}
	

