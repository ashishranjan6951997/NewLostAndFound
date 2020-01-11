package com.example.lostandfound.Controller.ProfileController;

import android.util.Log;

import com.example.lostandfound.Model.MixedObjectPOJO.MixedObject;
import com.example.lostandfound.Model.ProfileDatabaseModel.ProfileDatabaseModel;
import com.example.lostandfound.View.SecondUi.FragmentInterface;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.lostandfound.NameClass.DOUBLE_RENDER_TIME;

public class ProfileController {
    ProfileDatabaseModel model;
    FragmentInterface fragment;
    CompositeDisposable disposable;

    public ProfileController(FragmentInterface fragment) {
        model = new ProfileDatabaseModel();
        this.fragment = fragment;

        this.disposable = new CompositeDisposable();
    }

    public void showData() {

        disposable.add(model.setArrayList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<MixedObject>() {
                    @Override
                    public void onSuccess(MixedObject obj) {
                        fragment.setRecyclerView(obj.getList(),obj.getMap());
                        Log.e("ON SUCCESS","in success");
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.e("ON ERROR",e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }
}