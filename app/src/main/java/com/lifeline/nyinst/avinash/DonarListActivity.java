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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
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

import org.json.JSONArray;
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
    ProgressBar progressBar;
    int distance=10;
    TextView tvSorry,tvDistanceView;
    SeekBar seekBar;
    ImageView imgBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_list);

        fabExpand=findViewById(R.id.donar_list_fab_expand);
        fabDonateBlood=findViewById(R.id.donar_list_fab_donate_blood);
        fabAcceptBlood=findViewById(R.id.donar_list_fab_accept_blood);

        seekBar=findViewById(R.id.donar_list_seekbar);
        seekBar.setProgress(distance);


        progressBar=findViewById(R.id.donar_list_progress_bar);
        tvSorry=findViewById(R.id.donar_list_sorry_message);
        tvDistanceView=findViewById(R.id.donar_list_distance_view);
        imgBackButton=findViewById(R.id.donar_list_back_btn);

        recyclerView=findViewById(R.id.donar_list_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        getDonarList();

        imgBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance=progress;
                tvDistanceView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvDistanceView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getDonarList();
                tvDistanceView.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void getDonarList(){
        progressBar.setVisibility(View.VISIBLE);
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        final String latitude=sharedPreferences.getString(latitudeFinal,"");
        final String longitude=sharedPreferences.getString(longitudeFinal,"");

        final List<DonarModelClass> donarModelClassList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST+"getdonaracceptorlist.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            int id=Integer.parseInt(jsonObject.getString("id"));
                            String profileUrl=jsonObject.getString("profile_url");
                            String name=jsonObject.getString("name");
                            String city=jsonObject.getString("city");
                            Double distance=jsonObject.getDouble("distance");
                            String contact_no=jsonObject.getString("contact_no");
                            String blood_group=jsonObject.getString("blood_group");
                            donarModelClassList.add(new DonarModelClass(id,profileUrl,name,city,blood_group,contact_no,distance));
                        }
                        DonarAdapter adapter = new DonarAdapter(donarModelClassList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else{
                        tvSorry.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("interest","Donar");
                params.put("distance",String.valueOf(distance));

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
