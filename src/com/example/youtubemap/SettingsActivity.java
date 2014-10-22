package com.example.youtubemap;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	public static final String[] MENU_ITEMS = new String[]{"", "Item 1", "Item 2", "Item 3", "Item 4", "Settings"};
		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings);		
	}
}