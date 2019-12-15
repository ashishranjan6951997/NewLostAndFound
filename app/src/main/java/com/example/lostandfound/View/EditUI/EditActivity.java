package com.example.lostandfound.View.EditUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Controller.DatabaseController.SaveDataController;
import com.example.lostandfound.R;

import static com.example.lostandfound.NameClass.EDIT;

public class EditActivity extends AppCompatActivity {


    boolean saved;
    SaveDataController controller;
    EditText userNameEdit;
    EditText bioNameEdit;
    EditText emailEdit;
    EditText phoneNoEdit;
    ImageView imageView;
    int imageViewRequestCode;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);




        controller = new SaveDataController(this);

        userNameEdit = findViewById(R.id.userName);
        bioNameEdit = findViewById(R.id.bio);
        emailEdit = findViewById(R.id.email);
        phoneNoEdit = findViewById(R.id.phone);
        imageView = findViewById(R.id.profile_imageView);

        imageViewRequestCode = 1;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent,imageViewRequestCode);
            }
        });

        saved = false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imageViewRequestCode)
        {
            if(resultCode == RESULT_OK)
            {
                uri = data.getData();
                Glide.with(this).load(uri).into(imageView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
            String name = userNameEdit.getText().toString();
            String bio = bioNameEdit.getText().toString();
            String email = emailEdit.getText().toString();
            String phone = phoneNoEdit.getText().toString();

            if(!name.matches("") || !bio.matches("") || !email.matches("") || !phone.matches(""))
            {
                saved = true;
            }

            Log.v("SAVED",saved+"");

            if(item.getItemId() == R.id.save) {
                if (!saved) {
                    item.getIcon().setAlpha(100);
                } else {
                    String data[] = {name, bio, email, phone};
                    item.getIcon().setAlpha(255);

                    controller.saveData(data, uri, EDIT);
                }
            }

            return true;
        }
    }
