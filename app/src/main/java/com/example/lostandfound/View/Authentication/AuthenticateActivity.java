package com.example.lostandfound.View.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.lostandfound.View.Authentication.CallBackInterface;
import com.example.lostandfound.R;
import com.example.lostandfound.View.Authentication.LoginFragment;
import com.example.lostandfound.View.Authentication.SignUpFragment;

import static com.example.lostandfound.NameClass.logIn;
import static com.example.lostandfound.NameClass.signUp;

public class AuthenticateActivity extends AppCompatActivity implements CallBackInterface {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        addLoginFragment();
    }

    private void addLoginFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setCallBackInterface(this);

        // to know whether fragment is present after multiple fragment transaction

        if(getSupportFragmentManager().findFragmentById(R.id.framecon) !=null )
        {
            fragmentTransaction.replace(R.id.framecon,loginFragment);
        }
        else
        {
            fragmentTransaction.add(R.id.framecon, loginFragment);

        }
        fragmentTransaction.commit();
    }

    private void addSignUpFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.setCallBackInterface(this);


        // to know whether fragment is present after multiple fragment transaction

        if(getSupportFragmentManager().findFragmentById(R.id.framecon) !=null )
        {
            fragmentTransaction.replace(R.id.framecon,signUpFragment);
        }
        else
        {
            fragmentTransaction.add(R.id.framecon, signUpFragment);

        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void callBackMethod(String str) {
        if (str.equals(signUp))
            addSignUpFragment();

        if(str.equals(logIn))
            addLoginFragment();
    }
}
