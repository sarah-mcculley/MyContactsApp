package com.smcculley.mycontacts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class ContactActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ContactFragment();
    }



}
