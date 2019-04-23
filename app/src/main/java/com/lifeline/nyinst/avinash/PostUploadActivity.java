package com.lifeline.nyinst.avinash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lifeline.nyinst.avinash.SplashActivity.URL_POST;
import static com.lifeline.nyinst.avinash.SplashActivity.contactNumberFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.idFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;
import static com.lifeline.nyinst.avinash.SplashActivity.profilePicFinal;

public class PostUploadActivity extends AppCompatActivity {
    CircleImageView userImage;
    ImageView postUploadImage,camSelectImage;
    EditText postUploadText;
    TextView postUpload;
    ProgressBar progressBarMain,progressBarTextView;
    Boolean isImageSelected=false;

    private final int IMG_REQUEST = 1;
    Bitmap bitmap;
    SharedPreferences sharedPreferences;
    String userId,contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);
        userImage=findViewById(R.id.post_upload_user_profile_pic);
        postUploadImage=findViewById(R.id.post_upload_image_post);
        camSelectImage=findViewById(R.id.post_upload_cam_image_select);
        postUploadText=findViewById(R.id.post_upload__edit_text_post);
        postUpload=findViewById(R.id.post_upload_text_view);
        progressBarMain=findViewById(R.id.post_upload_progressBar);
        progressBarTextView=findViewById(R.id.post_upload_progressBar_textview);

        retriveSharedPreferences();

        camSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        postUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        postUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(postUploadText.getText().equals(""))||isImageSelected) {
                    progressBarMain.setVisibility(View.VISIBLE);
                    progressBarTextView.setVisibility(View.VISIBLE);
                    postUpload.setVisibility(View.GONE);
                    uploadPost();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Plese write something or select image for post",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void retriveSharedPreferences(){
        sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(profilePicFinal)){
            String photo = sharedPreferences.getString(profilePicFinal, "photo");
            assert photo != null;
            if(!photo.equals("photo"))
            {
                byte[] b = Base64.decode(photo, Base64.DEFAULT);
                InputStream is = new ByteArrayInputStream(b);
                bitmap = BitmapFactory.decodeStream(is);
                userImage.setImageBitmap(bitmap);
            }
        }
        if(sharedPreferences.contains(contactNumberFinal)){
            contactNumber=sharedPreferences.getString(contactNumberFinal,"Contact");
        }
        if(sharedPreferences.contains(idFinal)){
            userId=sharedPreferences.getString(idFinal,"id");
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
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                postUploadImage.setImageBitmap(bitmap);
                isImageSelected=true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private void uploadPost(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST+"uploadpost.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                if(response.contains("success")){
                    progressBarMain.setVisibility(View.GONE);
                    progressBarTextView.setVisibility(View.GONE);
                    postUpload.setVisibility(View.VISIBLE);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable to Upload your Post, Plese try again", Toast.LENGTH_LONG).show();
                    progressBarMain.setVisibility(View.GONE);
                    progressBarTextView.setVisibility(View.GONE);
                    postUpload.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostUploadActivity.this, error + "", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                String postDesc=postUploadText.getText().toString().trim();

                params.put("id",userId);
                params.put("post_desc",postDesc);
                if(isImageSelected) {
                    params.put("postimg", encodeTobase64(bitmap));
                }


                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public String encodeTobase64(Bitmap image) {
        Bitmap bitmap_image = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap_image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }
}
