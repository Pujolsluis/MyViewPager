package com.nopeia.viewpager.myviewpage;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Pujolsluis on 12/7/2017.
 */

public class ActivityUtils {

    private static final String TAG = ActivityUtils.class.getSimpleName();

    @SuppressLint("RestrictedApi")
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        String backStateName =  fragment.getClass().getName();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, backStateName);
        transaction.commit();
    }

    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId){

        //Avoid Fragments stacking up in the backstack if they already exist in it
        String fragmentTag =  fragment.getClass().getName();

        Fragment fragmentInStack = fragmentManager.findFragmentByTag(fragmentTag);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragmentInStack == null){ //fragment not in back stack, create it.
            Log.d(TAG, "The fragment doesn't exit create it");
            // Replace fragment, with fade in and fadeout animation
            // and add transaction to backstack to allow the user to undo
            // the action he just did and recover the last fragment state
            transaction.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
            );
            transaction = hideVisibleFragmentInActivity(fragmentManager, transaction);
            transaction.add(frameId, fragment, fragmentTag);
            transaction.addToBackStack(fragmentTag);
            transaction.show(fragment);
            transaction.commit();

        } else {
            Log.d(TAG, "The fragment exists just show it");
            transaction = hideVisibleFragmentInActivity(fragmentManager, transaction);
            transaction.show(fragment);
            transaction.commit();
        }

    }

    private static FragmentTransaction hideVisibleFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                                     @NonNull FragmentTransaction fragmentTransaction){
        List<Fragment> fragments = fragmentManager.getFragments();

        fragmentTransaction = fragmentManager.beginTransaction();

        if (fragments != null) {
            for(Fragment fragment1 : fragments) {
                if (fragment1 != null && fragment1.isVisible()) {
                    fragmentTransaction.hide(fragment1);
                }
            }
        }

        return fragmentTransaction;

    }

}
