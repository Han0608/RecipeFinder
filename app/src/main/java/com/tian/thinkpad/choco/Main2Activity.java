package com.tian.thinkpad.choco;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_index:
                    mTextMessage.setText(R.string.navigation_index);
                    return true;
                case R.id.navigation_sort:
                    mTextMessage.setText(R.string.navigation_sort);
                    return true;
                case R.id.navigation_setting:
                    mTextMessage.setText(R.string.navigation_setting);
                    return true;
                case R.id.navigation_login:
                    mTextMessage.setText(R.string.navigation_login);
                    return true;
            }
            return false;
        }
    };

    private Fragment index;
    private Fragment setting;
    private Fragment login;
    private Fragment sort;
    private Fragment[] fragments;
    private int lastfragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //fragment
        initFragment();


    }



    //initial fragment
    private void initFragment()
    {
        index = new index();
        setting = new setting();
        login = new login();
        sort=new sort();
        fragments = new Fragment[]{index,sort,setting,login};
        lastfragment=0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview,index).show(index).commit();
        bottomNavigationView=findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }
    //choose fragment
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.navigation_index:
                {
                    if(lastfragment!=0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment=0;

                    }

                    return true;
                }
                case R.id.navigation_sort:
                {
                    if(lastfragment!=1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment=1;

                    }

                    return true;
                }
                case R.id.navigation_setting:
            {
                if(lastfragment!=2)
                {
                    switchFragment(lastfragment,2);
                    lastfragment=2;

                }

                return true;
            }
                case R.id.navigation_login:
                {
                    if(lastfragment!=3)
                    {
                        switchFragment(lastfragment,3);
                        lastfragment=3;
                    }
                    return true;
                }
            }
            return false;
        }
    };

  //switch fragment
  private void switchFragment(int lastfragment,int index)
  {
      FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
      transaction.hide(fragments[lastfragment]);//hide last Fragment
      if(fragments[index].isAdded()==false)
      {
          transaction.add(R.id.mainview,fragments[index]);
      }
      transaction.show(fragments[index]).commitAllowingStateLoss();
  }

}

