package com.smcculley.mycontacts;

import android.util.Log;

import java.util.UUID;

/**
 * Created by smcculley on 2/9/2017.
 */

public class Contact {
    private static final String TAG = Contact.class.getSimpleName();
    private UUID mID;
    private String mName;
    private String mEmail;

    public Contact() {
        mID = UUID.randomUUID();
    }

    public UUID getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        Log.i(TAG, "New name: " + name);
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        Log.i(TAG, "New email: " + email);
        mEmail = email;
    }
}
