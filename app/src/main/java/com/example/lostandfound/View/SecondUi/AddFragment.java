package com.example.lostandfound.View.SecondUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.R;

import de.hdodenhof.circleimageview.CircleImageView;

class AddFragment extends FragmentInterface
{
    View rootView;
    CircleImageView imageView;
    TextView nameText;
    EditText postText;
    ImageView imgView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.add_fragment,container,false);
        init();
        return rootView;
    }

    private void init()
    {
        imageView = rootView.findViewById(R.id.profile_imageView);
        nameText = rootView.findViewById(R.id.user_name);
        postText = rootView.findViewById(R.id.postWrite);
        imgView = rootView.findViewById(R.id.postImage);
    }
}