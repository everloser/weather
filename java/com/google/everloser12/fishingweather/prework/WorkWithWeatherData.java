package com.google.everloser12.fishingweather.prework;

import com.google.everloser12.fishingweather.model.Root;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by al-ev on 02.05.2016.
 */
public class WorkWithWeatherData
{

    private static String location;
    private static String date1, date2, date3;
    private static long dv;
    private static String press1, press2, press3, press4;
    private static String windS1, windS2, windS3;
    private static String windM1, windM2, windM3;
    private static String tempD1, tempD2, tempD3;
    private static String tempN1, tempN2, tempN3;
    private static String id1, id2, id3;
    private static String lat, lon;



    public static String generateWeatherString (Root root)
    {
        location = root.getCity().getName();
        dv = Long.valueOf(root.getList().get(0).getDt())*1000;
        date1 = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dv));
        windS1 = root.getList().get(0).getDeg().toString();
        press1 = root.getList().get(0).getPressure().toString();
        windM1 = String.format(Locale.ENGLISH,"%.1f", root.getList().get(0).getSpeed());
        tempD1 = String.format(Locale.ENGLISH,"%.0f",root.getList().get(0).getTemp().getDay() - 273.15);
        tempN1 = String.format(Locale.ENGLISH,"%.0f",root.getList().get(0).getTemp().getNight() - 273.15);
        id1 = root.getList().get(0).getWeather().get(0).getId().toString();
        dv = Long.valueOf(root.getList().get(1).getDt())*1000;
        date2 = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dv));
        windS2 = root.getList().get(1).getDeg().toString();
        press2 = root.getList().get(1).getPressure().toString();
        windM2 = String.format(Locale.ENGLISH,"%.1f", root.getList().get(1).getSpeed());
        tempD2 = String.format(Locale.ENGLISH,"%.0f", root.getList().get(1).getTemp().getDay() - 273.15);
        tempN2 = String.format(Locale.ENGLISH,"%.0f", root.getList().get(1).getTemp().getNight() - 273.15);
        id2 = root.getList().get(1).getWeather().get(0).getId().toString();
        dv = Long.valueOf(root.getList().get(2).getDt())*1000;
        date3 = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dv));
        windS3 = root.getList().get(2).getDeg().toString();
        press3 = root.getList().get(2).getPressure().toString();
        windM3 = String.format(Locale.ENGLISH,"%.1f", root.getList().get(2).getSpeed());
        tempD3 = String.format(Locale.ENGLISH,"%.0f", root.getList().get(2).getTemp().getDay() - 273.15);
        tempN3 = String.format(Locale.ENGLISH,"%.0f", root.getList().get(2).getTemp().getNight() - 273.15);
        id3 = root.getList().get(2).getWeather().get(0).getId().toString();
        press4 = root.getList().get(2).getPressure().toString();


        String string = location+"|"+date1+"|"+windS1+"|"+windM1+"|"+press1+"|"+tempD1+"|"+tempN1+"|"+id1
                +"|"+date2+"|"+windS2+"|"+windM2+"|"+press2+"|"+tempD2+"|"+tempN2+"|"+id2
                +"|"+date3+"|"+windS3+"|"+windM3+"|"+press3+"|"+tempD3+"|"+tempN3+"|"+id3+"|"+press4;




        return string;
    }

    public static String dataLocation(Root root)
    {
        lat = String.format(Locale.ENGLISH,"%.3f",root.getCity().getCoord().getLat());
        lon = String.format(Locale.ENGLISH,"%.3f",root.getCity().getCoord().getLon());

        String string = lat+"|"+lon;
        return string;
    }
}
