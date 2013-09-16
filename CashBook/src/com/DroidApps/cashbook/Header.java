package com.DroidApps.cashbook;

import android.content.ContentValues;
import android.util.Log;

public class Header {

	public static final String TAG = Header.class.getSimpleName();
	private String name = "";
	private String category = "";
	private String description = "";
	private float dlimit = 0;
	private float wlimit = 0;
	private float mlimit = 0;
	private boolean isNull = true;

	public Header() {

	}

	public Header(String name, String cat, String desc, Float d, Float w,
			Float m) {

		this.name = name;
		this.category = cat;
		if (desc != null)
			this.description = desc;
		if (d != null)
			this.dlimit = d;
		if (w != null)
			this.wlimit = w;
		if (m != null)
			this.mlimit = m;
		isNull = false;
	}

	public void updateHeaderName(String hName, String cat, String desc,
			Float d, Float w, Float m) {

		if (hName != null)
			this.name = hName;
		if (cat != null)
			this.category = cat;
		if (desc != null)
			this.description = desc;
		if (d != null)
			this.dlimit = d;
		if (w != null)
			this.wlimit = w;
		if (m != null)
			this.mlimit = m;

		if (this.name != null)
			isNull = false;
	}

	public boolean isNull() {
		if (name == null) {
			isNull = true;
		} else {
			isNull = false;
		}

		Log.d(TAG, "isNull is " + isNull);
		return isNull;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public float getWLimit() {
		return wlimit;
	}

	public float getDLimit() {

		return dlimit;
	}

	public float getMLimit() {
		return mlimit;
	}

	public ContentValues getValue() {
		ContentValues values = new ContentValues();
		if (name == null)
			name = "Nameless Header";
		values.put("name", name);
		if (category == null)
			category = "null";
		values.put("category", category);
		if (description == null)
			description = "null";
		values.put("description", description);
		values.put("dlimit", dlimit);
		values.put("wlimit", wlimit);
		values.put("mlimit", mlimit);

		Log.d(TAG, "Name : " + name + "; Cat: " + category + "; Desc: "
				+ description + "; D Limit: " + dlimit + "; W Limit: " + wlimit
				+ "; M Limit: " + mlimit + "; isNull: " + isNull);
		return values;
	}
}
