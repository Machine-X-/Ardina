package com.myardina.buckeyes.myardina;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.myardina.buckeyes.myardina.Activity.ChatActivity;
import com.myardina.buckeyes.myardina.Activity.DoctorActivity;
import com.myardina.buckeyes.myardina.Activity.LoginActivity;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;
import com.robotium.solo.Solo;


/**
 * Created by cryst on 3/8/2017.
 */

public class ChatActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    public ChatActivityTest() {
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
//    public void testChatActivityStart() throws Exception{
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
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
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
//        //Click on 3rd option: Chat
//        solo.clickOnButton(2);
//
//        solo.waitForView(100);
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//    }
//
//    public void testChatActivityGoBackToDialogue() throws Exception{
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
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
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
//        // Click on 3rd button option: Chat
//        solo.clickOnButton(2);
//        solo.goBack();
//
//        solo.waitForView(100);
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//    }
//    public void testChatActivityGoBackToDoctorList() throws Exception{
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
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
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
//        solo.goBack();
//        solo.goBack();
//
//        solo.waitForView(50);
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//    }


//    public void testChatSendMessage1() throws Exception{
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
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
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
//        //Click on 3rd option: Chat
//        solo.clickOnButton(2);
//
//        solo.waitForView(5);
//
//        Button mSendButton = (Button) solo.getCurrentActivity().findViewById(R.id.btn_send);
//        EditText mEditText = (EditText) solo.getCurrentActivity().findViewById(R.id.etxt_message);
//        solo.waitForView(mSendButton, 4000, false);
//        solo.enterText(mEditText, "TEST1");
//        solo.clickOnView(mSendButton);
//        solo.waitForView(mSendButton, 4000, false);
//
//        solo.waitForView(5);
//
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//    }
//


//    public void testChatSendMessage2() throws Exception{
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
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
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
//        //Click on 3rd option: Chat
//        solo.clickOnButton(2);
//
//        solo.waitForView(5);
//
//        Button mSendButton = (Button) solo.getCurrentActivity().findViewById(R.id.btn_send);
//        EditText mEditText = (EditText) solo.getCurrentActivity().findViewById(R.id.etxt_message);
//        solo.waitForView(mSendButton, 4000, false);
//        solo.enterText(mEditText, "TEST1");
//        solo.clickOnView(mSendButton);
//        solo.waitForText("TEST1");
//        solo.enterText(mEditText, "TEST2");
//        solo.clickOnView(mSendButton);
//        solo.waitForView(mSendButton, 4000, false);
//
//
//        solo.waitForView(5);
//
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//    }




//    public void testChatSendMessage3() throws Exception{
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
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
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
//        //Click on 3rd option: Chat
//        solo.clickOnButton(2);
//
//        solo.waitForView(5);
//
//        Button mSendButton = (Button) solo.getCurrentActivity().findViewById(R.id.btn_send);
//        EditText mEditText = (EditText) solo.getCurrentActivity().findViewById(R.id.etxt_message);
//        solo.waitForView(mSendButton, 4000, false);
//        solo.enterText(mEditText, "TEST1");
//        solo.clickOnView(mSendButton);
//        solo.waitForText("TEST1");
//        solo.enterText(mEditText, "TEST2");
//        solo.clickOnView(mSendButton);
//        solo.waitForText("TEST2");
//        solo.enterText(mEditText, "TEST3");
//        solo.clickOnView(mSendButton);
//        solo.waitForView(mSendButton, 4000, false);
//
//
//        solo.waitForView(5);
//
//
//        doctorDTO.setAvailable(false);
//        doctorDTO.setTableKey("-KdbonOqVaOpQmswnUSW");
//        mDoctorService = new DoctorServiceImpl();
//        mDoctorService.updateDoctorAvailability(doctorDTO);
//
//    }



    public void testChatSendMessage3() throws Exception{

        solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 1000);
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

        Spinner spinner = (Spinner) solo.getCurrentActivity().findViewById(R.id.spinner_doctor_availability);
        solo.waitForView(spinner, 4000, false);
        solo.clickOnView(spinner);
        solo.clickInList(0);
//        solo.pressSpinnerItem(0,0);

//        solo.waitForActivity(ChatActivity.class, 2000);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent acceptIntent;

        acceptIntent = new Intent(solo.getCurrentActivity(), ChatActivity.class);

        DoctorDTO doctorDTO = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);

        acceptIntent.putExtra(CommonConstants.DOCTOR_DTO, doctorDTO);

        PendingIntent pendingAcceptIntent = PendingIntent.getActivity(solo.getCurrentActivity(), 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(solo.getCurrentActivity())
                        .setSmallIcon(R.drawable.splash)
                        .setContentTitle("Ardina Patient Request")
                        .setContentText("You have a request from a patient.");

        mBuilder.setContentIntent(pendingAcceptIntent);
        mNotificationManager.notify(1, mBuilder.build());


        solo.waitForView(1000);


//        solo.waitForView(spinner, 4000, false);

//        solo.waitForView(continueButton, 4000, false);
//        solo.clickOnView(continueButton);
//        solo.waitForView(continueButton, 4000, false);
//        //wait for and check that next activity is PatientPaymentActivity
//        solo.waitForActivity(PatientPaymentActivity.class, 2000);
//        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now



    }










}
