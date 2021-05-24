package com.example.farm_elp.FirebaseUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference reference;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageReference;

    private FirebaseUtil(){}

    public static void openFBReference(String ref){
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        reference=firebaseDatabase.getReference().child(ref);
        connectStorage();
    }

    public static void connectStorage(){
        mStorage=FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference().child("ItemList");
    }
}
