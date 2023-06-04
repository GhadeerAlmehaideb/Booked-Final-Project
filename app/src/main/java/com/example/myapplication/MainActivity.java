package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText usermail, userpassword;
    Button lgin, sgup;
    DatabaseHelper db;
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Log In");

        db = new DatabaseHelper(this);
        usermail= findViewById(R.id.umail);
        userpassword= findViewById(R.id.upwd);
        lgin= findViewById(R.id.login);
        sgup=findViewById(R.id.signup);


        sgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intologin = new Intent(MainActivity.this, signup.class);
                startActivity(intologin);
            }
        });
        lgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailid = usermail.getText().toString();
                String pwd = userpassword.getText().toString();
                if (!validateForm(mailid, pwd)) return;

                 if (db.login(mailid, pwd)) {
                        Intent i = new Intent(MainActivity.this, Home.class);
                        startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "Your password is wrong!!", Toast.LENGTH_SHORT).show();;
                }
            }
        });


    }
    private boolean validateForm(String mailid, String pwd) {
        if (mailid.isEmpty()){
            usermail.setError("Please enter the email");
            usermail.requestFocus();
            return false;
        }
        if (pwd.isEmpty()){
            userpassword.setError("Please Enter user Password");
            userpassword.requestFocus();
            return false;
        }
        if (db.getUser(mailid) == null) {
            Toast.makeText(this, "Your email does not exist", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
