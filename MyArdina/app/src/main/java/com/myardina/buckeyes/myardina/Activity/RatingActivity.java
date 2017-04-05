package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.myardina.buckeyes.myardina.R;

public class RatingActivity extends AppCompatActivity {

    private TextView mRatingHeader;
    private Spinner mRatingSpinner;
    private Button mSubmitButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        //Text
        mRatingHeader = (TextView) findViewById(R.id.rating_header);
        mRatingHeader.append(" This Doctor"); //TODO: append doctor's actual name

        //Spinner
        mRatingSpinner = (Spinner) findViewById(R.id.rating_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rating_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRatingSpinner.setAdapter(adapter);


        //Buttons
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        handleButtonClicks();
    }

    private void handleButtonClicks() {
        //Submit:
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: update doctor's rating, then show success message
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        //Cancel:
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToLogin();
            }
        });
    }

    private void redirectToLogin() {
        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void onBackPressed() {
        //block backing out
    }
}
