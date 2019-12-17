package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Model.ChatPOJO.Chat;
import com.example.lostandfound.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{
    Context context;
    List objects;

    public ChatAdapter(@NonNull Context context, @NonNull List objects)
    {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_chat, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position)
    {
        Chat c = (Chat) objects.get(position);

        if(c.getmUser())
        {
            holder.mMessage2.setText(c.getmMessage());
            holder.mMessage2.setGravity(Gravity.END);
            holder.mContainer1.setVisibility(View.GONE);
//            holder.mMessage.setTextColor(Color.parseColor());
//            holder.mMessage2.setBackground(Color.parseColor());
//            holder.mMessage2.setBackgroundColor(ContextCompat.getColor(this, R.drawable.item_right_chat));
//            holder.mContainer2
        }
        else
        {
            holder.mMessage1.setText(c.getmMessage());
           holder.mMessage1.setGravity(Gravity.START);
           holder.mContainer2.setVisibility(View.GONE);
           // holder.mMessage.setTextColor(Color.parseColor("#ffffff"));
           // holder.mMessage.setBackgroundColor(Color.parseColor("#43A047"));

            //holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView mMessage1;
        public LinearLayout mContainer1;
        public TextView mMessage2;
        public LinearLayout mContainer2;


        public ViewHolder(View view) {
            super(view);
            mMessage1 = view.findViewById(R.id.txt_msg1);
            mContainer1 = view.findViewById(R.id.container1);
            mMessage2 = view.findViewById(R.id.txt_msg2);
            mContainer2 = view.findViewById(R.id.container2);
        }
    }
}