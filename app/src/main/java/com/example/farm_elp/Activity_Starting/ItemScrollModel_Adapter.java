package com.example.farm_elp.Activity_Starting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farm_elp.R;
import com.example.farm_elp.model.ItemScrollModel;

import java.util.List;

public class ItemScrollModel_Adapter extends RecyclerView.Adapter<ItemScrollModel_Adapter.ItemViewHolder> {

    private List<ItemScrollModel> itemScrollModelList;


    public ItemScrollModel_Adapter(List<ItemScrollModel> itemScrollModelList, Context context) {
        this.itemScrollModelList = itemScrollModelList;
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemscroll,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemScrollModel itemScrollModel=itemScrollModelList.get(position);
        Glide.with(context).load(itemScrollModel.getImage()).into(holder.scrollImage);
        
    }

    @Override
    public int getItemCount() {
        return itemScrollModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView scrollImage;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            scrollImage=itemView.findViewById(R.id.scrollImage);
        }
    }
}
