package com.example.farm_elp.Navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.farm_elp.R;
import com.example.farm_elp.Activity.SettingActivity;
import com.example.farm_elp.Fragments.HomeFragment;
import com.example.farm_elp.Activity.Login;
import com.example.farm_elp.MyProduct_Activity.MyProductsActivity;
import com.example.farm_elp.Fragments.NotificationFragment;
import com.example.farm_elp.Fragments.ProfileFragment;
import com.example.farm_elp.model.Loged_in_User;
import com.example.farm_elp.model.UserData;
import com.google.android.material.navigation.NavigationView;

public class MainActivity_NavigationPage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //toggle is the three-line one left which is used to open drawer....
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    Fragment temp;
    FragmentManager fm;
    FragmentTransaction ft;
    int n;

    View hView;
    Loged_in_User loggedUser;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_navigation_page);
        //Variables initialization--------------------------------------------------
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navmenu);
        toolbar = findViewById(R.id.toolBar);

        //Toolbar--------------------------------------------------------------------
        setSupportActionBar(toolbar);
/*
        //Hide or Show items on menu
        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.login).setVisible(False);
        menu.findItem(R.id.logout).setVisible(False);
        menu.findItem(R.id.profile).setVisible(False);
*/


        //Navigation menu------------------------------------------------------------
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //Calling home_fragment as the first fragment layout on screen
        getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment()).commit();

        //Now navigating to different fragment using the navigation menu items using onNavigationItemSelectedListener event
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                onItemSelected(menuItem);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        hView =navigationView.getHeaderView(0);
        userName = hView.findViewById(R.id.name_onTop);

        loggedUser =new Loged_in_User();
        Menu menu=navigationView.getMenu();
        if(loggedUser.getUID().equals("")){
            menu.findItem(R.id.addItem).setVisible(false);
            menu.findItem(R.id.logout).setVisible(false);
            Toast.makeText(this,"NO USER", Toast.LENGTH_LONG).show();
        }
        else{
            menu.findItem(R.id.login).setVisible(false);
            userName.setText(loggedUser.getLogged_UserName());
        }
    }





    @Override
    public void onBackPressed() {
        n++;
        if (n == 1) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {

                temp = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
            }
        } else if (n == 2) {
            System.exit(0);
        }
    }

    @SuppressLint("NonConstantResourceId")
    void onItemSelected(@NonNull MenuItem menuItem) {
        n = 0;
        String fr = "";
        switch (menuItem.getItemId()) {
            case R.id.home:
                temp = new HomeFragment();
                fr = "frag";
                break;
            case R.id.addItem:
                startActivity(new Intent(MainActivity_NavigationPage.this, MyProductsActivity.class));
                break;
            case R.id.login:
                Intent i = new Intent(MainActivity_NavigationPage.this, Login.class);
                i.putExtra("mainActivity","one");
                startActivity(i);
                break;
            case R.id.profile:
                temp = new ProfileFragment();
                fr = "frag";
                break;
            case R.id.notification:
                temp = new NotificationFragment();
                fr = "frag";
                break;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                /*
                //Initialize alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder("name of the activity");
                //set Title
                builder.setTitle("Logout");
                //Set message
                builder.setMessage("Are you sure you want to logout?");
                //Postive yes button
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //finish activity
                        activity.finishAffinity();
                        //exitApp
                        System.exit(0);
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
                */
                break;
            case R.id.settings:
                startActivity(new Intent(MainActivity_NavigationPage.this, SettingActivity.class));
                break;
            case R.id.rating:
                Toast.makeText(getApplicationContext(), "Rating", Toast.LENGTH_SHORT).show();
                break;
        }
        if (fr.equals("frag"))
            getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
    }
}