package com.example.farm_elp.StartingActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.farm_elp.Activity.Login;
import com.example.farm_elp.Activity.Registration;
import com.example.farm_elp.Navigation.MainActivity_NavigationPage;
import com.example.farm_elp.R;
import com.example.farm_elp.model.ItemScrollModel;

import java.util.ArrayList;
import java.util.List;

public class StartingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemScrollModel> itemScrollModelList;
    private ItemScrollModel_Adapter itemScrollModel_adapter;
    Button skip_btn;
    LinearLayout login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        ////////////on clicking login///////////////
        login=findViewById(R.id.linear_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        ////////////on clicking login///////////////
        register=findViewById(R.id.linear_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

        //////////////////Clicking skip button opens the main screen/////////////////////////
        skip_btn=findViewById(R.id.skip_btn);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, MainActivity_NavigationPage.class));
            }
        });
        //////////////////Hiding Status Bar start/////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        //////////////////Hiding Status Bar end/////////////////////////

        recyclerView = findViewById(R.id.scrollImage_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        itemScrollModelList = new ArrayList<>();
        itemScrollModelList.add(new ItemScrollModel(R.drawable.photo));
        itemScrollModelList.add(new ItemScrollModel(R.drawable.photo));
        itemScrollModelList.add(new ItemScrollModel(R.drawable.photo));
        itemScrollModelList.add(new ItemScrollModel(R.drawable.photo));
        itemScrollModelList.add(new ItemScrollModel(R.drawable.photo));

        itemScrollModel_adapter = new ItemScrollModel_Adapter(itemScrollModelList, this);
        recyclerView.setAdapter(itemScrollModel_adapter);
        itemScrollModel_adapter.notifyDataSetChanged();

        /////////////call autoscroll//////////////////
        autoScroll();
    }

    public void autoScroll(){
        final int speedScroll=0;
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
           int count=0;
            @Override
            public void run() {
               if(count==itemScrollModel_adapter.getItemCount())
                   count=0;
               if(count<itemScrollModel_adapter.getItemCount()){
                   recyclerView.smoothScrollToPosition(++count);
                   handler.postDelayed(this, speedScroll);
               }
            }
        };
handler.postDelayed(runnable, speedScroll);
    }
}