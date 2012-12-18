package com.example.receiptspro;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
 
/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class analyzeData extends Activity
{
 
    private XYPlot mySimpleXYPlot;
    private database databaseHelper;
    private ArrayList<Float> dollarVal;
    private ArrayList<String> expenseType;
    private ArrayList<String>uniqueCategories;
    ArrayList<Float> aggregatedData;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        databaseHelper=new database(this);
		databaseHelper.open();
		fillData();
		int b=dollarVal.size();
		
		aggregateData();
		int a=uniqueCategories.size();
		
        // initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
 
        // Create a couple arrays of y-values to plot:
        Number[] data=aggregatedData.toArray(new Number[aggregatedData.size()]);
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(data),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Expense Report");                             // Set the display title of the series
 
        // same as above
       // XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        BarFormatter series1Format = new BarFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0));                                  // fill color (none)
 
        // add a new series' to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);
 
        // same as above:
      //  mySimpleXYPlot.addSeries(series2,
        //        new BarFormatter(Color.rgb(0, 0, 200), Color.rgb(0, 0, 100)));
 
 
        // reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);
 
        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        mySimpleXYPlot.disableAllMarkup();
    }
	private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = databaseHelper.fetchAllReceipts();
        dollarVal=new ArrayList<Float>();
        expenseType=new ArrayList<String>();
        
        if(c != null){
        	while(c.moveToNext()){
        		dollarVal.add(Float.valueOf(c.getString(2)));
        		expenseType.add(c.getString(3));
        	}
        }
        
        
    }
	private void aggregateData(){
		uniqueCategories = new ArrayList<String>();
		// add elements to al, including duplicates
		HashSet hs = new HashSet();
		hs.addAll(expenseType);
		uniqueCategories.addAll(hs);
		aggregatedData= new ArrayList<Float>(uniqueCategories.size());
		for(int k=0; k<aggregatedData.size();k++){
			aggregatedData.set(k,Float.valueOf("0"));
		}
		
		
		for(int i=0;i<dollarVal.size();i++){
			for(int j=0;j<uniqueCategories.size(); j++){
				if(uniqueCategories.get(j).equals(expenseType.get(i))){
					if(dollarVal.get(i)>0)
						aggregatedData.set(j,aggregatedData.get(j)+dollarVal.get(i));
				}
			}
		}
		/*aggregatedData.set(aggregatedData.size(), new Float("0"));
		for(int p=0;p<aggregatedData.size();p++){
			aggregatedData.set(aggregatedData.size(),aggregatedData.get(aggregatedData.size())+aggregatedData.get(p));
		}
		String finalCategory="Total";
		uniqueCategories.set(uniqueCategories.size(),finalCategory);*/
	}
	
	
}
