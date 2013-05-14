package com.example.joshuatree;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DisplayPhotoActivity extends Activity {
	
	private static final String TAG = "JoshuaTree";
	
	private ImageView mImageView;
	private EditText mTitleText;
	private EditText mBodyText;
	
    private Long mRowId;
    private PhotosDb mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		Button addButton = (Button)findViewById(R.id.addButton);
		
		Intent intent = getIntent();
		
		mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
		
	    mImageView = (ImageView)findViewById(R.id.imageview);
	    
		// Get the dimensions of the View
	    int targetW = mImageView.getWidth();
	    int targetH = mImageView.getHeight();
		  
		// Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(MainActivity.mCurrentPhotoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
		  
	    // Determine how much to scale down the image
	    int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}
		
	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
		  
	    Bitmap bitmap = BitmapFactory.decodeFile(MainActivity.mCurrentPhotoPath, bmOptions);
	    
	    mImageView.setImageBitmap(bitmap);
	
		
//		Bitmap bitmap = BitmapFactory.decodeFile(MainActivity.mCurrentPhotoPath);
				
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// data would be passed to cloud here?
//				byte[] photo = ImageView; 		// need a function to process image to byte[]
				String title = mTitleText.getText().toString(); 
				String body = mBodyText.getText().toString(); 
				
				if (mRowId == null) {
		            long id = mDbHelper.createNote(title, body); // add photo
		            if (id > 0) {
		                mRowId = id;
		            }
		        } else {
		            mDbHelper.updateNote(mRowId, title, body); // add photo
		        }
				finish();
			}
		});
	}
}