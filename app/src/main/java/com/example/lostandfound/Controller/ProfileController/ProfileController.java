package com.example.lostandfound.Controller.ProfileController;

import android.util.Log;

import com.example.lostandfound.Model.ProfileDatabaseModel.ProfileDatabaseModel;
import com.example.lostandfound.View.SecondUi.FragmentInterface;

import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;

public class ProfileController
{
    ProfileDatabaseModel model;
    FragmentInterface fragment;

    public ProfileController(FragmentInterface fragment)
    {
        model = new ProfileDatabaseModel();
        this.fragment = fragment;
    }

    public void showData()
    {
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
                            //Log.v("Size from controller ", list.size() + "");
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