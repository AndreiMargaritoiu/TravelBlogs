package com.example.travelblogs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelblogs.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TripAdapter extends FirestoreAdapter<TripAdapter.ViewHolder> {

    public interface OnTripSelectedListener {

        void onTripSelected(DocumentSnapshot trip);

    }

    private OnTripSelectedListener mListener;
    private static List<String> listOfIds = new ArrayList<>();

    public TripAdapter(Query query, OnTripSelectedListener listener) {
        super(query);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_trip, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }


    /**
     * USE THIS METHOND ONLY TO DELETE
     * @param position
     * @return
     */
    public String returnIdDestination(int position){
        String s = listOfIds.get(position);
        listOfIds.remove(position);
        return s;
    }

    public int returnSize(){
        return listOfIds.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView countryView;
        TextView budgetView;
        TextView monthView;
//        TextView accomodationView;
//        TextView transportView;
//        TextView detailsView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.place_title);
            countryView = itemView.findViewById(R.id.place_country);
            budgetView = itemView.findViewById(R.id.place_budget);
            monthView = itemView.findViewById(R.id.place_month);
//            accomodationView = itemView.findViewById(R.id.place_accomodation);
//            transportView = itemView.findViewById(R.id.place_transport);
//            detailsView = itemView.findViewById(R.id.place_details);
        }


        public void bind(final DocumentSnapshot snapshot,
                         final OnTripSelectedListener listener) {

            Map<String, Object> values = snapshot.getData();
            onTripLoaded(values);

            listOfIds.add(values.get("Name").toString());



            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onTripSelected(snapshot);
                    }
                }
            });
        }

        private void onTripLoaded(Map<String, Object> trip) {

            nameView.setText(trip.get("Name").toString());
            countryView.setText(trip.get("Country").toString());
            budgetView.setText(trip.get("Budget").toString());
            monthView.setText(trip.get("Month").toString());
//            accomodationView.setText(trip.get("Accomodation").toString());
//            transportView.setText(trip.get("Transport").toString());
//            detailsView.setText(trip.get("Details").toString());
        }

    }


}
