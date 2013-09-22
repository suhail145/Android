package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class TransDisp extends Activity {

	ExpandableListView lvTransDisp;
	DbUpdater db;
	static ArrayList<String> dispHeads;
	HashMap<String, ArrayList<Transaction>> dayTrans;
	TransDispAdapter adapter = null;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_disp);
		db = new DbUpdater(this);
		lvTransDisp = (ExpandableListView) findViewById(R.id.lvTransList);
		dayTrans = new HashMap<String, ArrayList<Transaction>>();
		
		intent = getIntent();
		String view = intent.getStringExtra("view");
		
		if (view.equals("byDates")) {
			dispHeads = db.getTransactionDates();
			Iterator<String> dates = dispHeads.iterator();
			while (dates.hasNext()) {
				String date = dates.next();
				ArrayList<Transaction> trans = db.getDayTransactions(date);
				dayTrans.put(date, trans);

			}
			adapter = new TransDispAdapter(this,
					R.layout.trans_by_day_trans_row, dayTrans, dispHeads);
		}else {
			dispHeads = db.getHeadersWithTrans();
			Iterator<String> headers = dispHeads.iterator();
			while (headers.hasNext()) {
				String header = headers.next();
				ArrayList<Transaction> trans = db.getHeaderTransactions(header);
				dayTrans.put(header, trans);

			}
			adapter = new TransDispAdapter(this,
					R.layout.trans_by_header_row, dayTrans, dispHeads);
		}
		lvTransDisp.setAdapter(adapter);

		lvTransDisp.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				return false;
			}
		});
		lvTransDisp.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {

			}

		});

		lvTransDisp.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		menu.clear();
		if (intent.getStringExtra("view").equals("byDates"))
			menu.add("List By Headers");
		else
			menu.add("List By Dates");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		String view=(String) item.getTitle();
		if(view.equals("List By Headers")){
			Intent intent = getIntent();
			intent.removeExtra("view");
			intent.putExtra("view","byHeaders");
			finish();
			System.out.println(intent.getStringExtra("view"));
			startActivity(intent);
		}else {
			Intent intent = getIntent();
			intent.removeExtra("view");
			intent.putExtra("view","byDates");
			finish();
			System.out.println(intent.getStringExtra("view"));
			startActivity(intent);
		}
			
		return super.onOptionsItemSelected(item);
	}

	

}
