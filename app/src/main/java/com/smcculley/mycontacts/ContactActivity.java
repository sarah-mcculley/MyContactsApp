package com.smcculley.mycontacts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class ContactActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new ContactFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
            .commit();
        }
    }


    public String getPackage(Context context) {
        return context.getPackageName();
    }
}
