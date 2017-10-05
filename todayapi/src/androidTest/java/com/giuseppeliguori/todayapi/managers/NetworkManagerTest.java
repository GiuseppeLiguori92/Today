package com.giuseppeliguori.todayapi.managers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by giuseppeliguori on 04/10/2017.
 */
public class NetworkManagerTest {

    private Context context;

    private NetworkInfo networkInfo;

    @Before
    public void setUp() throws Exception {
        context = mock(Context.class);

        ConnectivityManager connectivityManager = mock(ConnectivityManager.class);
        networkInfo = mock(NetworkInfo.class);

        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void isConnected_not_connected() throws Exception {
        when(networkInfo.isConnectedOrConnecting()).thenReturn(false);

        assertFalse(NetworkManager.getInstance().isConnected(context));
    }

    @Test
    public void isConnected_connected() throws Exception {
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);

        assertTrue(NetworkManager.getInstance().isConnected(context));
    }
}