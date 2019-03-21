package com.lifeline.nyinst.avinash;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegistrationBloodgoupActivity extends AppCompatActivity {

    LinearLayout ll_a_positive,ll_b_positive,ll_ab_positive,ll_o_positive,ll_a_negative,ll_b_negative,ll_ab_negative,ll_o_negative;
    Button bt_reg_bloodgroup_next;
    TextView bloodStatusDonar,bloodStatusAcceptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_bloodgoup);

        bt_reg_bloodgroup_next=findViewById(R.id.btn_reg_bloodgroup_next);

        ll_a_positive=findViewById(R.id.ll_a_positive);
        ll_b_positive=findViewById(R.id.ll_b_positive);
        ll_ab_positive=findViewById(R.id.ll_ab_positive);
        ll_o_positive=findViewById(R.id.ll_o_positive);
        ll_a_negative=findViewById(R.id.ll_a_negative);
        ll_b_negative=findViewById(R.id.ll_b_negative);
        ll_ab_negative=findViewById(R.id.ll_ab_negative);
        ll_o_negative=findViewById(R.id.ll_o_negative);
        bloodStatusDonar=findViewById(R.id.reg_blood_status_donar);
        bloodStatusAcceptor=findViewById(R.id.reg_blood_status_acceptor);

        bloodStatusAcceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodStatusAcceptor.setBackgroundResource(R.drawable.curved_rectangular_red_background);
                bloodStatusAcceptor.setTextColor(Color.WHITE);
                bloodStatusDonar.setBackgroundResource(R.drawable.curved_rectangular_red_border);
                bloodStatusDonar.setTextColor(Color.RED);
            }
        });

        bloodStatusDonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodStatusAcceptor.setBackgroundResource(R.drawable.curved_rectangular_red_border);
                bloodStatusAcceptor.setTextColor(Color.RED);
                bloodStatusDonar.setBackgroundResource(R.drawable.curved_rectangular_red_background);
                bloodStatusDonar.setTextColor(Color.WHITE);
            }
        });

        bt_reg_bloodgroup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegistrationBloodgoupActivity.this,RegistrationGenderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        ll_a_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_b_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_ab_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_o_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_select_img);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_a_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_select_img);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_b_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_select_img);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_ab_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_select_img);
                ll_o_negative.setBackgroundResource(R.drawable.circular_red_border);
            }
        });

        ll_o_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_a_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_positive.setBackgroundResource(R.drawable.circular_red_border);
                ll_a_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_b_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_ab_negative.setBackgroundResource(R.drawable.circular_red_border);
                ll_o_negative.setBackgroundResource(R.drawable.circular_select_img);
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}