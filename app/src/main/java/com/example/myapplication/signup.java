package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class signup extends AppCompatActivity {

    EditText name, email, password, contact, cpasswd;
    Button register;

    DatabaseHelper db;
    RadioGroup rg ;
    private String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new DatabaseHelper(this);
        name= findViewById(R.id.fname);
        password = findViewById(R.id.passwd);
        register = findViewById(R.id.regs);
        email= findViewById(R.id.uemail);
        contact= findViewById(R.id.contactno);
        cpasswd = findViewById(R.id.cpasswd);
        rg = findViewById(R.id.radio_group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null && radioButton.isChecked()) {
                    String selectedValue = radioButton.getText().toString();
                    gender = selectedValue;

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailid = email.getText().toString();
                String pwd = password.getText().toString();
                String fullName = name.getText().toString();
                String contactno= contact.getText().toString();
                String confPassword = cpasswd.getText().toString();
                if(fullName.isEmpty()){
                    name.setError("Please enter the Name");
                    name.requestFocus();
                    return ;
                }
                if (mailid.isEmpty()){
                    email.setError("Please enter the email");
                    email.requestFocus();
                    return;
                }
                if (contactno.isEmpty()){
                    password.setError("Please enter the contact no.");
                    password.requestFocus();
                    return;
                }
                if (pwd.isEmpty()){
                    password.setError("Please Enter the Password.");
                    password.requestFocus();
                    return;
                }
                if (confPassword.isEmpty() || !pwd.equals(confPassword) ) {
                    cpasswd.setError("Please Enter The Same Password");
                    cpasswd.requestFocus();
                    return;
                }
                if (gender == null){
                    Toast.makeText(signup.this, "Please Enter a Gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User(fullName, mailid, pwd, contactno, gender);
                if(!db.insertUser(user)){
                            Toast.makeText(signup.this, "Registration Unsuccessful, Please Try again!", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(signup.this, Home.class));
                }
            }
        });
    }
}
