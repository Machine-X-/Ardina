package com.myardina.buckeyes.myardina;
        import android.app.AlertDialog;
        import android.app.DialogFragment;
        import android.test.ActivityInstrumentationTestCase2;
        import android.widget.Button;
        import android.widget.EditText;

        import com.myardina.buckeyes.myardina.Activity.DoctorsAvailableActivity;
        import com.myardina.buckeyes.myardina.Activity.LoginActivity;
        import com.myardina.buckeyes.myardina.Activity.PatientPaymentActivity;
        import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
        import com.paypal.android.sdk.payments.PaymentActivity;
        import com.robotium.solo.Solo;
/**
 * Created by cryst on 2/19/2017.
 */

public class VideoActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    public VideoActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Assumption: User has already registered and the account is in the database
     * Clicks on leg and chooses "pink eye" for symptom, goes to PatientPaymentActivity
     * @throws Exception
     */
    public void testVideoCallChoice() throws Exception {
        solo.unlockScreen();
        //solo.sleep(5000);
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
        solo.waitForActivity(LoginActivity.class, 1000); // was 1000
        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        solo.sleep(5000);
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "c@p.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Dummy1");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000); // was 2000
        solo.sleep(5000); //added
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
        Button bLegs = (Button) solo.getCurrentActivity().findViewById(R.id.mv_legs_button);
        //click on the legs
        solo.waitForView(bLegs, 3000, false);
        solo.clickOnView(bLegs);
        //symptoms picker fragment should show up with symptoms associated with legs
        solo.waitForFragmentByTag("SymptomsPickerDialog",2000);
        DialogFragment SymptomsPickerFragment = (DialogFragment) solo.getCurrentActivity().getFragmentManager().findFragmentById(0);
        //get the view elements inside fragment & click on pink eye option
        AlertDialog SymptomsPickerDialog = (AlertDialog) SymptomsPickerFragment.getDialog();
        //check the pink eye
        solo.clickOnView(SymptomsPickerDialog.getListView().getChildAt(2));

        //needs to wait or else things get messed up
        solo.waitForDialogToClose(2500);
        SymptomsPickerFragment.dismiss();

        //check that symptoms list is just pink eye
        assertEquals(1, SymptomsActivity.mSelList.size());
        assertEquals(1, SymptomsActivity.mSymptoms.size());
        assertEquals(2, (int) SymptomsActivity.mSelList.get(0));
        assertEquals("Pink Eye", SymptomsActivity.mSymptoms.get(0));

        //click on continue button
        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.clickOnView(continueButton);

        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected Payment activity", PatientPaymentActivity.class);

        //should be on payment activity now
        continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        //solo.sleep(5000);
        solo.waitForView(continueButton, 10000, false);
        solo.clickOnView(continueButton);
        //solo.sleep(5000);
        //wait for and check that next activity is DoctorsAvailableActivity
        solo.waitForActivity(DoctorsAvailableActivity.class, 2000); //was 2000
        solo.assertCurrentActivity("Expected Doctors Available activity", DoctorsAvailableActivity.class);
        //Should be Available Doctors activity now, set doctor to available
        solo.sleep(5000);

        /*
        To test:
            -Get to call choice screen
                -test : click on Video
                 -test: click on Phone
                 -Check that it gets to Video activity
                 -Check that it gets to Phone activity
                 -Check after choosing Video, back out to Choice screen and dialog buttons work
                 -Check after choosing Phone, back out to Choice screen and dialog buttons work
                 -Check after back out of video choice that back to choice activity
                 -Check after back out of phone choice that back to choice activity


         */
    }
}
