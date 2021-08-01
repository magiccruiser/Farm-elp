package com.example.farm_elp.Activity.DrawerActivity.ContactSeller;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity.MainActivity_NavigationPage;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.UserData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    DatabaseReference ownBookRef;

    ContactAdapter adapter;

    FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();

    ContactSevice service = new ContactSevice();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        try {
            Toolbar toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            assert actionbar != null;
          //  actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            setTitle("Seller Contact Details");

        }catch (Exception e){
            Toast.makeText(this,"Toolbar error",Toast.LENGTH_LONG).show();
        }

        FirebaseUtil.openFBReference("Users");
        ownBookRef = FirebaseUtil.reference;

        //setUpRecyclerView
        FirebaseRecyclerOptions<UserData> options = new FirebaseRecyclerOptions.Builder<UserData>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(logged_user.getUid())
                        .child("SellersContactReceived"), UserData.class)
                .build();

        adapter = new ContactAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.notification_recycler_public_itemView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //------------------------Delete By Swiping--------------------------
        service.deleteOnSwipe(adapter,recyclerView, ContactActivity.this);

        ////////////////////////////event......on contact click///////////////////
        Dialog dialog=new Dialog(ContactActivity.this);
        service.openContactDetailPopup(dialog, adapter);

        ////////////////////////////dialog on clicking call///////////////////////////
        adapter.setOnLikeClickListener((dataSnapshot, position) -> {
            System.out.println("Liked :"+dataSnapshot.getValue());
            String phone = String.valueOf(dataSnapshot.getValue(UserData.class).getPhoneNo());
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+phone));
            if (ActivityCompat.checkSelfPermission( ContactActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
            else {
                /* Exibe a tela para o usuário dar a permissão. */
                ActivityCompat.requestPermissions(
                        ContactActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CODE);
            }
            // Toast.makeText(contactActivity,"Response Send", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                }
            }
        }
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
        startActivity(new Intent(ContactActivity.this, MainActivity_NavigationPage.class));
    }

}