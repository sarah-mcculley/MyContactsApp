package com.smcculley.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by smcculley on 3/9/2017.
 */

//allows us to swipe through pages

public class ContactPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Contact> mContacts;
    private static final String EXTRA_CONTACT_ID = "com.smcculley.mycontacts.contact_id";

    public static Intent newIntent(Context context, UUID contactID) {
        Intent intent = new Intent(context, ContactPagerActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contactID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_contact_pager_view_pager);
        mContacts = AddressBook.get().getContacts();
        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                                  @Override
                                  //called as we swipe with the number of the next index position
                                  public Fragment getItem(int position) {
                                      Contact contact = mContacts.get(position);
                                      return ContactFragment.newInstance(contact.getID());
                                  }

                                  @Override
                                  public int getCount() {
                                      return mContacts.size();
                                  }
                              }
        );

        //loads details for person that was tapped
        UUID contactID = (UUID) getIntent().getSerializableExtra(EXTRA_CONTACT_ID);
        for(int index = 0; index < mContacts.size(); index++){
            if (mContacts.get(index).getID().equals(contactID)){
                mViewPager.setCurrentItem(index);
                break;
            }
        }


    }


}
