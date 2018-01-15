package com.example.user.eran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class basePage extends AppCompatActivity {

    private Button btnSignOut,btnProfile,btnMap,btnREADME,btnaddApp,btnusers,appsm,priceListBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String userID;
    private static final String TAG = "ViewUser";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_page);


        btnMap = (Button) findViewById(R.id.Map);
        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        btnProfile = (Button) findViewById(R.id.profle);
        btnREADME= (Button) findViewById(R.id.readme);
        btnaddApp= (Button) findViewById(R.id.addApp);
        btnusers= (Button) findViewById(R.id.usersb);
        appsm= (Button) findViewById(R.id.apps);
        priceListBtn = (Button) findViewById(R.id.btnPriceList);


        mAuth=FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        myRef = mFirebaseDatabase.getReference();

        //get current user
        user = mAuth.getCurrentUser();

//        if(user!=null) {
            userID = user.getUid();
//        }
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(isAdmin(dataSnapshot)){
                    btnusers.setVisibility(View.VISIBLE);
                    appsm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
                Intent myIntent = new Intent(basePage.this, logIn.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(basePage.this, mapsActivity.class);
                startActivityForResult(myIntent, 0);
                //finish();
            }
        });

        btnaddApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(basePage.this, addApp.class);
                startActivityForResult(myIntent, 0);
                //finish();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(basePage.this, profilePage.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

        btnREADME.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(basePage.this, readme.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

        btnusers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(basePage.this, AllUsers.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

        appsm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(basePage.this, AllApp.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

        priceListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(basePage.this, PricelistPage.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

    }




    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private boolean isAdmin(DataSnapshot dataSnapshot) {
        try {
            dataSnapshot = dataSnapshot.child("Users").child(userID);
            if (dataSnapshot.child("Type").getValue().toString().equals("Admin")) {
                return true;
            } else return false;
        }
        catch(java.lang.NullPointerException e){
            return false;
        }
    }
}
