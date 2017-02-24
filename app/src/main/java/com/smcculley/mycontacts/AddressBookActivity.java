package com.smcculley.mycontacts;

import android.support.v4.app.Fragment;

/**
 * Created by smcculley on 2/23/2017.
 */

public class AddressBookActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AddressBookFragment();
    }
}
