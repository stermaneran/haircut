package com.example.user.eran;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class profilePage extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ImageView imageView;

    private FirebaseUser user;
    private TextView mfull_name;
    private TextView mdisplayed_name;
    private TextView memail_field;
    private TextView maddress;
    private TextView gender;
    private TextView mapp;

    private ValueEventListener listener;


    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mfull_name = (TextView) findViewById(R.id.full_name);
        mdisplayed_name = (TextView) findViewById(R.id.displayed_name);
        memail_field = (TextView) findViewById(R.id.email_field);
        maddress = (TextView) findViewById(R.id.address);
        gender = (TextView) findViewById(R.id.gender);
        mapp = (TextView) findViewById(R.id.app);

        imageView = (ImageView) findViewById(R.id.user_photo);

        //get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        myRef = mFirebaseDatabase.getReference();

        //get current user
        user = mAuth.getCurrentUser();

        userID = user.getUid();

        // Read from the database
        listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                showApp(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        dataSnapshot = dataSnapshot.child("Users").child(userID);
        Customer uInfo = dataSnapshot.getValue(Customer.class);
        if (!uInfo.imagePath.contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(uInfo.imagePath);
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(profilePage.this).load(uInfo.imagePath).into(imageView);
        }
        gender.setText("Gender: " + uInfo.gender);
        mfull_name.setText("Full Name: " + uInfo.FirstName + " " + uInfo.LastName);
        mdisplayed_name.setText("User Name: " + uInfo.UserName);
        memail_field.setText("Email: " + user.getEmail());
        maddress.setText("Address: " + uInfo.Street + ", " + uInfo.City);
    }


    private void showApp(DataSnapshot dataSnapshot) {

        dataSnapshot = dataSnapshot.child("Appointments").child(userID);
        Appointment uInfo = dataSnapshot.getValue(Appointment.class);

        if (dataSnapshot.getValue() != null) {
            mapp.setText("Appointment: " + uInfo._date + " at " + uInfo._time);
        } else {
            mapp.setText("No Appointments set");
        }

    }


    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            myRef.removeEventListener(listener);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}



