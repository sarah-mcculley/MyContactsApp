package com.smcculley.mycontacts;

import android.graphics.Bitmap;
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
    private boolean mFavorite;
    private String mAddress;
    private Bitmap mImage;

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }



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
        //Log.i(TAG, "New name: " + name);
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        //Log.i(TAG, "New email: " + email);
        mEmail = email;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
        Log.d(TAG, "New address: " + mAddress);
    }


    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        Log.i(TAG, "Favorite: " + favorite);
        this.mFavorite = favorite;
    }

}
