package com.accademy.harvin.harvinacademy.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;

import org.w3c.dom.Text;

/**
 * Created by ishank on 4/9/17.
 */

public class Badges {
    private static TextView badgeView;
    private static Context context;
    private boolean blue=false;
    private boolean green=false;
    private boolean orange=false;


    public static TextView getGreenBadge(String text, TextView badgeView,Context context){
        badgeView.setBackground(ContextCompat.getDrawable(context,R.drawable.badge_green));
        badgeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,8);

        badgeView.setText(text);
        return badgeView;
    }

    public static TextView getBlueBadge(String text, TextView badgeView,Context context){
        badgeView.setBackground(ContextCompat.getDrawable(context,R.drawable.badge_blue));
        badgeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,8);
        badgeView.setText(text);
        return badgeView;
    }

    public static TextView getOrangeBadge(String text, TextView badgeView,Context context){
        badgeView.setBackground(ContextCompat.getDrawable(context,R.drawable.badge_orange));
        badgeView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,8);
        badgeView.setText(text);
        return badgeView;
    }
    public static RelativeLayout.LayoutParams getLayoutarams(){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return  params;
    }
}
