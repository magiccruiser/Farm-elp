package com.example.farm_elp.Adapters;

import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm_elp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;

public class UserData_Adapter extends FirestoreRecyclerAdapter<UserData, UserData_Adapter.UserDataHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserData_Adapter(@NonNull FirestoreRecyclerOptions<UserData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserDataHolder holder, int position, @NonNull UserData model) {
    }

    @NonNull
    @Override
    public UserDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_headerlayout,parent,false);
        return new UserData_Adapter.UserDataHolder(v);
    }

    public class UserDataHolder extends RecyclerView.ViewHolder {

        TextInputLayout userName, phoneNo;
        public UserDataHolder(@NonNull View dataview){
            super(dataview);
            userName = dataview.findViewById(R.id.name_onTop);
        }
    }
}
