package com.example.basicfragmentbottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {

    //    Bundle extras = getIntent().getExtras();
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView bnv = findViewById(R.id.bottom_navigngo);
        bnv.setOnNavigationItemSelectedListener(navListner);
        String uid;

        Intent intent = getIntent();


        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragmentHomeNGO()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment currFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home: {
                            currFragment = new FragmentHomeNGO();
                            Bundle args = new Bundle();
                            args.putString("UID", id);
                            currFragment.setArguments(args);
                            break;

                        }
                        case R.id.nav_info: {
                            currFragment = new FragmentInfoNGO();
                            break;
                        }
                        case R.id.nav_add:{
                            currFragment = new EventRequestFragment();
                            break;
                        }

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, currFragment).commit();
                    return true;
                }
            };
}
