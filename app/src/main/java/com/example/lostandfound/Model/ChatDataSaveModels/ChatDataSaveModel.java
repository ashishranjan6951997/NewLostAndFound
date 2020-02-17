package com.example.lostandfound.Model.ChatDataSaveModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CREATED_BY;
import static com.example.lostandfound.NameClass.DATE;
import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;
import static com.example.lostandfound.NameClass.HOUR;
import static com.example.lostandfound.NameClass.IS_URL;
import static com.example.lostandfound.NameClass.MINUTE;
import static com.example.lostandfound.NameClass.MONTH;
import static com.example.lostandfound.NameClass.RENDER_TIME;
import static com.example.lostandfound.NameClass.TEXT;
import static com.example.lostandfound.NameClass.YEAR;
import static com.example.lostandfound.NameClass.chatImageUri;

public class ChatDataSaveModel {

    StorageReference chatStorageReference;
    Bitmap bitmap;
    Context context;

    public ChatDataSaveModel(Context context) {
        chatStorageReference = FirebaseStorage.getInstance().getReference()
                .child(chatImageUri);
        this.context = context;
    }


    public Maybe<Card> saveDataInStorage(final String key, final String innerKey, final Map map, final boolean isUri)
    {

        final String chatPhoto[] = new String[1];
        chatStorageReference = chatStorageReference.child(key).child(innerKey);


        if(isUri)
        {
            if (map.get(TEXT).toString() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(map.get(TEXT).toString()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);
                byte[] bytes = outputStream.toByteArray();

                UploadTask uploadTask = chatStorageReference.putBytes(bytes);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        chatStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                Log.d("URI", uri.toString());
                                chatPhoto[0] = uri.toString();
                                map.put(TEXT,chatPhoto[0]);
                                Log.e("CHAT", chatPhoto[0] + "");

                                saveDataInDatabase(key,innerKey,map,isUri);

                            }
                        });
                    }
                });
            }
        }

        Log.e("MAP_before",map.toString());

        return Maybe.create(new MaybeOnSubscribe() {
            @Override
            public void subscribe(MaybeEmitter emitter) throws Exception {
//                saveDataInDatabase(key,innerKey,map,isUri);

                Card card = new Card();
                emitter.onSuccess(card);
                emitter.onComplete();
            }
        });
    }

    private void saveDataInDatabase(String key,String innerKey,Map dataMap,boolean isUri)
    {
        Log.e("MAP_after",dataMap.toString());

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference()
                .child(CHAT)
                .child(key)
                .child(innerKey);

        Map map = new HashMap();
        map.put(CREATED_BY, currentUser);
        map.put(TEXT, dataMap.get(TEXT).toString());
        map.put(IS_URL,isUri);
        map.put(HOUR,dataMap.get(HOUR).toString());
        map.put(MINUTE,dataMap.get(MINUTE).toString());
        map.put(DATE,dataMap.get(DATE).toString());
        map.put(MONTH,dataMap.get(MONTH).toString());
        map.put(YEAR,dataMap.get(YEAR).toString());

        chatReference.setValue(map);
    }
}