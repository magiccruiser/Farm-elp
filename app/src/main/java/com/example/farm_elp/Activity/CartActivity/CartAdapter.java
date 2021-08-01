package com.example.farm_elp.Activity.CartActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.example.farm_elp.model.SignalSend;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartAdapter extends FirebaseRecyclerAdapter<Item, CartAdapter.ShoppingItemHolder> {
    private CartAdapter.OnDetailClickListener listener;
    private CartAdapter.OnContactClickListener listener1;

    public static String status;
    CartService service = new CartService();

    /**
     *
     * @param options
     */
    public CartAdapter(FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    /**
     *
     * @param holder
     * @param position
     * @param model
     */
    @Override
    protected void onBindViewHolder(@NonNull CartAdapter.ShoppingItemHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getItemName());
        Glide.with(holder.itemImage.getContext()).load(model.getItemUrl()).centerCrop().into(holder.itemImage);


        //-----------------------------check status and change call icon accordingly-----------------------
        String key = getSnapshots().getSnapshot(position).getKey();

        checkAndChange(key, holder);

    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CartAdapter.ShoppingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item,parent,false);
        return new CartAdapter.ShoppingItemHolder(v);
    }

    /**
     *  @param position
     * @param s
     * @param key
     */
    public void deleteItem(int position, String s, String key){
        FirebaseUtil.openFBReference("Users");
        DatabaseReference ref = FirebaseUtil.reference;
        ref.child(FirebaseUtil.getLoggedUserID())
                .child("SignalSend").child(s).getRef().removeValue();
        ref.child(key)
                .child("SignalReceived").child(s).getRef().removeValue();
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }


    /**
     *
     * @param key
     * @param holder
     */
    private void checkAndChange(String key, ShoppingItemHolder holder) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("Users").child(currentUser.getUid()).child("SignalSend").child(key).orderByChild(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("Found for(" + key + ") :" + snapshot.getValue());
                        status = snapshot.getValue(SignalSend.class).getApprovedStatus();
                        switch (status) {
                            case "Yes":
                               // service.setContact(currentUser,snapshot.getValue(SignalSend.class).itemUserID);
                                System.out.println("OutPut is yes : " + status);
                                holder.contact.setEnabled(true);
                                holder.phoneIcon.setImageResource(R.drawable.ic_can_call);
                                holder.status.setImageResource(R.drawable.ic_approved);
                                break;
                            case "No":
                                System.out.println("OutPut is no : " + status);
                                holder.contact.setEnabled(false);
                                holder.phoneIcon.setImageResource(R.drawable.ic_cannot_call);
                                holder.status.setImageResource(R.drawable.ic_notapproved);
                                break;
                            case "":
                                System.out.println("OutPut is wait : " + status);
                                holder.contact.setEnabled(false);
                                holder.phoneIcon.setImageResource(R.drawable.ic_cannot_call);
                                holder.status.setImageResource(R.drawable.ic_wait_for_approval);
                                break;
                            default:
                                System.out.println("Error Occurred/ Null value");
                                break;
                        }
                        return;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Error Occurred");
                    }
                });
    }

    /**
     * Holder Class
     */
    class ShoppingItemHolder extends RecyclerView.ViewHolder {
        LinearLayout itemDetail;
        TextView itemName;
        ImageView itemImage, status, phoneIcon;
        LinearLayout contact;

        public ShoppingItemHolder(@NonNull View itemView) {
            super(itemView);
            itemDetail = itemView.findViewById(R.id.shopping_itemName);
            itemName = itemView.findViewById(R.id.shopping_item);
            itemImage = itemView.findViewById(R.id.shopping_itemImage);
            contact = itemView.findViewById(R.id.shopping_call);
            status = itemView.findViewById(R.id.approval_status);
            phoneIcon = itemView.findViewById(R.id.call);

            /////////////////on item click/////////////////
            itemDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onDetailClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onDetailClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener1.onContactClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnDetailClickListener{
        void onDetailClick(DataSnapshot documentSnapshot, int position);
    }
    public interface OnContactClickListener{
        void onContactClick(DataSnapshot documentSnapshot, int position);
    }
    public void setOnDetailClickListener(CartAdapter.OnDetailClickListener listener){
        this.listener=listener;
    }
    public void setOnContactClickListener(CartAdapter.OnContactClickListener listener){
        this.listener1=listener;
    }
}