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
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.LatitudeStorageInDatabase;

import static com.example.lostandfound.NameClass.LongitudeStorageInDatabase;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.bioForStoringDatabase;
import static com.example.lostandfound.NameClass.descriptionForStoringDatabase;
import static com.example.lostandfound.NameClass.emailForStroringDatabase;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.phoneForStoringDatabase;
import static com.example.lostandfound.NameClass.photoUriForStoringDatabase;
import static com.example.lostandfound.NameClass.profileImageUri;
import static com.example.lostandfound.NameClass.radioButtonText;

public class RealtimeDatabaseDemoModel {
    DatabaseReference reference;
    List<Observer> observers;

    public RealtimeDatabaseDemoModel() {
        reference = FirebaseDatabase.getInstance().getReference(USERS);
        observers = new ArrayList<>();
    }


    public void saveData(Map dataMap, String text) {
        Log.e("REFERENCE", String.valueOf(reference));
        Map map = new HashMap();
        String uId = FirebaseAuth.getInstance().getUid();
        String radioText = (String) dataMap.get(radioButtonText);
        //reference = reference.child(radioText);

        String key = FirebaseDatabase.getInstance().getReference()
                .child(USERS)
                .child(uId)
                .child(DETAILS)
                .child(POST)
                .push()
                .getKey();

        if (text.equals(POST)) {
            if (dataMap.get(descriptionForStoringDatabase) != null) {
                String desc = (String) dataMap.get(descriptionForStoringDatabase);
                map.put(descriptionForStoringDatabase, desc);
            }
            if (dataMap.get(LatitudeStorageInDatabase) != null) {
                String lat = (String) dataMap.get(LatitudeStorageInDatabase);
                map.put(LatitudeStorageInDatabase, lat);
            }
            if (dataMap.get(LongitudeStorageInDatabase) != null) {
                String lang = (String) dataMap.get(LongitudeStorageInDatabase);
                map.put(LongitudeStorageInDatabase, lang);
            }
            if (dataMap.get(photoUriForStoringDatabase) != null) {
                String postUri = (String) dataMap.get(photoUriForStoringDatabase);
                map.put(photoUriForStoringDatabase, postUri);
            }

            reference.child(uId).child(DETAILS).child(text).child(key).setValue(map);
        }


        if (text.equals(EDIT)) {
            if (dataMap.get(nameForStoringDatabase) != null) {
                String name = (String) dataMap.get(nameForStoringDatabase);
                map.put(NAME, name);
            }

            if (dataMap.get(bioForStoringDatabase) != null) {
                String bio = (String) dataMap.get(bioForStoringDatabase);
                map.put(bioForStoringDatabase, bio);
            }
            if (dataMap.get(emailForStroringDatabase) != null) {
                String email = (String) dataMap.get(emailForStroringDatabase);
                map.put(emailForStroringDatabase, email);
            }
            if (dataMap.get(phoneForStoringDatabase) != null) {
                String phone = (String) dataMap.get(phoneForStoringDatabase);
                map.put(phoneForStoringDatabase, phone);
            }

            if (dataMap.get(profileImageUri) != null) {
                String imageUri = (String) dataMap.get(NameClass.profileImageUri);
                map.put(IMAGE_URI, imageUri);
            }

            reference.child(uId).child(DETAILS).child(text).setValue(map);

        }

        int k = 1;
    }

    // Code for subscriber publisher pattern

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.updateToast();
        }
    }
}