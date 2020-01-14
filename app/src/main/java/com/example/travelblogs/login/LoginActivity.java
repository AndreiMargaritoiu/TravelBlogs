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
import com.example.travelblogs.activities.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private TextView text_view_register;
    private Button button_sign_in;
    private EditText text_email;
    private EditText edit_text_password;
    private TextView resetpass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_sign_in = findViewById(R.id.button_sign_in);
        text_email = findViewById(R.id.text_email);
        edit_text_password = findViewById(R.id.edit_text_password);
        text_view_register = findViewById(R.id.text_view_register);
        resetpass = findViewById(R.id.text_view_forget_password);

        mAuth = FirebaseAuth.getInstance();

        text_view_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {

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
            edit_text_password.setError("6 Characters Reuired");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,
                                    NavigationDrawerActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Login failed! " +
                        "Please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }
}



