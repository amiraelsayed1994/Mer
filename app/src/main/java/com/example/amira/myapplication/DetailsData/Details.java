package com.example.amira.myapplication.DetailsData;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.amira.myapplication.R;

public class Details extends ActionBarActivity {
DetailFragment de;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = new Bundle();
        bundle=getIntent().getExtras();
        DetailFragment fragments =new DetailFragment();
        fragments.setArguments(bundle);
            if (savedInstanceState == null) {
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, fragment)
                        .commit();
            }
        }




    }

