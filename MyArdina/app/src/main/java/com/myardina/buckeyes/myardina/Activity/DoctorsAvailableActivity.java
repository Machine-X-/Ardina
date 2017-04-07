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
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
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
    private DoctorDTO mDoctorDTO;
    private DecimalFormat df = new DecimalFormat("#.##");
    private AlertDialog talkDialog;
    private ArrayList<DoctorDTO> activeDoctorsList;


    private DatabaseReference mDoctorsTable;

    private ArrayAdapter<String> mAdapter;
    private List<String> names;
    private Map<Integer, String> userKeys;
    private Map<Integer, DoctorDTO> doctorKeys;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeListeners();

        names = new ArrayList<>();
        userKeys = new HashMap<>();
        activeDoctorsList = new ArrayList<>();
        doctorKeys = new HashMap<>();

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
        View alertDialogView = createSpeakMethodDialog();
        addDialogListeners(alertDialogView);
        talkDialog = createAlertDialog(alertDialogView);

    }

    private void updateList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    private View createSpeakMethodDialog() {
        //bring external view into current activity
        LayoutInflater inflater = getLayoutInflater();
        return inflater.inflate(R.layout.doctors_available_contact_dialog, null);
    }

    private AlertDialog createAlertDialog(View alertDialogView) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DoctorsAvailableActivity.this);
        alertDialogBuilder.setView(alertDialogView);
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        return alertDialog;
    }

    private void addDialogListeners(View parentView) {


        Button phoneButton = (Button) parentView.findViewById(R.id.phone_button);
        final Button videoButton = (Button) parentView.findViewById(R.id.video_button);
        Button chatButton = (Button) parentView.findViewById(R.id.chat_button);


        final Intent videoIntent = new Intent(this, VideoActivity.class);
        final Intent phoneIntent = new Intent(this, TeleMedicineActivity.class);
        final Intent chatIntent = new Intent(this, ChatActivity.class);

        DoctorServiceImpl doctorService = new DoctorServiceImpl();


                phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneIntent.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
                phoneIntent.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                mDoctorDTO.setVideoRequested(false);
                mDoctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
                mDoctorDTO.setVisitWith(mPatientDTO.getEmail());
                phoneIntent.putExtra(CommonConstants.DOCTOR_DTO, mDoctorDTO);
                mPaymentDTO.setDoctorId(mDoctorDTO.getTableKey());
                mPaymentService.updatePaymentWithDoctor(mPaymentDTO);
                mDoctorsTable.removeEventListener(mValueEventListener);
                mDoctorService.updateDoctorToNotAvailable(mDoctorDTO);
                startActivity(phoneIntent);

            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoIntent.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
                videoIntent.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                mDoctorDTO.setVideoRequested(true);
                mDoctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
                mDoctorDTO.setVisitWith(mPatientDTO.getEmail());
                videoIntent.putExtra(CommonConstants.DOCTOR_DTO, mDoctorDTO);
                mPaymentDTO.setDoctorId(mDoctorDTO.getTableKey());
                mPaymentService.updatePaymentWithDoctor(mPaymentDTO);
                mDoctorsTable.removeEventListener(mValueEventListener);
                mDoctorService.updateDoctorToNotAvailable(mDoctorDTO);
                startActivity(videoIntent);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatIntent.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
                chatIntent.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                mDoctorDTO.setChatRequested(true);
                mDoctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
                mDoctorDTO.setVisitWith(mPatientDTO.getEmail());
                chatIntent.putExtra(CommonConstants.DOCTOR_DTO, mDoctorDTO);
                mPaymentDTO.setDoctorId(mDoctorDTO.getTableKey());
                mPaymentService.updatePaymentWithDoctor(mPaymentDTO);
                mDoctorsTable.removeEventListener(mValueEventListener);
                mDoctorService.updateDoctorToNotAvailable(mDoctorDTO);
                startActivity(chatIntent);
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
                activeDoctorsList.clear();
                int doctorCount = 0;
                for (DoctorDTO doctor : availableDoctors) {

                    double ratingAverage = (doctor.getRatingCount() == 0) ? 0 : doctor.getTotalRatingPoints() / (double) doctor.getRatingCount();
                    userKeys.put(names.size(), doctor.getTableKey());
                    doctorKeys.put(doctorCount, doctor);
                    String name = doctor.getFirstName() + CommonConstants.SPACE + doctor.getLastName() + CommonConstants.SPACE
                            + df.format(ratingAverage);
                    Log.d(LOG_TAG, "Name: " + name);
                    activeDoctorsList.add(doctor);
                    names.add(name);
                    doctorCount++;
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
//                        mDoctorDTO = new DoctorDTO();
//                        mDoctorDTO.setTableKey(userKeys.get(position));
                        mDoctorDTO = doctorKeys.get(position);
                        talkDialog.show();
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
    protected void onStart() {
        System.out.println("onStart method for LoginActivity being called");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        System.out.println("onRestart method for LoginActivity being called");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause method for LoginActivity being called");
        // Release db listener
        mDoctorsTable.removeEventListener(mValueEventListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        System.out.println("onResume method for LoginActivity being called");
        mDoctorsTable.addValueEventListener(mValueEventListener);
        updateList();
        super.onResume();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop method for LoginActivity being called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy method for LoginActivity being called");
        super.onDestroy();
    }


}
