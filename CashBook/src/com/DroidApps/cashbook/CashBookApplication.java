package com.DroidApps.cashbook;

import android.app.Application;

public class CashBookApplication extends Application {

	private static Transaction trans = new Transaction();;

	public static Transaction getTrans() {

		return trans;
	}

	public static void setTrans(Transaction transaction) {
		trans = transaction;
	}

	// public DbUpdater getDbHandle() {
	// db = new DbUpdater(this);
	// return db;
	// }

}
