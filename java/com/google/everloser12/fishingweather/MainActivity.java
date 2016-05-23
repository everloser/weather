package com.google.everloser12.fishingweather;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableWeightLayout;
import com.google.everloser12.fishingweather.constants.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kd.dynamic.calendar.generator.ImageGenerator;

public class MainActivity extends AppCompatActivity {

    public static void showActivity(Activity activity, String text)
    {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(Constants.INTENT_KEY,text);
        activity.startActivity(intent);
    }


    private ExpandableWeightLayout mExpandLayout;
    private Handler mHandler;
    private TextView local,temp1, windday1,day2,day3, textId1;
    private Calendar cal1,cal2,cal3;
    private ImageGenerator mImageGenerator;
    private Bitmap mGeneratedDateIcon1, mGeneratedDateIcon2, mGeneratedDateIcon3;
    private ImageView imageCal1, imageCal2, imageCal3, imageDay1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mExpandLayout = (ExpandableWeightLayout) findViewById(R.id.expandableLayout);

// запуск развертывания лейаута
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable,800);

// получение данных с погодой из интента
        Intent intent = getIntent();
        String text = intent.getStringExtra(Constants.INTENT_KEY);
        String[] array = text.split("\\|");



// обработка данных, изображение календаря для дат

        setCalendarData(array);

        imageCal1 = (ImageView) findViewById(R.id.imgCalendar1);
        imageCal2 = (ImageView) findViewById(R.id.imgCalendar2);
        imageCal3 = (ImageView) findViewById(R.id.imgCalendar3);

        mImageGenerator = new ImageGenerator(this);
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

        local = (TextView) findViewById(R.id.locationtext);
        local.setText(array[0]);
        temp1 = (TextView) findViewById(R.id.temp1_day1);
        temp1.setText(array[6] + "..." + array[5] + " \u2103");
        windday1 = (TextView) findViewById(R.id.wind_day1);
        windday1.setText(R.string.wind);
        String a = String.valueOf(windday1.getText());
        windday1.setText(array[3] + " " + a);
        textId1 = (TextView) findViewById(R.id.text_id_day1);
        String icn = "s"+array[7];
        int resID = this.getResources().getIdentifier(icn, "string", this.getPackageName());
        textId1.setText(resID);
        imageDay1 = (ImageView) findViewById(R.id.image_day1);
        icn = "i"+array[7];
        resID = this.getResources().getIdentifier(icn, "drawable", this.getPackageName());
        imageDay1.setImageResource(resID);





    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
       // mHandler.removeCallbacks(mRunnable);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            mExpandLayout.toggle();
        }
    };




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
}
