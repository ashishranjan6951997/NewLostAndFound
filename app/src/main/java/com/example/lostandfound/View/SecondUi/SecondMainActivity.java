package com.example.lostandfound.View.SecondUi;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.lostandfound.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SecondMainActivity extends AppCompatActivity {@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second_main);
    BottomNavigationView bottomNavigationView=findViewById(R.id.button_navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
}
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedfragment=null;
                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_home:
                            selectedfragment=new HomeFragment();
                            break;
                        case R.id.nav_matches:
                            selectedfragment=new MatchesFragment();
                            break;
                        case R.id.nav_messages:
                            selectedfragment=new MessageFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    return true;
                }
            };
}