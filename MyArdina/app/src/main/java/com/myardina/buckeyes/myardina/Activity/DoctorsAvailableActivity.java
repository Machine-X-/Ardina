package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;
import com.myardina.buckeyes.myardina.Sevice.Impl.DoctorServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.Impl.PaymentServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.PaymentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsAvailableActivity extends AppCompatActivity {

    // Activity log tag
    private static final String LOG_TAG = "DOCTORS_AVAILABLE_ACT";

    // Data information objects
    private DoctorService mDoctorService;
    private PaymentService mPaymentService;
    private PatientDTO mPatientDTO;
    private PaymentDTO mPaymentDTO;

    private DatabaseReference mDoctorsTable;

    private ArrayAdapter<String> mAdapter;
    private List<String> names;
    private Map<Integer, String> userKeys;

    // Listeners
    private ValueEventListener mValueEventListener;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_available);
        //setting custom toolbar don't remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //setting back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeListeners();

        names = new ArrayList<>();
        userKeys = new HashMap<>();

        final ListView lvDoctorListView = (ListView) findViewById(R.id.lvDoctorsAvailableList);
        lvDoctorListView.setOnItemClickListener(mOnItemClickListener);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        lvDoctorListView.setAdapter(mAdapter);


        mPatientDTO = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);
        mPaymentDTO = (PaymentDTO) getIntent().getExtras().get(CommonConstants.PAYMENT_DTO);
        mDoctorService = new DoctorServiceImpl();
        mPaymentService = new PaymentServiceImpl();

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mDoctorsTable = mRef.getReference().child(CommonConstants.DOCTORS_TABLE);

        Log.d(LOG_TAG, "Exiting onCreate...");

        updateList();

    }

    private void updateList(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private View createSpeakMethodDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DoctorsAvailableActivity.this);

        //bring external view into current activity
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.doctors_available_contact_dialog, null);
        alertDialogBuilder.setView(dialoglayout);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        return dialoglayout;

    }

    private void addDialogListeners(View parentView, final DoctorDTO doctorDTO){
        Button phoneButton = (Button)parentView.findViewById(R.id.phone_button);
        Button videoButton = (Button)parentView.findViewById(R.id.video_button);

        final Intent videoIntent = new Intent(this, VideoActivity.class);
        final Intent phoneIntent = new Intent(this, TeleMedicineActivity.class);


        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneIntent.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
                phoneIntent.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                doctorDTO.setVideoRequested(false);
                doctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
                mDoctorService.updateDoctorToNotAvailable(doctorDTO);
                mPaymentDTO.setDoctorId(doctorDTO.getTableKey());
                mPaymentService.updatePaymentWithDoctor(mPaymentDTO);
                mDoctorsTable.removeEventListener(mValueEventListener);
                startActivity(phoneIntent);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoIntent.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
                videoIntent.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                doctorDTO.setVideoRequested(true);
                doctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
                mDoctorService.updateDoctorToNotAvailable(doctorDTO);
                mPaymentDTO.setDoctorId(doctorDTO.getTableKey());
                mPaymentService.updatePaymentWithDoctor(mPaymentDTO);
                mDoctorsTable.removeEventListener(mValueEventListener);
                startActivity(videoIntent);
            }
        });



    }

    private void initializeListeners() {
        Log.d(LOG_TAG, "Entering initializeListeners...");
        mValueEventListener = new ValueEventListener() {
            /**
             * Live update the list of available doctors displayed to the user
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                names.clear();
                List<DoctorDTO> availableDoctors = mDoctorService.retrieveAvailableDoctors(dataSnapshot);
                for (DoctorDTO doctor : availableDoctors) {
                    userKeys.put(names.size(), doctor.getTableKey());
                    String name = doctor.getFirstName() + CommonConstants.SPACE + doctor.getLastName();
                    Log.d(LOG_TAG, "Name: " + name);
                    names.add(name);
                }

                if (names.size() == 0) {
                    Toast.makeText(DoctorsAvailableActivity.this, CommonConstants.NO_DOCTORS_MESSAGE, Toast.LENGTH_LONG).show();
                }
                updateList();
                Log.d(LOG_TAG, "Exiting onDataChange...");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LOG_TAG, "Entering onCancelled...");
                Log.d(LOG_TAG, "ERROR : " + databaseError.getMessage());
                Log.d(LOG_TAG, "Exiting onCancelled...");
            }
        };

        mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "Entering onItemClick...");
                int viewId = parent.getId();
                switch (viewId) {
                    case R.id.lvDoctorsAvailableList:
                        DoctorDTO doctorDTO = new DoctorDTO();
                        doctorDTO.setTableKey(userKeys.get(position));
                        View parentView = createSpeakMethodDialog();
                        addDialogListeners(parentView, doctorDTO);
                        break;
                    default:
                        break;
                }
                Log.d(LOG_TAG, "Exiting onItemClick...");
            }
        };
        Log.d(LOG_TAG, "Exiting initializeListeners...");
    }

    /**
     * *************************
     * ACTIVITY STATE LOGIC  *
     * *************************
     */

    @Override
    protected void onStart(){
        System.out.println("onStart method for LoginActivity being called");
        super.onStart();
    }

    @Override
    protected void onRestart(){
        System.out.println("onRestart method for LoginActivity being called");
        super.onRestart();
    }

    @Override
    protected void onPause(){
        System.out.println("onPause method for LoginActivity being called");
        // Release db listener
        mDoctorsTable.removeEventListener(mValueEventListener);
        super.onPause();
    }

    @Override
    protected void onResume(){
        System.out.println("onResume method for LoginActivity being called");
        mDoctorsTable.addValueEventListener(mValueEventListener);
        updateList();
        super.onResume();
    }

    @Override
    protected void onStop()
    {
        System.out.println("onStop method for LoginActivity being called");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        System.out.println("onDestroy method for LoginActivity being called");
        super.onDestroy();
    }
}
