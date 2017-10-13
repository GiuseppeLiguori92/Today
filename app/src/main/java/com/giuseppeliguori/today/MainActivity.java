package com.giuseppeliguori.today;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Contract.View {
    private static final String TAG = "MainActivity";

    private Presenter presenter;

    @BindView(R.id.main_layout) ConstraintLayout mainLayout;

    @BindView(R.id.textView_dateMonthDay) TextView textViewDateMonthDay;
    @BindView(R.id.textView_dateDayYear) TextView textViewDateDayYear;

    @BindView(R.id.layout_noItems) LinearLayout layoutNoItems;

    @BindView(R.id.recycleView) RecyclerView recyclerView;
    private RecyclerView.LayoutManager recycleViewLayoutManager;

    private Snackbar snackbar;

    private Menu menu;
    private String dateMonthDay;

    @BindView(R.id.calendarView) CalendarView calendarView;
    private int calendarHeight = 0;
    private boolean isCalendarVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        recycleViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recycleViewLayoutManager);
        snackbar = Snackbar.make(mainLayout, "", 0);

        calendarHeight = calendarView.getHeight();
        calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calendarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                calendarHeight = calendarView.getHeight();
                calendarView.setY(-calendarHeight);
            }
        });

        presenter = new Presenter(this);

        presenter.requestDate(new Date());
        presenter.requestData();
    }

    @Override
    public void onConnectionEstablished() {
        showConnectionEstablishedSnackbar();
    }

    private void showConnectionEstablishedSnackbar() {
        showNoConnectionSnackbar();
    }

    private void showNoConnectionSnackbar() {
        snackbar.setText(R.string.connection_established);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorSnackbarSuccess));
        snackbar.setDuration(Snackbar.LENGTH_SHORT);
        snackbar.setAction("", null); snackbar.show();
    }

    @Override
    public void onConnectionLost() {
        snackbar.setText(R.string.connection_lost);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorSnackbarError));
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorSnackbarErrorActionText));
        snackbar.setAction(getString(R.string.settings).toUpperCase(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        }); snackbar.show();
    }

    @Override
    public void onRequestDataStarted() {
        recyclerView.setVisibility(View.GONE);
        layoutNoItems.setVisibility(View.GONE);
    }

    @Override
    public void onRequestDataSuccess() {
        recyclerView.setVisibility(View.VISIBLE);
        RecycleViewPresenter recycleViewPresenter = new RecycleViewPresenter(presenter.getData());
        RecycleViewAdapter mAdapter = new RecycleViewAdapter(recycleViewPresenter);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRequestDataFailure() {
        layoutNoItems.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDateView(String dateMonthDay, String dateDayYear) {
        this.dateMonthDay = dateMonthDay.substring(0, 1).toUpperCase() + dateMonthDay.substring(1);
        textViewDateMonthDay.setText(this.dateMonthDay);
        textViewDateDayYear.setText(dateDayYear.substring(0, 1).toUpperCase() + dateDayYear.substring(1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        if (menu != null) {
            menu.getItem(0).setTitle(new String(Character.toChars(0x1F4C5)));
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.date:

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(isCalendarVisible ? -calendarHeight, 3f);
                int mDuration = 3000; //in millis
                valueAnimator.setDuration(mDuration);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        view.setTranslationX((float)animation.getAnimatedValue());
                    }
                });
                valueAnimator.setRepeatCount(5);
                valueAnimator.start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}