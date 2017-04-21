package com.smcculley.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.smcculley.mycontacts.database.ContactBaseHelper;
import com.smcculley.mycontacts.database.ContactCursorWrapper;
import com.smcculley.mycontacts.database.ContactDbSchema;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by smcculley on 2/23/2017.
 */

public class AddressBook {
    private static AddressBook sAddressBook;
    private SQLiteDatabase mDatabase;



    private AddressBook(Context context){
        mDatabase = new ContactBaseHelper(context).getWritableDatabase();
    }

    //singleton pattern
    public static AddressBook get(Context context){
        if(sAddressBook == null){
            sAddressBook = new AddressBook(context);
        }
        return sAddressBook;
    }

    public void add(Contact contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactDbSchema.ContactTable.NAME, null, values);
    }

    public void updateContact(Contact contact) {
        String uuidString = contact.getID().toString();
        ContentValues values = getContentValues(contact);
        mDatabase.update(ContactDbSchema.ContactTable.NAME, values,
                ContactDbSchema.ContactTable.Cols.UUID  + " = ?",
                new String[] { uuidString });
    }

    public List<Contact> getContacts(){

        return new ArrayList<>();
    }

    public List<Contact> getFavoriteContacts(){
        return new ArrayList<>();
    }

    public Contact getContact(UUID id){
        return null;
    }

    private static ContentValues getContentValues(Contact contact) {
        // convert image to a byte array for storage
        byte[] imageData = {};
        if (contact.getImage() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            contact.getImage().compress(Bitmap.CompressFormat.PNG, 0, stream);
            imageData = stream.toByteArray();
        }

        ContentValues values = new ContentValues();
        values.put(ContactDbSchema.ContactTable.Cols.UUID, contact.getID().toString());
        values.put(ContactDbSchema.ContactTable.Cols.NAME, contact.getName());
        values.put(ContactDbSchema.ContactTable.Cols.EMAIL, contact.getEmail());
        values.put(ContactDbSchema.ContactTable.Cols.FAVORITE, contact.isFavorite() ? "true" : "false");
        values.put(ContactDbSchema.ContactTable.Cols.ADDRESS, contact.getAddress());
        values.put(ContactDbSchema.ContactTable.Cols.IMAGE, imageData);

        return values;
    }

    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(ContactDbSchema.ContactTable.NAME, null, whereClause, whereArgs,
                null, null, null, null);
        return new ContactCursorWrapper(cursor);
    }
}
