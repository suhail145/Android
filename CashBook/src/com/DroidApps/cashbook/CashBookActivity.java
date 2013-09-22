package com.DroidApps.cashbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CashBookActivity extends Activity {

	public static final String TAG = CashBookActivity.class.getSimpleName();
	Button cbHeaderEntry, cbHeaderDisplay, cbTransEntry, cbQuickEntry, cbGrid, cbDBTrans,cbDispTrans;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_book);
		context = this;

		cbHeaderEntry = (Button) findViewById(R.id.buttonMainHeaderEntry);
		cbHeaderDisplay = (Button) findViewById(R.id.buttonMainHeaderDisplay);
		cbTransEntry = (Button) findViewById(R.id.buttonMainTransEntry);
		cbQuickEntry = (Button) findViewById(R.id.buttonMainQuick);
		cbGrid = (Button) findViewById(R.id.buttonGrid);
		cbDBTrans= (Button) findViewById(R.id.buttonDBTrans);
		cbDispTrans=(Button) findViewById(R.id.buttonTransDisplay);

		cbDispTrans.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, TransDisp.class);
				intent.putExtra("view", "byHeaders");
				// intent.setAction("HEADER_ENTRY");
				startActivity(intent);

			}

		});

		cbDBTrans.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, TransactionTest.class);
				// intent.setAction("HEADER_ENTRY");
				startActivity(intent);

			}

		});
		
		cbGrid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, TransGrid.class);
				// intent.setAction("HEADER_ENTRY");
				startActivity(intent);

			}

		});

		cbHeaderEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, HeaderEntry.class);
				intent.setAction("HEADER_ENTRY");
				startActivity(intent);

			}

		});

		cbHeaderDisplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HeaderDisplay.class);
				startActivity(intent);

			}

		});

		cbTransEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, TransEntry.class);
				startActivity(intent);

			}

		});

		cbQuickEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, QuickEntry.class);
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_cash_book, menu);
		return true;
	}
}
