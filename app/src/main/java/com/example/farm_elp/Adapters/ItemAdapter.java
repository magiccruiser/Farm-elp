package com.example.farm_elp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.R;
import com.example.farm_elp.model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;

public class ItemAdapter extends FirebaseRecyclerAdapter<Item, ItemAdapter.ItemHolder>{
    private OnItemClickListener listener;


    public ItemAdapter(FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getItemName());
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        Glide.with(holder.itemImage.getContext()).load(model.getItemUrl()).centerCrop().into(holder.itemImage);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView quantity;
        ImageView itemImage;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.quantity);
            itemImage = itemView.findViewById(R.id.itemImage);

            /////////////////on item click/////////////////
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DataSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
