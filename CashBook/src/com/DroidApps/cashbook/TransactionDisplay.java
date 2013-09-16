package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionDisplay extends Activity {

	public static final String TAG = TransactionDisplay.class.getSimpleName();
	DbUpdater db;
	ListView lvTransDisp;
	TextView date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_display);
		db = new DbUpdater(this);
		date = new TextView(this);
		date.setBackgroundColor(getResources().getColor(R.color.Azure));
		date.setText("Date");
		ArrayList<Transaction> array = db.getAllTransactions();
		if (array != null) {
			lvTransDisp = (ListView) findViewById(R.id.lvTransDisplay);
			TransAdapter ad = new TransAdapter(this, array);
			lvTransDisp.setFastScrollEnabled(true);
			lvTransDisp.addHeaderView(date);
			lvTransDisp.setAdapter(ad);
		} else {
			Toast.makeText(this, "No transactions to display! Add some",
					Toast.LENGTH_SHORT).show();
			finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_display, menu);
		return true;
	}

}
