package com.example.lostandfound.View.SecondUi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lostandfound.R;
import com.example.lostandfound.View.Authentication.AuthenticateActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.lostandfound.NameClass.AddFragmentTAG;
import static com.example.lostandfound.NameClass.MatchesFragmentTAG;
import static com.example.lostandfound.NameClass.MessageFragmentTAG;
import static com.example.lostandfound.NameClass.ProfileFragmentTAG;

public class SecondMainActivity extends AppCompatActivity {

    static String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MatchesFragment(), MatchesFragmentTAG).commit();

        if(!isConnectedToInternet())
        {
            showToast();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragment = null;
                    switch (menuItem.getItemId()) {

                        case R.id.nav_matches:
                            selectedfragment = new MatchesFragment();
                            TAG = MatchesFragmentTAG;
                            if(!isConnectedToInternet())
                            {
                                showToast();
                            }
                            break;
                        case R.id.nav_messages:
                            selectedfragment = new MessageFragment();
                            TAG = MessageFragmentTAG;

                            if(!isConnectedToInternet())
                            {
                                showToast();
                            }
                            break;
                        case R.id.nav_profile:
                            selectedfragment = new ProfileFragment();
                            TAG = ProfileFragmentTAG;

                            if(!isConnectedToInternet())
                            {
                                showToast();
                            }
                            break;
                        case R.id.nav_add:
                            selectedfragment = new AddFragment();
                            TAG = AddFragmentTAG;
                            if(!isConnectedToInternet())
                            {
                                showToast();
                            }
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedfragment, TAG).commit();
                    return true;
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(AddFragmentTAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.second_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SecondMainActivity.this, AuthenticateActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showToast()
    {
        Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
    }
}