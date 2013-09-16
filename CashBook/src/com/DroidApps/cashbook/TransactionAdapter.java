package com.DroidApps.cashbook;

import java.util.Date;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

	public static final String TAG = TransactionAdapter.class.getSimpleName();
	private int rId;
	private Context context;
	float amount = 0;
	DbUpdater db;

	public TransactionAdapter(Context context, int resourceId,
			List<Transaction> transactions, DbUpdater db) {
		super(context, resourceId, transactions);
		this.rId = resourceId;
		this.context = context;
		this.db = db;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout listItem;
		// db = new DbUpdater(context);
		final Transaction trans = getItem(position);

		String header = trans.getHeader();
		amount = trans.getAmount();
		if (convertView == null) {
			listItem = new LinearLayout(getContext());
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(rId, listItem, true);
		} else {
			listItem = (LinearLayout) convertView;
		}

		TextView tvHeader = (TextView) listItem.findViewById(R.id.tvQTrans);
		tvHeader.setText(header);
		tvHeader.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {

				final Dialog descDialog = new Dialog(context);
				descDialog.setContentView(R.layout.trans_desc_popup);
				descDialog.setCancelable(true);
				descDialog.setCanceledOnTouchOutside(true);
				descDialog.setTitle(R.string.titlePopupTransDesc);

				final EditText editDesc = (EditText) descDialog
						.findViewById(R.id.editPopupTransDesc);
				if (!trans.getDescription().equals(""))
					editDesc.setText(trans.getDescription());

				Button ok = (Button) descDialog
						.findViewById(R.id.buttonTransDescOK);
				Button cancel = (Button) descDialog
						.findViewById(R.id.buttonTransDescCancel);

				ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						trans.setDesc(editDesc.getText().toString());
						descDialog.dismiss();
					}

				});

				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						descDialog.dismiss();
					}

				});

				descDialog.show();

				return true;
			}

		});
		final EditText editAmt = (EditText) listItem.findViewById(R.id.etQAmt);
		editAmt.setText(Float.toString(amount));
		Button addTrans;
		final Button modDate;
		addTrans = (Button) listItem.findViewById(R.id.buttonQTransAdd);
		modDate = (Button) listItem.findViewById(R.id.buttonQNow);

		addTrans.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String amt = editAmt.getText().toString();
				if (!amt.equals(""))
					amount = Float.valueOf(amt);
				else {
					Toast.makeText(context, "Amount cannot be 0",
							Toast.LENGTH_SHORT).show();
					return;
				}
				trans.setAmount(amount);
				db.addTransaction(trans);

			}

		});

		modDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				final Dialog datePicker = new Dialog(getContext());
				datePicker.setContentView(R.layout.date_picker_popup);
				datePicker.setCancelable(true);
				datePicker.setCanceledOnTouchOutside(true);
				datePicker.setTitle(R.string.TitleDatePickerDialog);

				// Define the components of the date picker view so as to use
				// them

				TextView date, time;
				final DatePicker datePick;
				final TimePicker timePick;
				Button submit;

				date = (TextView) datePicker.findViewById(R.id.tvDatePopup);
				time = (TextView) datePicker.findViewById(R.id.tvTimePopup);
				datePick = (DatePicker) datePicker.findViewById(R.id.datePick);
				timePick = (TimePicker) datePicker.findViewById(R.id.timePick);
				submit = (Button) datePicker.findViewById(R.id.buttonDatePick);

				String[] dt = trans.getDateString().split(" ");

				date.setText(dt[0]);
				time.setText(dt[1]);

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

						Log.d(TAG, "Time logged : " + day + "-" + month + "-"
								+ year + " " + hour + ":" + min);
						dt.setDate(day);
						dt.setMonth(month);
						dt.setYear(year - 1900);
						dt.setHours(hour);
						dt.setMinutes(min);

						Date today = new Date();
						// Calendar today = Calendar.getInstance();
						Log.d(TAG, "Date now is " + today.toString());
						if (dt.after(today)) {
							Toast.makeText(context, R.string.warningFutureDate,
									Toast.LENGTH_SHORT).show();
							modDate.setText(R.string.buttonNow);
						} else {
							trans.setDate(dt);
							Log.d(TAG, "Date set is " + trans.getDateString());
							modDate.setText(R.string.buttonDatePickerDateSet);
							datePicker.dismiss();
						}
					}

				});
				datePicker.show();

			}

		});

		return listItem;
	}

}
