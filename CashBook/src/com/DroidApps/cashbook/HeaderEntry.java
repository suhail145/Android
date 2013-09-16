package com.DroidApps.cashbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HeaderEntry extends Activity {

	public static final String TAG = HeaderEntry.class.getSimpleName();

	String headerName;
	Header header = new Header();
	EditText editName, editCat, editDesc, editDL, editWL, editML;
	TextView caption;
	Button buttonDone;
	DbUpdater db;
	Context context;
	Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_header_entry);

		db = new DbUpdater(this);
		context = this;

		editName = (EditText) findViewById(R.id.hNameEdit);
		editCat = (EditText) findViewById(R.id.hCatEdit);
		editDesc = (EditText) findViewById(R.id.hDescEdit);
		editDL = (EditText) findViewById(R.id.hDLimitEdit);
		editWL = (EditText) findViewById(R.id.hWLimitEdit);
		editML = (EditText) findViewById(R.id.hMLimitEdit);

		buttonDone = (Button) findViewById(R.id.buttonHeaderDone);

		caption = (TextView) findViewById(R.id.hText);

		intent = getIntent();

		headerName = "";
		if (intent.getAction().equals("HEADER_UPDATE"))
			headerName = intent.getStringExtra(HeaderDisplay.HEADER_NAME);

		if (intent.getAction().equals("HEADER_UPDATE")) {

			header = db.getHeaderDetails(headerName);
			editName.setText(header.getName());
			editCat.setText(header.getCategory());
			editDesc.setText(header.getDescription());
			editDL.setText(Float.toString(header.getDLimit()));
			editWL.setText(Float.toString(header.getWLimit()));
			editML.setText(Float.toString(header.getMLimit()));
			caption.setText("Header Update");

		}

		buttonDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				String name = editName.getText().toString().trim();
				String cat = editCat.getText().toString().trim();
				String desc = editDesc.getText().toString().trim();
				String temp;
				float dlimit = 0, wlimit = 0, mlimit = 0;

				temp = editDL.getText().toString().trim();
				if (!temp.isEmpty())
					dlimit = Float.valueOf(temp);
				temp = editWL.getText().toString().trim();

				if (!temp.isEmpty())
					wlimit = Float.valueOf(temp);

				temp = editML.getText().toString().trim();
				if (!temp.isEmpty())
					mlimit = Float.valueOf(temp);

				header.updateHeaderName(name, cat, desc, dlimit, wlimit, mlimit);

				Log.d(TAG, "Name : " + name + "; Cat: " + cat + "; Desc: "
						+ desc + "; D Limit: " + dlimit + "; W Limit: "
						+ wlimit + "; M Limit: " + mlimit);

				if (!header.isNull()) {

					if (headerName.isEmpty()) {
						int result = db.insertHeader(header);
						if (result == 1) {
							Log.d(TAG, "The header entry has been made");
							Toast.makeText(
									context,
									"Header " + header.getName()
											+ " has been added",
									Toast.LENGTH_SHORT).show();
						} else if (result == 0) {
							Toast.makeText(
									context,
									"The header "
											+ header.getName()
											+ " is already present, please choose a different name",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(
									context,
									"The entry couldnt be made, please recheck the values entered",
									Toast.LENGTH_SHORT).show();

						}
					} else {
						int result = db.updateHeader(header);
						if (result == 1) {
							Log.d(TAG, "The header entry has been updated");
							Toast.makeText(
									context,
									"Header " + header.getName()
											+ " has been updated",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context,
									"The entry couldnt be updated",
									Toast.LENGTH_SHORT).show();

						}

					}
				}

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_header_entry, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		db.close();
	}
}
