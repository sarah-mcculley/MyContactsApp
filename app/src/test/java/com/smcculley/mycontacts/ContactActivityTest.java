package com.smcculley.mycontacts;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
/**
 * Created by smcculley on 2/16/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ContactActivityTest {
    private static final String PACKAGE_NAME = "com.smcculley.mycontacts";

    @Mock
    Context mContext;

    @Test
    public void getPackageTest() {
        ContactActivity activity = new ContactActivity();
        when(mContext.getPackageName()).thenReturn(PACKAGE_NAME);
        activity.getPackageName();
        assertEquals(PACKAGE_NAME, activity.getPackage(mContext));

    }

}
