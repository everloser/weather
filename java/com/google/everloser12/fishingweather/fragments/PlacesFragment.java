package com.google.everloser12.fishingweather.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.everloser12.fishingweather.R;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
    }


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
