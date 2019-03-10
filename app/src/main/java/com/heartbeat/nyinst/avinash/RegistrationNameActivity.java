package com.heartbeat.nyinst.avinash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationNameActivity extends AppCompatActivity {

    Button bt_reg_name_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_name);
        bt_reg_name_next=(Button)findViewById(R.id.btn_reg_name_next);
        bt_reg_name_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(RegistrationNameActivity.this,RegistrationBloodgoupActivity.class);
                startActivity(i);
            }
        });
    }
}
