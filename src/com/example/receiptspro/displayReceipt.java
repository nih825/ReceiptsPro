package com.example.receiptspro;



import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class displayReceipt extends Activity {
	private static String file;
	private database databaseHelper;
	private ArrayList<String> menuChoices=new ArrayList<String>();
	Spinner s;
	//,"Business","Dining","Travel","Entertainment","Medical","Charities","Other"});
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt_view);
		databaseHelper=new database(this);
		databaseHelper.open();
		Bundle extras = getIntent().getExtras(); 
		file = extras.getString("file");
		
		//Initialize Menu
		menuChoices.add("Personal");
		menuChoices.add("Business");
		menuChoices.add("Dining");
		menuChoices.add("Travel");
		menuChoices.add("Entertainment");
		menuChoices.add("Medical");
		menuChoices.add("Charities");
		menuChoices.add("Other");
		ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,menuChoices.toArray(new String[menuChoices.size()]));
		list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		s=(Spinner)findViewById(R.id.expensetype);
		s.setAdapter(list);
		
		
		File imgFile = new  File(file);
		if(imgFile.exists()){

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    ImageView mImageView = (ImageView) findViewById(R.id.receipt);
			mImageView.setImageBitmap(myBitmap);

		}
		else{
			databaseHelper.close();
			finish();
		}
		Button submit= (Button)findViewById(R.id.save);
		submit.setOnClickListener(submitIt);	
	}
   
	
	private OnClickListener submitIt=new OnClickListener() {
		public void onClick(View v) {
			EditText dollarVal=(EditText)findViewById(R.id.dollarValue);
			String dollars=dollarVal.getText().toString();
			Float receiptDollars;
			if(dollars.equals("")){
				receiptDollars=Float.valueOf("-1");
			}
			else{
				receiptDollars=Float.valueOf(dollars); 
			}
			String expenseType=s.getSelectedItem().toString();
			
			long a=databaseHelper.createReceipt(file, receiptDollars,expenseType);
			Toast.makeText(getApplicationContext(), "Receipt Stored", Toast.LENGTH_LONG).show();
			databaseHelper.close();
		    finish();
			}
	};
	
}
