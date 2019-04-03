package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {



    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SharedPreferences sharedPreferences;

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
        sharedPreferences=getSharedPreferences("lifelinePref", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("contactKey")){
            if(sharedPreferences.contains("userType")){
                Intent mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else {
                Intent mainIntent = new Intent(SplashActivity.this,RegistrationNameActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
        else{
            Intent mainIntent = new Intent(SplashActivity.this,MobileVerificationActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
