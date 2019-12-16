package com.example.lostandfound.View.SecondUi;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.ProfileController.ProfileController;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Adapter.ProfileAdapter;
import com.example.lostandfound.View.EditUI.EditActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.USERS;

public class ProfileFragment extends FragmentInterface {
    View rootView;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ImageView profileImageView;
    TextView numberOfPosts;
    RecyclerView recyclerView;
    LinearLayout layout;
    Button editBtn;
    TextView nameText;
    ProfileController controller;
    ProfileAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        initView();
        return rootView;
    }

    private void initView()
    {
        numberOfPosts = rootView.findViewById(R.id.number_of_posts);
        linearLayout = rootView.findViewById(R.id.linear_layout_image_and_number_of_post);
        profileImageView = rootView.findViewById(R.id.profile_imageView);
        recyclerView = rootView.findViewById(R.id.recycler);
        layout = rootView.findViewById(R.id.substitute_linear_profile);
        editBtn = rootView.findViewById(R.id.edit_btn);

        nameText = rootView.findViewById(R.id.userName);

        controller = new ProfileController(this);
        final String[] name = new String[1];
        final String[] uri = new String[1];


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                getActivity().startActivity(intent);
            }
        });


        String user = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference nameReference = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(user)
                .child(DETAILS)
                .child(EDIT);

        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("SNAPSHOT", dataSnapshot.toString());
                name[0] = dataSnapshot.child(NAME).getValue().toString();
                uri[0] = dataSnapshot.child(IMAGE_URI).getValue().toString();
                Log.e("SNAPSHOT LATER", name[0]);
                getActivity().setTitle(name[0]);

                nameText.setText(name[0]);
                Glide.with(getActivity()).load(Uri.parse(uri[0])).into(profileImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        controller.showData();
    }

    public void setRecyclerView(List list)
    {
        if (list.size() == 0) {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new ProfileAdapter(getContext(),list);
            adapter.notifyDataSetChanged();
            final RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }

    }
}