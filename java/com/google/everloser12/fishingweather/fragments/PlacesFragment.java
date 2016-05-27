package com.google.everloser12.fishingweather.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.everloser12.fishingweather.MyApplication;
import com.google.everloser12.fishingweather.R;
import com.google.everloser12.fishingweather.constants.Constants;
import com.google.everloser12.fishingweather.dialogs.MyDialogs;
import com.google.everloser12.fishingweather.prework.GoFish;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class PlacesFragment extends BaseFragment {

    private static final String ARG_PLACE1 = "param1";
    private static final String ARG_PLACE2 = "param2";
    private static final String ARG_PLACE3 = "param3";

    private static final String TAG = PlacesFragment.class.getSimpleName();
    private ActivityListener toolbarListener;

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String data2, data3, data4, mylatlon2, mylatlon3, mylatlon4;
    private Button testBut1, testBut2, testBut3;
    private LinearLayout myLayout1, myLayout2, myLayout3;
    SharedPreferences sharedPreferences;
    private ImageButton vis1, vis2, vis3, clos1, clos2, clos3;
    private static final String URL =
       "https://maps.googleapis.com/maps/api/staticmap?sensor=false&size=180x180&zoom=13&center=%s,%s";



    public PlacesFragment() {
        // Required empty public constructor
    }


    public static PlacesFragment createInstance(FragmentManager manager, String param1, String param2, String param3) {

        PlacesFragment fragment = (PlacesFragment) manager.findFragmentByTag(PlacesFragment.TAG);
        if (fragment == null)

        {
            fragment = new PlacesFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PLACE1, param1);
            args.putString(ARG_PLACE2, param2);
            args.putString(ARG_PLACE2, param3);
            fragment.setArguments(args);

        }

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PLACE1);
            mParam2 = getArguments().getString(ARG_PLACE2);
            mParam3 = getArguments().getString(ARG_PLACE3);
        }
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        sharedPreferences = MyApplication.getInstance().
                getSharedPreferences("file", Context.MODE_PRIVATE);
        String firstMy = sharedPreferences.getString(Constants.SHARED_FIRST_MY, null);
        if (firstMy == null)
        {
            DialogFragment dialog = MyDialogs.newInstance(Constants.DIALOG_CONNECT_MY);
            dialog.show(getChildFragmentManager(), "fdiagmy");
            sharedPreferences.edit().putString(Constants.SHARED_FIRST_MY, "not first").apply();
        }
        data2 = sharedPreferences.getString(Constants.SHARED_DATA2, null);
        mylatlon2 = sharedPreferences.getString(Constants.SHARED_LOC2, null);
        if (!TextUtils.isEmpty(data2)&& !TextUtils.isEmpty(mylatlon2))
        {
            testBut1 = (Button) view.findViewById(R.id.clear_butt1);
            myLayout1 = (LinearLayout) view.findViewById(R.id.my_layout1);
            testBut1.setVisibility(Button.GONE);
            myLayout1.setVisibility(LinearLayout.VISIBLE);
            String[] array2 = data2.split("\\|");
            double[] fishData2 = GoFish.goFish(array2);

            double lat = 0;
            double lon = 0;

                String[] l = mylatlon2.split("\\|");
                lat = Double.valueOf(l[0]);
                lon = Double.valueOf(l[1]);
                Uri uri = Uri.parse(String.format(URL, lat, lon));
                SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.map_image1);
                draweeView.setImageURI(uri);

            TextView loc1 = (TextView) view.findViewById(R.id.text_loc1);
            TextView day1 = (TextView) view.findViewById(R.id.date1_loc1);
            TextView day2 = (TextView) view.findViewById(R.id.date2_loc1);
            TextView day3 = (TextView) view.findViewById(R.id.date3_loc1);
            loc1.setText(array2[0]);
            day1.setText(array2[1]);
            day2.setText(array2[8]);
            day3.setText(array2[15]);
            TextView fish1 = (TextView) view.findViewById(R.id.fish1_loc1);
            TextView fish2 = (TextView) view.findViewById(R.id.fish2_loc1);
            TextView fish3 = (TextView) view.findViewById(R.id.fish3_loc1);
            fish1.setText(String.format("%.1f",fishData2[0])+ " / 7,0");
            fish2.setText(String.format("%.1f",fishData2[1])+ " / 7,0");
            fish3.setText(String.format("%.1f",fishData2[2])+ " / 7,0");


        }
        data3 = sharedPreferences.getString(Constants.SHARED_DATA3, null);
        mylatlon3 = sharedPreferences.getString(Constants.SHARED_LOC3, null);
        if (!TextUtils.isEmpty(data3)&& !TextUtils.isEmpty(mylatlon3))
        {
            testBut2 = (Button) view.findViewById(R.id.clear_butt2);
            myLayout2 = (LinearLayout) view.findViewById(R.id.my_layout2);
            testBut2.setVisibility(Button.GONE);
            myLayout2.setVisibility(LinearLayout.VISIBLE);
            String[] array3 = data3.split("\\|");
            double[] fishData3 = GoFish.goFish(array3);

            double lat = 0;
            double lon = 0;

            String[] l = mylatlon3.split("\\|");
            lat = Double.valueOf(l[0]);
            lon = Double.valueOf(l[1]);
            Uri uri = Uri.parse(String.format(URL, lat, lon));
            SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.map_image2);
            draweeView.setImageURI(uri);

            TextView loc2 = (TextView) view.findViewById(R.id.text_loc2);
            TextView day1 = (TextView) view.findViewById(R.id.date1_loc2);
            TextView day2 = (TextView) view.findViewById(R.id.date2_loc2);
            TextView day3 = (TextView) view.findViewById(R.id.date3_loc2);
            loc2.setText(array3[0]);
            day1.setText(array3[1]);
            day2.setText(array3[8]);
            day3.setText(array3[15]);
            TextView fish1 = (TextView) view.findViewById(R.id.fish1_loc2);
            TextView fish2 = (TextView) view.findViewById(R.id.fish2_loc2);
            TextView fish3 = (TextView) view.findViewById(R.id.fish3_loc2);
            fish1.setText(String.format("%.1f",fishData3[0])+ " / 7,0");
            fish2.setText(String.format("%.1f",fishData3[1])+ " / 7,0");
            fish3.setText(String.format("%.1f",fishData3[2])+ " / 7,0");


        }
        data4 = sharedPreferences.getString(Constants.SHARED_DATA4, null);
        mylatlon4 = sharedPreferences.getString(Constants.SHARED_LOC4, null);
        if (!TextUtils.isEmpty(data4)&& !TextUtils.isEmpty(mylatlon4))
        {
            testBut3 = (Button) view.findViewById(R.id.clear_butt3);
            myLayout3 = (LinearLayout) view.findViewById(R.id.my_layout3);
            testBut3.setVisibility(Button.GONE);
            myLayout3.setVisibility(LinearLayout.VISIBLE);
            String[] array4 = data4.split("\\|");
            double[] fishData4 = GoFish.goFish(array4);

            double lat = 0;
            double lon = 0;

            String[] l = mylatlon4.split("\\|");
            lat = Double.valueOf(l[0]);
            lon = Double.valueOf(l[1]);
            Uri uri = Uri.parse(String.format(URL, lat, lon));
            SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.map_image3);
            draweeView.setImageURI(uri);

            TextView loc3 = (TextView) view.findViewById(R.id.text_loc3);
            TextView day1 = (TextView) view.findViewById(R.id.date1_loc3);
            TextView day2 = (TextView) view.findViewById(R.id.date2_loc3);
            TextView day3 = (TextView) view.findViewById(R.id.date3_loc3);
            loc3.setText(array4[0]);
            day1.setText(array4[1]);
            day2.setText(array4[8]);
            day3.setText(array4[15]);
            TextView fish1 = (TextView) view.findViewById(R.id.fish1_loc3);
            TextView fish2 = (TextView) view.findViewById(R.id.fish2_loc3);
            TextView fish3 = (TextView) view.findViewById(R.id.fish3_loc3);
            fish1.setText(String.format("%.1f",fishData4[0])+ " / 7,0");
            fish2.setText(String.format("%.1f",fishData4[1])+ " / 7,0");
            fish3.setText(String.format("%.1f",fishData4[2])+ " / 7,0");


        }

        clos1 = (ImageButton) view.findViewById(R.id.close1);
        clos2 = (ImageButton) view.findViewById(R.id.close2);
        clos3 = (ImageButton) view.findViewById(R.id.close3);
        clos1.setOnClickListener(cleaner);
        clos2.setOnClickListener(cleaner);
        clos3.setOnClickListener(cleaner);
        vis1 = (ImageButton) view.findViewById(R.id.visible1);
        vis2 = (ImageButton) view.findViewById(R.id.visible2);
        vis3 = (ImageButton) view.findViewById(R.id.visible3);
        vis1.setOnClickListener(visible);
        vis2.setOnClickListener(visible);
        vis3.setOnClickListener(visible);

        return view;
    }

    View.OnClickListener cleaner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.close1:
                {
                    sharedPreferences.edit().remove(Constants.SHARED_DATA2)
                            .remove(Constants.SHARED_LOC2)
                            .apply();
                    testBut1.setVisibility(Button.VISIBLE);
                    myLayout1.setVisibility(LinearLayout.GONE);
                    break;
                }
                case R.id.close2:
                {
                    sharedPreferences.edit().remove(Constants.SHARED_DATA3)
                            .remove(Constants.SHARED_LOC3)
                            .apply();
                    testBut2.setVisibility(Button.VISIBLE);
                    myLayout2.setVisibility(LinearLayout.GONE);
                    break;
                }
                case R.id.close3:
                {
                    sharedPreferences.edit().remove(Constants.SHARED_DATA4)
                            .remove(Constants.SHARED_LOC4)
                            .apply();
                    testBut3.setVisibility(Button.VISIBLE);
                    myLayout3.setVisibility(LinearLayout.GONE);
                    break;
                }

            }
        }
    };

    View.OnClickListener visible = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String dd = null;
            String ll = null;

            switch (v.getId())
            {
                case R.id.visible1:
                {
                    dd = data2;
                    ll = mylatlon2;
                    break;
                }
                case R.id.visible2:
                {
                    dd = data3;
                    ll = mylatlon3;
                    break;
                }
                case R.id.visible3:
                {
                    dd = data4;
                    ll = mylatlon4;
                    break;
                }
            }
            sharedPreferences.edit().putString(Constants.SHARED_DATA1, dd)
                    .putString(Constants.SHARED_LOC, ll)
                    .apply();
            toolbarListener.switchFrafment(FragmentOne.createInstance(dd),
                    false, true, FragmentAnim.NONE);
        }
    };


    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (toolbarListener != null)
        {
            toolbarListener.setTitle((R.string.toolbartitleplac));
            toolbarListener.showDialogg(Constants.DIALOG_CONNECT);
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
}
