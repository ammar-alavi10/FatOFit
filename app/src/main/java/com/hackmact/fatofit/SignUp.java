package com.hackmact.fatofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText editTextWeight, editTextEmail, editTextPassword, editTextHeight, editTextAge, editTextGender;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextHeight = findViewById(R.id.edit_text_height);
        editTextEmail = findViewById(R.id.edit_text_id);
        editTextPassword = findViewById(R.id.edit_text_pwd);
        editTextWeight = findViewById(R.id.edit_text_weight);
        editTextGender = findViewById(R.id.edit_text_gender);
        editTextAge = findViewById(R.id.edit_text_age);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null)
        {
            //handle user
        }

    }

    public void register(View view)
    {
        final String height = editTextHeight.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String weight = editTextWeight.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();

        if (height.isEmpty()) {
            editTextHeight.setError(getString(R.string.input_error_height));
            editTextHeight.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (weight.isEmpty()) {
            editTextWeight.setError(getString(R.string.input_error_weight));
            editTextWeight.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    email,
                                    height,
                                    weight,
                                    age,
                                    gender
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignUp.this,MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignUp.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    public void tosignin(View view)
    {
        Intent intent = new Intent(SignUp.this,SignIn.class);
        startActivity(intent);
    }

}
