package com.DroidApps.cashbook;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class CheckColors extends Activity {

	ArrayList<Color> colors;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_color);
		colors = new ArrayList<Color>();
		
		
	}

	private class Color{
		String name;
		String value;
		
		Color(String name, String value){
			this.name=name;
			this.value=value;
		}
		
		public String getColor(){
			return this.value;
		}
	}
	
}
