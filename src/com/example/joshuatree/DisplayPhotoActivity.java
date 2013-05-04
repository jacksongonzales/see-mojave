package com.example.joshuatree;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DisplayPhotoActivity extends Activity {
	
	private static final String TAG = "JoshuaTree";
	
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		Button addButton = (Button)findViewById(R.id.addButton);
		
		Intent displayIntent = getIntent();
		Bitmap bitmap = (Bitmap) displayIntent.getParcelableExtra("BitmapImage");
		
		mImageView = (ImageView)findViewById(R.id.imageview);
		mImageView.setImageBitmap(bitmap); 
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// data would be passed to cloud here?
				finish();
			}
		});
	}
}