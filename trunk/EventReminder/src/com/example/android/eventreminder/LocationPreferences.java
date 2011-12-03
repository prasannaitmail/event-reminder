package com.example.android.eventreminder;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class LocationPreferences extends PreferenceActivity{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
	}
}
