package com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.farm_elp.Activity.DrawerActivity.ContactSeller.ContactActivity;
import com.example.farm_elp.Activity.MainPageActivity.PublicPageActivity.MainPage;
import com.example.farm_elp.Activity.LoginRegistration.Login;
import com.example.farm_elp.Activity.DrawerActivity.Notification.NotificationActivity;
import com.example.farm_elp.Activity.SettingActivity;
import com.example.farm_elp.Activity.DrawerActivity.MyProduct.MyProductsActivity;
import com.example.farm_elp.R;
import com.example.farm_elp.Activity_Starting.StartingActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityService {
    TextView userName;
    CardView sowing, tools, places;
    ActionBarDrawerToggle toggle;

    /**
     * Function navigate from main page to another page
     * @param itemId
     * @param mainActivity_navigationPage
     * @param temp
     * @param fr
     */
    public void drawerActivity(int itemId, MainActivity_NavigationPage mainActivity_navigationPage, Fragment temp, String fr) {
        switch (itemId) {
            case R.id.home:
                mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, MainActivity_NavigationPage.class));
                break;
            case R.id.addItem:
                mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, MyProductsActivity.class));
                break;
            case R.id.login:
                Intent i = new Intent(mainActivity_navigationPage, Login.class);
                i.putExtra("mainActivity","one");
                mainActivity_navigationPage.startActivity(i);
                break;
            case R.id.profile:
                mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, ContactActivity.class));
                break;
            case R.id.notification:
                mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, NotificationActivity.class));
                break;
            case R.id.logout:
                Toast.makeText(mainActivity_navigationPage, "Logout", Toast.LENGTH_SHORT).show();
                //Initialize alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity_navigationPage);
                //set Title
                builder.setTitle("Logout");
                //Set message
                builder.setMessage("Are you sure you want to logout?");
                //Postive yes button
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //finish activity
                        FirebaseAuth.getInstance().signOut();
                        //mainActivity_navigationPage.finishAffinity();
                       mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, StartingActivity.class));
                    }
                });
                //Negative no button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss dialog
                        dialog.dismiss();
                    }
                });
                //show dialog
                builder.show();
                break;
            case R.id.settings:
                mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, SettingActivity.class));
                break;
            case R.id.rating:
                Toast.makeText(mainActivity_navigationPage, "Rating", Toast.LENGTH_SHORT).show();
                break;
        }
        if (fr.equals("frag"))
            mainActivity_navigationPage.getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
    }


    /**
     * Function to se visible and invisible the item
     * @param hView
     * @param navigationView
     * @param mainActivity_navigationPage
     * @param loggedUser
     */
    public void addHeaderName(View hView, NavigationView navigationView
            , MainActivity_NavigationPage mainActivity_navigationPage, FirebaseUser loggedUser) {

    }

    /**
     * Main page front activity to select particular type
     * @param mainActivity_navigationPage
     */
    public void selectSeedsFruitVegetable(MainActivity_NavigationPage mainActivity_navigationPage) {
        sowing=mainActivity_navigationPage.findViewById(R.id.sowing);
        tools=mainActivity_navigationPage.findViewById(R.id.tools);
        places=mainActivity_navigationPage.findViewById(R.id.places);
        Intent intent = new Intent(mainActivity_navigationPage, MainPage.class);
        try {
            sowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("Main","Seeds");
                    mainActivity_navigationPage.startActivity(intent);
                  //  mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, SeedsActivity.class));
                }
            });

            places.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("Main","Vegetables");
                    mainActivity_navigationPage.startActivity(intent);
                   // mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, VegetablesActivity.class));
                }
            });

            tools.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("Main","Fruits");
                    mainActivity_navigationPage.startActivity(intent);
                   // mainActivity_navigationPage.startActivity(new Intent(mainActivity_navigationPage, FruitsActivity.class));
                }
            });
        }catch (Exception e){
            Toast.makeText(mainActivity_navigationPage,"Activity Selection Error",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Adding elements to layout
     * @param mainActivity_navigationPage
     * @param drawerLayout
     * @param navigationView
     * @param toolbar
     */
    public void addElementsToLayout(MainActivity_NavigationPage mainActivity_navigationPage, DrawerLayout drawerLayout, NavigationView navigationView, Toolbar toolbar) {

        //Toolbar--------------------------------------------------------------------
        try {
            mainActivity_navigationPage.setSupportActionBar(toolbar);
        }catch (Exception e){
            Toast.makeText(mainActivity_navigationPage,"Toolbar error",Toast.LENGTH_LONG).show();
        }

        //Navigation menu------------------------------------------------------------
        try {
            navigationView.bringToFront();
        }catch (Exception e){
            Toast.makeText(mainActivity_navigationPage,"Navigation error",Toast.LENGTH_LONG).show();
        }
        try {
            toggle = new ActionBarDrawerToggle(mainActivity_navigationPage, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }catch (Exception e){
            Toast.makeText(mainActivity_navigationPage,"Toggle and drawer Error",Toast.LENGTH_LONG).show();
        }

        //Now navigating to different fragment using the navigation menu items using onNavigationItemSelectedListener event
        try {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    mainActivity_navigationPage.onItemSelected(menuItem);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }catch (Exception e){
            Toast.makeText(mainActivity_navigationPage,"Navigation View selection error",Toast.LENGTH_LONG).show();
        }
    }
}
