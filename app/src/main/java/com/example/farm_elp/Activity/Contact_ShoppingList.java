package com.example.farm_elp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.Adapters.PublicItemAdapter;
import com.example.farm_elp.Adapters.ShoppingItemAdapter;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.Navigation.MainActivity_NavigationPage;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.UserData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact_ShoppingList extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ownBookRef;

    ShoppingItemAdapter adapter;
    ImageView closePopUp;

    TextView itemDesc, itemName, sellerName, sellerAddress;
    Button sendRequest, cancelRequest;
    ImageView itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__shopping_list);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        setTitle("Contact [Basket]");

        FirebaseUtil.openFBReference("ItemList");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        ownBookRef = FirebaseUtil.reference;

        //setUpRecyclerView
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(FirebaseDatabase.getInstance().getReference("ItemList")
                        .orderByChild("itemType").equalTo("sowing"), Item.class)
                .build();

        adapter = new ShoppingItemAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.shopping_recycler_public_itemView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        ////////////////////////////event......on contact click///////////////////
        Dialog dialog=new Dialog(Contact_ShoppingList.this);
        adapter.setOnDetailClickListener((dataSnapshot, position) -> {
            dialog.setContentView(R.layout.item_detail_popup);

            Item item=dataSnapshot.getValue(Item.class);

            //------------------------------Adding Sellers data-----------------------
            DatabaseReference ref;
            FirebaseUtil.openFBReference("Users");
            ref=FirebaseUtil.reference;

            sellerName = dialog.findViewById(R.id.popup_item_user_name);
            sellerAddress = dialog.findViewById(R.id.popup_item_user_address);

            ref.orderByChild(item.getUID()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.getKey().equals(item.getUID())){
                        sellerName.setText(snapshot.getValue(UserData.class).getFullName());
                        sellerAddress.setText(snapshot.getValue(UserData.class).getAddress());
                        dialog.show();
                        //  Toast.makeText(SowingActivity.this, "Seller Successful" , Toast.LENGTH_LONG).show();
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


            itemDesc = dialog.findViewById(R.id.popup_item_description);
            itemName = dialog.findViewById(R.id.popup_item_name);
            itemImage =dialog.findViewById(R.id.popup_item_image);


            closePopUp =dialog.findViewById(R.id.closePopUp);

            assert item != null;
            itemName.setText(item.getItemName());
            itemDesc.setText(item.getDescription());
            Glide.with(itemImage.getContext()).load(item.getItemUrl()).centerCrop().into(itemImage);

            closePopUp.setOnClickListener(v -> dialog.dismiss());
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount=-0.8f;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            if(item.getUID().equals("")){
                dialog.show();
            }
        });

        ////////////////////////////dialog on clicking call///////////////////////////
        Dialog dialog1=new Dialog(Contact_ShoppingList.this);
        adapter.setOnContactClickListener((dataSnapshot, position) -> {
            Item item=dataSnapshot.getValue(Item.class);

            dialog1.setContentView(R.layout.contact_popup);

            closePopUp =dialog1.findViewById(R.id.closePopUp_req);
            cancelRequest = dialog1.findViewById(R.id.cancel_call_request);
            sendRequest = dialog1.findViewById(R.id.send_call_request);

            closePopUp.setOnClickListener(v -> dialog1.dismiss());
            cancelRequest.setOnClickListener(v -> dialog1.dismiss());
            WindowManager.LayoutParams lp = dialog1.getWindow().getAttributes();
            lp.dimAmount=-0.8f;
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.show();
        });
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

        switch (getIntent().getStringExtra("Sowing")){
            case "first":
                startActivity(new Intent(Contact_ShoppingList.this, SowingActivity.class));
                break;
            case "second":
                startActivity(new Intent(Contact_ShoppingList.this, ToolsActivity.class));
                break;
            case "third":
                startActivity(new Intent(Contact_ShoppingList.this, PlacesActivity.class));
                break;
            default:
                startActivity(new Intent(Contact_ShoppingList.this, MainActivity_NavigationPage.class));
        }
    }

}