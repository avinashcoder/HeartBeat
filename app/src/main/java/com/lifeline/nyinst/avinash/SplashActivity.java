package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    static final String myPreferences="LifeLineData";
    public static final String countryCodeFinal="countryCodeKey";
    public static final String contactNumberFinal="contactNumberKey";
    public static final String profilePicFinal="profilePicKey";
    public static final String nameFinal="nameKey";
    public static final String dobFinal="dobKey";
    public static final String addressFinal="addressKey";
    public static final String bloodGroupFinal="bloodGroupKey";
    public static final String userInterestFinal="userInterestKey";
    public static final String genderFinal="genderKey";
    public static final String userTypeFinal="userTypeKey";
    public static final String cityFinal="cityKey";
    public static final String stateFinal="stateKey";
    public static final String countryFinal="countryFinal";
    public static final String latitudeFinal="latitudeKey";
    public static final String longitudeFinal="longitudeKey";
    public static String URL_POST ="http://192.168.0.126/lifeline/";

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                checkLogin();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkLogin(){
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(contactNumberFinal))
        {
            if(sharedPreferences.contains(userTypeFinal))
            {
                Intent mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            else
            {
                Intent mainIntent = new Intent(SplashActivity.this,RegistrationNameActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }
        else{
            Intent mainIntent = new Intent(SplashActivity.this,MobileVerificationActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }
}
