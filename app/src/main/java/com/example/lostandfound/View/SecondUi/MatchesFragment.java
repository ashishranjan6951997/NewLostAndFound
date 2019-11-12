package com.example.lostandfound.View.SecondUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lostandfound.R;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

public class MatchesFragment extends Fragment {

    View rootView;
    SwipeFlingAdapterView flingContainer;
    TextView descText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.matches_fragment,container,false);
        init();
        return rootView;
    }

    private void init()
    {
        flingContainer = rootView.findViewById(R.id.frame);
        descText = rootView.findViewById(R.id.desc);

    }
}
