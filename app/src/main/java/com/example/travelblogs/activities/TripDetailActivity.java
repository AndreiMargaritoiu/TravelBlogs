package com.example.travelblogs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.travelblogs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class TripDetailActivity extends AppCompatActivity {


    private String TAG = TripDetailActivity.class.getSimpleName();
    public static final String KEY_TRIP_ID = "key_trip_id";

    private FirebaseFirestore mFirestore;
    private DocumentReference mTripRef;

    TextView nameView;
    TextView countryView;
    TextView budgetView;
    TextView monthView;
    TextView accomodationView;
    TextView transportView;
    TextView detailsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        nameView = findViewById(R.id.place_title_detail);
        countryView = findViewById(R.id.place_country_detail);
        budgetView = findViewById(R.id.place_budget_detail);
        monthView = findViewById(R.id.place_month_detail);
        accomodationView = findViewById(R.id.place_accomodation_detail);
        transportView = findViewById(R.id.place_transport_detail);
        detailsView = findViewById(R.id.place_details_detail);

        String tripID = getIntent().getExtras().getString(KEY_TRIP_ID);
        mFirestore = FirebaseFirestore.getInstance();
        mTripRef = mFirestore.collection("trips").document(tripID);
        mTripRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> values = document.getData();
                        onTripLoaded(values);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void onTripLoaded(Map<String, Object> trip) {
        nameView.setText(trip.get("Name").toString());
        countryView.setText(trip.get("Country").toString());
        budgetView.setText(trip.get("Budget").toString());
        monthView.setText(trip.get("Month").toString());
        accomodationView.setText(trip.get("Accomodation").toString());
        transportView.setText(trip.get("Transport").toString());
        detailsView.setText(trip.get("Details").toString());
    }
}
