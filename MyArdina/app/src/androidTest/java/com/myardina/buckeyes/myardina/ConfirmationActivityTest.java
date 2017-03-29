package com.myardina.buckeyes.myardina;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.myardina.buckeyes.myardina.Activity.ChatActivity;
import com.myardina.buckeyes.myardina.Activity.ConfirmationActivity;
import com.myardina.buckeyes.myardina.Activity.DoctorActivity;
import com.myardina.buckeyes.myardina.Activity.LoginActivity;
import com.myardina.buckeyes.myardina.Activity.PatientPaymentActivity;
import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;
import com.myardina.buckeyes.myardina.Sevice.Impl.DoctorServiceImpl;
import com.robotium.solo.Solo;

/**
 * Created by Chuck T on 3/27/2017.
 */

public class ConfirmationActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    public ConfirmationActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    private DoctorService mDoctorService;


    /**
     * Assumption: User has already registered and has paypal account
     * Just test that user can get to paypal app
     * @throws Exception
     */
    public void testConfirmationActivityPatientVideo() throws Exception{

        solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "ardina@yahoo.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Ardina43212!");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        //click on continue button
        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(continueButton, 4000, false);
        solo.clickOnView(continueButton);
        solo.waitForView(continueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now

        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(bypassPayPal, 2000, false);

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(bypassPayPal);

        solo.clickInList(1);
        solo.clickOnButton(0);

        solo.waitForView(5);

        ToggleButton mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Confirmation Activity", LoginActivity.class);

        solo.waitForView(5);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);


    }


            /**
      * Test that doctor goes to Confirmation Activity after chat ends
      * @throws Exception
      */
    public void testConfirmationActivityDoctorChat() throws Exception{
            
        solo.unlockScreen();
            //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 2000);
            // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
            //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "junit@yahoo.com");
            //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Junit43212!");
            //click sign in button
                    Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
            //waiting for login in case there is a network delay
        solo.waitForActivity(DoctorActivity.class, 2000);
        solo.assertCurrentActivity("Expected Doctor Activity",DoctorActivity.class);

                    // Set doctor to available
        Spinner spinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.spinner_doctor_availability);
        solo.waitForView(spinner, 4000, false);
        solo.clickOnView(spinner);
        solo.clickInList(0);

                // Continue to go directly to chat session
        Button continueBtn = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_chat);
        solo.clickOnView(continueBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(ChatActivity.class, 2000);
        solo.assertCurrentActivity("Expected Chat Activity",ChatActivity.class);
        solo.waitForView(5);

                // Send a test message
        Button mSendButton = (Button) solo.getCurrentActivity().findViewById(R.id.btn_send);
        EditText mEditText = (EditText) solo.getCurrentActivity().findViewById(R.id.etxt_message);
        solo.waitForView(mSendButton, 4000, false);
        solo.enterText(mEditText, "TEST1");
        solo.clickOnView(mSendButton);
        solo.waitForText("TEST1");

                    // Click end chat button and go to Confirmation activity
        Button endBtn = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
        solo.clickOnView(endBtn);
        solo.waitForActivity(ConfirmationActivity.class, 5000);
        solo.assertCurrentActivity("Expected Confirmation Activity", ConfirmationActivity.class);
    }

            /**
      * Test that doctor goes to Confirmation Activity after chat ends and
      * confirms appointment
      * @throws Exception
      */
    public void testConfirmationActivityDoctorChatConfirm() throws Exception{
            
        solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
                //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "junit@yahoo.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Junit43212!");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(DoctorActivity.class, 2000);
        solo.assertCurrentActivity("Expected Doctor Activity",DoctorActivity.class);

        // Set doctor to available
        Spinner spinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.spinner_doctor_availability);
        solo.waitForView(spinner, 4000, false);
        solo.clickOnView(spinner);
        solo.clickInList(0);

        // Continue to go directly to chat session
        Button continueBtn = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_chat);
        solo.clickOnView(continueBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(ChatActivity.class, 2000);
        solo.assertCurrentActivity("Expected Chat Activity",ChatActivity.class);
        solo.waitForView(5);

        // Send a test message
        Button mBtnSend = (Button) solo.getCurrentActivity().findViewById(R.id.btn_send);
        EditText mEditText = (EditText) solo.getCurrentActivity().findViewById(R.id.etxt_message);
        solo.waitForView(mBtnSend, 4000, false);
        solo.enterText(mEditText, "TEST1");
        solo.clickOnView(mBtnSend);
        solo.waitForText("TEST1");

        // Click end chat button and go to Confirmation activity
        Button endBtn = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
        solo.clickOnView(endBtn);
        solo.waitForActivity(ConfirmationActivity.class, 5000);
        solo.assertCurrentActivity("Expected Confirmation Activity", ConfirmationActivity.class);

        // Click on Confirm button and go to patient visit form
        Button confirmBtn = (Button) solo.getCurrentActivity().findViewById(R.id.confirm_button);
        solo.waitForView(confirmBtn);
        solo.clickOnView(confirmBtn);
        solo.waitForActivity(ConfirmationActivity.class, 2000);



        // Enter patient notes and send confirmation email
        DoctorDTO mDoctorDTO = new DoctorDTO();
        PatientDTO mPatientDTO = new PatientDTO();

        mDoctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mPatientDTO.setTableKey("-KcduJgyWTwaF4zq39JY");
        //mDoctorDTO = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);
        //mPatientDTO = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);
        Button mSendButton = (Button) solo.getCurrentActivity().findViewById(R.id.send_button);
        EditText mPatientNotes = (EditText) solo.getCurrentActivity().findViewById(R.id.patient_notes);
        solo.enterText(mPatientNotes, "Test patient notes");

                /* NOTE / TO FIX: Fails after hitting send button on patient notes form: Null object reference in
           ConfirmationActivity line 81
          *///solo.clickOnView(mSendButton);
                                            
                                                    
    }


    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

}

