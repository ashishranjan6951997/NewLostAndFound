package com.example.lostandfound.Controller.CardController;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.lostandfound.Model.DatabaseForMatchFragment.FragmentDatabaseModel;
import com.example.lostandfound.View.SecondUi.FragmentInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;
import static com.example.lostandfound.NameClass.USERS;


public class CardController {
    DatabaseReference databaseReference;
    String currentUser;
    FragmentDatabaseModel model;
    FragmentInterface fragment;
    List list;

    public CardController(Fragment fragment) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.fragment = (FragmentInterface) fragment;
        list = new ArrayList();
        model = new FragmentDatabaseModel(list);
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

    public void setRecyclerViewForMessage()
    {
        model.setArrayListForMessage();

        Thread timer = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(DOUBLE_RENDER_TIME);
                    fragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.setRecyclerView(model.getListForMessage());
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