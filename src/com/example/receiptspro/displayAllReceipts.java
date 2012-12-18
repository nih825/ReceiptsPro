package com.example.receiptspro;



import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
 
public class displayAllReceipts extends Activity {
 
	GridView gridView;
	private database databaseHelper;
	private ArrayList<String> expenseTypes=new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);
		databaseHelper=new database(this);
		databaseHelper.open();
		
		gridView = (GridView) findViewById(R.id.gridView1);
		ArrayList<String> paths=fillData();
		databaseHelper.close();
		String path1=paths.get(0);
		gridView.setAdapter(new ImageAdaptor(this,paths,expenseTypes));
 
		/*gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(
				   getApplicationContext(),
				   ((TextView) v.findViewById(R.id.grid_item_label))
				   .getText(), Toast.LENGTH_SHORT).show();
 
			}
		});*/
 
	}
	
	
	
	private ArrayList<String> fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = databaseHelper.fetchAllReceipts();
        ArrayList<String> paths = new ArrayList();
        if(c != null){
        	while(c.moveToNext()){
        		paths.add(c.getString(1));
        		expenseTypes.add(c.getString(3));
        	}
        }
        return paths;
        
    }
 
}