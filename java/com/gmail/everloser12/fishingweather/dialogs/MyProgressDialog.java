package com.gmail.everloser12.fishingweather.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.everloser12.fishingweather.R;

/**
 * Created by al-ev on 19.05.2016.
 */
public class MyProgressDialog extends Dialog {

    private ImageView iv;

    public MyProgressDialog(Context context) {

        super(context, R.style.MyProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        iv = new ImageView(context);
        iv.setBackgroundResource(R.drawable.pop_anim);
        layout.addView(iv, params);
        addContentView(layout, params);

    }

    @Override
    public void show() {
        super.show();
        AnimationDrawable frameAnimation = (AnimationDrawable) iv.getBackground();

        frameAnimation.start();
    }
}
