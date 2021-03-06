package com.lifeline.nyinst.avinash;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lifeline.nyinst.avinash.SplashActivity.URL_POST;
import static com.lifeline.nyinst.avinash.SplashActivity.addressFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.bloodGroupFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;
import static com.lifeline.nyinst.avinash.SplashActivity.nameFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.profilePicFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.userInterestFinal;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView bloodGroupImgView;
    TextView userName,address;
    SharedPreferences sharedPreferences;
    String name,addressvalue,contact,bloodGroup,interest;
    ImageView camSelectImage;
    CircleImageView profileImgView, homeUserPic;
    TextView tvTextPost;
    FloatingActionButton fabDonateBlood,fabAcceptBlood;
    FloatingActionMenu fabExpand;
    Bitmap bitmap;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBar;
    SimpleDraweeView imageSlider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.nav_drawer_user_name);
        address=headerView.findViewById(R.id.nav_drawer_user_address);
        profileImgView=headerView.findViewById(R.id.nav_drawer_user_profile_pic);
        bloodGroupImgView=headerView.findViewById(R.id.nav_drawer_user_blood_group);
        homeUserPic=findViewById(R.id.home_user_profile_pic);
        mSwipeRefreshLayout=findViewById(R.id.home_swife_refresh);
        progressBar=findViewById(R.id.home_progressbar);
        imageSlider=findViewById(R.id.home_image_slider);

        fabDonateBlood=findViewById(R.id.home_fab_donate_blood);
        fabAcceptBlood=findViewById(R.id.home_fab_accept_blood);
        fabExpand=findViewById(R.id.home_fab_expand);

        recyclerView=findViewById(R.id.home_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        navigationView.setNavigationItemSelectedListener(this);

        camSelectImage=findViewById(R.id.home_cam_image_select);
        tvTextPost=findViewById(R.id.home_edit_text_post);

        setUpToolBar();
        retriveSharedPreferenceData();
        setImageSlider();
        //getUploadedPost();

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        //mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                if(mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
                // TODO Fetching data from server
                getUploadedPost();
            }
        });


        fabAcceptBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interest.equals("Acceptor")){
                    showMessageDialog("Already a Acceptor","You are already a Acceptor. No need to update");
                }
                else {
                    showConfirmationDialog("Acceptor","Are you sure to become a Acceptor ?");
                }
            }
        });

        fabDonateBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interest.equals("Donar")){
                    showMessageDialog("Already a Donar","You are already a Donar. No need to update");
                }
                else {
                    showConfirmationDialog("Donar","Are you sure to become a Donar ?");
                }

            }
        });

        camSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostUploadActivity();
            }
        });
        tvTextPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostUploadActivity();
            }
        });
    }

    private  void setUpToolBar(){
        toolbar = findViewById(R.id.home_toolbar);
        drawerLayout=findViewById(R.id.nav_drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toolbar.setTitle("LIFELINE");
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    private void retriveSharedPreferenceData(){
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(nameFinal)){
            name=sharedPreferences.getString(nameFinal,"User Name");
            userName.setText(name);
        }
        if(sharedPreferences.contains(addressFinal)){
            addressvalue=sharedPreferences.getString(addressFinal,"Address");
            address.setText(addressvalue);
        }
        if(sharedPreferences.contains(contactNumberFinal)){
            contact=sharedPreferences.getString(contactNumberFinal,"Contact");
        }
        if(sharedPreferences.contains(bloodGroupFinal)){
            bloodGroup=sharedPreferences.getString(bloodGroupFinal,"Blood Group");
            setBloodGroupImage();
        }
        if(sharedPreferences.contains(userInterestFinal)){
            interest=sharedPreferences.getString(userInterestFinal,"interest");
            Toast.makeText(getApplicationContext(),interest,Toast.LENGTH_LONG).show();
        }
        if(sharedPreferences.contains(profilePicFinal)){
            String photo = sharedPreferences.getString(profilePicFinal, "photo");
            assert photo != null;
            if(!photo.equals("photo"))
            {
                byte[] b = Base64.decode(photo, Base64.DEFAULT);
                InputStream is = new ByteArrayInputStream(b);
                bitmap = BitmapFactory.decodeStream(is);
                profileImgView.setImageBitmap(bitmap);
                homeUserPic.setImageBitmap(bitmap);
            }
        }
    }

    private void setBloodGroupImage(){
        switch (bloodGroup){
            case "A+":
                bloodGroupImgView.setImageResource(R.drawable.a_positive);
                break;

            case "B+":
                bloodGroupImgView.setImageResource(R.drawable.b_positive);
                break;

            case "AB+":
                bloodGroupImgView.setImageResource(R.drawable.ab_positive);
                break;

            case "O+":
                bloodGroupImgView.setImageResource(R.drawable.o_positive);
                break;

            case "A-":
                bloodGroupImgView.setImageResource(R.drawable.a_negative);
                break;

            case "B-":
                bloodGroupImgView.setImageResource(R.drawable.b_negative);
                break;

            case "AB-":
                bloodGroupImgView.setImageResource(R.drawable.ab_negative);
                break;

            case "O-":
                bloodGroupImgView.setImageResource(R.drawable.o_negative);
                break;

            default:
                bloodGroupImgView.setImageResource(R.drawable.a_positive);
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.overflow_menu_donate_blood:
                i = new Intent(this,DonarListActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;

            case R.id.overflow_menu_find_blood: {
                i = new Intent(this,AcceptorListActivity.class);
                this.startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()) {

            case R.id.nav_menu_donate_blood: {
                i = new Intent(this,DonarListActivity.class);
                this.startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
            case R.id.nav_menu_find_blood: {
                i = new Intent(this,AcceptorListActivity.class);
                this.startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
            case R.id.nav_menu_setting: {
                i = new Intent(this,SettingActivity.class);
                this.startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void openPostUploadActivity(){
        Intent i=new Intent(HomeActivity.this,PostUploadActivity.class);
        startActivity(i);
    }

    private void getUploadedPost(){
        final List<PostViewModalClass> postViewModalClasses = new ArrayList<>();
        mSwipeRefreshLayout.setRefreshing(true);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST+"getuploadedpost.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    mSwipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONArray items=new JSONArray(response);
                    for(int i=0;i<items.length();i++){
                        JSONObject jsonObject = items.getJSONObject(i);
                        int id=Integer.parseInt(jsonObject.getString("id"));
                        String profileUrl=jsonObject.getString("profile_img");
                        String name=jsonObject.getString("name");
                        String city=jsonObject.getString("city");
                        String day=jsonObject.getString("uploaded_time");
                        String postDesc=jsonObject.getString("post_description");
                        String postUrl=jsonObject.getString("post_url");
                        int totalLike=Integer.parseInt(jsonObject.getString("total_likes"));
                        int totalComment=Integer.parseInt(jsonObject.getString("total_comments"));

                        postViewModalClasses.add(new PostViewModalClass(id,profileUrl,name,city,day,postDesc,postUrl,totalLike,totalComment));
                    }
                    //postViewModalClasses.add(new PostViewModalClass(15,"https://upload.wikimedia.org/wikipedia/commons/1/16/HDRI_Sample_Scene_Balls_%28JPEG-HDR%29.jpg","Avinash Kumar Singh","Chennai","1 day ago","Nice bro , Well Done","https://upload.wikimedia.org/wikipedia/commons/1/16/HDRI_Sample_Scene_Balls_%28JPEG-HDR%29.jpg",52,10));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PostViewAdapter adapter = new PostViewAdapter(postViewModalClasses);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this,""+error,Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getUploadedPost();
    }

    private void setImageSlider(){
        Uri uri=Uri.parse(URL_POST + "slider/3.jpeg");
        imageSlider.setImageURI(uri);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Uri uri=Uri.parse(URL_POST + "slider/1.jpeg");
                imageSlider.setImageURI(uri);

            }
        }, 8000);

    }

    private void updateInterest(final String uInterest){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST+"interestupdate.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if(response.contains("success")) {
                    sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(userInterestFinal, uInterest);
                    editor.commit();
                    interest=uInterest;
                    Toast.makeText(HomeActivity.this, "You are now a "+interest , Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(HomeActivity.this, "Failed, Plese try later"+response , Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error + "", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("interest",uInterest);
                params.put("mob_no",contact);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //To show some Message in the dialog Box
    private void showMessageDialog(String dialogTitle,String dialogMessage){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage(dialogMessage)
                .setTitle(dialogTitle)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void showConfirmationDialog(final String confirmInterest, String confirmMessage){

        String message=confirmMessage;
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateInterest(confirmInterest);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
