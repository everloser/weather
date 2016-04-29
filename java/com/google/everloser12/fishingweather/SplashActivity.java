package com.google.everloser12.fishingweather;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import com.google.everloser12.fishingweather.prework.WeatherRequest;
import com.google.everloser12.fishingweather.rest.CallBack;
import com.google.everloser12.fishingweather.rest.ServiceBroker;

public class SplashActivity extends Activity implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private ImageView popl;
    private TextView load;
    private static GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    double lat, lon;


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
        }


    }


    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null)
        {

            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
//            Toast.makeText(this, lat + " & " + lon, Toast.LENGTH_LONG).show();
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            WeatherRequest weatherRequest = new WeatherRequest(lat,lon);
            ServiceBroker.getInstance().getWeather(weatherRequest, new CallBack() {
                @Override
                public void response(boolean isError) {
                    if (!isError) {

                        int t = (int) (ServiceBroker.getInstance().getRoot().getList().get(0).getMain().getTemp() - 273.15);
//                        Toast.makeText(SplashActivity.this, ServiceBroker.getInstance().getRoot()
//                                .getCity().getName() +": "+ t + " degrees", Toast.LENGTH_LONG).show();

                        load.setText(ServiceBroker.getInstance().getRoot()
                                .getCity().getName() + ": " + t + " degrees");
                    }
                    else
                        Toast.makeText(SplashActivity.this, "EEERRRR", Toast.LENGTH_LONG).show();
                }
            });
        }
        Log.d("Moi", "test in func ");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Internet connection needed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}
