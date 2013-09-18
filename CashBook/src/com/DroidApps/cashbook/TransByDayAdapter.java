package com.DroidApps.cashbook;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class TransByDayAdapter extends BaseExpandableListAdapter{

	static int listViewResourceId = R.id.lvTransByDay;
	Context context;
	HashMap<String,ArrayList<Transaction>> dayTrans;
	public TransByDayAdapter(Context context, HashMap<String,ArrayList<Transaction>> dayTrans){
		this.context=context;
		this.dayTrans=dayTrans;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object getGroup(int groupPosition) {
		dayTrans.get(groupPosition);
		return null;
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
