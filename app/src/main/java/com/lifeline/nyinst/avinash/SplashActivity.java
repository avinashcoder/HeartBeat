package com.lifeline.nyinst.avinash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    public static String countryCodeFinal;
    public static String contactNumberFinal;
    public static String nameFinal;
    public static String dateOfBirthFinal;
    public static String addressFinal;
    public static double latitudeFinal;
    public static double longitudeFinal;
    public static String cityFinal;
    public static String stateFinal;
    public static String countryFinal;
    public static String bloodGroupFinal;
    public static String interestFinal;
    public static String genderFinal;
    public static String userTypeFinal;

    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MobileVerificationActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
