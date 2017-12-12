package com.example.user.eran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class eranBasePage extends AppCompatActivity {

    private Button btnSignOut,btnProfile,btnMap,btnREADME;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eran_base_page);

        btnMap = (Button) findViewById(R.id.Map);
        btnSignOut = (Button) findViewById(R.id.button4);
        btnProfile = (Button) findViewById(R.id.profle);
        btnREADME= (Button) findViewById(R.id.readme);

        mAuth=FirebaseAuth.getInstance();


        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
                Intent myIntent = new Intent(eranBasePage.this, eranLogIn.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(eranBasePage.this, eranMapsActivity.class);
                startActivityForResult(myIntent, 0);
                //finish();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(eranBasePage.this, profilePage.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

        btnREADME.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(eranBasePage.this, eranreadme.class);
                startActivityForResult(i, 0);
                //finish();
            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
