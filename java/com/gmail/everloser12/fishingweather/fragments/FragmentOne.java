package com.gmail.everloser12.fishingweather.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gmail.everloser12.fishingweather.rest.CallBack;
import com.gmail.everloser12.fishingweather.MyApplication;
import com.gmail.everloser12.fishingweather.R;
import com.gmail.everloser12.fishingweather.constants.Constants;
import com.gmail.everloser12.fishingweather.dialogs.MyFishDialog;
import com.gmail.everloser12.fishingweather.dialogs.MyProgressDialog;
import com.gmail.everloser12.fishingweather.prework.GoFish;
import com.gmail.everloser12.fishingweather.prework.WeatherRequest;
import com.gmail.everloser12.fishingweather.prework.WorkWithWeatherData;
import com.gmail.everloser12.fishingweather.rest.ServiceBroker;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends BaseFragment {


    private static final String TAG = FragmentOne.class.getSimpleName();
    private static final String KEY_NAME = "key";
    private ActivityListener toolbarListener;
    private TextView local,temp1, temp2, temp3, windday1, windday2, windday3, textId1;
    private TextView textId2, textId3, addMy;
    private Calendar cal1,cal2,cal3;
    private ImageGenerator mImageGenerator;
    private Bitmap mGeneratedDateIcon1, mGeneratedDateIcon2, mGeneratedDateIcon3;
    private ImageView imageCal1, imageCal2, imageCal3, imageDay1, imageDay2, imageDay3;
    private ImageView addButt;
    private String data, l2, l3, l4;
    private RatingBar bar1, bar2, bar3;
    private CardView cv1, cv2,cv3;
    Handler mHandler;
    int check;
    SharedPreferences sharedPreferences;
    MyProgressDialog progressDialog;


    public static FragmentOne createInstance(String name)
    {

//        FragmentOne fragmentOne = (FragmentOne) manager.findFragmentByTag(FragmentOne.TAG);
//        if (fragmentOne == null)

     //   {
         FragmentOne   fragmentOne = new FragmentOne();
       // }

        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, name);
        fragmentOne.setArguments(bundle);

        return fragmentOne;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);

        Bundle bundle = getArguments();
        sharedPreferences = MyApplication.getInstance().
                getSharedPreferences("file", Context.MODE_PRIVATE);
        if(bundle != null)
        {
            data = bundle.getString(KEY_NAME);
            if(TextUtils.isEmpty(data))
            {
                String sd = sharedPreferences.getString(Constants.SHARED_DATA1, null);
                if (!TextUtils.isEmpty(sd)) {
                    data = sd;
                } else {
                    data = Constants.DEF_WEATHER_DATA;
                }

            }
        }
        else
        {
            String sd = sharedPreferences.getString(Constants.SHARED_DATA1, null);
            if (!TextUtils.isEmpty(sd)) {
                data = sd;
            } else {
                data = Constants.DEF_WEATHER_DATA;
            }
        }
        String[] array = data.split("\\|");


        setCalendarData(array);
        double[] fish = GoFish.goFish(array);
        final String toDialog = Arrays.toString(fish);

        imageCal1 = (ImageView) view.findViewById(R.id.imgCalendar1);
        imageCal2 = (ImageView) view.findViewById(R.id.imgCalendar2);
        imageCal3 = (ImageView) view.findViewById(R.id.imgCalendar3);

        mImageGenerator = new ImageGenerator(view.getContext());
        mImageGenerator.setIconSize(50, 80);
        mImageGenerator.setDateSize(44);
        mImageGenerator.setMonthSize(14);
        mImageGenerator.setDatePosition(70);
        mImageGenerator.setMonthPosition(22);
        mImageGenerator.setDateColor(Color.parseColor("#2962ff"));
        mImageGenerator.setMonthColor(Color.WHITE);

        mGeneratedDateIcon1 = mImageGenerator.generateDateImage(cal1, R.drawable.empty_calendar);
        mGeneratedDateIcon2 = mImageGenerator.generateDateImage(cal2, R.drawable.empty_calendar);
        mGeneratedDateIcon3 = mImageGenerator.generateDateImage(cal3, R.drawable.empty_calendar);

        imageCal1.setImageBitmap(mGeneratedDateIcon1);
        imageCal2.setImageBitmap(mGeneratedDateIcon2);
        imageCal3.setImageBitmap(mGeneratedDateIcon3);


        // устанавливаем полученные данные

        cv1 = (CardView) view.findViewById(R.id.cv);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFishDialog myFishDialog = MyFishDialog.newInstance(1, toDialog);
                myFishDialog.show(getChildFragmentManager(), "fishdial");
            }
        });
        cv2 = (CardView) view.findViewById(R.id.cv2);
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFishDialog myFishDialog = MyFishDialog.newInstance(2,toDialog);
                myFishDialog.show(getChildFragmentManager(), "fishdial2");
            }
        });

        cv3 = (CardView) view.findViewById(R.id.cv3);
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFishDialog myFishDialog = MyFishDialog.newInstance(3,toDialog);
                myFishDialog.show(getChildFragmentManager(), "fishdial3");
            }
        });

        local = (TextView) view.findViewById(R.id.locationtext);
        local.setText(array[0]);

        bar1 = (RatingBar) view.findViewById(R.id.ratingBar1);
        bar1.setRating((float) fish[0]);
        bar1.setIsIndicator(true);
        bar1.setFocusable(false);

        bar2 = (RatingBar) view.findViewById(R.id.ratingBar2);
        bar2.setRating((float) fish[1]);
        bar2.setIsIndicator(true);
        bar2.setFocusable(false);

        bar3 = (RatingBar) view.findViewById(R.id.ratingBar3);
        bar3.setRating((float) fish[2]);
        bar3.setIsIndicator(true);
        bar3.setFocusable(false);

        temp1 = (TextView) view.findViewById(R.id.temp1_day1);
        temp1.setText(array[6] + "..." + array[5] + " \u2103");

        temp2 = (TextView) view.findViewById(R.id.temp1_day2);
        temp2.setText(array[13] + "..." + array[12] + " \u2103");

        temp3 = (TextView) view.findViewById(R.id.temp1_day3);
        temp3.setText(array[20] + "..." + array[19] + " \u2103");

        windday1 = (TextView) view.findViewById(R.id.wind_day1);
        windday1.setText(R.string.wind);
        String a = String.valueOf(windday1.getText());
        windday1.setText(array[3] + " " + a);

        windday2 = (TextView) view.findViewById(R.id.wind_day2);
        windday2.setText(array[10] + " " + a);

        windday3 = (TextView) view.findViewById(R.id.wind_day3);
        windday3.setText(array[17] + " " + a);

        textId1 = (TextView) view.findViewById(R.id.text_id_day1);
        String icn = "s"+array[7];
        int resID = this.getResources().getIdentifier(icn, "string", view.getContext().getPackageName());
        textId1.setText(resID);

        textId2 = (TextView) view.findViewById(R.id.text_id_day2);
        icn = "s"+array[14];
        resID = this.getResources().getIdentifier(icn, "string", view.getContext().getPackageName());
        textId2.setText(resID);

        textId3 = (TextView) view.findViewById(R.id.text_id_day3);
        icn = "s"+array[21];
        resID = this.getResources().getIdentifier(icn, "string", view.getContext().getPackageName());
        textId3.setText(resID);

        imageDay1 = (ImageView) view.findViewById(R.id.image_day1);
        icn = "i"+array[7];
        resID = this.getResources().getIdentifier(icn, "drawable", view.getContext().getPackageName());
        imageDay1.setImageResource(resID);

        imageDay2 = (ImageView) view.findViewById(R.id.image_day2);
        icn = "i"+array[14];
        resID = this.getResources().getIdentifier(icn, "drawable", view.getContext().getPackageName());
        imageDay2.setImageResource(resID);

        imageDay3 = (ImageView) view.findViewById(R.id.image_day3);
        icn = "i"+array[21];
        resID = this.getResources().getIdentifier(icn, "drawable", view.getContext().getPackageName());
        imageDay3.setImageResource(resID);

        addButt = (ImageView) view.findViewById(R.id.imageButton);
        addMy = (TextView) view.findViewById(R.id.addto);
        final String l1 = sharedPreferences.getString(Constants.SHARED_LOC, null);
        l2 = sharedPreferences.getString(Constants.SHARED_LOC2, null);
        l3 = sharedPreferences.getString(Constants.SHARED_LOC3, null);
        l4 = sharedPreferences.getString(Constants.SHARED_LOC4, null);

        if(!TextUtils.isEmpty(sharedPreferences.getString(Constants.SHARED_DATA2, null))
                && !TextUtils.isEmpty(sharedPreferences.getString(Constants.SHARED_DATA3, null))
                && !TextUtils.isEmpty(sharedPreferences.getString(Constants.SHARED_DATA4, null))
                )
        {
            addMy.setVisibility(TextView.GONE);
            addButt.setVisibility(ImageView.GONE);
        }
        else if (!TextUtils.isEmpty(l1) && !TextUtils.isEmpty(l2) && l1.equals(l2)
                || !TextUtils.isEmpty(l1) && !TextUtils.isEmpty(l3) && l1.equals(l3)
                || !TextUtils.isEmpty(l1) && !TextUtils.isEmpty(l4) && l1.equals(l4)
                )
        {
            addMy.setVisibility(TextView.GONE);
            addButt.setVisibility(ImageView.GONE);
        }

        addButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sharedPreferences.getString(Constants.SHARED_DATA2, null)))
                {
                    sharedPreferences.edit().putString(Constants.SHARED_DATA2, data)
                            .putString(Constants.SHARED_LOC2, l1)
                            .apply();
                }
                else if (TextUtils.isEmpty(sharedPreferences.getString(Constants.SHARED_DATA3, null)))
                {
                    sharedPreferences.edit().putString(Constants.SHARED_DATA3, data)
                            .putString(Constants.SHARED_LOC3, l1)
                            .apply();
                }
                else if (TextUtils.isEmpty(sharedPreferences.getString(Constants.SHARED_DATA4, null)))
                {
                    sharedPreferences.edit().putString(Constants.SHARED_DATA4, data)
                            .putString(Constants.SHARED_LOC4, l1)
                            .apply();
                }
                addMy.setVisibility(TextView.GONE);
                addButt.setVisibility(ImageView.GONE);
            }
        });


//        fbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ((FragActivity)getActivity()).showFrag(FragmentTwo.getInstance(),true);
//                if (toolbarListener!= null)
//
//                {
//                    toolbarListener.switchFrafment(FragmentTwo.getInstance(),true, false);
//                }
//            }
//        });


        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (toolbarListener != null)
        {
            toolbarListener.setTitle(R.string.toolbartext);
            toolbarListener.showDialogg(Constants.DIALOG_CONNECT);
        }


    }

    @Override
    public void onAttach(Context context)
    {
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
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void setCalendarData(String[] array)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = df.parse(array[1]);
        } catch (ParseException e) { }
        cal1 = Calendar.getInstance();
        cal1.setTime(date);
        try {
            date = df.parse(array[8]);
        } catch (ParseException e) { }
        cal2 = Calendar.getInstance();
        cal2.setTime(date);
        try {
            date = df.parse(array[15]);
        } catch (ParseException e) { }
        cal3 = Calendar.getInstance();
        cal3.setTime(date);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_one, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_map:

            {
                String loca = sharedPreferences.getString(Constants.SHARED_LOC, null);
                toolbarListener.switchFrafment(MapaFragment.createInstance(getChildFragmentManager(),
                        loca), true, false, FragmentAnim.RIGHT_TO_LEFT);
                break;
            }
            case R.id.action_my:
            {
                mHandler = new Handler();
                long time = System.currentTimeMillis();
                String myTime = sharedPreferences.getString(Constants.SHARED_TIME_MY, null);
                if (!TextUtils.isEmpty(myTime) && (time - Long.valueOf(myTime)) < 10800000
                        || TextUtils.isEmpty(l2)&& TextUtils.isEmpty(l3) && TextUtils.isEmpty(l4))
                {
                    toolbarListener.switchFrafment(PlacesFragment.createInstance(getChildFragmentManager(),
                            "", "", ""), true, false, FragmentAnim.RIGHT_TO_LEFT);
                }
                else
                {
                    check = 0;
                    progressDialog = new MyProgressDialog(FragmentOne.this.getActivity());
                    progressDialog.show();
                    if (TextUtils.isEmpty(l2))
                        check++;
                    if (TextUtils.isEmpty(l3))
                        check++;
                    if (TextUtils.isEmpty(l4))
                        check++;

                    if (!TextUtils.isEmpty(l2))
                    {
                        String[] l = l2.split("\\|");
                        double lat = Double.valueOf(l[0]);
                        double lon = Double.valueOf(l[1]);
                        new MyWeatherTask3().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,lat, lon, 1.0);
                    }
                    if (!TextUtils.isEmpty(l3))
                    {
                        String[] l = l3.split("\\|");
                        double lat = Double.valueOf(l[0]);
                        double lon = Double.valueOf(l[1]);
                        new MyWeatherTask3().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,lat, lon, 2.0);
                    }
                    if (!TextUtils.isEmpty(l4))
                    {
                        String[] l = l4.split("\\|");
                        double lat = Double.valueOf(l[0]);
                        double lon = Double.valueOf(l[1]);
                        new MyWeatherTask3().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,lat, lon, 3.0);
                    }

                    // // TODO: 26.05.2016 запустить прогресс диалог, обновить данные, закрыть диалог, переключить фрагмент

                }

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWeatherTask3 extends AsyncTask<Double, Void, Void>
    {

        @Override
        protected Void doInBackground(Double... params) {
            WeatherRequest weatherRequest = new WeatherRequest(params[0], params[1]);
            double locNumber = params[2];
            final int locNum = (int)locNumber;
            ServiceBroker.getInstance().getWeather(weatherRequest, new CallBack() {
                @Override
                public void response(boolean isError) {


                    if (!isError) {

                        String timeLast = String.valueOf(System.currentTimeMillis());
                        final String dataF = WorkWithWeatherData.generateWeatherString(ServiceBroker.getInstance().getRoot());
                        switch (locNum)
                        {
                            case 1:
                            {
                                sharedPreferences.edit().putString(Constants.SHARED_DATA2, dataF).apply();
                                break;
                            }
                            case 2:
                            {
                                sharedPreferences.edit().putString(Constants.SHARED_DATA3, dataF).apply();
                                break;
                            }
                            case 3:
                            {
                                sharedPreferences.edit().putString(Constants.SHARED_DATA4, dataF).apply();
                                break;
                            }
                        }
                        sharedPreferences.edit().putString(Constants.SHARED_TIME_MY, timeLast)
                                .putString(Constants.SHARED_ERROR, "0")
                                .apply();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                check++;
                                if (check == 3)
                                {
                                    progressDialog.dismiss();
                                    toolbarListener.switchFrafment(PlacesFragment.createInstance(getChildFragmentManager(),
                                            "", "", ""), true, false, FragmentAnim.RIGHT_TO_LEFT);
                                }

                            }
                        });

                        //
//
                    } else {

                        sharedPreferences.edit().putString(Constants.SHARED_ERROR, "1").apply();

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                check++;
                                if (check == 3)
                                {
                                    progressDialog.dismiss();
                                    toolbarListener.switchFrafment(PlacesFragment.createInstance(getChildFragmentManager(),
                                            "", "", ""), true, false, FragmentAnim.RIGHT_TO_LEFT);
                                }
                            }
                        });


                    }
                }
            });

            return null;
        }
    }
}
