package com.example.farm_elp.Activity.DrawerActivity.Notification;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.SignalReceived;
import com.example.farm_elp.model.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService {

    TextView buyerName, buyerAddress, buyerItem, buyerphone, buyerEmail;
    ImageView closePopUp;


    public void openDetailPopup(Dialog dialog, NotificationAdapter adapter) {
        adapter.setOnItemClickListener((dataSnapshot, position) -> {
            dialog.setContentView(R.layout.user_detail);

            System.out.println("Notification" + dataSnapshot.getValue());

            //------------------------------Adding Sellers data-----------------------
            FirebaseUtil.openFBReference("Users");

            buyerName = dialog.findViewById(R.id.user_detail_name);
            buyerAddress = dialog.findViewById(R.id.user_detail_address);
            buyerphone = dialog.findViewById(R.id.user_detail_phone);
            buyerItem = dialog.findViewById(R.id.user_item_wanted);
            buyerEmail = dialog.findViewById(R.id.user_detail_email);


            UserData user = adapter.userValue();

            buyerName.setText(user.getFullName() + "\n" + user.getProfession());
            buyerphone.setText(String.valueOf(user.getPhoneNo()));
            buyerAddress.setText(user.getAddress());
            buyerEmail.setText(user.getEmail());

            Item item = FirebaseUtil.getItemDataByID(dataSnapshot.getKey(), dialog);
            buyerItem.setText(item.getItemName());

            closePopUp = dialog.findViewById(R.id.closePopUp);

            closePopUp.setOnClickListener(v -> dialog.dismiss());
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = -0.8f;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        });
    }

    public void deleteOnReplying(NotificationAdapter adapter, Activity notificationActivity) {
        adapter.setOnLikeClickListener((dataSnapshot, position) -> {
            System.out.println("Liked :" + dataSnapshot.getValue());
            adapter.deleteItem(position);

            String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ProgressDialog dialog = new ProgressDialog(notificationActivity);
            dialog.setMessage("Sending your Reply");
            dialog.show();

            FirebaseUtil.openFBReference("Users");
            DatabaseReference ref = FirebaseUtil.reference;
            ref.child(dataSnapshot.getValue(SignalReceived.class).getRequestedUserUID())
                    .child("SignalSend").child(dataSnapshot.getKey()).child("approvedStatus").setValue("Yes");

            ref.child(ID).child("UserData").orderByChild(ID)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            setContact(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    snapshot.getValue(UserData.class)
                                    , dataSnapshot.getValue(SignalReceived.class).getRequestedUserUID(), notificationActivity);
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            Toast.makeText(notificationActivity, "Response Send", Toast.LENGTH_LONG).show();
        });
        adapter.setOnDislikeClickListener((dataSnapshot, position) -> {
            System.out.println("Disliked :" + dataSnapshot.getKey());
            adapter.deleteItem(position);

            FirebaseUtil.openFBReference("Users");
            DatabaseReference ref = FirebaseUtil.reference;
            ref.child(dataSnapshot.getValue(SignalReceived.class).getRequestedUserUID())
                    .child("SignalSend").child(dataSnapshot.getKey()).child("approvedStatus").setValue("No");
            Toast.makeText(notificationActivity, "Response Send", Toast.LENGTH_LONG).show();
        });
    }

    public void setContact(String sellerID, UserData userDataByID, String buyerID, Activity notificationActivity) {
        System.out.println("Item added in the sellerContact with key : " + buyerID);

        ProgressDialog dialog = new ProgressDialog(notificationActivity);
        dialog.setMessage("Sending your Reply");
        dialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(buyerID).child("SellersContactReceived").child(sellerID).setValue(userDataByID).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
                Toast.makeText(notificationActivity, "Reply sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
