package com.sahil.tmailapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.PrefManager;

public class UserCredentialsAdapter extends RecyclerView.Adapter<UserCredentialsAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;
    private PrefManager prefManager;

    public UserCredentialsAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        prefManager = new PrefManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {

            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String token = cursor.getString(cursor.getColumnIndex("token"));

            holder.emailAdd.setText(email);

            if (prefManager.getString("EMAIL").equals(email)){
                holder.checkBox.setChecked(true);
            }else {
                holder.checkBox.setChecked(false);
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    prefManager.setString("EMAIL",email);
                    prefManager.setString("TOKEN",token);

                    if (prefManager.getString("EMAIL").equals(email)){
                        holder.checkBox.setChecked(true);
                    }else {
                        holder.checkBox.setChecked(false);
                    }
                    notifyDataSetChanged();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView emailAdd;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            emailAdd = itemView.findViewById(R.id.emailAdd);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}

