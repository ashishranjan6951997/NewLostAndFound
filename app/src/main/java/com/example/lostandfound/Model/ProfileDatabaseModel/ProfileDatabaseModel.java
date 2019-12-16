package com.example.lostandfound.Model.ProfileDatabaseModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.LatitudeStorageInDatabase;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.descriptionForStoringDatabase;

public class ProfileDatabaseModel {

    List list;

    public ProfileDatabaseModel() {
        list = new ArrayList();
    }

    public void setArrayList() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        final String userName[] = new String[1];

        DatabaseReference referencePost = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS)
                .child(POST);

        final DatabaseReference referenceName = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(USERS)
                .child(userId)
                .child(DETAILS)
                .child(EDIT);

        referenceName.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                userName[0] = dataSnapshot.child(NAME).getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        referencePost.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {
                    String name = "";
                    String desc = "";
                    String uri = "";
                    Card card = new Card();

                    if (dataSnapshot.child(descriptionForStoringDatabase).exists()) {
                        desc = dataSnapshot.child(descriptionForStoringDatabase).getValue().toString();
                        card.setDesc(desc);
                    }

                    if (dataSnapshot.child(IMAGE_URI).exists()) {
                        uri = dataSnapshot.child(IMAGE_URI).getValue().toString();
                        card.setProfileImageUrl(uri);
                    }

                    if(!userName[0].equals(""))
                    {
                        name = userName[0];
                        card.setId(name);
                    }

                    list.add(card);

                }
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

    public List getList() {
        return list;
    }

}
