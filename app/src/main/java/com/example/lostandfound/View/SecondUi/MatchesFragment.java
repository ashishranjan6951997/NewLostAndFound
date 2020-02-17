package com.example.lostandfound.View.SecondUi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Controller.CardController.CardController;
import com.example.lostandfound.MapActivity.MapActivity;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Adapter.MatchesAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.lostandfound.NameClass.RECEIVED_TIME;


public class MatchesFragment extends FragmentInterface {
    int locationRequestCode = 1;
    boolean isScrolling;
    View rootView;
    CardController controller;
    MatchesAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout layout;
    ProgressBar progressBar;
    int currentItems, totalItems, scrollOutItems;
    EditText searchText;
    double choosenLongitude;
    double choosenLatitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.matches_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    private void init()
    {
        getActivity().setTitle("Search Your Item");
        recyclerView = rootView.findViewById(R.id.recycler);
        layout = rootView.findViewById(R.id.substitute_linear);
        progressBar = rootView.findViewById(R.id.progress);
        controller = new CardController(this);
        //controller.setFlingContainer();
        searchText = rootView.findViewById(R.id.fragmentLocationButton);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedToInternet()) {

                    List list = new ArrayList();
                    layout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    adapter = new MatchesAdapter(getContext(), list);
                    adapter.notifyDataSetChanged();
                    final RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);

                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    startActivityForResult(intent, locationRequestCode);
                }
                else {
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void setRecyclerView(final List list) {
        if (list.size() == 0)
        {
            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {

            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new MatchesAdapter(getContext(), list);
            adapter.notifyDataSetChanged();
            final RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == locationRequestCode && resultCode == RESULT_OK) {
            choosenLongitude = data.getDoubleExtra("choosenLongitude", 0);
            choosenLatitude = data.getDoubleExtra("choosenLatitude", 0);
            searchText.setText(data.getStringExtra("StringText"));
            controller.setLatLang(choosenLongitude, choosenLatitude);
            controller.setRecyclerView();

        }
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }


    private void showData() {
        progressBar.setVisibility(View.VISIBLE);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(RECEIVED_TIME);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
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