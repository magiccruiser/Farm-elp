package com.example.farm_elp.Activity.DrawerActivity.ContactSeller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.UserData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class ContactAdapter extends FirebaseRecyclerAdapter<UserData, ContactAdapter.ContactHolder> {
    private OnItemClickListener listener;
    private OnLikeClickListener listener1;
    UserData user;
    private OnDislikeClickListener listener2;


    public ContactAdapter(FirebaseRecyclerOptions<UserData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ContactAdapter.ContactHolder holder, int position, @NonNull UserData model) {
        FirebaseUtil.openFBReference("Users");
        holder.UserName.setText(model.getFullName());
        user = model;
    }

    public UserData userValue() {
        return user;
    }


    @NonNull
    @Override
    public ContactAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_seller, parent, false);
        return new ContactAdapter.ContactHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }


    class ContactHolder extends RecyclerView.ViewHolder {
        TextView UserName;
        ImageView contactSeller;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.notification_user_name);
            contactSeller = itemView.findViewById(R.id.call_seller);

            /////////////////on item click/////////////////
            UserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            contactSeller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener1.onLikeClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(ContactAdapter.OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnLikeClickListener{
        void onLikeClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnLikeClickListener(ContactAdapter.OnLikeClickListener listener){
        this.listener1=listener;
    }

    public interface OnDislikeClickListener{
        void onDislikeClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnDislikeClickListener(ContactAdapter.OnDislikeClickListener listener){
        this.listener2=listener;
    }
}
