package com.example.lostandfound.Model.ProfileDatabaseModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.USERS;

public class ProfileDatabaseModel
{

    List list;

    public ProfileDatabaseModel()
    {
        list = new ArrayList();
    }

    public void setArrayList()
    {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final DatabaseReference reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS)
                .child(POST);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public List getList()
    {
        return list;
    }
}
