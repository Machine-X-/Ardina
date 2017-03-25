package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.R;

public class ConfirmationActivity extends AppCompatActivity {
    private String LOG_TAG = "Confirmation";
    private Button mConfirmButton;
    private Button mCancelButton;
    private TextView mConfirmText;
    private LinearLayout mConfirmationButtons;
    private TextView mPatientNotesPrompt;
    private EditText mPatientNotes;
    private Button mSendButton;
    private DoctorDTO mDoctorDTO;
    private PatientDTO mPatientDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        mConfirmButton = (Button) findViewById(R.id.confirm_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mConfirmText = (TextView) findViewById(R.id.confirmation_text);
        mConfirmationButtons = (LinearLayout) findViewById(R.id.confirmation_buttons);
        mPatientNotesPrompt = (TextView) findViewById(R.id.patient_notes_instructions);
        mPatientNotes = (EditText) findViewById(R.id.patient_notes);
        mSendButton = (Button) findViewById(R.id.send_button);

        handleConfirmClickListeners();

    }

    private void handleConfirmClickListeners() {
        Log.d(LOG_TAG, "entering SetConfirmClickListeners...");

        //Confirm
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmText.setVisibility(View.GONE);
                mConfirmationButtons.setVisibility(View.GONE);
                mPatientNotesPrompt.setVisibility(View.VISIBLE);
                mPatientNotes.setVisibility(View.VISIBLE);
                mSendButton.setVisibility(View.VISIBLE);
                handlePatientNotesForm();
            }
        });

        //Cancel
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doctorAvailability = new Intent(ConfirmationActivity.this, DoctorsAvailableActivity.class);
                ConfirmationActivity.this.startActivity(doctorAvailability);
            }
        });
    }

    private void handlePatientNotesForm() {
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDoctorDTO = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);
                mPatientDTO = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",mDoctorDTO.getVisitWith(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ardina Confirmation");
                String body = mPatientNotes.getText().toString();
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
