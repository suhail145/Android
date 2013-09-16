package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class QuickEntry extends Activity {

	public static final String TAG = QuickEntry.class.getSimpleName();
	ListView list;
	TransactionAdapter adapter;
	ArrayList<Transaction> trans;
	DbUpdater db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quick_view_entry);
		db = new DbUpdater(this);

		list = (ListView) findViewById(R.id.lvQTrans);

		// Log.d(TAG,
		// "Transaction list recieved in QuickEntry class has the following entries:");
		trans = db.getQuickTrans();
		// Iterator<Transaction> iterator = trans.iterator();
		// while(iterator.hasNext()){
		// Transaction trans = (Transaction) iterator.next();
		// Log.d(TAG,trans.toString());
		// }
		adapter = new TransactionAdapter(this, R.layout.activity_quick_entry,
				trans, db);
		list.setAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		db.close();
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_quick_entry, menu);
		return true;
	}*/
}
