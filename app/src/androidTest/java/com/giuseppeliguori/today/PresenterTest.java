package com.giuseppeliguori.today;

import static junit.framework.Assert.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by giuseppeliguori on 05/10/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class PresenterTest {

    @Captor
    private ArgumentCaptor<String> captorMonthDay;

    @Captor
    private ArgumentCaptor<String> captorDayYear;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void requestDate() throws Exception {
        MainActivity activity = mock(MainActivity.class);
        Presenter presenter = new Presenter(activity);
        presenter = spy(presenter);

        Date date = new Date();
        date.setTime(1507196625448L);
        presenter.requestDate(date);

        verify(activity).setDateView(anyString(), anyString());

        verify(activity).setDateView(captorMonthDay.capture(), captorDayYear.capture());
        assertEquals("October 5", captorMonthDay.getValue());
        assertEquals("Thursday, 2017", captorDayYear.getValue());
    }

}