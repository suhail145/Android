package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;
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
	HashMap<String,ArrayList<Transaction>> dayTrans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_by_day);
		db = new DbUpdater(this);
		dayTrans = new HashMap<String,ArrayList<Transaction>>();
		
		Iterator<String> dates = db.getTransactionDates().iterator();
		while(dates.hasNext()){
			String date=dates.next();
			ArrayList<Transaction> trans = db.getDayTransactions(date);
			dayTrans.put(date,trans);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_by_day, menu);
		return true;
	}

}
