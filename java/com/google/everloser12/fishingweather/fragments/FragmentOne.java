package com.google.everloser12.fishingweather.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.google.everloser12.fishingweather.MyApplication;
import com.google.everloser12.fishingweather.R;
import com.google.everloser12.fishingweather.constants.Constants;
import com.google.everloser12.fishingweather.dialogs.MyDialogs;
import com.google.everloser12.fishingweather.dialogs.MyFishDialog;
import com.google.everloser12.fishingweather.prework.GoFish;
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
    private TextView textId2, textId3;
    private Calendar cal1,cal2,cal3;
    private ImageGenerator mImageGenerator;
    private Bitmap mGeneratedDateIcon1, mGeneratedDateIcon2, mGeneratedDateIcon3;
    private ImageView imageCal1, imageCal2, imageCal3, imageDay1, imageDay2, imageDay3;
    private String data;
    private RatingBar bar1, bar2, bar3;
    private CardView cv1, cv2,cv3;
    SharedPreferences sharedPreferences;


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

                String loca =  sharedPreferences.getString(Constants.SHARED_LOC, null);
                toolbarListener.switchFrafment(MapaFragment.createInstance(getChildFragmentManager(),
                        loca), true, false, FragmentAnim.RIGHT_TO_LEFT);
                break;
            case R.id.action_my:
                toolbarListener.switchFrafment(PlacesFragment.createInstance(getChildFragmentManager(),
                        "", "", ""), true, false, FragmentAnim.RIGHT_TO_LEFT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
