package com.example.lostandfound.View.Adapter.MatchFragmentLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.R;

public class MatchListView
{
    View rootView;
    TextView nameText, descText, timeText;
    ImageView imageView;
    Card card;

    public MatchListView(LayoutInflater inflater, ViewGroup parent)
    {
        this.rootView = inflater.inflate(R.layout.items_match_fragment, parent, false);
    }

    public void initViews()
    {
        imageView = rootView.findViewById(R.id.image);
        nameText = rootView.findViewById(R.id.name);
        descText = rootView.findViewById(R.id.desc);
        timeText = rootView.findViewById(R.id.time);
    }


    public void bindDataToView(Object cards)
    {
        this.card = (Card) cards;
        nameText.setText(card.getId());
        descText.setText(card.getDesc());
        timeText.setText("Date :" + card.getDate() + "  " + "Time :" + card.getTime());
        Log.v("CARD Details",card.getId()+"  "+card.getDesc());

        switch (card.getProfileImageUrl()) {
            case "default":
                Glide.with(rootView.getContext()).load(R.mipmap.ic_launcher).into(imageView);
                break;
            default:
                Glide.with(rootView.getContext()).load(card.getProfileImageUrl()).into(imageView);
                break;
        }
    }

    public View getRootView() {
        return rootView;
    }
}