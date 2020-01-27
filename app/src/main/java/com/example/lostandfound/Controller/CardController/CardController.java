package com.example.lostandfound.Controller.CardController;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.lostandfound.Model.CardPOJO.Card;
import com.example.lostandfound.Model.DatabaseForMatchFragment.FragmentDatabaseModel;
import com.example.lostandfound.View.Adapter.MessageAdapter;
import com.example.lostandfound.View.SecondUi.FragmentInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;
import static com.example.lostandfound.NameClass.USERS;


public class CardController {
    DatabaseReference databaseReference;
    String currentUser;
    FragmentDatabaseModel model;
    FragmentInterface fragment;
    List list;
    double choosenLongitude;
    double choosenLatitude;
    CompositeDisposable disposable;

    public CardController(Fragment fragment) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS);
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.fragment = (FragmentInterface) fragment;
        list = new ArrayList();
        model = new FragmentDatabaseModel();
        disposable = new CompositeDisposable();
    }


    public void setRecyclerView() {
        disposable.add(model.setArrayList(choosenLongitude, choosenLatitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<List>() {
                    @Override
                    public void onSuccess(List incomingList) {
                        fragment.setRecyclerView(incomingList);

                        Log.e("SIZE", incomingList.size() + "");
                        //incomingList.clear();
                        Log.e("SIZE REMOVED", String.valueOf(incomingList.size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void setRecyclerViewForMessage() {
        disposable.add(model.setArrayListForMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<List>() {
                    @Override
                    public void onSuccess(List incomingList) {
                        fragment.setRecyclerView(incomingList);
                        Log.e("SIZE", incomingList.toString());
                        //incomingList.clear();
                        Log.e("SIZE REMOVED", String.valueOf(incomingList.toString()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void setLatLang(double choosenLongitude, double choosenLatitude) {
        this.choosenLongitude = choosenLongitude;
        this.choosenLatitude = choosenLatitude;
    }

    public void filter(String s, List<Card> dataList, MessageAdapter adapter) {
        ArrayList<Card> list = new ArrayList();
        for (Card card : dataList) {
            if (card.getName().toLowerCase().contains(s.toLowerCase())) {
                list.add(card);
            }
        }
        adapter.filterList(list);
    }
}
