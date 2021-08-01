package com.example.farm_elp.Activity.CartActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.farm_elp.Activity.MainPageActivity.PublicPageActivity.MainPage;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity.MainActivity_NavigationPage;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart_ContactList extends AppCompatActivity {
    FirebaseUser logged_User = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ownBookRef;

    CartAdapter adapter;
    CartService service = new CartService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__shopping_list);

        RecyclerView recyclerView = findViewById(R.id.shopping_recycler_public_itemView);

        service.setToolbar(Cart_ContactList.this);

        //-----------------------setUpRecyclerView---------------------------
        ownBookRef = FirebaseUtil.reference;

        //service.setItemInRecyclerView(logged_User,adapter,Cart_ContactList.this,recyclerView);
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(logged_User.getUid())
                        .child("CartItems"), Item.class)
                .build();

        adapter = new CartAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart_ContactList.this));
        recyclerView.setAdapter(adapter);

        //------------------------Delete By Swiping--------------------------
        service.deleteOnSwipe(adapter,recyclerView, Cart_ContactList.this);

        //------------------------event......on contact click-------------------
        Dialog dialog=new Dialog(Cart_ContactList.this);
        try {
            service.openDetailPopup(dialog, adapter);
        }catch (Exception e){
            Toast.makeText(this,"Dialog error",Toast.LENGTH_SHORT).show();
        }

        //----------------------------dialog on clicking call-------------------------
        Dialog dialog1=new Dialog(Cart_ContactList.this);
       // sendRequest = dialog1.findViewById(R.id.send_call_request);
        service.openContactPopup(dialog1,adapter,Cart_ContactList.this);
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
        Intent intent = new Intent(this, MainPage.class);
        switch (getIntent().getStringExtra("Sowing")){
            case "Seeds":
                intent.putExtra("Main","Seeds");
                startActivity(intent);
                break;
            case "Fruits":
                intent.putExtra("Main","Fruits");
                startActivity(intent);
                break;
            case "Vegetables":
                intent.putExtra("Main","Vegetables");
                startActivity(intent);
                break;
            case "Zero":
                startActivity(new Intent(Cart_ContactList.this, MainActivity_NavigationPage.class));
                break;
            default:
                startActivity(new Intent(Cart_ContactList.this, MainActivity_NavigationPage.class));
        }
    }

}