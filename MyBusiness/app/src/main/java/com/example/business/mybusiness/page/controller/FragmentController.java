package com.example.business.mybusiness.page.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.example.business.mybusiness.R;

/**
 * Created by zhang.la on 2015/9/17.
 */
public class FragmentController {

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int contentId, String tag) {
        if (null != fragment && null != fragmentManager) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.comm_fragment_anim_in, R.anim.comm_fragment_anim_out);
            Fragment fragment1 = fragmentManager.findFragmentById(contentId);
            if(fragment1 != null){
                if (fragment1 instanceof FragmentManager.OnBackStackChangedListener) {
                    fragmentManager.addOnBackStackChangedListener((FragmentManager.OnBackStackChangedListener) fragment1);
                }
                transaction.hide(fragment1);
            }
            transaction.add(contentId, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commitAllowingStateLoss();
        }
    }

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, String tag) {
        addFragment(fragmentManager, fragment, Window.ID_ANDROID_CONTENT, tag);
    }
}
