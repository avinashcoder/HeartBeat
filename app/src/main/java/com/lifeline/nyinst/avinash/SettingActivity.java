package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import static com.lifeline.nyinst.avinash.SplashActivity.URL_POST;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.countryCodeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;

public class SettingActivity extends AppCompatActivity {
    TextView settingSignOut, deactivateAccount,contactNumber;
    SharedPreferences sharedPreferences;
    String userContactNo,userCountryCode,newContactNo,newCountryCode;
    LinearLayout contactNumberLayout;
    EditText edCountryCode,edContactNumber,edOTP;
    String OTP,URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingSignOut = findViewById(R.id.setting_sign_out);
        deactivateAccount = findViewById(R.id.setting_deactivate_account);
        contactNumber = findViewById(R.id.setting_contact_number);
        contactNumberLayout=findViewById(R.id.setting_contact_number_layout);

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        userContactNo = sharedPreferences.getString(contactNumberFinal, "");
        userCountryCode=sharedPreferences.getString(countryCodeFinal,"");
        contactNumber.setText(userCountryCode+" "+userContactNo);

        contactNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.change_contact_details,null);
                edCountryCode=view.findViewById(R.id.change_contact_details_country_code);
                edContactNumber=view.findViewById(R.id.change_contact_details_contact_no);
                edOTP=view.findViewById(R.id.change_contact_details_otp);
                edCountryCode.setText(userCountryCode);
                edContactNumber.setText(userContactNo);
                alert.setView(view);
                alert.setTitle("Update Contact Details");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        newContactNo=edContactNumber.getText().toString().trim();
                        newCountryCode=edCountryCode.getText().toString().trim();
                        if(!(newCountryCode.equals("")||newContactNo.equals("")))
                        {
                            OTP=generateOTP();
                            URL = "http://control.msg91.com/api/sendotp.php?otp_length=4&authkey=266493ATTdZz7uWMZ5c8255e9&message=Verification code to get connect with LifeLine is " + OTP + "&sender=LIFELN&mobile=" + newCountryCode + newContactNo + "&otp=" + OTP;
                            sendOtp();
                            otpValidate();
                        }
                        else{
                            Toast.makeText(SettingActivity.this,"Plese enter valid contact number and country code",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

        settingSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Are you sure to sign out ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeSharedPreferencesData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        deactivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("Are you sure to deactivate your account ?")
                        .setMessage("Deactivating of your account will delete all the details, post and photos belongs to you and cannot be recovered further")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteUserAccount();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void removeSharedPreferencesData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent i = new Intent(SettingActivity.this, MobileVerificationActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void deleteUserAccount() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST + "deleteuseraccount.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(SettingActivity.this, "Successfully Deleted your Account", Toast.LENGTH_LONG).show();
                    removeSharedPreferencesData();
                } else {
                    Toast.makeText(SettingActivity.this, "Sorry Unable to process your request...Try Later", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingActivity.this, "Sorry Unable to process your request...Try Later", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("contact_no", userContactNo);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void changeContactNumber(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST + "updatecontactdetail.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(SettingActivity.this, "Successfully Changed Your Contact Number", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(contactNumberFinal,newContactNo);
                } else {
                    Toast.makeText(SettingActivity.this, "Sorry Unable to process your request...Try Later", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingActivity.this, "Sorry Unable to process your request...Try Later", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("old_country_code",userCountryCode);
                params.put("old_contact_no", userContactNo);
                params.put("new_country_code",newCountryCode);
                params.put("new_contact_no", newContactNo);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void otpValidate(){
        AlertDialog.Builder alert = new AlertDialog.Builder(SettingActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.change_contact_details,null);
        edCountryCode=view.findViewById(R.id.change_contact_details_country_code);
        edContactNumber=view.findViewById(R.id.change_contact_details_contact_no);
        edOTP=view.findViewById(R.id.change_contact_details_otp);
        edCountryCode.setText(userCountryCode);
        edContactNumber.setText(userContactNo);

        edCountryCode.setVisibility(View.GONE);
        edContactNumber.setVisibility(View.GONE);
        edOTP.setVisibility(View.VISIBLE);
        alert.setView(view);
        alert.setTitle("Update Contact Details");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String inputOtp=edOTP.getText().toString().trim();
                if(OTP.equals(inputOtp)){
                    changeContactNumber();
                }
                else {
                    Toast.makeText(SettingActivity.this,"Incorrect OTP, Plese Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public String generateOTP()
    {
        Random random=new Random();
        String otp = String.format("%04d", random.nextInt(10000));
        return otp;
    }

    public void sendOtp(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(response.contains("success")){

                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {

                        }
                    }, 2000);

                }
                else{
                    Toast.makeText(getApplicationContext(),"Unable to send OTP. Plese try again",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Unable to send OTP. Plese check your network connection and try again",Toast.LENGTH_LONG).show();

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(SettingActivity.this);
        requestQueue.add(stringRequest);

    }

}
