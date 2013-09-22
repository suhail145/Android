package com.DroidApps.cashbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.util.Log;

public class Transaction {

	public static final String TAG = Transaction.class.getSimpleName();
	private Date dt = new Date();
	private String header = "";
	private Float amount = (float) 0;
	private String description = "";
	private boolean isNull = true;
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd h:mm a", Locale.US);
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("dd-MM-yy");
//	private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

	public Transaction() {

		isNull = true;
	}

	public Transaction(Date d, String head, Float amt, String desc) {
		this.dt = d;
		this.header = head;
		this.amount = amt;
		if (desc != null)
			this.description = desc;

		isNull = false;
	}

	public void updateTransaction(Date d, String head, Float amt, String desc) {
		if (d != null)
			this.dt = d;
		if (head != null)
			this.header = head;
		if (amt != null)
			this.amount = amt;
		if (desc != null)
			this.description = desc;

		if (dt != null && header != null && amount != null) {
			isNull = false;
		}
	}

	public void updateTransaction(String d, String head, Float amt, String desc) {
		if (d != null) {
			try {
				this.dt = dateFormat.parse(d);
			} catch (ParseException e) {
				Log.d(TAG, "Bad date string, date nul for this transaction");
			}
		}
		if (head != null)
			this.header = head;
		if (amt != null)
			this.amount = amt;
		if (desc != null)
			this.description = desc;

		if (dt != null && header != null && amount != null) {
			isNull = false;
		}
	}

	public void updateTransaction(String head, Float amt, String desc) {

		if (head != null)
			this.header = head;
		if (amt != null)
			this.amount = amt;
		if (desc != null)
			this.description = desc;

		if (header != null && amount != null) {
			isNull = false;
		}
	}

	public void setAmount(float amt) {
		this.amount = amt;
	}

	public void setDate(Date date) {
		this.dt = date;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

	public ContentValues getValue() {
		ContentValues values = new ContentValues();
		String date = dateFormat.format(dt);
		values.put("datestmp", date);
		values.put("header", header);
		values.put("amount", amount);
		values.put("description", description);
		Log.d(TAG, "Date: " + date + "; Header: " + header + "; Desc: "
				+ description + "; Amount: " + amount + "; isNull: " + isNull);
		return values;
	}

	public String toString() {
		return "Date: " + this.getDateString() + "; Header: " + header
				+ "; Desc: " + description + "; Amount: " + amount
				+ "; isNull: " + isNull;
	}

	public void resetDate() {
		dt = null;
		dt = new Date();
	}

	public Date getDate() {
		return dt;
	}

	public String getDateString() {

		String date = dateOnlyFormat.format(dt);
		return date;
	}

	public String getDayString() {

		String date = dateFormat.format(dt);
		return date;
	}

	public String getHeader() {
		return header;
	}

	public String getDescription() {
		return description;
	}

	public float getAmount() {
		return amount;
	}

}
