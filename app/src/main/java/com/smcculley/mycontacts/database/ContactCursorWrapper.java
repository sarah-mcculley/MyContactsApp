package com.smcculley.mycontacts.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smcculley.mycontacts.Contact;

import java.util.UUID;

/**
 * Created by smcculley on 4/20/2017.
 */

public class ContactCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Contact getContact() {
        String uuid = getString(getColumnIndex(ContactDbSchema.ContactTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactDbSchema.ContactTable.Cols.NAME));
        String email = getString(getColumnIndex(ContactDbSchema.ContactTable.Cols.EMAIL));
        String favorite = getString(getColumnIndex(ContactDbSchema.ContactTable.Cols.FAVORITE));
        String address = getString(getColumnIndex(ContactDbSchema.ContactTable.Cols.ADDRESS));
        byte[] imageData = getBlob(getColumnIndex(ContactDbSchema.ContactTable.Cols.IMAGE));
        // convert the byte array into a Bitmap
        Bitmap image = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);


        Contact contact = new Contact(UUID.fromString(uuid));
        contact.setName(name);
        contact.setEmail(email);
        contact.setFavorite(favorite.equals("true"));
        contact.setImage(image);

        return contact;
    }
}
