package com.giuseppeliguori.todayapi.api;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.giuseppeliguori.todayapi.apiclass.Data;
import com.giuseppeliguori.todayapi.apiclass.Header;
import com.giuseppeliguori.todayapi.managers.NetworkManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by giuseppeliguori on 04/10/2017.
 */
public class TodayAPITest {

    private Context context;
    private TodayAPI mTodayAPI;

    @Before
    public void setUp() throws Exception {
        context = mock(Context.class);
        mTodayAPI = new TodayAPI(context);
        mTodayAPI = spy(mTodayAPI);

        ConnectivityManager connectivityManager = mock(ConnectivityManager.class);
        NetworkInfo networkInfo = mock(NetworkInfo.class);
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
        when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void requestData_data_null() throws Exception {

        TodayAPI.TodayCallback todayCallback = mock(TodayAPI.TodayCallback.class);

        when(mTodayAPI.isConnected()).thenReturn(false);

        Date date = new Date();
        mTodayAPI.requestData(todayCallback, date);

        verify(mTodayAPI).handleCall(todayCallback, date);
    }

    @Test
    public void requestData_data_not_null() throws Exception {

        TodayAPI.TodayCallback todayCallback = mock(TodayAPI.TodayCallback.class);

        when(mTodayAPI.isConnected()).thenReturn(false);

        mTodayAPI.requestData(todayCallback);

        verify(mTodayAPI).handleCall(todayCallback, null);
    }

    @Test
    public void handleCall_no_network() throws Exception {
        TodayAPI.TodayCallback todayCallback = mock(TodayAPI.TodayCallback.class);

        when(mTodayAPI.isConnected()).thenReturn(false);

        mTodayAPI.handleCall(todayCallback, null);
        verify(todayCallback).onFailure(TodayAPI.Failure.NO_NETWORK);
    }

    @Test
    public void handleCall() throws Exception {
        TodayAPI.TodayCallback todayCallback = mock(TodayAPI.TodayCallback.class);

        when(mTodayAPI.isConnected()).thenReturn(true);

        MuffinlabsAPI muffinlabsAPI = mock(MuffinlabsAPI.class);

        Call<Header> call = mTodayAPI.getCall(muffinlabsAPI, null);
        when(muffinlabsAPI.getData()).thenReturn(call);


        doNothing().when(mTodayAPI).getCallback(todayCallback);

        Callback callback = new Callback<Header>() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        };

        when(mTodayAPI.getCallback(todayCallback)).thenReturn(callback);

        mTodayAPI.handleCall(todayCallback, null);
        verify(call).enqueue(callback);
    }

    @Test
    public void getCall_date_null() throws Exception {
        MuffinlabsAPI muffinlabsAPI = mock(MuffinlabsAPI.class);
        mTodayAPI.getCall(muffinlabsAPI, null);
        verify(muffinlabsAPI).getData();
    }

    @Test
    public void getCall_date_not_null() throws Exception {
        MuffinlabsAPI muffinlabsAPI = mock(MuffinlabsAPI.class);
        Date date = mock(Date.class);
        mTodayAPI.getCall(muffinlabsAPI, date);
        verify(muffinlabsAPI).getData(any(int.class), any(int.class));
    }
}