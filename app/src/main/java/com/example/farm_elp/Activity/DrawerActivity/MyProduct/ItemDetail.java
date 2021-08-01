package com.example.farm_elp.Activity.DrawerActivity.MyProduct;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.Validate.ItemValidation;
import com.example.farm_elp.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ItemDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();

    TextInputLayout name, quantity, description;
    Button choosebtn;
    private static String url;
    String type;
    int once=1;
    private ImageView itemImage;
    Spinner type_spinner;

    private Uri imageUri;
    ProgressDialog dialog;
    private static long progress;
    boolean uploaded = false;

    Item item;
    ItemValidation validation;

    public static final int PICK_IMAGE_REQUEST = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        setTitle("Item Details");
//-------------------------------------Item Unit --------------------------------------------------
     /*   item_spinner = findViewById(R.id.item_unit);
        ArrayAdapter<CharSequence> item_adapter =ArrayAdapter.createFromResource(this
                ,R.array.units, android.R.layout.simple_spinner_item);
        item_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item_spinner.setAdapter(item_adapter);
        item_spinner.setOnItemSelectedListener(this);

      */

//-------------------------------------Item Type --------------------------------------------------
        type_spinner = findViewById(R.id.item_type);
        ArrayAdapter<CharSequence> type_adapter =ArrayAdapter.createFromResource(ItemDetail.this
                ,R.array.itemType, android.R.layout.simple_spinner_item);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(type_adapter);
        type_spinner.setOnItemSelectedListener(this);


        //-------------------------------------Initialize --------------------------------------------------
        name = findViewById(R.id.item_name);
        quantity = findViewById(R.id.item_quantity);
        description = findViewById(R.id.item_Description);
        choosebtn = findViewById(R.id.choose_photo_btn);
        itemImage = findViewById(R.id.choosed_itemImage);

        //--------------------------------Clicking choose to choose image---------------------------------
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            itemImage.setImageURI(imageUri);
            uploadImage();
        }
    }

    private void uploadImage() {
        dialog = new ProgressDialog(ItemDetail.this);
        FirebaseUtil.openFBReference("ItemList");
        StorageReference ref = FirebaseUtil.mStorageReference.child(imageUri.getLastPathSegment());
        ref.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploaded=true;
                        url=uri.toString();
                        System.out.println(url);
                        saveItem();
                    }
                });
                Toast.makeText(ItemDetail.this, "Item is Saved", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(this, new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                dialog.setMessage("Uploading ");
                dialog.show();
                once++;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_itemmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.save_item:
                uploadItem();
                clean();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadItem() {
        FirebaseUtil.openFBReference("ItemList");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference= FirebaseUtil.reference;
        validation =new ItemValidation();
        if( validation.validateItem(ItemDetail.this,item)) {
            if(uploaded == true) {
                databaseReference.push().setValue(item);
                Toast.makeText(ItemDetail.this, "Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MyProductsActivity.class));
            }
            else{
                Toast.makeText(ItemDetail.this, "Not uploaded Item on server till yet", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clean() {

    }

    private void saveItem() {
        String name = this.name.getEditText().getText().toString();
        String description = this.description.getEditText().getText().toString();
        int quantity = Integer.parseInt(this.quantity.getEditText().getText().toString());
        item = new Item(url, name, description, quantity, type, logged_user.getUid());
    }
}
