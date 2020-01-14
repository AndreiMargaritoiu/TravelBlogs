package com.example.travelblogs.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.travelblogs.NavigationDrawerActivity;
import com.example.travelblogs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private LinearLayout layoutPassword;
    private LinearLayout layoutUpdatePassword;
    private Button authenticatebtn;
    private EditText passwordet;
    private Button updatepass;
    private EditText newpass;
    private EditText newpassconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        layoutPassword = findViewById(R.id.layoutPassword);
        layoutUpdatePassword = findViewById(R.id.layoutUpdatePassword);
        authenticatebtn = findViewById(R.id.button_authenticate);
        passwordet = findViewById(R.id.edit_text_password);
        updatepass = findViewById(R.id.button_update);
        newpass = findViewById(R.id.edit_text_new_password);
        newpassconfirm = findViewById(R.id.edit_text_new_password_confirm);
    }

    @Override
    protected void onResume() {
        layoutPassword.setVisibility(View.VISIBLE);
        layoutUpdatePassword.setVisibility(View.GONE);

        authenticatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = passwordet.getText().toString();

                if (password.isEmpty()) {
                    passwordet.setError("Password required");
                }

                AuthCredential credential = EmailAuthProvider
                        .getCredential(currentUser.getEmail(), password);
                currentUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    layoutPassword.setVisibility(View.GONE);
                                    layoutUpdatePassword.setVisibility(View.VISIBLE);
                                } else {
                                    passwordet.setError("Invalid Password");
                                }
                            }
                        });
            }
        });

        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatepass();
            }
        });
        super.onResume();
    }

    public void updatepass() {
        String password = newpass.getText().toString();
        String passconfirm = newpassconfirm.getText().toString();
        if (password.isEmpty() || password.length() < 6) {
            newpass.setError("At least 6 char password required");
        } else if (!password.equals(passconfirm)) {
            newpassconfirm.setError("password did not match");
        } else {
            currentUser.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "password updated successful!",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UpdatePasswordActivity.this,
                                        NavigationDrawerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }
}
