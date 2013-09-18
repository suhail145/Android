package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class TransByDayAdapter extends BaseExpandableListAdapter {

	static int listViewResourceId = R.id.lvTransByDay;
	Context context;
	HashMap<String, ArrayList<Transaction>> dayTrans = new HashMap<String, ArrayList<Transaction>>();
	ArrayList<String> dates = new ArrayList<String>();

	public TransByDayAdapter(Context context,
			HashMap<String, ArrayList<Transaction>> dayTrans,
			ArrayList<String> dates) {
		this.context = context;
		this.dayTrans = dayTrans;
		this.dates = dates;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.dayTrans.get(this.dates.get(groupPosition)).get(
				childPosition);

	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View childRow = convertView;
		Transaction trans = (Transaction) getChild(groupPosition, childPosition);
		if (childRow == null) {
			LayoutInflater inflator = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			childRow = inflator
					.inflate(R.layout.trans_by_day_trans_row, null);
		}
		TextView tvChildRowHeader = (TextView) childRow
				.findViewById(R.id.tvTransByDayHeader);
		TextView tvChildRowAmt = (TextView) childRow
				.findViewById(R.id.tvTransByDayAmt);
		tvChildRowHeader.setText(trans.getHeader());
		String Amt;
		Float amt = trans.getAmount();
		if (amt != null)
			Amt = amt.toString();
		else
			Amt = "0";
		tvChildRowAmt.setText(Amt);
//		System.out.println(trans.getHeader()+":"+Amt);
		return childRow;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return dayTrans.get(dates.get(groupPosition)).size();
		
	}

	@Override
	public Object getGroup(int groupPosition) {

		return dates.get(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return dates.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View dateView = convertView;
		String date = (String) getGroup(groupPosition);
		if (dateView == null) {
			LayoutInflater inflator = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			dateView = inflator.inflate(R.layout.trans_by_day_date, null);
		}
		
		TextView tvDate = (TextView) dateView
				.findViewById(R.id.tvTransByDayDate);
		tvDate.setText(date);
		return dateView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
