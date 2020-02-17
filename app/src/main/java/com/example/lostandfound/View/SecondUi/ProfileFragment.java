package com.example.lostandfound.View.SecondUi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

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
import java.util.Map;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.Found;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.Lost;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.USERS;

public class ProfileFragment extends FragmentInterface
{
    View rootView;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ImageView profileImageView;
    RecyclerView recyclerView;
    LinearLayout layout;
    Button editBtn;
    TextView nameText;
    ProfileController controller;
    ProfileAdapter adapter;
    TextView totalPostText,lostPostText,foundPostText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        linearLayout = rootView.findViewById(R.id.linear_layout_image_and_number_of_post);
        profileImageView = rootView.findViewById(R.id.profile_imageView);
        recyclerView = rootView.findViewById(R.id.recycler);
        layout = rootView.findViewById(R.id.substitute_linear_profile);
        editBtn = rootView.findViewById(R.id.edit_btn);

        totalPostText = rootView.findViewById(R.id.number_of_posts);
        lostPostText = rootView.findViewById(R.id.number_of_lost_posts);
        foundPostText = rootView.findViewById(R.id.number_of_found_posts);

        nameText = rootView.findViewById(R.id.userName);

        controller = new ProfileController(this);
        final String[] name = new String[1];
        final String[] uri = new String[1];


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isConnectedToInternet()) {
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    getActivity().startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }
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

                if (dataSnapshot.child(NAME).getValue() != null) {
                    name[0] = dataSnapshot.child(NAME).getValue().toString();
                }

                if(dataSnapshot.child(IMAGE_URI).getValue() !=null) {
                    uri[0] = dataSnapshot.child(IMAGE_URI).getValue().toString();
                }
                getActivity().setTitle(name[0]);
                if (name[0] != null)
                    nameText.setText(name[0]);
                if (uri[0] != null)
                    Glide.with(getActivity()).load(Uri.parse(uri[0])).into(profileImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.e("DATA_START","start");
        controller.showData();
    }

    public void setRecyclerView(List list, Map map)
    {
        if (list.size() == 0) {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {

            String lost = map.get(Lost).toString();
            String found = map.get(Found).toString();
            int lostPost = Integer.parseInt(lost);
            int foundPost = Integer.parseInt(found);
            int total = lostPost + foundPost;

            totalPostText.setText(String.valueOf(total));
            foundPostText.setText(String.valueOf(foundPost));
            lostPostText.setText(String.valueOf(lostPost));

            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new ProfileAdapter(getContext(),list);
            adapter.notifyDataSetChanged();
            final RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}