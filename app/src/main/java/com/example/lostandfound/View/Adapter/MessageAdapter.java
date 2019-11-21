package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Chat.ChatActivity;

import java.util.List;

import static com.example.lostandfound.NameClass.CHAT_ID;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    Context context;
    List objects;
    String id;

    public MessageAdapter(@NonNull Context context, @NonNull List objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_card_layout, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position)
    {
        Card card = (Card)objects.get(position);
        holder.nameText.setText(card.getDesc());
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
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nameText = itemView.findViewById(R.id.textViewName);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {                Intent intent = new Intent(context,ChatActivity.class);
                    Bundle b = new Bundle();
                    b.putString(CHAT_ID,id);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
        }
    }
}