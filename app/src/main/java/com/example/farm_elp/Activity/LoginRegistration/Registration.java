package com.example.farm_elp.Activity.LoginRegistration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.Validate.UserValidation;
import com.example.farm_elp.model.UserData;
import com.example.farm_elp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    //////////////////user's credential variables////////////////////////
    TextInputLayout fullName, email, phoneNo, adhaarID, address, profession;
    TextInputLayout create_password, confirm_password;

    UserData dataclass;
    String emailID, createPass, add, name, profess;
    long adhaar, phone;

    Button submit_btn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        firebaseAuth = FirebaseAuth.getInstance();
try {
    Toolbar toolbar = findViewById(R.id.toolBar);
    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    assert actionbar != null;
    try {
        Intent intentGet = getIntent();
        String parentName = intentGet.getStringExtra("loginActivity");
        System.out.println(parentName);
        if (parentName.equals("one")) {
            actionbar.setDisplayHomeAsUpEnabled(false);
        }
    } catch (Exception e) {
        System.out.println("Error Occurred");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }
    actionbar.setDisplayShowHomeEnabled(true);
    setTitle("Register");
}catch (Exception e){
        Toast.makeText(this,"Toolbar error",Toast.LENGTH_LONG).show();
    }

        /////////////////////Uploading User Data to Firebase///////////////////
        fullName=findViewById(R.id.fullName);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phoneNo);
        adhaarID=findViewById(R.id.adhaarID);
        address=findViewById(R.id.address);
        profession=findViewById(R.id.profession);
        create_password=findViewById(R.id.createpass);
        confirm_password=findViewById(R.id.confirmpass);

        submit_btn = findViewById(R.id.submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtil.openFBReference("Users");
                firebaseDatabase = FirebaseUtil.firebaseDatabase;
                reference= FirebaseUtil.reference;
                SaveUserData();
                if(checkValidity()) {
                    System.out.println("Data is correct");
                    firebaseAuth.createUserWithEmailAndPassword(emailID,createPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dataclass = new UserData(name, emailID, add, phone, adhaar, createPass, profess);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("UserData")
                                        .setValue(dataclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Registration.this,"Data is Saved in authentication",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(Registration.this, Login.class));
                                        }
                                        else{
                                            Toast.makeText(Registration.this,"Data is Not Saved in authentication",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    System.out.println("Data is wrong");
                    Toast.makeText(Registration.this, "Data is Not Valid.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void SaveUserData() {
        /////////////////Get all values//////////////////////
        try {
            name = fullName.getEditText().getText().toString();
            emailID = email.getEditText().getText().toString();
            phone = Long.parseLong(phoneNo.getEditText().getText().toString());
            adhaar = Long.parseLong(adhaarID.getEditText().getText().toString());
            add = address.getEditText().getText().toString();
            createPass = create_password.getEditText().getText().toString();
            profess = profession.getEditText().getText().toString();

        }catch (Exception e){
            Toast.makeText(Registration.this, "Invalid Action", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private boolean checkValidity() {
        UserValidation validate = new UserValidation();
        boolean validate1 = validate.validateUser(Registration.this);
        return validate1;
    }

}