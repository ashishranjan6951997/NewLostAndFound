package com.example.lostandfound.Controller.DatabaseController;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lostandfound.Model.DatabaseModel.RealtimeDatabaseDemoModel;
import com.example.lostandfound.View.SecondUi.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.LatitudeStorageInDatabase;
import static com.example.lostandfound.NameClass.LongitudeStorageInDatabase;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.bioForStoringDatabase;
import static com.example.lostandfound.NameClass.emailForStroringDatabase;
import static com.example.lostandfound.NameClass.nameForStoringDatabase;
import static com.example.lostandfound.NameClass.phoneForStoringDatabase;
import static com.example.lostandfound.NameClass.postImageUri;
import static com.example.lostandfound.NameClass.profileImageUri;
import static com.example.lostandfound.NameClass.radioButtonText;

public class SaveDataController {
    Map map;
    RealtimeDatabaseDemoModel databaseDemo;
    StorageReference storageReferenceForPost;
    StorageReference storageReferenceForProfile;
    String userId;
    //HomeFragment homeFragment;
    Context context;

    public SaveDataController(Context ctx) {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseDemo = new RealtimeDatabaseDemoModel();
        storageReferenceForPost = FirebaseStorage.getInstance().getReference().child(postImageUri).child(userId);
        storageReferenceForProfile = FirebaseStorage.getInstance().getReference().child(profileImageUri).child(userId);
        context = ctx;
        map = new HashMap();
    }


    public void saveData(String[] array, Uri uri, String str) {
        // map.put(profileImageUri,);
        //getPhotoUri(uri);
        getPhotoUri(uri, array, str);
    }

    public void getPhotoUri(Uri uri, final String[] array, String text) {
        final String[] str = new String[1];
        Bitmap bitmap = null;

        if (uri != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.v("URI", uri.toString());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);
            byte[] bytes = outputStream.toByteArray();

            if (text.equals(POST)) {
                UploadTask uploadTask = storageReferenceForPost.putBytes(bytes);
                try {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReferenceForPost.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //map.put(profileImageUri, uri.toString());
                                    str[0] = uri.toString();
                                    Log.v("Modified URI--", str[0] + "");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showErrorMessage();

                        }
                    });
                } catch (Exception e) {
                    //Toast.makeText(view.getRootView().getContext(),"Error--"+e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("Error--", e.getMessage());
                }

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                map.put(nameForStoringDatabase, array[0]);
                                map.put(radioButtonText, array[1]);
                                map.put(profileImageUri, str[0]);
                                map.put(LatitudeStorageInDatabase, array[2]);
                                map.put(LongitudeStorageInDatabase, array[3]);
                                databaseDemo.saveData(map);
                                Log.v("Modified URkkkkI--", str[0] + "");
                            }
                        },
                        5000);

            }

            if (text.equals(EDIT)) {
                UploadTask uploadTask = storageReferenceForProfile.putBytes(bytes);
                try {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReferenceForProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //map.put(profileImageUri, uri.toString());
                                    str[0] = uri.toString();
                                    Log.v("Modified URI--", str[0] + "");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showErrorMessage();
                        }
                    });
                } catch (Exception e) {
                    //Toast.makeText(view.getRootView().getContext(),"Error--"+e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.e("Error--", e.getMessage());
                }


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Map map = new HashMap();
                                map.put(nameForStoringDatabase, array[0]);
                                map.put(bioForStoringDatabase, array[1]);
                                map.put(emailForStroringDatabase, array[2]);
                                map.put(phoneForStoringDatabase, array[3]);
                                map.put(profileImageUri, str[0]);

                                databaseDemo.saveData(map);
                            }
                        }, 5000);
            }
        }
    }

    private String addUri(String toString) {
        return toString;
    }

    private void showErrorMessage() {
        //homeFragment.photoUploadError();
    }
}