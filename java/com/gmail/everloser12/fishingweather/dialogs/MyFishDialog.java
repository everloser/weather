package com.gmail.everloser12.fishingweather.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gmail.everloser12.fishingweather.R;

/**
 * Created by al-ev on 23.05.2016.
 */
public class MyFishDialog extends DialogFragment {

    public static MyFishDialog newInstance(int code, String data) {
        MyFishDialog frag = new MyFishDialog();
        Bundle args = new Bundle();
        args.putInt("code", code);
        args.putString("data", data);
        frag.setArguments(args);
        return frag;
    }

    TextView moon, windDir, windDelta, windSpeed, pressure, pressChange, tempChang, condition;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int code = getArguments().getInt("code");
        String data = getArguments().getString("data");
        String[] arr = data.split("\\,");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fish_dialog, null);
        moon = (TextView) view.findViewById(R.id.moon_score);
        windDir = (TextView) view.findViewById(R.id.wind_side_score);
        windDelta = (TextView) view.findViewById(R.id.wind_change_score);
        windSpeed = (TextView) view.findViewById(R.id.wind_speed_score);
        pressure = (TextView) view.findViewById(R.id.pressure_score);
        pressChange = (TextView) view.findViewById(R.id.pressure_changes_score);
        tempChang = (TextView) view.findViewById(R.id.temp_changes_score);
        condition = (TextView) view.findViewById(R.id.weather_cond_score);
        switch (code)
        {
            case 1:
            {
                moon.setText(arr[3]+"  /  14.0");
                windDir.setText(arr[6]+"  /  7.0");
                windDelta.setText(arr[9]+"  /  7.0");
                windSpeed.setText(arr[12]+"  /  7.0");
                pressure.setText(arr[15]+"  /  6.0");
                pressChange.setText(arr[18]+"  /  14.0");
                tempChang.setText(arr[21]+"  /  6.0");
                condition.setText(arr[24]+"  /  7.0");
                break;
            }
            case 2:
            {
                moon.setText(arr[4]+"  /  14.0");
                windDir.setText(arr[7]+"  /  7.0");
                windDelta.setText(arr[10]+"  /  7.0");
                windSpeed.setText(arr[13]+"  /  7.0");
                pressure.setText(arr[16]+"  /  6.0");
                pressChange.setText(arr[19]+"  /  14.0");
                tempChang.setText(arr[22]+"  /  6.0");
                condition.setText(arr[25]+"  /  7.0");
                break;
            }
            case 3:
            {
                moon.setText(arr[5]+"  /  14.0");
                windDir.setText(arr[8]+"  /  7.0");
                windDelta.setText(arr[11]+"  /  7.0");
                windSpeed.setText(arr[14]+"  /  7.0");
                pressure.setText(arr[17]+"  /  6.0");
                pressChange.setText(arr[20]+"  /  14.0");
                tempChang.setText(arr[23]+"  /  6.0");
                condition.setText(arr[26]+"  /  7.0");
                break;
            }
        }


        builder.setView(view)
                // Add action buttons
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }
}
