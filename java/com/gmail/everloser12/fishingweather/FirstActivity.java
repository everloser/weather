package com.gmail.everloser12.fishingweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.gmail.everloser12.fishingweather.constants.Constants;
import com.gmail.everloser12.fishingweather.dialogs.MyDialogs;
import com.gmail.everloser12.fishingweather.fragments.BaseFragment;
import com.gmail.everloser12.fishingweather.fragments.FragmentOne;
import com.gmail.everloser12.fishingweather.fragments.ActivityListener;
import com.gmail.everloser12.fishingweather.fragments.FragmentAnim;

public class FirstActivity extends AppCompatActivity implements ActivityListener{


    private TextView mTextView;
    SharedPreferences sharedPreferences;

    public static void showActivity(Activity activity, String text)
    {
        Intent intent = new Intent(activity, FirstActivity.class);
        intent.putExtra(Constants.INTENT_KEY,text);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        sharedPreferences = MyApplication.getInstance().
                getSharedPreferences("file", Context.MODE_PRIVATE);
        initToolbar();
        // получение данных с погодой из интента
        Intent intent = getIntent();
        String text = intent.getStringExtra(Constants.INTENT_KEY);


        showFrag(FragmentOne.createInstance(text), false, false, FragmentAnim.NONE);



    }


    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextView = (TextView) toolbar.findViewById(R.id.tooltext);
//        mTextView.setText(R.string.toolbartext);
//
//
//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String loca =  sharedPreferences.getString(Constants.SHARED_LOC, null);
//                showFrag(MapaFragment.createInstance(getSupportFragmentManager(), loca), true, false, FragmentAnim.RIGHT_TO_LEFT);
//            }
//        });
        toolbar.setNavigationIcon(R.drawable.ic_fish_f);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(FirstActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//                showFrag(PlacesFragment.createInstance(getSupportFragmentManager(), "", "", ""), true, false, FragmentAnim.RIGHT_TO_LEFT);
//
//            }
//        });
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {

            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }


    }

    private void showFrag(BaseFragment fragment, boolean addBack, boolean clearBackStack, FragmentAnim fragmentAnim)
    {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragmentAnim == FragmentAnim.RIGHT_TO_LEFT)

            transaction.setCustomAnimations(R.anim.frag_enter_from_right, R.anim.frag_exit_to_left,
                    R.anim.frag_enter_from_left, R.anim.frag_exit_from_right);

        transaction.replace(R.id.container, fragment, fragment.getFragmentTag());
        if (addBack)
            transaction.addToBackStack(null);
        if (clearBackStack)
        {
            while (getSupportFragmentManager().getBackStackEntryCount()>0)
            {getSupportFragmentManager().popBackStackImmediate();}
        }
        transaction.commit();
    }

    @Override
    public void switchFrafment(BaseFragment fragment, boolean addBackStack, boolean clearBackStack, FragmentAnim fragmentAnim) {

        showFrag(fragment, addBackStack, clearBackStack, fragmentAnim);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTextView.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTextView.setText(titleId);
    }

    @Override
    public void showDialogg(int numberDialog)
    {
        String error =  sharedPreferences.getString(Constants.SHARED_ERROR, null);
        if (error!=null && error.equals("1"))
        {
            DialogFragment ddd = MyDialogs.newInstance(numberDialog);
            ddd.show(getSupportFragmentManager(), "dial1");
            sharedPreferences.edit().putString(Constants.SHARED_ERROR, "0").apply();
        }
    }


}
