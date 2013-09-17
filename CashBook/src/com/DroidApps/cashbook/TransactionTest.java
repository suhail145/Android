package com.DroidApps.cashbook;

import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TransactionTest extends Activity {

	public static final String TAG = TransactionTest.class.getSimpleName();
	DbUpdater db;
	TextView tv;
	Button button1, button2, button3;
	String textV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_view);

		db = new DbUpdater(this);
		tv = (TextView) findViewById(R.id.testViewText);
		button1 = (Button) findViewById(R.id.btTextViewHeaders);

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				textV = "";
				Iterator<String> it = db.getHeaders().iterator();
				System.out.println(it.hasNext());
				while (it.hasNext()) {
					textV = textV.concat(it.next() + "\n");
					System.out.println(textV);
				}
				if (textV.isEmpty()) {
					tv.setText("No headers to display");
					System.out.println(textV);
				} else {
					tv.setText(textV);
				}

			}

		});

		button2 = (Button) findViewById(R.id.btTextViewTransactions);

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				textV = "";
				if (db.getAllTransactions() != null) {
					Iterator<Transaction> it = db.getAllTransactions()
							.iterator();
					System.out.println(it.hasNext());
					while (it.hasNext()) {

						Transaction trans = it.next();
						textV = textV.concat(trans.getHeader() + "\t"
								+ Float.toString(trans.getAmount()) + "\n");
						System.out.println(textV);
					}
					if (textV.isEmpty()) {
						tv.setText("No transactions to display");
						System.out.println(textV);
					} else {
						tv.setText(textV);
					}
				} else {
					tv.setText("No transactions found!");
				}

			}

		});

		button3 = (Button) findViewById(R.id.btTextViewTransByDay);

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				textV = "";
				if (db.getTransactionDates() != null) {
					Iterator<String> itDates = db.getTransactionDates()
							.iterator();
					// System.out.println(itDates.hasNext());
					while (itDates.hasNext()) {

						String date = itDates.next();
						textV = textV.concat(date+":\n");
						if (db.getDayTransactions(date) != null) {
							Iterator<Transaction> itTrans = db
									.getDayTransactions(date).iterator();
							while (itTrans.hasNext()) {
								Transaction trans = itTrans.next();
								textV = textV.concat(trans.getHeader() + "\t"
										+ Float.toString(trans.getAmount())
										+ "\n");
								System.out.println(textV);
							}
						}

					}
					if (textV.isEmpty()) {
						tv.setText("No transactions to display");
						System.out.println(textV);
					} else {
						tv.setText(textV);
					}
				} else {
					tv.setText("No transactions found!");
				}

			}

		});

	}

}
