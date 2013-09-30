package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
	private GestureDetector mDetector;
	static ArrayList<Transaction> totalTrans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_disp);
		db = new DbUpdater(this);
		lvTransDisp = (ExpandableListView) findViewById(R.id.lvTransList);
		dayTrans = new HashMap<String, ArrayList<Transaction>>();
		mDetector = new GestureDetector(this, new MyGestureListener());
		totalTrans = CashBookApplication.getTransArray();
		
		intent = getIntent();
		String view = intent.getStringExtra("view");

		if (view.equals("byDates")) {
			dispHeads = db.getTransactionDates();
			Iterator<String> dates = dispHeads.iterator();
			while (dates.hasNext()) {
				String date = dates.next();
				ArrayList<Transaction> trans = db.getDayTransactions(date);
				totalTrans.addAll(trans);
				dayTrans.put(date, trans);

			}
			adapter = new TransDispAdapter(this,
					R.layout.trans_by_day_trans_row, dayTrans, dispHeads);
		} else {
			dispHeads = db.getHeadersWithTrans();
			Iterator<String> headers = dispHeads.iterator();
			while (headers.hasNext()) {
				String header = headers.next();
				ArrayList<Transaction> trans = db.getHeaderTransactions(header);
				totalTrans.addAll(trans);
				dayTrans.put(header, trans);

			}
			adapter = new TransDispAdapter(this, R.layout.trans_by_header_row,
					dayTrans, dispHeads);
		}
		lvTransDisp.setAdapter(adapter);

//		lvTransDisp.setOnGroupClickListener(new OnGroupClickListener() {
//
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v,
//					int groupPosition, long id) {
//
//				return false;
//			}
//		});
//		lvTransDisp.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//			@Override
//			public void onGroupExpand(int groupPosition) {
//
//			}
//
//		});
//
//		lvTransDisp.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//			@Override
//			public void onGroupCollapse(int groupPosition) {
//
//			}
//		});
		
		lvTransDisp.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				GestureDetector mDetector = new GestureDetector(new MyGestureListener());
				
				return mDetector.onTouchEvent(arg1);
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
		menu.add("To CSV");
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		String view = (String) item.getTitle();
		if (view.equals("List By Headers")) {
			Intent intent = getIntent();
			intent.removeExtra("view");
			intent.putExtra("view", "byHeaders");
			finish();
			System.out.println(intent.getStringExtra("view"));
			startActivity(intent);
		} else if(view.equals("List By Dates")) {
			Intent intent = getIntent();
			intent.removeExtra("view");
			intent.putExtra("view", "byDates");
			finish();
			System.out.println(intent.getStringExtra("view"));
			startActivity(intent);
		} else if(view.equals("To CSV")){
			ConvertToCSV.convert2csv(this,totalTrans);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		System.out.println(event.toString());
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
//		private static final String DEBUG_TAG = "Gestures";
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2,
				float velocityX, float velocityY) {
			// Log.d(DEBUG_TAG, "onFling: " +
			// event1.toString()+event2.toString());

			if (Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
			if(event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
				Intent intent = getIntent();
				intent.removeExtra("view");
				intent.putExtra("view", "byHeaders");
				finish();
			}else if(event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
				Intent intent = getIntent();
				intent.removeExtra("view");
				intent.putExtra("view", "byDates");
				finish();
			}
			return true;
		}

	}

}
