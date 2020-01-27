package com.example.lostandfound.Model.ChatDataSaveModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.CONNECTIONS;
import static com.example.lostandfound.NameClass.USERS;

public class ChatKeySaveModel {
    DatabaseReference referenceUser, referenceChat;

    public ChatKeySaveModel() {
        referenceUser = FirebaseDatabase.getInstance().getReference().child(USERS);
        referenceChat = FirebaseDatabase.getInstance().getReference().child(CHAT);
    }

    public Maybe<String> saveChatKey(final String user, final String currentUser) {

        final String[] key = new String[1];

        return Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(final MaybeEmitter<String> emitter) throws Exception {


                referenceUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.v("USER", user);
                        if (dataSnapshot.child(currentUser).child(CONNECTIONS).hasChild(user)) {
                            if (dataSnapshot.child(currentUser).child(CONNECTIONS).child(user).hasChild(CHAT_ID)) {
                                key[0] = (String) dataSnapshot.child(currentUser).child(CONNECTIONS).child(user).child(CHAT_ID).getValue();
                                referenceChat = referenceChat.child(key[0]);
                                Log.e("KEY SAVE0", key[0]);
                            }
                        } else {
                            key[0] = FirebaseDatabase.getInstance().getReference().child(CHAT).push().getKey();
                            referenceChat = referenceChat.child(key[0]);
                            Log.e("KEY SAVE0", key[0]);

                        }


                        Log.e("KEY SAVE1", key[0] + "");
                        emitter.onSuccess(key[0]);
                        emitter.onComplete();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
