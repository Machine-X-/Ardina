package com.myardina.buckeyes.myardina;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.myardina.buckeyes.myardina.Activity.DoctorsAvailableActivity;
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
    double count = 0.0;
    double rating = 0.0;

    /**
     * Assumption: User has already registered and has paypal account
     * Just test that user can get to paypal app
     *
     * @throws Exception
     */

   /*
   Tests that the patient is able to submit ratings after a Video call
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
        solo.waitForView(1000);

        ToggleButton mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.waitForView(mToggleConnectButton, 4000, false);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);

        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 2000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        // Rate doctor as a "5/5"
        Spinner rating = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(rating, 4000, false);
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
   Tests that the patient is able to cancel ratings after a Video call
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
        solo.waitForView(1000);

        ToggleButton mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.waitForView(mToggleConnectButton, 4000, false);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 2000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        // Cancel, do not rate doctor
        Button cancelButton = (Button) solo.getCurrentActivity().findViewById(R.id.cancel_button);
        solo.waitForView(cancelButton, 4000, false);
        solo.clickOnView(cancelButton);

        solo.waitForActivity(LoginActivity.class, 2000);
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
    }
   /*
  Tests that average doctor ratings are updated after rating a doctor choosing Video call
  */

    public void testRatingActivityPatientVideoViewRatings() throws Exception {

        int ratingCount = 0;
        double docRating = 0;
        int indRating = 0;
        double average = 0;

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");

        mDoctorService = new DoctorServiceImpl();
        doctorDTO.setTotalRatingPoints(0);
        doctorDTO.setRatingCount(0);
        mDoctorService.updateDoctorRating(doctorDTO);

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
        solo.waitForActivity(SymptomsActivity.class, 4000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms Activity", SymptomsActivity.class);
        //click on continue button
        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(continueButton);
        solo.clickOnView(continueButton);
        solo.waitForView(continueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);

        Button bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(bypassPayPal, 2000, false);

        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);

        // Set rating count and total rating to 0
        solo.clickOnView(bypassPayPal);

        // Choose an available doctor from the list
        solo.clickInList(1);
        // Choose a communication method-video
        solo.clickOnButton(0);
        solo.waitForView(5);

        ToggleButton mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.waitForView(mToggleConnectButton, 4000, false);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);
        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 2000);
        // solo.sleep(2000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);


        // Rate doctor as a "5/5"
        Spinner rating = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(rating, 4000, false);
        solo.clickOnView(rating);
        solo.clickInList(6);
        docRating  += 5;
        ratingCount++;

        // Submit rating
        Button submitB = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
        solo.clickOnView(submitB);

        // update ratings for doctor
        solo.waitForActivity(LoginActivity.class, 2000);
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);

        /******Login again to rate doctor 0 ******/

        //solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "ardina@yahoo.com");
        //add password
        password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Ardina43212!");
        //click sign in button
        loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        //click on continue button
        continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        //solo.waitForView(continueButton, 4000, false);
        solo.waitForView(continueButton);
        solo.clickOnView(continueButton);
        solo.waitForView(continueButton);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);

        bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(bypassPayPal);

        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(bypassPayPal);

        // Choose an available doctor from the list
        average = docRating / ratingCount;
        // Check the average doctor rating
        assertEquals(5.0, average, 0.4);


        solo.clickInList(1);

        // Choose a communication method-video
        solo.clickOnButton(0);
        solo.waitForView(5);

        mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.waitForView(mToggleConnectButton, 4000, false);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);

        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 2000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        // Rate doctor as a "0/5"
        rating = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(rating, 4000, false);
        solo.clickOnView(rating);
        solo.clickInList(0);
        docRating += 0;
        ratingCount++;

        // Submit rating
        submitB = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
        solo.clickOnView(submitB);

        solo.waitForActivity(LoginActivity.class, 2000);
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);

        /******Login once more to verify average rating ******/

        //solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "ardina@yahoo.com");
        //add password
        password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Ardina43212!");
        //click sign in button
        loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        //click on continue button
        continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(continueButton, 4000, false);
        solo.clickOnView(continueButton);
        solo.waitForView(continueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);

        bypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(bypassPayPal, 2000, false);

        doctorDTO = new DoctorDTO();
        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(bypassPayPal);
        solo.waitForActivity(DoctorsAvailableActivity.class, 3000);
        //(true, solo.getCurrentActivity().findViewById(R.id.stars_2_half).isShown());
        average = docRating / ratingCount;
        assertEquals(2.5, average, 0.4);
        // Choose an available doctor from the list
        solo.clickInList(1);
        // Choose a communication method-video
        solo.clickOnButton(0);
        solo.waitForView(5);

        mToggleConnectButton = (ToggleButton) solo.getCurrentActivity().findViewById(R.id.toggleConnectButton);
        solo.waitForView(mToggleConnectButton, 4000, false);
        solo.clickOnView(mToggleConnectButton);
        solo.clickOnView(mToggleConnectButton);

        // check that we have the Rating activity
        solo.waitForActivity(RatingActivity.class, 2000);
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        // Rate doctor as a "5/5"
        rating = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(rating, 2000, false);
        solo.clickOnView(rating);
        solo.clickInList(5);

        // Submit rating
        submitB = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
        solo.clickOnView(submitB);

        solo.waitForActivity(LoginActivity.class, 2000);
        solo.assertCurrentActivity("Expected LoginActivity", LoginActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);

        // Clear ratings at end of test
        doctorDTO.setTotalRatingPoints(0);
        doctorDTO.setRatingCount(0);
        mDoctorService.updateDoctorRating(doctorDTO);

    }



    /**
     * Patient goes is taken back to Login Activity after ending the Chat Activity
     * @throws Exception
     */
    public void testRatingActivityPatientEndChat() throws Exception{

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

        solo.waitForView(1000);

        Button mEndChatButton = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
        solo.waitForView(mEndChatButton, 4000, false);
        solo.clickOnView(mEndChatButton);
        solo.waitForActivity(RatingActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);

        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);

    }


    public void testRatingActivityPatientCancelRating() throws Exception{

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


        Button mCancelRatingButton = (Button) solo.getCurrentActivity().findViewById(R.id.cancel_button);
        solo.waitForView(mCancelRatingButton, 4000, false);
        solo.clickOnView(mCancelRatingButton);
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);


        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);


    }


    public void testRatingActivityPatientSubmitRating1() throws Exception{

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
        solo.clickInList(2);

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
        mDoctorService.updateDoctorRating(doctorDTO);

    }


    public void testRatingActivityPatientSubmitRating2() throws Exception{

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase
        mDoctorService.updateDoctorRating(doctorDTO);


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


        mDoctorService.updateDoctorRating(doctorDTO);

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
        solo.clickInList(2);

        rating+= 1;
        count++;

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


        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText mEmail = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(mEmail, "ardina@yahoo.com");
        //add password
        EditText mPassword = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(mPassword, "Ardina43212!");
        //click sign in button
        Button mLoginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(mLoginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        //click on continue button
        Button mContinueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(mContinueButton, 4000, false);
        solo.clickOnView(mContinueButton);
        solo.waitForView(mContinueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 4000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now

        Button mBypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(mBypassPayPal, 2000, false);

        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(mBypassPayPal);

        solo.sleep(4000);
        solo.assertCurrentActivity("Expected DoctorAvailable activity", DoctorsAvailableActivity.class);
        assertEquals(1.0, rating/count);
        rating = 0.0;
        count = 0.0;

        mDoctorService.updateDoctorRating(doctorDTO);

    }

    public void testRatingActivityPatientSubmitRating3() throws Exception{

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase
        mDoctorService.updateDoctorRating(doctorDTO);

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

        mDoctorService.updateDoctorRating(doctorDTO);

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
        solo.clickInList(2);

        rating+= 1.0;
        count++;

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


        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText nEmail = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(nEmail, "ardina@yahoo.com");
        //add password
        EditText nPassword = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(nPassword, "Ardina43212!");
        //click sign in button
        Button nLoginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(nLoginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        //click on continue button
        Button nContinueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(nContinueButton, 4000, false);
        solo.clickOnView(nContinueButton);
        solo.waitForView(nContinueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 4000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now

        Button nBypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(nBypassPayPal, 2000, false);

        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(nBypassPayPal);

        solo.clickInList(1);
        solo.clickOnButton(2);

        solo.waitForView(5);

        Button nEndChatButton = (Button) solo.getCurrentActivity().findViewById(R.id.endChat);
        solo.waitForView(nEndChatButton, 4000, false);
        solo.clickOnView(nEndChatButton);
        solo.waitForActivity(RatingActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Rating Activity", RatingActivity.class);


        // Select Rating
        Spinner nSpinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.rating_spinner);
        solo.waitForView(nSpinner, 4000, false);
        solo.clickOnView(nSpinner);
        solo.clickInList(3);

        rating+= 2.0;
        count++;

        Button nSubmitRatingButton = (Button) solo.getCurrentActivity().findViewById(R.id.submit_button);
        solo.waitForView(nSubmitRatingButton, 4000, false);
        solo.clickOnView(nSubmitRatingButton);
        solo.waitForActivity(LoginActivity.class, 2000);
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login Activity", LoginActivity.class);


        doctorDTO.setAvailable(false);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);


        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText mEmail = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(mEmail, "ardina@yahoo.com");
        //add password
        EditText mPassword = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(mPassword, "Ardina43212!");
        //click sign in button
        Button mLoginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(mLoginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        //click on continue button
        Button mContinueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(mContinueButton, 4000, false);
        solo.clickOnView(mContinueButton);
        solo.waitForView(mContinueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 4000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now

        Button mBypassPayPal = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(mBypassPayPal, 2000, false);

        doctorDTO.setAvailable(true);
        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
        mDoctorService = new DoctorServiceImpl();
        mDoctorService.updateDoctorAvailability(doctorDTO);
        //Sets Doctor availability to true in Firebase

        solo.clickOnView(mBypassPayPal);
        solo.sleep(4000);
        solo.assertCurrentActivity("Expected DoctorAvailable activity", DoctorsAvailableActivity.class);
        assertEquals(1.5, rating/count);
        rating = 0.0;
        count = 0.0;

        mDoctorService.updateDoctorRating(doctorDTO);

    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }


}