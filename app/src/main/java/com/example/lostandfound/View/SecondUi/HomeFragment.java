package com.example.lostandfound.View.SecondUi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.DatabaseController.SaveDataController;
import com.example.lostandfound.MapActivity.MapActivity;
import com.example.lostandfound.Model.DatabaseModel.RealtimeDatabaseDemoModel;
import com.example.lostandfound.Observer;
import com.example.lostandfound.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.lostandfound.NameClass.MESSAGE;
import static com.example.lostandfound.NameClass.TITLE;

public class HomeFragment extends Fragment implements Observer {
    View rootView;
    RadioButton radioButton;
    Button saveButton;
    RadioGroup radioGroup;
    EditText nameText;
    String radioText, name;
    ArrayList list;
    SaveDataController controller;
    RealtimeDatabaseDemoModel demo;
    ImageView imageView;
    Uri uri;
    Map map;
    Button locationButton;
    double choosenLongitude;
    double choosenLatitude;
    Bundle bundle;
    int imagwViewRequestCode=1;
    int locationRequestCode=2;
    Double lat, lng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        demo = new RealtimeDatabaseDemoModel();
        demo.register(this);
        init();
        bundle = this.getArguments();
        return rootView;
    }


    public void init() {
        map = new HashMap();
        controller = new SaveDataController(this);
        locationButton = rootView.findViewById(R.id.location_button);
        saveButton = rootView.findViewById(R.id.fab);
        saveButton.setEnabled(false);
        radioGroup = rootView.findViewById(R.id.radio_id);
        imageView = rootView.findViewById(R.id.image_view);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                saveButton.setEnabled(true);
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MapActivity.class);
                startActivityForResult(intent,locationRequestCode);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = rootView.findViewById(id);
                radioText = radioButton.getText().toString();

                name ="Ashish";
                final String[] inputArray = {name, radioText,Double.toString(choosenLatitude),Double.toString(choosenLongitude)};

                new AlertDialog.Builder(getActivity())
                        .setTitle(TITLE)
                        .setMessage(MESSAGE)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                controller.setMap(map);
                                controller.saveData(inputArray, uri);
                                demo.notifyObserver();
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .show();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                getActivity().startActivityForResult(intent, imagwViewRequestCode);
            }
        });
    }

    @Override
    public void updateToast() {
         Toast.makeText(getActivity(), "Data Saved Successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == imagwViewRequestCode && resultCode == RESULT_OK)
        {
            uri = data.getData();
            Glide.with(this).load(uri).into(imageView);
        }
        if(requestCode == locationRequestCode && resultCode==RESULT_OK)
        {
             choosenLongitude  = data.getDoubleExtra("choosenLongitude", 0);
             choosenLatitude=data.getDoubleExtra("choosenLatitude", 0);
            Toast.makeText(getActivity(),choosenLatitude+"ok"+ choosenLongitude,Toast.LENGTH_LONG).show();
        }
    }

    public void photoUploadError() {
        Toast.makeText(getActivity(), "Photo Upload Error", Toast.LENGTH_LONG).show();
        //Glide.with(getActivity()).load(imageUri).into(imageView);
    }

    public void setLatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}