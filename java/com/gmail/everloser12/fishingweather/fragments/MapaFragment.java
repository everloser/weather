package com.gmail.everloser12.fishingweather.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.everloser12.fishingweather.MyApplication;
import com.gmail.everloser12.fishingweather.constants.Constants;
import com.gmail.everloser12.fishingweather.dialogs.MyDialogs;
import com.gmail.everloser12.fishingweather.dialogs.MyProgressDialog;
import com.gmail.everloser12.fishingweather.prework.WeatherRequest;
import com.gmail.everloser12.fishingweather.prework.WorkWithWeatherData;
import com.gmail.everloser12.fishingweather.rest.CallBack;
import com.gmail.everloser12.fishingweather.rest.ServiceBroker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.gmail.everloser12.fishingweather.R;

/**

 */
public class MapaFragment extends BaseFragment {


    MapView mapView;
    GoogleMap map;
    SharedPreferences sharedPreferences;
    Handler mHandler;
    MyProgressDialog progressDialog;
    private static final String KEY_NAME = "keymap";
    private double lat, lon;
    private final double DEFAULT_LAT = 51.482730;
    private final double DEFAULT_LON = -0.007691;
    private ActivityListener toolbarListener;
    private static final String TAG = MapaFragment.class.getSimpleName();

    public MapaFragment() {
        // Required empty public constructor
    }



    public static MapaFragment createInstance(FragmentManager manager, String name)
    {

        MapaFragment fragment = (MapaFragment) manager.findFragmentByTag(MapaFragment.TAG);
        if (fragment == null)

        {
            fragment = new MapaFragment();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_NAME, name);
            fragment.setArguments(bundle);
        }


        return fragment;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);

        sharedPreferences = MyApplication.getInstance().
                getSharedPreferences("file", Context.MODE_PRIVATE);

        Bundle bundle = getArguments();
        String latlonMy;
        if(bundle != null)
        {
            latlonMy = bundle.getString(KEY_NAME);
            if (!TextUtils.isEmpty(latlonMy))
            {
                String[] l = latlonMy.split("\\|");
                lat = Double.valueOf(l[0]);
                lon = Double.valueOf(l[1]);

            }
            else
            {
                lat = DEFAULT_LAT;
                lon = DEFAULT_LON;
            }
        }
        else
        {
            lat = DEFAULT_LAT;
            lon = DEFAULT_LON;
        }

        String first = sharedPreferences.getString(Constants.SHARED_FIRST, null);
        if (first == null)
        {
            DialogFragment dialog = MyDialogs.newInstance(Constants.DIALOG_CONNECT_F);
            dialog.show(getChildFragmentManager(), "fdiag");
            sharedPreferences.edit().putString(Constants.SHARED_FIRST, "not first").apply();
        }

        mHandler = new Handler();
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setMyLocationButtonEnabled(false);
                //map.setMyLocationEnabled(true);

                // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                MapsInitializer.initialize(MapaFragment.this.getActivity());

                // Updates the location and zoom of the MapView

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 10);
                map.animateCamera(cameraUpdate);
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        progressDialog = new MyProgressDialog(MapaFragment.this.getActivity());
                        progressDialog.show();
                        new MyWeatherTask2().execute(latLng.latitude,latLng.longitude);
                    }
                });

            }
        });


        //map = mapView.getMap();


        return v;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (toolbarListener != null)
        {
            toolbarListener.setTitle(R.string.toolbartitlemap);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityListener)
        {
            toolbarListener = (ActivityListener)context;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        toolbarListener = null;
        super.onDetach();
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_two, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_main:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWeatherTask2 extends AsyncTask<Double, Void, String>
    {

       //private String dataF = "";
        @Override
        protected String doInBackground(Double... params) {

            Log.d("Moi", "doInBackground");


            WeatherRequest weatherRequest = new WeatherRequest(params[0], params[1]);
            Log.d("Moi", "weatherRequest");

            ServiceBroker.getInstance().getWeather(weatherRequest, new CallBack() {
                @Override
                public void response(boolean isError) {


                    if (!isError) {

                        String timeLast = String.valueOf(System.currentTimeMillis());
                        final String dataF = WorkWithWeatherData.generateWeatherString(ServiceBroker.getInstance().getRoot());
                        String loca = WorkWithWeatherData.dataLocation(ServiceBroker.getInstance().getRoot());
                        sharedPreferences.edit().putString(Constants.SHARED_DATA1, dataF)
                                .putString(Constants.SHARED_LOC, loca)
                                .putString(Constants.SHARED_TIME, timeLast)
                                .putString(Constants.SHARED_ERROR, "0")
                                .apply();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                toolbarListener.switchFrafment(FragmentOne.createInstance(dataF),
                                        false, true, FragmentAnim.NONE);
                            }
                        }, 1000);

                        //
//
                    } else {

                        sharedPreferences.edit().putString(Constants.SHARED_ERROR, "1").apply();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String sd = sharedPreferences.getString(Constants.SHARED_DATA1, null);
                                progressDialog.dismiss();

                                toolbarListener.switchFrafment(FragmentOne.createInstance(sd),
                                        false, true, FragmentAnim.NONE);
                            }
                        }, 1000);


                    }
                }
            });

            return null;
        }


    }

}
