package com.example.lostandfound.View.SecondUi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.DatabaseController.SaveDataController;
import com.example.lostandfound.MapActivity.MapActivity;
import com.example.lostandfound.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.descriptionForStoringDatabase;
import static com.example.lostandfound.NameClass.locationForStoringDatabase;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.photoUriForStoringDatabase;

class AddFragment extends FragmentInterface {
    View rootView;
    TextView dateDisplay;
    TextView locationDisplay;
    CircleImageView imageView;
    TextView nameText;
    Spinner spinner;
    EditText descriptionOfItem;
    ImageView imageViewOfItem;
    SlidingUpPanelLayout slidingUpPanelLayout;
    DatabaseReference databaseUser;
    Button locationBtn;
    Button photoBtn;
    Button dateClickedButton;
    double choosenLongitude;
    double choosenLatitude;
    Uri uri;
    Button postBtn;
    SaveDataController controller;


    static int LOCATION_REQUEST = 1;
    static int PHOTO_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_fragment, container, false);
        init();
        return rootView;
    }

    private void init() {
        descriptionOfItem = rootView.findViewById(R.id.postWrite);
        imageViewOfItem = rootView.findViewById(R.id.pickedImage);
        imageView = rootView.findViewById(R.id.profile_imageView);
        nameText = rootView.findViewById(R.id.user_name);
        spinner = rootView.findViewById(R.id.spinnerItems);
        slidingUpPanelLayout = rootView.findViewById(R.id.sliding_layout);
        locationBtn = rootView.findViewById(R.id.locationButton);
        photoBtn = rootView.findViewById(R.id.photoButton);
        postBtn = rootView.findViewById(R.id.postButton);
        dateClickedButton=rootView.findViewById(R.id.date_pick);
        dateDisplay=rootView.findViewById(R.id.date_display);
        controller = new SaveDataController(getActivity());
        locationDisplay=rootView.findViewById(R.id.location_display);
        //locationBtn = rootView.findViewById(R.id.);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final String[] userName = new String[1];

        databaseUser = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS)
                .child(EDIT)
                .child(nameForStoringDatabase);

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName[0] = (String) dataSnapshot.getValue();
                nameText.setText(userName[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivityForResult(intent, LOCATION_REQUEST);
            }
        });

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, PHOTO_REQUEST);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = descriptionOfItem.getText().toString();
                String location = "kiit";
                String array[] = {desc,location, String.valueOf(choosenLatitude), String.valueOf(choosenLongitude)};

                controller.saveData(array,uri,POST);
            }
        });
        dateClickedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFunction();
            }
        });

    }

    private void dateFunction() {
        Calendar mcurrentDate=Calendar.getInstance();
        int day,year,month;
        day=mcurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mcurrentDate.get(Calendar.MONTH);
        year=mcurrentDate.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                dateDisplay.setText(i2+"-"+i1+"-"+i);

            }
        },year,month,day);
        datePickerDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.v("RESULT CODE", String.valueOf(resultCode));
        Log.v("REQUEST CODE", String.valueOf(requestCode));
        Log.v("RESULT OK CODE", String.valueOf(RESULT_OK));

        if (requestCode == LOCATION_REQUEST) {
            choosenLongitude = data.getDoubleExtra("choosenLongitude", 0);
            choosenLatitude = data.getDoubleExtra("choosenLatitude", 0);
            locationDisplay.setText(data.getStringExtra("StringText"));
        }

        if (requestCode == PHOTO_REQUEST) {
            uri = data.getData();
            Glide.with(this).load(uri).into(imageViewOfItem);
            descriptionOfItem.setHint("Say Something about Photo");
        }
    }
}