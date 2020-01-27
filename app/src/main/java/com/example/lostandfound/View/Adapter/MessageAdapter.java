package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.lostandfound.OnItemClickInterface;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Chat.ChatActivity;

import java.util.List;

import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.CHAT_TIME;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    Context context;
    List objects;
    String id;
    OnItemClickInterface onItemClickInterface;

    public MessageAdapter(@NonNull Context context, @NonNull List objects,OnItemClickInterface onItemClickInterface)
    {
        this.context = context;
        this.objects = objects;
        this.onItemClickInterface = onItemClickInterface;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_card_layout, parent, false);
        return new MessageAdapter.ViewHolder(view,onItemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position)
    {

        Card card = (Card)objects.get(position);
        Log.e("PHOTO",card.getProfileImageUrl()+"");
        if(card.getName()!=null) {
            holder.nameText.setText(card.getName());
        }
        if(card.getProfileImageUrl()!=null) {
            Glide.with(context).load(Uri.parse(card.getProfileImageUrl())).into(holder.imageView);
        }
        else{
            holder.imageView.setImageResource(R.drawable.account_photo);
        }
        id = card.getId();
    }

    @Override
    public int getItemCount()
    {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameText;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView, final OnItemClickInterface onItemClickInterface)
        {
            super(itemView);
            nameText = itemView.findViewById(R.id.textViewName);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onItemClickInterface.onItemClick(getAdapterPosition(),objects);
                }
            });
        }
    }

    public void filterList(List<Card> list)
    {
        this.objects = list;
        notifyDataSetChanged();
    }
}