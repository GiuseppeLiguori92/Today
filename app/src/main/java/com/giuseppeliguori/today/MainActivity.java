package com.giuseppeliguori.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Contract.View {
    private static final String TAG = "MainActivity";

    private Presenter presenter;

    @BindView(R.id.main_layout) ConstraintLayout mainLayout;

    @BindView(R.id.textView_dateMonthDay) TextView textViewDateMonthDay;
    @BindView(R.id.textView_dateDayYear) TextView textViewDateDayYear;

    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        snackbar = Snackbar.make(mainLayout, "", 0);

        presenter = new Presenter(this);

        presenter.requestDate();
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
        Log.d(TAG, "onRequestDataStarted() called");
    }

    @Override
    public void onRequestDataSuccess() {
        List<Event> events = presenter.requestEvents();
        List<Birth> births = presenter.requestBirths();
        List<Death> deaths = presenter.requestDeaths();

        for (int i = 0; i < events.size(); i++) {
            Log.d(TAG, "onRequestDataSuccess: " + events.get(i).getText());
        }

        for (int i = 0; i < births.size(); i++) {
            Log.d(TAG, "onRequestDataSuccess: " + births.get(i).getText());
        }

        for (int i = 0; i < deaths.size(); i++) {
            Log.d(TAG, "onRequestDataSuccess: " + deaths.get(i).getText());
        }
    }

    @Override
    public void onRequestDataFailure() {
    }

    @Override
    public void setDateView(String dateMonthDay, String dateDayYear) {
        textViewDateMonthDay.setText(dateMonthDay);
        textViewDateDayYear.setText(dateDayYear);
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