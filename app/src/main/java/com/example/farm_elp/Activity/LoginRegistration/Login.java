package com.example.farm_elp.Activity.LoginRegistration;

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

import com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity.MainActivity_NavigationPage;
import com.example.farm_elp.R;
import com.example.farm_elp.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextInputLayout email, password;
    TextView register;
    TextView forgot_pass;
    ImageView closePopUp;
    Dialog dialog;
    Button login;

    UserData user = new UserData();
    UserData user1 = new UserData();

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
            Toolbar toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            try {
                Intent intentGet = getIntent();
                String parentName = intentGet.getStringExtra("mainActivity");
                if (parentName.equals("one")) {
                    actionbar.setDisplayHomeAsUpEnabled(false);
                }
            } catch (Exception e) {
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

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        mAuth= FirebaseAuth.getInstance();

        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailID = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();

                System.out.println(emailID+"  :  "+pass);
                try {
                    mAuth.signInWithEmailAndPassword(emailID, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this, MainActivity_NavigationPage.class));
                            } else {
                                System.out.println(mAuth.toString());
                                Toast.makeText(Login.this, "Failed to Login, Check Credentials/Register ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
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