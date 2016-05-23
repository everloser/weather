package com.google.everloser12.fishingweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.everloser12.fishingweather.constants.Constants;
import com.google.everloser12.fishingweather.prework.BusProvider;
import com.google.everloser12.fishingweather.prework.LocationDataReceived;
import com.google.everloser12.fishingweather.prework.WeatherRequest;
import com.google.everloser12.fishingweather.prework.WorkWithWeatherData;
import com.google.everloser12.fishingweather.rest.CallBack;
import com.google.everloser12.fishingweather.rest.ServiceBroker;
import com.squareup.otto.Subscribe;


public class SplashActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private ImageView popl;
    private TextView load;
    private static GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double lat, lon;
    private final double DEFAULT_LAT = 51.482730;
    private final double DEFAULT_LON = -0.007691;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        popl = (ImageView) findViewById(R.id.splashView);
        load = (TextView) findViewById(R.id.splashText);
        popl.setBackgroundResource(R.drawable.pop_anim);
        AnimationDrawable frameAnimation = (AnimationDrawable) popl.getBackground();

        frameAnimation.start();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("Moi", "mGoogleApiClient builded");
        }


    }


    @Override
    public void onConnected(Bundle bundle) {

        Log.d("Moi", "onConnected");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
                if (mLastLocation != null) {
                    Log.d("Moi", "mLastLocation != null");
                    lat = mLastLocation.getLatitude();
                    lon = mLastLocation.getLongitude();
                } else {
                    Log.d("Moi", "mLastLocation = null");
                }
                BusProvider.getInstance().post(produceLocation());
                mGoogleApiClient.disconnect();
                Log.d("Moi", "mGoogleApiClient.disconnect");

            }
        }, 1300);

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.d("Moi", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, R.string.connect_warning, Toast.LENGTH_LONG).show();
        Log.d("Moi", "onConnectionFailed");
        BusProvider.getInstance().post(produceLocation());

    }

    @Override
    protected void onStart() {
        Log.d("Moi", "onStart");
        mGoogleApiClient.connect();
        BusProvider.getInstance().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("Moi", "onStop");
        if (mGoogleApiClient.isConnected())
        mGoogleApiClient.disconnect();
        BusProvider.getInstance().unregister(this);
        super.onStop();
    }

    public LocationDataReceived produceLocation() {

        Log.d("Moi", "LocationDataReceived method starts");
        if (mLastLocation != null)
        {
                        return new LocationDataReceived(lat, lon);
        }

        else
        {

            SharedPreferences sharedPreferences = MyApplication.getInstance().
                    getSharedPreferences("file", Context.MODE_PRIVATE);
            String loca =  sharedPreferences.getString(Constants.SHARED_LOC, null);
            if (!TextUtils.isEmpty(loca))
            {
                String[] l = loca.split("\\|");
                lat = Double.valueOf(l[0]);
                lon = Double.valueOf(l[1]);

            }
            else
            {
                lat = DEFAULT_LAT;
                lon = DEFAULT_LON;
            }

        }
        return new LocationDataReceived(lat, lon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onLocationReceived(LocationDataReceived event) {

        long time= System.currentTimeMillis();
        SharedPreferences sharedPreferences = MyApplication.getInstance().
                getSharedPreferences("file", Context.MODE_PRIVATE);
        String timeLast =  sharedPreferences.getString(Constants.SHARED_TIME, null);
        if (!TextUtils.isEmpty(timeLast) && (time - Long.valueOf(timeLast)) < 10800000)
        {
            FirstActivity.showActivity(SplashActivity.this, sharedPreferences.getString(Constants.SHARED_DATA1, null));
            finish();
        }

        else
        {
            new MyWeatherTask().execute(event.lat, event.lon);
        }
    }


    private class MyWeatherTask extends AsyncTask<Double, Void, Void>
    {
      protected String data1 = "";

        @Override
        protected Void doInBackground(Double... params) {

            Log.d("Moi", "doInBackground");

            WeatherRequest weatherRequest = new WeatherRequest(params[0], params[1]);
            Log.d("Moi", "weatherRequest");

            ServiceBroker.getInstance().getWeather(weatherRequest, new CallBack() {
                @Override
                public void response(boolean isError) {

                    SharedPreferences sharedPreferences = MyApplication.getInstance().
                            getSharedPreferences("file", Context.MODE_PRIVATE);
                    if (!isError) {

                        String timeLast = String.valueOf(System.currentTimeMillis());
                        data1 = WorkWithWeatherData.generateWeatherString(ServiceBroker.getInstance().getRoot());
                        String loca = WorkWithWeatherData.dataLocation(ServiceBroker.getInstance().getRoot());
                        sharedPreferences.edit().putString(Constants.SHARED_DATA1, data1)
                                .putString(Constants.SHARED_LOC, loca)
                                .putString(Constants.SHARED_TIME, timeLast)
                                .putString(Constants.SHARED_ERROR, "0")
                                .apply();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load.setText(R.string.splashtextdone);
                                FirstActivity.showActivity(SplashActivity.this, data1);
                                finish();
                            }
                        });
//
//
                    } else {
                        //Toast.makeText(SplashActivity.this, R.string.connect_warning, Toast.LENGTH_LONG).show();
                        sharedPreferences.edit().putString(Constants.SHARED_ERROR, "1").apply();
                        String sd = sharedPreferences.getString(Constants.SHARED_DATA1, null);
                        if (!TextUtils.isEmpty(sd)) {
                            data1 = sd;
                        } else {
                            data1 = Constants.DEF_WEATHER_DATA;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load.setText(R.string.splashtextdone);
                                FirstActivity.showActivity(SplashActivity.this, data1);
                                finish();
                            }
                        });
                    }
                }
            });

            return null;
        }


    }

}
