package com.lifeline.nyinst.avinash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RegistrationGenderActivity extends AppCompatActivity {

    ImageView genderMale,genderFemale,individual,bloodBank,doctor,genderMaleMark,genderFemaleMark,individualMark,bloodBankMark,doctorMark;
    Button regGenderNext;
    String gender="",userType="";
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
                    SplashActivity.genderFinal=gender;
                    SplashActivity.userTypeFinal=userType;
                    Intent i = new Intent(RegistrationGenderActivity.this, HomeActivity.class);
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
                gender="Male";
            }
        });

        genderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderFemaleMark.setImageResource(R.drawable.mark);
                genderMaleMark.setImageResource(R.drawable.blank);
                gender="Female";
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
}
