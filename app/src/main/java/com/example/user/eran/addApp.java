package com.example.user.eran;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.Calendar;
import java.util.Map;




public class addApp extends AppCompatActivity {

    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    protected  String datestr;
    protected  String timestr;
    Button submitBtn,pickDateBtn,pickTimeBtn;
    TextView dateTitle,timeTitle;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app);
        ref = FirebaseDatabase.getInstance().getReference();
        submitBtn = (Button) findViewById(R.id.submitBtn);
        pickDateBtn = (Button) findViewById(R.id.pickDateBtn);
        pickTimeBtn = (Button) findViewById(R.id.pickTimeBtn);
        dateTitle = (TextView) findViewById(R.id.dateTitle);
        timeTitle = (TextView) findViewById(R.id.timeTitle);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(addApp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                datestr=dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                                // set day of month , month and year value in the edit text
                                dateTitle.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                final int mMinutes = c.get(Calendar.MINUTE);
                // date picker dialog
                TimePickerDialog timePicker = new TimePickerDialog(addApp.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                timestr = mHour + ":" + mMinutes;
                                timeTitle.setText(mHour + ":" + mMinutes);
                            }
                        }, mHour,mMinutes,true);
                timePicker.show();
            }
        });



        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Appointment app = new Appointment(datestr,timestr,mAuth.getCurrentUser().getUid().toString(),"100");
                Log.v("TEST","Created App");
                ref.child("Test");
            }
        });


    }


}
