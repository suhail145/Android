package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class TransDispAdapter extends BaseExpandableListAdapter {

	int listRowResourceId;
	static int DispByDayID = R.layout.trans_by_day_trans_row;
	static int DispByHeaderID = R.layout.trans_by_header_row;
	static int DispHeadID = R.layout.trans_disp_header;
	Context context;
	DbUpdater db;
	HashMap<String, ArrayList<Transaction>> dayTrans = new HashMap<String, ArrayList<Transaction>>();
	ArrayList<String> dates = new ArrayList<String>();

	public TransDispAdapter(Context context, int listViewResourceId,
			HashMap<String, ArrayList<Transaction>> dayTrans,
			ArrayList<String> dates) {
		this.context = context;
		this.listRowResourceId = listViewResourceId;
		this.dayTrans = dayTrans;
		this.dates = dates;
		db = new DbUpdater(context);
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
		if (listRowResourceId == DispByDayID) {
			if (childRow == null) {
				LayoutInflater inflator = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				childRow = inflator.inflate(listRowResourceId, null);
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
		} else if (listRowResourceId == DispByHeaderID) {
			if (childRow == null) {
				LayoutInflater inflator = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				childRow = inflator.inflate(listRowResourceId, null);
			}
			TextView tvChildRowDate = (TextView) childRow
					.findViewById(R.id.tvTransDispHeaderDate);
			// TextView tvChildRowHeader = (TextView) childRow
			// .findViewById(R.id.tvTransDispHeaderHead);
			TextView tvChildRowAmt = (TextView) childRow
					.findViewById(R.id.tvTransDispHeaderAmt);
			tvChildRowDate.setText(trans.getDayString());
			// tvChildRowHeader.setText(trans.getHeader());
			String Amt;
			Float amt = trans.getAmount();
			if (amt != null)
				Amt = amt.toString();
			else
				Amt = "0";
			tvChildRowAmt.setText(Amt);
		}

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
		View headView = convertView;
		String dispHead = (String) getGroup(groupPosition);

		if (headView == null) {
			LayoutInflater inflator = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			headView = inflator.inflate(DispHeadID, null);
		}

		TextView tvHead = (TextView) headView
				.findViewById(R.id.tvTransDispHead);
		TextView tvAmt = (TextView) headView.findViewById(R.id.tvTransDispAmt);
		tvHead.setText(dispHead);
		Float amt = (float) 0;
		if (listRowResourceId == DispByDayID){
			System.out.println(dispHead);
			amt = db.getAmtByDates(dispHead, dispHead);
		}else
			amt = db.getAmtByHeader(dispHead);
		System.out.println(amt);
		tvAmt.setText(amt.toString());
		return headView;
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
