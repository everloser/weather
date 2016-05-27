package com.google.everloser12.fishingweather.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.everloser12.fishingweather.R;
import com.google.everloser12.fishingweather.constants.Constants;

/**
 * Created by al-ev on 10.05.2016.
 */
public class MyDialogs extends DialogFragment {

    public static MyDialogs newInstance(int title) {
        MyDialogs frag = new MyDialogs();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        TextView titleText;
        TextView mainText;

        switch (title)
        {
            case Constants.DIALOG_CONNECT:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.my_dialog, null);
                builder.setView(view)
                        // Add action buttons
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                return builder.create();

            }
            case Constants.DIALOG_CONNECT_F:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.my_dialog, null);
                titleText = (TextView) view.findViewById(R.id.textView3);
                mainText = (TextView) view.findViewById(R.id.dialog_text);
                titleText.setText(R.string.toolbartitlemap);
                mainText.setText(R.string.mapdialog);


                builder.setView(view)
                        // Add action buttons
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                return builder.create();
            }
            case Constants.DIALOG_CONNECT_MY:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.my_dialog, null);
                titleText = (TextView) view.findViewById(R.id.textView3);
                mainText = (TextView) view.findViewById(R.id.dialog_text);
                titleText.setText(R.string.toolbartitleplac);
                mainText.setText(R.string.myplacedialogtext);


                builder.setView(view)
                        // Add action buttons
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                return builder.create();
            }
            default: return null;
        }
    }
}
