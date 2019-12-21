package com.example.lostandfound.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.USERS;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    Context context;
    List list;
    String userId;
    DatabaseReference referenceProfile;

    public ProfileAdapter(@NonNull Context context, @NonNull List objects) {
        this.context = context;
        this.list = objects;
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        referenceProfile = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS);


    }


    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.items_profile_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileAdapter.ViewHolder holder, int position) {
        final Card card = (Card) list.get(position);
        holder.nameText.setText(card.getName());
        holder.descText.setText(card.getDesc());
        holder.descText.setTextColor(Color.parseColor("#000000"));


        referenceProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if(dataSnapshot.child(EDIT).exists() || dataSnapshot.child(EDIT).child(IMAGE_URI).exists()) {
                        String uri = dataSnapshot.child(EDIT).child(IMAGE_URI).getValue().toString();
                        Glide.with(context).load(Uri.parse(uri)).into(holder.profilePhoto);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (card.getProfileImageUrl() != null) {
            Glide.with(context).load(card.getProfileImageUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, profilePhoto;
        TextView nameText, descText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name);
            descText = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.image);
            profilePhoto = itemView.findViewById(R.id.card_layout_home_fragment_imageView);
        }
    }
}
