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

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CREATED_BY;
import static com.example.lostandfound.NameClass.DATE;
import static com.example.lostandfound.NameClass.HOUR;
import static com.example.lostandfound.NameClass.IS_URL;
import static com.example.lostandfound.NameClass.MINUTE;
import static com.example.lostandfound.NameClass.MONTH;
import static com.example.lostandfound.NameClass.TEXT;
import static com.example.lostandfound.NameClass.YEAR;

public class ChatActivityDetailsModel {
    List list;
    DatabaseReference referenceChat;

    public ChatActivityDetailsModel() {
        this.list = new ArrayList();
        referenceChat = FirebaseDatabase.getInstance().getReference().child(CHAT);
    }

    public Maybe<List> getChatMessage(final String chatId) {

//        Log.e("CHAT_ID",chatId);

        return Maybe.create(new MaybeOnSubscribe<List>() {
            @Override
            public void subscribe(final MaybeEmitter<List> emitter) throws Exception {

                referenceChat = referenceChat.child(chatId);
                Log.v("Beginning", "IN BEGINNING");
                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                //   referenceChat = referenceChat.push();
                referenceChat.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.v("IN DATA_SNAPSHOT", dataSnapshot.exists() + "");
                        int k = 0;
                        if (dataSnapshot.exists()) {

                            final String[] message = new String[1];
                            final String[] createdByUser = new String[1];
                            final String[] isPhoto = new String[1];
                            final String[] date = new String[1];
                            final String[] month = new String[1];
                            final String[] year = new String[1];
                            final String[] hour = new String[1];
                            final String[] min = new String[1];


                            isPhoto[0] = "";
                            date[0] = "";
                            month[0] = "";
                            year[0] = "";
                            hour[0] = "";
                            min[0] = "";

                            Log.v("child(TEXT)", dataSnapshot.child(TEXT).getValue() + "");

                            if (dataSnapshot.child(TEXT).getValue() != null) {
                                message[0] = dataSnapshot.child(TEXT).getValue().toString();
                                Log.v("MESSAGE[0]", message[0]);
                            }
                            if (dataSnapshot.child(CREATED_BY).getValue() != null)
                            {
                                createdByUser[0] = dataSnapshot.child(CREATED_BY).getValue().toString();
                                Log.v("USER[0]", createdByUser[0]);
                            }

                            if (dataSnapshot.child(IS_URL).exists())
                            {
                                isPhoto[0] = dataSnapshot.child(IS_URL).getValue().toString();
                            }

                            if(dataSnapshot.child(DATE).exists())
                            {
                                hour[0] = dataSnapshot.child(DATE).getValue().toString();
                            }

                            if(dataSnapshot.child(MONTH).exists())
                            {
                                min[0] = dataSnapshot.child(MONTH).getValue().toString();
                            }

                            if(dataSnapshot.child(YEAR).exists())
                            {
                                year[0] = dataSnapshot.child(YEAR).getValue().toString();
                            }

                            if(dataSnapshot.child(HOUR).exists())
                            {
                                hour[0] = dataSnapshot.child(HOUR).getValue().toString();
                            }
                            if(dataSnapshot.child(MINUTE).exists())
                            {
                                min[0] = dataSnapshot.child(MINUTE).getValue().toString();
                            }

                            if (message[0] != null && createdByUser[0] != null) {
                                final Boolean currentUserBoolean[] = new Boolean[1];
                                currentUserBoolean[0] = false;
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

                                boolean isPhotoBoolean = false;
                                if (isPhoto[0].equals("true")) {
                                    isPhotoBoolean = true;
                                }

                                c.setPhoto(isPhotoBoolean);
                                c.setDate(date[0]);
                                c.setMonth(month[0]);
                                c.setYear(year[0]);
                                c.setHour(hour[0]);
                                c.setMinute(min[0]);

                                list.add(c);

                                emitter.onSuccess(list);

                                Log.e("SIZE_LIST", list.size() + "");
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
                // Log.v("LATER SIZE[0]", list.size() + "");

            }
        });
    }
}