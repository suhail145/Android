package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.app.Application;

public class CashBookApplication extends Application {

	private static Transaction trans = new Transaction();;
	public static ArrayList<Transaction> transArray;
	public static Transaction getTrans() {

		return trans;
	}

	public static void setTrans(Transaction transaction) {
		trans = transaction;
	}

	public static ArrayList<Transaction> getTransArray(){
		transArray = new ArrayList<Transaction>();
		return transArray;
	}

}
