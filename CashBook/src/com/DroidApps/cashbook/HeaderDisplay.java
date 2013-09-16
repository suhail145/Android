package com.DroidApps.cashbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HeaderDisplay extends Activity {

	public static final String TAG = HeaderDisplay.class.getSimpleName();
	public static final String HEADER_NAME = "com.DroidApps.cashbook.Name";
	ListView list;
	DbUpdater db;
	Context context;
	String name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_header_display);

		db = new DbUpdater(this);
		context = this;

		list = (ListView) findViewById(R.id.hList);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, db.getHeaders());

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				name = (String) list.getItemAtPosition(position);

				Intent intent = new Intent(context, HeaderEntry.class);
				intent.setAction("HEADER_UPDATE");
				intent.putExtra(HEADER_NAME, name);
				startActivity(intent);

			}

		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		db.close();
	}
}
