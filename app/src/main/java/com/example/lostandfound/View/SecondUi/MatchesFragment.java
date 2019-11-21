package com.example.lostandfound.View.SecondUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Controller.CardController.CardController;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Adapter.MatchesAdapter;

import java.util.List;

import static com.example.lostandfound.NameClass.RECEIVED_TIME;


public class MatchesFragment extends Fragment {
    Button button;
    boolean isScrolling;
    View rootView;
    CardController controller;
    MatchesAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    int currentItems,totalItems,scrollOutItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.matches_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init()
    {
        recyclerView = rootView.findViewById(R.id.recycler);
        progressBar = rootView.findViewById(R.id.progress);
        controller = new CardController(this);
        //controller.setFlingContainer();
        button=rootView.findViewById(R.id.fragmentLocationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        Card card = new Card("123","Sayan","default");
//        List list1 = new ArrayList();
//        list1.add(card);
//        setFlingContainer(list1);
        controller.setRecyclerView();
    }

    public void setRecyclerView(final List list)
    {
        adapter = new MatchesAdapter(getContext(), list);
        adapter.notifyDataSetChanged();
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();

                if(isScrolling == true && currentItems + scrollOutItems == totalItems)
                {
                    showData();
                }
            }
        });
    }

    private void showData()
    {
        progressBar.setVisibility(View.VISIBLE);

        Thread timer = new Thread(){

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