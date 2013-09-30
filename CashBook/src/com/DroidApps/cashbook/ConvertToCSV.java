package com.DroidApps.cashbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class ConvertToCSV {
	ArrayList<Transaction> transList;
	final static String FileName = "CashBook_Accounts";
	final static String Extn = ".csv";
	static String path = "/sdcard";
	static File csvDump;
	Context context;

	// private static int byHeader = 0;
	// private static int byDates = 0;

	private static File genFileName(Context context, String filePath) {

		String filename = FileName + "*" + Extn;
		String absFilePath = filePath + "/" + filename;
		int Seq = 1;
		File fpath = new File(Environment.getExternalStorageDirectory(),
				absFilePath);
		if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
		    Toast.makeText(context, "External SD card not mounted", Toast.LENGTH_LONG).show();
		}
		System.out.println(fpath.canRead());
		if (fpath.canRead() && fpath.listFiles().length > 0) {
			for (File file : fpath.listFiles()) {

				if (file.isFile()) {
					String seq = file.getName();
					seq = seq.replace(FileName, "");
					seq = seq.replace(Extn, "");
					int newSeq = Integer.parseInt(seq);
					if (newSeq > Seq)
						Seq = newSeq;
				}
			}
		}
//		System.out.println(filePath);
		fpath = new File(Environment.getExternalStorageDirectory(), filePath);
		csvDump = new File(fpath, FileName + Seq + Extn);
		try {
			csvDump.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(
					context, "Couldnt create file" + FileName + Seq + Extn + " on the sdcard!",
					Toast.LENGTH_SHORT).show();
		}
		System.out.println(FileName + Seq + Extn);
		System.out.println(csvDump.exists());
		return csvDump;
	}

	public static void convert2csv(Context context,
			ArrayList<Transaction> transList) {
		
		Iterator<Transaction> transIterate = transList.iterator();

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					genFileName(context, path), true));
			out.write("Date,Header,Amount");
			while (transIterate.hasNext()) {
				Transaction trans = transIterate.next();
				out.write(trans.getDateString() + "," + trans.getHeader() + ","
						+ trans.getAmount());
			}
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context,
					"An error occured during creation of csv files!",
					Toast.LENGTH_SHORT).show();
		}

	}
}
