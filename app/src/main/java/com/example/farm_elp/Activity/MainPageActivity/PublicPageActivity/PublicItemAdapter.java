package com.example.farm_elp.Activity.MainPageActivity.PublicPageActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class PublicItemAdapter extends FirebaseRecyclerAdapter<Item, PublicItemAdapter.PublicItemHolder> {
private OnDetailClickListener listener;
private OnContactClickListener listener1;


public PublicItemAdapter(FirebaseRecyclerOptions<Item> options) {
        super(options);
        }

@Override
protected void onBindViewHolder(@NonNull PublicItemHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getItemName());
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        Glide.with(holder.itemImage.getContext()).load(model.getItemUrl()).centerCrop().into(holder.itemImage);
        }

@NonNull
@Override
public PublicItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.public_item,parent,false);
        return new PublicItemHolder(v);
        }

public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();
        }

class PublicItemHolder extends RecyclerView.ViewHolder {
    TextView itemName;
    TextView quantity;
    ImageView itemImage;
    LinearLayout contact, details;

    public PublicItemHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.public_item_name);
        quantity = itemView.findViewById(R.id.public_item_quantity);
        itemImage = itemView.findViewById(R.id.public_item_image);
        contact = itemView.findViewById(R.id.public_item_contact);
        details = itemView.findViewById(R.id.public_item_details);


        /////////////////on details click/////////////////
        details.setOnClickListener(new View.OnClickListener() {
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


    public void setOnDetailClickListener(PublicItemAdapter.OnDetailClickListener listener){
        this.listener=listener;
    }
    public void setOnContactClickListener(PublicItemAdapter.OnContactClickListener listener){
        this.listener1=listener;
    }
}
