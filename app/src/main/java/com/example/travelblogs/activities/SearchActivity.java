package com.example.travelblogs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.travelblogs.R;

public class SearchActivity extends AppCompatActivity {

    private EditText searchLocation;
    private Button locationbtn;
    private EditText searchUser;
    private Button userbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final View lay1 = findViewById(R.id.linlay1);
        final View lay2 = findViewById(R.id.linlay2);

        searchLocation = findViewById(R.id.search_place);
        locationbtn = findViewById(R.id.button_searchlocation);
        searchUser = findViewById(R.id.search_user);
        userbtn = findViewById(R.id.button_searchuser);

        ((RadioGroup) findViewById(R.id.radio_group))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.raiodbtn1:
                                lay1.setVisibility(View.VISIBLE);
                                lay2.setVisibility(View.INVISIBLE);
                                break;
                            case R.id.radiobtn2:
                                lay2.setVisibility(View.VISIBLE);
                                lay1.setVisibility(View.INVISIBLE);
                                break;
                        }
                    }
                });

        locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, TripDetailActivity.class);
                intent.putExtra(TripDetailActivity.KEY_TRIP_ID, searchLocation.getText().toString());
                startActivity(intent);
            }
        });
    }
}
