package com.lifeline.nyinst.avinash;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class DonarListActivity extends AppCompatActivity {

    FloatingActionButton fabDonateBlood,fabAcceptBlood,fabExpand;
    int fabExpandCheck=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_list);
        fabDonateBlood=findViewById(R.id.donar_list_fab_donate_blood);
        fabAcceptBlood=findViewById(R.id.donar_list_fab_accept_blood);
        fabExpand=findViewById(R.id.donar_list_fab_expand);
        fabDonateBlood.hide();
        fabAcceptBlood.hide();

        fabExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabExpandCheck==0) {
                    fabExpandCheck = 1;
                    fabDonateBlood.show();
                    fabAcceptBlood.show();
                    rotateFabForward();
                }
                else {
                    fabExpandCheck = 0;
                    fabDonateBlood.hide();
                    fabAcceptBlood.hide();
                    rotateFabBackward();
                }
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void rotateFabForward() {
        ViewCompat.animate(fabExpand)
                .rotation(135.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }

    public void rotateFabBackward() {
        ViewCompat.animate(fabExpand)
                .rotation(0.0F)
                .withLayer()
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(10.0F))
                .start();
    }
}
