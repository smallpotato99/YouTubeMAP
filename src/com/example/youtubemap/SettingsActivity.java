package com.example.youtubemap;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class SettingsActivity extends Activity {	
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		addPreferencesFromResource(R.xml.settings);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
	}
}
