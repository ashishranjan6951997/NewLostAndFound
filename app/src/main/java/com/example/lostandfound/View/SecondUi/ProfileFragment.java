package com.example.lostandfound.View.SecondUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.lostandfound.R;

public class ProfileFragment extends FragmentInterface {
    View rootView;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ImageView profileImageView;
    TextView numberOfPosts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.profile_fragment,container,false);
        initView();
        return rootView;
    }

    private void initView() {
        numberOfPosts=rootView.findViewById(R.id.number_of_posts);
        linearLayout=rootView.findViewById(R.id.linear_layout_image_and_number_of_post);
        profileImageView=rootView.findViewById(R.id.profile_imageView);
        getActivity().setTitle("Ashish Ranjan");
    }


}
