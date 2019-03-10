package com.heartbeat.nyinst.avinash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static com.heartbeat.nyinst.avinash.R.drawable.circular_select_img;

public class RegistrationBloodgoupActivity extends AppCompatActivity {

    LinearLayout ll_a_positive,ll_b_positive,ll_ab_positive,ll_o_positive,ll_a_negative,ll_b_negative,ll_ab_negative,ll_o_negative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_bloodgoup);

        ll_a_positive=(LinearLayout)findViewById(R.id.ll_a_positive);
        ll_b_positive=(LinearLayout)findViewById(R.id.ll_b_positive);
        ll_ab_positive=(LinearLayout)findViewById(R.id.ll_ab_positive);
        ll_o_positive=(LinearLayout)findViewById(R.id.ll_o_positive);
        ll_a_negative=(LinearLayout)findViewById(R.id.ll_a_negative);
        ll_b_negative=(LinearLayout)findViewById(R.id.ll_b_negative);
        ll_ab_negative=(LinearLayout)findViewById(R.id.ll_ab_negative);
        ll_o_negative=(LinearLayout)findViewById(R.id.ll_o_negative);

        ll_a_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_b_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_ab_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_o_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_a_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_select_img);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_b_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_select_img);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_ab_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_select_img);
                ll_o_negative.setBackgroundResource(R.drawable.circular_white_border);
            }
        });

        ll_o_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_white_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_white_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_select_img);
            }
        });


    }
}
