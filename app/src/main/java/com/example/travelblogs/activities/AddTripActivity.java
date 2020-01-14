package com.example.travelblogs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelblogs.NavigationDrawerActivity;
import com.example.travelblogs.R;
import com.example.travelblogs.fragments.TripsFragment;
import com.example.travelblogs.model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTripActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;

    TextView locationET;
    TextView countryET;
    TextView budgetET;
    TextView monthET;
    TextView accomodationET;
    TextView transportET;
    TextView detailsET;
    Button addTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        mFirestore = FirebaseFirestore.getInstance();

        locationET = findViewById(R.id.location_et);
        countryET = findViewById(R.id.country_et);
        budgetET = findViewById(R.id.budget_et);
        monthET = findViewById(R.id.month_et);
        accomodationET = findViewById(R.id.accomodation_et);
        transportET = findViewById(R.id.transport_et);
        detailsET = findViewById(R.id.details_et);
        addTrip = findViewById(R.id.add_button);

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTripMethod();
            }
        });

    }

    private void addTripMethod() {
        CollectionReference trips = mFirestore.collection("trips");
        String location = locationET.getText().toString();
        String country = countryET.getText().toString();
        String budget = budgetET.getText().toString();
        String month = monthET.getText().toString();
        String accomodation = accomodationET.getText().toString();
        String transport = transportET.getText().toString();
        String details = detailsET.getText().toString();
        Map<String, String> data = new HashMap<>();
        data.put("Name", location);
        data.put("Country", country);
        data.put("Budget", budget);
        data.put("Month", month);
        data.put("Accomodation", accomodation);
        data.put("Transport", transport);
        data.put("Details", details);
        trips.document(location).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Add successful!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddTripActivity.this,
                                    NavigationDrawerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
    }
}
