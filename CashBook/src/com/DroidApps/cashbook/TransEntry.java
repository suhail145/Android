package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TransEntry extends Activity {

	public static final String TAG = TransEntry.class.getSimpleName();
	Context context;
	Transaction trans;
	EditText editAmt, editDesc;
	TextView tvDate; // , tvTime;
	Button buttonAddTrans, buttonAddHeader;
	Spinner headerList;
	String[] dateTime;
	Date dt;

	DbUpdater db;
	ArrayList<String> headers;
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_entry);
		context = this;

		db = new DbUpdater(this);

		tvDate = (TextView) findViewById(R.id.tvTransDate);
		// tvTime = (TextView) findViewById(R.id.tvTransTime);
		editAmt = (EditText) findViewById(R.id.editTransAmnt);
		editDesc = (EditText) findViewById(R.id.editTransDesc);

		trans = new Transaction();
		dt = trans.getDate();

		buttonAddHeader = (Button) findViewById(R.id.buttonTransAddHeader);
		buttonAddTrans = (Button) findViewById(R.id.buttonTransAdd);

		headerList = (Spinner) findViewById(R.id.spinTransHeadList);

		Log.d(TAG, "Starting TransEntry");
		updateSpinner();

		tvDate.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP) {

					final Dialog datePicker = new Dialog(context);
					datePicker.setContentView(R.layout.date_picker_popup);
					datePicker.setCancelable(true);
					datePicker.setCanceledOnTouchOutside(true);
					datePicker.setTitle(R.string.TitleDatePickerDialog);

					// Define the components of the date picker view so as to
					// use
					// them

					TextView date, time;
					final DatePicker datePick;
					final TimePicker timePick;
					Button submit;

					date = (TextView) datePicker.findViewById(R.id.tvDatePopup);
					time = (TextView) datePicker.findViewById(R.id.tvTimePopup);
					datePick = (DatePicker) datePicker
							.findViewById(R.id.datePick);
					timePick = (TimePicker) datePicker
							.findViewById(R.id.timePick);
					submit = (Button) datePicker
							.findViewById(R.id.buttonDatePick);

					String[] dt = trans.getDayString().split(" ");

					date.setText(dt[0]);
					time.setText(dt[1]+dt[2]);

					timePick.setCurrentHour(trans.getDate().getHours());
					timePick.setCurrentMinute(trans.getDate().getMinutes());
					timePick.setIs24HourView(true);

					submit.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Date dt = new Date();
							int year = 0, month = 0, day = 0, hour = 0, min = 0;
							year = datePick.getYear();
							month = datePick.getMonth();
							day = datePick.getDayOfMonth();

							hour = timePick.getCurrentHour();
							min = timePick.getCurrentMinute();

							Log.d(TAG, "Time logged : " + day + "-" + month
									+ "-" + year + " " + hour + ":" + min);
							dt.setDate(day);
							dt.setMonth(month);
							dt.setYear(year - 1900);
							dt.setHours(hour);
							dt.setMinutes(min);

							Date today = new Date();
							// Calendar today = Calendar.getInstance();
							Log.d(TAG, "Date now is " + today.toString());
							if (dt.after(today)) {
								Toast.makeText(context,
										R.string.warningFutureDate,
										Toast.LENGTH_SHORT).show();
								// modDate.setText(R.string.buttonNow);
							} else {
								trans.setDate(dt);
								Log.d(TAG,
										"Date set is " + trans.getDayString());
								tvDate.setText(trans.getDayString());
								// modDate.setText(R.string.buttonDatePickerDateSet);
								datePicker.dismiss();
							}
						}

					});
					datePicker.show();

				}

				else if (event.getAction() == MotionEvent.ACTION_MOVE) {

					Toast.makeText(
							context,
							"Your moving your finger! trying something more useful! :P",
							Toast.LENGTH_SHORT).show();
				}
				return true;
			}

		});

		buttonAddHeader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HeaderEntry.class);
				intent.setAction("HEADER_ENTRY");

				Log.d(TAG, "Leaving TransEntry");
				startActivityForResult(intent, 0);

				Log.d(TAG, "returned back tp TransEntry");
				updateSpinner();

			}
		});

		buttonAddTrans.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String header = (String) headerList.getSelectedItem();
				if (header.equals("No Headers found, please add some")) {
					Toast.makeText(context, header, Toast.LENGTH_SHORT).show();
					return;
				}
				Date date = trans.getDate();
				String desc = editDesc.getText().toString();
				Float amt = trans.getAmount();
				if (!editAmt.getText().toString().equals(""))
					amt = Float.valueOf(editAmt.getText().toString());
				trans.updateTransaction(date, header, amt, desc);
				Log.d(TAG, trans.toString());
				Toast.makeText(context, trans.toString(), Toast.LENGTH_SHORT)
						.show();

				db.addTransaction(trans);

				// after the DB entry, reset the transaction
				trans = null;
				trans = new Transaction();
				// dateTime = trans.getDayString().split(" ");
				tvDate.setText(trans.getDayString());

				// tvTime.setText(dateTime[1]);
			}

		});

		// dateTime = trans.getDayString().split(" ");
		tvDate.setText(trans.getDayString());

		// tvTime.setText(dateTime[1]);

		Log.d(TAG, trans.getDayString());
		Toast.makeText(context, trans.getDayString(), Toast.LENGTH_SHORT)
				.show();

	}

	private void updateSpinner() {

		// headers = null;
		headers = db.getHeaders();
		// if(headers.isEmpty())
		// headers.add("No Headers present, please add some!");
		adapter = null;
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, headers);

		headerList.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_trans_entry, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateSpinner();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		db.close();
	}

}
