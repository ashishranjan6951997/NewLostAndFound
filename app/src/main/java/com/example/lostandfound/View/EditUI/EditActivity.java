package com.example.lostandfound.View.EditUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.DatabaseController.SaveDataController;
import com.example.lostandfound.Model.DatabaseModel.RealtimeDatabaseDemoModel;
import com.example.lostandfound.Observer;
import com.example.lostandfound.R;
import com.example.lostandfound.View.SecondUi.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.ProfileFragmentTAG;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.bioForStoringDatabase;
import static com.example.lostandfound.NameClass.emailForStroringDatabase;
import static com.example.lostandfound.NameClass.phoneForStoringDatabase;

public class EditActivity extends AppCompatActivity implements Observer
{
    SaveDataController controller;
    EditText userNameEdit;
    EditText bioNameEdit;
    EditText emailEdit;
    EditText phoneNoEdit;
    ImageView imageView;
    int imageViewRequestCode;
    Uri uri;
    Button saveButton;
    RealtimeDatabaseDemoModel demo;
    DatabaseReference reference;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        controller = new SaveDataController(this);

        demo = new RealtimeDatabaseDemoModel();
        demo.register(this);

        userNameEdit = findViewById(R.id.userName);
        bioNameEdit = findViewById(R.id.bio);
        emailEdit = findViewById(R.id.email);
        phoneNoEdit = findViewById(R.id.phone);
        imageView = findViewById(R.id.profile_imageView);
        saveButton = findViewById(R.id.saveBtn);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        imageViewRequestCode = 1;

        reference = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS)
                .child(EDIT);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String profilePhotoUri = (String) dataSnapshot.child(IMAGE_URI).getValue();
                    String userName = (String) dataSnapshot.child(NAME).getValue();
                    String bio = (String) dataSnapshot.child(bioForStoringDatabase).getValue();
                    String email = (String) dataSnapshot.child(emailForStroringDatabase).getValue();
                    String phone = (String) dataSnapshot.child(phoneForStoringDatabase).getValue();

                    if(profilePhotoUri != null) {
                        Glide.with(EditActivity.this).load(Uri.parse(profilePhotoUri)).into(imageView);
                    }

                    userNameEdit.setText(userName);
                    bioNameEdit.setText(bio);
                    emailEdit.setText(email);
                    phoneNoEdit.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, imageViewRequestCode);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userNameEdit.getText().toString();
                String bio = bioNameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String phone = phoneNoEdit.getText().toString();
                String data[] = {name, bio, email, phone};

                controller.saveData(data, uri, EDIT);
                demo.notifyObserver();
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imageViewRequestCode) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                Glide.with(this).load(uri).into(imageView);
            }
        }
    }

    @Override
    public void updateToast() {
        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_LONG).show();
    }
}