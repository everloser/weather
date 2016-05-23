package com.google.everloser12.fishingweather.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by al-ev on 05.05.2016.
 */
public interface ActivityListener {

    void setTitle (CharSequence name);
    void setTitle (int resTitleId);
    void switchFrafment(BaseFragment fragment, boolean addBackStack, boolean clearBackStack, FragmentAnim fragmentAnim);
    void showDialogg(int numberDialog);
}
