package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class TransactionByDay extends Activity {

	ExpandableListView lvTransByDay;
	DbUpdater db;
	ArrayList<String> TransDates;
	HashMap<String, ArrayList<Transaction>> dayTrans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_by_day);
		db = new DbUpdater(this);
		lvTransByDay = (ExpandableListView) findViewById(R.id.lvTransByDay);
		dayTrans = new HashMap<String, ArrayList<Transaction>>();
		TransDates = db.getTransactionDates();

		Iterator<String> dates = TransDates.iterator();
		while (dates.hasNext()) {
			String date = dates.next();
			ArrayList<Transaction> trans = db.getDayTransactions(date);
			dayTrans.put(date, trans);

		}
		TransByDayAdapter adapter = new TransByDayAdapter(this, dayTrans,
				TransDates);
		lvTransByDay.setAdapter(adapter);

		lvTransByDay.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				return false;
			}
		});
		lvTransByDay.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {

			}

		});

		lvTransByDay.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_by_day, menu);
		return true;
	}

}
