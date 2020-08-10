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

public class MainActivity extends AppCompatActivity {

//    Bundle extras = getIntent().getExtras();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bnv=findViewById(R.id.bottom_navig);
        bnv.setOnNavigationItemSelectedListener(navListner);
        String uid;

          Intent intent = getIntent();

        id = intent.getStringExtra("UID");

        Toast.makeText(MainActivity.this,"this:"+id,Toast.LENGTH_SHORT).show();

       getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new FragmentHome()).commit();

    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListner=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment currFragment=null;
                    switch(menuItem.getItemId()){
                        case R.id.nav_home: {

                            currFragment = new FragmentHome();
                            Bundle args = new Bundle();
                            args.putString("UID",id);
                            currFragment.setArguments(args);
                            break;

                        }
                        case R.id.nav_info:{
                            currFragment=new FragmentInfo();
                            break;}
                        case R.id.nav_people:
                        {currFragment=new FragmentProfile();
                            break;}

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,currFragment).commit();
                    return  true;
                }
            };
}
