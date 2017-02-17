package com.smcculley.mycontacts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



/**
 * Created by smcculley on 2/16/2017.
 */

public class ContactClass {

    @Test
    public void contactNameTest()
    {
        String name = "Test name";
        Contact contact = new Contact();
        contact.setName(name);
        assertEquals(name, contact.getName());
    }

    @Test
    public void contactEmailTest() {
        String email = "test@email.com";
        Contact contact = new Contact();
        contact.setEmail(email);
        assertEquals(email, contact.getEmail());
    }

    @Test
    public void UUIDCreationTest() {
        Contact contact = new Contact();
        assertNotNull(contact.getID());
    }
}
