package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import static com.lifeline.nyinst.avinash.SplashActivity.addressFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.bloodGroupFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.cityFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.countryCodeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.countryFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.dobFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.genderFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.latitudeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.longitudeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;
import static com.lifeline.nyinst.avinash.SplashActivity.nameFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.profilePicFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.stateFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.userInterestFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.userTypeFinal;

public class RegistrationGenderActivity extends AppCompatActivity {

    ImageView genderMale,genderFemale,individual,bloodBank,doctor,genderMaleMark,genderFemaleMark,individualMark,bloodBankMark,doctorMark;
    Button regGenderNext;
    String gender="",userType="";
    SharedPreferences sharedPreferences;
    String URL_POST ="http://192.168.43.253/lifeline/lifelineregister.php";
    String countryCode,contactNumber,name,birthday,address,bloodGroup,city,state,country,latitude,longitude,interest,profilePicString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_gender);
        genderMale=findViewById(R.id.reg_gender_male);
        genderFemale=findViewById(R.id.reg_gender_female);
        individual=findViewById(R.id.reg_usertype_individual);
        bloodBank=findViewById(R.id.reg_usertype_bloodbank);
        doctor=findViewById(R.id.reg_usertype_doctor);
        genderMaleMark=findViewById(R.id.reg_gender_male_mark);
        genderFemaleMark=findViewById(R.id.reg_gender_female_mark);
        individualMark=findViewById(R.id.reg_usertype_individual_mark);
        bloodBankMark=findViewById(R.id.reg_usertype_bloodbank_mark);
        doctorMark=findViewById(R.id.reg_usertype_doctor_mark);
        regGenderNext=findViewById(R.id.btn_reg_gender_next);

        regGenderNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender.equals(""))
                {
                    Toast.makeText(RegistrationGenderActivity.this,"Plese select your gender",Toast.LENGTH_SHORT).show();
                }
                else if(userType.equals(""))
                {
                    Toast.makeText(RegistrationGenderActivity.this,"Plese tell us your category",Toast.LENGTH_SHORT).show();
                }
                else {
                    sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(genderFinal,gender);
                    editor.putString(userTypeFinal,userType);
                    editor.commit();
                    retriveSharedPreferenceData();
                    registerUser();
                    Intent i = new Intent(RegistrationGenderActivity.this, HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        genderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderMaleMark.setImageResource(R.drawable.mark);
                genderFemaleMark.setImageResource(R.drawable.blank);
                gender="M";
            }
        });

        genderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderFemaleMark.setImageResource(R.drawable.mark);
                genderMaleMark.setImageResource(R.drawable.blank);
                gender="F";
            }
        });

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodBankMark.setImageResource(R.drawable.blank);
                individualMark.setImageResource(R.drawable.mark);
                doctorMark.setImageResource(R.drawable.blank);
                userType="Individual";
            }
        });

        bloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodBankMark.setImageResource(R.drawable.mark);
                individualMark.setImageResource(R.drawable.blank);
                doctorMark.setImageResource(R.drawable.blank);
                userType="Bloodbank";
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodBankMark.setImageResource(R.drawable.blank);
                individualMark.setImageResource(R.drawable.blank);
                doctorMark.setImageResource(R.drawable.mark);
                userType="Doctor";
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void registerUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationGenderActivity.this, error + "", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("country_code",countryCode);
                params.put("mob_no",contactNumber);
                params.put("name",name);
                params.put("birthday",birthday);
                params.put("bloodgroup",bloodGroup);
                params.put("gender",gender);
                params.put("user_category",userType);
                params.put("address",address);
                params.put("city",city);
                params.put("state",state);
                params.put("country",country);
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                params.put("interest",interest);
                params.put("profilepic",profilePicString);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void retriveSharedPreferenceData(){
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(countryCodeFinal)){
            countryCode=sharedPreferences.getString(countryCodeFinal,"countrycode");
        }
        if(sharedPreferences.contains(contactNumberFinal)){
            contactNumber=sharedPreferences.getString(contactNumberFinal,"Contact");
        }
        if(sharedPreferences.contains(nameFinal)){
            name=sharedPreferences.getString(nameFinal,"User Name");
        }
        if(sharedPreferences.contains(dobFinal)){
            birthday=sharedPreferences.getString(dobFinal,"Contact");
        }
        if(sharedPreferences.contains(addressFinal)){
            address=sharedPreferences.getString(addressFinal,"Address");
        }

        if(sharedPreferences.contains(bloodGroupFinal)){
            bloodGroup=sharedPreferences.getString(bloodGroupFinal,"Blood Group");
        }
        if(sharedPreferences.contains(userInterestFinal)){
            interest=sharedPreferences.getString(userInterestFinal,"interest");
        }
        if(sharedPreferences.contains(genderFinal)){
            gender=sharedPreferences.getString(genderFinal,"Gender");
        }
        if(sharedPreferences.contains(userTypeFinal)){
            userType=sharedPreferences.getString(userTypeFinal,"usertype");
        }
        if(sharedPreferences.contains(cityFinal)){
            city=sharedPreferences.getString(cityFinal,"city");
        }
        if(sharedPreferences.contains(stateFinal)){
            state=sharedPreferences.getString(stateFinal,"state");
        }
        if(sharedPreferences.contains(countryFinal)){
            country=sharedPreferences.getString(countryFinal,"country");
        }
        if(sharedPreferences.contains(latitudeFinal)){
            latitude=sharedPreferences.getString(latitudeFinal,"latitude");
        }
        if(sharedPreferences.contains(longitudeFinal)){
            longitude=sharedPreferences.getString(longitudeFinal,"longitude");
        }
        if(sharedPreferences.contains(profilePicFinal)){
            profilePicString=sharedPreferences.getString(profilePicFinal,"profilePic");
        }
    }
}
