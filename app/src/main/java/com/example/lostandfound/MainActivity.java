package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.lostandfound.Authentication.CallBackInterface;
import com.example.lostandfound.Authentication.LoginFragment;
import com.example.lostandfound.Authentication.SignUpFragment;

public class MainActivity extends AppCompatActivity implements CallBackInterface {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        addLoginFragment();
    }

    private void addLoginFragment() {
        fragmentTransaction=fragmentManager.beginTransaction();

        LoginFragment loginFragment=new LoginFragment();
        loginFragment.setCallBackInterface(this);
        fragmentTransaction.add(R.id.framecon,loginFragment);
        fragmentTransaction.commit();
    }
    private void addSignUpFragment()
    {
        fragmentTransaction=fragmentManager.beginTransaction();
        SignUpFragment signUpFragment=new SignUpFragment();
        fragmentTransaction.replace(R.id.framecon,signUpFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void callBackMethod() {
        addSignUpFragment();
    }
}
