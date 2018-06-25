package com.example.mindy.simplymatch3;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_clothing:
                    setTitle("Collections");
                    FragmentOne fragment = new FragmentOne() ; //set the title of the action bar
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction1.replace(R.id.fram,fragment,"FragmentName") ; //fram is id of framelayout in xml file
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_add:
                    setTitle("Add Outfits");
                    FragmentTwo fragment2 = new FragmentTwo() ;
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction2.replace(R.id.fram, fragment2,"FragmentName") ;
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_pieces:
                    setTitle("Clothing Pieces");
                    FragmentThree fragment3 = new FragmentThree() ;
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction3.replace(R.id.fram,fragment3,"FragmentName") ;
                    fragmentTransaction3.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fram, new FragmentOne()).commit() ;
    }

    private android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selectedFragment = null ;

                    switch(item.getItemId()) {
                        case R.id.navigation_clothing:
                            selectedFragment = new FragmentOne() ;
                            break;
                        case R.id.navigation_add:
                            selectedFragment = new FragmentTwo() ;
                            break;
                        case R.id.navigation_pieces:
                            selectedFragment = new FragmentThree() ;
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fram,selectedFragment).commit() ;

                    return true;
                }
            };
    }


