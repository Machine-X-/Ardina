package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                //TODO: Setup email intent to patient with the text from mPatientNotes
            }
        });
    }
}
