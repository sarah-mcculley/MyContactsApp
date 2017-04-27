package com.smcculley.mycontacts;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by smcculley on 2/23/2017.
 */

public class AddressBookActivity extends SingleFragmentActivity implements AddressBookFragment.Callbacks, ContactFragment.Callbacks{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list_detail;
    }

    @Override
    protected Fragment createFragment() {
        return new AddressBookFragment();
    }

    @Override
    public void onContactSelected(Contact contact) {
        //phone
        if (findViewById(R.id.detail_fragment_container)== null){
            Intent intent = ContactPagerActivity.newIntent(this, contact.getID());
            startActivity(intent);
        }
        //tablet
        else {
            Fragment newDetail = ContactFragment.newInstance(contact.getID());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onContactUpdated() {
        AddressBookFragment addressBookFragment = (AddressBookFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        addressBookFragment.updateUI();
    }
}
