package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.R.layout;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class TransByDayAdapter extends BaseAdapter{

	static int listViewResourceId = R.id.lvTransByDay;
	Context context;
	ArrayList<View> trans;
	public TransByDayAdapter(Context context, ArrayList<View> views){
		this.context=context;
		this.trans=views;
	}
	@Override
	public int getCount() {
		return trans.size();
		
	}
	@Override
	public Object getItem(int arg0) {
		
		return trans.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		LayoutInflater inflator = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		inflator.inflate(R.id.lvTransDisplay, parent);
		View viewRow=trans.get(position);
//		viewRow.inflate(context, R.id.lvTransDisplay, parent);
		return viewRow;
	}
	

	
	
	

}
