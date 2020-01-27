package com.example.lostandfound.View.SecondUi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.Controller.CardController.CardController;
import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.OnItemClickInterface;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Adapter.MatchesAdapter;
import com.example.lostandfound.View.Adapter.MessageAdapter;
import com.example.lostandfound.View.Chat.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.lostandfound.NameClass.CHAT_ID;
import static com.example.lostandfound.NameClass.RECEIVED_TIME;

public class MessageFragment extends FragmentInterface implements OnItemClickInterface
{
    View rootView;
    RecyclerView recyclerView;
    CardController controller;
    MessageAdapter adapter;
    boolean isScrolling;
    ProgressBar progressBar;
    int currentItems,totalItems,scrollOutItems;
    EditText searchEdit;
    List<Card> dataList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView =  inflater.inflate(R.layout.message_fragment,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
    }


    public void init()
    {
        recyclerView = rootView.findViewById(R.id.my_recycler_view);
        progressBar = rootView.findViewById(R.id.progress);
        searchEdit = rootView.findViewById(R.id.search);
        controller = new CardController(this);
        controller.setRecyclerViewForMessage();

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(dataList!=null) {
                    controller.filter(s.toString(), dataList, adapter);
                }
                else{
                    Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        getActivity().setTitle("Chats");
    }


    @Override
    public void setRecyclerView(List list)
    {
        dataList = list;
        adapter = new MessageAdapter(getContext(), list,this);
        adapter.notifyDataSetChanged();
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int pos,List list)
    {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        Bundle b = new Bundle();
        Card c = (Card) list.get(pos);
        String id = c.getId();
        b.putString(CHAT_ID,id);
        intent.putExtras(b);
        getActivity().startActivity(intent);

    }
}