package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static com.lifeline.nyinst.avinash.SplashActivity.URL_POST;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.countryCodeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;

public class SettingActivity extends AppCompatActivity {
    TextView settingSignOut, deactivateAccount,contactNumber;
    SharedPreferences sharedPreferences;
    String userContactNo,userCountryCode;
    LinearLayout contactNumberLayout;

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

                alert.setView(R.layout.change_contact_details);
                alert.setTitle("Change Contact Details");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

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
        editor.commit();
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
                    changeContactNumber();
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
                params.put("old_country_code",userCountryCode);
                params.put("old_contact_no", userContactNo);
                params.put("new_country_code",userCountryCode);
                params.put("new_contact_no", userContactNo);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
