package com.example.lostandfound.Model.DatabaseModel;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.NameClass;
import com.example.lostandfound.Observer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.LatitudeStorageInDatabase;

import static com.example.lostandfound.NameClass.LongitudeStorageInDatabase;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.bioForStoringDatabase;
import static com.example.lostandfound.NameClass.emailForStroringDatabase;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.phoneForStoringDatabase;
import static com.example.lostandfound.NameClass.profileImageUri;
import static com.example.lostandfound.NameClass.radioButtonText;

public class RealtimeDatabaseDemoModel {
    DatabaseReference reference;
    List<Observer> observers;

    public RealtimeDatabaseDemoModel()
    {
        reference = FirebaseDatabase.getInstance().getReference(USERS);
        observers = new ArrayList<>();
    }


    public void saveData(Map dataMap)
    {
        Log.e("REFERENCE",String.valueOf(reference));
        Map map = new HashMap();
        String uId = FirebaseAuth.getInstance().getUid();
        String radioText = (String) dataMap.get(radioButtonText);
        //reference = reference.child(radioText);

        if(dataMap.get(nameForStoringDatabase) != null)
        {
            String name = (String) dataMap.get(nameForStoringDatabase);
            map.put(NAME, name);
        }

        if(dataMap.get(profileImageUri) != null) {
            String imageUri = (String) dataMap.get(NameClass.profileImageUri);
            map.put(IMAGE_URI, imageUri);
        }

        if(dataMap.get(LatitudeStorageInDatabase) !=null) {
            String Latitute = (String) dataMap.get(LatitudeStorageInDatabase);
            map.put(LatitudeStorageInDatabase, Latitute);
        }

        if(dataMap.get(LongitudeStorageInDatabase) !=null) {
            String Longitude = (String) dataMap.get(LongitudeStorageInDatabase);
            map.put(LongitudeStorageInDatabase, Longitude);
        }

        if(dataMap.get(bioForStoringDatabase) !=null)
        {
            String bio = (String) dataMap.get(bioForStoringDatabase);
            map.put(bioForStoringDatabase,bio);
        }
        if(dataMap.get(emailForStroringDatabase) != null)
        {
            String email = (String) dataMap.get(emailForStroringDatabase);
            map.put(emailForStroringDatabase,email);
        }
        if(dataMap.get(phoneForStoringDatabase) !=null)
        {
            String phone = (String) dataMap.get(phoneForStoringDatabase);
            map.put(phoneForStoringDatabase,phone);
        }

        int k = 1;
        reference.child(uId).child(DETAILS).setValue(map);
    }

    // Code for subscriber publisher pattern

    public void register(Observer observer)
    {
        observers.add(observer);
    }

    public void notifyObserver()
    {
        for(Observer observer : observers)
        {
            observer.updateToast();
        }
    }
}