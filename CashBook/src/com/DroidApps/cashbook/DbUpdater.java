package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbUpdater {
	public static final String TAG = DbUpdater.class.getSimpleName();
	public static final String DB_NAME = "cashbook.db";
	public static final int DB_VERSION = 1;
	Context context;
	Data dt;
	SQLiteDatabase db;

	public DbUpdater(Context context) {
		this.context = context;
		dt = new Data(context);
		db = dt.getWritableDatabase();
	}

	// Database methods for Headers
	public int insertHeader(Header header) {
		String[] columns = { "name" };
		String[] values = { header.getName() };
		Cursor cursor = db.query("headers", columns, "name = ?", values, null,
				null, null);
		if (cursor.getCount() > 0)
			return 0;
		else {
			if (db.insert("headers", null, header.getValue()) != -1)
				return 1;
			else
				return -1;
		}
	}

	public int updateHeader(Header header) {
		String[] args = { header.getName() };
		return db.update("headers", header.getValue(), "name = ?", args);
	}

	public ArrayList<String> getHeaders() {
		ArrayList<String> headers = new ArrayList<String>();
		String[] columns = { "name" };
		Cursor cursor = db.query("headers", columns, null, null, null, null,
				null);
		if (cursor.getCount() < 1) {
			headers.add("No Headers found, please add some");
			return headers;
		}
		while (!cursor.isLast()) {
			cursor.moveToNext();
			headers.add(cursor.getString(0));
		}
		return headers;
	}

	public ArrayList<String> getHeadersWithTrans() {
		ArrayList<String> headers = new ArrayList<String>();
		String[] columns = { "distinct header" };
		Cursor cursor = db.query("transactions", columns, null, null, null,
				null, null);
		// System.out.println("Distinct headers found :" + cursor.getCount());
		if (cursor.getCount() > 0) {
			while (!cursor.isLast()) {
				cursor.moveToNext();
				headers.add(cursor.getString(0));
			}
		}
		return headers;
	}

	public Header getHeaderDetails(String name) {
		Header header = new Header();
		String[] columns = { "*" };
		String[] values = { name };
		Cursor cursor = db.query("headers", columns, "name = ?", values, null,
				null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToNext();

			header.updateHeaderName(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getFloat(3),
					cursor.getFloat(4), cursor.getFloat(5));
			return header;
		} else
			return null;
	}

	// Database methods for Monetary Transactions
	public int addTransaction(Transaction trans) {
		ContentValues values = trans.getValue();
		int result = (int) db.insert("transactions", null, values);
		return result;
	}

	public ArrayList<Transaction> getQuickTrans() {
		ArrayList<Transaction> array = new ArrayList<Transaction>();
		array.clear();
		String[] columns = { "*" };
		Cursor cursor = db.query("quickentry", columns, null, null, null, null,
				null);
		if (cursor.getCount() < 1) {
			Log.d(TAG, "No quick entries found!");
			return null;
		} else {
			// Log.d(TAG, "Before populating the list");
			int count = 0;
			while (cursor.moveToNext()) {
				Transaction trans = new Transaction();
				String header = cursor.getString(0);
				String desc = cursor.getString(2);
				String amt = cursor.getString(1);
				float amount;
				if (amt.equals(""))
					amount = 0;
				else
					amount = Float.valueOf(amt);
				trans.updateTransaction(header, amount, desc);
				array.add(count, trans);
			}
			return array;
		}
	}

	public ArrayList<Transaction> getDayTransactions(String day) {
		String[] date = day.split("-");
		ArrayList<Transaction> array = new ArrayList<Transaction>();
		if (date.length == 3) {
			String[] columns = { "*" };
			String arg = date[0] + "-" + date[1] + "-" + date[2];
			String args[] = { arg + " 00:00:00", arg + " 23:59:59" };
			Cursor cursor = db.query("transactions", columns,
					"datestmp between ? and ?", args, null, null, "datestmp");
			if (cursor.getCount() < 1) {
				Log.d(TAG, "No transactions found!");
				return null;
			} else {
				while (cursor.moveToNext()) {

					Transaction trans = new Transaction();
					String header = cursor.getString(1);
					String desc = cursor.getString(3);
					String amt = cursor.getString(2);
					float amount;
					if (amt.equals(""))
						amount = 0;
					else
						amount = Float.valueOf(amt);
					trans.updateTransaction(header, amount, desc);
					array.add(trans);
				}
			}
			return array;
		} else {
			return null;
		}
	}

	public ArrayList<Transaction> getHeaderTransactions(String byHeader) {

		ArrayList<Transaction> array = new ArrayList<Transaction>();

		String[] columns = { "*" };

		String args[] = { byHeader };
		Cursor cursor = db.query("transactions", columns, "header = ?", args,
				null, null, "datestmp");
		if (cursor.getCount() < 1) {
			Log.d(TAG, "No transactions found!");
			return null;
		} else {
			while (cursor.moveToNext()) {

				Transaction trans = new Transaction();
				String header = cursor.getString(1);
				String desc = cursor.getString(3);
				String amt = cursor.getString(2);
				float amount;
				if (amt.equals(""))
					amount = 0;
				else
					amount = Float.valueOf(amt);
				trans.updateTransaction(header, amount, desc);
				array.add(trans);
			}
		}
		return array;

	}

	public Float getAmtByDates(String stDate, String enDate) {
		String[] sDate = stDate.split("-");
		String[] eDate = enDate.split("-");
		Float amt = (float) 0;
		if (sDate.length == 3 & eDate.length == 3) {
			String[] columns = { "amount" };
			String args[] = { stDate + " 00:00:00", enDate + " 23:59:59" };
			Cursor cursor = db.query("transactions", columns,
					"datestmp between ? and ?", args, null, null, "datestmp");
			if (cursor.getCount() < 1) {
				Log.d(TAG, "No transactions found for these dates!");
				return null;
			} else {
				while (cursor.moveToNext()) {
					amt = amt + Float.parseFloat(cursor.getString(0));
				}
			}
		} else {
			Log.d(TAG, "Date format given for getAmtByDates() is wrong!");
			return null;
		}

		return amt;
	}

	public Float getAmtByHeader(String byHeader) {
		Float amt = (float) 0;
		String[] columns = { "amount" };
		String args[] = { byHeader };
		Cursor cursor = db.query("transactions", columns,
				"header = ?", args, null, null, "datestmp");
		if (cursor.getCount() < 1) {
			Log.d(TAG, "No transactions found for these dates!");
			return null;
		} else {
			while (cursor.moveToNext()) {
				amt = amt + Float.parseFloat(cursor.getString(0));
			}
		}
		return amt;
	}

	public ArrayList<String> getTransactionDates() {
		ArrayList<String> transDates = new ArrayList<String>();
		String[] columns = { "datestmp" };
		Cursor cursor = db.query("transactions", columns, null, null, null,
				null, "datestmp");
		if (cursor.getCount() < 1) {
			Log.d(TAG, "No transactions found!");
			return null;
		} else {
			while (cursor.moveToNext()) {

				String transDt = cursor.getString(0);
				String[] dt = transDt.split(" ");
				if (!transDates.contains(dt[0]))
					transDates.add(dt[0]);

			}
		}
		return transDates;

	}

	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> array = new ArrayList<Transaction>();

		String[] columns = { "*" };
		Cursor cursor = db.query("transactions", columns, null, null, null,
				null, "datestmp");
		if (cursor.getCount() < 1) {
			Log.d(TAG, "No transactions found!");
			array.add(new Transaction());
			// return array;
		} else {
			while (cursor.moveToNext()) {

				Transaction trans = new Transaction();
				String header = cursor.getString(1);
				String desc = cursor.getString(3);
				String amt = cursor.getString(2);
				String timestmp = cursor.getString(0);
				float amount;
				if (amt.equals(""))
					amount = 0;
				else
					amount = Float.valueOf(amt);
				trans.updateTransaction(timestmp, header, amount, desc);
				array.add(trans);
			}
		}
		return array;

	}

	public void close() {
		dt.close();
	}

	private class Data extends SQLiteOpenHelper {
		public Data(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String header = "create table headers (name text, category text, description text, dlimit float, wlimit float, mlimit float, primary key(name))";
			String transaction = "create table transactions (datestmp timestamp, header text, amount float, description text)";
			String category = "create table category (datestmp timestamp, category text, amount float)";
			String quickEntry = "create table quickentry (header text, amount float, description text)";

			db.execSQL(header);
			db.execSQL(transaction);
			db.execSQL(category);
			db.execSQL(quickEntry);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}
}