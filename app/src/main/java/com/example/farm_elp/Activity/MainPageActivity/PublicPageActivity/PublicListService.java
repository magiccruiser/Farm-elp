package com.example.farm_elp.Activity.MainPageActivity.PublicPageActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.SignalReceived;
import com.example.farm_elp.model.SignalSend;
import com.example.farm_elp.model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class PublicListService {
    TextView itemDesc, itemName, sellerName, sellerAddress, sellerProfession;
    ImageView itemImage, closePopUp;
    Button sendRequest, cancelRequest;

    SignalSend signalSend = new SignalSend();
    SignalReceived signalReceived = new SignalReceived();

    FirebaseUser logged_user = FirebaseAuth.getInstance().getCurrentUser();

    @SuppressLint("SetTextI18n")
    public void detailPopupDialog(Dialog dialog, PublicItemAdapter adapter, MainPage mainPage) {
        final int[] n = {1};
        adapter.setOnDetailClickListener((dataSnapshot, position) -> {
            Item item = dataSnapshot.getValue(Item.class);
            dialog.setContentView(R.layout.item_detail_popup);

            //------------------------------Adding Sellers data-----------------------
            FirebaseUtil.openFBReference("Users");

            sellerName = dialog.findViewById(R.id.popup_item_user_name);
            sellerAddress = dialog.findViewById(R.id.popup_item_user_address);
            sellerProfession = dialog.findViewById(R.id.popup_item_user_Profession);
            itemDesc = dialog.findViewById(R.id.popup_item_description);
            itemName = dialog.findViewById(R.id.popup_item_name);
            itemImage = dialog.findViewById(R.id.popup_item_image);

            UserData userData =FirebaseUtil.getUserDataByIDshowDialog(item.getUID(),dialog
                    ,sellerAddress,sellerName,sellerProfession);

            closePopUp = dialog.findViewById(R.id.closePopUp);

            assert item != null;
            itemName.setText(item.getItemName());
            itemDesc.setText(item.getDescription());
            Glide.with(itemImage.getContext()).load(item.getItemUrl()).centerCrop().into(itemImage);

            closePopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = -0.8f;
        });
    }

    @SuppressLint("SetTextI18n")
    public void contactPopupDialog(Dialog dialog1, PublicItemAdapter adapter, Activity activity) {
        adapter.setOnContactClickListener((dataSnapshot, position) -> {
            Item item = dataSnapshot.getValue(Item.class);

            dialog1.setContentView(R.layout.contact_popup);

            closePopUp = dialog1.findViewById(R.id.closePopUp_req);
            cancelRequest = dialog1.findViewById(R.id.cancel_call_request);
            TextView callText = dialog1.findViewById(R.id.call_context);
            cancelRequest.setText("Send contact Request");
            callText.setText("Do you want to have the Sellers Contact details? " +
                    "\n\n"+"If Yes then click the button Below" +
                    "\n\n"+"I No then click cross on to right corner");
            // sendRequest = dialog1.findViewById(R.id.send_call_request);

            //-----------------------------adding Item to Cart and sending signal to the User--------------------------------------
            FirebaseUtil.openFBReference("Users");
            DatabaseReference cartRef = FirebaseUtil.reference;
            cancelRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (logged_user != null) {
                        signalSend.requestSent = "Yes";
                        signalReceived.requestReceived = "Yes";
                        signalReceived.requestedUserUID = FirebaseUtil.getLoggedUserID();
                        signalSend.approvedStatus = "";
                        signalReceived.approveTheRequest = "";
                        signalSend.itemUserID = item.getUID();
                        cartRef.child(item.getUID()).child("SignalReceived").child(dataSnapshot.getKey()).setValue(signalReceived);
                        cartRef.child(logged_user.getUid()).child("SignalSend").child(dataSnapshot.getKey()).setValue(signalSend);
                        cartRef.child(logged_user.getUid()).child("CartItems").child(dataSnapshot.getKey()).setValue(item);
                        Toast.makeText(activity, "Item is saved in cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "Log-in/Sign-in", Toast.LENGTH_SHORT).show();
                    }
                    dialog1.dismiss();
                }
            });

            //----------------------------------------end-----------------------------------------------------------------
            UserData userData =FirebaseUtil.getUserDataByID(item.getUID());

            closePopUp.setOnClickListener(v -> dialog1.dismiss());
            WindowManager.LayoutParams lp = dialog1.getWindow().getAttributes();
            lp.dimAmount = -0.8f;
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            if(userData.getEmail() != null) {
                dialog1.show();
            }
        });
    }

}
