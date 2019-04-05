package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lifeline.nyinst.avinash.SplashActivity.addressFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.bloodGroupFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;
import static com.lifeline.nyinst.avinash.SplashActivity.nameFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.profilePicFinal;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView profileImgView,bloodGroupImgView;
    TextView userName,address;
    SharedPreferences sharedPreferences;
    String name,addressvalue,contact,bloodGroup;
    ImageView camSelectImage,imgPost;
    CircleImageView homeUserPic;
    Button btnPost;
    EditText editTextPost;
    String postText;
    ProgressBar progressBar;
    FloatingActionButton fabDonateBlood,fabAcceptBlood;
    FloatingActionMenu fabExpand;
    Bitmap bitmap;

    private final int IMG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.nav_drawer_user_name);
        address=headerView.findViewById(R.id.nav_drawer_user_address);
        profileImgView=headerView.findViewById(R.id.nav_drawer_user_profile_pic);
        bloodGroupImgView=headerView.findViewById(R.id.nav_drawer_user_blood_group);
        homeUserPic=findViewById(R.id.home_user_profile_pic);

        fabDonateBlood=findViewById(R.id.home_fab_donate_blood);
        fabAcceptBlood=findViewById(R.id.home_fab_accept_blood);
        fabExpand=findViewById(R.id.home_fab_expand);

        navigationView.setNavigationItemSelectedListener(this);

        camSelectImage=findViewById(R.id.home_cam_image_select);
        imgPost=findViewById(R.id.home_post_image);
        editTextPost=findViewById(R.id.home_edit_text_post);
        btnPost=findViewById(R.id.home_post_btn);
        progressBar=findViewById(R.id.home_post_upload_progressBar);

        setUpToolBar();
        retriveSharedPreferenceData();



        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        imgPost.setVisibility(View.INVISIBLE);
                        btnPost.setVisibility(View.INVISIBLE);
                        editTextPost.setText("");
                    }
                }, 3000);
            }
        });

        camSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        editTextPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postText=editTextPost.getText().toString().trim();
                if(postText.isEmpty()){
                    btnPost.setVisibility(View.GONE);
                }
                else{
                    btnPost.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void selectImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            imgPost.setImageURI(path);
            imgPost.setVisibility(View.VISIBLE);
            btnPost.setVisibility(View.VISIBLE);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.finish();
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
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }

        return true;
    }

}
