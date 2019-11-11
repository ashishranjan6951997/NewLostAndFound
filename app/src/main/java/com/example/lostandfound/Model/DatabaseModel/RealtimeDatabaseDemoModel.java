package com.example.lostandfound.Model.DatabaseModel;

import android.content.Intent;
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

import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.profileImageUri;
import static com.example.lostandfound.NameClass.radioButtonText;

public class RealtimeDatabaseDemoModel {
    DatabaseReference reference;
    Map map;
    List<Observer> observers;

    public RealtimeDatabaseDemoModel(Map map) {
        reference = FirebaseDatabase.getInstance().getReference(USERS);
        this.map = map;
    }

    public RealtimeDatabaseDemoModel()
    {
        observers = new ArrayList<>();
    }

    public void saveData(Map dataMap)
    {
        Map map = new HashMap();
        String uId = FirebaseAuth.getInstance().getUid();
        String radioText = (String) dataMap.get(radioButtonText);
        reference = reference.child(radioText);
        String name = (String) dataMap.get(nameForStoringDatabase);
        map.put(NAME,name);
        String imageUri = (String) dataMap.get(NameClass.profileImageUri);
        map.put(IMAGE_URI,imageUri);
        reference.child(uId).setValue(map);
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