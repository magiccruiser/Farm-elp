package com.example.farm_elp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.Navigation.MainActivity_NavigationPage;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Loged_in_User;
import com.example.farm_elp.model.UserData;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.spec.ECField;

public class Login extends AppCompatActivity {
    TextInputLayout phone, password;
    TextView register;
    TextView forgot_pass;
    ImageView closePopUp;
    Dialog dialog;
    Button login;

    UserData user = new UserData();
    UserData user1 = new UserData();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Toolbar toolbar= findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar=getSupportActionBar();
        try {
            Intent intentGet = getIntent();
            String parentName = intentGet.getStringExtra("mainActivity");
            System.out.println(parentName);
            if (parentName.equals("one")) {
                actionbar.setDisplayHomeAsUpEnabled(false);
            }
        }catch(Exception e){
            System.out.println("Error Occurred");
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        actionbar.setDisplayShowHomeEnabled(true);
        setTitle("User Login");

        ////////////// on Clicking for registration /////////////////
        register = findViewById(R.id.registerTextView);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registration.class);
                i.putExtra("loginActivity","one");
                startActivity(i);
            }
        });

        ///////////////////// on clicking forgot password //////////////////
        forgot_pass=findViewById(R.id.forgotpass);
        dialog=new Dialog(this);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });

        //-----------------------------------On Clicking Login----------------------------------

        login = findViewById(R.id.login_btn);
        phone = findViewById(R.id.login_phoneNo);
        password = findViewById(R.id.login_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtil.openFBReference("Users");
                firebaseDatabase = FirebaseUtil.firebaseDatabase;
                reference= FirebaseUtil.reference;
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        user = snapshot.getValue(UserData.class);
                        String pass = password.getEditText().getText().toString();
                        long phn = Long.parseLong(phone.getEditText().getText().toString());
                        if(pass.equals(user.getPassword()) && phn == user.getPhoneNo()){
                            System.out.println(snapshot.getKey());
                            Loged_in_User userlogedIn = new Loged_in_User();
                            userlogedIn.setUID(snapshot.getKey());
                            userlogedIn.setLogged_UserName(user.getFullName());
                            userlogedIn.setLogged_Phone(user.getPhoneNo());
                            user1 = user;
                            System.out.println(snapshot.getKey());
                            Toast.makeText(Login.this, "Login Successful" , Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity_NavigationPage.class));
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    public void showPopUp() {

        dialog.setContentView(R.layout.forgot_password);
        closePopUp=dialog.findViewById(R.id.closePopUp);
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.show();
    }
}