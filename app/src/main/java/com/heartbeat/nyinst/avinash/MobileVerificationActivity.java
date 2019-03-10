package com.heartbeat.nyinst.avinash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MobileVerificationActivity extends AppCompatActivity {
    EditText et_mob_no,et_otp;
    Button bt_send_otp,bt_verify_otp;
    LinearLayout mob_linear_layout,otp_linear_layout;
    String OTP="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        et_mob_no=(EditText)findViewById(R.id.ed_mob_no);
        bt_send_otp=(Button)findViewById(R.id.btn_send_otp);

        et_otp=(EditText)findViewById(R.id.ed_otp);
        bt_verify_otp=(Button)findViewById(R.id.btn_verify_otp);

        mob_linear_layout=(LinearLayout)findViewById(R.id.mobile_layout);
        otp_linear_layout=(LinearLayout)findViewById(R.id.otp_layout);

        otp_linear_layout.setVisibility(View.GONE);

        bt_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mob_linear_layout.setVisibility(View.GONE);
                otp_linear_layout.setVisibility(View.VISIBLE);
                if(OTP.equals("")) {
                    OTP = GenerateOTP();
                }
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {Toast.makeText(getApplicationContext(),"OTP Automatically Detected",Toast.LENGTH_SHORT).show();
                        et_otp.setText(OTP);
                    }
                }, 5000);
            }
        });

        bt_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_otp.getText().toString().equals(OTP)) {
                    Intent intent = new Intent(MobileVerificationActivity.this, RegistrationNameActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Incorrect OTP, Plese enter correct OTP to Proceed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public String GenerateOTP()
    {
        Random random=new Random();
        String id = String.format("%04d", random.nextInt(10000));
        return id;
    }
}
