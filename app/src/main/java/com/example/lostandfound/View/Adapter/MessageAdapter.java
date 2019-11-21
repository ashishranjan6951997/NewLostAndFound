package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    Context context;
    List objects;

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
        String name = (String) objects.get(position);
        holder.nameText.setText(name);
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
        }
    }
}
