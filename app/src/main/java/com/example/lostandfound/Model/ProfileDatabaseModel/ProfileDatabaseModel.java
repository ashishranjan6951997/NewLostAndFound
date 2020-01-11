package com.example.lostandfound.Model.ProfileDatabaseModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.Model.MixedObjectPOJO.MixedObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

import static com.example.lostandfound.NameClass.DETAILS;
import static com.example.lostandfound.NameClass.EDIT;
import static com.example.lostandfound.NameClass.Found;
import static com.example.lostandfound.NameClass.IMAGE_URI;
import static com.example.lostandfound.NameClass.LatitudeStorageInDatabase;
import static com.example.lostandfound.NameClass.Lost;
import static com.example.lostandfound.NameClass.NAME;
import static com.example.lostandfound.NameClass.POST;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.amOrPmForStoringDatabse;
import static com.example.lostandfound.NameClass.categoryForStoringDatabase;
import static com.example.lostandfound.NameClass.dateForStoringDatabse;
import static com.example.lostandfound.NameClass.descriptionForStoringDatabase;
import static com.example.lostandfound.NameClass.hourForStoringDatabse;
import static com.example.lostandfound.NameClass.minuteForStoringDatabse;
import static com.example.lostandfound.NameClass.monthForStoringDatabse;
import static com.example.lostandfound.NameClass.yearForStoringDatabse;

public class ProfileDatabaseModel {

    List list;
    int found, lost;

    public ProfileDatabaseModel() {
        list = new ArrayList();
    }

    public Maybe<MixedObject> setArrayList() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final String userName[] = new String[1];
        userName[0] = "";

        final DatabaseReference referencePost = FirebaseDatabase
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
        final Map map = new HashMap();

        return Maybe.create(new MaybeOnSubscribe<MixedObject>() {
            @Override
            public void subscribe(final MaybeEmitter<MixedObject> emitter) throws Exception {

                referenceName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(NAME).exists()) {
                            userName[0] = dataSnapshot.child(NAME).getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                referencePost.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()) {
                            String name = "";
                            String desc = "";
                            String uri = "";
                            String date = "", month = "", year = "";
                            String hour = "", min = "";
                            String format = "";
                            Card card = new Card();

                            if (dataSnapshot.child(descriptionForStoringDatabase).exists()) {
                                desc = dataSnapshot.child(descriptionForStoringDatabase).getValue().toString();
                                card.setDesc(desc);
                            }

                            if (dataSnapshot.child(IMAGE_URI).exists()) {
                                uri = dataSnapshot.child(IMAGE_URI).getValue().toString();
                                card.setProfileImageUrl(uri);
                            }

                            if (dataSnapshot.child(dateForStoringDatabse).exists()) {
                                date = dataSnapshot.child(dateForStoringDatabse).getValue().toString();
                                card.setDate(date);
                            }

                            if (dataSnapshot.child(monthForStoringDatabse).exists()) {
                                month = dataSnapshot.child(monthForStoringDatabse).getValue().toString();
                                card.setMonth(month);
                            }

                            if (dataSnapshot.child(yearForStoringDatabse).exists()) {
                                year = dataSnapshot.child(yearForStoringDatabse).getValue().toString();
                                card.setYear(year);
                            }

                            if (dataSnapshot.child(minuteForStoringDatabse).exists()) {
                                min = dataSnapshot.child(minuteForStoringDatabse).getValue().toString();
                                card.setMinute(min);
                            }

                            if (dataSnapshot.child(hourForStoringDatabse).exists()) {
                                hour = dataSnapshot.child(hourForStoringDatabse).getValue().toString();
                                card.setHour(hour);
                            }

                            if (dataSnapshot.child(amOrPmForStoringDatabse).exists()) {
                                format = dataSnapshot.child(amOrPmForStoringDatabse).getValue().toString();
                                card.setFormat(format);
                            }

                            if (dataSnapshot.child(categoryForStoringDatabase).getValue().equals(Lost)) {
                                lost++;
                                Log.e("PROFILE LOST", String.valueOf(lost));
                            }

                            if (dataSnapshot.child(categoryForStoringDatabase).getValue().equals(Found)) {
                                found++;
                                Log.e("PROFILE FOUND", String.valueOf(found));
                            }

                            if (!userName[0].equals("")) {
                                name = userName[0];
                                card.setName(name);
                            }

                            list.add(card);
                            map.put(Lost, lost);
                            map.put(Found, found);
                            Log.e("PROFILE MAP", map.toString());

                            MixedObject mixedObject = new MixedObject();
                            mixedObject.setList(list);
                            mixedObject.setMap(map);

                            emitter.onSuccess(mixedObject);
                            emitter.onComplete();

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
        });


    }

    public Map getMap() {
        Map map = new HashMap();
        map.put(Lost, lost);
        map.put(Found, found);
        return map;
    }

    public List getList() {
        return list;
    }

}
