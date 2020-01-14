package com.example.travelblogs.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.travelblogs.R;
import com.example.travelblogs.activities.SearchActivity;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private Button button_search;
    private ImageView im;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        im = rootView.findViewById(R.id.travel_image);
        button_search = rootView.findViewById(R.id.search_trips);
        button_search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "Search apasat");
                        Intent intent2 = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent2);
                    }
                });

        return rootView;
    }
}
