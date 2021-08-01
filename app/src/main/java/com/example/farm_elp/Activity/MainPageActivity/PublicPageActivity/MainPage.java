package com.example.farm_elp.Activity.MainPageActivity.PublicPageActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm_elp.Activity.CartActivity.Cart_ContactList;
import com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity.MainActivity_NavigationPage;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainPage extends AppCompatActivity {

    private TextView mTextView;
    DatabaseReference ownBookRef;
    String activityName;
    PublicItemAdapter adapter;

    PublicListService service = new PublicListService();

    FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_page);

        activityName = getIntent().getStringExtra("Main");


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        setTitle(activityName);

        FirebaseUtil.openFBReference("ItemList");
        ownBookRef = FirebaseUtil.reference;

        //setUpRecyclerView
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(FirebaseDatabase.getInstance().getReference("ItemList")
                        .orderByChild("itemType").equalTo(activityName), Item.class)
                .build();

        adapter = new PublicItemAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_itemView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        //--------------------------event......on contact click-----------------------------
        Dialog dialog=new Dialog(MainPage.this);
        service.detailPopupDialog(dialog,adapter,MainPage.this);

        //-------------------------dialog on clicking call-----------------------------------
        Dialog dialog1=new Dialog(MainPage.this);
        service.contactPopupDialog(dialog1,adapter, MainPage.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(logged_user != null) {
            menuInflater.inflate(R.menu.cart, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.save_item:
                Intent intent = new Intent(this, Cart_ContactList.class);
                intent.putExtra("Sowing",activityName);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        startActivity(new Intent(MainPage.this, MainActivity_NavigationPage.class));
    }

}