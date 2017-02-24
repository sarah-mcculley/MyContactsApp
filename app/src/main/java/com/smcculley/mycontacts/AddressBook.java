package com.smcculley.mycontacts;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Created by smcculley on 2/23/2017.
 */

public class AddressBook {
    private static AddressBook mAddressBook;
    private List<Contact> mContacts;

    private AddressBook(){
        mContacts = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Contact contact = new Contact();
            contact.setName("Person "+ i);
            contact.setEmail("Email " + i + "@email.com");
            if (i % 10 == 0){
                contact.setFavorite(true);
            }
        }
    }

    //singleton pattern
    public static AddressBook get(){
        if(mAddressBook == null){
            mAddressBook = new AddressBook();
        }
        return mAddressBook;
    }

    public List<Contact> getContacts(){
        return mContacts;
    }

    public Contact getContact(UUID id){
        for (Contact contact: mContacts){
            if(contact.getID().equals(id)){
                return contact;
            }
        }
        return null;
    }
}
