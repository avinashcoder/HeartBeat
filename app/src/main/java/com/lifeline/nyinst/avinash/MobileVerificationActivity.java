package com.lifeline.nyinst.avinash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MobileVerificationActivity extends AppCompatActivity {
    EditText et_country_code,et_mob_no,et_otp;
    Button bt_send_otp,bt_verify_otp;
    LinearLayout mob_linear_layout,otp_linear_layout;
    String OTP="";
    String contactNo,countryCode;
    String URL;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        et_country_code=findViewById(R.id.ed_country_code);
        et_mob_no=findViewById(R.id.ed_mob_no);
        bt_send_otp=findViewById(R.id.btn_send_otp);
        progressBar=findViewById(R.id.progressBar);

        et_otp=findViewById(R.id.ed_otp);
        bt_verify_otp=findViewById(R.id.btn_verify_otp);

        mob_linear_layout=findViewById(R.id.mobile_layout);
        otp_linear_layout=findViewById(R.id.otp_layout);

        otp_linear_layout.setVisibility(View.GONE);

        bt_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                bt_send_otp.setVisibility(View.INVISIBLE);

                countryCode=et_country_code.getText().toString();
                contactNo= et_mob_no.getText().toString();
                if(OTP.equals("")) {
                    OTP = GenerateOTP();
                }

                URL="http://control.msg91.com/api/sendotp.php?otp_length=4&authkey=266493ATTdZz7uWMZ5c8255e9&message=Verification code to get connect with LifeLine is "+OTP+"&sender=LIFELN&mobile="+countryCode+contactNo+"&otp="+OTP;
                //sendOtp();

                mob_linear_layout.setVisibility(View.GONE);
                otp_linear_layout.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        et_otp.setText(OTP);
                    }
                }, 3000);



            }
        });

        bt_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_otp.getText().toString().equals(OTP)) {
                    Toast.makeText(getApplicationContext(),"OTP Verified Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MobileVerificationActivity.this, RegistrationNameActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);


                }
                else{
                    Toast.makeText(getApplicationContext(),"Incorrect OTP, Plese enter correct OTP to Proceed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MobileVerificationActivity.super.onBackPressed();
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

    public String GenerateOTP()
    {
        Random random=new Random();
        String id = String.format("%04d", random.nextInt(10000));
        return id;
    }

    public void sendOtp(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(response.contains("success")){

                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            mob_linear_layout.setVisibility(View.GONE);
                            otp_linear_layout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            bt_send_otp.setVisibility(View.VISIBLE);
                        }
                    }, 2000);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Unable to send OTP. Plese try again",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    bt_send_otp.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),"Unable to send OTP. Plese check your network connection and try again",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    bt_send_otp.setVisibility(View.VISIBLE);
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(MobileVerificationActivity.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
