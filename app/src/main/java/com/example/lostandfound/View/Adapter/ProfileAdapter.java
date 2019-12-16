package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    Context context;
    List list;

    public ProfileAdapter(@NonNull Context context, @NonNull List objects) {
        this.context = context;
        this.list = objects;
    }


    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_profile_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        final Card card = (Card) list.get(position);
        holder.nameText.setText(card.getId());
        holder.descText.setText(card.getDesc());
        holder.descText.setTextColor(Color.parseColor("#000000"));

        if (card.getProfileImageUrl() != null) {
            Glide.with(context).load(card.getProfileImageUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameText, descText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name);
            descText = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
