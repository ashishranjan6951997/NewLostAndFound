package com.example.lostandfound.Controller.CardController;

import android.util.Log;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.Model.DatabaseForMatchFragment.MatchFragmentDatabaseModel;
import com.example.lostandfound.View.SecondUi.MatchesFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.lostandfound.NameClass.CONNECTIONS;
import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;
import static com.example.lostandfound.NameClass.LEFT_SWIPE;
import static com.example.lostandfound.NameClass.NO;
import static com.example.lostandfound.NameClass.RIGHT_SWIPE;
import static com.example.lostandfound.NameClass.USERS;
import static com.example.lostandfound.NameClass.YES;

public class CardController {
    DatabaseReference databaseReference;
    String currentUser;
    MatchFragmentDatabaseModel model;
    MatchesFragment fragment;
    List list;

    public CardController(MatchesFragment fragment) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.fragment = fragment;
        list = new ArrayList();
        model = new MatchFragmentDatabaseModel(list);
    }


    public void setRecyclerView() {
        model.setArrayList();
        Thread timer = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(DOUBLE_RENDER_TIME);
                    fragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.setRecyclerView(model.getList());
                            Log.v("Size from controller ", list.size() + "");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}