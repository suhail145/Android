package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TransactionByDay extends Activity {
	
	ListView lvTransByDay,lvTrans;
	DbUpdater db;
	ArrayList<View> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_by_day);
		db = new DbUpdater(this);
		views = new ArrayList<View>();
		lvTransByDay = (ListView) findViewById(R.id.lvTransByDay);
		Iterator<String> dates = db.getTransactionDates().iterator();
		while(dates.hasNext()){
			String date=dates.next();
			ArrayList<Transaction> trans = db.getDayTransactions(date);
			lvTrans=new ListView(this);
			TransAdapter transAd = new TransAdapter(this, trans);
			TextView dateView = new TextView(this);
			dateView.setBackgroundColor(getResources().getColor(R.color.Azure));
			dateView.setText(date);
			lvTrans.addHeaderView(dateView);
			lvTrans.setAdapter(transAd);
			views.add(lvTrans);
		}
		System.out.println("Number of Views:"+views.size());
		TransByDayAdapter adapter = new TransByDayAdapter(this, views);
		lvTransByDay.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_by_day, menu);
		return true;
	}

}
