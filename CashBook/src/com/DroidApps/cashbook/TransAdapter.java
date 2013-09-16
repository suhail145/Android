package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TransAdapter extends ArrayAdapter<Transaction> {
	Context context;
	static int layoutResourceId = R.layout.trans_view;
	ArrayList<Transaction> trans = null;

	public TransAdapter(Context context, ArrayList<Transaction> trans) {
		super(context, layoutResourceId, trans);
		this.context = context;
		this.trans = trans;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		Transaction transaction = trans.get(position);
		TextView tvHeader, tvAmt;
		if (row == null) {

			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflator.inflate(layoutResourceId, parent, false);

		}

		tvHeader = (TextView) row.findViewById(R.id.tvTransHeader);
		tvAmt = (TextView) row.findViewById(R.id.tvTransAmt);
		if (transaction != null) {
			System.out.println(transaction.getHeader() + ":"
					+ Float.toString(transaction.getAmount()));
			System.out.println("tvHeader:" + tvHeader.isEnabled());
			tvHeader.setText(transaction.getHeader());
			tvAmt.setText(Float.toString(transaction.getAmount()));
		}else {
			tvHeader.setText("No transactions found!");
		}
		return row;
	}
}
