package com.example.lostandfound.Model.ChatDataSaveModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

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

import static com.example.lostandfound.NameClass.CHAT;
import static com.example.lostandfound.NameClass.CREATED_BY;
import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;
import static com.example.lostandfound.NameClass.IS_URL;
import static com.example.lostandfound.NameClass.RENDER_TIME;
import static com.example.lostandfound.NameClass.TEXT;
import static com.example.lostandfound.NameClass.chatImageUri;

public class ChatDataSaveModel {

    StorageReference chatStorageReference;
    Bitmap bitmap;
    Context context;

    public ChatDataSaveModel(Context context)
    {
        chatStorageReference = FirebaseStorage.getInstance().getReference()
                .child(chatImageUri);
        this.context = context;
    }

    public void saveDataInStorage(final String key, final String innerKey, String uri, final boolean isUri)
    {

        final String chatPhoto[] = new String[1];
        chatStorageReference = chatStorageReference.child(key).child(innerKey);
        if(uri!=null)
        {
            try{
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(uri));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,outputStream);
            byte[] bytes = outputStream.toByteArray();

            UploadTask uploadTask = chatStorageReference.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    chatStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            Log.d("URI",uri.toString());
                            chatPhoto[0] = uri.toString();
                            Log.e("CHAT BEFORE",chatPhoto[0]+"");
                        }
                    });
                }
            });
        }


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here

                        Log.e("CHAT AFTER",chatPhoto[0]+"");
                        saveDataInDatabase(key,innerKey,chatPhoto[0],isUri);
                    }
                },
                10000
        );
    }

    private void saveDataInDatabase(String key,String innerKey,String chatPhotoUrl,boolean isUri)
    {
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference()
                .child(CHAT)
                .child(key)
                .child(innerKey);

        Map map = new HashMap();
        map.put(CREATED_BY, currentUser);
        map.put(TEXT, chatPhotoUrl);
        map.put(IS_URL,isUri);

        chatReference.setValue(map);
    }
}