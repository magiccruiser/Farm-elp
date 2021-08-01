package com.example.farm_elp.FirebaseUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference reference;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageReference;

    private static UserData user = new UserData();
    public static Item item = new Item();
    public static int n=1;

    private static ProgressDialog dialog;

    private FirebaseUtil() {
    }

    public static void openFBReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        reference = firebaseDatabase.getReference().child(ref);
        connectStorage();
    }

    public static void connectStorage() {
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference().child("ItemList");

    }


    public static UserData getUserDataByReference(String child) {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference(child);
        reference.child(loggedUser.getUid()).child("UserData")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(UserData.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return user;
    }

    public static UserData getUserDataByID(String ID) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(ID).child("UserData").orderByChild(ID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(UserData.class);
                        if(dialog != null) {
                            dialog.dismiss();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return user;
    }



    public static Item getItemDataByID(String ID) {
        reference = FirebaseDatabase.getInstance().getReference("ItemList");
        reference.child(ID).orderByChild(ID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        item = snapshot.getValue(Item.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return item;
    }

    public static String getLoggedUserID() {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static UserData getUserDataByIDshowDialog(String ID, Dialog dialog, TextView sellerAddress, TextView sellerName, TextView sellerProfession) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(ID).child("UserData").orderByChild(ID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(UserData.class);
                        sellerName.setText(user.getFullName());
                        sellerAddress.setText(user.getAddress());
                        sellerProfession.setText(user.getProfession());
                        dialog.show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return user;
    }

    public static Item getItemDataByID(String ID, Dialog dialog) {
        reference = FirebaseDatabase.getInstance().getReference("ItemList");
        reference.child(ID).orderByChild(ID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        item = snapshot.getValue(Item.class);
                        dialog.show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return item;
    }

    public static UserData getUserDataByID(String ID, TextView userName) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(ID).child("UserData").orderByChild(ID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(UserData.class);
                        userName.setText(user.getFullName());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return user;
    }
}
