package com.smcculley.mycontacts;


import android.support.v4.app.Fragment;

/**
 * Created by smcculley on 4/20/2017.
 */

public class SettingsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SettingsFragment();
    }

}
