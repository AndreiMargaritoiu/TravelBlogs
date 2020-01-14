package com.example.travelblogs.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelblogs.NavigationDrawerActivity;
import com.example.travelblogs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextView text_view_login;
    private Button button_register;
    private EditText text_email;
    private EditText edit_text_password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        text_view_login = findViewById(R.id.text_view_login);
        button_register = findViewById(R.id.button_register);
        text_email = findViewById(R.id.text_email);
        edit_text_password = findViewById(R.id.edit_text_password);

        mAuth = FirebaseAuth.getInstance();

        text_view_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        String email = text_email.getText().toString();
        String password = edit_text_password.getText().toString();

        if (email.isEmpty()) {
            text_email.setError("Email Reuired");
            return;
        }

//                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    text_email.setError("Valid Email Reuired");
//                    return;
//                }

        if (password.isEmpty() || password.length() < 6) {
            edit_text_password.setError("Mininmum 6 Characters Reuired");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.getResult().toString();
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this,
                                    NavigationDrawerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed! " +
                                    "Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
}

