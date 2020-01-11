package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Model.ChatPOJO.Chat;
import com.example.lostandfound.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{
    Context context;
    List objects;

    public ChatAdapter(@NonNull Context context, @NonNull List objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_chat, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat c = (Chat) objects.get(position);
        boolean isPhoto = c.getPhoto();

        if (isPhoto) {
            //holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            Glide.with(context).load(Uri.parse(c.getmMessage())).into(holder.imageView);
        } else {
            //holder.mMessage2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            if (c.getmUser()) {
                //holder.imageView.setForegroundGravity(Gravity.END);
                holder.mMessage2.setText(c.getmMessage());
                holder.mContainer2.setGravity(Gravity.END);
                holder.mMessage2.setTextColor(Color.parseColor("#000000"));
                //holder.mContainer2.setLayoutParams(ViewGroup.LayoutParams.GRAVITY);
                //holder.mMessage2.setBackground(Color.parseColor());
                //holder.mContainer2.setBackgroundColor(Color.parseColor("#cdcdcd"));
                holder.mMessage2.setBackgroundResource(R.drawable.item_right_chat);
                holder.timeText.setBackgroundResource(R.drawable.item_right_chat);
                //holder.mContainer2
            } else {
                holder.mMessage2.setText(c.getmMessage());
                holder.mContainer2.setGravity(Gravity.START);
                //holder.mMessage2.setGravity(Gravity.END);
                //holder.mContainer2.setBackgroundColor(Color.parseColor("#43A047"));
                holder.mMessage2.setBackgroundResource(R.drawable.item_left_chat);
                holder.mMessage2.setTextColor(Color.parseColor("#ffffff"));
                holder.timeText.setBackgroundResource(R.drawable.item_left_chat);
                // holder.mMessage.setTextColor(Color.parseColor("#ffffff"));
                // holder.mMessage.setBackgroundColor(Color.parseColor("#43A047"));

                //holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
            }
        }

        if (c.getHour() != "" || c.getMinute() != "") {
            holder.timeText.setText(unitFormat(c.getHour()) + ":" + unitFormat(c.getMinute()));
        }
    }

    public String unitFormat(String str) {

        if (Integer.parseInt(str) < 10 && str!="") {
            String strModified = "0" + str;
            return strModified;
        }

        return str;
    }

    public String monthModified(String str) {
        String strMonth = "";
        switch (str) {
            case "1":
                strMonth = "Jan";
                break;
            case "2":
                strMonth = "Feb";
                break;
            case "3":
                strMonth = "Mar";
                break;
            case "4":
                strMonth = "Apr";
                break;
            case "5":
                strMonth = "May";
                break;
            case "6":
                strMonth = "June";
                break;
            case "7":
                strMonth = "July";
                break;
            case "8":
                strMonth = "Aug";
                break;
            case "9":
                strMonth = "Sep";
                break;
            case "10":
                strMonth = "Oct";
                break;
            case "11":
                strMonth = "Nov";
                break;
            case "12":
                strMonth = "Dec";
                break;
        }
        return strMonth;
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView mMessage2;
        public LinearLayout mContainer2;
        public ImageView imageView;
        public TextView timeText;


        public ViewHolder(View view) {
            super(view);

            mMessage2 = view.findViewById(R.id.txt_msg2);
            mContainer2 = view.findViewById(R.id.container2);
            imageView = view.findViewById(R.id.txt_image);
            timeText = view.findViewById(R.id.time);
        }
    }
}