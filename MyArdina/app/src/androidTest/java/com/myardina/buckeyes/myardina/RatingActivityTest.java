package com.myardina.buckeyes.myardina;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.myardina.buckeyes.myardina.Activity.DoctorActivity;
import com.myardina.buckeyes.myardina.Activity.LoginActivity;
import com.myardina.buckeyes.myardina.Activity.PatientPaymentActivity;
import com.myardina.buckeyes.myardina.Activity.RatingActivity;
import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;
import com.myardina.buckeyes.myardina.Sevice.Impl.DoctorServiceImpl;
import com.robotium.solo.Solo;

/**
 * Created by Crystal on 4/7/2017.
 */

public class RatingActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public RatingActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    private DoctorService mDoctorService;

    /**
     * Assumption: User has already registered and has paypal account
     * Just test that user can get to paypal app
     *
     * @throws Exception
     */

    /**
     * Patient goes is taken back to Login Activity after ending the Chat Activity
     * @throws Exception
     */
//    public void testRatingActivityPatientEndChat() throws Exception{
//
//        solo.unlockScreen();
//        //This code just logs in and gets to symptom activity,
//        //repeat of code of testing successful login from login activity test
//        solo.waitForActivity(LoginActivity.class, 1000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
//        //add username
//        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
//        solo.enterText(email, "ardina@yahoo.com");
//        //add password
//        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
//        solo.enterText(password, "Ardina43212!");
//        //click sign in button
//        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
//        solo.clickOnView(loginBtn);
//        //waiting for login in case there is a network delay
//        solo.waitForActivity(SymptomsActivity.class, 2000);
//        // assert that the current activity is the SymptomsActivity.class
//        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
//        //click on continue button
//        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
//        solo.waitForView(continueButton, 4000, false);
//        solo.clickOnView(continueButton);
//        solo.waitForView(continueButton, 4000, false);
//        //wait for and check that next activity is PatientPaymentActivity
//        solo.waitForActivity(PatientPaymentActivity.class, 4000);
//        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
//        //should be on payment activity now
//
//        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
//        solo.waitForView(bypassPayPal, 2000, false);
//
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setAvailable(true);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//        //Sets Doctor availability to true in Firebase
//
//        solo.clickOnView(bypassPayPal);
//
//        solo.clickInList(1);
//        solo.clickOnButton(2);
//
//        solo.waitForView(5);
//
//        Button mEndChatButton = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
//        solo.waitForView(mEndChatButton, 4000, false);
//        solo.clickOnView(mEndChatButton);
//        solo.waitForActivity(RatingActivity.class, 2000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//
//    }


//    public void testRatingActivityPatientCancelRating() throws Exception{
//
//        solo.unlockScreen();
//        //This code just logs in and gets to symptom activity,
//        //repeat of code of testing successful login from login activity test
//        solo.waitForActivity(LoginActivity.class, 1000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
//        //add username
//        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
//        solo.enterText(email, "ardina@yahoo.com");
//        //add password
//        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
//        solo.enterText(password, "Ardina43212!");
//        //click sign in button
//        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
//        solo.clickOnView(loginBtn);
//        //waiting for login in case there is a network delay
//        solo.waitForActivity(SymptomsActivity.class, 2000);
//        // assert that the current activity is the SymptomsActivity.class
//        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
//        //click on continue button
//        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
//        solo.waitForView(continueButton, 4000, false);
//        solo.clickOnView(continueButton);
//        solo.waitForView(continueButton, 4000, false);
//        //wait for and check that next activity is PatientPaymentActivity
//        solo.waitForActivity(PatientPaymentActivity.class, 4000);
//        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
//        //should be on payment activity now
//
//        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
//        solo.waitForView(bypassPayPal, 2000, false);
//
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setAvailable(true);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//        //Sets Doctor availability to true in Firebase
//
//        solo.clickOnView(bypassPayPal);
//
//        solo.clickInList(1);
//        solo.clickOnButton(2);
//
//        solo.waitForView(5);
//
//        Button mEndChatButton = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
//        solo.waitForView(mEndChatButton, 4000, false);
//        solo.clickOnView(mEndChatButton);
//        solo.waitForActivity(RatingActivity.class, 2000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);
//
//
//        Button mCancelRatingButton = (Button) solo.getCurrentActivity().findViewById(R.id.cancel_button);
//        solo.waitForView(mCancelRatingButton, 4000, false);
//        solo.clickOnView(mCancelRatingButton);
//        solo.waitForActivity(LoginActivity.class, 2000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
//
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//
//    }


//    public void testRatingActivityPatientSubmitRating() throws Exception{
//
//        solo.unlockScreen();
//        //This code just logs in and gets to symptom activity,
//        //repeat of code of testing successful login from login activity test
//        solo.waitForActivity(LoginActivity.class, 1000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
//        //add username
//        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
//        solo.enterText(email, "ardina@yahoo.com");
//        //add password
//        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
//        solo.enterText(password, "Ardina43212!");
//        //click sign in button
//        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
//        solo.clickOnView(loginBtn);
//        //waiting for login in case there is a network delay
//        solo.waitForActivity(SymptomsActivity.class, 2000);
//        // assert that the current activity is the SymptomsActivity.class
//        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
//        //click on continue button
//        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
//        solo.waitForView(continueButton, 4000, false);
//        solo.clickOnView(continueButton);
//        solo.waitForView(continueButton, 4000, false);
//        //wait for and check that next activity is PatientPaymentActivity
//        solo.waitForActivity(PatientPaymentActivity.class, 4000);
//        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
//        //should be on payment activity now
//
//        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
//        solo.waitForView(bypassPayPal, 2000, false);
//
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setAvailable(true);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//        //Sets Doctor availability to true in Firebase
//
//        solo.clickOnView(bypassPayPal);
//
//        solo.clickInList(1);
//        solo.clickOnButton(2);
//
//        solo.waitForView(5);
//
//        Button mEndChatButton = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
//        solo.waitForView(mEndChatButton, 4000, false);
//        solo.clickOnView(mEndChatButton);
//        solo.waitForActivity(RatingActivity.class, 2000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);
//
//
//        // Select Rating
//        Spinner spinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
//        solo.waitForView(spinner, 4000, false);
//        solo.clickOnView(spinner);
//        solo.clickInList(2);
//
//        Button mSubmitRatingButton = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
//        solo.waitForView(mSubmitRatingButton, 4000, false);
//        solo.clickOnView(mSubmitRatingButton);
//        solo.waitForActivity(LoginActivity.class, 2000);
//        // check that we have the right activity
//        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);
//
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//        mDoctorService.updateDoctorRating(doctorDTO);
//
//
//
//    }


    public void testRatingActivityPatientSubmitRating() throws Exception{

        solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 1000);
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
        solo.waitForActivity(PatientPaymentActivity.class, 4000);
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
        solo.clickOnButton(2);

        solo.waitForView(5);

        Button mEndChatButton = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
        solo.waitForView(mEndChatButton, 4000, false);
        solo.clickOnView(mEndChatButton);
        solo.waitForActivity(RatingActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);


        // Select Rating
        Spinner spinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(spinner, 4000, false);
        solo.clickOnView(spinner);
        solo.clickInList(5);

        Button mSubmitRatingButton = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
        solo.waitForView(mSubmitRatingButton, 4000, false);
        solo.clickOnView(mSubmitRatingButton);
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);


        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);



        //add username
        EditText mEmail = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(mEmail, "junit@yahoo.com");
        //add password
        EditText mPassword = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(mPassword, "Junit43212!");
        //click sign in button
        Button mLoginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(mLoginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(DoctorActivity.class, 2000);
        solo.assertCurrentActivity("Expected Doctor Activity",DoctorActivity.class);

        // Set doctor to available
        Spinner mSpinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.spinner_doctor_availability);
        solo.waitForView(mSpinner, 4000, false);
        solo.clickOnView(mSpinner);
        solo.clickInList(0);

        mDoctorService.updateDoctorRating(doctorDTO);




    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }


}