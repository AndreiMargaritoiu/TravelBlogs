package com.example.travelblogs.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelblogs.R;
import com.example.travelblogs.activities.UpdatePasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileFragment extends Fragment {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private EditText username;
    private TextView email;
    private TextView text_not_verified;
    private TextView password;
    private Button save;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        username = rootView.findViewById(R.id.edit_text_name);
        email = rootView.findViewById(R.id.text_email);
        text_not_verified = rootView.findViewById(R.id.text_not_verified);
        save = rootView.findViewById(R.id.button_save);
        password = rootView.findViewById(R.id.text_password);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (currentUser != null) {
            username.setText(currentUser.getDisplayName());
            email.setText(currentUser.getEmail());

            if (currentUser.isEmailVerified()) {
                text_not_verified.setVisibility(View.INVISIBLE);
            } else {
                text_not_verified.setVisibility(View.VISIBLE);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = username.getText().toString();
                if (name.isEmpty()) {
                    username.setError("name required");
                }

                UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();


                if (updates != null) {
                    currentUser.updateProfile(updates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Update successful!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdatePasswordActivity.class));
//                Fragment fragment = new UpdatePasswordFragment();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.profileFragment, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });

        text_not_verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUser.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Verification email sent!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}


