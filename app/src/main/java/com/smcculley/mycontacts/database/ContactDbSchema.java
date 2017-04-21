package com.smcculley.mycontacts.database;

/**
 * Created by smcculley on 4/20/2017.
 */

public class ContactDbSchema {
    public static final class ContactTable {
        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String ADDRESS = "address";
            public static final String FAVORITE = "favorite";
            public static final String IMAGE = "image";
        }
    }
}
