package com.example.lostandfound.View.SecondUi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.example.lostandfound.Model.DatabaseModel.RealtimeDatabaseDemoModel;
import com.example.lostandfound.Observer;
import com.example.lostandfound.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.profileImageUri;
import static com.example.lostandfound.NameClass.radioButtonText;

public class HomeFragment extends Fragment implements Observer
{

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        demo = new RealtimeDatabaseDemoModel();
        demo.register(this);
        init();
        return rootView;
    }


    public void init() {
        map = new HashMap();
        controller = new SaveDataController(this);

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

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = rootView.findViewById(id);
                radioText = radioButton.getText().toString();
                nameText = rootView.findViewById(R.id.name_text);
                name = nameText.getText().toString();
                String[] inputArray = {name,radioText};

                controller.setMap(map);
                controller.saveData(inputArray,uri);
                demo.notifyObserver();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                getActivity().startActivityForResult(intent, 1);
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
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            uri = data.getData();
            Glide.with(this).load(uri).into(imageView);
        }
    }

    public void photoUploadError()
    {
        Toast.makeText(getActivity(), "Photo Upload Error", Toast.LENGTH_LONG).show();
        //Glide.with(getActivity()).load(imageUri).into(imageView);
    }
}