package com.example.lostandfound.Controller.ChatController;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.Model.ChatDataSaveModels.ChatDataSaveModel;
import com.example.lostandfound.Model.ChatDataSaveModels.ChatKeySaveModel;
import com.example.lostandfound.Model.ChatDetailsModel.ChatActivityDetailsModel;
import com.example.lostandfound.View.Chat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.CONNECTIONS;
import static com.example.lostandfound.NameClass.TEXT;
import static com.example.lostandfound.NameClass.USERS;

public class ChatController
{
    ChatActivity context;
    ChatActivityDetailsModel chatActivityDetailsModel;
    ChatKeySaveModel chatKeySaveModel;
    DatabaseReference referenceUser, referenceChat;
    String chatId;
    String currentUser;
    String key;
    ChatDataSaveModel chatDataSaveModel;
    CompositeDisposable disposable;

    public ChatController(ChatActivity chatActivity, String user) {
        context = chatActivity;
        chatId = user;
        chatActivityDetailsModel = new ChatActivityDetailsModel();
        chatDataSaveModel = new ChatDataSaveModel(context);

        disposable = new CompositeDisposable();
    }

    private void getChatId() {
        referenceUser = FirebaseDatabase.getInstance().getReference().child(USERS);
        referenceChat = FirebaseDatabase.getInstance().getReference().child(CHAT);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.v("CREATE CHAT", "before createChatId");
    }

    private void getKey(final String user) {
        getChatId();


        Log.e("KEY0",key+"");
        chatKeySaveModel = new ChatKeySaveModel();
        disposable.add(chatKeySaveModel.saveChatKey(user,currentUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onSuccess(String str) {
                        key = str;
                        Log.e("KEY1",key+"");
                        getList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }


    public void send(final String user, final Map data, final boolean isUri)
    {
        getKey(user);
//        Log.e("TIME",time);

        Log.e("REFERENCECHAT 1", referenceChat.toString());
        Log.e("CHAT_KEY1",key+"");
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.e("CHAT_KEY2",key+"");


        if (!data.get(TEXT).equals("") || !data.get(TEXT).toString().isEmpty()) {
            Log.e("REFERENCECHAT 2", referenceChat.toString());

            referenceChat = referenceChat.push();

            Log.e("REFERENCECHAT 3", referenceChat.toString());

            String innerKey = referenceChat.getKey();

            disposable.add(chatDataSaveModel.saveDataInStorage(key, innerKey, data, isUri)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableMaybeObserver<Card>() {
                        @Override
                        public void onSuccess(Card incomingList) {
                            Log.e("ELSE ONSUCCESS", "In else");
                            getList();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            Log.e("ELSE ONCOMPLETE", "In else");
                            getList();

                        }

                    }));
        }

        /* else part not required here as else part code is written above */
//        } else {
//
//            Log.e("ELSE","In else");
//            getList();
//        }
    }


    public void getList()
    {
        Log.e("CHAT_KEY3",key+"");

        disposable.add(chatActivityDetailsModel.getChatMessage(key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<List>() {
                    @Override
                    public void onSuccess(List incomingList)
                    {
                        Log.e("ELSE GET","In else");

                        Log.e("LIST SIZE",incomingList.size()+"");
                        context.bindRecyclerView(incomingList);
                        int k = 9;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {


                    }

                }));
    }

    public void destroy()
    {
        disposable.dispose();
    }

}