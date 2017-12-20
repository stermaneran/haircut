package com.example.user.eran;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
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


    //private View bedit_info;

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

        //bedit_info = findViewById(R.id.edit_info);

        imageView = (ImageView) findViewById(R.id.user_photo);

        //get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        myRef = mFirebaseDatabase.getReference();

        //get current user
        user = mAuth.getCurrentUser();

        userID = user.getUid();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
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
        Customer uInfo = new Customer();
        dataSnapshot = dataSnapshot.child("Users").child(userID);

       // uInfo.set
        uInfo.setFname(dataSnapshot.child("FirstName").getValue().toString());
        uInfo.setGender(dataSnapshot.child("Gender").getValue().toString());
        uInfo.setLname(dataSnapshot.child("LastName").getValue().toString());
        uInfo.setCity(dataSnapshot.child("City").getValue().toString());
        uInfo.setStreet(dataSnapshot.child("Street").getValue().toString());
        uInfo.setUname(dataSnapshot.child("UserName").getValue().toString());

        uInfo.setImagePath(dataSnapshot.child("Photo").getValue().toString());

        if (!uInfo.getImagePath().contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(uInfo.getImagePath());
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            else{
                Picasso.with(profilePage.this).load(uInfo.getImagePath()).into(imageView);
            }
        gender.setText("Gender: " + uInfo.getGender());
        mfull_name.setText("Full Name: " + uInfo.getFname() + " " + uInfo.getLname());
        mdisplayed_name.setText("User Name: " +uInfo.getUname());
        memail_field.setText("Email: " +user.getEmail());
        maddress.setText("Address: " +uInfo.getStreet() + ", " + uInfo.getCity());


    }


    private void showApp(DataSnapshot dataSnapshot) {
        Appointment uInfo = new Appointment();
        dataSnapshot = dataSnapshot.child("Appointments").child(userID);

        // uInfo.set
        if(dataSnapshot.getValue()!=null) {
            uInfo.set_date(dataSnapshot.child("Date").getValue().toString());
            uInfo.set_time(dataSnapshot.child("Time").getValue().toString());

            mapp.setText("Appointment: " + uInfo.get_date() + " at " + uInfo.get_time());
        }
        else{
         mapp.setText("no Appointments set");
        }

    }


    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}



