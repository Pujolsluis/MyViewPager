package com.nopeia.viewpager.myviewpage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pujolsluis on 1/8/2018.
 */

public class IntroManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    IntroManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("first", 0);
        editor = sharedPreferences.edit();
    }

    public void setFirst(Boolean isFirst) {
        editor.putBoolean("check", isFirst);
        editor.commit();
    }

    public boolean Check() {
        return sharedPreferences.getBoolean("check", true);
    }


}
