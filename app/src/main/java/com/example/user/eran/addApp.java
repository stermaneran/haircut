package com.example.user.eran;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class addApp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    protected  String datestr;
    protected  String timestr;
    Button submitBtn,pickDateBtn,pickTimeBtn;
    TextView dateTitle,timeTitle;
    DatePickerDialog datePickerDialog;

    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Appointments");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        pickDateBtn = (Button) findViewById(R.id.pickDateBtn);
        pickTimeBtn = (Button) findViewById(R.id.pickTimeBtn);
        dateTitle = (TextView) findViewById(R.id.dateTitle);
        timeTitle = (TextView) findViewById(R.id.timeTitle);

        mAuth = FirebaseAuth.getInstance();
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                final Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                // date picker dialog
                datePickerDialog = new DatePickerDialog(addApp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datepicker,
                                                  int selectedyear, int selectedmonth,
                                                  int selectedday) {

                                mcurrentDate.set(Calendar.YEAR, selectedyear);
                                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                                dateTitle.setText(sdf.format(mcurrentDate.getTime()));
                                datestr=selectedday + "/"
                                        + (selectedmonth + 1) + "/" + selectedyear;
                            }

                        },mYear, mMonth, mDay);

                mcurrentDate.set(mYear,mMonth,mDay);
                long value=mcurrentDate.getTimeInMillis();
                datePickerDialog.getDatePicker().setMinDate(value);

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

                if(validateForm()) {
                    Appointment app = new Appointment(datestr, timestr, mAuth.getCurrentUser().getUid().toString(), "100");
                    app.save();
                    toastMessage("Appointment submitted");
                }
            }
        });

    }

    public boolean validateForm(){
        if (datestr==null) {
            toastMessage("Enter Date");
            return false;
        }

        if (timestr==null) {
            toastMessage("Enter Time");
            return false;
        }
        return true;

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
