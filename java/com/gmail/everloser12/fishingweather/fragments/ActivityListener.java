package com.gmail.everloser12.fishingweather.fragments;

/**
 * Created by al-ev on 05.05.2016.
 */
public interface ActivityListener {

    void setTitle (CharSequence name);
    void setTitle (int resTitleId);
    void switchFrafment(BaseFragment fragment, boolean addBackStack, boolean clearBackStack, FragmentAnim fragmentAnim);
    void showDialogg(int numberDialog);
}
