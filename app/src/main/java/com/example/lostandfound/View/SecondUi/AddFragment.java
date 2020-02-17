package com.example.lostandfound.View.SecondUi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.TimePicker;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.DatabaseController.SaveDataController;
import com.example.lostandfound.MapActivity.MapActivity;
import com.example.lostandfound.Model.DatabaseModel.RealtimeDatabaseDemoModel;
import com.example.lostandfound.Observer;
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
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.descriptionForStoringDatabase;
import static com.example.lostandfound.NameClass.locationForStoringDatabase;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.photoUriForStoringDatabase;

public class AddFragment extends FragmentInterface implements Observer
{
    Button timePicker;
    View rootView;
    TextView dateTimeDisplay;
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
    String format;
    Calendar mcurrent;
    int hour, min;
    RealtimeDatabaseDemoModel demo;

    static int LOCATION_REQUEST = 1;
    static int PHOTO_REQUEST = 2;
    int day, year, month;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_fragment, container, false);
        init();
        return rootView;
    }

    private void init() {
        final String[] spinnerText = new String[1];
        descriptionOfItem = rootView.findViewById(R.id.postWrite);
        imageViewOfItem = rootView.findViewById(R.id.pickedImage);
        imageView = rootView.findViewById(R.id.profile_imageView);
        nameText = rootView.findViewById(R.id.user_name);
        spinner = rootView.findViewById(R.id.spinnerItems);
        slidingUpPanelLayout = rootView.findViewById(R.id.sliding_layout);
        locationBtn = rootView.findViewById(R.id.locationButton);
        photoBtn = rootView.findViewById(R.id.photoButton);
        postBtn = rootView.findViewById(R.id.postButton);
        dateClickedButton = rootView.findViewById(R.id.date_pick);
        dateTimeDisplay = rootView.findViewById(R.id.date_time_display);
        controller = new SaveDataController(getActivity());
        locationDisplay = rootView.findViewById(R.id.location_display);
        //locationBtn = rootView.findViewById(R.id.);
        timePicker = rootView.findViewById(R.id.time_picker);
        mcurrent = Calendar.getInstance();
        hour = mcurrent.get(Calendar.HOUR_OF_DAY);
        min = mcurrent.get(Calendar.MINUTE);
        currentformatTime();
        currentFormatDate();
        dateTimeDisplay.setText(day + "-" + month + "-" + year + ", " + hour + ":" + min + " " + format);

        demo = new RealtimeDatabaseDemoModel();
        demo.register(this);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final String[] userName = new String[1];
        final String[] userPhoto = new String[1];

        databaseUser = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS)
                .child(EDIT);

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName[0] = (String) dataSnapshot.child(NAME).getValue();
                userPhoto[0] = (String) dataSnapshot.child(IMAGE_URI).getValue();

                nameText.setText(userName[0]);
                if (userPhoto[0] != null)
                    Glide.with(getActivity()).load(Uri.parse(userPhoto[0])).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                spinnerText[0] = (String) parent.getItemAtPosition(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
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
                String array[] = {
                        desc,
                        location,
                        String.valueOf(choosenLatitude),
                        String.valueOf(choosenLongitude),
                        String.valueOf(day),
                        String.valueOf(month),
                        String.valueOf(year),
                        String.valueOf(hour),
                        String.valueOf(min),
                        String.valueOf(spinnerText[0]),
                        format
                };

                if (!desc.equals("") && array[1]!="kiit") {
                    controller.saveData(array, uri, POST);
                    demo.notifyObserver();
                } else if(!desc.equals("")) {
                    Toast.makeText(getActivity(), "Please Write the description", Toast.LENGTH_LONG).show();
                }
                else if(array[1]!="kiit")
                {
                    Toast.makeText(getActivity(), "Please add some Location", Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(getActivity(), "Please add some details", Toast.LENGTH_LONG).show();


            }
        });
        dateClickedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFunction();
                dateTimeDisplay.setText(day + "-" + month + "-" + year + ", " + modifiedFormat(hour) + " : " + modifiedFormat(min) + " " + format);
            }
        });
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeFunction();
                dateTimeDisplay.setText(day + "-" + month + "-" + year + ", " + modifiedFormat(hour) + " : " + modifiedFormat(min) + " " + format);
            }
        });


        getActivity().setTitle("Post");

    }

    private String modifiedFormat(int min)
    {
        if(min<10)
        {
            return "0"+min;
        }
        else
            return String.valueOf(min);
    }


    private void currentformatTime() {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
    }

    private void currentFormatDate() {
        day = mcurrent.get(Calendar.DAY_OF_MONTH);
        month = mcurrent.get(Calendar.MONTH);
        month = month + 1;
        year = mcurrent.get(Calendar.YEAR);
    }

    private void timeFunction() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.MyDatePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                min = i1;
                currentformatTime();
            }
        }, hour, min, true);
        timePickerDialog.show();
        timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

    }

    private void dateFunction() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1 + 1;
                day = i2;
            }
        }, year, month, day);
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.v("RESULT CODE", String.valueOf(resultCode));
        Log.v("REQUEST CODE", String.valueOf(requestCode));
        Log.v("RESULT OK CODE", String.valueOf(RESULT_OK));

        if (requestCode == LOCATION_REQUEST) {
            if (data != null) {
                choosenLongitude = data.getDoubleExtra("choosenLongitude", 0);
                choosenLatitude = data.getDoubleExtra("choosenLatitude", 0);
                locationDisplay.setText(data.getStringExtra("StringText"));
            }
        }

        if (requestCode == PHOTO_REQUEST) {
            if (data != null) {
                uri = data.getData();
                Glide.with(this).load(uri).into(imageViewOfItem);
                descriptionOfItem.setHint("  Say Something about Photo");
            }
        }
    }

    @Override
    public void updateToast() {
        Toast.makeText(getActivity(), "Status Upload Completed", Toast.LENGTH_LONG).show();
        postBtn.setEnabled(false);
    }
}