package com.google.everloser12.fishingweather.prework;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by al-ev on 20.05.2016.
 */
public class GoFish {


    public static double[] goFish(String[] array){

        double[] data = new double[28];
        double first = 0;
        double second = 0;
        double third = 0;

  // set Moon
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = df.parse(array[1]);
        } catch (ParseException e) { }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        MoonPhase moonPhase = new MoonPhase(cal);
        moonPhase.getPhase();
        int moonDay = moonPhase.getMoonDayMy();

        Log.d("Moi", "1moonDay = " + moonDay);
        data[3] = setMoon(moonDay);
        first += setMoon(moonDay);

        try {
            date = df.parse(array[8]);
        } catch (ParseException e) { }
        cal.setTime(date);
        moonPhase.updateCal(cal);
        moonPhase.getPhase();
        moonDay = moonPhase.getMoonDayMy();
        Log.d("Moi", "2moonDay = " + moonDay);
        data[4] = setMoon(moonDay);
        second += setMoon(moonDay);

        try {
            date = df.parse(array[15]);
        } catch (ParseException e) { }
        cal.setTime(date);
        moonPhase.updateCal(cal);
        moonPhase.getPhase();
        moonDay = moonPhase.getMoonDayMy();
        Log.d("Moi", "3moonDay = " + moonDay);
        data[5] = setMoon(moonDay);
        third += setMoon(moonDay);
        
   // Set wind napramak
        
        int wet = Integer.valueOf(array[2]);
        Log.d("Moi", "1napramak = " + wet);
        data[6] = setWindS(wet);
        first += setWindS(wet);
        
        wet = Integer.valueOf(array[9]);
        Log.d("Moi", "2napramak = " + wet);
        data[7] = setWindS(wet);
        second += setWindS(wet);
        
        wet = Integer.valueOf(array[16]);
        Log.d("Moi", "3napramak = " + wet);
        data[8] = setWindS(wet);
        third += setWindS(wet);
        
   // Set wind delta
        
        wet = (int) Math.sqrt((Integer.valueOf(array[2])-Integer.valueOf(array[9]))*(Integer.valueOf(array[2])-Integer.valueOf(array[9])));
        Log.d("Moi", "1wind delta = " + wet);
        data[9] = setWindDelta(wet);
        first += setWindDelta(wet);
        
        wet = (int) Math.sqrt((Integer.valueOf(array[9])-Integer.valueOf(array[16]))*(Integer.valueOf(array[9])-Integer.valueOf(array[16])));
        Log.d("Moi", "2wind delta = " + wet);
        data[10] = setWindDelta(wet);
        second += setWindDelta(wet);

        data[11] = 4;
        third += 4;
        
   // Set wind moc
        String wetr = array[3];
        wetr = wetr.replace(",",".");
        double wm = Double.valueOf(wetr);
        Log.d("Moi", "1wind moc = " + wm);
        data[12] = setWindMoc(wm);
        first += setWindMoc(wm);

        wetr = array[10];
        wetr = wetr.replace(",", ".");
        wm = Double.valueOf(wetr);
        Log.d("Moi", "2wind moc = " + wm);
        data[13] = setWindMoc(wm);
        second += setWindMoc(wm);

        wetr = array[17];
        wetr = wetr.replace(",", ".");
        wm = Double.valueOf(wetr);
        Log.d("Moi", "3wind moc = " + wm);
        data[14] = setWindMoc(wm);
        third += setWindMoc(wm);
        
   // Set pressure
        
        wm = Double.valueOf(array[4]);
        Log.d("Moi", "1pressure = " + wm);
        data[15] = setPress(wm);
        first += setPress(wm);
        
        wm = Double.valueOf(array[11]);
        Log.d("Moi", "2pressure = " + wm);
        data[16] = setPress(wm);
        second += setPress(wm);
        
        wm = Double.valueOf(array[18]);
        Log.d("Moi", "3pressure = " + wm);
        data[17] = setPress(wm);
        third += setPress(wm);
        
        
        
   // Set pressure delta

        wm = Math.sqrt((Double.valueOf(array[4]) - Double.valueOf(array[11])) * (Double.valueOf(array[4]) - Double.valueOf(array[11])));
        Log.d("Moi", "1pressure delta = " + wm);
        data[18] = setPressDelta(wm);
        first += setPressDelta(wm);
        
        wm = Math.sqrt((Double.valueOf(array[11]) - Double.valueOf(array[18])) * (Double.valueOf(array[11]) - Double.valueOf(array[18])));
        Log.d("Moi", "2pressure delta = " + wm);
        data[19] = setPressDelta(wm);
        second += setPressDelta(wm);
        
        wm = Math.sqrt((Double.valueOf(array[18]) - Double.valueOf(array[22])) * (Double.valueOf(array[18]) - Double.valueOf(array[22])));
        Log.d("Moi", "3pressure delta = " + wm);
        data[20] = setPressDelta(wm);
        third += setPressDelta(wm);
    
   // Set temp delta 
        
        wet = Integer.valueOf(array[5])-Integer.valueOf(array[6]);
        Log.d("Moi", "1temp delta = " + wet);
        data[21] = setTempDelta(wet);
        first += setTempDelta(wet);
        
        wet = Integer.valueOf(array[12])-Integer.valueOf(array[13]);
        Log.d("Moi", "2temp delta = " + wet);
        data[22] = setTempDelta(wet);
        second += setTempDelta(wet);
        
        wet = Integer.valueOf(array[19])-Integer.valueOf(array[20]);
        Log.d("Moi", "3temp delta = " + wet);
        data[23] = setTempDelta(wet);
        third += setTempDelta(wet);
        
    // Set conditions
        
        wet = Integer.valueOf(array[7]);
        Log.d("Moi", "1conditions = " + wet);
        data[24] = setCond(wet);
        first += setCond(wet);
        
        wet = Integer.valueOf(array[14]);
        Log.d("Moi", "2conditions = " + wet);
        data[25] = setCond(wet);
        second += setCond(wet);
        
        wet = Integer.valueOf(array[21]);
        Log.d("Moi", "3conditions = " + wet);
        data[26] = setCond(wet);
        third += setCond(wet);

        first -= 11;
        second -= 11;
        third -= 11;

        first = first/8;
        second = second/8;
        third = third/8;
        
        data[27] = 0;
        data[0] = first;
        data[1] = second;
        data[2] = third;

        Log.d("Moi", "data = " + Arrays.toString(data));
        return data;
    }

    private static int setMoon(int md)
    {
        int f = 0;
        if (md == 1 || md == 2 || md == 28 || md == 29)
            f =1;
        else if (md >= 12 && md <= 16 || md == 27)
            f =2;
        else if (md == 26)
            f =5;
        else if (md >= 23 && md <= 25 || md == 11 || md == 17)
            f =8;
        else if (md == 3 || md == 9 || md == 10 || md == 18)
            f =10;
        else if (md >= 20 && md <= 22)
            f =12;
        else if (md >= 4 && md <= 8 || md == 19)
            f =14;
        Log.d("Moi", "moonscore = " + f);
        return f;
    }
    
    private static int setWindS(int wet)
    
    {
    	int w = 0;
    	if (wet >= 51 && wet <= 110)
    		w = 2;
    	else if (wet >= 31 && wet <= 50 || wet >= 111 && wet <= 140)
    		w = 3;
    	else if (wet >= 0 && wet <= 30 || wet >= 141 && wet <= 159 || wet >= 316 && wet <= 360)
    		w = 4;
    	else if (wet >= 160 && wet <= 210 || wet >= 301 && wet <= 315)
    		w = 5;
    	else if (wet >= 211 && wet <= 250 || wet >= 291 && wet <= 300)
    		w = 6;
    	else if (wet >= 251 && wet <= 290)
    		w = 7;

        Log.d("Moi", "napramak_score = " + w);
    	return w;
    }
    
private static int setWindDelta(int wet)
    
    {
    	int w = 0;
    	
    	if (wet > 130)
    		w = 1;
    	else if (wet > 110 && wet <= 130)
    		w = 2;
    	else if (wet > 90 && wet <= 110)
    		w = 3;
    	else if (wet > 60 && wet <= 90)
    		w = 4;
    	else if (wet > 40 && wet <= 60)
    		w = 5;
    	else if (wet > 20 && wet <= 40)
    		w = 6;
    	else if (wet <= 20)
    		w = 7;

        Log.d("Moi", "wind delta_score = " + w);
    	return w;
    }

private static int setWindMoc(double wet)

{
	int w = 0;
	
	if (wet > 8)
		w = 1;
	else if (wet > 7 && wet <= 8)
		w = 2;
	else if (wet > 6 && wet <= 7)
		w = 3;
	else if (wet > 4 && wet <= 6)
		w = 4;
	else if (wet > 0 && wet <= 2)
		w = 5;
	else if (wet > 2 && wet <= 4)
		w = 7;
    Log.d("Moi", "wind moc_score = " + w);
	
	return w;
}

private static int setPress(double wet)

{
	int w = 0;
	
	if (wet <= 973)
		w = 2;
	else if (wet > 973 && wet <= 980)
		w = 3;
	else if (wet > 980 && wet <= 1000)
		w = 4;
	else if (wet > 1030)
		w = 5;
	else if (wet > 1000 && wet <= 1030)
		w = 6;
    Log.d("Moi", "pressure_score = " + w);
	
	return w;

}
private static int setPressDelta(double wet)

{
	int w = 0;
	
	if (wet > 10)
		w = 1;
	else if (wet > 7 && wet <= 10)
		w = 3;
	else if (wet > 5 && wet <= 7)
		w = 6;
	else if (wet > 4 && wet <= 5)
		w = 7;
	else if (wet > 3 && wet <= 4)
		w = 8;
	else if (wet > 2 && wet <= 3)
		w = 11;
	else if ( wet <= 2)
		w = 14;

    Log.d("Moi", "pressure delta_score = " + w);
	return w;

}

private static int setTempDelta(int wet)

{
	int w = 0;
	
	if (wet > 5)
		w = 6;
	else 
		w = 2;


    Log.d("Moi", "temp delta_score = " + w);
	return w;
}

private static int setCond(int wet)

{
	int w = 0;
	if (wet >= 900 && wet <= 906 || wet >= 762 && wet <= 781 || wet == 212 || wet == 511 || wet == 621 || wet == 622)
		w = 1;
	else if (wet == 314 || wet == 321 || wet == 503 || wet == 504 || wet >= 522 && wet <= 531 || wet == 616 || wet == 731)
		w = 2;
	else if (wet == 202 || wet == 302 || wet == 312 || wet == 313 || wet == 602 || wet == 611 || wet == 751 || wet == 761)
		w = 3;
	else if (wet == 211 || wet == 221 || wet == 230 || wet == 231 || wet == 232 || wet == 311 || wet == 502 || wet == 620)
		w = 4;
	else if (wet == 200 || wet == 201 || wet == 210 || wet == 601 || wet == 615 || wet == 701 || wet == 711 || wet == 721 || wet == 741)
		w = 5;
	else if (wet == 300 || wet == 301 || wet == 310 || wet == 520 || wet == 800)
		w = 6;
	else if (wet == 500 || wet == 501 || wet == 600 || wet >= 801 && wet <= 804)
		w = 7;

    Log.d("Moi", "conditions_score = " + w);
	return w;
}

}