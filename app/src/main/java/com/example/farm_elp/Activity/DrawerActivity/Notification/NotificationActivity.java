package com.example.farm_elp.Activity.DrawerActivity.Notification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity.MainActivity_NavigationPage;
import com.example.farm_elp.R;
import com.example.farm_elp.model.SignalReceived;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ownBookRef;

    NotificationAdapter adapter;
    ImageView closePopUp;

    FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();

    NotificationService service = new NotificationService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        try {
            Toolbar toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            assert actionbar != null;
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            setTitle("Notification");

        }catch (Exception e){
            Toast.makeText(this,"Toolbar error",Toast.LENGTH_LONG).show();
        }

        FirebaseUtil.openFBReference("Users");
        ownBookRef = FirebaseUtil.reference;

        //setUpRecyclerView
        FirebaseRecyclerOptions<SignalReceived> options = new FirebaseRecyclerOptions.Builder<SignalReceived>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(logged_user.getUid())
                        .child("SignalReceived"), SignalReceived.class)
                .build();

        adapter = new NotificationAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.notification_recycler_public_itemView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        ////////////////////////////event......on contact click///////////////////
        Dialog dialog=new Dialog(NotificationActivity.this);
        service.openDetailPopup(dialog, adapter);

        ////////////////////////////dialog on clicking call///////////////////////////
        service.deleteOnReplying(adapter, NotificationActivity.this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NotificationActivity.this, MainActivity_NavigationPage.class));
    }

}