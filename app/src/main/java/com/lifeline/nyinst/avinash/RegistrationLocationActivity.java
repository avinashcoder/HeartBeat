package com.lifeline.nyinst.avinash;

//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.util.List;
//import java.util.Locale;
//
//public class RegistrationLocationActivity extends AppCompatActivity implements LocationListener {
//
//    protected static final String TAG = "LocationOnOff";
//
//    private GoogleApiClient googleApiClient;
//    final static int REQUEST_LOCATION = 199;
//
//    TextView textview_reg_location;
//    ImageView imgLocationRefresh;
//    Button btnRegLocationNext;
//    Animation rotateAnimation;
//
//    Geocoder geocoder;
//    List<Address> addresses;
//    Double latitude, longitude;
//    String address, area, city, country, postalcode;
//
//    protected LocationManager locationManager;
//    protected LocationListener locationListener;
//    protected Context context;
//    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
//    FusedLocationProviderClient fusedLocationClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration_location);
//        textview_reg_location = findViewById(R.id.tv_reg_location);
//        imgLocationRefresh=findViewById(R.id.img_view_refresh_location);
//        btnRegLocationNext=findViewById(R.id.btn_reg_location_next);
//        geocoder = new Geocoder(this, Locale.getDefault());
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        enableLoc();
//        checkLocationPermission();
//
//        btnRegLocationNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(RegistrationLocationActivity.this,RegistrationBloodgoupActivity.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
//
//        imgLocationRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rotateAnimation();
//                enableLoc();
//                checkLocationPermission();
//            }
//        });
//
//
//    }
//
//    private void rotateAnimation() {
//        rotateAnimation= AnimationUtils.loadAnimation(this,R.anim.rotate);
//        imgLocationRefresh.startAnimation(rotateAnimation);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    fusedLocationClient.getLastLocation()
//                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                                @Override
//                                public void onSuccess(Location location) {
//                                    // Got last known location. In some rare situations this can be null.
//                                    if (location != null) {
//                                        // Logic to handle location object
//                                        latitude = location.getLatitude();
//                                        longitude = location.getLongitude();
//                                        getAddress();
//                                    }
//                                }
//                            });
//                } else {
//                    textview_reg_location.setText("Permission denied by you,\n CLICK HERE TO SELECT ADDRESS");
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
//    }
//
//    private void enableLoc() {
//
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(RegistrationLocationActivity.this)
//                    .addApi(LocationServices.API)
//                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
//                        @Override
//                        public void onConnected(Bundle bundle) {
//
//                        }
//
//                        @Override
//                        public void onConnectionSuspended(int i) {
//                            googleApiClient.connect();
//                        }
//                    })
//                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
//                        @Override
//                        public void onConnectionFailed(ConnectionResult connectionResult) {
//
//                            textview_reg_location.setText("Location Permission Denied!\n Click here to choose your location");
//                        }
//                    }).build();
//            googleApiClient.connect();
//
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(5 * 1000);
//            locationRequest.setFastestInterval(1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(locationRequest);
//
//            builder.setAlwaysShow(true);
//
//            PendingResult<LocationSettingsResult> result =
//                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//                @Override
//                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            try {
//                                // Show the dialog by calling startResolutionForResult(),
//                                // and check the result in onActivityResult().
//                                status.startResolutionForResult(RegistrationLocationActivity.this, REQUEST_LOCATION);
//
//
//                            } catch (IntentSender.SendIntentException e) {
//                                // Ignore the error.
//                            }
//                            break;
//                    }
//                }
//            });
//        }
//    }
//
//
//
//    private void getAddress()
//    {
//        try
//        {
//            addresses = geocoder.getFromLocation(latitude,longitude,1);
//
//            address=addresses.get(0).getAddressLine(0);
//            area=addresses.get(0).getLocality();
//            city=addresses.get(0).getAdminArea();
//            country=addresses.get(0).getCountryName();
//            postalcode=addresses.get(0).getPostalCode();
//
//            textview_reg_location.setText(address);
//
//        }catch (Exception e){}
//    }
//
//    private void checkLocationPermission(){
//        if (ContextCompat.checkSelfPermission(RegistrationLocationActivity.this,
//                Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationLocationActivity.this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
//
//                ActivityCompat.requestPermissions(RegistrationLocationActivity.this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//                fusedLocationClient.getLastLocation()
//                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//                                // Got last known location. In some rare situations this can be null.
//                                if (location != null) {
//                                    // Logic to handle location object
//                                    latitude = location.getLatitude();
//                                    longitude = location.getLongitude();
//                                    getAddress();
//                                }
//                            }
//                        });
//                getAddress();
//            } else {
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(RegistrationLocationActivity.this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//                fusedLocationClient.getLastLocation()
//                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                            @Override
//                            public void onSuccess(Location location) {
//                                // Got last known location. In some rare situations this can be null.
//                                if (location != null) {
//                                    // Logic to handle location object
//                                    latitude = location.getLatitude();
//                                    longitude = location.getLongitude();
//                                    getAddress();
//                                }
//                            }
//                        });
//                getAddress();
//            }
//        }
//        else {
//            // Permission has already been granted
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            // Got last known location. In some rare situations this can be null.
//                            if (location != null) {
//                                // Logic to handle location object
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                                getAddress();
//                            }
//                        }
//                    });
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        latitude=location.getLatitude();
//        longitude=location.getLongitude();
//        getAddress();
//    }
//
//
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//    }
//}


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.lifeline.nyinst.avinash.SplashActivity.addressFinal;
import static com.lifeline.nyinst.avinash.SplashActivity.myPreferences;

public class RegistrationLocationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationLocationActivity.class.getSimpleName();

    TextView txtLocationResult;
    Button btnRegLocationNext;
    ProgressBar locationProgressBar;

    Geocoder geocoder;
    List<Address> addresses;
    Double latitude, longitude;
    String address="", area, city, country, postalcode;

    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_location);
        txtLocationResult=findViewById(R.id.tv_reg_location);
        btnRegLocationNext=findViewById(R.id.btn_reg_location_next);
        locationProgressBar=findViewById(R.id.location_progressBar);

       geocoder = new Geocoder(this, Locale.getDefault());


        btnRegLocationNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!address.equals("")) {
                    sharedPreferences=getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(addressFinal,address);
                    editor.commit();
                    Intent i = new Intent(RegistrationLocationActivity.this, RegistrationBloodgoupActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        // initialize the necessary libraries

        init();

        startLocationButtonClick();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);
    }

    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }

    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            latitude=mCurrentLocation.getLatitude();
            longitude=mCurrentLocation.getLongitude();

            try
            {
                addresses = geocoder.getFromLocation(latitude,longitude,1);

                address=addresses.get(0).getAddressLine(0);
                area=addresses.get(0).getLocality();
                city=addresses.get(0).getAdminArea();
                country=addresses.get(0).getCountryName();
                postalcode=addresses.get(0).getPostalCode();

                txtLocationResult.setText(address);

                btnRegLocationNext.setVisibility(View.VISIBLE);
                locationProgressBar.setVisibility(View.GONE);

                txtLocationResult.setAlpha(0);
                txtLocationResult.animate().alpha(1).setDuration(300);

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Unable to get location, Plese add the address from map"+e,Toast.LENGTH_LONG).show();
            }

//            txtLocationResult.setText(
//                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
//                            "Lng: " + mCurrentLocation.getLongitude()
//            );
//
//            // giving a blink animation on TextView
//            txtLocationResult.setAlpha(0);
//            txtLocationResult.animate().alpha(1).setDuration(300);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(RegistrationLocationActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(RegistrationLocationActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showLastKnownLocation() {
        if (mCurrentLocation != null) {
            Toast.makeText(getApplicationContext(), "Lat: " + mCurrentLocation.getLatitude()
                    + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}



