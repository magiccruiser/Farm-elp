package com.example.farm_elp.Adapters;

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

public class ShoppingItemAdapter extends FirebaseRecyclerAdapter<Item, ShoppingItemAdapter.ShoppingItemHolder> {
    private ShoppingItemAdapter.OnDetailClickListener listener;
    private ShoppingItemAdapter.OnContactClickListener listener1;


    public ShoppingItemAdapter(FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoppingItemAdapter.ShoppingItemHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getItemName());
        Glide.with(holder.itemImage.getContext()).load(model.getItemUrl()).centerCrop().into(holder.itemImage);
    }

    @NonNull
    @Override
    public ShoppingItemAdapter.ShoppingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item,parent,false);
        return new ShoppingItemAdapter.ShoppingItemHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    class ShoppingItemHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;
        LinearLayout contact;

        public ShoppingItemHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.shopping_itemName);
            itemImage = itemView.findViewById(R.id.shopping_itemImage);
            contact = itemView.findViewById(R.id.shopping_call);

            /////////////////on item click/////////////////
            itemName.setOnClickListener(new View.OnClickListener() {
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
    public void setOnDetailClickListener(ShoppingItemAdapter.OnDetailClickListener listener){
        this.listener=listener;
    }
    public void setOnContactClickListener(ShoppingItemAdapter.OnContactClickListener listener){
        this.listener1=listener;
    }
}