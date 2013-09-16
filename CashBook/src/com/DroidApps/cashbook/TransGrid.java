package com.DroidApps.cashbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TransGrid extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_trans_grid);

		LinearLayout layout = new LinearLayout(this);

		layout.setOrientation(LinearLayout.HORIZONTAL);
		ListView grid = new ListView(this); // = (GridView)
											// findViewById(R.id.gvTransGrid);
		String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
				"AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ",
				"AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ" };

		ListView grid2 = new ListView(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, alphabet);

		grid.setAdapter(adapter);
		grid2.setAdapter(adapter);

		// grid.setNumColumns(1);
		// grid2.setNumColumns(1);
		grid.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		// grid.setLayoutParams();
		grid2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		layout.addView(grid);
		layout.addView(grid2);

		HorizontalScrollView hScroll = new HorizontalScrollView(this);

		hScroll.addView(layout);

		setContentView(hScroll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_trans_grid2, menu);
		return true;
	}
}
