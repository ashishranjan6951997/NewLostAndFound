package com.example.lostandfound.View.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.lostandfound.View.Authentication.CallBackInterface;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Authentication.LoginFragment;
import com.example.lostandfound.View.Authentication.SignUpFragment;
import com.example.lostandfound.View.SecondUi.SecondMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.lostandfound.NameClass.logIn;
import static com.example.lostandfound.NameClass.signUp;

public class AuthenticateActivity extends AppCompatActivity implements CallBackInterface {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if(currentUser != null)
                {
                    Intent intent = new Intent(AuthenticateActivity.this, SecondMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


        fragmentManager = getSupportFragmentManager();
        addLoginFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void addLoginFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setCallBackInterface(this);

        // to know whether fragment is present after multiple fragment transaction

        if (getSupportFragmentManager().findFragmentById(R.id.framecon) != null) {
            fragmentTransaction.replace(R.id.framecon, loginFragment);
        } else {
            fragmentTransaction.add(R.id.framecon, loginFragment);

        }
        fragmentTransaction.commit();
    }

    private void addSignUpFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.setCallBackInterface(this);


        // to know whether fragment is present after multiple fragment transaction

        if (getSupportFragmentManager().findFragmentById(R.id.framecon) != null) {
            fragmentTransaction.replace(R.id.framecon, signUpFragment);
        } else {
            fragmentTransaction.add(R.id.framecon, signUpFragment);

        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void callBackMethod(String str) {
        if (str.equals(signUp))
            addSignUpFragment();

        if (str.equals(logIn))
            addLoginFragment();
    }
}
