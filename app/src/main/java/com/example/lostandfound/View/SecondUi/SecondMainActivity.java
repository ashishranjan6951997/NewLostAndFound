package com.example.lostandfound.View.SecondUi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
                            break;
                        case R.id.nav_messages:
                            selectedfragment = new MessageFragment();
                            TAG = MessageFragmentTAG;
                            break;
                        case R.id.nav_profile:
                            selectedfragment = new ProfileFragment();
                            TAG = ProfileFragmentTAG;
                            break;
                        case R.id.nav_add:
                            selectedfragment = new AddFragment();
                            TAG = AddFragmentTAG;
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}