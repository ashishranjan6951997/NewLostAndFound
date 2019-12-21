package com.example.lostandfound.Controller.ChatController;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lostandfound.Model.ChatDataSaveModels.ChatDataSaveModel;
import com.example.lostandfound.Model.ChatDetailsModel.ChatActivityDetailsModel;
import com.example.lostandfound.View.Chat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.CONNECTIONS;
import static com.example.lostandfound.NameClass.CREATED_BY;
import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;
import static com.example.lostandfound.NameClass.IS_URL;
import static com.example.lostandfound.NameClass.RENDER_TIME;
import static com.example.lostandfound.NameClass.TEXT;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.emailForStroringDatabase;

public class ChatController {
    ChatActivity context;
    ChatActivityDetailsModel model;
    DatabaseReference referenceUser, referenceChat;
    String chatId;
    String currentUser;
    String key;
    ChatDataSaveModel saveModel;

    public ChatController(ChatActivity chatActivity, String user) {
        context = chatActivity;
        chatId = user;
        model = new ChatActivityDetailsModel();
        saveModel = new ChatDataSaveModel(context);
    }

    private void getChatId(String user) {
        referenceUser = FirebaseDatabase.getInstance().getReference().child(USERS);
        referenceChat = FirebaseDatabase.getInstance().getReference().child(CHAT);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.v("CREATE CHAT", "before createChatId");
    }

    public void send(final String user, final String text, final boolean isUri)
    {
        getChatId(user);

        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.v("USER", user);
                if (dataSnapshot.child(currentUser).hasChild(CONNECTIONS)) {
                    key = (String) dataSnapshot.child(currentUser).child(CONNECTIONS).child(user).child(CHAT_ID).getValue();
                    referenceChat = referenceChat.child(key);
                } else {
                    key = FirebaseDatabase.getInstance().getReference().child(CHAT).push().getKey();
                    referenceChat = referenceChat.child(key);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Log.v("BEFORE FLOW","create chat id");
                if (!dataSnapshot.child(currentUser).child(CONNECTIONS).hasChild(user) || !dataSnapshot.child(currentUser).child(CONNECTIONS).child(user).child(CHAT_ID).exists()) {
                    int a = 9;
                    DatabaseReference database = referenceUser;

                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                referenceUser.child(user).child(CONNECTIONS).child(currentUser).child(CHAT_ID).setValue(key);
                                referenceUser.child(currentUser).child(CONNECTIONS).child(user).child(CHAT_ID).setValue(key);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        new java.util.Timer().schedule(

                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (!text.equals("") || !text.isEmpty())
                        {
                            referenceChat = referenceChat.push();
                            String innerKey = referenceChat.getKey();
                            if(!isUri) {
                                Map map = new HashMap();
                                map.put(CREATED_BY, currentUser);
                                map.put(TEXT, text);
                                map.put(IS_URL,isUri);

                                referenceChat.setValue(map);
                            }
                            else
                            {
                                saveModel.saveDataInStorage(key,innerKey,text,isUri);
                            }
                        }

                    }
                }, RENDER_TIME
        );

        // Log.v("AFTER FLOW","send");

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        getList();
                    }
                }, DOUBLE_RENDER_TIME
        );
    }

    private void getList() {
        int k = 9;
        model.getChatMessage(key);

//        new java.util.Timer().schedule(
//                new java.util.TimerTask()
//                {
//                    @Override
//                    public void run()
//                    {
//                        Log.v("List[0] LATER", model.getList().size() + "");
//                        context.bindRecyclerView(model.getList());
//                    }
//                }, DOUBLE_RENDER_TIME + 1000
//        );

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(DOUBLE_RENDER_TIME);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            context.bindRecyclerView(model.getList());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }


    public void createChatId(final String user) {
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                key = FirebaseDatabase.getInstance().getReference().child(CHAT).push().getKey();
                referenceChat = referenceChat.child(key);

                // Log.v("BEFORE FLOW","create chat id");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}