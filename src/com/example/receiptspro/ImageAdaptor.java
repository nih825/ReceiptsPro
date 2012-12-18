package com.example.receiptspro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdaptor extends BaseAdapter {
	private Context context;
	private final ArrayList<String> paths;
	private final ArrayList<String> expenseTypes;
	private final int IMAGE_MAX_SIZE=300;
	
	public ImageAdaptor(Context context,ArrayList<String> paths,ArrayList<String> expenseTypes) {
		this.context = context;
		this.paths = paths;
		this.expenseTypes=expenseTypes;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			//gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.images, null);
			String path=paths.get(position);
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(path.substring(path.length()-15,path.length()-13)+"/"+path.substring(path.length()-13,path.length()-11)+"/"+path.substring(path.length()-19,path.length()-15)+"\n"+expenseTypes.get(position));
 
			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
			
			
			File imgFile = new  File(path);
			if(imgFile.exists()){
				
				Bitmap myBitmap=decodeFile(imgFile);
			    //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				imageView.setImageBitmap(myBitmap);

			}

		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return paths.size();
	}
 
	@Override
	public Object getItem(int position) {
		return paths.get(position);
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	private Bitmap decodeFile(File f){
	    Bitmap b = null;
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;

	        FileInputStream fis = new FileInputStream(f);
	        BitmapFactory.decodeStream(fis, null, o);
	        fis.close();

	        int scale = 1;
	        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
	            scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
	        }

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        fis = new FileInputStream(f);
	        b = BitmapFactory.decodeStream(fis, null, o2);
	        fis.close();
	    } catch (IOException e) {
	    }
	    return b;
	}
 
}