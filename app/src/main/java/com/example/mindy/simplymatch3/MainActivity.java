package com.example.mindy.simplymatch3;

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
                case R.id.navigation_home:
                    setTitle("Collections");
                    FragmentOne fragment = new FragmentOne() ;
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction1.replace(R.id.fram,fragment,"FragmentName") ;
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("Add Outfits");
                    FragmentTwo fragment2 = new FragmentTwo() ;
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction2.replace(R.id.fram, fragment2,"FragmentName") ;
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_notifications:
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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //when app starts fragment one will be displayed
        setTitle("Collections");
        FragmentOne fragment = new FragmentOne() ;
        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction() ;
        fragmentTransaction1.replace(R.id.fram,fragment,"FragmentName") ;
        fragmentTransaction1.commit();
    }

}
