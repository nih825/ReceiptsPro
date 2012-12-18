package com.example.receiptspro;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static String file;
	
	File mediaFile1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        Button scan=(Button) findViewById(R.id.takepic);
        scan.setOnClickListener(buttonClick);
        Button viewReceipts=(Button) findViewById(R.id.viewreceipts);
        viewReceipts.setOnClickListener(display);
        Button viewBreakdown=(Button)findViewById(R.id.expenses);
        viewBreakdown.setOnClickListener(chartTime);
    }
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "Receipts");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("Receipts", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;    
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	        file=mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg";
	    } 
	    else {
	        return null;
	    }

	    return mediaFile;
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            /*Bitmap bitmap = BitmapFactory.decodeFile(fileUri.toString());
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos); 
	           byte[] byte_img_data = baos.toByteArray(); 
	           ParseFile file =new ParseFile(fileUri.toString(),byte_img_data);*/
	        	
	        	Intent intent = new Intent(MainActivity.this,displayReceipt.class);
	        	intent.putExtra("file",file);
	        	startActivity(intent);
	        	
	        	//file.saveInBackground();
	        
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        	Toast.makeText(this,"No receipt was captured",Toast.LENGTH_LONG).show();
	        } else {
	            // Image capture failed, advise user
	        	Toast.makeText(this, "There was an error capturing the image, please try again", Toast.LENGTH_LONG);
	        }
	    }

	 
	    
	}
	
    private OnClickListener buttonClick=new OnClickListener() {
		public void onClick(View v) {
			
			
			Context c = getApplicationContext();
			
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

		   // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		    //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

		    // start the image capture Intent
		    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		    
			}
		 
	};
	private OnClickListener display=new OnClickListener() {
		public void onClick(View v) {
			
			Intent intent= new Intent(MainActivity.this, displayAllReceipts.class);
	    	  
	    	  startActivity(intent);
		    
			}
		 
	};
	
	private OnClickListener chartTime=new OnClickListener() {
		public void onClick(View v) {
			
			Intent intent= new Intent(MainActivity.this, analyzeData.class);
	    	  
	    	  startActivity(intent);
		    
			}
		 
	};
}