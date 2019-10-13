package com.hackmact.fatofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {


    private EditText editTextPwd, editTextEmail;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail = findViewById(R.id.edit_text_id);
        editTextPwd = findViewById(R.id.edit_text_pwd);

        mAuth = FirebaseAuth.getInstance();

    }

    public void signin(View view)
    {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPwd.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FitOFat", "signInWithEmail:success");
                    Intent intent = new Intent(SignIn.this,MainActivity.class);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FitOFat", "signInWithEmail:failure", task.getException());
                    Toast.makeText(SignIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void tosignup(View view)
    {
        Intent intent = new Intent(SignIn.this,SignUp.class);
        startActivity(intent);

    }
}
