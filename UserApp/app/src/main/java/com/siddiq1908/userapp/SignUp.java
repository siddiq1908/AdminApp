package com.siddiq1908.userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.siddiq1908.userapp.R;

public class SignUp extends AppCompatActivity {
    Button signup;
    TextView login;
    EditText name;
    EditText email;
    EditText password;
    private FirebaseAuth auth;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup = findViewById(R.id.signupButton);
        login = findViewById(R.id.loginTextView);

        name = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        auth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString();
                String passStr = password.getText().toString();
                String nameStr = name.getText().toString();

                if ((TextUtils.isEmpty(emailStr)) || (TextUtils.isEmpty(passStr)) || nameStr.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter details", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(nameStr, emailStr, passStr);
                }
            }
        });
    }

    private void registerUser(String nameStr, String emailStr, String passStr) {
        pd.setMessage("Signing up...");
        pd.show();
        auth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    User user = new User(nameStr);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                    databaseReference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(SignUp.this, "Signed up successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                pd.dismiss();
                                Toast.makeText(SignUp.this, "Error...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}