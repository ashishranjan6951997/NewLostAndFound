package com.example.lostandfound.Model.DatabaseForMatchFragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.lostandfound.NameClass.CONNECTIONS;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.NO;
import static com.example.lostandfound.NameClass.RENDER_TIME;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.YES;

public class MatchFragmentDatabaseModel {

    public List list;

    public MatchFragmentDatabaseModel(List list) {

        this.list = list;
    }

    public void setArrayList() {
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(USERS);
        final Card[] item = {null};
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {

                    String profileImage = "default";

                    Log.e("ERROR IS -->", dataSnapshot.child(IMAGE_URI).toString());

                    if (dataSnapshot.child(IMAGE_URI).getValue() == null || dataSnapshot.child(IMAGE_URI).getValue().equals(profileImage)) {
                        item[0] = new Card(dataSnapshot.getKey(), dataSnapshot.child(NAME).getValue().toString(), profileImage);
                        list.add(item[0]);
                    } else {
                        profileImage = dataSnapshot.child(IMAGE_URI).getValue().toString();
                        item[0] = new Card(dataSnapshot.getKey(), dataSnapshot.child(NAME).getValue().toString(), profileImage);
                        list.add(item[0]);
                    }

                    Log.v("Item[0] value is ", item[0] + "");
                    //list.add(item);
//
//                    new java.util.Timer().schedule(
//                            new java.util.TimerTask() {
//                                @Override
//                                public void run() {
//                                    // your code here
//                                    Log.v("LATER item[0] value is ",item[0]+"");
//                                    list.add(item[0]);
//                                    Log.v("Size",list.size()+"");
//                                }
//                            },
//                            RENDER_TIME
//                    );
                    int k = 9;
                    // ar.notifyDataSetChanged();
                } else {
                    //Log.d("ERROR IS----->>>", "");
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

//
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        // your code here
//                        Log.v("LATER item[0] value is ",item[0]+"");
//                        list.add(item[0]);
//                        Log.v("List Size",list.size()+"");
//                    }
//                },
//                RENDER_TIME
//        );
    }

    public List getList() {
        return list;
    }
}