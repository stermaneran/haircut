package com.example.user.eran;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

 import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

    private View bedit_info;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mfull_name = (TextView) findViewById(R.id.full_name);
        mdisplayed_name = (TextView) findViewById(R.id.displayed_name);
        memail_field = (TextView) findViewById(R.id.email_field);
        maddress = (TextView) findViewById(R.id.address);


        bedit_info = findViewById(R.id.edit_info);

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
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {
        eranCustomer uInfo = new eranCustomer();
        dataSnapshot = dataSnapshot.child("customer").child(userID);

       // uInfo.set
        uInfo.setFname(dataSnapshot.child("FirstName").getValue().toString());
        uInfo.setLname(dataSnapshot.child("LastName").getValue().toString());
        uInfo.setCity(dataSnapshot.child("City").getValue().toString());
        uInfo.setStreet(dataSnapshot.child("Street").getValue().toString());
        uInfo.setUname(dataSnapshot.child("UserName").getValue().toString());

        uInfo.setImagePath(dataSnapshot.child("Photo").getValue().toString());


        Picasso.with(profilePage.this).load(uInfo.getImagePath()).into(imageView);
        mfull_name.setText("Full Name: " + uInfo.getFname() + " " + uInfo.getLname());
        mdisplayed_name.setText("User Name: " +uInfo.getUname());
        memail_field.setText("Email: " +user.getEmail());
        maddress.setText("Address: " +uInfo.getStreet() + ", " + uInfo.getCity());


    }



//    public void onProfileImageViewClick(View v){
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String pictureDirectoryPath = pictureDirectory.getPath();
//
//        Uri data = Uri.parse(pictureDirectoryPath);
//
//        photoPickerIntent.setDataAndType(data,"image/*");
//
//        startActivityForResult(photoPickerIntent, 20);
//
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            // if we are here, everything processed successfully.
//            if (requestCode == 20) {
//                // if we are here, we are hearing back from the image gallery.
//
//                // the address of the image on the SD Card.
//                Uri imageUri = data.getData();
//
//                // declare a stream to read the image data from the SD Card.
//                InputStream inputStream;
//
//                // we are getting an input stream, based on the URI of the image.
//                try {
//                    inputStream = getContentResolver().openInputStream(imageUri);
//
//                    // get a bitmap from the stream.
//                    Bitmap image = BitmapFactory.decodeStream(inputStream);
//
//                    uploadImageBtn.setVisibility(View.GONE);
//
//                    // show the image to the user
//                    profilePictureView.setImageBitmap(image);
//
//
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    // show a message to the user indictating that the image is unavailable.
//                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }
}



