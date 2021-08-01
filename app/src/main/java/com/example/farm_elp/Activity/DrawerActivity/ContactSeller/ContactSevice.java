package com.example.farm_elp.Activity.DrawerActivity.ContactSeller;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.UserData;

public class ContactSevice {
    private static final int REQUEST_CODE = 1;
    TextView buyerName, buyerAddress, buyerItem, buyerphone, buyerEmail;
    TextView discription, itemName;
    ImageView closePopUp;


    public void openContactDetailPopup(Dialog dialog, ContactAdapter adapter) {
        adapter.setOnItemClickListener((dataSnapshot, position) -> {
            dialog.setContentView(R.layout.user_detail);

            System.out.println("Notification"+dataSnapshot.getValue());
            Item item=FirebaseUtil.getItemDataByID(dataSnapshot.getKey());

            //------------------------------Adding Sellers data-----------------------
            FirebaseUtil.openFBReference("Users");

            buyerName = dialog.findViewById(R.id.user_detail_name);
            buyerAddress = dialog.findViewById(R.id.user_detail_address);
            buyerphone = dialog.findViewById(R.id.user_detail_phone);
            buyerItem = dialog.findViewById(R.id.user_item_wanted);
            buyerEmail = dialog.findViewById(R.id.user_detail_email);
            discription = dialog.findViewById(R.id.discription_textView);
            itemName = dialog.findViewById(R.id.item_wanted_textView);

            discription.setText("Seller contact Discription");
            itemName.setText("");


            UserData user = adapter.userValue();

            buyerName.setText(user.getFullName()+"\n"+user.getProfession());
            buyerphone.setText(String.valueOf(user.getPhoneNo()));
            buyerAddress.setText(user.getAddress());
            buyerEmail.setText(user.getEmail());
            buyerItem.setText(item.getItemName());

            closePopUp =dialog.findViewById(R.id.closePopUp);

            closePopUp.setOnClickListener(v -> dialog.dismiss());
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount=-0.8f;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
    }


    public void deleteOnSwipe(ContactAdapter adapter, RecyclerView recyclerView, ContactActivity contactActivity) {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0
                , ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    recyclerView.setTooltipText("Delete");
                }
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
                Toast.makeText(contactActivity, "Request Service Completed", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
