package com.example.farm_elp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farm_elp.R;
import com.example.farm_elp.Activity.PlacesActivity;
import com.example.farm_elp.Activity.SowingActivity;
import com.example.farm_elp.Activity.ToolsActivity;

public class HomeFragment extends Fragment {
    CardView sowing, tools, places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home,container,false);

        sowing=v.findViewById(R.id.sowing);
        tools=v.findViewById(R.id.tools);
        places=v.findViewById(R.id.places);

        sowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SowingActivity.class));
            }
        });

        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlacesActivity.class));
            }
        });
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ToolsActivity.class));
            }
        });

        return v;
    }

}