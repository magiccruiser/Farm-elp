package com.example.farm_elp.MyProduct_Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.Navigation.MainActivity_NavigationPage;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.Adapters.ItemAdapter;
import com.example.farm_elp.model.Loged_in_User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProductsActivity extends AppCompatActivity{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ownBookRef;

    ItemAdapter adapter;
    ImageView closePopUp;

    TextView itemDesc, itemName;
    ImageView itemImage;

    Loged_in_User loggedUser = new Loged_in_User();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        setTitle("My Items");

        FirebaseUtil.openFBReference("ItemList");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        ownBookRef = FirebaseUtil.reference;

        FloatingActionButton buttonAddItem = findViewById(R.id.button_add_item);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProductsActivity.this, ItemDetail.class));
            }
        });

        //setUpRecyclerView
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ItemList")
                        .orderByChild("uid").equalTo(loggedUser.getUID()), Item.class)
                .build();

        adapter = new ItemAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_itemview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        //////////////////////for deleting an item by swiping///////////////////////////
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);


        ////////////////////////////event......on item click///////////////////
        Dialog dialog=new Dialog(MyProductsActivity.this);
        adapter.setOnItemClickListener((dataSnapshot, position) -> {
            Item item=dataSnapshot.getValue(Item.class);
            String id=dataSnapshot.getKey();


            dialog.setContentView(R.layout.item_detail_popup);
            itemDesc = dialog.findViewById(R.id.popup_item_description);
            itemName = dialog.findViewById(R.id.popup_item_name);
            itemImage =dialog.findViewById(R.id.popup_item_image);


            closePopUp =dialog.findViewById(R.id.closePopUp);

            itemName.setText(item.getItemName());
            itemDesc.setText(item.getDescription());
            Glide.with(itemImage.getContext()).load(item.getItemUrl()).centerCrop().into(itemImage);

            closePopUp.setOnClickListener(v -> dialog.dismiss());
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount=-0.8f;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
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
        startActivity(new Intent(MyProductsActivity.this,MainActivity_NavigationPage.class));
    }

}