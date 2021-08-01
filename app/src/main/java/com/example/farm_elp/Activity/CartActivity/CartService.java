package com.example.farm_elp.Activity.CartActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.SignalSend;
import com.example.farm_elp.model.UserData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartService {
    TextView itemDesc, itemName, sellerName, sellerAddress, callContext, sellerProfession;
    ImageView itemImage, closePopUp;

    Button sendRequest, cancelRequest;
    SignalSend signal = new SignalSend();
    String status = "";

    /**
     * Cart DetailPopup
     * @param dialog
     * @param adapter
     */
    public void openDetailPopup(Dialog dialog, CartAdapter adapter) {
        try {
            adapter.setOnDetailClickListener((dataSnapshot, position) -> {
                dialog.setContentView(R.layout.item_detail_popup);

                Item item = dataSnapshot.getValue(Item.class);

                //------------------------------Adding Sellers data-----------------------
                FirebaseUtil.openFBReference("Users");

                sellerName = dialog.findViewById(R.id.popup_item_user_name);
                sellerAddress = dialog.findViewById(R.id.popup_item_user_address);
                sellerProfession = dialog.findViewById(R.id.popup_item_user_Profession);

                UserData userData =FirebaseUtil.getUserDataByIDshowDialog(item.getUID(),dialog
                        ,sellerAddress,sellerName,sellerProfession);


                itemDesc = dialog.findViewById(R.id.popup_item_description);
                itemName = dialog.findViewById(R.id.popup_item_name);
                itemImage = dialog.findViewById(R.id.popup_item_image);


                closePopUp = dialog.findViewById(R.id.closePopUp);

                assert item != null;
                itemName.setText(item.getItemName());
                itemDesc.setText(item.getDescription());
                Glide.with(itemImage.getContext()).load(item.getItemUrl()).centerCrop().into(itemImage);

                closePopUp.setOnClickListener(v -> dialog.dismiss());
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.dimAmount = -0.8f;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Cart ContactPopup
     * @param dialog1
     * @param adapter
     * @param _cartContactList
     */
    @SuppressLint("SetTextI18n")
    public void openContactPopup(Dialog dialog1, CartAdapter adapter, Cart_ContactList _cartContactList) {
        adapter.setOnContactClickListener((dataSnapshot, position) -> {
            System.out.println(dataSnapshot.getValue(Item.class).getUID());

            dialog1.setContentView(R.layout.contact_popup);
            Item item = dataSnapshot.getValue(Item.class);

            closePopUp = dialog1.findViewById(R.id.closePopUp_req);
            cancelRequest = dialog1.findViewById(R.id.cancel_call_request);
            // sendRequest = dialog1.findViewById(R.id.send_call_request);
            callContext = dialog1.findViewById(R.id.call_context);
            //-----------------------------Calling User -------------------------
            try {
                callContext.setText(String.valueOf("Phone no. -"+FirebaseUtil.getUserDataByID(item.getUID()).getPhoneNo())
                        + "\n\n" +"Email -"+
                        FirebaseUtil.getUserDataByID(item.getUID()).getEmail());
            } catch (Exception e) {
                Toast.makeText(_cartContactList, "Click Once More", Toast.LENGTH_LONG).show();
            }
            closePopUp.setOnClickListener(v -> dialog1.dismiss());
            cancelRequest.setOnClickListener(v -> dialog1.dismiss());
            WindowManager.LayoutParams lp = dialog1.getWindow().getAttributes();
            lp.dimAmount = -0.8f;
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.show();
        });
    }

    /**
     * Delete on Swiping the item
     *
     * @param adapter
     * @param recyclerView
     * @param cart_contactList
     */
    public void deleteOnSwipe(CartAdapter adapter, RecyclerView recyclerView, Cart_ContactList cart_contactList) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0
                , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

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
                adapter.deleteItem(viewHolder.getAdapterPosition(), adapter.getSnapshots()
                        .getSnapshot(viewHolder.getAdapterPosition()).getKey(), adapter.getSnapshots()
                        .getSnapshot(viewHolder.getAdapterPosition()).getValue(Item.class).getUID());
                Toast.makeText(cart_contactList, "Request Service Completed", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
    }


    /**
     * Setting layout of cart
     * @param cart_contactList
     */
    public void setToolbar(Cart_ContactList cart_contactList) {
        try {
            Toolbar toolbar = cart_contactList.findViewById(R.id.toolBar);
            cart_contactList.setSupportActionBar(toolbar);
            ActionBar actionbar = cart_contactList.getSupportActionBar();
            //assert actionbar != null;
            actionbar.setDisplayShowHomeEnabled(true);
            cart_contactList.setTitle("Selected Sellers - Cart");

        }catch (Exception e){
            Toast.makeText(cart_contactList,"Toolbar error",Toast.LENGTH_LONG).show();
        }
    }

}
