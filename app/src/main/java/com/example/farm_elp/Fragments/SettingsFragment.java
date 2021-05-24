package com.example.farm_elp.Fragments;

import android.os.Bundle;

import android.preference.PreferenceFragment;

import com.example.farm_elp.R;

public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}