package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Sevice.Impl.DoctorServiceImpl;

public class RatingActivity extends AppCompatActivity {

    private TextView mRatingHeader;
    private Spinner mRatingSpinner;
    private Button mSubmitButton;
    private Button mCancelButton;
    private DoctorDTO mDoctorDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        mDoctorDTO = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);

        //Text
        mRatingHeader = (TextView) findViewById(R.id.rating_header);
        mRatingHeader.append(" " + mDoctorDTO.getFirstName() + " " + mDoctorDTO.getLastName());

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
                mDoctorDTO.setRatingCount(mDoctorDTO.getRatingCount() + 1);
                int score = Integer.parseInt((String)mRatingSpinner.getSelectedItem());
                mDoctorDTO.setTotalRatingPoints(mDoctorDTO.getTotalRatingPoints() + score);
                DoctorServiceImpl doctorService = new DoctorServiceImpl();
                doctorService.updateDoctorRating(mDoctorDTO);
                mSubmitButton.setEnabled(false);
                mCancelButton.setEnabled(false);
                createDelay();
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                redirectToLogin();
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

    private void createDelay() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3500);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    @Override
    public void onBackPressed() {
        //block backing out
    }

}
