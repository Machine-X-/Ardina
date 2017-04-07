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
import com.myardina.buckeyes.myardina.Activity.RatingActivity;
import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
import com.myardina.buckeyes.myardina.Activity.VideoActivity;
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

    /*
    Tests that the patient is able to view the ratings on the Doctor availability page before
    choosing Video call
    */
    public void testRatingActivityPatientVideoSubmit() throws Exception {

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

        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(bypassPayPal, 2000, false);

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(bypassPayPal);

        // Choose an available doctor from the list
        solo.clickInList(1);
        // Choose a communication method-video
        solo.clickOnButton(0);
        solo.waitForView(5);

        ToggleButton mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 2000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        // Rate doctor as a "5/5"
        Spinner rating = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(rating, 2000, false);
        solo.clickOnView(rating);
        solo.clickInList(5);

        // Submit rating
        Button submitB = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
        solo.clickOnView(submitB);

        solo.waitForActivity(LoginActivity.class, 2000);
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
    }

    /*
    Tests that the patient is able to view the ratings on the Doctor availability page before
    choosing Video call
    */
    public void testRatingActivityPatientVideoCancel() throws Exception {

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

        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(bypassPayPal, 2000, false);

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(bypassPayPal);

        // Choose an available doctor from the list
        solo.clickInList(1);
        // Choose a communication method-video
        solo.clickOnButton(0);
        solo.waitForView(5);

        ToggleButton mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 4000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        // Cancel, do not rate doctor
        Button cancelButton = (Button) solo.getCurrentActivity().findViewById(R.id.cancel_button);
        solo.clickOnView(cancelButton);

        solo.waitForActivity(LoginActivity.class, 2000);
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
    }
}