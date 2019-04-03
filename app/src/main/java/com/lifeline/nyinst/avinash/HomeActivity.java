package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.lifeline.nyinst.avinash.SplashActivity.addressFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.bloodGroupFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.dobFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.genderFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;
import static com.lifeline.nyinst.avinash.SplashActivity.nameFinal;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView profilepic,bloodGroup;
    TextView userName,address;
    SharedPreferences sharedPreferences;
    String name,addressvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.nav_drawer_user_name);
        address=headerView.findViewById(R.id.nav_drawer_user_address);

        setUpToolBar();
        retriveSharedPreferenceData();
        //updateUI();


    }

    private  void setUpToolBar(){
        toolbar = findViewById(R.id.home_toolbar);
        drawerLayout=findViewById(R.id.nav_drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toolbar.setTitle("LIFELINE");
        toolbar.setSubtitle("Get & Donate blood 24/7");
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    private void retriveSharedPreferenceData(){
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(nameFinal)){
//            userName.setText(sharedPreferences.getString(nameFinal,"User Name"));
//            address.setText(sharedPreferences.getString(addressFinal,"Address"));
            name=sharedPreferences.getString(nameFinal,"User Name");
            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
            userName.setText(name);
        }
        if(sharedPreferences.contains(addressFinal)){
            addressvalue=sharedPreferences.getString(addressFinal,"Address");
            Toast.makeText(getApplicationContext(),addressvalue,Toast.LENGTH_LONG).show();
            address.setText(addressvalue);
        }
        if(sharedPreferences.contains(dobFinal)){
            Toast.makeText(getApplicationContext(),"DOB="+sharedPreferences.getString(dobFinal,"DOB"),Toast.LENGTH_LONG).show();
        }
        if(sharedPreferences.contains(contactNumberFinal)){
            Toast.makeText(getApplicationContext(),"Contact="+sharedPreferences.getString(contactNumberFinal,"Contact"),Toast.LENGTH_LONG).show();
        }
        if(sharedPreferences.contains(bloodGroupFinal)){
            Toast.makeText(getApplicationContext(),"Blood Group="+sharedPreferences.getString(bloodGroupFinal,"Blood Group"),Toast.LENGTH_LONG).show();
        }
        if(sharedPreferences.contains(genderFinal)){
            Toast.makeText(getApplicationContext(),"Gender="+sharedPreferences.getString(genderFinal,"Gender"),Toast.LENGTH_LONG).show();
        }
    }


}
