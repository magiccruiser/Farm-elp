package com.example.farm_elp.Activity.MainPageActivity.MainPageClickItemActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.farm_elp.Activity.CartActivity.Cart_ContactList;
import com.example.farm_elp.Activity.MainPageActivity.PublicPageActivity.MainPage;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.UserData;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity_NavigationPage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();

    //toggle is the three-line one left which is used to open drawer....
    MainActivityService service = new MainActivityService();
    Toolbar toolbar;
    TextView userName;
    Fragment temp;
    int n;

    View hView;

    /**
     * Oncreate Function
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity_navigation_page);

        // FirebaseUtil.setLoggedUserData(MainActivity_NavigationPage.this);
        //Variables initialization--------------------------------------------------
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navmenu);
        toolbar = findViewById(R.id.toolBar);

        try {
            //-------------------ADD elements to mainPage layout
            service.addElementsToLayout(MainActivity_NavigationPage.this, drawerLayout, navigationView, toolbar);

            //----------------------------Go to Activity[seed, fruit, vegetables]-------------------------------------
            service.selectSeedsFruitVegetable(MainActivity_NavigationPage.this);

            //-----------------------------Add User Name to Header----------------------------
            try {
                hView = navigationView.getHeaderView(0);
                userName = hView.findViewById(R.id.name_onTop);
                ImageView icon = hView.findViewById(R.id.user_image_onTop);
                icon.setImageResource(R.drawable.photo);

                Menu menu = navigationView.getMenu();
                menu.findItem(R.id.settings).setVisible(false);
                if (loggedUser == null) {
                    menu.findItem(R.id.notification).setVisible(false);
                    menu.findItem(R.id.addItem).setVisible(false);
                    menu.findItem(R.id.logout).setVisible(false);
                    menu.findItem(R.id.profile).setVisible(false);
                    Toast.makeText(MainActivity_NavigationPage.this, "NO USER", Toast.LENGTH_LONG).show();
                } else {
                    menu.findItem(R.id.login).setVisible(false);
                    FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
                    ProgressDialog dialog = new ProgressDialog(MainActivity_NavigationPage.this);
                    dialog.setMessage("Please Wait");
                    dialog.show();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                    ref.child(loggedUser.getUid()).child("UserData").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            userName.setText(snapshot.getValue(UserData.class).getFullName());
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            } catch (Exception e) {
                Toast.makeText(this, "Header Error", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            String Error = e.getStackTrace().toString();
            Toast.makeText(this, e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainPage.class);
            intent.putExtra("Sowing", Error);
            startActivity(intent);

        }
    }


    /**
     * @param menuItem
     */
    @SuppressLint("NonConstantResourceId")
    void onItemSelected(@NonNull MenuItem menuItem) {
        n = 0;
        String fr = "";
        try {
            service.drawerActivity(menuItem.getItemId(), MainActivity_NavigationPage.this, temp, fr);
        } catch (Exception e) {
            Toast.makeText(this, "Error while Selecting Menu Items", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (logged_user != null) {
            menuInflater.inflate(R.menu.cart, menu);
        }
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                Intent intent = new Intent(this, Cart_ContactList.class);
                intent.putExtra("Sowing", "zero");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * On back press activity
     */
    @Override
    public void onBackPressed() {
        n++;
        if (n == 1) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                Toast.makeText(this, "Press Once more to Exit", Toast.LENGTH_LONG).show();
            }
        } else if (n == 2) {
            MainActivity_NavigationPage.this.finishAffinity();
            System.exit(0);
        }
    }
}