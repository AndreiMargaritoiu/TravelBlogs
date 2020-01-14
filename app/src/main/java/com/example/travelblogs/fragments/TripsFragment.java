package com.example.travelblogs.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelblogs.activities.AddTripActivity;
import com.example.travelblogs.activities.TripDetailActivity;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelblogs.R;
import com.example.travelblogs.SeeTripsViewModel;
import com.example.travelblogs.adapter.TripAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class TripsFragment extends Fragment implements TripAdapter.OnTripSelectedListener {

    public static final String TAG = TripsFragment.class.getSimpleName();

    private FirebaseFirestore mFirestore;
    private TripAdapter mAdapter;
    private RecyclerView mTripsRecycler;
    private Query mQuery;
    private SeeTripsViewModel mViewModel;

    public TripsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trips, container, false);
        mViewModel = ViewModelProviders.of(this).get(SeeTripsViewModel.class);
        mTripsRecycler = rootView.findViewById(R.id.trips_recyclerview);
        FirebaseFirestore.setLoggingEnabled(true);
        initFirestore();
        initRecyclerView();
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("trips");
        Log.v(TAG, mQuery.toString());

    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Toast.makeText(getContext(), "Empty Query!", Toast.LENGTH_LONG).show();
        }

        mAdapter = new TripAdapter(mQuery, this) {

            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    mTripsRecycler.setVisibility(View.GONE);
                } else {
                    mTripsRecycler.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
            }
        };

        mTripsRecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mTripsRecycler.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mTripsRecycler);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void onTripSelected(DocumentSnapshot trip) {
        Intent intent = new Intent(getActivity(), TripDetailActivity.class);
        intent.putExtra(TripDetailActivity.KEY_TRIP_ID, trip.getId());
        startActivity(intent);
    }

    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper
            .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            final int position = viewHolder.getAdapterPosition();
            onDelete(viewHolder);
            mAdapter.notifyItemRemoved(position);
        }
    };

    private void onDelete(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFirestore.collection("trips")
                                .document(String.valueOf(mAdapter.returnIdDestination(position)))
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
