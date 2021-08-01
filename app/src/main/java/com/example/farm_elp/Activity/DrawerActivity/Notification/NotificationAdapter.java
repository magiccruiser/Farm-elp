package com.example.farm_elp.Activity.DrawerActivity.Notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm_elp.FirebaseUtils.FirebaseUtil;
import com.example.farm_elp.R;
import com.example.farm_elp.model.SignalReceived;
import com.example.farm_elp.model.UserData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class NotificationAdapter extends FirebaseRecyclerAdapter<SignalReceived, NotificationAdapter.NotificationItemHolder> {
    private OnItemClickListener listener;
    private OnLikeClickListener listener1;
    UserData user;
    private OnDislikeClickListener listener2;


    public NotificationAdapter(FirebaseRecyclerOptions<SignalReceived> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotificationAdapter.NotificationItemHolder holder, int position, @NonNull SignalReceived model) {
        FirebaseUtil.openFBReference("Users");
        user = FirebaseUtil.getUserDataByID(model.requestedUserUID,holder.UserName);
        holder.UserName.setText(user.getFullName());
    }

    public UserData userValue() {
        return user;
    }


    @NonNull
    @Override
    public NotificationAdapter.NotificationItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.NotificationItemHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    class NotificationItemHolder extends RecyclerView.ViewHolder {
        TextView UserName;
        ImageView approve, notApprove;

        public NotificationItemHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.notification_user_name);
            approve = itemView.findViewById(R.id.notification_approval_status_yes);
            notApprove = itemView.findViewById(R.id.notification_approval_status_no);

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
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener1.onLikeClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
            notApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener2.onDislikeClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(NotificationAdapter.OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnLikeClickListener{
        void onLikeClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnLikeClickListener(NotificationAdapter.OnLikeClickListener listener){
        this.listener1=listener;
    }

    public interface OnDislikeClickListener{
        void onDislikeClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnDislikeClickListener(NotificationAdapter.OnDislikeClickListener listener){
        this.listener2=listener;
    }
}