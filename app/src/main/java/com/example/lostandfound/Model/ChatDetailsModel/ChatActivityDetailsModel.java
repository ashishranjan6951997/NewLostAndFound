package com.example.lostandfound.Model.ChatDetailsModel;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.Model.ChatPOJO.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CREATED_BY;
import static com.example.lostandfound.NameClass.TEXT;

public class ChatActivityDetailsModel {
    List list;
    DatabaseReference referenceChat;

    public ChatActivityDetailsModel() {
        this.list = new ArrayList();
        referenceChat = FirebaseDatabase.getInstance().getReference().child(CHAT);
    }

    public void getChatMessage(String chatId)
    {
        referenceChat = referenceChat.child(chatId);
        Log.v("Beginning","IN BEGINNING");
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //   referenceChat = referenceChat.push();
        referenceChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v("IN DATA_SNAPSHOT", dataSnapshot.exists() + "");
                int k = 0;
                if (dataSnapshot.exists()) {

                    final String[] message = {};
                    final String[] createdByUser = {};

                    Log.v("child(TEXT)", dataSnapshot.child(TEXT).getValue() + "");

                    if (dataSnapshot.child(TEXT).getValue() != null) {
                        message[0] = dataSnapshot.child(TEXT).getValue().toString();
                        Log.v("MESSAGE[0]", message[0]);
                    }
                    if (dataSnapshot.child(CREATED_BY).getValue() != null) {
                        createdByUser[0] = dataSnapshot.child(CREATED_BY).getValue().toString();
                        Log.v("USER[0]", createdByUser[0]);
                    }


                    if (message[0] != null && createdByUser[0] != null) {
                        final Boolean currentUserBoolean[] = {};
                        if (createdByUser[0].equals(currentUser)) {
                            currentUserBoolean[0] = true;
                        }

//                        new java.util.Timer().schedule(
//                                new java.util.TimerTask() {
//                                    @Override
//                                    public void run() {
//                                        Chat c = new Chat(message[0],currentUserBoolean[0]);
//                                        list.add(c);
//                                        Log.v("List[0] PREVIOUS",list.size()+"");
//                                    }
//                                }, DOUBLE_RENDER_TIME
//                        );

                        Chat c = new Chat(message[0], currentUserBoolean[0]);
                        list.add(c);
                    }
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
        Log.v("SIZE[0]", list.size() + "");
        return list;
    }
}