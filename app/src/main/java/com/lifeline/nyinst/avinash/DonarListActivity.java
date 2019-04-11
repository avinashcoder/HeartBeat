package com.lifeline.nyinst.avinash;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lifeline.nyinst.avinash.SplashActivity.URL_POST;
import static com.lifeline.nyinst.avinash.SplashActivity.latitudeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.longitudeFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;
import static com.lifeline.nyinst.avinash.SplashActivity.userInterestFinal;

public class DonarListActivity extends AppCompatActivity {

    FloatingActionButton fabDonateBlood,fabAcceptBlood;
    FloatingActionMenu fabExpand;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_list);

        fabExpand=findViewById(R.id.donar_list_fab_expand);
        fabDonateBlood=findViewById(R.id.donar_list_fab_donate_blood);
        fabAcceptBlood=findViewById(R.id.donar_list_fab_accept_blood);

        recyclerView=findViewById(R.id.donar_list_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final List<DonarModelClass> donarModelClassList = new ArrayList<>();
        donarModelClassList.add(new DonarModelClass(1,"https://upload.wikimedia.org/wikipedia/commons/1/16/HDRI_Sample_Scene_Balls_%28JPEG-HDR%29.jpg","Avinash Kumar Singh","Chennai","B+","9931778331",4.5f));
        donarModelClassList.add(new DonarModelClass(2,"https://upload.wikimedia.org/wikipedia/commons/1/16/HDRI_Sample_Scene_Balls_%28JPEG-HDR%29.jpg","Avinash Kumar Singh","Chennai","B+","9931778331",4.5f));
        donarModelClassList.add(new DonarModelClass(3,"https://upload.wikimedia.org/wikipedia/commons/1/16/HDRI_Sample_Scene_Balls_%28JPEG-HDR%29.jpg","Avinash Kumar Singh","Chennai","B+","9931778331",4.5f));
        DonarAdapter adapter = new DonarAdapter(donarModelClassList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void getDonarList(){
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        final String latitude=sharedPreferences.getString(latitudeFinal,"");
        final String longitude=sharedPreferences.getString(longitudeFinal,"");
        final String interest=sharedPreferences.getString(userInterestFinal,"");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST+"getdonaracceptorlist.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DonarListActivity.this, error + "", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("latitude",latitude);
                params.put("longitude",longitude);
                params.put("interest",interest);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
