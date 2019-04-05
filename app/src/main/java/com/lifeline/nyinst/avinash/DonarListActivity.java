package com.lifeline.nyinst.avinash;


import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

public class DonarListActivity extends AppCompatActivity {

    FloatingActionButton fabDonateBlood,fabAcceptBlood;
    FloatingActionMenu fabExpand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_list);

        fabExpand=findViewById(R.id.donar_list_fab_expand);
        fabDonateBlood=findViewById(R.id.donar_list_fab_donate_blood);
        fabAcceptBlood=findViewById(R.id.donar_list_fab_accept_blood);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
