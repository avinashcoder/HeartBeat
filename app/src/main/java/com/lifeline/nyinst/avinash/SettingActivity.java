package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;

public class SettingActivity extends AppCompatActivity {
    TextView settingSignOut;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingSignOut=findViewById(R.id.setting_sign_out);
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        settingSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Are you sure to sign out ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent i=new Intent(SettingActivity.this,MobileVerificationActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
