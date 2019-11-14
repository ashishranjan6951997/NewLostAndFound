package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.util.Log;
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

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    Context context;
    List objects;

    public MatchesAdapter(@NonNull Context context, @NonNull List objects)
    {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_match_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, int position)
    {

        Card card = (Card) objects.get(position);
        holder.nameText.setText(card.getId());
        holder.descText.setText(card.getDesc());
        holder.timeText.setText("Date :" + card.getDate() + "  " + "Time :" + card.getTime());
        Log.v("CARD Details", card.getId() + "  " + card.getDesc());

        switch (card.getProfileImageUrl()) {
            case "default":
                Glide.with(context).load(R.mipmap.ic_launcher).into(holder.imageView);
                break;
            default:
                Glide.with(context).load(card.getProfileImageUrl()).into(holder.imageView);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameText, descText, timeText;

        public ViewHolder(View view)
        {
            super(view);

            imageView = view.findViewById(R.id.image);
            nameText = view.findViewById(R.id.name);
            descText = view.findViewById(R.id.desc);
            timeText = view.findViewById(R.id.time);
        }

    }
}